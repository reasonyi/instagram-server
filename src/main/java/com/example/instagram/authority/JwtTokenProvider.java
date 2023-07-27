package com.example.instagram.authority;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {
    private final long EXPIRATION_MILLISECONDS = 1000 * 60 * 30;   // 30분

    private final Key key;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // Token 생성
    public TokenInfo createToken(Authentication authentication) {
        // 권한들을 , 기준으로 분류하여 stream으로 가져옴
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date now = new Date();

        Date accessTokenExpiresIn = new Date(now.getTime() + EXPIRATION_MILLISECONDS);

        // Access Token
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)       // 권한들을 auth라는 이름으로 담아준다.
                .setIssuedAt(now)                       // 현재 발행 시간
                .setExpiration(accessTokenExpiresIn)    // 언제까지 유효한지를 나타냄
                .signWith(key, SignatureAlgorithm.HS256) // HS256 알고리즘을 사용한 key
                .compact();

        return TokenInfo.builder()  // TokenInfo에 담아서 돌려줌
                .grantType("Bearer")
                .accessToken(accessToken)
                .build();
    }
    
    // Token 정보 추출
    public Authentication getAuthentication(String accessToken) {
        Claims claims = getClaims(accessToken); // Claim을 가져온다

        if(claims.get("auth") == null)          // 전에 Claim 안에 넣었던 auth를 가져올 수 있다. 만약 없으면 잘못된 토큰
            throw new RuntimeException("잘못된 토큰입니다.");

        // 권한 정보 추출
        // ,기준으로 다시 잘라서 Collection에 담아준다.
        Collection<GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // Token 검증
    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;    // 문제가 없으면 true return
        }   // Exception이 있을 경우 각 Exception별로 처리함
        catch (SecurityException | MalformedJwtException e) {}
        catch (ExpiredJwtException e) {}
        catch (UnsupportedJwtException e) {}
        catch (IllegalArgumentException e) {}
        return false;
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
