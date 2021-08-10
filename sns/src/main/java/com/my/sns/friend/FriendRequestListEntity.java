package com.my.sns.friend;

public class FriendRequestListEntity {
	private Long userNo;
	private String userName;
	private String userImageUrl;
	
	public FriendRequestListEntity() {
		super();
	}

	public FriendRequestListEntity(Long userNo, String userName, String userImageUrl) {
		this.userNo = userNo;
		this.userName = userName;
		this.userImageUrl = userImageUrl;
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

	public String getUserImageUrl() {
		return userImageUrl;
	}

	public void setUserImageUrl(String userImageUrl) {
		this.userImageUrl = userImageUrl;
	}

}
