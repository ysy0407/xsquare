package com.xsquare.modules.xsquare.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xsquare.modules.xsquare.entity.LogMoneyEntity;
import com.xsquare.modules.xsquare.service.LogMoneyService;
import com.xsquare.common.utils.PageUtils;
import com.xsquare.common.utils.Query;
import com.xsquare.common.utils.R;




/**
 * 
 * 
 * @author yusongyuan
 * @email ysy_0407@foxmail.com
 * @date 2018-02-22 16:13:09
 */
@RestController
@RequestMapping("/xsquare/logmoney")
public class LogMoneyController {
	@Autowired
	private LogMoneyService logMoneyService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("xsquare:logmoney:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<LogMoneyEntity> logMoneyList = logMoneyService.queryList(query);
		int total = logMoneyService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(logMoneyList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("xsquare:logmoney:info")
	public R info(@PathVariable("id") Integer id){
		LogMoneyEntity logMoney = logMoneyService.queryObject(id);
		
		return R.ok().put("logMoney", logMoney);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("xsquare:logmoney:save")
	public R save(@RequestBody LogMoneyEntity logMoney){
		logMoneyService.save(logMoney);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("xsquare:logmoney:update")
	public R update(@RequestBody LogMoneyEntity logMoney){
		logMoneyService.update(logMoney);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("xsquare:logmoney:delete")
	public R delete(@RequestBody Integer[] ids){
		logMoneyService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
