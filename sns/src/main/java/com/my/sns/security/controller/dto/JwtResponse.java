package com.my.sns.security.controller.dto;

import java.util.List;

public class JwtResponse {
	private TokenDTO tokenDTO;
	private String type = "Bearer";
	private Long userNo;
//	private String userName;
	private List<String> userRole;	
	
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
