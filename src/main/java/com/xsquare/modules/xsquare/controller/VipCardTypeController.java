package com.xsquare.modules.xsquare.controller;

import java.util.List;
import java.util.Map;

import com.xsquare.common.annotation.SysLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xsquare.modules.xsquare.entity.VipCardTypeEntity;
import com.xsquare.modules.xsquare.service.VipCardTypeService;
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
@RequestMapping("/xsquare/vipcardtype")
public class VipCardTypeController {
	@Autowired
	private VipCardTypeService vipCardTypeService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("xsquare:vipcardtype:list")
	public R list(@RequestParam Map<String, Object> params){
		System.out.println(params);
		//查询列表数据
        Query query = new Query(params);

		List<VipCardTypeEntity> vipCardTypeList = vipCardTypeService.queryList(query);
		int total = vipCardTypeService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(vipCardTypeList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("xsquare:vipcardtype:info")
	public R info(@PathVariable("id") Integer id){
		VipCardTypeEntity vipCardType = vipCardTypeService.queryObject(id);
		
		return R.ok().put("vipCardType", vipCardType);
	}
	
	/**
	 * 保存
	 */
	@SysLog("新增会员卡类型")
	@RequestMapping("/save")
	@RequiresPermissions("xsquare:vipcardtype:save")
	public R save(@RequestBody VipCardTypeEntity vipCardType){
		vipCardTypeService.save(vipCardType);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("xsquare:vipcardtype:update")
	public R update(@RequestBody VipCardTypeEntity vipCardType){
		vipCardTypeService.update(vipCardType);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@SysLog("删除会员卡类型")
	@RequestMapping("/delete")
	@RequiresPermissions("xsquare:vipcardtype:delete")
	public R delete(@RequestBody Integer[] ids){
		vipCardTypeService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
