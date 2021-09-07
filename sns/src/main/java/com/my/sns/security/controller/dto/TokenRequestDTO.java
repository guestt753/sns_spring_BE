package com.my.sns.security.controller.dto;

public class TokenRequestDTO {
	private TokenDTO tokenDTO;
	private String accessToken; 
	private String refreshToken;
	private int type; // 0-> acessToken만 갱신, 1 -> refreshToken도 갱신..
	
	public String getAccessToken() {
		return accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public int getType() {
		return type;
	}
	public TokenDTO getTokenDTO() {
		return tokenDTO;
	}
	public void setTokenDTO(TokenDTO tokenDTO) {
		this.tokenDTO = tokenDTO;
	}
	public void setType(int type) {
		this.type = type;
	}

}
