package com.my.sns;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.my.sns.config.JwtProvider;
import com.my.sns.friend.FriendListEntity;
import com.my.sns.friend.FriendRequestListEntity;
import com.my.sns.friend.FriendService;
import com.my.sns.service.security.CustomUserDetails;
import com.my.sns.service.security.JwtResponse;
import com.my.sns.service.security.RequestResponse;
import com.my.sns.service.security.SearchListEntity;
import com.my.sns.service.security.UserDbService;
import com.my.sns.service.security.UserEntity;
import com.my.sns.service.security.UserRoleEntity;
import com.my.sns.user.UserRoleVO;
import com.my.sns.user.UserService;
import com.my.sns.user.UserVO;

@Controller
public class UserController {
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
	
	RequestResponse requestResponse = new RequestResponse();
	String message = null;
	int code;

	
//	@ResponseBody
//	@RequestMapping(value="login.do/posts", method=RequestMethod.POST, produces="application/json; charset=utf-8")
//	public int login(String objJson) {
//		System.out.println("::::::::Login:::::::::");
//		int result = 0;
//		Gson gson = new Gson();
//		try {
//			UserVO user = gson.fromJson(objJson, UserVO.class);
//			user = userService.getUser(user);
////			JSONObject obj = new JSONObject();
//			if(user != null) {
//				System.out.println(user.getUserName() + "님 환영합니다.");
//				result = 1;
//			}
//		} catch (Exception e) {
//			 e.printStackTrace();
//			 System.out.println("아이디 또는 비밀번호가 일치하지 않습니다.");
//		}
//		return result;
//	}

	
	@ResponseBody
	@RequestMapping(value="getlist.do/gets", method=RequestMethod.GET, produces="application/json; charset=utf-8")
	public ResponseEntity<?> getFriendRequestList() throws Exception {
		List<FriendRequestListEntity> friendRequestListEntities = new ArrayList<FriendRequestListEntity>();
		List<RequestResponse.FriendRequestListEntity> list = new ArrayList<>();
		System.out.println("::::::::Get_F_R_List:::::::::");
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof CustomUserDetails) {
			Long userNo = ((CustomUserDetails)principal).getUserNo();
			System.out.println("userNo : " + userNo);
			
			friendRequestListEntities = friendService.getFriendRequestList(userNo);
			
	        for(FriendRequestListEntity entity : friendRequestListEntities) {
	            list.add(new RequestResponse.FriendRequestListEntity(entity.getUserNo(), entity.getUserName(), entity.getUserImageUrl()));
	        }
			
			if(friendRequestListEntities == null) {
				message = "사용자가 받은 친구 요청 목록을 찾을 수 없습니다.";
				code = 4900;
				requestResponse.setMessage(message);
				requestResponse.setCode(code);
				requestResponse.setFriendRequestListEntity(list);
				return ResponseEntity.ok(requestResponse);
//	            throw new UsernameNotFoundException("사용자의 받은 친구 요청 목록을 찾을 수 없습니다.");
			}
			
			message = "조회 성공";
			code = 4700;
			requestResponse.setMessage(message);
			requestResponse.setCode(code);
			requestResponse.setFriendRequestListEntity(list);
		}
		else {
			String userNo = principal.toString();
			message = "principal의 변환을 실패했습니다.";
			code = 5100;
			requestResponse.setMessage(message);
			requestResponse.setCode(code);
			return ResponseEntity.ok(requestResponse);
//			throw new Exception("principal의 변환을 실패했습니다. " + userNo);
		}
	
