package com.my.sns.user;

import java.util.List;

import com.my.sns.friend.FriendListEntity;
import com.my.sns.friend.FriendRequestListEntity;
import com.my.sns.security.service.UserDbService;

public interface UserService extends UserDbService {

	/*
	 * // CRUD 기능의 메소드 구현 // 회원 조회 UserVO getUser(UserVO vo);
	 * 
	 * // 회원 등록 void insertUser(UserVO vo);
	 */
	
//	void insertUser (UserVO userVO, boolean admin);

	UserVO getUserByUserId(String loginId);
	UserVO getUserByUserNo(Long userNo);
	public List<UserVO> searchUserName(String query);

}