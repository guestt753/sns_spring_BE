package com.my.sns.user.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.my.sns.friend.FriendListEntity;
import com.my.sns.friend.FriendRequestListEntity;
import com.my.sns.security.controller.dto.TokenDTO;
import com.my.sns.security.entity.UserEntity;
import com.my.sns.user.UserRoleVO;
import com.my.sns.user.UserVO;
import com.my.sns.user.impl.UserRoleDAO.UserRoleRowMapper;

@Repository("userDAO")
public class UserDAO {
	// JDBC 관련 변수
	@Autowired
	JdbcTemplate jdbcTemplate;
//	@Autowired
//	NamedParameterJdbcTemplate jdbcTemplate;
	
	// SQL 명령어들
//	private final String USER_INSERT = "insert into user(user_name, user_password, user_id, user_signup_type) values(?,?,?,?)";
	private final String USER_INSERT = "insert into user(user_name, user_password, user_id, user_signup_type)" + "VALUES (:user_name, :user_password, :user_id, :user_signup_type);";
	public  final String SELECT_ALL_BY_USERID = "SELECT * FROM user WHERE user_id = :user_id";
//	private final String USER_GET = "select * from spring_sns.user where user_id =?";  aws 서버에있는 db용
	private final String USER_GET = "select * from user where user_id =?";
	private final String SELECT_USER_NAME_BY_SEARCH = "select * from user where user_name like concat('%', ? ,'%')";
	private final String SELECT_REFRESH_TOKEN_BY_USERNO = "select value from refresh_token where user_no = ?;";
	private final String UPDATE_REFRESH_TOKEN_BY_USERNO = "update refresh_token set value=? where user_no = ?;";
	private final String DELETE_REFRESH_TOKEN_BY_USERNO = "delete from refresh_token where user_no = ?;";
	private final String INSERT_REFRESH_TOKEN_BY_USERNO = "insert into refresh_token values (?,?);";
	
	
	// CRUD 기능의 메소드 구현
	// 회원 조회
	
	  public UserVO getUserByUserId(String userId) {
		  System.out.println("===> Spring JDBC로 getUserByUserId() 기능 처리");
		  Object[] args = {userId}; 
		  try {
			  return jdbcTemplate.queryForObject(USER_GET, args, new UserRowMapper());
		  } catch (Exception e) {
			 e.printStackTrace();
			 return null;
		}
	  }
	  
	  public List<UserVO> getUserNameBySearch(String query) {
		  System.out.println("===> Spring JDBC로 getUserNameBySearch() 기능 처리");
		  Object[] args = {query};
		  try {
			  return jdbcTemplate.query(SELECT_USER_NAME_BY_SEARCH, args, new UserRowMapper());
		  } catch (Exception e) {
			 e.printStackTrace();
			 return null;
		}
	  }
	  
	  public TokenDTO getRefreshTokenByuserNo(Long userNo) {
		  System.out.println("===> Spring JDBC로 getRefreshTokenByuserNo() 기능 처리");
		  Object[] args = {userNo};
		  try {
			  return jdbcTemplate.queryForObject(SELECT_REFRESH_TOKEN_BY_USERNO, args, new RefreshTokenRowMapper());
		  } catch (Exception e) {
			 e.printStackTrace();
			 return null;
		}
	  }
	  
	  public int updateRefreshToken(String token, Long userNo) {
		  System.out.println("===> Spring JDBC로 updateRefreshToken() 기능 처리");
		  Object[] args = {token,userNo};
		  try {
			  return jdbcTemplate.update(UPDATE_REFRESH_TOKEN_BY_USERNO, args);
		  } catch (Exception e) {
			 e.printStackTrace();
			 return 0;
		}
	  }
	  
	  public int deleteRefreshToken(Long userNo) {
		  System.out.println("===> Spring JDBC로 deleteRefreshToken() 기능 처리");
		  Object[] args = {userNo};
		  try {
			  return jdbcTemplate.update(DELETE_REFRESH_TOKEN_BY_USERNO, args);
		  } catch (Exception e) {
			 e.printStackTrace();
			 return 0;
		}
	  }

	  public int insertRefreshToken(String token, Long userNo) {
		  System.out.println("===> Spring JDBC로 updateRefreshToken() 기능 처리");
		  Object[] args = {userNo,token};
		  try {
			  return jdbcTemplate.update(INSERT_REFRESH_TOKEN_BY_USERNO, args);
		  } catch (Exception e) {
			 e.printStackTrace();
			 return 0;
		}
	  }
	   
	
	
//	public UserVO getUserByUserId(String userId) {
//		System.out.println("===> Spring JDBC로 getUserByUserId() 기능 처리");
//		Map<String, Object> map = new HashMap<>();
//		map.put("user_id", userId);
//		
//		return jdbcTemplate.queryForObject(SELECT_ALL_BY_USERID, map, new UserRowMapper());
//	}
	
	// 회원 등록
//	public void insertUser(UserVO vo) {
//		System.out.println("===> Spring JDBC로 insertUser() 기능 처리");
//		Map<String, Object> map = new HashMap<>();
//		map.put("user_name",vo.getUserName());
//		map.put("user_password",vo.getUserPassword());
//		map.put("user_id",vo.getUserId());
//		map.put("user_signup_type",vo.getUserSignupType());
//		
//		jdbcTemplate.update(USER_INSERT, map);
//	}
	
	class UserRowMapper implements RowMapper<UserVO> {
		public UserVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserVO vo = new UserVO();
			vo.setUserNo(rs.getLong("USER_NO"));
			vo.setUserName(rs.getString("USER_NAME"));
			vo.setUserPassword(rs.getString("USER_PASSWORD"));
			vo.setUserId(rs.getString("USER_ID"));
			vo.setUserImageUrl(rs.getString("USER_IMAGE_URL"));
			vo.setUserIntroduction(rs.getString("USER_INTRODUCTION"));
			vo.setUserSignupType(rs.getByte("USER_SIGNUP_TYPE"));
			return vo;
		}
	}
	
	class RefreshTokenRowMapper implements RowMapper<TokenDTO> {
		public TokenDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			TokenDTO tokenDTO = new TokenDTO();
			tokenDTO.setRefreshToken(rs.getString("VALUE"));
			return tokenDTO;
		}
	}
}
