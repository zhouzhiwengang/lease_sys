package com.zzg.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzg.dao.TokenRelationRepository;
import com.zzg.entity.TokenRelation;
import com.zzg.redis.RedisUtils;
import com.zzg.shrio.JWTToken;
import com.zzg.spring.SpringContextUtil;
import com.zzg.util.HttpContextUtil;

public class JWTFilter extends BasicHttpAuthenticationFilter {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	 // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();
   
    
	/**
     * 如果带有 token，则对 token 进行检查，否则直接通过
     * >2 接着执行 isAccessAllowed
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws UnauthorizedException {
        // 判断请求的请求头是否存在 Token
        if (isLoginAttempt(request, response)) {
            //如果存在，则进入 executeLogin 方法执行登入，检查 token 是否正确
            // 不正常就会抛出异常
            try {
                // 执行登录
                executeLogin(request, response);
                return true;
            } catch (Exception e) {
                //token 错误
                responseError(response, e.getMessage());
            }
        } else {
        	try{
        	 HttpServletResponse httpResponse = (HttpServletResponse) response;
             httpResponse.setContentType("application/json;charset=utf-8");
             httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
             httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtil.getOrigin());
             httpResponse.setCharacterEncoding("UTF-8");
             //设置编码，否则中文字符在重定向时会变为空字符串
             Map<String, Object> result = new HashMap<>();
             result.put("status", 400);
             result.put("msg", "token 已经注销");
             String json = MAPPER.writeValueAsString(result);
             httpResponse.getWriter().print(json);
             	return false;
        	}catch(IOException e) {
                logger.error(e.getMessage());
            }
        }
        // 如果请求头不存在 Token，则可能是执行登陆操作或者是游客状态访问，
        // 无需检查 token，直接返回 true
        return true;
    }

    /**
     * 判断用户是否想要登入。
     * 检测 header 里面是否包含 Token 字段
     *
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        System.out.println("req.getHeader(Token)"+req.getHeader("Token"));
        String token = req.getHeader("Token");
        if(StringUtils.isEmpty(token)){
        	return false;
        }
        TokenRelationRepository tokenRelationRepository = SpringContextUtil.getBean(TokenRelationRepository.class);
        TokenRelation relation = tokenRelationRepository.findByToken(token);
        if(relation != null){
        	return RedisUtils.get(relation.getUsername()) != null;
        }
        return false;

    }

    /**
     * 执行登陆操作
     *
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader("Token");

        JWTToken jwtToken = new JWTToken(token);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(jwtToken);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    /**
     *
     * 对跨域提供支持
     *
     * >1 请求最先从这开始执行
     *
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        // 设置 header key-value
        // httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE,aaa");
        // System.out.println(httpServletRequest.getHeader("Origin"));

        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // System.out.println( httpServletRequest.getHeader("Access-Control-Request-Headers"));

        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }

        return super.preHandle(request, response);
    }

    /**
     * 将非法请求跳转到 /unauthorized/**
     */
    private void responseError(ServletResponse response, String message) {
        try {

            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setContentType("application/json;charset=utf-8");
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtil.getOrigin());
            httpResponse.setCharacterEncoding("UTF-8");
            //设置编码，否则中文字符在重定向时会变为空字符串
            Map<String, Object> result = new HashMap<>();
            result.put("status", 500);
            result.put("msg", message);
            String json = MAPPER.writeValueAsString(result);
            httpResponse.getWriter().print(json);
        } catch (IOException e) {

            logger.error(e.getMessage());
        }
    }
}
