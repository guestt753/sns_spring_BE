package com.my.sns.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocket
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer  {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		 registry.addEndpoint("/stomp-chat").setAllowedOrigins("*").addInterceptors(new HttpHandshakeInterceptor()).withSockJS();
		
	}

	
	@Override
	public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
		registry.setMessageSizeLimit(8 * 1024);
		
	}


	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
//		registry.setApplicationDestinationPrefixes("/app"); 
		registry.enableSimpleBroker("/topic", "/queue"); 
		registry.setApplicationDestinationPrefixes("/topic", "/queue"); // prefix in client queries
		registry.setUserDestinationPrefix("/user");
	}
}
