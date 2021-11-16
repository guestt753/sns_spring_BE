package com.my.sns.user.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.sns.chat.ChatRoomDTO;
import com.my.sns.chat.impl.ChatRoomDAO;
import com.my.sns.security.entity.CustomUserDetails;
import com.my.sns.security.entity.UserEntity;
import com.my.sns.security.entity.UserRoleEntity;
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
	private ChatRoomDAO chatRoomDAO;
	@Autowired
	private UserRoleDAO userRoleDAO;
	
	//유저가 토큰을 발급받았으면 true 반환..
	@Override
	public boolean isIssuedToken(Long userNo) {
//		boolean result = false;
		int count = userDAO.isIssuedTokenByUserNo(userNo);
		
		if(count == -1) 
			throw new RuntimeException("==JDBC 오류==");
	
		return count > 0 ? true : false;
//		if(count > 0) 
//			result = true;
//		return result;
	}

	@Override
	@Transactional
	public UserEntity getUser(String loginUserId) {
		UserVO userVO = userDAO.getUserByUserId(loginUserId);
        return new UserEntity(userVO.getUserId(), userVO.getUserPassword(), userVO.getUserName(), userVO.getUserNo(),userVO.getUserImageUrl(), userVO.getUserIntroduction());
	}

	@Override
	@Transactional
	public UserEntity getUser(Long userNo) {
		UserVO userVO = userDAO.getUserByUserNo(userNo);
		return new UserEntity(userVO.getUserId(), userVO.getUserPassword(), userVO.getUserName(), userVO.getUserNo(),userVO.getUserImageUrl(), userVO.getUserIntroduction(), userVO.getAccessToken(), userVO.getRefreshToken());
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
	
	@Override
	@Transactional
	public boolean updateFcmToken(String token, Long userNo) {
		int result = -1;
		
		result = userDAO.updateFcmToken(token, userNo);
		if(result == 1)
			return true;
		else
			return false;
	}
	
	@Override
	@Transactional
	public List<String> getFcmTokens(String roomId) {
		List<ChatRoomDTO> userNoList;
		List<String> tokenList = new ArrayList<String>();
		userNoList = chatRoomDAO.getUsersByRoomId(roomId);
		
		Long myNo = 0L;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof CustomUserDetails) {
			myNo = ((CustomUserDetails)principal).getUserNo();
		}
		
		for (ChatRoomDTO chatRoomDTO : userNoList) {
			if(!myNo.equals(chatRoomDTO.getUserNo()))
				tokenList.add(userDAO.getFcmTokenByUserNo(chatRoomDTO.getUserNo()));
		}
		return tokenList;
	}
	

	@Override
	@Transactional(readOnly = false)
	public boolean insertUser(UserVO userVO, boolean admin) {
		userVO.setUserPassword(encoder.encode(userVO.getUserPassword()));
		
		try {
		userDAO.insertUser(userVO);
		UserVO selectedMember = userDAO.getUserByUserId(userVO.getUserId());
		Long userNo = selectedMember.getUserNo();
		if(admin) {
			userRoleDAO.addAdminRole(userNo);
		} else
			userRoleDAO.addUserRole(userNo);
		} catch (Exception e) {
			 e.printStackTrace();
			 return false;
		}
		return true;
	}

	@Override
	public UserVO getUserByUserId(String loginId) {
		return userDAO.getUserByUserId(loginId);
	}

	@Override
	public UserVO getUserByUserNo(Long userNo) {
		return userDAO.getUserByUserNo(userNo);
	}
	
	
	
	
	
}
