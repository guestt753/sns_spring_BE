package com.my.sns.friend;

import java.util.List;

public interface FriendService {
	public List<FriendListEntity> getFriendList(Long userNo1, Long userNo2);
	public List<FriendRequestListEntity> getFriendRequestList(Long userNo);
	public int acceptOrRefuseFriendRequest(Long userNo, Long requestedUserNo, int type);
	public int isFriendRequestOrFriend(Long userNo, Long myUserNo);
	public boolean friendRequestAction(Long myUserNo, Long userNo, final int type);
	public List<FriendListEntity> searchFriend(List<FriendListEntity> friendList, String searchText);
	public boolean deleteFriend(Long myUserNo, Long userNo);
}
