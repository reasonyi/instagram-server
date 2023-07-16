package com.example.instagram.dto.request;

import com.sun.istack.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Data
@Builder
public class SignUpRequestDto {
    @NotNull
    private String loginId;
    @NotNull
    private String password;
    @NotNull
    private String nickname;
    @NotNull
    private String name;
}
