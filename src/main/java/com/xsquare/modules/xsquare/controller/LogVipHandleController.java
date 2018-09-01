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

import com.xsquare.modules.xsquare.entity.LogVipHandleEntity;
import com.xsquare.modules.xsquare.service.LogVipHandleService;
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
@RequestMapping("/xsquare/logviphandle")
public class LogVipHandleController {
	@Autowired
	private LogVipHandleService logVipHandleService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("xsquare:logviphandle:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<LogVipHandleEntity> logVipHandleList = logVipHandleService.queryList(query);
		int total = logVipHandleService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(logVipHandleList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("xsquare:logviphandle:info")
	public R info(@PathVariable("id") Integer id){
		LogVipHandleEntity logVipHandle = logVipHandleService.queryObject(id);
		
		return R.ok().put("logVipHandle", logVipHandle);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("xsquare:logviphandle:save")
	public R save(@RequestBody LogVipHandleEntity logVipHandle){
		logVipHandleService.save(logVipHandle);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("xsquare:logviphandle:update")
	public R update(@RequestBody LogVipHandleEntity logVipHandle){
		logVipHandleService.update(logVipHandle);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("xsquare:logviphandle:delete")
	public R delete(@RequestBody Integer[] ids){
		logVipHandleService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
