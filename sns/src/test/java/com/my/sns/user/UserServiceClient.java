package com.my.sns.user;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.my.sns.user.UserService;
import com.my.sns.user.UserVO;

public class UserServiceClient {
	public static void main(String[] args) {
		// 1. 스프링 컨테이너를 구동한다.
		AbstractApplicationContext container =
				new GenericXmlApplicationContext("applicationContext.xml");
		
		// 2. 스프링 컨테이너로부터 UserServiceImpl 객체를 Look up 한다.
		UserService userService = (UserService)container.getBean("userService");
		
		// 3. 로그인 기능 테스트
		UserVO vo = new UserVO();
		vo.setUserId("test");
		vo.setUserPassword("test123");
		
//		UserVO user = userService.getUser(vo);
//		if(user != null) {
//			System.out.println(user.getUserName() + "님 환영합니다.");
//		} else {
//			System.out.println("로그인 실패");
//		}
		
		// 4. 스프링 컨테이너를 종료한다.
		container.close();
	}
}
