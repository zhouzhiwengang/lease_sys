package com.zzg.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzg.dao.VerificationRepository;
import com.zzg.entity.Verification;
import com.zzg.util.HttpContextUtil;

@RestController
public class VerificationController {
	// 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    @Autowired
    VerificationRepository verificationRepository;
    
    public Verification getVerification(String code){
    	Date date = new Date();
    	Verification verification = new Verification();
    	verification.setCreateDate(date);
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.set(Calendar.MINUTE,  calendar.get(Calendar.MINUTE) + 15);//分钟+15
    	verification.setFailDate(calendar.getTime());
    	verification.setStatus(1);
    	verification.setVerificationCode(code);
    	return verification;
    }
   
    
	@GetMapping("/sys/verification")
	public void defaultVerification(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
	throws Exception {
		Verification verification = this.getVerification("123456");
		verificationRepository.save(verification);
		
		httpServletResponse.setContentType("application/json;charset=utf-8");
		httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
		httpServletResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtil.getOrigin());
		httpServletResponse.setCharacterEncoding("UTF-8");
        //设置编码，否则中文字符在重定向时会变为空字符串
        Map<String, Object> result = new HashMap<>();
        result.put("status", 200);
        result.put("code", verification.getVerificationCode());
        result.put("msg", "验证码发送成功");
        String json = MAPPER.writeValueAsString(result);
        httpServletResponse.getWriter().print(json);
		
	}
}
