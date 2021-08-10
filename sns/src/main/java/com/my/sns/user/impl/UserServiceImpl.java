package com.my.sns.user.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.sns.service.security.UserEntity;
import com.my.sns.service.security.UserRoleEntity;
import com.my.sns.user.UserRoleVO;
import com.my.sns.user.UserService;
import com.my.sns.user.UserVO;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private UserRoleDAO userRoleDAO;

	@Override
	@Transactional
	public UserEntity getUser(String loginUserId) {
		UserVO userVO = userDAO.getUserByUserId(loginUserId);
        return new UserEntity(userVO.getUserId(), userVO.getUserPassword(), userVO.getUserNo());
	}

	@Override
	@Transactional
	public List<UserRoleEntity> getUserRoles(String loginUserId) {
		List<UserRoleVO> userRoles = userRoleDAO.getRolesByUserId(loginUserId);
        List<UserRoleEntity> list = new ArrayList<>();

        for(UserRoleVO userRole : userRoles) {
            list.add(new UserRoleEntity(loginUserId, userRole.getUserRoleName()));
        }
        return list;
	}
	
	//검색 결과 반환(userName, userNo, userImageUrl)
	@Override
	@Transactional
	public List<UserVO> searchUserName(String searchText) {
		List<UserVO> searchList = userDAO.getUserNameBySearch(searchText);
		
		return searchList;
	}
	

//	@Override
//	@Transactional(readOnly = false)
//	public void insertUser(UserVO userVO, boolean admin) {
//		userVO.setUserPassword(encoder.encode(userVO.getUserPassword()));
//		userDAO.insertUser(userVO);
//		
//		UserVO selectedMember = userDAO.getUserByUserId(userVO.getUserId());
//		Long userNo = selectedMember.getUserNo();
//		if(admin) {
//			userRoleDAO.addAdminRole(userNo);
//		}
//		userRoleDAO.addUserRole(userNo);
//	}

	@Override
	public UserVO getUserByUserId(String loginId) {
		return userDAO.getUserByUserId(loginId);
	}
	
	
	
	
	
}
