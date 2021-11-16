package com.my.sns.chat.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.my.sns.chat.ChatMsgDTO;
import com.my.sns.chat.ChatRoomDTO;
import com.my.sns.chat.ChatRoomService;
import com.my.sns.chat.EchoModel;
import com.my.sns.common.FcmUtil;
import com.my.sns.security.entity.CustomUserDetails;
import com.my.sns.user.UserService;
import com.my.sns.user.impl.UserDAO;

@Service
public class SocketService {

    @Autowired
    private SimpMessagingTemplate simpTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private ChatRoomService chatRoomService;
    
    Long myUserNo;

    public void echoMessage(String message, List<Long> userNoList) {
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof CustomUserDetails) {
			myUserNo = ((CustomUserDetails)principal).getUserNo();
			System.out.println("myUserNo : " + myUserNo);
		}
    	
        System.out.println("Start convertAndSend ${new Date()}");
        for(Long userNo : userNoList) {
        	if(myUserNo != userNo)
        		simpTemplate.convertAndSend("/topic/greetings" + userNo, new EchoModel(message));
        }
        System.out.println("End convertAndSend ${new Date()}");
    }
    
    public void echoMessage(ChatMsgDTO chatMsgDTO) {
    	FcmUtil fcmUtil = new FcmUtil();
    	final SimpleDateFormat mTimeFormat = new SimpleDateFormat("a hh:mm", Locale.getDefault());
        final SimpleDateFormat mTimeFormatText = new SimpleDateFormat("yyyy년 MMM dd일 E요일", Locale.getDefault());
    	String roomId = chatMsgDTO.getRoomId();
    	chatMsgDTO.setTime(mTimeFormat.format(new Date()));
    	
    	List<String> tokenList = userService.getFcmTokens(roomId);
    	List<ChatRoomDTO> roomList = chatRoomService.findChatRoomInfo(roomId);
    	
        System.out.println("Start convertAndSend ${new Date()}");
        simpTemplate.convertAndSend("/topic/" + roomId, chatMsgDTO);
        System.out.println("End convertAndSend ${new Date()}");
        System.out.println("user 이미지 : " + chatMsgDTO.getImageUrl());
        
        fcmUtil.sendAllChatFcm(tokenList, chatMsgDTO, roomList);
    }
}
