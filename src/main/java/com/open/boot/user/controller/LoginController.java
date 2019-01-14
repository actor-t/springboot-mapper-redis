package com.open.boot.user.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.open.boot.common.CommonConstant;
import com.open.boot.core.Result;
import com.open.boot.core.ResultGenerator;
import com.open.boot.core.redis.RedisUtil;
import com.open.boot.core.utils.CommonUtil;
import com.open.boot.core.utils.DigestUtil;
import com.open.boot.user.model.User;
import com.open.boot.user.service.IAliyunDysmsService;
import com.open.boot.user.service.IUserService;

@RestController
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private IUserService userService;
	@Resource
	RedisUtil RedisUtil;
	@Autowired
	IAliyunDysmsService aliyunDysmsService;

	/**
	 * 注册
	 * 
	 * @param user 用户对象
	 * @return 注册对象
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody User user) {
		if (StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty((user.getPassword()))) {
			return ResultGenerator.genFailResult("用户名或密码不能为空!");
		}
		user.setPassword((DigestUtil.MD5Encode(user.getPassword(), CommonConstant.TOKEN_SERECT, false)));
		userService.saveSelective(user);
		user.setPassword("");
		return ResultGenerator.genSuccessResult(user);
	}

	/**
	 * 登录
	 * 
	 * @param request HttpServletRequest
	 * @param user    用户对象
	 * @return 返回登录结果
	 */
	public Result login(HttpServletRequest request, @RequestBody User user) {
		if (StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty((user.getPassword()))) {
			return ResultGenerator.genFailResult("用户名或密码不能为空!");
		}
		User user2 = checkLoginWay(user);
		if (user2 == null) {
			return ResultGenerator.genFailResult("用户名或密码错误");
		}
		// 将登陆日志写入数据库
		writeLog(user2, request);
		HttpSession session = request.getSession();
		String token = DigestUtil.MD5Encode(session.getId(), user.getUserName(), false);
		user2.setToken(token);
		RedisUtil.hmset(token + "_" + user.getId(), RedisUtil.objectToMapFast(user2), 60 * 60);
		return ResultGenerator.genSuccessResult(user2);
	}

	public void writeLog(User user, HttpServletRequest request) {

	}

	public User checkLoginWay(User user) {
		Map<String, String> map = new HashMap<>();
		User user2 = null;
		if (user.getUserName().matches(CommonUtil.tel)) {
			map.put("tel", user.getUserName());
			map.put("password", user.getPassword());
			user2 = userService.findByConditions(map);
			return user2;
		} else {
			map.put("userName", user.getUserName());
			map.put("password", user.getPassword());
			user2 = userService.findByConditions(map);
			return user2;
		}
	}

	/**
	 * 发送短信验证码 (区分 |注册|找回密码|时发送验证码)
	 * 
	 * @param tel  value = "发送短信,0注册短信 1找回密码", required = true, dataType = "String"
	 * @param type type
	 * @return 发送结果
	 */
	@RequestMapping(value = "/queryMsgCode")
	public Result queryMsgCode(@RequestParam("tel") String tel, @RequestParam("type") int type) {
		String sources = "123456789"; // 加上一些字母，就可以生成pc站的验证码
		Random rand = new Random();
		StringBuffer verifyCode = new StringBuffer();
		for (int j = 0; j < 4; j++) {
			verifyCode.append(sources.charAt(rand.nextInt(8)) + "");
		}
		boolean falg = aliyunDysmsService.sendVcodeSms(tel, verifyCode.toString(), "outId", type);
		if (falg) {
			RedisUtil.set(tel, verifyCode, 180);// 默认180S
			return ResultGenerator.genSuccessResult("发送成功");
		} else {
			return ResultGenerator.genFailResult("发送失败");
		}
	}

	/**
	 * 验证验证码
	 * 
	 * @param tel        电话号码
	 * @param verifyCode 验证码
	 * @return 验证结果
	 */
	@RequestMapping(value = "/verityMsgCode")
	public Result verityMsgCode(@RequestParam("tel") String tel, @RequestParam("verifyCode") String verifyCode) {
		if (RedisUtil.hasKey(tel)) {
			String vCode = RedisUtil.get(tel).toString();
			if (vCode.equals(verifyCode)) {
				return ResultGenerator.genSuccessResult("验证成功");
			} else {
				return ResultGenerator.genFailResult("验证失败");
			}
		} else {
			return ResultGenerator.genFailResult("验证码过期");
		}
	}
}
