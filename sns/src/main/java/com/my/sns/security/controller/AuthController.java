package com.my.sns.security.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.my.sns.security.service.AuthService;

@Controller
public class AuthController {
	
	@Autowired
	AuthService authService;

//	@RequestBody api로 테스트할때만 붙이자 일단은..
	@ResponseBody
	@RequestMapping(value="login.do/posts", method=RequestMethod.POST, produces="application/json; charset=utf-8")
	public ResponseEntity<?> login(String objJson, String fcmToken) {
		return ResponseEntity.ok(authService.login(objJson, fcmToken));
	}

	@ResponseBody
	@RequestMapping(value="singup.do/posts", method=RequestMethod.POST,produces="application/json; charset=utf-8")
	public ResponseEntity<?> signup(String objJson) {
		return ResponseEntity.ok(authService.join(objJson));
	}
	
//	@ResponseBody
//	@RequestMapping(value="reissue.do/posts", method=RequestMethod.POST,produces="application/json; charset=utf-8")
//	public ResponseEntity<?> reissue(String objJson) {
//		return ResponseEntity.ok(authService.reissue(objJson));
//	}
	
	@ResponseBody
	@RequestMapping(value="autologin.do/posts", method=RequestMethod.POST,produces="application/json; charset=utf-8")
	public ResponseEntity<?> autoLogin() {
		return ResponseEntity.ok(authService.autoLogin());
	}
}
