package com.my.sns.common;

import java.io.FileInputStream;
import java.util.List;

import org.springframework.stereotype.Component;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.gson.Gson;
import com.my.sns.chat.ChatMsgDTO;
import com.my.sns.chat.ChatRoomDTO;

@Component
public class FcmUtil {
	
	public void sendFcm(String token, String title, String content ) {
		try {
			FileInputStream refreshToken = new FileInputStream("C:\\jsp\\pjt_study\\sns\\src\\main\\resources\\config\\sns-cloud-8f314-firebase-adminsdk-oudr4-ecdc782652.json");
			
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(refreshToken))
					.build();
			
			// 처음 호출시에 초기화 처리
			if (FirebaseApp.getApps().isEmpty()) {
				FirebaseApp.initializeApp(options);
			}
			
			String registrationToken = token;
			
			// message 작성
			Message message = Message.builder()
					.setAndroidConfig(AndroidConfig.builder()
							.setTtl(3600 * 1000) // 유효기간 1시간... 기본값 4주
							.setPriority(AndroidConfig.Priority.HIGH)
							.setNotification(AndroidNotification.builder()
									.setTitle(title)
									.setBody(content)
									.setTag(content)
//									.setIcon("stock_ticker_update")
									.setColor("#98FF98")
									.setNotificationCount(0)
									.build())
							.build())
					.putData("title", "FG title")
					.putData("message", "foreground message")
					.setToken(registrationToken)
					.build();
			
			// 메시지를 FirebaseMessaging에 보내기
			String response = FirebaseMessaging.getInstance().send(message);
			System.out.println("Successfully sent message : " + response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendAllChatFcm(List<String> token, ChatMsgDTO chatMsgDTO, List<ChatRoomDTO> roomList ) {
		try {
			FileInputStream refreshToken = new FileInputStream("C:\\jsp\\pjt_study\\sns\\src\\main\\resources\\config\\sns-cloud-8f314-firebase-adminsdk-oudr4-ecdc782652.json");
			
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(refreshToken))
					.build();
			
			// 처음 호출시에 초기화 처리
			if (FirebaseApp.getApps().isEmpty()) {
				FirebaseApp.initializeApp(options);
			}
			
			List<String> registrationToken = token;
			String title = chatMsgDTO.getUserName();
			String content = chatMsgDTO.getMessage();
			Gson gson = new Gson();
		    String objJson = gson.toJson(chatMsgDTO);
		    String roomListJson = gson.toJson(roomList);
			
			// message 작성
			MulticastMessage message = MulticastMessage.builder()
					.setAndroidConfig(AndroidConfig.builder()
							.setTtl(3600 * 1000) // 유효기간 1시간... 기본값 4주
							.setPriority(AndroidConfig.Priority.HIGH)
							.setNotification(AndroidNotification.builder()
									.setTitle(title)
									.setBody(content)
									.setColor("#98FF98")
									.setClickAction(".MainActivity")
									.build())
							.build())
					.putData("title", title)
					.putData("message", content)
					.putData("data", objJson)
					.putData("roomList", roomListJson)
					.addAllTokens(registrationToken)
					.build();
			
			// 메시지를 FirebaseMessaging에 보내기
			BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
			System.out.println(response.getSuccessCount() + " messages were sent successfully");
			
			for(ChatRoomDTO dto : roomList) {
	    		System.out.println(" 전달될 결과값  info : " + dto.toString());
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
