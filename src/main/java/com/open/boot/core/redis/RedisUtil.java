package com.open.boot.core.redis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.util.StringUtil;

import tk.mybatis.mapper.entity.EntityColumn;

/**
 * redis工具类
 * 
 * @author tys
 *
 */
@Component
public final class RedisUtil {
	// @Autowired
	// private RedisTemplate<String, Object> redisTemplate;
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	/**
	 * 默认过期时长，单位：秒
	 */
	public static final long DEFAULT_EXPIRE = 60 * 60 * 24;

	/**
	 * 不设置过期时长
	 */
	public static final long NOT_EXPIRE = -1;

	// 属性和列对应
	protected Map<String, EntityColumn> propertyMap;

	// *******************************************转换****************************************
	// BeanUtilsmap转obj
	public Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
		if (map == null)
			return null;
		try {
			Object obj = beanClass.newInstance();
			org.apache.commons.beanutils.BeanUtils.populate(obj, map);
			return obj;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	// BeanUtilsobj转map
	public Map<?, ?> objectToMap(Object obj) {
		if (obj == null)
			return null;
		return new org.apache.commons.beanutils.BeanMap(obj);
	}

	// fastJson互转
	public Object mapToObjectFast(Map<Object, Object> map, Class<?> beanClass) throws Exception {
		return JSON.parseObject(JSON.toJSONString(map), beanClass);
	}

	public Map<String, Object> objectToMapFast(Object obj) {
		if (obj == null)
			return null;
		Map<String, Object> params = JSON.parseObject(JSONObject.toJSONString(obj, SerializerFeature.WriteMapNullValue,
				SerializerFeature.WriteNullStringAsEmpty));
		return params;
	}

	// =========================================================common========================================================
	/**
	 * 得到实体类不为空的字段集合
	 * 
	 * @param param 实体类对象
	 * @return map集合
	 */
	public Map<String, Object> getBeanValue(Object param) {
		Map<String, Object> map = new HashMap<>();
		MetaObject metaObject = SystemMetaObject.forObject(param);
		String[] properties = metaObject.getGetterNames();
		for (String property : properties) {
			// 属性和列对应Map中有此属性
			if (propertyMap.get(property) != null) {
				Object value = metaObject.getValue(property);
				// 属性值不为空
				if (value != null) {
					map.put(property, metaObject.getValue(property));
				}
			}
		}
		return map;
	}

	/**
	 * 拼接key
	 * 
	 * @param key key
	 * @return keys keys
	 */
	public String getRedisKey(String... key) {
		String keys = "";
		if (key != null && key.length > 0) {
			for (int i = 0; i < key.length; i++) {
				if (i == 0) {
					keys = key[i];
				} else {
					keys = keys + ":" + key[i];
				}
			}
		}
		return keys;
	}

	/**
	 * 
	 * 指定缓存失效时间
	 * 
	 * @param key  键
	 * 
	 * @param time 时间(秒)
	 * 
	 * @return 是否失效，true为失效
	 * 
	 */
	public boolean expire(String key, long time) {
		try {
			if (time > 0) {
				redisTemplate.expire(key, time, TimeUnit.SECONDS);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * 根据key 获取过期时间
	 * 
	 * @param key 键 不能为null
	 * 
	 * @return 时间(秒) 返回0代表为永久有效
	 * 
	 */
	public long getExpire(String key) {
		return redisTemplate.getExpire(key, TimeUnit.SECONDS);
	}

	/**
	 * 
	 * 判断key是否存在
	 * 
	 * @param key 键
	 * 
	 * @return true 存在 false不存在
	 * 
	 */
	public boolean hasKey(String key) {
		try {
			return redisTemplate.hasKey(key);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * 删除缓存
	 * 
	 * @param key 可以传一个值 或多个
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void del(String... key) {
		if (key != null && key.length > 0) {
			if (key.length == 1) {
				redisTemplate.delete(key[0]);
			} else {
				redisTemplate.delete(CollectionUtils.arrayToList(key));
			}
		}
	}

	// ============================String=============================
	/**
	 * 
	 * 普通缓存获取
	 * 
	 * @param key 键
	 * 
	 * @return 值
	 * 
	 */

	public Object get(String key) {
		return key == null ? null : redisTemplate.opsForValue().get(key);
	}

	/**
	 * 返回key所对应的value值得长度
	 * 
	 * @param key 键
	 * @return 长度
	 */
	public Long size(String key) {
		return redisTemplate.opsForValue().size(key);
	}

	/**
	 * 截取key所对应的value字符串
	 * 
	 * @param key   键
	 * @param start 开始位置
	 * @param end   结束位置
	 * @return 结果
	 */
	String get(String key, long start, long end) {
		return redisTemplate.opsForValue().get(key, start, end);
	}

	/**
	 * 
	 * 普通缓存放入
	 * 
	 * @param key   键
	 * 
	 * @param value 值
	 * 
	 * @return true成功 false失败
	 * 
	 */
	public boolean set(String key, Object value) {
		try {
			redisTemplate.opsForValue().set(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 设置键的字符串值并返回其旧值
	 * 
	 * @param key   键
	 * @param value 值
	 * @return 旧值或失败
	 */
	public Object getAndSet(String key, Object value) {
		try {
			return redisTemplate.opsForValue().getAndSet(key, value);
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * 如果key已经存在并且是一个字符串，则该命令将该值追加到字符串的末尾。如果键不存在，则它被创建并设置为空字符串，因此APPEND在这种特殊情况下将类似于SET
	 * 
	 * @param key   键
	 * @param value 值
	 * @return 数
	 */
	public Integer append(String key, String value) {
		try {
			return redisTemplate.opsForValue().append(key, value);
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * 
	 * 普通缓存放入并设置时间
	 * 
	 * @param key   键
	 * 
	 * @param value 值
	 * 
	 * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
	 * 
	 * @return true成功 false 失败
	 * 
	 */

	public boolean set(String key, Object value, long time) {
		try {
			if (time > 0) {
				redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
			} else {
				set(key, value);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * 递增
	 * 
	 * @param key   键
	 * 
	 * @param delta 要增加几(大于0)
	 * 
	 * @return 之后的数
	 * 
	 */
	public long incr(String key, long delta) {
		if (delta < 0) {
			throw new RuntimeException("递增因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, delta);
	}

	/**
	 * 
	 * 递减
	 * 
	 * @param key   键
	 * 
	 * @param delta 要减少几(小于0)
	 * 
	 * @return 之后的数
	 * 
	 */

	public long decr(String key, long delta) {
		if (delta < 0) {
			throw new RuntimeException("递减因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, -delta);
	}

	/**
	 * 递增浮点数
	 * 
	 * @param key   键
	 * @param delta 要增加几(大于0)
	 * @return 之后的数
	 */
	public Double incr(String key, Double delta) {
		if (delta < 0) {
			throw new RuntimeException("递增因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, delta);
	}

	/**
	 * 递减浮点
	 * 
	 * @param key   键
	 * @param delta delta 要减少几(小于0)
	 * @return delta 之后的数
	 */
	public Double decr(String key, Double delta) {
		if (delta < 0) {
			throw new RuntimeException("递减因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, -delta);
	}

	// 返回所有(一个或多个)给定 key 的值
	public List<Object> MGet(List<String> keys) {
		return redisTemplate.opsForValue().multiGet(keys);
	}

	// ================================================================hash=================================================================
	/**
	 * 
	 * HashGet
	 * 
	 * @param key  键 不能为null
	 * 
	 * @param item 项 不能为null
	 * 
	 * @return 值
	 * 
	 */
	public Object hget(String key, String item) {
		return redisTemplate.opsForHash().get(key, item);
	}

	/**
	 * 
	 * 获取hashKey对应的所有键值
	 * 
	 * @param key 键
	 * 
	 * @return 对应的多个键值
	 * 
	 */

	public Map<Object, Object> hmget(String key) {
		return redisTemplate.opsForHash().entries(key);
	}

	/**
	 * 
	 * HashSet
	 * 
	 * @param key 键
	 * 
	 * @param map 对应多个键值
	 * 
	 * @return true 成功 false 失败
	 * 
	 */
	public boolean hmset(String key, Map<String, Object> map) {
		try {
			redisTemplate.opsForHash().putAll(key, map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * HashSet 并设置时间
	 * 
	 * @param key  键
	 * 
	 * @param map  对应多个键值
	 * 
	 * @param time 时间(秒)
	 * 
	 * @return true成功 false失败
	 * 
	 */
	public boolean hmset(String key, Map<String, Object> map, long time) {
		try {
			redisTemplate.opsForHash().putAll(key, map);
			if (time > 0) {
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * 向一张hash表中放入数据,如果不存在将创建
	 * 
	 * @param key   键
	 * 
	 * @param item  项
	 * 
	 * @param value 值
	 * 
	 * @return true 成功 false失败
	 * 
	 */

	public boolean hset(String key, String item, Object value) {
		try {
			redisTemplate.opsForHash().put(key, item, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * 向一张hash表中放入数据,如果不存在将创建时间
	 * 
	 * @param key   键
	 * 
	 * @param item  项
	 * 
	 * @param value 值
	 * 
	 * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
	 * 
	 * @return true 成功 false失败
	 * 
	 */

	public boolean hset(String key, String item, Object value, long time) {
		try {
			redisTemplate.opsForHash().put(key, item, value);
			if (time > 0) {
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * 删除hash表中的值
	 * 
	 * @param key  键 不能为null
	 * 
	 * @param item 项 可以是多个 不能为null
	 * 
	 */

	public void hdel(String key, Object... item) {
		redisTemplate.opsForHash().delete(key, item);
	}

	/**
	 * 
	 * 判断hash表中是否有该项的值
	 * 
	 * @param key  键 不能为null
	 * 
	 * @param item 项 不能为null
	 * 
	 * @return true 存在 false不存在
	 * 
	 */

	public boolean hHasKey(String key, String item) {
		return redisTemplate.opsForHash().hasKey(key, item);
	}

	/**
	 * 
	 * hash递增 如果不存在,就会创建一个 并把新增后的值返回
	 * 
	 * @param key  键
	 * 
	 * @param item 项
	 * 
	 * @param by   要增加几(大于0)
	 * 
	 * @return 新增后的hash值
	 * 
	 */

	public double hincr(String key, String item, double by) {
		return redisTemplate.opsForHash().increment(key, item, by);
	}

	/**
	 * 
	 * hash递减
	 * 
	 * @param key  键
	 * 
	 * @param item 项
	 * 
	 * @param by   要减少记(小于0)
	 * 
	 * @return 递减后的hash值
	 * 
	 */

	public double hdecr(String key, String item, double by) {
		return redisTemplate.opsForHash().increment(key, item, -by);
	}

	// =============================================list======================================

	/**
	 * 
	 * 获取list缓存的内容
	 * 
	 * @param key   键
	 * 
	 * @param start 开始
	 * 
	 * @param end   结束 0 到 -1代表所有值
	 * 
	 * @return list缓存中的内容
	 * 
	 */
	public List<Object> lGet(String key, long start, long end) {
		try {
			return redisTemplate.opsForList().range(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * 获取list缓存的长度
	 * 
	 * @param key 键
	 * 
	 * @return list缓存的长度
	 * 
	 */
	public long lGetListSize(String key) {
		try {
			return redisTemplate.opsForList().size(key);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 
	 * 返回列表 key 中，下标为 index 的元素
	 * 
	 * @param key   键
	 * 
	 * @param index 索引 index'大于='0时， 0 表头，1
	 *              第二个元素，依次类推；index'小于'0时，-1，表尾，-2倒数第二个元素，依次类推
	 * 
	 * @return 获取list中的值
	 * 
	 */
	public Object lGetIndex(String key, long index) {
		try {
			return redisTemplate.opsForList().index(key, index);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将list放入缓存
	 * 
	 * @param key   键
	 * @param value 值
	 * @return 是否成功将list放入缓存
	 */
	public boolean lSet(String key, Object value) {
		try {
			redisTemplate.opsForList().rightPush(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * 将list放入缓存
	 * 
	 * @param key   键
	 * 
	 * @param value 值
	 * 
	 * @param time  时间(秒)
	 * 
	 * @return 是否成功将list放入缓存
	 * 
	 */
	public boolean lSet(String key, Object value, long time) {
		try {
			redisTemplate.opsForList().rightPush(key, value);
			if (time > 0)
				expire(key, time);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * 将list放入缓存
	 * 
	 * @param key   键
	 * 
	 * @param value 值
	 * 
	 * 
	 * 
	 * @return 是否成功将list放入缓存
	 * 
	 */

	public boolean lSet(String key, List<Object> value) {
		try {
			redisTemplate.opsForList().rightPushAll(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * 将list放入缓存
	 * 
	 * 
	 * 
	 * @param key   键
	 * 
	 * @param value 值
	 * 
	 * @param time  时间(秒)
	 * 
	 * @return 是否成功将list放入缓存
	 * 
	 */
	public boolean lSet(String key, List<Object> value, long time) {
		try {
			redisTemplate.opsForList().rightPushAll(key, value);
			if (time > 0)
				expire(key, time);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * 根据索引修改list中的某条数据
	 * 
	 * @param key   键
	 * 
	 * @param index 索引
	 * 
	 * @param value 值
	 * 
	 * @return 操作结果
	 * 
	 */
	public boolean lUpdateIndex(String key, long index, Object value) {
		try {
			redisTemplate.opsForList().set(key, index, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * 从存储的列表中删除第一次count出现且值为value的元素
	 * 
	 * @param key   键
	 * 
	 * @param count 移除多少个
	 * 
	 * @param value 值
	 * 
	 * @return 移除的个数
	 * 
	 */
	public long lRemove(String key, long count, Object value) {
		try {
			Long remove = redisTemplate.opsForList().remove(key, count, value);
			return remove;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 分页查询
	public List<Object> LRange(String key, long start, long end) {
		return redisTemplate.opsForList().range(key, start, end);
	}

	// 列表元素左边添加
	public long LInsert(String key, Object pivot, Object value) {
		if (StringUtil.isEmpty(key)) {
			return 0;
		}
		return redisTemplate.opsForList().leftPush(key, pivot, value);
	}

	// 删除并返回存储在列表中的第一个元素key
	public Object LPop(String key) {
		return redisTemplate.opsForList().leftPop(key);
	}

	// ==============================================================SET===============================================================

	/**
	 * 
	 * 根据key获取Set中的所有值
	 * 
	 * @param key 键
	 * 
	 * @return set中的值
	 * 
	 */

	public Set<Object> sGet(String key) {
		try {
			return redisTemplate.opsForSet().members(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * 根据value从一个set中查询,是否存在
	 * 
	 * @param key   键
	 * 
	 * @param value 值
	 * 
	 * @return true 存在 false不存在
	 * 
	 */
	public boolean sHasKey(String key, Object value) {
		try {
			return redisTemplate.opsForSet().isMember(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * 将数据放入set缓存
	 * 
	 * @param key    键
	 * 
	 * @param values 值 可以是多个
	 * 
	 * @return 成功个数
	 * 
	 */
	public long sSet(String key, Object... values) {
		try {
			return redisTemplate.opsForSet().add(key, values);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 
	 * 将set数据放入缓存
	 * 
	 * @param key    键
	 * 
	 * @param time   时间(秒)
	 * 
	 * @param values 值 可以是多个
	 * 
	 * @return 成功个数
	 * 
	 */
	public long sSetAndTime(String key, long time, Object... values) {
		try {
			Long count = redisTemplate.opsForSet().add(key, values);
			if (time > 0)
				expire(key, time);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 
	 * 获取set缓存的长度
	 * 
	 * @param key 键
	 * 
	 * @return set缓存中的长度
	 * 
	 */
	public long sGetSetSize(String key) {
		try {
			return redisTemplate.opsForSet().size(key);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 
	 * 移除值为value的
	 * 
	 * @param key    键
	 * 
	 * @param values 值 可以是多个
	 * 
	 * @return 移除的个数
	 * 
	 */
	public long setRemove(String key, Object... values) {
		try {
			Long count = redisTemplate.opsForSet().remove(key, values);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	// ******************************************************ZSET*********************************************************************

	public void ZAdd(String key, Double score, Object value) {
		redisTemplate.opsForZSet().add(key, value, score);
	}

	// 移除
	public Long ZRem(String key, Object... values) {
		return redisTemplate.opsForZSet().remove(key, values);
	}

	// 成员o的socre值
	public Double ZScore(String key, Object o) {
		return redisTemplate.opsForZSet().score(key, o);
	}

	// 下标参数 start 和 stop 都以 0 为底,-1 表示最后一个成员,升序
	public Set<Object> ZRange(String key, Integer start, Integer end) {
		return redisTemplate.opsForZSet().range(key, start, end);
	}

	// 降序
	public Set<Object> ZReverseRange(String key, Integer start, Integer end) {
		return redisTemplate.opsForZSet().reverseRange(key, start, end);
	}

	// 带有score的降序
	public Iterator<TypedTuple<Object>> reverseRangeWithScores(String key, Integer start, Integer end) {
		Set<TypedTuple<Object>> set = redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
		Iterator<TypedTuple<Object>> iterator = set.iterator();
		return iterator;
	}

	// 所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。有序集成员按 score 值递增(从小到大)次序排列
	public Set<Object> ZRangeByScore(String key, Double min, Double max) {
		return redisTemplate.opsForZSet().rangeByScore(key, min, max);
	}

	// 带有score
	public Set<TypedTuple<Object>> ZRangeByScoreWithScores(String key, Double min, Double max) {
		return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max);
	}
}
