package com.my.sns.security.controller.dto;

public class TokenDTO {
	private String accessToken;
	private String refreshToken;
	private String accessTokenSecretKey;
	private String refreshTokenSecretKey;
	private int code;
	
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getRefreshTokenSecretKey() {
		return refreshTokenSecretKey;
	}
	public void setRefreshTokenSecretKey(String refreshTokenSecretKey) {
		this.refreshTokenSecretKey = refreshTokenSecretKey;
	}
	
	public String getAccessTokenSecretKey() {
		return accessTokenSecretKey;
	}
	public void setAccessTokenSecretKey(String accessTokenSecretKey) {
		this.accessTokenSecretKey = accessTokenSecretKey;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	
	
}
