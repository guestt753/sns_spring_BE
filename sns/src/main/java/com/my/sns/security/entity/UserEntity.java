package com.my.sns.security.entity;

public class UserEntity {
    private String loginUserId;
    private String password;
    private Long userNo;
    private String accessToken;
    private String refreshToken;
    
    

    public UserEntity() {
		super();
	}

	public UserEntity(String loginUserId, String password, Long userNo, String accessToken, String refreshToekn) {
        this.loginUserId = loginUserId;
        this.password = password;
        this.userNo = userNo;
        this.accessToken = accessToken;
        this.refreshToken = refreshToekn;
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

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
    
    
}