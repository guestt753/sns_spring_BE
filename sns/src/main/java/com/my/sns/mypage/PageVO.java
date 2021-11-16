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
	
	@Override
	public String toString() {
		return "이름: "+ username + "자기소개:"+ userIntroduce+"사진이름:"+ptoName;
	}
}
