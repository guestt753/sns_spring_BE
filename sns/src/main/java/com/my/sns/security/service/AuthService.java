package com.my.sns.security.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.my.sns.config.JwtProvider;
import com.my.sns.friend.FriendService;
import com.my.sns.security.controller.dto.JwtResponse;
import com.my.sns.security.controller.dto.TokenDTO;
import com.my.sns.security.controller.dto.TokenRequestDTO;
import com.my.sns.security.entity.CustomUserDetails;
import com.my.sns.user.UserRoleVO;
import com.my.sns.user.UserService;
import com.my.sns.user.UserVO;
import com.my.sns.user.impl.UserDAO;

@Service
public class AuthService {
	@Autowired
	UserService userService;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	UserVO user;
	@Autowired
	UserDAO userDAO;
	@Autowired
	UserRoleVO role;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	CustomUserDetails customUserDetails;
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	JwtResponse jwtResponse = new JwtResponse();
	TokenDTO tokenDTO = new TokenDTO();
	
	@Transactional
	public JwtResponse login(String objJson) {
		System.out.println("::::::::Login:::::::::");
		Gson gson = new Gson();
		try {
			
			System.out.println("josn : " + objJson);
			user = gson.fromJson(objJson, UserVO.class);
//			user = userService.getUser(user);
//			JSONObject obj = new JSONObject();
//			System.out.println(user.getUserName() + " " + user.getUserPassword());  현재 gson으로 데어터를 보냈는데 데이터가 제대로 안받아지는듯 값이 없다고 뜸 nullpointer exception
			
		} catch (Exception e) {
			 e.printStackTrace();
			 System.out.println("json 변환오류");
		}

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(user.getUserId(), user.getUserPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String acessToken = jwtProvider.generateJwtToken(authentication);
		String refreshToken = jwtProvider.generateJwtToken();
		
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();		
		List<String> roles = customUserDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		tokenDTO.setAccessToken(acessToken);
		tokenDTO.setAccessTokenExpiresIn(jwtProvider.getExpireTime(acessToken));
		tokenDTO.setRefreshToken(refreshToken);
		tokenDTO.setRefreshTokenExpireIn(jwtProvider.getExpireTime(refreshToken));
		jwtResponse.setTokenDTO(tokenDTO);
		jwtResponse.setUserNo(customUserDetails.getUserNo());
		jwtResponse.setUserRole(roles);
		
		if(userDAO.insertRefreshToken(refreshToken, customUserDetails.getUserNo()) == 0) {
			throw new RuntimeException("DB에 토큰정보가 저장되지 않았습니다.");
		}
		
		System.out.println(acessToken);

		return jwtResponse;
	}
	
//	public int join(String objJson) {
//	System.out.println("::::::::Join:::::::::"); 
//	int result = 0;
//	Gson gson = new Gson();
//	try {
//		String decodeStr = URLDecoder.decode(objJson, "UTF-8");
//		UserVO user = gson.fromJson(decodeStr, UserVO.class);
//		userService.insertUser(user);
//		result = 1;
//	}catch (Exception e) {
//		e.printStackTrace();
//		System.out.println("가입 실패");
//	}
//	return result;
//}
	
	@Transactional
	public TokenDTO reissue(TokenRequestDTO tokenRequestDTO) {
		// 안드로이드에서 acess token 유효기간이 얼마 남지않았을대 요청하게됨
		// refresh token 검증
		if(!jwtProvider.validateJwtToken(tokenRequestDTO.getRefreshToken())) {
			throw new RuntimeException("Refresh Token이 유효하지 않습니다.");
		}
		
		String username = jwtProvider.getUserNameFromJwtToken(tokenRequestDTO.getAccessToken());
		CustomUserDetails userDetails = (CustomUserDetails)customUserDetailsService.loadUserByUsername(username);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		Long userNo = jwtProvider.getUserNoFromJwtToken(tokenRequestDTO.getAccessToken());
		
		// userNo을 통해 DB에서 refresh token 값 가져옴
		TokenDTO tokenDTO = userDAO.getRefreshTokenByuserNo(userNo);
		if(tokenDTO == null)
			throw new RuntimeException("로그아웃된 사용자 압니다.");
		
		// refresh token 일치하는지 검사
		if(!tokenDTO.getRefreshToken().equals(tokenRequestDTO.getRefreshToken())) {
			throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
		}
		
		// 새로운 토큰 생성
		if(tokenRequestDTO.getType() == 1) { //refresh token 도 갱신해야 할 경우
			String refreshToken = jwtProvider.generateJwtToken();
			tokenDTO.setRefreshToken(refreshToken);
			tokenDTO.setRefreshTokenExpireIn(jwtProvider.getExpireTime(refreshToken));
			// 저장소 refresh token 정보 업데이트(refresh token 재발급 시)
			if(userDAO.updateRefreshToken(refreshToken, userNo) == 0) {
				throw new RuntimeException("DB에 토큰정보가 갱신되지 않았습니다.");
			}
			
		}
		String acessToken = jwtProvider.generateJwtToken(authentication); 
		tokenDTO.setAccessToken(acessToken);
		tokenDTO.setAccessTokenExpiresIn(jwtProvider.getExpireTime(acessToken));
		
		// return
		return tokenDTO;
	}

}
