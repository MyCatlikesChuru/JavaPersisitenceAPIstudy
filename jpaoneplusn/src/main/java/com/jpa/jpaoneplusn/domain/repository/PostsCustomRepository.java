package com.jpa.jpaoneplusn.domain.repository;

import com.jpa.jpaoneplusn.domain.entity.Posts;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.jpa.jpaoneplusn.domain.entity.QPosts.posts;

public interface PostsCustomRepository {

    Posts findPost_noFetchJoin(Long id);

    Posts findPost_useFetchJoin(Long id);

}
