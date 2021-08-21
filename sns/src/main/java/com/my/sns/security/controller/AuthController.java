package com.my.sns.security.controller;

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
import com.my.sns.security.controller.dto.JwtResponse;
import com.my.sns.security.controller.dto.TokenRequestDTO;
import com.my.sns.security.entity.CustomUserDetails;
import com.my.sns.security.service.AuthService;
import com.my.sns.user.UserRoleVO;
import com.my.sns.user.UserService;
import com.my.sns.user.UserVO;

@Controller
public class AuthController {
	
	@Autowired
	AuthService authService;

//	@RequestBody api로 테스트할때만 붙이자 일단은..
	@ResponseBody
	@RequestMapping(value="login.do/posts", method=RequestMethod.POST, produces="application/json; charset=utf-8")
	public ResponseEntity<?> login(String objJson) {
		return ResponseEntity.ok(authService.login(objJson));
	}

//	@ResponseBody
//	@RequestMapping(value="singup.do/posts", method=RequestMethod.POST,produces="application/json; charset=utf-8")
//	public ResponseEntity<?> signup(String objJson) {
//		return ResponseEntity.ok(authService.signup(objJson));
//	}
	
	@ResponseBody
	@RequestMapping(value="reissue.do/posts", method=RequestMethod.POST,produces="application/json; charset=utf-8")
	public ResponseEntity<?> reissue(TokenRequestDTO objJson) {
		return ResponseEntity.ok(authService.reissue(objJson));
	}
}
