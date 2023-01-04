package com.example.jpalazyeager.show;

import com.example.jpalazyeager.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Show(Long id, String title, String content){
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public void addMember(Member member){
        this.member = member;
    }


}
