package com.my.sns.user.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.my.sns.config.JwtProvider;
import com.my.sns.friend.FriendService;
import com.my.sns.service.security.CustomUserDetails;
import com.my.sns.service.security.JwtResponse;
import com.my.sns.user.UserRoleVO;
import com.my.sns.user.UserService;
import com.my.sns.user.UserVO;

@Controller
public class LoginController {
	@Autowired
	UserService userService;
	@Autowired
	FriendService friendService;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	UserVO user;
	@Autowired
	UserRoleVO role;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	CustomUserDetails customUserDetails;
	
	

//	@RequestBody api로 테스트할때만 붙이자 일단은..
	@ResponseBody
	@RequestMapping(value="login.do/posts", method=RequestMethod.POST, produces="application/json; charset=utf-8")
	public ResponseEntity<?> authenticateUser(String objJson) {
		System.out.println("::::::::Login:::::::::");
		Gson gson = new Gson();
		try {
			
			System.out.println("josn : " + objJson);
			user = gson.fromJson(objJson, UserVO.class);
//			user = userService.getUser(user);
//			JSONObject obj = new JSONObject();
//			System.out.println(user.getUserName() + " " + user.getUserPassword());  현재 gson으로 데어터를 보냈는데 데이터가 제대로 안받아지는듯 값이 없다고 뜸 nullpointer exception
			
		} catch (Exception e) {
			 e.printStackTrace();
			 System.out.println("json 변환오류");
		}

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(user.getUserId(), user.getUserPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtProvider.generateJwtToken(authentication);
		
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();		
		List<String> roles = customUserDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		System.out.println(jwt);

		return ResponseEntity.ok(new JwtResponse(jwt, 
												 customUserDetails.getUserNo(), 
												 roles));
	}
}
