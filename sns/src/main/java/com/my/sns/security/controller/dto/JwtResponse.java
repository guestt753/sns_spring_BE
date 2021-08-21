package com.my.sns.security.controller.dto;

import java.util.List;

public class JwtResponse {
	private TokenDTO tokenDTO;
	private String type = "Bearer";
	private Long userNo;
//	private String userName;
	private List<String> userRole;	
	
//	public class TokenDTO {
//		private String accessToken;
//		private String refreshToken;
//		
//		public TokenDTO() {
//			super();
//		}
//		public TokenDTO(String accessToken, String refreshToken) {
//			super();
//			this.accessToken = accessToken;
//			this.refreshToken = refreshToken;
//		}
//		public String getAccessToken() {
//			return accessToken;
//		}
//		public void setAccessToken(String accessToken) {
//			this.accessToken = accessToken;
//		}
//		public String getRefreshToken() {
//			return refreshToken;
//		}
//		public void setRefreshToken(String refreshToken) {
//			this.refreshToken = refreshToken;
//		}
//	}
	
//	public TokenDTO getTokenDTO() {
//		return tokenDTO;
//	}
//	public void setTokenDTO(TokenDTO tokenDTO) {
//		this.tokenDTO = tokenDTO;
//	}
	
	public TokenDTO getTokenDTO() {
		return tokenDTO;
	}
	public void setTokenDTO(TokenDTO tokenDTO) {
		this.tokenDTO = tokenDTO;
	}
	public Long getUserNo() {
		return userNo;
	}
	public void setUserNo(Long userNo) {
		this.userNo = userNo;
	}

	public List<String> getUserRole() {
		return userRole;
	}

	public void setUserRole(List<String> userRole) {
		this.userRole = userRole;
	}
	
//	public String getUserName() {
//		return userName;
//	}
//
//	public void setUserName(String userName) {
//		this.userName = userName;
//	}

	
}
