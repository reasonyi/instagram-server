package com.example.instagram.controller;

import com.example.instagram.dto.request.SignUpRequestDto;
import com.example.instagram.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping(value = "/signup")
    public ResponseEntity signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        return ResponseEntity.ok().body(memberService.signUp(signUpRequestDto));
    }
    
}
