package com.my.sns.security.controller;

import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.my.sns.chat.ChatMsgDTO;
import com.my.sns.chat.ChatRoomService;
import com.my.sns.chat.EchoModel;
import com.my.sns.chat.impl.SocketService;
import com.my.sns.user.UserVO;

@RestController
public class SocketController {
	@Autowired
    SocketService socketService;
	@Autowired
	ChatRoomService chatRoomService;
	
	@MessageMapping("/greeting")
	public String handle(String greeting) {
		return "[" + TimeZone.getTimeZone(greeting) + ": " + greeting;
	}

    @MessageMapping("/hello-msg-mapping")
    @SendTo("/topic/greetings")
    public EchoModel echoMessageMapping(String message) {
        System.out.println("React to hello-msg-mapping");
        return new EchoModel(message.trim());
    }

    @ResponseBody
    @RequestMapping(value = "/hello-convert-and-send", method=RequestMethod.POST)
    public void echoConvertAndSend(String objJson) {
    	Gson gson = new Gson();
    	ChatMsgDTO chatMsgDTO = new ChatMsgDTO();
		try {
			
			System.out.println("josn : " + objJson);
			chatMsgDTO = gson.fromJson(objJson, ChatMsgDTO.class);			
		} catch (Exception e) {
			 e.printStackTrace();
			 System.out.println("json 변환오류");
		}
		
    	System.out.println(chatMsgDTO);
        socketService.echoMessage(chatMsgDTO);
    }
    
//    @RequestMapping(value = "/hello-convert-and-send", method=RequestMethod.POST)
//    public void echoConvertAndSend(@RequestParam("msg") String message, @RequestParam("roomId") String roomId) {
//    	List<Long> userNoList = chatRoomService.findAllChatRoomUserNo(roomId);
//        socketService.echoMessage(message, userNoList);
//        // chatroom Id 와 일치하는 사람들에게 보내기(본인 제외)
//    }
}
