package com.open.boot.core.configurer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * websorcket配置
 * 开启WebSocket支持
 * @author tys
 *
 */
@Configuration
public class WebSocketConfig{
	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}
}
