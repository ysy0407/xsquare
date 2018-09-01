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

import com.xsquare.modules.xsquare.entity.HandleSystemEntity;
import com.xsquare.modules.xsquare.service.HandleSystemService;
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
@RequestMapping("/xsquare/handlesystem")
public class HandleSystemController {
	@Autowired
	private HandleSystemService handleSystemService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("xsquare:handlesystem:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<HandleSystemEntity> handleSystemList = handleSystemService.queryList(query);
		int total = handleSystemService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(handleSystemList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("xsquare:handlesystem:info")
	public R info(@PathVariable("id") Integer id){
		HandleSystemEntity handleSystem = handleSystemService.queryObject(id);
		
		return R.ok().put("handleSystem", handleSystem);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("xsquare:handlesystem:save")
	public R save(@RequestBody HandleSystemEntity handleSystem){
		handleSystemService.save(handleSystem);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("xsquare:handlesystem:update")
	public R update(@RequestBody HandleSystemEntity handleSystem){
		handleSystemService.update(handleSystem);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("xsquare:handlesystem:delete")
	public R delete(@RequestBody Integer[] ids){
		handleSystemService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
