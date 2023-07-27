package com.example.instagram.authority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@AllArgsConstructor
@Data
@NoArgsConstructor
public class TokenInfo {

    private String grantType;
    private String accessToken;

}
