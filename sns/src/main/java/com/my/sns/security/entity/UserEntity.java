package com.my.sns.security.entity;

public class UserEntity {
    private String loginUserId;
    private String password;
    private String userName;
    private Long userNo;
    private String userImageUrl;
    private String userIntroduction;
    private String accessToken;
    private String refreshToken;
    
    

    public UserEntity() {
		super();
	}
    
    
	
    public UserEntity(String loginUserId, String password, String userName, Long userNo, String userImageUrl,
			String userIntroduction) {
		super();
		this.loginUserId = loginUserId;
		this.password = password;
		this.userName = userName;
		this.userNo = userNo;
		this.userImageUrl = userImageUrl;
		this.userIntroduction = userIntroduction;
	}



	public UserEntity(String loginUserId, String password, String userName, Long userNo, String userImageUrl, String userIntroduction,
			String accessToken, String refreshToken) {
		super();
		this.loginUserId = loginUserId;
		this.password = password;
		this.userName = userName;
		this.userNo = userNo;
		this.userImageUrl = userImageUrl;
		this.userIntroduction = userIntroduction;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserImageUrl() {
		return userImageUrl;
	}

	public void setUserImageUrl(String userImageUrl) {
		this.userImageUrl = userImageUrl;
	}

	public String getUserIntroduction() {
		return userIntroduction;
	}

	public void setUserIntroduction(String userIntroduction) {
		this.userIntroduction = userIntroduction;
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