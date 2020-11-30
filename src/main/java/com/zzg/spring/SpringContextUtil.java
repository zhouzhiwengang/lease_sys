package com.zzg.spring;

import org.springframework.context.ApplicationContext;


public class SpringContextUtil {
	private static ApplicationContext applicationContext;

	public static void setApplicationContext(ApplicationContext applicationContext){
		// TODO Auto-generated method stub
		if(SpringContextUtil.applicationContext == null) {
			SpringContextUtil.applicationContext = applicationContext;
		}
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	// 通过class 获取bean
	public static <T> T getBean(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}
	
	// 通过name 获取bean
	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}
	
	  //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name,Class<T> clazz){
        return applicationContext.getBean(name, clazz);
    }
}
