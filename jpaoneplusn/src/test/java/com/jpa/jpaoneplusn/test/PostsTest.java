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


    @BeforeEach
    public void setUp() {
        queryFactory = new JPAQueryFactory(em);

        for (long i = 1; i <= 4; i++) {
            Posts post = Posts.builder().title("제목 "+i).build();
            Tag tag = Tag.builder().name("태그 "+i).type("타입 "+i).build();
            PostsTag postTag = PostsTag.builder().tag(tag).posts(post).build();
            post.addPostsTag(postTag);
            tagRepository.save(tag);
            postsRepository.save(post);
//            em.persist(tag);
//            em.persist(post);
        }
    }

    @Test
    @DisplayName("N+1 문제 발생하는 테스트 케이스")
    public void jpaProblem() {

        // 영속성 컨텍스트 초기화
        em.flush();
        em.clear();

        System.out.println("============= 쿼리 시작 =============");
        List<Posts> findPosts = postsRepository.findAll_noFetchJoin();

        System.out.println("============= PostsTag 가져오기 =============");
        List<PostsTag> findPostsTag = findPosts.get(0).getPostsTags();
        System.out.println("PostsTag id = " + findPostsTag.get(0).getId());

        System.out.println("============= Tag 가져오기 =============");
        Tag tag = findPostsTag.get(0).getTag();
        System.out.println("Tag Name = " + tag.getName());
    }

    @Test
    @DisplayName("N+1 문제 해결한 테스트 케이스")
    public void jpaProblemSolve() {

        // 영속성 컨텍스트 초기화
        em.flush();
        em.clear();

        System.out.println("============= 쿼리 시작 =============");
        List<Posts> findPosts = postsRepository.findAll_useFetchJoin();

        System.out.println("============= PostsTag 가져오기 =============");
        List<PostsTag> findPostsTag = findPosts.get(0).getPostsTags();
        System.out.println("PostsTag id = " + findPostsTag.get(0).getId());

        System.out.println("============= Tag 가져오기 =============");
        Tag tag = findPostsTag.get(0).getTag();
        System.out.println("Tag Name = " + tag.getName());
    }

}
