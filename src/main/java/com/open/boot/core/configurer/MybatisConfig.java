package com.open.boot.core.configurer;

import static com.open.boot.core.CoreConstant.BASE_PACKAGE;
import static com.open.boot.core.CoreConstant.MAPPER_INTERFACE_REFERENCE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.ObjectUtils;

import com.github.pagehelper.PageHelper;
import com.open.boot.core.interceptor.CameHumpInterceptor;

import tk.mybatis.spring.mapper.MapperScannerConfigurer;

/**
 * Mybatis,Mapper,PageHelper 配置
 *
 */
@Configuration
public class MybatisConfig {

	@Bean
	@ConfigurationProperties(prefix = "mybatis.configuration")
	public org.apache.ibatis.session.Configuration gloablConfiguration() {
		return new org.apache.ibatis.session.Configuration();
	}

	@Bean
	public SqlSessionFactory sqlSessionFactoryBean(@Qualifier("dataSource") DataSource dataSource,
			org.apache.ibatis.session.Configuration gloablConfiguration) throws Exception {
		SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
		factory.setDataSource(dataSource);
		// 配置分页插件，详情请查阅官方文档
		PageHelper pageHelper = new PageHelper();
		Properties properties = new Properties();
		properties.setProperty("pageSizeZero", "true");// 分页尺寸为0时查询所有纪录不再执行分页
		properties.setProperty("reasonable", "true");// 页码<=0
														// 查询第一页，页码>=总页数查询最后一页
		properties.setProperty("supportMethodsArguments", "true");// 支持通过 Mapper
																	// 接口参数来传递分页参数
		pageHelper.setProperties(properties);

		// 查询结果 Map 类型下画线Key 转小写驼峰形式
		CameHumpInterceptor cameHumpInterceptor = new CameHumpInterceptor();
		// 添加插件
		factory.setPlugins(new Interceptor[] { pageHelper, cameHumpInterceptor });

		// 添加XML目录
		// 如果没有此目录的话，后续初始化SqlSessionFactory会报错，这里参考MybatisProperties的代码
		// 这样纯使用注解，没有mapper目录也可以了
		org.springframework.core.io.Resource[] resources = null;
		if (!ObjectUtils.isEmpty(resources = resolveMapperLocations("classpath:mapper/**/*.xml"))) {
			factory.setMapperLocations(resources);
		}
		return factory.getObject();
	}

	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
		mapperScannerConfigurer.setBasePackage(BASE_PACKAGE + ".**.dao");

		// 配置通用Mapper，详情请查阅官方文档
		Properties properties = new Properties();
		properties.setProperty("mappers", MAPPER_INTERFACE_REFERENCE);
		properties.setProperty("notEmpty", "false");
		properties.setProperty("IDENTITY", "MYSQL");
		mapperScannerConfigurer.setProperties(properties);

		return mapperScannerConfigurer;
	}

	public org.springframework.core.io.Resource[] resolveMapperLocations(String... mapperLocations) {
		PathMatchingResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
		ArrayList<Resource> resources = new ArrayList<Resource>();
		if (mapperLocations != null) {
			int total = mapperLocations.length;
			for (int i = 0; i < total; ++i) {
				String mapperLocation = mapperLocations[i];
				try {
					org.springframework.core.io.Resource[] mappers = resourceResolver.getResources(mapperLocation);
					resources.addAll(Arrays.asList(mappers));
				} catch (IOException ex) {
					;
				}
			}
		}
		return (org.springframework.core.io.Resource[]) resources
				.toArray(new org.springframework.core.io.Resource[resources.size()]);
	}
}
