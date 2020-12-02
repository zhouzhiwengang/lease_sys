package com.zzg.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.Filter;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.zzg.filter.JWTFilter;
import com.zzg.shrio.MyRealm;

@Configuration
public class ShiroConfig {
	/**
	 * 先走 filter ，然后 filter 如果检测到请求头存在 token，则用 token 去 login，走 Realm 去验证
	 */
	@Bean
	public ShiroFilterFactoryBean factory(org.apache.shiro.mgt.SecurityManager securityManager) {
		ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
		// 添加自己的过滤器并且取名为jwt
		Map<String, Filter> filterMap = new LinkedHashMap<>();
		// 设置自定义的JWT过滤器
		filterMap.put("jwt", new JWTFilter());
		factoryBean.setFilters(filterMap);
		factoryBean.setSecurityManager(securityManager);

		Map<String, String> filterRuleMap = new HashMap<>();
		filterRuleMap.put("/common/login", "anon"); //简单登录接口排除
		filterRuleMap.put("/phone/login", "anon"); //邮箱登录接口排除
		filterRuleMap.put("/email/login", "anon"); //邮箱登录接口排除
		filterRuleMap.put("/sys/login", "anon"); //登录接口排除
		filterRuleMap.put("/sys/logout", "anon"); //登出接口排除
		filterRuleMap.put("/sys/kaptcha", "anon"); //验证码接口排除
		filterRuleMap.put("/sys/verification", "anon"); //验证码接口排除
		// 所有请求通过我们自己的JWT Filter
		filterRuleMap.put("/**", "jwt");
		factoryBean.setFilterChainDefinitionMap(filterRuleMap);
		return factoryBean;
	}

	/**
	 * 注入 securityManager
	 */
	@Bean
	public org.apache.shiro.mgt.SecurityManager securityManager(MyRealm customRealm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 设置自定义 realm.
		securityManager.setRealm(customRealm);
		/*
		 * 关闭shiro自带的session，详情见文档
		 * http://shiro.apache.org/session-management.html#SessionManagement-
		 * StatelessApplications%28Sessionless%29
		 */
		DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
		DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
		defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
		subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
		securityManager.setSubjectDAO(subjectDAO);
		return securityManager;
	}

	/**
	 * 添加注解支持
	 */
	@Bean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		// 强制使用cglib，防止重复代理和可能引起代理出错的问题
		// https://zhuanlan.zhihu.com/p/29161098
		defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
		return defaultAdvisorAutoProxyCreator;

	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(org.apache.shiro.mgt.SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}

	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

}
