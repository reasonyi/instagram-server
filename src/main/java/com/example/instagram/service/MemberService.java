package com.example.instagram.service;

import com.example.instagram.dto.request.SignUpRequestDto;
import com.example.instagram.entity.Member;
import com.example.instagram.entity.MemberRole;
import com.example.instagram.entity.Role;
import com.example.instagram.repository.MemberRepository;
import com.example.instagram.repository.MemberRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberRoleRepository memberRoleRepository;

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
}
