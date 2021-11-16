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
//	JdbcTemplate jdbcTemplateInstance;
//	@Autowired
//	NamedParameterJdbcTemplate jdbcTemplate;

	// SQL 명령어들
	private final String USER_INSERT = "insert into user(user_name, user_password, user_id, user_signup_type) values(?,?,?,?)";
//	private final String USER_INSERT = "insert into user(user_name, user_password, user_id, user_signup_type)" + "VALUES (:user_name, :user_password, :user_id, :user_signup_type);";
	public  final String SELECT_ALL_BY_USERID = "SELECT * FROM user WHERE user_id = :user_id";
//	private final String USER_GET = "select * from spring_sns.user where user_id =?";  aws 서버에있는 db용
	private final String USER_GET_BY_USERID = "select * from user where user_id =?;";
	private final String USER_GET_BY_USERNO = "select u.user_no, u.user_name, u.user_password, u.user_id, u.user_image_url, u.user_introduction, u.user_signup_type, tk.access_token_value, tk.refresh_token_value from user u join token tk on u.user_no = tk.user_no where u.user_no = ?;";
	private final String SELECT_USER_NAME_BY_SEARCH = "select * from user where user_name like concat('%', ? ,'%')";
	private final String IS_TOKEN_BY_USERNO = "select count(*) from token where user_no = ?;";
	private final String SELECT_TOKEN_BY_USERNO = "select refresh_token_value, refresh_token_secret_key, access_token_value, access_token_secret_key from token where user_no = ?;";
	private final String UPDATE_REFRESH_TOKEN_BY_USERNO = "update token set user_no = ?, refresh_token_value = ?, refresh_token_secret_key = ? where user_no = ?;";
	private final String DELETE_REFRESH_TOKEN_BY_USERNO = "delete from token where user_no = ?;";
	private final String INSERT_TOKEN = "insert into token(user_no, refresh_token_value, refresh_token_secret_key, access_token_value, access_token_secret_key, fcm_token_value) values (?,?,?,?,?,?);";
	private final String UPDATE_TOKEN = "update token set refresh_token_value = ?, refresh_token_secret_key = ?, access_token_value = ?, access_token_secret_key = ? where user_no = ?;";
	private final String UPDATE_ACCESS_TOKEN_BY_USERNO = "update token set user_no = ?, access_token_value = ?, access_token_secret_key = ? where user_no = ?;";
	private final String SELECT_FCM_TOKEN_BY_USERNO = "select fcm_token_value from token where user_no = ?;";
	private final String UPDATE_FCM_TOKEN_BY_USERNO = "update token set fcm_token_value = ? where user_no = ?;";
	private final String IS_FCM_TOKEN_BY_USER_NO = "select count(fcm_token_value) from token where user_no = ?;";
	
	// CRUD 기능의 메소드 구현
	// 회원 조회
	
	  public UserVO getUserByUserId(String userId) {
		  System.out.println("===> Spring JDBC로 getUserByUserId() 기능 처리");
		  Object[] args = {userId}; 
		  try {
			  return jdbcTemplate.queryForObject(USER_GET_BY_USERID, args, new UserRowMapper());
		  } catch (Exception e) {
			 e.printStackTrace();
			 return null;
		}
	  }
	  
	  public UserVO getUserByUserNo(Long userNo) {
		  System.out.println("===> Spring JDBC로 getUserByUserNo() 기능 처리");
//		  jdbcTemplateInstance = new JdbcTemplate();
//		  jdbcTemplateInstance.getDataSource();
		  Object[] args = {userNo}; 
		  try {
			  return jdbcTemplate.queryForObject(USER_GET_BY_USERNO, args, new UserEntityRowMapper());
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
	  
	  public int updateFcmToken(String token, Long userNo) {
		  System.out.println("===> Spring JDBC로 updateFcmToken() 기능 처리");
		  Object[] args = {token, userNo};
		  try {
			  return jdbcTemplate.update(UPDATE_FCM_TOKEN_BY_USERNO, args);
		  } catch (Exception e) {
			 e.printStackTrace();
			 return 0;
		}
	  }
	  
	  public String getFcmTokenByUserNo(Long userNo) {
		  System.out.println("===> Spring JDBC로 getFcmTokenByUserNo() 기능 처리");
		  Object[] args = {userNo};
		  try {
			  return jdbcTemplate.queryForObject(SELECT_FCM_TOKEN_BY_USERNO, args, String.class);
		  } catch (Exception e) {
			 e.printStackTrace();
			 return null;
		}
	  }
	  
	  public int isFcmTokenByUserNo(Long userNo) {
		  System.out.println("===> Spring JDBC로 isFcmTokenByUserNo() 기능 처리");
		  Object[] args = {userNo};
		  try {
			  return jdbcTemplate.queryForObject(IS_FCM_TOKEN_BY_USER_NO, args, Integer.class);
		  } catch (Exception e) {
			 e.printStackTrace();
			 return -1;
		}
	  }
	  
	  public int isIssuedTokenByUserNo(Long userNo) {
		  System.out.println("===> Spring JDBC로 isIssuedTokenByUserNo() 기능 처리");
		  Object[] args = {userNo};
		  try {
			  return jdbcTemplate.queryForObject(IS_TOKEN_BY_USERNO, args, Integer.class);
		  } catch (Exception e) {
			 e.printStackTrace();
			 return -1;
		}
	  }
	  
	  public TokenDTO getTokenByUserNo(Long userNo) {
		  System.out.println("===> Spring JDBC로 getTokenByUserNo() 기능 처리");
		  Object[] args = {userNo};
		  try {
			  return jdbcTemplate.queryForObject(SELECT_TOKEN_BY_USERNO, args, new TokenRowMapper());
		  } catch (Exception e) {
			 e.printStackTrace();
			 return null;
		}
	  }
	  
	  public int updateRefreshToken(String token, Long userNo, String key) {
		  System.out.println("===> Spring JDBC로 updateRefreshToken() 기능 처리");
		  Object[] args = {userNo, token, key, userNo};
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
	  
	  public int insertToken(String accessToken, String accessKey, String refreshToken, String refreshKey, Long userNo, String fcmToken) {
		  System.out.println("===> Spring JDBC로 insertToken() 기능 처리");
		  Object[] args = {userNo, refreshToken, refreshKey, accessToken, accessKey, fcmToken};
		  try {
			  return jdbcTemplate.update(INSERT_TOKEN, args);
		  } catch (Exception e) {
			 e.printStackTrace();
			 return 0;
		}
	  }
	  
	  public int updateToken(String accessToken, String accessKey, String refreshToken, String refreshKey, Long userNo) {
		  System.out.println("===> Spring JDBC로 updateToken() 기능 처리");
		  Object[] args = {refreshToken, refreshKey, accessToken, accessKey, userNo};
		  try {
			  return jdbcTemplate.update(UPDATE_TOKEN, args);
		  } catch (Exception e) {
			 e.printStackTrace();
			 return 0;
		}
	  }
	  
	  public int updateAccessToken(String token, Long userNo, String key) {
		  System.out.println("===> Spring JDBC로 updateAccessToken() 기능 처리");
		  Object[] args = {userNo, token, key, userNo};
		  try {
			  return jdbcTemplate.update(UPDATE_ACCESS_TOKEN_BY_USERNO, args);
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
	public void insertUser(UserVO vo) {
		System.out.println("===> Spring JDBC로 insertUser() 기능 처리");
		Map<String, Object> map = new HashMap<>();
		map.put("user_name",vo.getUserName());
		map.put("user_password",vo.getUserPassword());
		map.put("user_id",vo.getUserId());
		map.put("user_signup_type",vo.getUserSignupType());
		
		Object[] args = {vo.getUserName(), vo.getUserPassword(), vo.getUserId(), vo.getUserSignupType()};
		
		jdbcTemplate.update(USER_INSERT, args);
	}
	
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
	
	class UserEntityRowMapper implements RowMapper<UserVO> {
		public UserVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserVO vo = new UserVO();
			vo.setUserNo(rs.getLong("USER_NO"));
			vo.setUserName(rs.getString("USER_NAME"));
			vo.setUserPassword(rs.getString("USER_PASSWORD"));
			vo.setUserId(rs.getString("USER_ID"));
			vo.setUserImageUrl(rs.getString("USER_IMAGE_URL"));
			vo.setUserIntroduction(rs.getString("USER_INTRODUCTION"));
			vo.setUserSignupType(rs.getByte("USER_SIGNUP_TYPE"));
			vo.setAccessToken(rs.getString("ACCESS_TOKEN_VALUE"));
			vo.setRefreshToken(rs.getString("REFRESH_TOKEN_VALUE"));
			return vo;
		}
	}
	
	class TokenRowMapper implements RowMapper<TokenDTO> {
		public TokenDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			TokenDTO tokenDTO = new TokenDTO();
			tokenDTO.setRefreshToken(rs.getString("REFRESH_TOKEN_VALUE"));
			tokenDTO.setRefreshTokenSecretKey(rs.getString("REFRESH_TOKEN_SECRET_KEY"));
			tokenDTO.setAccessToken(rs.getString("ACCESS_TOKEN_VALUE"));
			tokenDTO.setAccessTokenSecretKey(rs.getString("ACCESS_TOKEN_SECRET_KEY"));
			return tokenDTO;
		}
	}
}
