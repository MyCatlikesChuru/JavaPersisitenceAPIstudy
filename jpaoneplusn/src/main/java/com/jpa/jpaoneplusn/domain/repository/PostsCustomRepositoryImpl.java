package com.jpa.jpaoneplusn.domain.repository;

import com.jpa.jpaoneplusn.domain.entity.Posts;
import com.jpa.jpaoneplusn.domain.entity.QPosts;
import com.jpa.jpaoneplusn.domain.entity.QPostsTag;
import com.jpa.jpaoneplusn.domain.entity.QTag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.jpa.jpaoneplusn.domain.entity.QPosts.posts;
import static com.jpa.jpaoneplusn.domain.entity.QPostsTag.*;
import static com.jpa.jpaoneplusn.domain.entity.QTag.*;

@Repository
@RequiredArgsConstructor
public class PostsCustomRepositoryImpl implements PostsCustomRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Posts> findAll_noFetchJoin() {
        return queryFactory
                .selectFrom(posts)
                .leftJoin(posts.postsTags, postsTag)
                .leftJoin(postsTag.tag, tag)
                .where(posts.id.eq(postsTag.id))
                .fetch();
    }

    @Override
    public List<Posts> findAll_useFetchJoin() {
        return queryFactory
                .selectFrom(posts)
                .leftJoin(posts.postsTags, postsTag).fetchJoin()
                .leftJoin(postsTag.tag, tag).fetchJoin()
                .where(posts.id.eq(postsTag.id))
                .fetch();
    }
}
