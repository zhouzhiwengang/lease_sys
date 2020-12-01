package com.zzg.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.zzg.dao.TokenRelationRepository;
import com.zzg.dao.UserRepository;
import com.zzg.dao.VerificationRepository;
import com.zzg.entity.TokenRelation;
import com.zzg.entity.User;
import com.zzg.entity.Verification;
import com.zzg.redis.RedisUtils;
import com.zzg.util.JWTUtil;

@RestController
public class LoginController {
	public static final String VRIFY_CODE = "VRIFYCODE";
	// 过期时间 24 小时
	private static final long EXPIRE_TIME = 60 * 24 * 60 * 1000;
	@Autowired
	UserRepository userRepository;
	@Autowired
	TokenRelationRepository tokenRelationRepository;
	@Autowired
	VerificationRepository verificationRepository;
	
	
	/**
     * 登录
     */
    @PostMapping("/sys/login")
    @ResponseBody
    public Map<String, Object> login(@RequestParam("username")String username, @RequestParam("password")String password, @RequestParam("captcha")String captcha) {
        Map<String, Object> result = new HashMap<>();
        
        //校验验证码
        //基于session的验证码
        // String sessionCaptcha = (String) SecurityUtils.getSubject().getSession().getAttribute(VRIFY_CODE);
        //基于redis的验证码
        String redisCaptcha = (String) RedisUtils.hGet(VRIFY_CODE, captcha);
        if (StringUtils.isEmpty(redisCaptcha)) {
        	 result.put("status", 400);
             result.put("msg", "验证码有误");
             return result;
        }
        //用户信息
        User user = userRepository.findByUsername(username);
        //账号不存在、密码错误
        if (user == null || !user.getPassword().equals(password)) {
            result.put("status", 400);
            result.put("msg", "账号或密码有误");
        } else {
            //生成token，并保存到redis
        	String token = JWTUtil.createToken(username);
        	RedisUtils.set(username,token);
        	RedisUtils.expire(username, EXPIRE_TIME);
        	// user->token 关系保存到mysql 中
        	TokenRelation relation = tokenRelationRepository.findByUsername(username);
        	if(relation == null){
        		relation = new TokenRelation();
        		relation.setUsername(username);
            	relation.setToken(token);
        	} else {
        		relation.setToken(token);
        	}
        	tokenRelationRepository.save(relation);
        	result.put("token", token);
            result.put("status", 200);
            result.put("msg", "登陆成功");
            // 移除验证码
            RedisUtils.hDelete(VRIFY_CODE, captcha);
        }
        return result;
    }
    
    public boolean validateCode(String code){
    	 Verification verification = verificationRepository.findByVerificationCode(code);
    	 if(verification != null){
    		 
    	 }
    	 return false;
    }
    
    /**
     * 邮箱登录
     */
    @PostMapping("/email/login")
    @ResponseBody
    public Map<String, Object> emailLogin(@RequestParam("email")String email, @RequestParam("code")String code) {
        Map<String, Object> result = new HashMap<>();
        
      
        //用户信息
        User user = userRepository.findByEmail(email);
        Verification verification = verificationRepository.findByVerificationCode(code);
        //账号不存在、验证码错误
        if (user == null || verification == null) {
            result.put("status", 400);
            result.put("msg", "邮箱有误");
        } else {
            //生成token，并保存到redis
        	String token = JWTUtil.createToken(user.getUsername());
        	RedisUtils.set(user.getUsername(),token);
        	RedisUtils.expire(user.getUsername(), EXPIRE_TIME);
        	// user->token 关系保存到mysql 中
        	TokenRelation relation = tokenRelationRepository.findByUsername(user.getUsername());
        	if(relation == null){
        		relation = new TokenRelation();
        		relation.setUsername(user.getUsername());
            	relation.setToken(token);
        	} else {
        		relation.setToken(token);
        	}
        	tokenRelationRepository.save(relation);
        	result.put("token", token);
            result.put("status", 200);
            result.put("msg", "登陆成功");
            
            // 验证码移除
            verificationRepository.deleteById(verification.getVerificationId());
           
        }
        return result;
    }
    
    /**
     * 邮箱登录
     */
    @PostMapping("/phone/login")
    @ResponseBody
    public Map<String, Object> phoneLogin(@RequestParam("phone")String phone, @RequestParam("code")String code) {
        Map<String, Object> result = new HashMap<>();
        
      
        //用户信息
        User user = userRepository.findByTelephone(phone);
        Verification verification = verificationRepository.findByVerificationCode(code);
        //账号不存在、验证码错误
        if (user == null || verification == null) {
            result.put("status", 400);
            result.put("msg", "手机号码有误");
        } else {
            //生成token，并保存到redis
        	String token = JWTUtil.createToken(user.getUsername());
        	RedisUtils.set(user.getUsername(),token);
        	RedisUtils.expire(user.getUsername(), EXPIRE_TIME);
        	// user->token 关系保存到mysql 中
        	TokenRelation relation = tokenRelationRepository.findByUsername(user.getUsername());
        	if(relation == null){
        		relation = new TokenRelation();
        		relation.setUsername(user.getUsername());
            	relation.setToken(token);
        	} else {
        		relation.setToken(token);
        	}
        	tokenRelationRepository.save(relation);
        	result.put("token", token);
            result.put("status", 200);
            result.put("msg", "登陆成功");
            
            // 验证码移除
            verificationRepository.deleteById(verification.getVerificationId());
           
        }
        return result;
    }
    
    
    /**
     * 退出
     */
   
    @PostMapping("/sys/logout")
    public Map<String, Object> logout(@RequestHeader("token")String token) {
        Map<String, Object> result = new HashMap<>();
        String username = null;
        TokenRelation relation = tokenRelationRepository.findByToken(token);
        if(relation != null){
        	username = relation.getUsername();
        	tokenRelationRepository.deleteById(relation.getRelationSid());
        }
        if(!StringUtils.isEmpty(username)){
        	// redis 移除token
            RedisUtils.del(username);
        }
       
        result.put("status", 200);
        result.put("msg", "您已安全退出系统");
        return result;
    }
}
