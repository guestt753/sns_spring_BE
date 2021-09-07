package com.my.sns.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.my.sns.common.Reissue;
import com.my.sns.security.controller.dto.TokenRequestDTO;
import com.my.sns.security.entity.CustomUserDetails;
import com.my.sns.security.service.CustomUserDetailsService;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	private JwtProvider jwtProvider;
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	@Autowired
	private Reissue reissue;
	
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)throws ServletException, IOException {
		try {
			TokenRequestDTO tokenRequestDTO = new TokenRequestDTO();
			String accessToken = null;
			String refreshToken = null;
			try {
				tokenRequestDTO = getToken(request);
				accessToken = tokenRequestDTO.getAccessToken();
				refreshToken = tokenRequestDTO.getRefreshToken();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("요청으로 전달받은 토큰 값이 없음");
			}
			System.out.println("요청으로 전달받은 토큰 값(access) : " + accessToken);
			System.out.println("요청으로 전달받은 토큰 값 (refresh): " + refreshToken);
			
			accessToken = reissue.reissue(accessToken, refreshToken);
			if (accessToken != null && jwtProvider.validateJwtToken(accessToken) && jwtProvider.validateJwtToken(refreshToken)) {
				String userName = jwtProvider.getUserNameFromJwtToken(accessToken);
				Long userNo = jwtProvider.getUserNoFromJwtToken(accessToken);
				
				CustomUserDetails userDetails = (CustomUserDetails)customUserDetailsService.loadUserByUserNo(userNo,userName);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
				logger.error("Cannot set user authentication: {}", e);
		  }
			filterChain.doFilter(request, response);
	}
	
	private TokenRequestDTO getToken(HttpServletRequest request){
		TokenRequestDTO tokenRequestDTO = new TokenRequestDTO();
		String headerAuth = request.getHeader("Authorization");
		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			String tokenString = headerAuth.substring(7, headerAuth.length());
			String[] splitString = tokenString.split(",");
			String accessToken = splitString[0];
			String refreshToken = splitString[1].substring(8, splitString[1].length());
			
			tokenRequestDTO.setAccessToken(accessToken);
			tokenRequestDTO.setRefreshToken(refreshToken);
			return tokenRequestDTO;
		}
		return null;
	} 
}


