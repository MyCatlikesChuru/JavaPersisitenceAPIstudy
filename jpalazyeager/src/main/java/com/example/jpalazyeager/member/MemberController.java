package com.example.jpalazyeager.member;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity getSomething(){

        memberService.createMember();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity findMember(){

        memberService.findMember();

        return new ResponseEntity<>(HttpStatus.OK);
    }



}
