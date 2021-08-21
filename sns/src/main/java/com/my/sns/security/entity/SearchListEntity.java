package com.my.sns.security.entity;

public class SearchListEntity {
	String userName;
	String isFriend;
	String userImageUrl;
	
	public SearchListEntity() {
		super();
	}

	public SearchListEntity(String userName, String userImageUrl) {
		super();
		this.userName = userName;
		this.userImageUrl = userImageUrl;
	}

	public SearchListEntity(String userName, String isFriend, String userImageUrl) {
		super();
		this.userName = userName;
		this.isFriend = isFriend;
		this.userImageUrl = userImageUrl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIsFriend() {
		return isFriend;
	}

	public void setIsFriend(String isFriend) {
		this.isFriend = isFriend;
	}

	public String getUserImageUrl() {
		return userImageUrl;
	}

	public void setUserImageUrl(String userImageUrl) {
		this.userImageUrl = userImageUrl;
	}
	
	

}
