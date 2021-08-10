package com.my.sns.friend;

public class FriendListEntity {
	private Long userOneNo;
	private byte friendStatus;
	
	
	
	public FriendListEntity() {
		super();
	}
	public FriendListEntity(Long userOneNo, byte friendStatus) {
		super();
		this.userOneNo = userOneNo;
		this.friendStatus = friendStatus;
	}
	public Long getUserOneNo() {
		return userOneNo;
	}
	public void setUserOneNo(Long user_one_no) {
		this.userOneNo = user_one_no;
	}
	public byte getFriendStatus() {
		return friendStatus;
	}
	public void setFriendStatus(byte friendStatus) {
		this.friendStatus = friendStatus;
	}
	
	
	

}
