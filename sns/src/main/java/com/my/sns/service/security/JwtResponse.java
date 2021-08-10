package com.my.sns.service.security;

import java.util.List;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private Long userNo;
//	private String userName;
	private List<String> userRole;	
	
	public JwtResponse(String token, Long userNo, List<String> userRole) {
	super();
	this.token = token;
	this.userNo = userNo;
	this.userRole = userRole;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
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
