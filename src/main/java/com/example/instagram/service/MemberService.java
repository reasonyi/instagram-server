package com.example.instagram.service;

import com.example.instagram.authority.JwtTokenProvider;
import com.example.instagram.authority.TokenInfo;
import com.example.instagram.dto.request.LoginRequestDto;
import com.example.instagram.dto.request.SignUpRequestDto;
import com.example.instagram.entity.Member;
import com.example.instagram.entity.MemberRole;
import com.example.instagram.entity.Role;
import com.example.instagram.repository.MemberRepository;
import com.example.instagram.repository.MemberRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    public String signUp(SignUpRequestDto signupRequestDto) {
        // id 중복 검사
        if(memberRepository.existsByLoginId(signupRequestDto.getLoginId())){
            return "이미 등록된 ID 입니다.";
        }

        Member member = Member.builder()
                .loginId(signupRequestDto.getLoginId())
                .password(signupRequestDto.getPassword())
                .name(signupRequestDto.getName())
                .nickname(signupRequestDto.getNickname())
                .build();

        memberRepository.save(member);

        MemberRole memberRole = new MemberRole(null, Role.MEMBER, member);
        memberRoleRepository.save(memberRole);

        return "회원가입이 완료되었습니다.";
    }

    // 로그인 -> 토큰 발행
    public TokenInfo login(LoginRequestDto loginRequestDto) {
        // UsernamePasswordAuthenticationToken 토큰 발행 (id, password 정보를 가지고 만듦)
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequestDto.getLoginId(), loginRequestDto.getPassword());
        // ManagerBuilder에 토큰을 전달하여 DB 멤버 정보와 비교함
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        return jwtTokenProvider.createToken(authentication);    // 문제가 없을 시 토큰을 발행하여 사용자에게 전달
    }
}
