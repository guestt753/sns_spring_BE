package com.my.sns.user;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.my.sns.security.entity.UserEntity;

public class JDBC_Test {
	
	UserService userService;
	AbstractApplicationContext container;
	UserVO vo;
	UserEntity user;

	@Before
	public void setUp() throws Exception {
		// 1. 스프링 컨테이너를 구동한다.
				container =	new GenericXmlApplicationContext("applicationContext.xml");
				
		// 2. 스프링 컨테이너로부터 UserServiceImpl 객체를 Look up 한다.
				 userService = (UserService)container.getBean("userService");
				 
				 vo = new UserVO();
				 user = new UserEntity();
	}

	@After
	public void tearDown() throws Exception {
		userService = null;
		
		// 4. 스프링 컨테이너를 종료한다
		container.close();
	}

	@Test
	public void test() {
		// 3. 로그인 기능 테스트
				vo.setUserId("test");
				vo.setUserPassword("test123");
				
				user = userService.getUser(vo.getUserId());
				if(user != null) {
					System.out.println(user.getUserNo() + "님 환영합니다.");
				} else {
					System.out.println("로그인 실패");
				}
	}

}
