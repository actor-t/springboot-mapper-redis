package com.open.boot.core;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.exceptions.TooManyResultsException;

import tk.mybatis.mapper.entity.Condition;

/**
 * Service 层 基础接口，其他Service 接口 请继承该接口
 */
public interface Service<T> {
	int saveAll(T model);// 保存一个实体，null的属性也会保存，不会使用数据库默认值

	int saveSelective(T model);// 只保存非空的字段

	int saveList(List<T> models);// 批量持久化

	int deleteById(Integer id);// 通过主鍵刪除

	int deleteByIds(String ids);// 批量刪除 eg：ids -> “1,2,3,4”

	int updateAll(T model);// 根据主键更新全部属性

	int updateSelective(T model);// 根据主键更新属性不为null的值

	T findById(Integer id);// 通过ID查找

	T findBy(String fieldName, Object value) throws TooManyResultsException; // 通过Model中某个成员变量名称（非数据表中column的名称）查找,value需符合unique约束

	List<T> findByIds(String ids);// 通过多个ID查找//eg：ids -> “1,2,3,4”

	List<T> findByCondition(Condition condition);// 根据条件查找

	List<T> findAll();// 获取所有

	/**
	 * 根据Condition条件更新实体record包含的不是null的属性值
	 * 
	 * @param record    实体类
	 * @param condition 条件
	 * @return 数量
	 */
	int updateByConditionSelective(@Param("record") T record, Object condition);

	/**
	 * 根据Condition条件更新实体record包含的全部属性，null值会被更新
	 * 
	 * @param record    实体类
	 * @param condition 条件
	 * @return 数量
	 */
	int updateByCondition(T record, Object condition);

	/**
	 * 根据Condition条件删除数据
	 * 
	 * @param condition 条件
	 * @return 删除数
	 */
	int deleteByCondition(Object condition);

	/**
	 * 根据Condition条件进行查询总数
	 * 
	 * @param condition 条件
	 * @return 总数
	 */
	int findCountByCondition(Object condition);

	/**
	 * 差异更新增加一个差异更新的方法 updateByDiffer 根据两个参数 old 和 newer 进行差异更新，当对应某个字段值不同时才会更新。
	 * 
	 * @param old
	 * @param newer
	 * @return 更新数量
	 */
	int UpdateByDiffer(T old, T newer);
}
