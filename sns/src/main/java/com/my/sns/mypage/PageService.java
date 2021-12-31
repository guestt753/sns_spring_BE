package com.my.sns.mypage;

import java.time.LocalDate;
import java.util.List;

import com.google.type.Date;


public interface PageService {

	
	public void InsertUserNameIntroduction(AndroidSendData vo,Long userNo);
	public PageVO getUserNameIntroduction(Long userNo);
	public PageVO InsertFeedtextandpto(AndroidSendData vo, Long userNo);
	public void InsertFeedptonames(String name,int feedno);
	public void UpdateIsnow(int feedno);
	public PageVO getImagenames(Long userNo);
	public PageVO getnameimagecomment(Long userNo);
	public void UpdateComments(Long userNo,String text,int feed_no,String commenttime);
	public PageVO getComments(int feedno);
	public void UpdateRecomments(Long userNo,String recommenttext,String commentsno,String recommenttime);
	public PageVO getRecomments(int commentsno);
	public void Feedlikedata(Long userNo,int feedno);
	public void delete_Feed_Like_Data(Long userNo,int feedno);
	public PageVO get_Feed_like_data(Long userNo);

	
}
