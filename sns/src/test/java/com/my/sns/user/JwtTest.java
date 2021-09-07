package com.my.sns.user;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.my.sns.config.JwtProvider;


public class JwtTest {
	AuthenticationManager authenticationManager;
	UserVO user;
	JwtProvider jwt;
	String token1;
	String token2;
	String key1;
	String key2;
	
	@Before
	public void setUp() throws Exception {
		jwt = new JwtProvider();

		
//		token1 = jwt.generateJwtToken();
//		
//		token2 = jwt.generateJwtToken();
		
	}
	
	@Test
	public void test() {
		System.out.println("token1 : " + token1);
		System.out.println("token2 : " + token2);
		System.out.println("token1 : " + jwt.validateJwtToken(token1));
		System.out.println("token1 : " + jwt.validateJwtToken(token2));
		System.out.println("key encoded to sting : " + jwt.getKeytoString());
	}

}
