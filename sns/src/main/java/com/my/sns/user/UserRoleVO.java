package com.my.sns.user;

import org.springframework.stereotype.Component;

@Component
public class UserRoleVO {
	private Long userRoleNo;
	private Long userNo;
	private String userRoleName;
	
	public UserRoleVO() {
	}

	public UserRoleVO(Long userNo, String userRoleName) {
		this.userNo = userNo;
		this.userRoleName = userRoleName;
	}

	public Long getUserRoleNo() {
		return userRoleNo;
	}

	public void setUserRoleNo(Long userRoleNo) {
		this.userRoleNo = userRoleNo;
	}

	public Long getUserNo() {
		return userNo;
	}

	public void setUserNo(Long userNo) {
		this.userNo = userNo;
	}

	public String getUserRoleName() {
		return userRoleName;
	}

	public void setUserRoleName(String userRoleName) {
		this.userRoleName = userRoleName;
	}

	
}
