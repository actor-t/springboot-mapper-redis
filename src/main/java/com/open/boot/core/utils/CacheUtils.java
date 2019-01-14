package com.open.boot.core.utils;

import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;

import com.open.boot.core.context.SpringContextUtil;

/**
 * 本地缓存工具类
 * 
 * 本系统暂不做分布式，请使用spring缓存 @Cacheable
 * 
 */
public class CacheUtils {

	/**
	 * 获取本地CacheManager
	 * 
	 * @return 本地CacheManager
	 */
	public final static EhCacheCacheManager getCacheManagerInstance() {
		return (EhCacheCacheManager) SpringContextUtil.getBean("appEhCacheCacheManager");
	}

	/**
	 * 根据缓存名称，从本地CacheManager获取一个缓存实例
	 * 
	 * @author xiakun
	 * @param cacheName cacheName
	 * @return 缓存实例
	 */
	public final static Cache getCache(String cacheName) {
		return getCacheManagerInstance().getCache(cacheName);
	}

	// TODO：多级缓存同步等其他辅助功能，可以在此类添加

}