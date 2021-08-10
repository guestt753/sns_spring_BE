package com.my.sns.friend;

import java.util.List;

public interface FriendService {
	public List<FriendListEntity> getFriendList(Long userNo1, Long userNo2);
	public List<FriendRequestListEntity> getFriendRequestList(Long userNo);
	public int acceptOrRefuseFriendRequest(Long userNo, Long requestedUserNo, int type);
}
