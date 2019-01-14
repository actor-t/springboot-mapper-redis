package com.open.boot.core.configurer;

import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
public class SchedulingConfig implements SchedulingConfigurer {
	private final Logger logger = LoggerFactory.getLogger(SchedulingConfig.class);

	@Scheduled(cron = "0 30 0 * * ? ") // 每天半夜12.30执行
	public void getToken() {
		logger.info("删除了个项目的数据");
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		// TODO Auto-generated method stub
		taskRegistrar.setScheduler(Executors.newScheduledThreadPool(10));
	}
}
