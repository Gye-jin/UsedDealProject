package com.spring.deal.config.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.spring.deal.entity.User;

/**
 * 모든 주소에서 동작함. (토큰 검증)
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

	@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // log.debug("디버그 : JwtAuthorizationFilter doFilterInternal()");
		
        // 1. 헤더검증 후 헤더가 있다면 토큰 검증 후 임시 세션 생성
        if (isHeaderVerify(request, response)) {
            log.debug("디버그 : Jwt 토큰 검증 성공");
            // 토큰 파싱하기 (Bearer 없애기)
            String token = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");
            String userId = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token)
  					.getClaim("userId").asString();
  			String role = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token)
  					.getClaim("level").asString();       
            // 토큰 검증
            User user = JwtProcess.verify(token);

            // 임시 세션 생성
            Authentication authentication = new UsernamePasswordAuthenticationToken(user,
                    null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            request.setAttribute("userId", userId);
            request.setAttribute("role", role);
        }
        
        

        // 2. 세션이 있는 경우와 없는 경우로 나뉘어서 컨트롤러로 진입함
        chain.doFilter(request, response);
    }

    private boolean isHeaderVerify(HttpServletRequest request, HttpServletResponse response) {
        String header = request.getHeader(JwtProperties.HEADER_STRING);
        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            return false;
        } else {
            return true;
        }
    }
}