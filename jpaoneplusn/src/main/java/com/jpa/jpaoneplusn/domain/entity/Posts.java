package com.jpa.jpaoneplusn.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @OneToMany(mappedBy = "posts", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER) // LAZY, EAGER 변경후 쿼리 시점변경
    private List<PostsTag> postsTags = new ArrayList<>();

    @Builder
    public Posts(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public void addPostsTag(PostsTag postsTag) {
        this.postsTags.add(postsTag);
    }
}
