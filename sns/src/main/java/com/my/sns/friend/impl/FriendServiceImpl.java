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
	
	@Override
	@Transactional
	public int isFriendRequestOrFriend(Long userNo, Long myUserNo) {
		int isFriend = friendDAO.isMyFriendByUserNo(myUserNo, userNo);
		if(isFriend == 1) { //친구일 경우
			return 1;
		} else if(isFriend == 0) {
			int isFriendRequest = friendDAO.isFriendRequestByUserNo(myUserNo, userNo);
			if(isFriendRequest == 2) { // 내가 친구요청한 경우
				return 3;
			} else if(isFriendRequest == 1) { // 나에게 친구요청을 했는지
				return 2;
			} else if(isFriendRequest == 0) {
				return 0;
			} else {
				throw new RuntimeException("DB(friend_request)에러");
			}
		} else {
			throw new RuntimeException("DB(friend)에러");
		}
	}
	
	// 친구인 userNo,status을 반환..
		@Override
		@Transactional
		public List<FriendListEntity> getFriendList(Long userNo1, Long userNo2) {
			List<FriendListEntity> list = friendDAO.getFriendListByUserNo(userNo1, userNo2);
			if(list == null) return list; // 검색 결과 없음 혹은 오류
			
			List<FriendListEntity> friendList = new ArrayList<>();
			
			for(FriendListEntity entity : list) {
				if(entity.getUserNo() != userNo1) {
					System.out.println("add전 친구 : " + entity.getUserName());
					friendList.add(new FriendListEntity(entity.getUserNo(), entity.getUserName(), entity.getUserImageUrl(), entity.getFriendStatus()));
				}
			}
			
			return friendList;
		}
		
		@Override
		@Transactional
		public List<FriendRequestListEntity> getFriendRequestList(Long userNo) {
			List<FriendRequestListEntity> friendRequestListEntities = friendDAO.getFriendRequestByUserNo(userNo);
			int resultSize = friendRequestListEntities.size();
//			List<FriendRequestListEntity> list = new ArrayList<FriendRequestListEntity>();
//			
//			for(FriendRequestListEntity entity : friendRequestListEntities) {
//				list.add(new FriendRequestListEntity(entity.getUserName(), entity.getUserImageUrl()));
//			}
	        if(resultSize != 0)
	        	return friendRequestListEntities;
	        return null;
		}
		
		@Override
		@Transactional
		public int acceptOrRefuseFriendRequest(Long userNo, Long requestedUserNo, final int type) {
			final int REFUSE_TYPE = 0;
			if(type == 0) {
				return friendDAO.friendRequestDeleteAction(userNo, requestedUserNo, REFUSE_TYPE); // 거절 성공적으로 수행시 1 리턴, 실패 0리턴
			}
			else {
				return friendDAO.friendRequestDeleteAction(userNo, requestedUserNo, REFUSE_TYPE) + friendDAO.insertFriend(userNo, requestedUserNo); // 성공시 3리턴, friendRequestAction 실패시 2리턴, friendInsert 실패시 1리턴, 전부 실패 0리턴
			}
		}
		
		@Override
		@Transactional
		public boolean friendRequestAction(Long myUserNo, Long userNo, final int type) {
			final int REFUSE_TYPE = 1;
			if(type == 0) {
				int result = friendDAO.friendRequestInsertAction(userNo, myUserNo);
				if(result == 1) return true;
				return false;
			}else {
				int result = friendDAO.friendRequestDeleteAction(myUserNo, userNo, REFUSE_TYPE);
				if(result == 1) return true;
				return false;
			}
		}
		
		@Override
		@Transactional
		public boolean deleteFriend(Long myUserNo, Long userNo) {
			int result = friendDAO.deleteFriend(myUserNo, userNo);
			System.out.println("결과값 : " + result);
			if(result == 2) return true;
			return false;
		}
		
		@Override
		public List<FriendListEntity> searchFriend(List<FriendListEntity> friendList, String searchText) {
			List<FriendListEntity> resultList = new ArrayList<FriendListEntity>();
			boolean isadded = false;
			
			for(FriendListEntity entity : friendList) {
				if(entity.getUserName().contains(searchText)) {
					resultList.add(new FriendListEntity(entity.getUserNo(), entity.getUserName(), entity.getUserImageUrl(), entity.getFriendStatus()));
					isadded = true;
				}
			}
			if(isadded)
				return resultList;
			else
				return null;
		}
}
