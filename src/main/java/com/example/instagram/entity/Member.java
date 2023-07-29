package com.example.instagram.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String loginId;
    private String password;
    private String nickname;
    private String name;

    // 로그인 시에 받을 필요 없음. 프로필 수정 시에만 변경 또는 추가할 수 있도록 할 것
    @Enumerated(EnumType.STRING)
    private Gender gender;      // 성별

    private String content;     // 자기소개...?
    private String profileImg;  // 프로필 이미지

    @OneToMany(fetch=FetchType.EAGER, mappedBy = "member")
    private List<MemberRole> roles = null;
}