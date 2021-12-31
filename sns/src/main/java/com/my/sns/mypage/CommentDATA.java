package com.my.sns.mypage;

public class CommentDATA {

	//댓글 내용
	String text;
	int feedno;
	//유저이름, 프로필 사진이름
	String username;
	String picname;
	
	//어떤 유저가 작성했는지 알수 있게 구분
	int userno;
	//댓글 작성시간
	String commenttime;

	//댓글 번호:comments_no
    String commentsno;
    
    //답글 내용
    String recommenttext;
    
    //답글 작성시간
    String recommenttime;
    
    //답글화면에서 필요한 commentsno
    int comments_no;
    
    //댓글화면에서 답글 갯수를 표기하기 위한 변수
    int recomment_count;
    
    
    
   
	
	
	public int getRecomment_count() {
		return recomment_count;
	}
	public void setRecomment_count(int recomment_count) {
		this.recomment_count = recomment_count;
	}
	public int getComments_no() {
		return comments_no;
	}
	public void setComments_no(int comments_no) {
		this.comments_no = comments_no;
	}
	public String getRecommenttext() {
		return recommenttext;
	}
	public void setRecommenttext(String recommenttext) {
		this.recommenttext = recommenttext;
	}
	public String getRecommenttime() {
		return recommenttime;
	}
	public void setRecommenttime(String recommenttime) {
		this.recommenttime = recommenttime;
	}
	public String getCommentsno() {
		return commentsno;
	}
	public void setCommentsno(String commentsno) {
		this.commentsno = commentsno;
	}
	
	public String getCommenttime() {
		return commenttime;
	}
	public void setCommenttime(String commenttime) {
		this.commenttime = commenttime;
	}
	public int getUserno() {
		return userno;
	}
	public void setUserno(int userno) {
		this.userno = userno;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPicname() {
		return picname;
	}
	public void setPicname(String picname) {
		this.picname = picname;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getFeedno() {
		return feedno;
	}
	public void setFeedno(int feedno) {
		this.feedno = feedno;
	}
	
}
