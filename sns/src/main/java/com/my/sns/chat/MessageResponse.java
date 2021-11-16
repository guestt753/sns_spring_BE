package com.my.sns.chat;

import java.text.SimpleDateFormat;

public class MessageResponse {
	private Long roomNo;
	private Long userNo;
	private String profileImgUrl;
	private String writer;
	private String message;
	private SimpleDateFormat time;
	
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public SimpleDateFormat getTime() {
		return time;
	}
	public void setTime(SimpleDateFormat time) {
		this.time = time;
	}
	public Long getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(Long roomNo) {
		this.roomNo = roomNo;
	}
	public Long getUserNo() {
		return userNo;
	}
	public void setUserNo(Long userNo) {
		this.userNo = userNo;
	}
	public String getProfileImgUrl() {
		return profileImgUrl;
	}
	public void setProfileImgUrl(String profileImgUrl) {
		this.profileImgUrl = profileImgUrl;
	}
	

}
