package com.open.boot.core.configurer;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.open.boot.core.Result;
import com.open.boot.core.ResultCode;
import com.open.boot.core.ServiceException;

/**
 * Spring MVC 配置
 */

/**
 * Spring MVC 配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	private final Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);
	@Value("${spring.profiles.active}")
	private String env;// 当前激活的配置文件

	// 使用阿里 FastJson 作为JSON MessageConverter
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
		FastJsonConfig config = new FastJsonConfig();
		config.setSerializerFeatures(SerializerFeature.WriteMapNullValue, // 保留空的字段
				SerializerFeature.WriteNullStringAsEmpty, // String null -> ""
				SerializerFeature.WriteNullNumberAsZero);// Number null -> 0
		// config.setDateFormat("yyyy-MM-dd HH:mm:ss");
		config.setDateFormat("");
		converter.setFastJsonConfig(config);
		List<MediaType> list = new ArrayList<>();
		list.add(MediaType.APPLICATION_JSON_UTF8);
		list.add(MediaType.APPLICATION_XML);
		list.add(MediaType.TEXT_XML);
		list.add(MediaType.TEXT_PLAIN);
		list.add(MediaType.TEXT_EVENT_STREAM);
		converter.setSupportedMediaTypes(list);
		converter.setDefaultCharset(Charset.forName("UTF-8"));
		for (int i = 0; i < converters.size(); i++) {
			if (converters.get(i) instanceof MappingJackson2HttpMessageConverter) {
				converters.set(i, converter);
			}
		}
		// converters.add(converter);
	}

	// 统一异常处理
	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		exceptionResolvers.add((request, response, handler, e) -> {
			Result result = new Result();
			if (e instanceof ServiceException) {// 业务失败的异常，如“账号或密码错误”
				result.setCode(ResultCode.FAIL).setMessage(e.getMessage());
				logger.info(e.getMessage());
			} else if (e instanceof NoHandlerFoundException) {
				result.setCode(ResultCode.NOT_FOUND).setMessage("接口 [" + request.getRequestURI() + "] 不存在");
			} else if (e instanceof ServletException) {
				result.setCode(ResultCode.FAIL).setMessage(e.getMessage());
			} else {
				result.setCode(ResultCode.INTERNAL_SERVER_ERROR)
						.setMessage("接口 [" + request.getRequestURI() + "] 内部错误或系统繁忙，请联系管理员");
				String message;
				if (handler instanceof HandlerMethod) {
					HandlerMethod handlerMethod = (HandlerMethod) handler;
					message = String.format("接口 [%s] 出现异常，方法：%s.%s，异常摘要：%s", request.getRequestURI(),
							handlerMethod.getBean().getClass().getName(), handlerMethod.getMethod().getName(),
							e.getMessage());
				} else {
					message = e.getMessage();
				}
				logger.error(message, e);
			}
			responseResult(response, result);
			return new ModelAndView();
		});
	}

	// 解决跨域问题
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// registry.addMapping("/**");
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
