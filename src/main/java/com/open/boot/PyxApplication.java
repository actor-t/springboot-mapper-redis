package com.open.boot;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.open.boot.core.context.SpringContextUtil;

@SpringBootApplication
@EnableTransactionManagement
@ServletComponentScan
@EnableCaching
@EnableScheduling
public class PyxApplication {

	public static void main(String[] args) {
		ApplicationContext app = SpringApplication.run(PyxApplication.class, args);
		SpringContextUtil.setApplicationContext(app);
	}
}
