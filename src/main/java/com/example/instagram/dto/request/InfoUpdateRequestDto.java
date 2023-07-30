package com.example.instagram.dto.request;

import com.example.instagram.entity.Gender;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class InfoUpdateRequestDto {
    @NotNull
    private String nickname;
    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;      // 성별

    private String content;     // 자기소개...?
    private String profileImg;  // 프로필 이미지

}
