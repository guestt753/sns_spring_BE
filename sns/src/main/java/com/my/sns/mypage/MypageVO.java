package com.my.sns.mypage;


public class MypageVO {

	private String username;
	private String userIntroduce;
	private String ptname;
	
	public String getUsername() {
		return username;
	}
	public String getUserIntroduce() {
		return userIntroduce;
	}
	public String getptname() {
		return ptname;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	public void setUserIntroduce(String userIntroduce) {
		this.userIntroduce = userIntroduce;
	}
	public void setptname(String ptname) {
		this.ptname= ptname;
	}
	
	
	
	@Override
	public String toString() {
		return "유저이름:"+username+"유저 소개:"+userIntroduce+"사진명"+ptname;
	}
	
}
