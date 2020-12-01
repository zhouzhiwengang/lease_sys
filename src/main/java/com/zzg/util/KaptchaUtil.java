package com.zzg.util;

import java.util.Properties;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

public class KaptchaUtil {
	private static KaptchaUtil instance;
	 
	private KaptchaUtil() {
	};
 
	public static KaptchaUtil getInstance() {
		if (instance == null) {
			instance = new KaptchaUtil();
		}
		return instance;
	}
 
	/**
	 * 生成DefaultKaptcha 默认配置
	 * @return
	 */
	public DefaultKaptcha produce() {
		Properties properties = new Properties();
		properties.put("kaptcha.border", "no");
		properties.put("kaptcha.border.color", "105,179,90");
		properties.put("kaptcha.textproducer.font.color", "blue");
		properties.put("kaptcha.image.width", "100");
		properties.put("kaptcha.image.height", "50");
		properties.put("kaptcha.textproducer.font.size", "27");
		properties.put("kaptcha.session.key", "code");
		properties.put("kaptcha.textproducer.char.length", "4");
		properties.put("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
		properties.put("kaptcha.textproducer.char.string", "0123456789ABCEFGHIJKLMNOPQRSTUVWXYZ");
		properties.put("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.WaterRipple");
		properties.put("kaptcha.noise.color", "black");
		properties.put("kaptcha.noise.impl", "com.google.code.kaptcha.impl.DefaultNoise");
		properties.put("kaptcha.background.clear.from", "185,56,213");
		properties.put("kaptcha.background.clear.to", "white");
		properties.put("kaptcha.textproducer.char.space", "3");
		
		Config config = new Config(properties);
		DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
		defaultKaptcha.setConfig(config);
		return defaultKaptcha;
	}

}
