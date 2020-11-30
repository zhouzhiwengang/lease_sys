package com.zzg.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zzg.dao.UserRepository;
import com.zzg.entity.User;
import com.zzg.redis.RedisUtils;
import com.zzg.util.JWTUtil;

@RestController
public class LoginController {
	// 过期时间 24 小时
	private static final long EXPIRE_TIME = 60 * 24 * 60 * 1000;
	@Autowired
	UserRepository userRepository;
	
	/**
     * 登录
     */
    @PostMapping("/sys/login")
    @ResponseBody
    public Map<String, Object> login(@RequestParam("username")String username, @RequestParam("password")String password) {
        Map<String, Object> result = new HashMap<>();

        //用户信息
        User user = userRepository.findByUsername(username);
        //账号不存在、密码错误
        if (user == null || !user.getPassword().equals(password)) {
            result.put("status", 400);
            result.put("msg", "账号或密码有误");
        } else {
            //生成token，并保存到redis
        	String token = JWTUtil.createToken(username);
        	RedisUtils.set(token,token);
        	RedisUtils.expire(token, EXPIRE_TIME);
        	
        	result.put("token", token);
            result.put("status", 200);
            result.put("msg", "登陆成功");
        }
        return result;
    }
    
    /**
     * 退出
     */
   
    @PostMapping("/sys/logout")
    public Map<String, Object> logout(@RequestHeader("token")String token) {
        Map<String, Object> result = new HashMap<>();
        // redis 移除token
        RedisUtils.del(token);
        result.put("status", 200);
        result.put("msg", "您已安全退出系统");
        return result;
    }
}
