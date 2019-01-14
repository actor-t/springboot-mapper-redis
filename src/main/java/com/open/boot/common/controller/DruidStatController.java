package com.open.boot.common.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.stat.DruidStatManagerFacade;

@RestController
@RequestMapping("/druid")
public class DruidStatController {
	@RequestMapping("/druid/stat")
	public Object druidStat() {
		// DruidStatManagerFacade#getDataSourceStatDataList 该方法可以获取所有数据源的监控数据，除此之外
		// DruidStatManagerFacade 还提供了一些其他方法，你可以按需选择使用。
		return DruidStatManagerFacade.getInstance().getDataSourceStatDataList();
	}
}
