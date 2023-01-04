//package com.example.jpalazyeager.show;
//
//import com.example.jpalazyeager.member.Member;
//import com.example.jpalazyeager.member.MemberRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class ShowService {
//
//    private final ShowRepository showRepository;
//
//    public void createShow(){
//        Show show = Show.builder()
//                .title("제목1")
//                .content("내용1")
//                .build();
//
//        show.addMember(new Member(1L,"이재혁","1234"));
//
//        showRepository.save(show);
//    }
//}
