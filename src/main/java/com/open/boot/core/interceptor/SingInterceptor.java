package com.open.boot.core.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.open.boot.common.CommonConstant;
import com.open.boot.core.Result;
import com.open.boot.core.ResultCode;


public class SingInterceptor implements HandlerInterceptor {
	private final Logger logger = LoggerFactory.getLogger(SingInterceptor.class);

	@Value("${spring.profiles.active}")
	private String env;// 当前激活的配置文件

	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o)
			throws Exception {
		if (env.equals("test")) {
			return true;
		}
		if (!env.equals("dev")) {
			if (httpServletRequest.getMethod().equals("OPTIONS")) {
				return true;
			}
			// 验证签名
			boolean pass = validateSign(httpServletRequest);
			if (pass) {
				return true;
			} else {
				logger.warn("签名认证失败，请求接口：{}，请求IP：{}，请求参数：{}", httpServletRequest.getRequestURI(),
						getIpAddress(httpServletRequest), JSON.toJSONString(httpServletRequest.getParameterMap()));
				Result result = new Result();
				result.setCode(ResultCode.UNSIGNED).setMessage("签名认证失败");
				responseResult(httpServletResponse, result);
				httpServletResponse.setStatus(402);
				return false;
			}
		} else {
			return true;
		}
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object o, Exception e) throws Exception {

	}

	/**
	 * 一个简单的签名认证，规则： 1. 将请求参数按ascii码排序 2. 拼接为a=value&b=value...这样的字符串（不包含sign）
	 * 3. 混合密钥（secret）进行md5获得签名，与请求的签名进行比较
	 */
	private boolean validateSign(HttpServletRequest request) {
		String requestSign = request.getHeader("sign");// 获得请求签名，如sign=19e907700db7ad91318424a97c54ed57
		if (StringUtils.isEmpty(requestSign)) {
			return false;
		}
		String linkString = request.getRequestURL().toString();
		//System.out.println(linkString + "&" + requestSign);
		String secret = CommonConstant.TOKEN_SERECT;// 密钥，自己修改949b815af0a60d605163496aecacc35d
		String sign = DigestUtils.md5Hex(linkString + secret);// 混合密钥md5
		//System.out.println(sign);
		return StringUtils.equals(sign, requestSign);// 比较
	}

	private String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		// 如果是多级代理，那么取第一个ip为客户端ip
		if (ip != null && ip.contains(",")) {
			ip = ip.substring(0, ip.indexOf(",")).trim();
		}
		return ip;
	}

	private void responseResult(HttpServletResponse response, Result result) {
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "application/json;charset=UTF-8");
		response.setStatus(200);
		try {
			response.getWriter().write(JSON.toJSONString(result));
		} catch (IOException ex) {
			logger.error(ex.getMessage());
		}
	}
}
