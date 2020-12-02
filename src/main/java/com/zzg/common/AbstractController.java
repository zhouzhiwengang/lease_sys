package com.zzg.common;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.data.domain.PageRequest;

import com.zzg.common.constant.WebAppConstants;

public abstract class AbstractController {
	/**
	 * 参数分页参数转换校验：现在仅支持:get请求、参数格式:?page=1&limit=10
	 * 
	 * @param param
	 * @return
	 */
	protected PageRequest initPageBounds(Map<String, Object> paramter) {
		SimpleTypeConverter converter = new SimpleTypeConverter();
		// 页码、分页大小初始化设置值
		int page = 0;
		int limit = 10;
		if (paramter.get(WebAppConstants.PAGE) != null) {
			page = converter.convertIfNecessary(paramter.get(WebAppConstants.PAGE), int.class);
		}
		if (paramter.get(WebAppConstants.LIMIT) != null) {
			limit = converter.convertIfNecessary(paramter.get(WebAppConstants.LIMIT), int.class);
		}
		PageRequest pb = new PageRequest(page, limit);
		return pb;
	}

	public static void copyPropertiesIgnoreNull(Object src, Object target) {
		BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
	}

	public static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<String>();
		for (java.beans.PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null)
				emptyNames.add(pd.getName());
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}
}
