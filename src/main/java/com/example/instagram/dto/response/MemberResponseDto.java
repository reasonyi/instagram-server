package com.example.instagram.dto.response;

import com.example.instagram.entity.Gender;
import com.sun.istack.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Data
@Builder
public class MemberResponseDto {
    @NotNull
    private String loginId;
    @NotNull
    private String nickname;
    @NotNull
    private String name;

    private String gender;
    private String content;
    private String profileImg;

}
