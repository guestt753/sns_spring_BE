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
	
	//내 유저 번호 - feed_like에서 가져오는거
	int userno;
	
	//spring에서 가져오는 내 번호
	int spring_my_userno;
	
	
	
	
	
	public int getSpring_my_userno() {
		return spring_my_userno;
	}
	public void setSpring_my_userno(int spring_my_userno) {
		this.spring_my_userno = spring_my_userno;
	}

	//메인 화면에서 필요한 데이터를 가져오기 위한 리스트
	List<FeednoimagenameDATA> list = new ArrayList<>();
	
	//메인 화면에서 필요한 데이터를 가져오기 위한 리스트-2
	List<FeednoimagenameDATA> list2 = new ArrayList<>();
	
	//댓글 화면에서 필요한 데이터를 가져오기 위한 리스트
	List<CommentDATA> data = new ArrayList<>();
	
	//답글 화면에서 필요한 데이터를 가져오기 위한 리스트
	List<CommentDATA> recommentdata = new ArrayList<>();
	
	
   

	public List<FeednoimagenameDATA> getList2() {
		return list2;
	}
	public void setList2(List<FeednoimagenameDATA> list2) {
		this.list2 = list2;
	}
	public int getUserno() {
		return userno;
	}
	public void setUserno(int userno) {
		this.userno = userno;
	}
	public List<CommentDATA> getRecommentdata() {
		return recommentdata;
	}
	public void setRecommentdata(List<CommentDATA> recommentdata) {
		this.recommentdata = recommentdata;
	}
	
	public String getPtoName() {
		return ptoName;
	}
	public void setPtoName(String ptoName) {
		this.ptoName = ptoName;
	}
	public List<CommentDATA> getData() {
		return data;
	}
	public void setData(List<CommentDATA> data) {
		this.data = data;
	}
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
