package com.open.boot.user.dao;

import java.util.Map;

import org.apache.ibatis.annotations.SelectProvider;

import com.open.boot.core.Mapper;
import com.open.boot.user.dao.provider.UserProvider;
import com.open.boot.user.model.User;

public interface UserMapper extends Mapper<User> {
	@SelectProvider(type = UserProvider.class, method = "queryByConditions")
	User queryByConditions(Map<String, String> map);
}