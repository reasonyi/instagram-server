package com.example.instagram.dto.request;

import com.sun.istack.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Data
@Builder
public class LoginRequestDto {
    @NotNull
    private String loginId;
    @NotNull
    private String password;
}
