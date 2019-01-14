package com.open.boot.user.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.open.boot.core.AbstractService;
import com.open.boot.user.dao.UserMapper;
import com.open.boot.user.model.User;
import com.open.boot.user.service.IUserService;

/**
 * Created by actor-T on 2018/12/07.
 */
@Service
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class UserServiceImpl extends AbstractService<User> implements IUserService {
	@Autowired
	private UserMapper userMapper;

	@Override
	public User findByConditions(Map<String, String> map) {
		// TODO Auto-generated method stub
		return userMapper.queryByConditions(map);
	}
}
