package com.my.sns.friend.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.my.sns.friend.FriendListEntity;
import com.my.sns.friend.FriendRequestListEntity;

@Repository("friendDAO")
public class FriendDAO {
	// JDBC 관련 변수
		@Autowired
		JdbcTemplate jdbcTemplate;
		
		private final String SELECT_FRIEND_BY_USERNO = "select user_one_no, friend_status from sns.friend where user_one_no = ? or user_two_no = ?;";
		private final String DELETE_FRIEND_REQUEST_BY_USERNO = "delete from sns.friend_request where user_two_no = ? and user_one_no = ?";
		private final String SELECT_FRIEND_REQUEST_BY_USERNO = "select u.user_no, u.user_name, u.user_image_url from sns.user u join sns.friend_request rq on rq.user_one_no = u.user_no where rq.user_two_no = ?;";
		private final String INSERT_FRIEND = "insert into sns.friend(user_one_no, user_two_no) values(?,?)";
		
		//AWS 전용 SQL문
		private final String AWS_SELECT_FRIEND_BY_USERNO = "select user_one_no, friend_status from spring_sns.friend where user_one_no = ? or user_two_no = ?;";
		private final String AWS_DELETE_FRIEND_REQUEST_BY_USERNO = "delete from spring_sns.friend_request where user_two_no = ? and user_one_no = ?";
		private final String AWS_SELECT_FRIEND_REQUEST_BY_USERNO = "select u.user_no, u.user_name, u.user_image_url from spring_sns.user u join spring_sns.friend_request rq on rq.user_one_no = u.user_no where rq.user_two_no = ?;";
		private final String AWS_INSERT_FRIEND = "insert into spring_sns.friend(user_one_no, user_two_no) values(?,?)";
		
		public List<FriendRequestListEntity> getFriendRequestByUserNo(Long userNo) {
			  System.out.println("===> Spring JDBC로 getFriendRequestByUserNo() 기능 처리");
			  Object[] args = {userNo};
			  try {
				  return jdbcTemplate.query(AWS_SELECT_FRIEND_REQUEST_BY_USERNO, args, new FriendRequestRowMapper());
			  } catch (Exception e) {
				 e.printStackTrace();
				 return null;
			}
		  }
		
		public List<FriendListEntity> getFriendListByUserNo(Long userNo1, Long userNo2) {
			  System.out.println("===> Spring JDBC로 getFriendListByUserNo() 기능 처리");
			  Object[] args = {userNo1,userNo2};
			  try {
				  return jdbcTemplate.query(AWS_SELECT_FRIEND_BY_USERNO, args, new FriendListRowMapper());
			  } catch (Exception e) {
				 e.printStackTrace();
				 return null;
			}
		  }
		  
		  public int friendRequestAction(Long userNo, Long requestedUserNo) {
			  Object[] args = {userNo,requestedUserNo};
			  try {
				  return jdbcTemplate.update(AWS_DELETE_FRIEND_REQUEST_BY_USERNO, args); //성공시 1리턴
			  } catch (Exception e) {
				 e.printStackTrace();
				 return 0;
			}
		  }
		  
		  public int friendInsert(Long userNo1, Long userNo2) {
			  try {
				  return jdbcTemplate.update(AWS_INSERT_FRIEND, userNo1, userNo2) + jdbcTemplate.update(AWS_INSERT_FRIEND, userNo2, userNo1); //성공시 2리턴
			  } catch (Exception e) {
				 e.printStackTrace();
				 return 0;
			}
		  }
		  
		  
		  
		  
		  // RowMapper...
		  class FriendRequestRowMapper implements RowMapper<FriendRequestListEntity> {
				public FriendRequestListEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
					FriendRequestListEntity vo = new FriendRequestListEntity();
					vo.setUserNo(rs.getLong("USER_NO"));
					vo.setUserName(rs.getString("USER_NAME"));
					vo.setUserImageUrl(rs.getString("USER_IMAGE_URL"));
					return vo;
				}
			}
		  
		  class FriendListRowMapper implements RowMapper<FriendListEntity> {
				public FriendListEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
					FriendListEntity vo = new FriendListEntity();
					vo.setUserOneNo(rs.getLong("USER_ONE_NO"));
					vo.setFriendStatus(rs.getByte("FRIEND_STATUS"));
					return vo;
				}
			}

}
