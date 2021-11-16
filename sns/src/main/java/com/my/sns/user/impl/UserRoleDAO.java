package com.my.sns.user.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.my.sns.user.UserRoleVO;
import com.my.sns.user.UserVO;

@Repository("userRoleDAO")
public class UserRoleDAO {
//	@Autowired
//	NamedParameterJdbcTemplate jdbcTemplate;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public final String SELECT_ALL_BY_USERID = "SELECT ur.user_role_no, ur.user_no, ur.user_role_name FROM spring_sns.user_role ur JOIN spring_sns.user u ON ur.user_no = u.user_no WHERE u.user_id = ?";
//	public final String SELECT_ALL_BY_USERID = "SELECT ur.user_role_no, ur.user_no, ur.user_role_name FROM user_role ur JOIN user u ON ur.user_no = u.user_no WHERE u.user_id = :user_id";
<<<<<<< HEAD
	public final String INSERT_ADMIN_ROLE = "INSERT INTO spring_sns.user_role(user_no, user_role_name) " + "VALUES (:userNo, \"ROLE_ADMIN\");";
	public final String INSERT_USER_ROLE = "INSERT INTO spring_sns.user_role(user_no, user_role_name) " + "VALUES (:userNo, \"ROLE_USER\");";
=======
	public final String INSERT_ADMIN_ROLE = "INSERT INTO user_role(user_no, user_role_name) " + "VALUES (:userNo, \"ROLE_ADMIN\");";
//	public final String INSERT_USER_ROLE = "INSERT INTO user_role(user_no, user_role_name) " + "VALUES (:userNo, \"ROLE_USER\");";
	private final String INSERT_USER_ROLE = "INSERT INTO user_role(user_no, user_role_name) VALUES(?, \"ROLE_USER\");";
>>>>>>> b5ca04ae10957658703fcdcbce9b799bac999086
	
//	public List<UserRoleVO> getRolesByUserId(String userId){
//		System.out.println("===> Spring JDBC로 getRolesByUserId() 기능 처리");
//		Map<String, Object> map = new HashMap<>();
//		map.put("user_id", userId);
//
//		return jdbcTemplate.query(SELECT_ALL_BY_USERID, map, new UserRoleRowMapper());
//	}
	
	public List<UserRoleVO> getRolesByUserId(String userId){
		System.out.println("===> Spring JDBC로 getRolesByUserId() 기능 처리");
		Object[] args = {userId};

		return jdbcTemplate.query(SELECT_ALL_BY_USERID, args, new UserRoleRowMapper());
	}

	public void addAdminRole(Long userNo) {
		Map<String, Object> params = Collections.singletonMap("userNo", userNo);
		jdbcTemplate.update(INSERT_ADMIN_ROLE, params);
	}

	public void addUserRole(Long userNo) {
//		Map<String, Object> params = Collections.singletonMap("userNo", userNo);
		Object[] args = {userNo};
		jdbcTemplate.update(INSERT_USER_ROLE, args);
	}
	
	class UserRoleRowMapper implements RowMapper<UserRoleVO> {
		public UserRoleVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserRoleVO vo = new UserRoleVO();
			vo.setUserRoleNo(rs.getLong("USER_ROLE_NO"));
			vo.setUserNo(rs.getLong("USER_NO"));
			vo.setUserRoleName(rs.getString("USER_ROLE_NAME"));
			return vo;
		}
	}
}
