package com.my.sns.security.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.my.sns.security.controller.dto.RequestResponse;
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
	
	JwtResponse jwtResponse = new JwtResponse();
	TokenDTO tokenDTO = new TokenDTO();
	RequestResponse requestResponse = new RequestResponse();
	int code;
	
	@Transactional
	public JwtResponse login(String objJson, String fcmToken) {
		System.out.println("::::::::Login:::::::::");
		String accessTokenSecretKey;
		String refreshTokenSecretKey;
		Gson gson = new Gson();
		try {
			
			System.out.println("josn : " + objJson);
<<<<<<< HEAD
			//gson -> 자바 객체
=======
			System.out.println("fcmToken : " + fcmToken);
>>>>>>> b5ca04ae10957658703fcdcbce9b799bac999086
			user = gson.fromJson(objJson, UserVO.class);			
		} catch (Exception e) {
			 e.printStackTrace();
			 System.out.println("json 변환오류");
		}

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(user.getUserId(), user.getUserPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String accessToken = jwtProvider.generateAccessJwtToken(authentication);
		accessTokenSecretKey = jwtProvider.getKeytoString();
		String refreshToken = jwtProvider.generateRefreshJwtToken(authentication);
		refreshTokenSecretKey = jwtProvider.getKeytoString();
	
		
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();	
		Long userNo = customUserDetails.getUserNo();
		String userName = customUserDetails.getUserNickName();
		String userImageUrl = customUserDetails.getUserImageUrl();
		String userIntroduction = customUserDetails.getUserIntroduction();
		
		List<String> roles = customUserDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		if(userService.isIssuedToken(userNo))
			throw new RuntimeException("DB에 토큰정보가 이미 있습니다.");
		else if(userDAO.insertToken(accessToken, accessTokenSecretKey, refreshToken, refreshTokenSecretKey, userNo, fcmToken) == 0) {
			throw new RuntimeException("DB에 토큰정보가 저장되지 않았습니다.");
		}
		
		tokenDTO.setAccessToken(accessToken);
		tokenDTO.setRefreshToken(refreshToken);
		jwtResponse.setTokenDTO(tokenDTO);
		jwtResponse.setUserNo(userNo);
		jwtResponse.setUserRole(roles);
		jwtResponse.setUserName(userName);
		jwtResponse.setUserImageUrl(userImageUrl);
		jwtResponse.setUserIntroduction(userIntroduction);

		System.out.println("불러온 내 이름 : " + userName);
		return jwtResponse;
	}
	
	public RequestResponse join(String objJson) {
	System.out.println("::::::::Join:::::::::"); 
//	UserVO user = new UserVO();
	boolean result = false;
	Gson gson = new Gson();
	try {
//		String decodeStr = URLDecoder.decode(objJson, "UTF-8");
		user = gson.fromJson(objJson, UserVO.class);
	}catch (Exception e) {
		e.printStackTrace();
		System.out.println("가입 실패");
	}
	result = userService.insertUser(user, false);
	
	if(result) {
		code = 4700;
		requestResponse.setCode(code);
		return requestResponse;
	}else {
		code = 5100;
		requestResponse.setCode(code);
		return requestResponse;
	}
}
	
//	@Transactional
//	public TokenRequestDTO reissue(String objJson) {
//		System.out.println("::::::::Reissue:::::::::");
//		String accessTokenSecretKey;
//		String refreshTokenSecretKey;
//		Gson gson = new Gson();
//		TokenRequestDTO tokenRequestDTO = new TokenRequestDTO();
//		try {
//			
//			System.out.println("josn : " + objJson);
//			tokenRequestDTO = gson.fromJson(objJson, tokenRequestDTO.getClass());
//		} catch (Exception e) {
//			 e.printStackTrace();
//			 System.out.println("json 변환오류");
//		}
//		// 안드로이드에서 acess token 유효기간이 얼마 남지않았을대 요청하게됨
//		// refresh token 검증
//		System.out.println("===refresh 토큰 검증  ===");
//		if(!jwtProvider.validateJwtToken(tokenRequestDTO.getRefreshToken())) {
//			throw new RuntimeException("Refresh Token이 유효하지 않습니다.");
//		}
//		
//		String username = jwtProvider.getUserNameFromJwtToken(tokenRequestDTO.getAccessToken());
//		CustomUserDetails customUserDetails = (CustomUserDetails)customUserDetailsService.loadUserByUsername(username);
//		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
//		Long userNo = jwtProvider.getUserNoFromJwtToken(tokenRequestDTO.getAccessToken());
//		
//		// userNo을 통해 DB에서 refresh token 값 가져옴
//		System.out.println("===로그아웃 상태 검사 ===");
//		TokenDTO tokenDTO = userDAO.getTokenByUserNo(userNo);
//		if(tokenDTO == null)
//			throw new RuntimeException("로그아웃된 사용자 압니다.");
//		
//		// refresh token 일치하는지 검사
//		System.out.println("===refresh 정보 일치 검사 ===");
//		if(!tokenDTO.getRefreshToken().equals(tokenRequestDTO.getRefreshToken())) {
//			throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
//		}
//		
//		// 새로운 토큰 생성
//		if(tokenRequestDTO.getType() == 1) { //refresh token 도 갱신해야 할 경우
//			String refreshToken = jwtProvider.generateRefreshJwtToken(authentication);
//			refreshTokenSecretKey = jwtProvider.getKeytoString();
//			tokenDTO.setRefreshToken(refreshToken);
//			tokenDTO.setRefreshTokenExpireIn(jwtProvider.getExpireTime(refreshToken));
//			// 저장소 refresh token 정보 업데이트(refresh token 재발급 시)
//			if(userDAO.updateRefreshToken(refreshToken, userNo, refreshTokenSecretKey) == 0) {
//				tokenDTO.setCode(5100); //DB에 토큰정보가 갱신되지 않았습니다.
//				tokenRequestDTO.setTokenDTO(tokenDTO);
////				throw new RuntimeException("DB에 토큰정보가 갱신되지 않았습니다.");
//			}
//			tokenRequestDTO.setType(1);
//		}
//		
//		String accessToken = jwtProvider.generateAccessJwtToken(authentication);
//		accessTokenSecretKey = jwtProvider.getKeytoString();
//		if(userDAO.updateAccessToken(accessTokenSecretKey, userNo) == 0) {
//			if(tokenDTO.getCode() == 5100) {
//				System.out.println("=== code : " + tokenDTO.getCode() + " ===");
//				return tokenRequestDTO;
//			}
//			tokenDTO.setCode(5100);
//			tokenRequestDTO.setTokenDTO(tokenDTO);
//			System.out.println("=== code : " + tokenDTO.getCode() + " ===");
//			return tokenRequestDTO;   //DB에 SecretKey 정보가 저장되지 않았습니다.
////			throw new RuntimeException("DB에 SecretKey 정보가 저장되지 않았습니다.");
//		}
//		tokenDTO.setAccessToken(accessToken);
//		tokenDTO.setAccessTokenExpiresIn(jwtProvider.getExpireTime(accessToken));
//		if(tokenDTO.getCode() != 5100)
//			tokenDTO.setCode(4700);
//		else
//			return tokenRequestDTO;
//		
//		tokenRequestDTO.setTokenDTO(tokenDTO);
//		System.out.println("=== code : " + tokenDTO.getCode() + " ===");
//		// return
//		return tokenRequestDTO;
//	}
	
	
	/* code : 5100 fail)
	 * code : 4700 success(토큰 갱신 포함)
	 * 인증 성공(토큰들의 갱신은 x) -> code : 4800 반환
	 * tokenRequestDTO.type == 3 , refresh token 만료(재로그인 필요)
	 * 
	 * 9.6 수정 : refresh 만료만 확인하면 됨 (수정 작업 진행 중)
	 * */
	
	public TokenRequestDTO autoLogin() {
		System.out.println("::::::::AutoLogin:::::::::");
		String accessToken = null;
		String refreshToken = null;
//		String accessTokenSecretKey;
//		String refreshTokenSecretKey;
		TokenRequestDTO tokenRequestDTO = new TokenRequestDTO();
		Long userNo = (long) 0;
		
		Object principal= SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof CustomUserDetails) {
			accessToken = ((CustomUserDetails)principal).getAccessToken();
			refreshToken = ((CustomUserDetails)principal).getRefreshToken();
			userNo = ((CustomUserDetails)principal).getUserNo();
		}
		
		// refresh token 검증
		if(!jwtProvider.validateJwtToken(refreshToken)) {
//			throw new RuntimeException("Refresh Token이 유효하지 않습니다.");
			// 실패코드로 응답하고 재로그인하도록 함
			tokenDTO.setCode(5100); //인증 실패
			tokenRequestDTO.setTokenDTO(tokenDTO);
			tokenRequestDTO.setType(3);
			return tokenRequestDTO;
		}
		
//		Gson gson = new Gson();
//		try {
//			
//			System.out.println("josn : " + objJson);
//			tokenRequestDTO = gson.fromJson(objJson, tokenRequestDTO.getClass());
//		} catch (Exception e) {
//			 e.printStackTrace();
//			 System.out.println("json 변환오류");
//		}
		
		
//		String username = jwtProvider.getUserNameFromJwtToken(tokenRequestDTO.getAccessToken());
//		CustomUserDetails customUserDetails = (CustomUserDetails)customUserDetailsService.loadUserByUsername(username);
//		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
//		Long userNo = jwtProvider.getUserNoFromJwtToken(tokenRequestDTO.getAccessToken());
		
		// userNo을 통해 DB에서 refresh token 값 가져옴
//		TokenDTO tokenDTO = userDAO.getTokenByUserNo(userNo);
//		if(tokenDTO == null)
//			throw new RuntimeException("로그아웃된 사용자 압니다.");
//		
//		// refresh token 일치하는지 검사
//		if(!tokenDTO.getRefreshToken().equals(tokenRequestDTO.getRefreshToken())) {
//			throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
//		}
		
//		long remain = tokenRequestDTO.getTokenDTO().getRefreshTokenExpireIn() - System.currentTimeMillis();
//		System.out.println("===refresh 유효기간 검사 ===");
//		if (remain <= 6000*3) { //refresh token 만료 3분 전(만료되면 안드로이드에서 autologin 요청을 보내지 않음)
//			String refreshToken = jwtProvider.generateRefreshJwtToken(authentication);
//			refreshTokenSecretKey = jwtProvider.getKeytoString();
//			System.out.println("refreshTokenSecretKey : " + refreshTokenSecretKey);
//			tokenDTO.setRefreshToken(refreshToken);
//			tokenDTO.setRefreshTokenExpireIn(jwtProvider.getExpireTime(refreshToken));
//			// 저장소 refresh token 정보 업데이트(refresh token 재발급 시)
//			if(userDAO.updateRefreshToken(refreshToken, userNo, refreshTokenSecretKey) == 0) {
//				tokenDTO.setCode(5100); //DB에 토큰정보가 갱신되지 않았습니다.
//				tokenRequestDTO.setTokenDTO(tokenDTO);
////				throw new RuntimeException("DB에 토큰정보가 갱신되지 않았습니다.");
//			}
//			tokenRequestDTO.setType(1);
//		} else
//			tokenRequestDTO.setType(0); //refresh 갱신x
//		
//		remain = tokenRequestDTO.getTokenDTO().getAccessTokenExpiresIn() - System.currentTimeMillis();
//		System.out.println("===access 유효기간 검사 ===");
//		if ( remain <= 6000*3 ) { // access token 만료 3분전 재발급
//			String accessToken = jwtProvider.generateAccessJwtToken(authentication);
//			accessTokenSecretKey = jwtProvider.getKeytoString();
//			System.out.println("accessTokenSecretKey" + accessTokenSecretKey);
//			if(userDAO.updateAccessToken(accessTokenSecretKey, userNo) == 0) {
//				if(tokenDTO.getCode() == 5100) { // db 토큰 갱신 실패
//					return tokenRequestDTO;
//				}
//				tokenDTO.setCode(5100);			// acess token key 갱신 실패
//				tokenRequestDTO.setTokenDTO(tokenDTO);
//				return tokenRequestDTO;   //DB에 SecretKey 정보가 저장되지 않았습니다.
//	//			throw new RuntimeException("DB에 SecretKey 정보가 저장되지 않았습니다.");
//			}
//			tokenDTO.setAccessToken(accessToken);
//			tokenDTO.setAccessTokenExpiresIn(jwtProvider.getExpireTime(accessToken));
//			
//			if(tokenDTO.getCode() != 5100)
//				tokenDTO.setCode(4700);
//			else
//				return tokenRequestDTO;
//			tokenRequestDTO.setTokenDTO(tokenDTO);
//		}
		
		tokenDTO.setCode(4700);
		tokenDTO.setAccessToken(accessToken);
		tokenDTO.setRefreshToken(refreshToken);
		tokenRequestDTO.setTokenDTO(tokenDTO);
		tokenRequestDTO.setUserNo(userNo);
		
		return tokenRequestDTO;
//		if(tokenDTO.getCode() == 0) //갱신없이 자동로그인 인증 완료..
//			tokenDTO.setCode(4800);
//		tokenRequestDTO.setTokenDTO(tokenDTO);
//		System.out.println("=== code : " + tokenDTO.getCode() + " ===");
//		return tokenRequestDTO;
	}

}
