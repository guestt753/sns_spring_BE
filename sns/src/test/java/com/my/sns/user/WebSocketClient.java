package com.my.sns.user;

import java.util.Scanner;

import org.junit.Test;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import com.my.sns.config.SessionHandler;

public class WebSocketClient { 
	private static final String URL = "ws://localhost:8080/sns/stomp-chat";
	
	@Test
	public void main() { 
		org.springframework.web.socket.client.WebSocketClient client = new StandardWebSocketClient(); 
		WebSocketStompClient stompClient = new WebSocketStompClient(client); 
		stompClient.setMessageConverter(new MappingJackson2MessageConverter()); 
		stompClient.connect(URL, new SessionHandler()); 
		System.out.println("test");
		new Scanner(System.in).nextLine(); // Don't close immediately. 
		}
	}



