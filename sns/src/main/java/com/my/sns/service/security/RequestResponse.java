package com.my.sns.service.security;

import java.util.ArrayList;
import java.util.List;

public class RequestResponse {
	private String message;
	private int code;
	private List<FriendRequestListEntity> friendRequestList = new ArrayList<>();
	private List<SearchListEntity> searchList = new ArrayList<>();
	
	public static class FriendRequestListEntity {
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
	
	public static class SearchListEntity {
		Long userNo;
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

		public SearchListEntity(Long userNo, String userName, String userImageUrl) {
			super();
			this.userNo = userNo;
			this.userName = userName;
			this.userImageUrl = userImageUrl;
		}

		public SearchListEntity(Long userNo, String userName, String isFriend, String userImageUrl) {
			super();
			this.userNo = userNo;
			this.userName = userName;
			this.isFriend = isFriend;
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
	
//	public FriendRequestListEntity instance = new RequestResponse.FriendRequestListEntity();

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public List<FriendRequestListEntity> getFriendRequestListEntity() {
		return friendRequestList;
	}

	public void setFriendRequestListEntity(List<FriendRequestListEntity> friendRequestList) {
		this.friendRequestList = friendRequestList;
	}

	public List<SearchListEntity> getSearchListEntity() {
		return searchList;
	}
	
	public void setSearchListEntity(List<SearchListEntity> searList) {
		this.searchList = searList;
	}
	
	

}
