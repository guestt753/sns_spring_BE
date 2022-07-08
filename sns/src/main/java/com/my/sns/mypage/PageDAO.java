package com.my.sns.mypage;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

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
		Object[] args = {userNo,userNo,userNo};
		return jdbcTemplate.query(MAINNEED,args,new getnumimagecomment());
	}
	
}
