package com.my.sns.mypage;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.google.type.Date;
import com.my.sns.security.entity.CustomUserDetails;

@Repository("pageDAO")
public class PageDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;
	//사진 저장할 경로
     String uploadPath="C:/upload";
    
    //안드로이드 전송용 데이터 클래스
     PageVO pagevo = new PageVO();
     
    
	
    //내 프로필 수정 화면 관련 sql문들
	private final String USER_INSERT = "update user set user_name=?,user_introduction=?,user_image_url=? where user_no=?";
	private final String USER_NAME_INTRODUCTION = "select user_name,user_introduction,user_image_url,(select count(user_two_no)from sns.friend where (user_two_no = ?)) as number_of_friend from user where user_no=?;";
	
	//게시글 추가 화면 관련 sql문들
	//피드 텍스트를 feed의 writedata에 넣는다
	private final String FEED_ADD = "insert into feed(user_no,feed_content,is_now) values(?,?,?)";
	//피드 사진들을 image_video에 넣는다
	private final String FEED_PTOS= "Insert into image_video(feed_no,image_video_url) values(?,?)";
	// feed 테이블에서 feed_no를 가져온다
	private final String FEED_NO = "select feed_no from feed where is_now=1 and user_no=?";
	// feed 테이블에서 feed_no를 가져오고 is_now를 0으로 바꾼다 
	private final String IS_NOW = "update feed set is_now=0 where feed_no=?";
	// image_video 테이블에서 사진 이름을 피드 번호별로 1개씩 가져오는 코드
	private final String get_IMAGENAME = "select image_video.feed_no,image_video_url from feed inner join image_video on feed.feed_no "
			+ "= image_video.feed_no where feed.user_no=?"; 
	
	//메인 화면에서 필요한   1.피드 번호,2.사진들,3.피드 추가시 작성한 코멘트 4.내 이름, 5.내 프로필 사진 가져오기
	private final String MAINNEED = "select image_video.feed_no,image_video_url,feed.feed_content,(select user_name from user where user_no=?) as user_name,(select user_image_url from user where user_no=?) as user_image_url from feed"
			+ " inner join image_video on feed.feed_no = image_video.feed_no where feed.user_no=?";
	
	//AWS 전용 SQL구문
	//내 프로필 수정 화면 관련 sql문들
		private final String AWS_USER_INSERT = "update spring_sns.user set user_name=?,user_introduction=?,user_image_url=? where user_no=?";
		private final String AWS_USER_NAME_INTRODUCTION = "select user_name,user_introduction,user_image_url from spring_sns.user where user_no=?";
		
		//게시글 추가 화면 관련 sql문들
		//피드 텍스트를 feed의 writedata에 넣는다
		private final String AWS_FEED_ADD = "insert into spring_sns.feed(user_no,feed_content,is_now) values(?,?,?)";
		//피드 사진들을 image_video에 넣는다
		private final String AWS_FEED_PTOS= "Insert into spring_sns.image_video(feed_no,image_video_url) values(?,?)";
		// feed 테이블에서 feed_no를 가져온다
		private final String AWS_FEED_NO = "select feed_no from spring_sns.feed where is_now=1 and user_no=?";
		// feed 테이블에서 feed_no를 가져오고 is_now를 0으로 바꾼다 
		private final String AWS_IS_NOW = "update spring_sns.feed set is_now=0 where feed_no=?";
		// image_video 테이블에서 사진 이름을 피드 번호별로 1개씩 가져오는 코드
		private final String AWS_get_IMAGENAME = "select image_video.feed_no,image_video_url from spring_sns.feed inner join spring_sns.image_video on feed.feed_no "
				+ "= image_video.feed_no where feed.user_no=?"; 
		
		//메인 화면에서 필요한   1.피드 번호,2.사진들,3.피드 추가시 작성한 코멘트 4.내 이름, 5.내 프로필 사진 가져오기
		private final String AWS_MAINNEED = "select image_video.feed_no,image_video_url,feed.feed_content,(select user_name from spring_sns.user where user_no=?) as user_name,(select user_image_url from spring_sns.user where user_no=?) as user_image_url from spring_sns.feed"
				+ " inner join spring_sns.image_video on feed.feed_no = image_video.feed_no where feed.user_no=?";
	
	//메인 화면에서 필요한 feed_like 테이블의 정보 4가지 ,user_no,feed_like_count,feed_like_feed_no,image_video_feed_no
	private final String MAINNEED2 = "select distinct feed_like.user_no,image_video.feed_no,(select  feed_no from spring_sns.feed_like where image_video.feed_no=feed_like.feed_no) as feed_like_feed_no,(select count(user_no) from spring_sns.feed_like) as feed_like_count from spring_sns.feed_like inner join spring_sns.image_video";
	
	//1.댓글 작성한 것과 2.feed_no 3.user_no를 db에 저장하기 위한 sql문
	private final String COMMENT_TEXT = "insert into spring_sns.comments(feed_no,user_no,comments_writedate,commnets_content) values(?,?,?,?)";
	
	
	
	//댓글화면에서 필요한 1.유저이름,유저 2.프로필 사진명 3.댓글 작성시각 4.댓글 내용을 user테이블과 comments테이블에서 가져온다
	private final String COMMENT_DATA = "select (select count(reply.comments_no)from spring_sns.reply where reply.comments_no=comments.comments_no) as recomment_count,user.user_name,user.user_image_url,comments.comments_writedate,comments.commnets_content,comments.comments_no,user.user_no from spring_sns.user join spring_sns.comments where comments.feed_no=?";
	
	//답글화면에서 작성한 1.답글 2.댓글 번호 3.답글 작성시간을 db에 넣기 위한 코드
	private final String RECOMMENTDATA = "insert into spring_sns.reply(comments_no,user_no,reply_content,reply_writedate) values(?,?,?,?)";
	
	//답글화면에서 필요한 1.내이름 2.프로필 사진 3.답글 내용 4.답글 작성시간
	private final String GETRECOMMENTDATA = "select user.user_name,user.user_image_url,reply.reply_content,reply.reply_writedate,reply.comments_no,user.user_no from spring_sns.user join spring_sns.reply where reply.comments_no=?";
	
	//메인화면에서 좋아요를 눌렀을때 - 빈하트인 상태에서 좋아요 버튼을 눌렀을떄 db에 값을 insert 한다
	private final String POSTFEEDLIKEDATA = "insert into spring_sns.feed_like(feed_no,user_no) values(?,?)";
	
	//메인화면에서 좋아요를 눌렀을떄 - 빨간 하트인 상태에서 좋아요를 눌렀을때 db에 있는 데이터를 삭제한다
	private final String DELETE_FEED_LIKE_DATA = "delete from spring_sns.feed_like where user_no=?";
	
	
	
	
	//db에 값 넣기 + pagevo에 이미지 이름 넣기 (내 프로필 편집화면)
	public void InsertUserNameIntroduction(AndroidSendData vo,Long userNo) {
		System.out.println("안드로이드에서 가져온 값들을 db에 넣기");
		Object[] args = {vo.getUsername(),vo.getUserIntroduce(),vo.getPtoName(),userNo};
		
		
		//텍스트를 db에 넣는다 (이름,자기소개)
		jdbcTemplate.update(USER_INSERT,args);
		//사진이 저장된 폴더의 경로를 db에 넣는다
		
		
	}
	
	//db에서 데이터 가져오기 (내 프로필 편집 화면에서 보낸 데이터들)
	public PageVO getUserNameIntroduction(Long userNo) {
		System.out.println("ID를 확인후 사용자 이름과 자기 소개 가져오기");
		Object[] args = {userNo, userNo};
		try {
			return jdbcTemplate.queryForObject(USER_NAME_INTRODUCTION,args,new UserNameMapper());
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
		
	// 내 프로필 편집화면과 관련 db에서 이름,소개,사진이름을 가져온다
	class UserNameMapper implements RowMapper<PageVO>{
		public PageVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			
			pagevo.setUsername(rs.getString("user_name"));
			pagevo.setUserIntroduce(rs.getString("user_introduction"));
			pagevo.setPtoname(rs.getString("user_image_url"));
			pagevo.setNumberOfFriend(rs.getInt("number_of_friend"));
			
			System.out.println("안드로이드로 보낼 데이터 클래스 저장 여부 확인 -> 이름:"+rs.getString("user_name"));
			System.out.println("안드로이드로 보낼 데이터 클래스 저장 여부 확인 -> 소개:"+rs.getString("user_introduction"));
			System.out.println("안드로이드로 보낼 데이터 클래스 저장 여부 확인 -> 사진이름:"+rs.getString("user_image_url"));
			
			return pagevo;	
		}
	}
	// 게시글 추가 화면 관련 db에서 feed_no를 가져온다
	class FeedNo implements RowMapper<PageVO>{
		public PageVO mapRow(ResultSet rs, int rowNum) throws SQLException{
			pagevo.setFeedno(rs.getInt("feed_no"));
			return pagevo;
		}
	}
	// 내 프로필 화면에서 db에 저장된 사진 이름 가져오기
	class getImagenames implements RowMapper<FeednoimagenameDATA>{
		public FeednoimagenameDATA mapRow(ResultSet rs,int ronNum) throws SQLException{
			
			FeednoimagenameDATA data = new FeednoimagenameDATA();
			
				data.setFeedno(rs.getInt("feed_no"));
			    data.setImagename(rs.getString("image_video_url"));
			    
			    System.out.println("feedno:"+data.getFeedno());
			    System.out.println("imagename:"+data.getImagename());
			return data;
		}
	}
	//메인화면에서 필요한 1.feed 번호 그리고 2.이미지들 이름 그리고 3.피드 추가시 작성한 텍스트 4.내 이름 5.내 프로필 사진을 가져온다
	class getnumimagecomment implements RowMapper<FeednoimagenameDATA>{
		public FeednoimagenameDATA mapRow(ResultSet rs,int ronNum) throws SQLException{
			FeednoimagenameDATA data = new FeednoimagenameDATA();
			     data.setFeedno(rs.getInt("feed_no"));
			     data.setImagename(rs.getString("image_video_url"));
			     data.setFeedcontent(rs.getString("feed_content"));
			     data.setUsername(rs.getString("user_name"));
			     data.setMyimagename(rs.getString("user_image_url"));
			   
			          
			     return data;
		}
	}
	//메인화면에서 필요한 정보를 feed_like 테이블에서 가져온다
	class get_feed_like_data implements RowMapper<FeednoimagenameDATA>{
		public FeednoimagenameDATA mapRow(ResultSet rs,int ronNum) throws SQLException{
			FeednoimagenameDATA data = new FeednoimagenameDATA();
			data.setUserno(rs.getInt("user_no"));
			data.setFeedno(rs.getInt("feed_no"));
			data.setFeed_like_feed_no(rs.getInt("feed_like_feed_no"));
			data.setFeedlikecount(rs.getInt("feed_like_count"));
			
			return data;
		}
	}
	//댓글 화면에서 필요한 1.프로필 사진,2.내 이름,3.댓글 내용 4.user_no를 가져온다
	class getComments implements RowMapper<CommentDATA> {
		public CommentDATA mapRow(ResultSet rs,int ronNum) throws SQLException{
			CommentDATA data = new CommentDATA();
			data.setPicname(rs.getString("user_image_url"));
			data.setUsername(rs.getString("user_name"));
			data.setText(rs.getString("commnets_content"));
			data.setUserno(rs.getInt("user_no"));
			data.setCommenttime(rs.getString("comments_writedate"));
			data.setCommentsno(rs.getString("comments_no"));
			data.setRecomment_count(rs.getInt("recomment_count"));
			
			return data;
			
		}
		
	}
	
	//답글화면에서 필요한 1.이름 2.프로필 사진 3.답글 내용 4.답글 작성시간
	class getRecomments implements RowMapper<CommentDATA>{
		public CommentDATA mapRow(ResultSet rs,int ronNum) throws SQLException{
			CommentDATA data = new CommentDATA();
			data.setUsername(rs.getString("user_name"));
			data.setPicname(rs.getString("user_image_url"));
			data.setRecommenttext(rs.getString("reply_content"));
			data.setRecommenttime(rs.getString("reply_writedate"));
			data.setComments_no(rs.getInt("comments_no"));
			return data;
			
		
		}
	}
	
	//게시글 추가화면이랑 관련 - 피드 텍스트를 db에 넣는다
	public PageVO InsertFeedtextandpto(AndroidSendData vo,Long userNo) {
		System.out.println("게시글 추가 관련 데이터");
		Object[] args = {userNo,vo.getFeedtext(),1};
	    Object[] args2 = {userNo};
		jdbcTemplate.update(FEED_ADD,args);
		//db에서 feed_no를 가져온다
		return jdbcTemplate.queryForObject(FEED_NO,args2,new FeedNo());
	}
	
	//게시글 추가 화면이랑 관련 - 사진이름들
	public void InsertFeedptonames(String name,int feedno) {
		Object[] args = {feedno,name};
		jdbcTemplate.update(FEED_PTOS,args);
	}
	//is_now에 값을 삽입
	public void UpdateIsnow(int feedno) {
		Object[] args = {feedno};
		jdbcTemplate.update(IS_NOW,args);
	}
	//db에서 image_video 이름 가져오기
	public List<FeednoimagenameDATA> getImagenames(Long userNo) {
		Object[] args = {userNo};
		List<FeednoimagenameDATA> list=null;
		list = jdbcTemplate.query(get_IMAGENAME,args,new getImagenames());
		
	  return list;
	}
	//메인 화면에서 필요한 feed번호, image 이름들, 피드 텍스트들을 가져온다
	public List<FeednoimagenameDATA> getnumimagecomment(Long userNo){
		Object[] args = {userNo,userNo,userNo,userNo};
		return jdbcTemplate.query(MAINNEED,args,new getnumimagecomment());
	}
	//메인 화면에서 필요한 정보를 feed_like 테이블에서 가져온다
	public List<FeednoimagenameDATA> get_Feed_like_data(Long userNo){
		
		return jdbcTemplate.query(MAINNEED2,);
	//안드로이드에서 보낸 feed_no,댓글 내용을 db에 넣기 위한 코드
	public void UpdateComments(Long userNo,String text,int feed_no,String commenttime) {
		
		Object[] args = {feed_no,userNo,commenttime,text};
		jdbcTemplate.update(COMMENT_TEXT, args);
		
	}
	
	//안드로이드 댓글 화면에서 필요한  1.프로필 사진, 2.내 이름  3.댓글 내용
	public List<CommentDATA> getComments(int feedno){
		Object[] args = {feedno};
		List<CommentDATA> list = null;
		
		list = jdbcTemplate.query(COMMENT_DATA, args,new getComments());
		
		return list;
	}
	//답글화면에서 작성한 답글을 db에 저장하기 위한코드
	public void UpdateRecomments(Long userNo,String recommenttext,String commentsno,String recommenttime) {
		Object[] args = {commentsno,userNo,recommenttext,recommenttime};
		jdbcTemplate.update(RECOMMENTDATA, args);
	}
	
	//답글화면에서 필요한 1.프로필 사진 2.내 이름 3.답글 내용 4.답글 작성 시간 5.commentsno 을 가져온다
	public List<CommentDATA> getRecomments(int commentsno){
		Object[] args = {commentsno};
		List<CommentDATA> list = null;
		list = jdbcTemplate.query(GETRECOMMENTDATA, args, new getRecomments());
		return list;
	}
	
	//메인화면에서 좋아요를 눌렀을때 - 빈하트인 상태에서 좋아요 버튼을 눌렀을떄 db에 값을 insert 한다
	public void Feedlikedata(Long userNo,int feedno) {
		Object[] args = {feedno,userNo};
		jdbcTemplate.update(POSTFEEDLIKEDATA,args);
	}
	//메인화면에서 좋아요를 눌렀을떄 - 빨간 하트인 상태에서 좋아요를 눌렀을때 db에 있는 데이터를 삭제한다
	public void delete_Feed_Like_Data(Long userNo,int feedno) {
		Object[] args = {userNo};
		jdbcTemplate.update(DELETE_FEED_LIKE_DATA, args);
	}
}
