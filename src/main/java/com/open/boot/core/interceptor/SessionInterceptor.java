package com.open.boot.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.open.boot.core.utils.CacheUtils;

/**
 * 用户session拦截器 UserSessionInterceptor.java
 *
 * @author tys
 */
public class SessionInterceptor implements HandlerInterceptor {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${spring.profiles.active}")
	private String env;// 当前激活的配置文件

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info(request.getRequestURI() + "_tyslog");
		if (env.equals("dev") || env.equals("test")) {
			return true;
		}
		if (request.getMethod().equals("OPTIONS")) {
			return true;
		}
		String token = request.getHeader("access_token");
		if (StringUtils.isNotEmpty(token)) {
			if (CacheUtils.getCache("tokenCache").get(token) != null) {
				return true;
			}
		}
		response.setStatus(401);
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}
}
