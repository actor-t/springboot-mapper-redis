package com.open.boot.core.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet Filter implementation class ServletFilter 注解注册过滤器：实现
 * javax.servlet.Filter接口 filterName 是过滤器的名字 urlPatterns 是需要过滤的请求
 * ，这里只过滤servlet/* 下面的所有请求
 */
@WebFilter(filterName = "ServletFilter", urlPatterns = "/*")
public class ServletFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub
//		System.out.println("初始化过滤器。");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
//		System.out.println("过滤器正在执行...");
//		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        //这里填写你允许进行跨域的主机ip
//		httpServletResponse.setHeader("Access-Control-Allow-Origin",httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        //允许的访问方法
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
        //Access-Control-Max-Age 用于 CORS 相关配置的缓存
        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept,access_token,sign"); //access_token,sign 为自定义head，用于前端传递token给后端
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true"); // 是否支持cookie跨域
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
//		System.out.println("过滤器被销毁。");
    }

}
