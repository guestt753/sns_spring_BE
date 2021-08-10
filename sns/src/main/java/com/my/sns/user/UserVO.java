package com.my.sns.user;

import org.springframework.stereotype.Component;

@Component
public class UserVO {
	private Long userNo;
	private String userName;
	private String userId;
	private String userPassword;
	private String userImageUrl;
	private String userIntroduction;
	private byte userSignupType;

	

	public UserVO() {
		super();
	}


	public UserVO(String userId, String userPassword) {
		this.userId = userId;
		this.userPassword = userPassword;
	}

	
	public Long getUserNo() {
		return userNo;
	}


	public void setUserNo(Long userNo) {
		this.userNo = userNo;
	}


	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserImageUrl() {
		return userImageUrl;
	}
	public void setUserImageUrl(String userImageUrl) {
		this.userImageUrl = userImageUrl;
	}
	public String getUserIntroduction() {
		return userIntroduction;
	}
	public void setUserIntroduction(String userIntroduction) {
		this.userIntroduction = userIntroduction;
	}
	public byte getUserSignupType() {
		return userSignupType;
	}
	public void setUserSignupType(byte userSignupType) {
		this.userSignupType = userSignupType;
	}
	@Override
	public String toString() {
		return "UserVO [userName=" + userName + ", userId=" + userId + ", userPassword=" + userPassword
				+ ", userImageUrl=" + userImageUrl + ", userIntroduction=" + userIntroduction + ", userSignupType="
				+ userSignupType + "]";
	}
	
	
}
