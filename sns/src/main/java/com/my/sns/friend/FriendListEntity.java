package com.my.sns.friend;

public class FriendListEntity {
	private Long userNo;
	private String userName;
	private String userImageUrl;
	private byte friendStatus;
	
	public FriendListEntity() {
		super();
	}
	public FriendListEntity(Long userNo, byte friendStatus) {
		super();
		this.userNo = userNo;
		this.friendStatus = friendStatus;
	}
	
	public FriendListEntity(Long userNo, String userName, String userImageUrl, byte friendStatus) {
		super();
		this.userNo = userNo;
		this.userName = userName;
		this.userImageUrl = userImageUrl;
		this.friendStatus = friendStatus;
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
	public byte getFriendStatus() {
		return friendStatus;
	}
	public void setFriendStatus(byte friendStatus) {
		this.friendStatus = friendStatus;
	}

}
