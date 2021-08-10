package com.my.sns.friend.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.sns.friend.FriendListEntity;
import com.my.sns.friend.FriendRequestListEntity;
import com.my.sns.friend.FriendService;

@Service("friendService")
public class FriendServiceImpl implements FriendService {
	@Autowired
	private FriendDAO friendDAO;
	
	// 친구인 userNo,status을 반환..
		@Override
		@Transactional
		public List<FriendListEntity> getFriendList(Long userNo1, Long userNo2) {
			List<FriendListEntity> list = friendDAO.getFriendListByUserNo(userNo1, userNo2);
			
			List<FriendListEntity> friendList = new ArrayList<FriendListEntity>();
			
			for(FriendListEntity entity : list) {
				if(entity.getUserOneNo() != userNo1)
					friendList.add(new FriendListEntity(entity.getUserOneNo(), entity.getFriendStatus()));
			}
			
			return friendList;
		}
		
		@Override
		@Transactional
		public List<FriendRequestListEntity> getFriendRequestList(Long userNo) {
			List<FriendRequestListEntity> friendRequestListEntities = friendDAO.getFriendRequestByUserNo(userNo);
//			List<FriendRequestListEntity> list = new ArrayList<FriendRequestListEntity>();
//			
//			for(FriendRequestListEntity entity : friendRequestListEntities) {
//				list.add(new FriendRequestListEntity(entity.getUserName(), entity.getUserImageUrl()));
//			}
	        
	        return friendRequestListEntities;
		}
		
		@Override
		@Transactional
		public int acceptOrRefuseFriendRequest(Long userNo, Long requestedUserNo, int type) {
			if(type == 0) {
				return friendDAO.friendRequestAction(userNo, requestedUserNo); // 거절 성공적으로 수행시 1 리턴, 실패 0리턴
			}
			else {
				return friendDAO.friendRequestAction(userNo, requestedUserNo) + friendDAO.friendInsert(userNo, requestedUserNo); // 성공시 3리턴, friendRequestAction 실패시 2리턴, friendInsert 실패시 1리턴, 전부 실패 0리턴
			}
		}

}
