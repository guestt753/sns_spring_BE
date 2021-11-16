package com.my.sns.chat.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.sns.chat.ChatRoomDTO;
import com.my.sns.chat.ChatRoomService;

@Service("ChatRoomService")
public class ChatRoomServiceImpl implements ChatRoomService {
	@Autowired
	ChatRoomDAO chatRoomDAO;

	@Override
	public List<Long> findAllChatRoomUserNo(String roomId) {
		List<ChatRoomDTO> list = chatRoomDAO.getUsersByRoomId(roomId);
		List<Long> userNoList = new ArrayList<Long>();
		
		for(ChatRoomDTO entity : list) {
			userNoList.add(entity.getUserNo());
		}
		return userNoList;
	}

	@Override
	public boolean setChatRoom(List<ChatRoomDTO> dataList) {
		int countNum = 0;
		for(ChatRoomDTO entity : dataList) {
			countNum = countNum + chatRoomDAO.insertChatRoom(entity.getRoomNo(), entity.getUserNo(), entity.getRoomName(), entity.getMyImageUrl(), entity.getParticipantNumbers(), entity.getUserName());
		}
		if(countNum == dataList.size())
			return true;
		else
			return false;
	}
	
	@Override
	public List<ChatRoomDTO> findChatRoomInfo(String roomId) {
		List<ChatRoomDTO> list = chatRoomDAO.getChatRoomInfo(roomId);
		List<ChatRoomDTO> chatRoomList = list;
		List<ChatRoomDTO> resultList = new ArrayList<ChatRoomDTO>(list.size());
		List<List<Long>> usersNumList = new ArrayList<List<Long>>();
		List<Long> userNoList = new ArrayList<>();
		List<List<String>> usersImageList = new ArrayList<List<String>>();
        List<String> userImageList = new ArrayList<>();
        ArrayList<List<String>> realmUserImageList = new ArrayList<>();
        ArrayList<List<Long>> realmNoLists = new ArrayList<>();
//        ChatRoomDTO entity = new ChatRoomDTO();
		
      
        for(ChatRoomDTO entity : list) {
        	
        	
        	userNoList.add(entity.getUserNo());
        	userImageList.add(entity.getMyImageUrl());
        	
        }
        for(ChatRoomDTO entity : list) {
        	entity.setUserImageUrl(userImageList);
        	entity.setUserNumbers(userNoList);
        	resultList.add(entity);
        }
        
//		for(ChatRoomDTO entity : chatRoomList) {
//			System.out.println(" 처음  info : " + entity.toString());
//			ChatRoomDTO resultDTO = new ChatRoomDTO();
//			resultDTO.setRoomNo(entity.getRoomNo());
//			resultDTO.setRoomName(entity.getRoomName());
//			resultDTO.setUserNo(entity.getUserNo());
//			resultDTO.setMyImageUrl(entity.getMyImageUrl());
//			resultDTO.setUserName(entity.getUserName());
//			resultDTO.setParticipantNumbers(entity.getParticipantNumbers());
////			userImageList.clear();
////			userNoList.clear();
//			for (ChatRoomDTO chatRoomDTO : list) {
//				if(entity.getUserNo() != chatRoomDTO.getUserNo()) {
//					userNoList.add(chatRoomDTO.getUserNo());
//					userImageList.add(chatRoomDTO.getMyImageUrl());
//					System.out.println("추가된 no : " + chatRoomDTO.getUserNo());
//					System.out.println("추가된 이미지 : " + chatRoomDTO.getMyImageUrl());
//				}
//			}
//			resultDTO.setUserNoList(userNoList);
//			realmNoLists.add(userNoList);
//			
//			resultDTO.setImageUrlList(userImageList);
//			realmUserImageList.add(userImageList);
//			
//			resultList.add(resultDTO);
//		
//			
//			System.out.println("추가된  info : " + entity.toString());
//			System.out.println("추가된 List : " + resultList.toString());
//			System.out.println("추가된 리스트 리스트 : " + realmNoLists.toString());
//			
//		}

        
//        userNoList = new ArrayList<>();
//        userImageList = new ArrayList<>();
        
       
        
//        ArrayList<ArrayList<String>> realmUserImageList = new ArrayList<>();
//        ArrayList<String> realmImageUrlList = new ArrayList<>();
//        ArrayList<ArrayList<Long>> realmNoLists = new ArrayList<>();
//        ArrayList<Long> realmUserNumList = new ArrayList<>();
//        for(int i = 0; i < list.size(); i++) {
//            Long key = list.get(i).getUserNo();
//            
//            realmImageUrlList.clear();
//            realmUserNumList.clear();
//            
//            for (int j = 0; j < list.size(); j++) {
//                    if(!key.equals(list.get(j).getUserNo())) {
//                        
//                        realmImageUrlList.add(list.get(j).getMyImageUrl());
//                        
//                        realmUserNumList.add(list.get(j).getUserNo());
//                        
//                     
//                        
//                    }
//                    
//                }
//           
//           
//            userNoList.add(key);
//
//            System.out.println("완성된 imageList : " + realmImageUrlList);
//            realmUserImageList.add(i,realmImageUrlList);
//        
//
//            System.out.println("완성된 userNoList : "+ realmUserNumList);
//            realmNoLists.add(i,realmUserNumList);
//            
//            System.out.println("완성된 리스트 : " + realmNoLists);          
//
//            
//        }
//        
//        for(int i = 0; i < list.size(); i++) {
//            for(int j = 0; j < list.size(); j++) {
//                if (list.get(j).getUserNo() == userNoList.get(i)) {
//                	System.out.println("for문 밖 완성된 리스트 : " + realmNoLists.get(i));
//                	list.get(j).setImageUrlList(realmUserImageList.get(i));
//                	list.get(j).setUserNoList(realmNoLists.get(i));
//                	
//                    break;
//                }
//            }
//        }
        
		int j = 0;
		for(ChatRoomDTO dto : resultList) {
			System.out.println("리턴하기 전 info : " + resultList.get(j).getUserImageUrl());
			System.out.println("리턴하기 전 info : " + resultList.get(j).getUserNumbers());
			j++;
		}
		return resultList;
		
	}
}
