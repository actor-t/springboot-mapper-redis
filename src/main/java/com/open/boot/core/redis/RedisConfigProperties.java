package com.open.boot.core.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * redis配置
 * 
 * @author tys
 *
 */
@Configuration
@PropertySource(value = "classpath:application.yml")
@ConfigurationProperties(prefix = "spring.redis")
public class RedisConfigProperties {
	// 主机地址
	public String host;
	// 端口
	public int port;
	// 密码没有不填写
	public String password;
	// Redis数据库索引（默认为0）

	public int database;
	// 最大活跃连接
	public int maxActive;
	// 连接池最大阻塞等待时间（使用负值表示没有限制）
	public int maxWait;
	// 连接池中的最大空闲连接
	public int maxIdle;
	// 连接池中的最小空闲连接
	public int minIdle;
	// 连接超时时间（毫秒）
	public int timeOut;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getDatabase() {
		return database;
	}

	public void setDatabase(int database) {
		this.database = database;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public int getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

}
