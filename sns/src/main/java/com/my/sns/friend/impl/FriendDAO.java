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
		
		private final String DELETE_FRIEND_BY_USERNO = "delete from friend where (user_one_no = ? and user_two_no = ?) or (user_one_no = ? and user_two_no = ?);";
		private final String INSERT_FRIEND_REQUEST = "insert into friend_request values(?,?);";
		private final String SELECT_FRIEND_BY_USERNO = "select user_one_no, (select user_name from user where user_no = user_one_no) as 'user_name', (select user_image_url from user where user_no = user_one_no) as 'user_image_url', friend_status from friend where (user_one_no = ?) or (user_two_no = ?);";
		private final String DELETE_FRIEND_REQUEST_BY_USERNO = "delete from sns.friend_request where user_two_no = ? and user_one_no = ?";
		private final String SELECT_FRIEND_REQUEST_BY_USERNO = "select u.user_no, u.user_name, u.user_image_url from sns.user u join sns.friend_request rq on rq.user_one_no = u.user_no where rq.user_two_no = ?;";
		private final String INSERT_FRIEND = "insert into sns.friend(user_one_no, user_two_no) values(?,?)";
		private final String IS_FRIEND_REQUEST = "select count(*) from friend_request where (user_one_no = ?) and (user_two_no = ?);";
		private final String IS_MY_FRIEND = "select count(*) from sns.friend where (user_one_no = ?) and (user_two_no = ?);";
		
		//AWS 전용 SQL문
		private final String AWS_SELECT_FRIEND_BY_USERNO = "select user_one_no, friend_status from spring_sns.friend where user_one_no = ? or user_two_no = ?;";
		private final String AWS_DELETE_FRIEND_REQUEST_BY_USERNO = "delete from spring_sns.friend_request where user_two_no = ? and user_one_no = ?";
		private final String AWS_SELECT_FRIEND_REQUEST_BY_USERNO = "select u.user_no, u.user_name, u.user_image_url from spring_sns.user u join spring_sns.friend_request rq on rq.user_one_no = u.user_no where rq.user_two_no = ?;";
		private final String AWS_INSERT_FRIEND = "insert into spring_sns.friend(user_one_no, user_two_no) values(?,?)";
		
		public int isFriendRequestByUserNo(Long myUserNo, Long userNo) {
			  System.out.println("===> Spring JDBC로 isFriendRequestByUserNo() 기능 처리");
			  Object[] args = {userNo, myUserNo};
			  Object[] args2 = {myUserNo, userNo};
			  try {
				  int result = jdbcTemplate.queryForObject(IS_FRIEND_REQUEST, args2, Integer.class);
				  if(result > 0) { // 내가 친구 신청한 경우
					  return 2;
				  }
				  return jdbcTemplate.queryForObject(IS_FRIEND_REQUEST, args, Integer.class);
			  } catch (Exception e) {
				 e.printStackTrace();
				 return -1;
			}
		}
		
		public int isMyFriendByUserNo(Long myUserNo, Long userNo) {
			  System.out.println("===> Spring JDBC로 isMyFriendByUserNo() 기능 처리");
			  Object[] args = {userNo, myUserNo};
			  try {
				  return jdbcTemplate.queryForObject(IS_MY_FRIEND, args, Integer.class);
			  } catch (Exception e) {
				 e.printStackTrace();
				 return -1;
			}
		}
		
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
		  
		  public int friendRequestDeleteAction(Long userNo, Long requestedUserNo, final int type) {
			  Object[] argsTypeZero = {userNo,requestedUserNo}; // 받은 친구 신청을 거절할경우
			  Object[] argsTypeOne = {requestedUserNo,userNo}; // 내가 친구 신청했을 때 취소할 경우
			  try {
<<<<<<< HEAD
				  return jdbcTemplate.update(AWS_DELETE_FRIEND_REQUEST_BY_USERNO, args); //성공시 1리턴
=======
				  if(type == 0)
					  return jdbcTemplate.update(DELETE_FRIEND_REQUEST_BY_USERNO, argsTypeZero); //성공시 1리턴
				  else
					  return jdbcTemplate.update(DELETE_FRIEND_REQUEST_BY_USERNO, argsTypeOne); //성공시 1리턴
>>>>>>> feature/dong
			  } catch (Exception e) {
				 e.printStackTrace();
				 return 0;
			}
		  }
		  
		  public int insertFriend(Long userNo1, Long userNo2) {
			  try {
				  return jdbcTemplate.update(AWS_INSERT_FRIEND, userNo1, userNo2) + jdbcTemplate.update(AWS_INSERT_FRIEND, userNo2, userNo1); //성공시 2리턴
			  } catch (Exception e) {
				 e.printStackTrace();
				 return 0;
			}
		  }
		  
		  public int deleteFriend(Long myUserNo, Long userNo) {
			  Object[] args = {myUserNo, userNo, userNo, myUserNo};
			  try {
				  return jdbcTemplate.update(DELETE_FRIEND_BY_USERNO, args); // 성공시 2리턴
			  } catch (Exception e) {
				 e.printStackTrace();
				 return 0;
			}
		  }
		  
		  public int friendRequestInsertAction(Long userNo, Long myUserNo) {
			  Object[] args = {myUserNo, userNo};
			  try {
				  return jdbcTemplate.update(INSERT_FRIEND_REQUEST, args);
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
					vo.setUserNo(rs.getLong("USER_ONE_NO"));
					vo.setUserName(rs.getString("USER_NAME"));
					vo.setUserImageUrl(rs.getString("USER_IMAGE_URL"));
					vo.setFriendStatus(rs.getByte("FRIEND_STATUS"));
					return vo;
				}
			}

}
