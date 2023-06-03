package com.jpa.jpaoneplusn.test;

import com.jpa.jpaoneplusn.domain.entity.Posts;
import com.jpa.jpaoneplusn.domain.entity.PostsTag;
import com.jpa.jpaoneplusn.domain.entity.QPosts;
import com.jpa.jpaoneplusn.domain.entity.Tag;
import com.jpa.jpaoneplusn.domain.repository.PostsRepository;
import com.jpa.jpaoneplusn.domain.repository.TagRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.jpa.jpaoneplusn.domain.entity.QPosts.posts;
import static com.jpa.jpaoneplusn.domain.entity.QPostsTag.postsTag;
import static com.jpa.jpaoneplusn.domain.entity.QTag.tag;

@SpringBootTest
@Transactional
public class PostsTest {

    @Autowired
    EntityManager em;
    JPAQueryFactory queryFactory;
    @Autowired
    PostsRepository postsRepository;
    @Autowired
    TagRepository tagRepository;



    // 2개의 포스트를 조회할 경우
    // Lazy ->
    // Eager ->
    @BeforeEach
    public void setUp() {
        queryFactory = new JPAQueryFactory(em);
        Posts post1 = Posts.builder().title("계절별 강수량 조사 게시글 입니다.").build();
        Posts post2 = Posts.builder().title("분기별 강수량 조사 게시글 입니다.").build();
        postsRepository.save(post1);
        postsRepository.save(post2);

        // 2개의 게시글, 4개의 태그
        // 중간테이블 postTag = 1개의 게시글에 각각 태그를 연결해놓은 상황 , -> 총 8개의 행
        for (long i = 1; i <= 4; i++) {
            String[] seasons = new String[]{"봄","여름","가을","겨울"};
            String[] quarters = new String[]{"1분기","2분기","3분기","4분기"};
            String season = seasons[(int)(i - 1L)];
            String quarter = quarters[(int)(i - 1L)];
            Tag tag1 = Tag.builder().name(season).type("계절").build();
            Tag tag2 = Tag.builder().name(quarter).type("분기").build();
            PostsTag postTag1 = PostsTag.builder().tag(tag1).posts(post1).build();
            PostsTag postTag2 = PostsTag.builder().tag(tag2).posts(post2).build();
            tag1.addPostsTag(postTag1);
            tag2.addPostsTag(postTag2);
            tagRepository.save(tag1);
            tagRepository.save(tag2);
        }
    }

    @Test
    @DisplayName("N+1 문제 발생하는 테스트 케이스")
    public void jpaProblem() {

        // 영속성 컨텍스트 초기화
        em.flush();
        em.clear();

        System.out.println("============= 쿼리 시작 =============");
        List<Posts> findPosts = postsRepository.findAllPost_noFetchJoin();


        System.out.println("============= PostsTag 가져오기 =============");

        for (Posts findPost : findPosts) {
            for (PostsTag postsTag : findPost.getPostsTags()) {
                System.out.println("PostsTag id = " + postsTag.getId());
            }
        }
        // Tag정보를 가져올때 N+1문제가 발생하는 모습!!
        System.out.println("============= Tag 가져오기 =============");
        for (Posts findPost : findPosts) {
            for (PostsTag postsTag : findPost.getPostsTags()) {
                System.out.println("Tag Name = " + postsTag.getTag().getName());
            }
        }
    }

    @Test
    @DisplayName("N+1 문제 해결한 테스트 케이스")
    public void jpaProblemSolve() {

        // 영속성 컨텍스트 초기화
        em.flush();
        em.clear();

        System.out.println("============= 쿼리 시작 =============");
        List<Posts> findPosts = postsRepository.findAllPost_useFetchJoin();

        System.out.println("============= PostsTag 가져오기 =============");
        for (Posts findPost : findPosts) {
            for (PostsTag postsTag : findPost.getPostsTags()) {
                System.out.println("PostsTag id = " + postsTag.getId());
            }
        }

        System.out.println("============= Tag 가져오기 =============");
        for (Posts findPost : findPosts) {
            for (PostsTag postsTag : findPost.getPostsTags()) {
                System.out.println("Tag Name = " + postsTag.getTag().getName());
            }
        }
    }

}
