package com.example.jpalazyeager.member;

import com.example.jpalazyeager.show.Show;
import com.example.jpalazyeager.show.ShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ShowRepository showRepository;

    public void createMember(){
        Member member = Member.builder()
                .name("이재혁")
                .password("1234")
                .build();

        Show show = Show.builder()
                .title("제목1")
                .content("내용1")
                .build();

        show.addMember(member);
        member.addShowList(show); // casecade 전이 실험

        System.out.println("=========== save 쿼리 시작 ============");
        Member saveMember = memberRepository.save(member);
        System.out.println("=========== save 쿼리 끝 ============");
    }

    public void findMember(){

        System.out.println("=========== findMember 쿼리 시작 ============");
        Optional<Member> memberId = memberRepository.findById(1L);
        System.out.println("=========== findMember 쿼리 끝 ============");

        System.out.println("=========== findMember 영속성 컨텍스트 확인 ============");
        memberRepository.findById(1L);
        System.out.println("=========== findMember 영속성 컨텍스트 끝 ============");


        System.out.println("=========== findShow 쿼리 시작 ============");
        Optional<Show> showId = showRepository.findById(1L);
        System.out.println("=========== findShow 쿼리 끝 ============");


    }
}
