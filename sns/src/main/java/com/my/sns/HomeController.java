package com.my.sns;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.sns.user.UserVO;
import com.my.sns.user.impl.UserDAO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	/*
	 * @Autowired private SqlSession sqlSession;
	 * 
	 * 안드로이드 통신관련
	 * 
	 * @RequestMapping("/vision")
	 * 
	 * @ResponseBody public Map<String, String>
	 * androidTestWithRequestAndResponse(HttpServletRequest request){
	 * 
	 * ArrayList<UserVO> vo = new ArrayList<UserVO>(); Map<String, String> result =
	 * new HashMap<String, String>(); String nowTime = getCurrentTime("YYYY,M,d");
	 * String num = "",day = "",content = "";
	 * 
	 * UserDAO dao = sqlSession.getMapper(UserDAO.class);
	 * 
	 * dao.writeDao(nowTime,request.getParameter("content"));
	 * 
	 * visionDto = dao.listDao();
	 * 
	 * for(int i=0;i<visionDto.size();i++) { num +=
	 * Integer.toString(visionDto.get(i).getNum())+"\t"; day +=
	 * visionDto.get(i).getDay()+"\t"; content +=
	 * visionDto.get(i).getContent()+"\t"; }
	 * 
	 * result.put("num",num); result.put("day",day); result.put("content",content);
	 * 
	 * return result; }
	 * 
	 * private String getCurrentTime(String timeFormat) { // TODO Auto-generated
	 * method stub return new
	 * SimpleDateFormat(timeFormat).format(System.currentTimeMillis()); }
	 */
	
}
