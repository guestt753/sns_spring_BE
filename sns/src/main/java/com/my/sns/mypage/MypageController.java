package com.my.sns.mypage;


import java.io.File;
import java.text.Format;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.websocket.Transformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.type.Date;
import com.google.type.DateTime;
import com.my.sns.security.entity.CustomUserDetails;

import sun.text.resources.FormatData;

//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;

@Controller
public class MypageController {
      @Autowired
      PageService service;
      
      @Autowired
      PageDAO dao;
      
      //사진 저장할 경로 - 내 프로필 편집화면
      String uploadPath="C:/upload";
      
      //사진 저장할 경로 - 내 프로필 편집화면
      String uploadPath2="C:/upload2";
      //사진 파일 이름 - 내 프로필 편집
      String picfile;
      // 텍스트와 사진명을 저장할 데이터 클레스
      AndroidSendData data = new AndroidSendData();
      
      PageVO vo = new PageVO();
      //feed_no를 저장할 변수
      int feedno;
      
	    //안드로이드에서 스프링으로 데이터 보내기 (텍스트+이미지) 프로필 편집 화면
		@RequestMapping(value = "posts/post", method = RequestMethod.POST,consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
        @ResponseBody
		public ResponseEntity<?> user(@RequestParam Map<String,RequestBody> texfile,@RequestParam("photo") MultipartFile photo) {
			
			try 
			{	   
				
				byte[] byte3=null;
				//텍스트
				System.out.println("안드로이드에서 보낸 map값:"+texfile);
				System.out.println("안드로이드에서 보낸 multipart값:"+photo);
				
				Map<String,Object> temp = new HashMap<>();
				temp.put("name",texfile.get("name"));
				temp.put("Introduce",texfile.get("Introduce"));
				
				String username = (String) temp.get("name").toString();
	            String userIntroduce = (String) temp.get("Introduce").toString();
	            // 전달이 잘 되었는지 확인
	            System.out.println("안드로이드에서 보낸 이름:"+username);
	            System.out.println("안드로이드에서 보낸 자기소개:"+userIntroduce);
	            
	            data.setUsername(username);
	            data.setUserIntroduce(userIntroduce);
	            
	           
	            //안드로이드에서 보낸 사진 가져오기 
	            if(!photo.isEmpty()) {
					 byte3 = photo.getBytes();		
				}
	            
	             picfile = byte3.toString();
	            
	            System.out.println("안드로이드에서 보낸 사진파일:"+picfile);
	            //파일이름 가져오기
	            String imgfileName = photo.getOriginalFilename();
	            System.out.println("사진이름 확인:"+imgfileName);
	          
	            //사진이름이 겹치지 않도록 이름을 설정 (이름+랜덤값)
	            String ptonamecode = username+UUID.randomUUID().toString()+".jpg";
	            
	           
                System.out.println("내가 바꾼 사진이름 확인:"+ptonamecode);
                //데이터 클레스에 사진명을 적는다
	            data.setPtoName(ptonamecode);
	            //저장할 폴더 이름과 파일 이름
	            File target = new File(uploadPath,ptonamecode);
	            //임시디렉토리에 저장된 업로드된 파일을 지정된 디렉토리로 복사
	            FileCopyUtils.copy(photo.getBytes(),target);
	            
	            
	           
	            
				Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if(principal instanceof CustomUserDetails) {
					Long userNo = ((CustomUserDetails)principal).getUserNo();
					System.out.println("userNo : " + userNo);
				
					service.InsertUserNameIntroduction(data,userNo);
				}
				
				return ResponseEntity.ok(vo);
				
			}
			catch(Exception e) {
				e.printStackTrace();
				
			}
			return null;
		}
		
		//mysql에서 spring으로 데이터 보내기
		@RequestMapping(value = "get/gets/1", method = RequestMethod.GET,produces="application/json; charset=utf-8")
		@ResponseBody
		public ResponseEntity<?> user2() {
				
			
			
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(principal instanceof CustomUserDetails) {
				Long userNo = ((CustomUserDetails)principal).getUserNo();
				System.out.println("userNo : " + userNo);
				
			 vo = service.getUserNameIntroduction(userNo);
			}
		
			return ResponseEntity.ok(vo);
		}
		
		//게시글 추가화면에서 스프링으로 사진들과 피드 텍스트 보내기
		@RequestMapping(value = "postss/post2", method = RequestMethod.POST,consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
        @ResponseBody
		public ResponseEntity<?> user3(@RequestParam Map<String,RequestBody> texfile,@RequestParam("photo") List<MultipartFile> photo) {
			try {
				
				byte[] byte3 = null;
				//피드 텍스트 저장 
				
				//사진 이름 저장
				List<String> ptoname = new ArrayList<>();
				
				System.out.println("안드로이드에서 보낸 map값:"+texfile);
				System.out.println("안드로이드에서 보낸 multipart값:"+photo);
				
				//피드 텍스트를 꺼낸다
				Map<String,Object> temp = new HashMap<>();
				temp.put("feedtext",texfile.get("feedtext"));
				
				String feedtext = (String) temp.get("feedtext").toString();
	            
	            // 안드로이드에서 보낸 피드 텍스트를 잘 꺼내왔는지 확인
	            System.out.println("안드로이드에서 보낸 피드텍스트:"+feedtext);  
	            //데이터 클래스에 넣는다
	            data.setFeedtext(feedtext);
	            
	            //사용자 고유 번호 가져오기 + 피드 텍스트 부터 업로드 시작
		           Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					if(principal instanceof CustomUserDetails) {
						Long userNo = ((CustomUserDetails)principal).getUserNo();
						System.out.println("userNo : " + userNo);
					
						vo = service.InsertFeedtextandpto(data, userNo);
						feedno = vo.getFeedno();
						System.out.println("feed 번호 확인:"+feedno);
					}
	            
	          //안드로이드에서 보낸 사진이름 가져오기
	            for(int i=0;i<photo.size();i++) {
	            	if(!photo.isEmpty()) {
						 byte3 = photo.get(i).getBytes();	
						 ptoname.add(byte3.toString()+".jpg");
					}
	            }
	            
	           //사진 이름을 전송 , 
	          for(String name: ptoname) {
	        	  //db에 사진 이름 넣기
	        	  service.InsertFeedptonames(name,feedno);
	          }
	          //폴더에 사진 업로드 시작
	            for(int i=0;i<photo.size();i++) {
	            	File target = new File(uploadPath2,ptoname.get(i));
		            //임시디렉토리에 저장된 업로드된 파일을 지정된 디렉토리로 복사
		            FileCopyUtils.copy(photo.get(i).getBytes(),target);
	            }
	            
	            //is_now에 0 넣는 코드
	            service.UpdateIsnow(feedno);
	           
	          
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			return null;
		}
		//image_video에서 사진 이름과 피드 번호 가져오기 - 내 프로필 화면 리사이클러뷰 관련
		//mysql에서 spring으로 데이터 보내기
				@RequestMapping(value = "gett/gets2", method = RequestMethod.POST,produces="application/json; charset=utf-8")
				@ResponseBody
				public ResponseEntity<?> user4(){
					
					System.out.println("************안드로이드 내 프로필 사진 불러오기 요청 성공*************");
					Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					if(principal instanceof CustomUserDetails) {
						Long userNo = ((CustomUserDetails)principal).getUserNo();
						System.out.println("userNo : " + userNo);
						
					 vo = service.getImagenames(userNo);	
					 
					 
				}
					 return ResponseEntity.ok(vo);
         }
	  //메인 화면에서 필요한 1.유저 이름과 feed추가시 작성한 2.comment, 3.feed_no(번호), 4.image이름들 가져오는 코드 5.내 프로필 사진까지
				@RequestMapping(value = "gett2/gets", method = RequestMethod.POST,produces="application/json; charset=utf-8")
				@ResponseBody
				public ResponseEntity<?> user5(){
					
					Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					if(principal instanceof CustomUserDetails) {
						Long userNo = ((CustomUserDetails)principal).getUserNo();
						System.out.println("userNo : " + userNo);
						
					 vo = service.getnameimagecomment(userNo);
					 
					 
					 int userno = Long.valueOf(userNo).intValue();
					 vo.setSpring_my_userno(userno);
					 
					/* for(int i=0;i<vo.list.size();i++) {
						 System.out.println(i+"번쨰:"+"feedno:"+vo.list.get(i).getFeedno());
						 System.out.println(i+"번쨰:"+"imagename:"+vo.list.get(i).getImagename());
						 System.out.println(i+"번쨰:"+"username:"+vo.list.get(i).getUsername());
						 System.out.println(i+"번쨰:"+"feedcontent:"+vo.list.get(i).getFeedcontent());
						 System.out.println(i+"번쨰:"+"myimagename:"+vo.list.get(i).getMyimagename());
					 }*/
					
				}
					 return ResponseEntity.ok(vo);
         }
				
	//안드로이드에서 댓글작성한 내용들을 받아 db에 저장하는 코드
				@RequestMapping(value = "comments/post3",method = RequestMethod.POST,consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
				@ResponseBody
				public void user6(@RequestParam Map<String,RequestBody> requestMapp){
					System.out.println("안드로이드에서 나에게 보낸 댓글 관련 서버 구동중");
					
					CommentDATA data = new CommentDATA();
					
					Map<String,Object> temp = new HashMap<>();
					temp.put("text",requestMapp.get("text"));
					temp.put("feedno",requestMapp.get("feedno"));
					temp.put("commenttime",requestMapp.get("commenttime"));
					
					String text = (String) temp.get("text").toString();
					String feedno = (String) temp.get("feedno").toString();
					String commenttime = (String) temp.get("commenttime").toString();
					
					String commentsno = null;
					
					
					int feed_no = Integer.parseInt(feedno);
					
					
							
					
					Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					if(principal instanceof CustomUserDetails) {
						Long userNo = ((CustomUserDetails)principal).getUserNo();
						System.out.println("userNo : " + userNo);
						
						  service.UpdateComments(userNo, text, feed_no,commenttime);
				}
					
					
					
          }
				
	//안드로이드에 댓글 화면에  1.내 프로필 사진 , 2.내 이름  3.댓글 내용 4.comments_no을 가져오는 코드 
				@RequestMapping(value = "gett3/gets", method = RequestMethod.POST,produces="application/json; charset=utf-8")
				@ResponseBody
				public ResponseEntity<?> user7(String num){
					
					Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					if(principal instanceof CustomUserDetails) {
						Long userNo = ((CustomUserDetails)principal).getUserNo();
						System.out.println("userNo : " + userNo);
					
				    String feednumber = num;
					int feedno = Integer.parseInt(feednumber);
					
					vo = service.getComments(feedno);
				}
					return ResponseEntity.ok(vo);
         }
				
	            //답글 화면에서 내가 작성한 답글을 받기 위한 코드
				@RequestMapping(value = "recomment/post4", method = RequestMethod.POST,consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
				@ResponseBody
				public void user8(@RequestParam Map<String,RequestBody> recommentdata){
					
					
					Map<String,Object> temp = new HashMap<>();
					
					temp.put("recommenttext", recommentdata.get("recommenttext"));
					temp.put("commentsno", recommentdata.get("commentsno"));
					temp.put("recommenttime", recommentdata.get("recommenttime"));
					
					String recommenttext = (String) temp.get("recommenttext").toString();
					String commentsno = (String) temp.get("commentsno").toString();
					String recommenttime = (String) temp.get("recommenttime").toString();
					
					
					Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					if(principal instanceof CustomUserDetails) {
						Long userNo = ((CustomUserDetails)principal).getUserNo();
						System.out.println("userNo : " + userNo);
						
			
						service.UpdateRecomments(userNo, recommenttext, commentsno, recommenttime);
						
						
				}
				
        }
				
	   //답글 화면에서 필요한 1.내 이름 2.프로필 사진 3.답글 내용 4.답글 작성 시간을 가져온다
				@RequestMapping(value = "getrecommentdata/gets", method = RequestMethod.POST,produces="application/json; charset=utf-8")
				@ResponseBody
				public ResponseEntity<?> user9(String commentsno){
					
					Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					if(principal instanceof CustomUserDetails) {
						Long userNo = ((CustomUserDetails)principal).getUserNo();
						System.out.println("userNo : " + userNo);
					
				    String feednumber = commentsno;
					int feedno = Integer.parseInt(feednumber);
					
					vo = service.getRecomments(feedno);
					
					
				}
					return ResponseEntity.ok(vo);
         }	
			
				//메인 화면에서 좋아요 버튼을 눌렀을때 
				@RequestMapping(value = "postfeedlike/feedlike", method = RequestMethod.POST,produces="application/json; charset=utf-8")
				@ResponseBody
				public ResponseEntity<?> user10(String feedno,String like_btn_state){
					
					Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					if(principal instanceof CustomUserDetails) {
						Long userNo = ((CustomUserDetails)principal).getUserNo();
						System.out.println("userNo : " + userNo);
			
				   //안드로이드에서 전송한 feed_no를 받기 위한 변수		
				   String feednum = feedno;
				   int feednoo = Integer.parseInt(feednum);
				   
				   //안드로이드에서 보낸 like_btn_State를 받는 변수 
				   String like_button_state = like_btn_state;
				   int like_btn_statee = Integer.parseInt(like_button_state);
				   
				   //안드로이드에서 좋아요 버튼을 누르기전 상태가 빈 하트일 경우 좋아요 버튼을 클릭시 db에 데이터를 insert를 하기 위한 코드
				   if(like_btn_statee==1) {
					   service.Feedlikedata(userNo,feednoo);
				   }
				   //안드로이드에서 좋아요 버튼을 누르기전 상태가 빨간 하트일 경우 좋아요 버튼 클릭시 db안의 데이터를 delete를 하기 위한 코드
				   else if(like_btn_statee==0) {
					   
				   }
				   
				 
				 
				   
				  int userno = Long.valueOf(userNo).intValue();
				  vo.setUserno(userno);
					
				}
					return ResponseEntity.ok(vo);
         }	
}