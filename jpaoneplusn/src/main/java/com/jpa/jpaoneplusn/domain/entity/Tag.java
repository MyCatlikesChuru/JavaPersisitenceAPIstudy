package com.jpa.jpaoneplusn.domain.entity;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<PostsTag> postsTags = new ArrayList<>();

    @Builder
    public Tag(Long id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public void addPostsTag(PostsTag postsTag) {
        this.postsTags.add(postsTag);
    }
}
