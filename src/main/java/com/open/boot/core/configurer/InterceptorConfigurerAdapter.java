package com.open.boot.core.configurer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.open.boot.core.interceptor.SessionInterceptor;
import com.open.boot.core.interceptor.SingInterceptor;

/**
 * 创建java类继承WebMvcConfigurerAdapter类，重写addInterceptors方法
 * 
 * @author tys
 *
 */
@Configuration
public class InterceptorConfigurerAdapter implements WebMvcConfigurer {

	@Bean
	SingInterceptor getSingInterceptor() {
		return new SingInterceptor();
	}

	@Bean
	SessionInterceptor getSessionInterceptor() {
		return new SessionInterceptor();
	}

	/**
	 * 该方法用于注册拦截器 可注册多个拦截器，多个拦截器组成一个拦截器链
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// addPathPatterns 添加路径
		// excludePathPatterns 排除路径
		registry.addInterceptor(getSingInterceptor()).addPathPatterns("/**").excludePathPatterns("/picture/**");
		registry.addInterceptor(getSessionInterceptor()).addPathPatterns("/**")
				.excludePathPatterns("/enum/type/queryAllEnums");
	
	}
}
