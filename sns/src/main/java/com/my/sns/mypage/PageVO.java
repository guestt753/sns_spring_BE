package com.my.sns.mypage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class PageVO {

	private String username;
	private String userIntroduce;
	private String ptoName;
	private int feedno; 
	
	List<FeednoimagenameDATA> list = new ArrayList<>();
	
	/*
	친구 관계 판별하기위한 코드
	4900 : 친구가 아님
	4800 : 친구 요청을 받음
	4700 : 내가 친구요청을 함
	4600 : 친구관계			
	*/
	private int code;
	private int numberOfFriend;
	

	public List<FeednoimagenameDATA> getList() {
		return list;
	}
	public void setList(List<FeednoimagenameDATA> list2) {
		this.list = list2;
		
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setUserIntroduce(String userIntroduce) {
		this.userIntroduce = userIntroduce;
	}
	
	public void setPtoname(String ptoName) {
		this.ptoName = ptoName;
	}
	
	public void setFeedno(int feedno) {
		this.feedno = feedno;
	}
	
	
	public String getUsername() {
		return username;
	}
	public String getUserIntroduce(){
		return userIntroduce;
	}
	public String getPtoname(){
		return ptoName;
	}
	
	public int getFeedno() {
		return feedno;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	public int getNumberOfFriend() {
		return numberOfFriend;
	}
	public void setNumberOfFriend(int numberOfFriend) {
		this.numberOfFriend = numberOfFriend;
	}
	@Override
	public String toString() {
		return "이름: "+ username + "자기소개:"+ userIntroduce+"사진이름:"+ptoName;
	}
}
