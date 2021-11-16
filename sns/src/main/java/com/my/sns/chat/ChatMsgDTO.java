package com.my.sns.chat;

public class ChatMsgDTO {
    private String roomId;
    private Long userNo;
    private String userName;
    private String imageUrl;
    private String message;
    private String time;

    public ChatMsgDTO() {
    }

    public ChatMsgDTO(String roomId, Long userNo, String userName, String imageUrl, String message, String time) {
        this.roomId = roomId;
        this.userNo = userNo;
        this.userName = userName;
        this.imageUrl = imageUrl;
        this.message = message;
        this.time = time;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}


