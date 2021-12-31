package com.my.sns.chat.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.my.sns.chat.ChatRoomDTO;

@Repository
public class ChatRoomDAO {
	@Autowired
	JdbcTemplate jdbcTemplate;
	private Map<String, ChatRoomDTO> chatRoomMap;
	
	private final String SELECT_USERNO_BY_ROOMID = "select user_no from chatroom where chatroom_no = ?;";
	private final String INSERT_CHATROOM = "insert into chatroom values(?,?,?,?,?,?);";
	private final String SELECT_CHATROOM_INFO_BY_ROOMID = "select * from chatroom where chatroom_no = ?;";
	
	//AWS 전용 SQL문
	private final String AWS_SELECT_USERNO_BY_ROOMID = "select spring_sns.user_no from chatroom where chatroom_no = ?;";
	private final String AWS_INSERT_CHATROOM = "insert into  spring_sns.chatroom values(?,?,?,?,?,?);";
	private final String AWS_SELECT_CHATROOM_INFO_BY_ROOMID = "select * from  spring_sns.chatroom where chatroom_no = ?;";
	
//	@PostConstruct
//	private void init() {
//		chatRoomMap = new LinkedHashMap<String, ChatRoomDTO>();
//	}

	public List<ChatRoomDTO> getUsersByRoomId(String roomId) {
		  System.out.println("===> Spring JDBC로 getUsersByRoomId() 기능 처리");
		  Object[] args = {roomId};
		  try {
			  return jdbcTemplate.query(AWS_SELECT_USERNO_BY_ROOMID, args, new UserNoListRowMapper());
		  } catch (Exception e) {
			 e.printStackTrace();
			 return null;
		}
	  }
	
	public int insertChatRoom(String roomId, Long userNo, String roomName, String myImageUrl, int participantsNo, String userName) {
		System.out.println("===> Spring JDBC로 insertChatRoom() 기능 처리");
		Object[] args = {roomId, userNo, roomName, myImageUrl, participantsNo, userName};
		try {
			return jdbcTemplate.update(AWS_INSERT_CHATROOM, args);
		} catch (Exception e) {
			 e.printStackTrace();
			 return 0;
		}
	}
	
	public List<ChatRoomDTO> getChatRoomInfo(String roomId) {
		System.out.println("===> Spring JDBC로 getChatRoomInfo() 기능 처리");
		Object[] args = {roomId};
		try {
			  return jdbcTemplate.query(AWS_SELECT_CHATROOM_INFO_BY_ROOMID, args, new ChatRoomRowMapper());
		  } catch (Exception e) {
			 e.printStackTrace();
			 return null;
		}
	}
	
	// 채팅방 순서 최근순으로 반환
	public List<ChatRoomDTO>  findAllRooms() {
		List<ChatRoomDTO> result = new ArrayList<ChatRoomDTO>(chatRoomMap.values());
		Collections.reverse(result);
		
		return result;
	}
	
	public ChatRoomDTO findRoomById(String id) {
		return chatRoomMap.get(id);
	}
	
//	public ChatRoomDTO createChatRoomDTO(String name) {
//		ChatRoomDTO room = ChatRoomDTO.create(name);
//		chatRoomMap.put(room.getRoomId(), room);
//		
//		return room;
//	}
	
	class UserNoListRowMapper implements RowMapper<ChatRoomDTO> {
		public ChatRoomDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			ChatRoomDTO vo = new ChatRoomDTO();
			vo.setUserNo(rs.getLong("USER_NO"));
			return vo;
		}
	}
	
	class ChatRoomRowMapper implements RowMapper<ChatRoomDTO> {
		public ChatRoomDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			ChatRoomDTO vo = new ChatRoomDTO();
			vo.setRoomNo(rs.getString("CHATROOM_NO"));
			vo.setUserNo(rs.getLong("USER_NO"));
			vo.setRoomName(rs.getString("CHATROOM_NAME"));
			vo.setMyImageUrl(rs.getString("MY_IMAGE_URL"));
			vo.setParticipantNumbers(rs.getInt("PARTICIPANTS_NO"));
			vo.setUserName(rs.getString("USER_NAME"));
			return vo;
		}
	}
	
}
