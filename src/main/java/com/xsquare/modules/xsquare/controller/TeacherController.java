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

import com.xsquare.modules.xsquare.entity.TeacherEntity;
import com.xsquare.modules.xsquare.service.TeacherService;
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
@RequestMapping("/xsquare/teacher")
public class TeacherController {
	@Autowired
	private TeacherService teacherService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("xsquare:teacher:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<TeacherEntity> teacherList = teacherService.queryList(query);
		int total = teacherService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(teacherList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("xsquare:teacher:info")
	public R info(@PathVariable("id") Integer id){
		TeacherEntity teacher = teacherService.queryObject(id);
		
		return R.ok().put("teacher", teacher);
	}
	
	/**
	 * 保存
	 */
	@SysLog("新增老师")
	@RequestMapping("/save")
	@RequiresPermissions("xsquare:teacher:save")
	public R save(@RequestBody TeacherEntity teacher){
		teacherService.save(teacher);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("xsquare:teacher:update")
	public R update(@RequestBody TeacherEntity teacher){
		teacherService.update(teacher);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@SysLog("删除老师")
	@RequestMapping("/delete")
	@RequiresPermissions("xsquare:teacher:delete")
	public R delete(@RequestBody Integer[] ids){
		teacherService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
