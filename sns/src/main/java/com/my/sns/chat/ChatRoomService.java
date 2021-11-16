package com.my.sns.chat;

import java.util.List;

public interface ChatRoomService {
//	public List<ChatRoomDTO> findAllChatRoom(Long userNo);
	public List<Long> findAllChatRoomUserNo(String roomId);
	public boolean setChatRoom(List<ChatRoomDTO> dataList);
	public List<ChatRoomDTO> findChatRoomInfo(String roomId);

}
