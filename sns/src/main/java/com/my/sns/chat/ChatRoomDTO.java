package com.my.sns.chat;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.web.socket.WebSocketSession;

public class ChatRoomDTO {
	private String roomNo;
    private String roomName;

    private Long userNo;
    private String myImageUrl;
    private String userName;
    
    private List<Long> userNumbers;
    private List<String> userImageUrl;
    
    private int participantNumbers;
    private String receivedMsg;
    private String receivedMsgTime;
    private int receivedMsgNumbers;
//	private Set<WebSocketSession> sessions = new HashSet<WebSocketSession>(); //spring 에서 websocket connection이 맺어진 세션
//	
//	public static ChatRoomDTO create(String name) {
//		ChatRoomDTO room = new ChatRoomDTO();
//		
//		room.roomId = UUID.randomUUID().toString();
//		room.name = name;
//		return room;
//	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public Long getUserNo() {
		return userNo;
	}

	public void setUserNo(Long userNo) {
		this.userNo = userNo;
	}

	
	
	public String getMyImageUrl() {
		return myImageUrl;
	}

	public void setMyImageUrl(String myImageUrl) {
		this.myImageUrl = myImageUrl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	

	public List<Long> getUserNumbers() {
		return userNumbers;
	}

	public void setUserNumbers(List<Long> userNumbers) {
		this.userNumbers = userNumbers;
	}

	public List<String> getUserImageUrl() {
		return userImageUrl;
	}

	public void setUserImageUrl(List<String> userImageUrl) {
		this.userImageUrl = userImageUrl;
	}

	public int getParticipantNumbers() {
		return participantNumbers;
	}

	public void setParticipantNumbers(int participantNumbers) {
		this.participantNumbers = participantNumbers;
	}

	public String getReceivedMsg() {
		return receivedMsg;
	}

	public void setReceivedMsg(String receivedMsg) {
		this.receivedMsg = receivedMsg;
	}

	public String getReceivedMsgTime() {
		return receivedMsgTime;
	}

	public void setReceivedMsgTime(String receivedMsgTime) {
		this.receivedMsgTime = receivedMsgTime;
	}

	public int getReceivedMsgNumbers() {
		return receivedMsgNumbers;
	}

	public void setReceivedMsgNumbers(int receivedMsgNumbers) {
		this.receivedMsgNumbers = receivedMsgNumbers;
	}

	@Override
	public String toString() {
		return "ChatRoomDTO [roomNo=" + roomNo + ", roomName=" + roomName + ", userNo=" + userNo + ", myImageUrl="
				+ myImageUrl + ", userName=" + userName + ", userNoList=" + userNumbers + ", imageUrlList="
				+ userImageUrl + ", participantNumbers=" + participantNumbers + "]";
	}
	
	
	

//	public Set<WebSocketSession> getSessions() {
//		return sessions;
//	}
//
//	public void setSessions(Set<WebSocketSession> sessions) {
//		this.sessions = sessions;
//	}
	
	
}