		System.out.println(ResponseEntity.ok(requestResponse));
//		return ResponseEntity.ok(new ArrayList<FriendRequestListEntity>(friendRequestListEntities));
		return ResponseEntity.ok(requestResponse);
	}
	
	@ResponseBody
	@RequestMapping(value="getSearchList.do/gets", method=RequestMethod.GET, produces="application/json; charset=utf-8")
	public ResponseEntity<?> getSearchList(String searchText) {
		List<UserVO> searchList = new ArrayList<UserVO>();
		List<FriendListEntity> friendList = new ArrayList<FriendListEntity>();
		List<RequestResponse.SearchListEntity> searListEntities = new ArrayList<RequestResponse.SearchListEntity>();
		int isadded = 0;
		System.out.println("::::::::Get_Search_List:::::::::");
		System.out.println(searchText);
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof CustomUserDetails) {
			Long userNo = ((CustomUserDetails)principal).getUserNo();
			System.out.println("userNo : " + userNo);
			
			searchList = userService.searchUserName(searchText);
			friendList = friendService.getFriendList(userNo, userNo);
			System.out.println(searchList + " " + friendList);
			
	        for(UserVO entity : searchList) {
	        	if(entity.getUserNo() == userNo) { //검색결과 자신일 경우 맨 위에 결과가 뜨게금 설정
	        		searListEntities.add(new RequestResponse.SearchListEntity(entity.getUserNo(),entity.getUserName(),entity.getUserImageUrl()));
	        		continue;
	        	}
	        	for(FriendListEntity list : friendList) {
	        		if(entity.getUserNo() == list.getUserOneNo()) { //검색 결과가 친구 일 경우
	        			searListEntities.add(new RequestResponse.SearchListEntity(entity.getUserNo(), entity.getUserName(), "친구" ,entity.getUserImageUrl()));
	        			isadded = 1;
	        			break;
	        		}
	        	}
	        	if(isadded == 0) {
	        		searListEntities.add(new RequestResponse.SearchListEntity(entity.getUserNo(), entity.getUserName(),entity.getUserImageUrl()));
	        	}
	        	isadded = 0;
	        }
			
	        //검색 결과가 없을 경우
			if(searchList == null) { 
				message = "사용자가 받은 친구 요청 목록을 찾을 수 없습니다.";
				code = 4900;
				requestResponse.setMessage(message);
				requestResponse.setCode(code);
				requestResponse.setSearchListEntity(searListEntities);
				return ResponseEntity.ok(requestResponse);
			} else {
			message = "조회 성공";
			code = 4700;
			requestResponse.setMessage(message);
			requestResponse.setCode(code);
			requestResponse.setSearchListEntity(searListEntities);
			}
		}
		else {
			String userNo = principal.toString();
			message = "principal의 변환을 실패했습니다.";
			code = 5100;
			requestResponse.setMessage(message);
			requestResponse.setCode(code);
			return ResponseEntity.ok(requestResponse);
//			throw new Exception("principal의 변환을 실패했습니다. " + userNo);
		}
		return ResponseEntity.ok(requestResponse);
	}
	
	@ResponseBody
	@RequestMapping(value="actionFriendRequest.do/posts", method=RequestMethod.POST, produces="application/json; charset=utf-8")
	public ResponseEntity<?> actionFriendRequest(Long requestedUserNo, int type) {
		int result;
		System.out.println("::::::::Action_Friend_Request:::::::::");
		System.out.println(requestedUserNo.TYPE + " " + type);
		System.out.println(requestedUserNo + " " + type);

		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof CustomUserDetails) {
			Long userNo = ((CustomUserDetails)principal).getUserNo();
			System.out.println("userNo : " + userNo);
			
			result = friendService.acceptOrRefuseFriendRequest(userNo, requestedUserNo, type);
			System.out.println("result : " + result);
			
			if(result == 1 || result == 3) { // 거절 or 수락 성공...
				message = "Request Action success.";
				code = 4700;
				requestResponse.setMessage(message);
				requestResponse.setCode(code);
			}else { 
				message = "Request Action failed.";
				code = 4900;
				requestResponse.setMessage(message);
				requestResponse.setCode(code);
				return ResponseEntity.ok(requestResponse);
			}		 
		}
		else {
			String userNo = principal.toString();
			message = "principal의 변환을 실패했습니다.";
			code = 5100;
			requestResponse.setMessage(message);
			requestResponse.setCode(code);
			return ResponseEntity.ok(requestResponse);
//			throw new Exception("principal의 변환을 실패했습니다. " + userNo);
		}
		System.out.println(ResponseEntity.ok(requestResponse));
		System.out.println(requestResponse.getCode() + " " + requestResponse.getFriendRequestListEntity() + " " + requestResponse.toString());
		return ResponseEntity.ok(requestResponse);
	}
}
