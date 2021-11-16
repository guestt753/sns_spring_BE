package com.my.sns.mypage.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.sns.mypage.AndroidSendData;
import com.my.sns.mypage.FeednoimagenameDATA;
import com.my.sns.mypage.PageDAO;
import com.my.sns.mypage.PageService;
import com.my.sns.mypage.PageVO;

@Service("pageService")
public class PageServiceImpl implements PageService{

	@Autowired
	private PageDAO pageDAO;
	
	//내 프로필 편집화면
	@Override
	public void InsertUserNameIntroduction(AndroidSendData vo,Long userNo) {
		pageDAO.InsertUserNameIntroduction(vo,userNo);
	}

	//내 프로필 편집화면
	@Override
	public PageVO getUserNameIntroduction(Long userNo) {
		return pageDAO.getUserNameIntroduction(userNo);
	}
	
	//게시글 추가 화면
	@Override
	public PageVO InsertFeedtextandpto(AndroidSendData vo,Long userNo) {
		return pageDAO.InsertFeedtextandpto(vo, userNo);
	}
	//게시글 추가 화면
	@Override
	public void InsertFeedptonames(String name,int feedno) {
		pageDAO.InsertFeedptonames(name,feedno);
	}
	//게시글 추가화면
	@Override
	public void UpdateIsnow(int feedno) {
		pageDAO.UpdateIsnow(feedno);
	}
	//db에서 image_video 테이블에서 이미지 이름 가져오기
	@Override
	public PageVO getImagenames(Long userNo) {
		PageVO vo = new PageVO();
		vo.setList(pageDAO.getImagenames(userNo));
		
		return vo;
	}
	
	//db에서 유저 이름과, feed 번호, image이름들과 ,feed comment 가져오기
	@Override 
	public PageVO getnameimagecomment(Long userNo) {
		PageVO vo = new PageVO();
		
		vo.setList(pageDAO.getnumimagecomment(userNo));
		
		return vo;
	}
	
	
}
