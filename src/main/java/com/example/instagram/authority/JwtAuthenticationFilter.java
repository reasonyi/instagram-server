package com.example.instagram.authority;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Request Header 에서 JWT 토큰 추출
        String token = resolveToken((HttpServletRequest) request);

        // validateToken 으로 토큰 유효성 검사
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // 토큰이 유효할 경우 정보를 가져온다.
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);   // 가져온 정보를 SecurityContextHolder에 기록
        }
        chain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");    // 헤더에서 Authorization으로 된 문자열을 가져온다.
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {  // Bearer에 맞는지를 찾는다.
            return bearerToken.substring(7);                                // 맞다면 key값만 가져온다.
        }
        return null;
    }
}
