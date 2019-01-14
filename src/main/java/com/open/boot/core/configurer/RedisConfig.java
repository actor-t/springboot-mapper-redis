package com.open.boot.core.configurer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * redis配置类
 * 
 * @author tys
 *
 */
@Configuration
public class RedisConfig {
//	@Autowired
//	private RedisConfigProperties redis;

	// 选择redis作为默认缓存工具
//	@Bean
//	public CacheManager cacheManager(RedisTemplate<?, ?> redisTemplate) {
//		CacheManager cacheManager = new RedisCacheManager(redisTemplate);
//		return cacheManager;
//		/*
//		 * RedisCacheManager rcm = new RedisCacheManager(redisTemplate); //
//		 * 多个缓存的名称,目前只定义了一个 rcm.setCacheNames(Arrays.asList("thisredis"));
//		 * //设置缓存默认过期时间(秒) rcm.setDefaultExpiration(600); return rcm;
//		 */
//	}

	/*
	 * @Bean public RedisConnectionFactory redisConnectionFactory() {
	 * JedisPoolConfig jedisPoolConfig = new JedisPoolConfig(); // 最大空闲接连
	 * jedisPoolConfig.setMaxIdle(redis.getMaxIdle()); // 最小空闲连接
	 * jedisPoolConfig.setMinIdle(redis.getMinIdle()); // 连接池最大阻塞等待时间
	 * jedisPoolConfig.setMaxWaitMillis(redis.getMaxWait()); JedisConnectionFactory
	 * jedisConnectionFactory = new JedisConnectionFactory(); // 主机地址
	 * jedisConnectionFactory.setHostName(redis.getHost()); // 端口
	 * jedisConnectionFactory.setPort(redis.getPort()); // 密码
	 * jedisConnectionFactory.setPassword(redis.getPassword()); // 索引
	 * jedisConnectionFactory.setDatabase(redis.getDatabase()); // 超时时间
	 * jedisConnectionFactory.setTimeout(redis.getTimeOut());
	 * jedisConnectionFactory.setUsePool(true);
	 * jedisConnectionFactory.setPoolConfig(jedisPoolConfig); return
	 * jedisConnectionFactory; }
	 */

	/*
	 * @Bean public RedisTemplate<String, String>
	 * redisTemplate(RedisConnectionFactory factory,
	 * 
	 * @SuppressWarnings("rawtypes") RedisSerializer fastJson2JsonRedisSerializer) {
	 * StringRedisTemplate redisTemplate = new StringRedisTemplate(factory);
	 * redisTemplate.setConnectionFactory(redisConnectionFactory()); // redis 开启事务
	 * redisTemplate.setEnableTransactionSupport(true); // hash 使用jdk 的序列化
	 * redisTemplate.setHashValueSerializer(fastJson2JsonRedisSerializer new
	 * JdkSerializationRedisSerializer() ); // StringRedisSerializer key 序列化
	 * redisTemplate.setHashKeySerializer(new StringRedisSerializer()); //
	 * keySerializer 对key的默认序列化器。默认值是StringSerializer
	 * redisTemplate.setKeySerializer(new StringRedisSerializer()); //
	 * valueSerializer
	 * redisTemplate.setValueSerializer(fastJson2JsonRedisSerializer);
	 * redisTemplate.afterPropertiesSet(); return redisTemplate; }
	 */

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(factory);
		// jackson2JsonRedisSerializer
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
				Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		// stringRedisSerializer
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		// key采用String的序列化方式
		template.setKeySerializer(stringRedisSerializer);
		// hash的key也采用String的序列化方式
		template.setHashKeySerializer(stringRedisSerializer);
		// value序列化方式采用jackson
		template.setValueSerializer(jackson2JsonRedisSerializer);
		// hash的value序列化方式采用jackson
		template.setHashValueSerializer(jackson2JsonRedisSerializer);
		template.afterPropertiesSet();
		return template;
	}

}
