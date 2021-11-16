package com.my.sns.mypage;

import java.util.ArrayList;
import java.util.List;

public class AndroidSendData {

	//프로필 편집화면 이름,자기소개,사진
	private String username;
	private String userIntroduce;
	private String ptoName;
	//게시글 추가화면 피드 텍스트,사진
    private String feedtext;
    private List<String> feedptos = new ArrayList<>();
    //게시글 feed_no
    private int feed_no;
    
    
    
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserIntroduce() {
		return userIntroduce;
	}
	public void setUserIntroduce(String userIntroduce) {
		this.userIntroduce = userIntroduce;
	}
	public String getPtoName() {
		return ptoName;
	}
	public void setPtoName(String ptoName) {
		this.ptoName = ptoName;
	}
	public String getFeedtext() {
		return feedtext;
	}
	public void setFeedtext(String feedtext) {
		this.feedtext = feedtext;
	}
	
    
	
	
	
	
}
