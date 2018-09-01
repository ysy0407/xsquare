package com.xsquare.modules.xsquare.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.xsquare.modules.xsquare.entity.DictEntity;
import com.xsquare.modules.xsquare.service.DictService;
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
@RequestMapping("/xsquare/dict")
public class DictController {
	@Autowired
	private DictService dictService;
	
	/**
	 * 列表
	 */
	@GetMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		System.out.println(params);
		//查询列表数据
        Query query = new Query(params);

		List<DictEntity> dictList = dictService.queryList(query);
		int total = dictService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(dictList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("xsquare:dict:info")
	public R info(@PathVariable("id") Integer id){
		DictEntity dict = dictService.queryObject(id);
		
		return R.ok().put("dict", dict);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("xsquare:dict:save")
	public R save(@RequestBody DictEntity dict){
		dictService.save(dict);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("xsquare:dict:update")
	public R update(@RequestBody DictEntity dict){
		dictService.update(dict);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("xsquare:dict:delete")
	public R delete(@RequestBody Integer[] ids){
		dictService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
