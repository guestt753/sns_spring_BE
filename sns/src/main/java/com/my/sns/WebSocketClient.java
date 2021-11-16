package com.my.sns;

import java.util.Scanner;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import com.my.sns.config.SessionHandler;

@ComponentScan(basePackages = "com.my.sns")
public class WebSocketClient { 
	private static final String URL = "ws://localhost:80/stomp-chat";
	public static void main(String[] args) { 
		org.springframework.web.socket.client.WebSocketClient client = new StandardWebSocketClient(); 
		WebSocketStompClient stompClient = new WebSocketStompClient(client); 
		stompClient.setMessageConverter(new MappingJackson2MessageConverter()); 
		stompClient.connect(URL, new SessionHandler()); 
		new Scanner(System.in).nextLine(); // Don't close immediately. 
		}
	}



