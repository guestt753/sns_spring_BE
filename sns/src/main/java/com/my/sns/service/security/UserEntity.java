package com.my.sns.service.security;

public class UserEntity {
    private String loginUserId;
    private String password;
    private Long userNo;
    
    

    public UserEntity() {
		super();
	}

	public UserEntity(String loginUserId, String password, Long userNo) {
        this.loginUserId = loginUserId;
        this.password = password;
        this.userNo = userNo;
    }

    public String getLoginUserId() {
        return loginUserId;
    }

    public void setLoginUserId(String loginUserId) {
        this.loginUserId = loginUserId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public Long getUserNo() {
		return userNo;
	}

	public void setUserNo(Long userNo) {
		this.userNo = userNo;
	}
    
    
}