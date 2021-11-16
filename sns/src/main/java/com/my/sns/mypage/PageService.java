package com.my.sns.mypage;


public interface PageService {

	
	public void InsertUserNameIntroduction(AndroidSendData vo,Long userNo);
	public PageVO getUserNameIntroduction(Long userNo);
	public PageVO InsertFeedtextandpto(AndroidSendData vo, Long userNo);
	public void InsertFeedptonames(String name,int feedno);
	public void UpdateIsnow(int feedno);
	public PageVO getImagenames(Long userNo);
	public PageVO getnameimagecomment(Long userNo);
	

	
}
