package com.example.jpalazyeager.member;


import com.example.jpalazyeager.show.Show;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;

    // FetchType.EAGER과 LAZY를 바꿔가면서 쿼리 비교해보기
    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Show> showList = new ArrayList<>();

    @Builder
    public Member(Long id, String name, String password){
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public void addShowList(Show show){
        this.showList.add(show);
    }

}

