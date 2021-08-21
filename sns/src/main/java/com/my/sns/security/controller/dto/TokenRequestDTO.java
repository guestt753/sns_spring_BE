package com.my.sns.security.controller.dto;

public class TokenRequestDTO {
	private String accessToken; 
	private String refreshToken;
	private int type; // 0-> acessToken만 갱신, 1 -> refreshToken도 갱신..
	
	public String getAccessToken() {
		return accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public int getType() {
		return type;
	}

}
