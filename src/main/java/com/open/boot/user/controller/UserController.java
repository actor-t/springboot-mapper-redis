package com.open.boot.user.controller;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.open.boot.core.CorePager;
import com.open.boot.core.Result;
import com.open.boot.core.ResultGenerator;
import com.open.boot.core.redis.RedisUtil;
import com.open.boot.core.utils.CommonUtil;
import com.open.boot.user.model.User;
import com.open.boot.user.service.IUserService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Condition;
import com.alibaba.fastjson.JSON;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
/**
* Created by actor-T on 2018/12/07.
*/
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;
	@Resource
	RedisUtil RedisUtil;
	
    @RequestMapping("/add")
    public Result add(@RequestBody User user) {
        userService.saveSelective(user);
        return ResultGenerator.genSuccessResult(user);
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        userService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/updateSelective")
    public Result update(@RequestBody User user) {
        userService.updateSelective(user);
        return ResultGenerator.genSuccessResult();
    }
    
    @RequestMapping("/updateAll")
    public Result updateAll(@RequestBody User user) {
        userService.updateAll(user);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        User user = userService.findById(id);
        return ResultGenerator.genSuccessResult(user);
    }

    @RequestMapping("/listAll")
    public Result listAll(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<User> list = userService.findAll();
        PageInfo<User> pageInfo = new PageInfo<User>(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
    
	@RequestMapping("/addList")
	public Result addList(@RequestBody List<User> list) {
		int count = userService.saveList(list);
		return ResultGenerator.genSuccessResult(count);
	}
	
	@RequestMapping("/updateList")
	public Result updateListUser(@RequestBody List<User> list) {
		for (User item : list) {
			userService.updateSelective(item);
		}
		return ResultGenerator.genSuccessResult();
	}
	
	/**
	 * actor-T 传分页对象就分页查询，不传就不分页
	 * 
	 * @param map paper对象和实体类objBean条件对象
	 * @return 查询结果
	 */
    @RequestMapping("/listByCondition")
	public Result listByCondition(@RequestBody Map<String, Object> map) {
		String pagerStr = CommonUtil.getMapValue(map, "pager");
		User objBean = JSON.parseObject(map.get("objBean").toString(), User.class);
		if (StringUtil.isEmpty(pagerStr)) {
			Condition condition = new Condition(User.class);
			condition.createCriteria().andEqualTo(objBean);
			List<User> list = userService.findByCondition(condition);
			return ResultGenerator.genSuccessResult(list);
		} else {
			CorePager pager = JSON.parseObject(pagerStr, CorePager.class);
			PageHelper.startPage(pager.getPageNum(), pager.getPageSize());
			Condition condition = new Condition(User.class);
			condition.createCriteria().andEqualTo(objBean);
			List<User> list = userService.findByCondition(condition);
			PageInfo<User> pageInfo = new PageInfo<>(list);
			return ResultGenerator.genSuccessResult(pageInfo);
		}
	}
	
	/**
	 * actor-T 根据条件更新objBean不为空的值
	 * 
	 * @param map objCondition为更新条件对象，objBean为更新实体内对象
	 * @return 查询结果
	 */
	@RequestMapping("/updateByConditionSelective")
    public Result updateByConditionSelective(@RequestBody Map<String, Object> map) {
        User objBean = JSON.parseObject(map.get("objBean").toString(), User.class);
       	User objCondition = JSON.parseObject(map.get("objCondition").toString(), User.class);
       	Condition condition = new Condition(User.class);
		condition.createCriteria().andEqualTo(objCondition);
		userService.updateByConditionSelective(objBean,objCondition);
        return ResultGenerator.genSuccessResult();
    }
}
