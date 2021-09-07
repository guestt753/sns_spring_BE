package com.my.sns.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.my.sns.config.JwtProvider;
import com.my.sns.security.controller.dto.TokenDTO;
import com.my.sns.security.controller.dto.TokenRequestDTO;
import com.my.sns.security.entity.CustomUserDetails;
import com.my.sns.security.service.CustomUserDetailsService;
import com.my.sns.user.impl.UserDAO;

@Service
public class Reissue {
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	CustomUserDetails customUserDetails;
	@Autowired
	CustomUserDetailsService customUserDetailsService;
	@Autowired
	UserDAO userDAO;
	
	/* 전달받은 토큰들 중에 만료가 되었으면
	 * 만료 된 것만 재발급 해 줌
	 *  */
	public String reissue(String accessToken, String refreshToken) {
		System.out.println("::::::::Reissue:::::::::");
		TokenDTO tokenDTO = new TokenDTO();
		String accessTokenSecretKey;
		String refreshTokenSecretKey;
		
		if(accessToken == null) return null;
		
		// refresh token 검증
		System.out.println("===refresh 토큰 검증  ===");
		if(!jwtProvider.validateJwtToken(refreshToken)) {
			System.out.println("Refresh Token이 유효하지 않습니다.");
//			throw new RuntimeException("Refresh Token이 유효하지 않습니다.");
			return null;
		}
		
		String username = jwtProvider.getUserNameFromJwtToken(accessToken);
		CustomUserDetails customUserDetails = (CustomUserDetails)customUserDetailsService.loadUserByUsername(username);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
		Long userNo = jwtProvider.getUserNoFromJwtToken(accessToken);
		
		// userNo을 통해 DB에서 refresh token 값 가져옴
		System.out.println("===로그아웃 상태 검사 ===");
		tokenDTO = userDAO.getTokenByUserNo(userNo);
		if(tokenDTO == null)
			throw new RuntimeException("로그아웃된 사용자 압니다.");
		
		// refresh token 일치하는지 검사
		System.out.println("===refresh 정보 일치 검사 ===");
		if(!tokenDTO.getRefreshToken().equals(refreshToken)) {
			throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
		}
		
		long remainRefreshTime = jwtProvider.getExpireTime(refreshToken) - System.currentTimeMillis();
		long remainAccessTime = jwtProvider.getExpireTime(accessToken) - System.currentTimeMillis();
		if (remainAccessTime <= 6000*3) { //access or (access and refresh) 재발급
			if(remainRefreshTime <= 6000*3) { // access, refresh 재발
				// refresh 재발급
				refreshToken = jwtProvider.generateRefreshJwtToken(authentication);
				refreshTokenSecretKey = jwtProvider.getKeytoString();
				// 저장소 refresh token 정보 업데이트
				if(userDAO.updateRefreshToken(refreshToken, userNo, refreshTokenSecretKey) == 0) 
					throw new RuntimeException("DB에 refresh 토큰정보가 갱신되지 않았습니다.");
				// access 재발급
				accessToken = jwtProvider.generateAccessJwtToken(authentication);
				accessTokenSecretKey = jwtProvider.getKeytoString();
				if(userDAO.updateAccessToken(accessToken ,userNo, accessTokenSecretKey) == 0) 
					throw new RuntimeException("DB에 access 토큰정보가 저장되지 않았습니다.");
				return accessToken;
			} else { //access만 재밣급
				accessToken = jwtProvider.generateAccessJwtToken(authentication);
				accessTokenSecretKey = jwtProvider.getKeytoString();
				// 저장소 access token 정보 업데이트
				if((userDAO.updateAccessToken(accessToken ,userNo, accessTokenSecretKey)) == 0) 
					throw new RuntimeException("DB에 access 토큰정보가 저장되지 않았습니다.");
				return accessToken;
			}
		
		} else if(remainRefreshTime <= 6000*3) { //refresh token 만 재발급..(만료되면 재발급 x)
			refreshToken = jwtProvider.generateRefreshJwtToken(authentication);
			refreshTokenSecretKey = jwtProvider.getKeytoString();
			// 저장소 refresh token 정보 업데이트
			if(userDAO.updateRefreshToken(refreshToken, userNo, refreshTokenSecretKey) == 0) 
				throw new RuntimeException("DB에 refresh 토큰정보가 갱신되지 않았습니다.");
			return accessToken;
		} else { // 둘다 재발급 필요x
			System.out.println("토큰들이 만료되지 않아 재발급이 되지 않았습니다.");
			return accessToken;
		} 
	}
	
	public TokenDTO setTokenObject( ) {
		System.out.println("::::::::setTokenObject:::::::::");
		String accessToken = null;
		String refreshToken = null;
		TokenDTO tokenDTO = new TokenDTO();
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof CustomUserDetails) {
			accessToken = ((CustomUserDetails)principal).getAccessToken();
			refreshToken = ((CustomUserDetails)principal).getRefreshToken();
		}
		
		// refresh token 검증
		if(!jwtProvider.validateJwtToken(refreshToken)) {
//			throw new RuntimeException("Refresh Token이 유효하지 않습니다.");
			// 실패코드로 응답하고 재로그인하도록 함
			tokenDTO.setCode(5100); //인증 실패
			return tokenDTO;
		}
		
		tokenDTO.setCode(4700);
		tokenDTO.setAccessToken(accessToken);
		tokenDTO.setRefreshToken(refreshToken);
		
		return tokenDTO;
	}

}
