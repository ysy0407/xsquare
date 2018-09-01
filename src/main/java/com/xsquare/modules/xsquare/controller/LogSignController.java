package com.xsquare.modules.xsquare.controller;

import java.util.List;
import java.util.Map;

import com.xsquare.common.annotation.SysLog;
import com.xsquare.common.exception.RRException;
import com.xsquare.common.utils.DateUtils;
import com.xsquare.modules.xsquare.entity.VipCardEntity;
import com.xsquare.modules.xsquare.service.VipCardService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xsquare.modules.xsquare.entity.LogSignEntity;
import com.xsquare.modules.xsquare.service.LogSignService;
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
@RequestMapping("/xsquare/logsign")
public class LogSignController {
	@Autowired
	private LogSignService logSignService;
	@Autowired
	private VipCardService vipCardService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("xsquare:logsign:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<LogSignEntity> logSignList = logSignService.queryList(query);
		int total = logSignService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(logSignList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("xsquare:logsign:info")
	public R info(@PathVariable("id") Integer id){
		LogSignEntity logSign = logSignService.queryObject(id);
		
		return R.ok().put("logSign", logSign);
	}
	
	/**
	 * 保存
	 */
	@SysLog("新增签到")
	@RequestMapping("/save")
	@RequiresPermissions("xsquare:logsign:save")
	public R save(@RequestBody LogSignEntity logSign){
		logSignService.save(logSign);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("xsquare:logsign:update")
	public R update(@RequestBody LogSignEntity logSign){
		logSignService.update(logSign);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@SysLog("撤销签到")
	@RequestMapping("/delete")
	@RequiresPermissions("xsquare:logsign:delete")
	public R delete(@RequestBody Map<String, Object> map){
		logSignService.delete(map);
		
		return R.ok();
	}
	
}
