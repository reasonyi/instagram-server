package com.example.instagram.controller;

import com.example.instagram.authority.TokenInfo;
import com.example.instagram.dto.request.LoginRequestDto;
import com.example.instagram.dto.request.SignUpRequestDto;
import com.example.instagram.dto.response.MemberResponseDto;
import com.example.instagram.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping(value = "/signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        return ResponseEntity.ok().body(memberService.signUp(signUpRequestDto));
    }

    // 로그인
    @PostMapping(value = "/login")
    public ResponseEntity<TokenInfo> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok().body(memberService.login(loginRequestDto));
    }

    @GetMapping(value = "/info/{loginId}")
    public ResponseEntity<MemberResponseDto> searchMyInfo(@PathVariable String loginId) {
        return ResponseEntity.ok().body(memberService.serchMyInfo(loginId));
    }
}
