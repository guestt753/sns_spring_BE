package com.my.sns.mypage.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.type.Date;
import com.my.sns.mypage.AndroidSendData;
import com.my.sns.mypage.CommentDATA;
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
	//메인화면에서 필요한 정보를 feed_like 테이블에서 가져온다
	@Override
	public PageVO get_Feed_like_data(Long userNo){
		PageVO vo = new PageVO();
		
		vo.setList2(pageDAO.get_Feed_like_data(userNo));
		
		return vo;
	}
	//내가 작성한 댓글 내용과 feed_no를 db에 넣기 위한 코드
	@Override
	public void UpdateComments(Long userNo,String text,int feed_no,String commenttime) {
		 pageDAO.UpdateComments(userNo, text, feed_no,commenttime);
		
	}
	
	//댓글 화면에서 필요한 1.이름,2.프로필 사진,3.댓글 내용가져오기
	@Override
	public PageVO getComments(int feedno){
		PageVO vo = new PageVO();
		vo.setData(pageDAO.getComments(feedno));
		
		return vo;	
	}
	//답글 화면에서 작성한 답글을 db에 넣기 위한코드
    @Override
    public void UpdateRecomments(Long userNo,String recommenttext,String commentsno,String recommenttime) {
    	pageDAO.UpdateRecomments(userNo, recommenttext, commentsno, recommenttime);
    }
    //답글 화면에서 필요한 1.내이름 2.프로필 사진 3.답글 내용 4.답글 작성 시간을 가져온다
    @Override
    public PageVO getRecomments(int commentsno){
    	PageVO vo = new PageVO();
    	vo.setRecommentdata(pageDAO.getRecomments(commentsno));
    	return vo;
    }
  //메인화면에서 좋아요를 눌렀을때 - 빈하트인 상태에서 좋아요 버튼을 눌렀을떄 db에 값을 insert 한다
    @Override
    public void Feedlikedata(Long userNo,int feedno) {
    	 pageDAO.Feedlikedata(userNo, feedno);
    }
    //메인화면에서 좋아요를 눌렀을떄 - 빨간 하트인 상태에서 좋아요를 눌렀을때 db에 있는 데이터를 삭제한다
    @Override
    public void delete_Feed_Like_Data(Long userNo,int feedno) {
    	pageDAO.delete_Feed_Like_Data(userNo, feedno);
    }
}
