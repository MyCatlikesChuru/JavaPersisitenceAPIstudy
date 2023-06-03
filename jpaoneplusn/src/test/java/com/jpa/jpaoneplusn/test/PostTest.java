package com.jpa.jpaoneplusn.test;

import com.jpa.jpaoneplusn.domain.entity.Posts;
import com.jpa.jpaoneplusn.domain.entity.PostsTag;
import com.jpa.jpaoneplusn.domain.entity.Tag;
import com.jpa.jpaoneplusn.domain.repository.PostsRepository;
import com.jpa.jpaoneplusn.domain.repository.TagRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
public class PostTest {

    @Autowired
    EntityManager em;
    JPAQueryFactory queryFactory;
    @Autowired
    PostsRepository postsRepository;
    @Autowired
    TagRepository tagRepository;

    private static Long POST_ID;


    // 1개의 포스트만 조회할 경우
    // Lazy -> N+1개의 TAG가 조회된다.
    // Eager -> POST, POSTS_TAG의 해당하는 SELECT JOIN쿼리만 발생한다.
    @BeforeEach
    public void setUp() {
        queryFactory = new JPAQueryFactory(em);
        Posts post = Posts.builder().title("계절별 강수량 조사 게시글 입니다.").build();
        Posts savedPost = postsRepository.save(post);
        POST_ID = savedPost.getId();

        // 1개의 게시글, 4개의 태그
        // 중간테이블 postTag = 1개의 게시글에 각각 태그를 연결해놓은 상황
        for (long i = 1; i <= 4; i++) {
            String[] seasons = new String[]{"봄","여름","가을","겨울"};
            String season = seasons[(int)(i - 1L)];
            Tag tag = Tag.builder().name(season).type("계절").build();
            PostsTag postTag = PostsTag.builder().tag(tag).posts(post).build();
            tag.addPostsTag(postTag);
            tagRepository.save(tag);
        }
    }

    @Test
    @DisplayName("N+1 문제 발생하는 테스트 케이스")
    public void jpaProblem() {

        // 영속성 컨텍스트 초기화
        em.flush();
        em.clear();

        System.out.println("============= 쿼리 시작 =============");
        Posts findPost = postsRepository.findPost_noFetchJoin(POST_ID);


        System.out.println("============= PostsTag 가져오기 =============");
        for (PostsTag postsTag : findPost.getPostsTags()) {
            System.out.println("PostsTag id = " + postsTag.getId());
        }

        // Tag정보를 가져올때 N+1문제가 발생하는 모습!!
        System.out.println("============= Tag 가져오기 =============");
        for (PostsTag postsTag : findPost.getPostsTags()) {
            System.out.println("Tag Name = " + postsTag.getTag().getName());
        }
    }

    @Test
    @DisplayName("N+1 문제 해결한 테스트 케이스")
    public void jpaProblemSolve() {

        // 영속성 컨텍스트 초기화
        em.flush();
        em.clear();

        System.out.println("============= 쿼리 시작 =============");
        Posts findPost = postsRepository.findPost_useFetchJoin(POST_ID);

        System.out.println("============= PostsTag 가져오기 =============");
        for (PostsTag postsTag : findPost.getPostsTags()) {
            System.out.println("PostsTag id = " + postsTag.getId());
        }

        System.out.println("============= Tag 가져오기 =============");
        for (PostsTag postsTag : findPost.getPostsTags()) {
            System.out.println("Tag Name = " + postsTag.getTag().getName());
        }
    }

}
