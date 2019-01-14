package com.open.boot.user.dao.provider;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import com.open.boot.core.utils.CommonUtil;

public class UserProvider {

	/**
	 * 通过用户名，邮箱，手机号登录
	 * 
	 * @param map userName,email,tel,password
	 * @return string
	 */
	public String queryByConditions(Map<String, Object> map) {
		String userName = CommonUtil.getMapValue(map, "userName");
		String email = CommonUtil.getMapValue(map, "email");
		String tel = CommonUtil.getMapValue(map, "tel");
		String password = CommonUtil.getMapValue(map, "password");
		return new SQL() {
			{
				SELECT("*");
				FROM("t_user");
				if (StringUtils.isNotEmpty(userName)) {
					WHERE("user_name = #{userName}");
				}
				if (StringUtils.isNotEmpty(email)) {
					WHERE("email = #{email}");
				}
				if (StringUtils.isNotEmpty(tel)) {
					WHERE("tel = #{tel}");
				}
				if (StringUtils.isNotEmpty(password)) {
					WHERE("password = #{password}");
				}
			}
		}.toString();
	}
}
