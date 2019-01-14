package com.open.boot.user.service;

import java.util.Map;

import com.open.boot.core.Service;
import com.open.boot.user.model.User;

/**
 * Created by actor-T on 2018/12/07.
 */
public interface IUserService extends Service<User> {
	User findByConditions(Map<String, String> map);
}
