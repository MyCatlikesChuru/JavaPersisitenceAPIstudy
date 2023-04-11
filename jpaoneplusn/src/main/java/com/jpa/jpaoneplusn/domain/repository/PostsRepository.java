package com.jpa.jpaoneplusn.domain.repository;

import com.jpa.jpaoneplusn.domain.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long>, PostsCustomRepository {
}
