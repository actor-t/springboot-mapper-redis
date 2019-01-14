package ${basePackage}.controller;
import ${basePackage}.model.${modelNameUpperCamel};
import ${basePackage}.service.I${modelNameUpperCamel}Service;
import ${corePackage}.core.Result;
import ${corePackage}.core.ResultGenerator;
import ${corePackage}.core.redis.RedisUtil;
import ${corePackage}.core.CorePager;
import ${corePackage}.core.utils.CommonUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
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
* Created by ${author} on ${date}.
*/
@RestController
@RequestMapping("${baseRequestMapping}")
public class ${modelNameUpperCamel}Controller {
    @Autowired
    private I${modelNameUpperCamel}Service ${modelNameLowerCamel}Service;
	@Resource
	RedisUtil RedisUtil;
	
    @RequestMapping("/add")
    public Result add(@RequestBody ${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ${modelNameLowerCamel}Service.saveSelective(${modelNameLowerCamel});
        return ResultGenerator.genSuccessResult(${modelNameLowerCamel});
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        ${modelNameLowerCamel}Service.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/updateSelective")
    public Result update(@RequestBody ${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ${modelNameLowerCamel}Service.updateSelective(${modelNameLowerCamel});
        return ResultGenerator.genSuccessResult();
    }
    
    @RequestMapping("/updateAll")
    public Result updateAll(@RequestBody ${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ${modelNameLowerCamel}Service.updateAll(${modelNameLowerCamel});
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        ${modelNameUpperCamel} ${modelNameLowerCamel} = ${modelNameLowerCamel}Service.findById(id);
        return ResultGenerator.genSuccessResult(${modelNameLowerCamel});
    }

    @RequestMapping("/listAll")
    public Result listAll(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<${modelNameUpperCamel}> list = ${modelNameLowerCamel}Service.findAll();
        PageInfo<${modelNameUpperCamel}> pageInfo = new PageInfo<${modelNameUpperCamel}>(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
    
	@RequestMapping("/addList")
	public Result addList(@RequestBody List<${modelNameUpperCamel}> list) {
		int count = ${modelNameLowerCamel}Service.saveList(list);
		return ResultGenerator.genSuccessResult(count);
	}
	
	@RequestMapping("/updateList")
	public Result updateList${modelNameUpperCamel}(@RequestBody List<${modelNameUpperCamel}> list) {
		for (${modelNameUpperCamel} item : list) {
			${modelNameLowerCamel}Service.updateSelective(item);
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
		${modelNameUpperCamel} objBean = JSON.parseObject(map.get("objBean").toString(), ${modelNameUpperCamel}.class);
		if (StringUtil.isEmpty(pagerStr)) {
			Condition condition = new Condition(${modelNameUpperCamel}.class);
			condition.createCriteria().andEqualTo(objBean);
			List<${modelNameUpperCamel}> list = ${modelNameLowerCamel}Service.findByCondition(condition);
			return ResultGenerator.genSuccessResult(list);
		} else {
			CorePager pager = JSON.parseObject(pagerStr, CorePager.class);
			PageHelper.startPage(pager.getPageNum(), pager.getPageSize());
			Condition condition = new Condition(${modelNameUpperCamel}.class);
			condition.createCriteria().andEqualTo(objBean);
			List<${modelNameUpperCamel}> list = ${modelNameLowerCamel}Service.findByCondition(condition);
			PageInfo<${modelNameUpperCamel}> pageInfo = new PageInfo<>(list);
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
        ${modelNameUpperCamel} objBean = JSON.parseObject(map.get("objBean").toString(), ${modelNameUpperCamel}.class);
       	${modelNameUpperCamel} objCondition = JSON.parseObject(map.get("objCondition").toString(), ${modelNameUpperCamel}.class);
       	Condition condition = new Condition(${modelNameUpperCamel}.class);
		condition.createCriteria().andEqualTo(objCondition);
		${modelNameLowerCamel}Service.updateByConditionSelective(objBean,objCondition);
        return ResultGenerator.genSuccessResult();
    }
}
