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

import com.xsquare.modules.xsquare.entity.ClassroomEntity;
import com.xsquare.modules.xsquare.service.ClassroomService;
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
@RequestMapping("/xsquare/classroom")
public class ClassroomController {
	@Autowired
	private ClassroomService classroomService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("xsquare:classroom:list")
	public R list(@RequestParam Map<String, Object> params){
		if (!params.containsKey("status")) {
			params.put("status", 1);
		}
		//查询列表数据
        Query query = new Query(params);

		List<ClassroomEntity> classroomList = classroomService.queryList(query);
		int total = classroomService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(classroomList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("xsquare:classroom:info")
	public R info(@PathVariable("id") Integer id){
		ClassroomEntity classroom = classroomService.queryObject(id);
		
		return R.ok().put("classroom", classroom);
	}
	
	/**
	 * 保存
	 */
	@SysLog("新增教室")
	@RequestMapping("/save")
	@RequiresPermissions("xsquare:classroom:save")
	public R save(@RequestBody ClassroomEntity classroom){
		classroomService.save(classroom);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("xsquare:classroom:update")
	public R update(@RequestBody ClassroomEntity classroom){
		classroomService.update(classroom);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@SysLog("删除教室")
	@RequestMapping("/delete")
	@RequiresPermissions("xsquare:classroom:delete")
	public R delete(@RequestBody Integer[] ids){
		classroomService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
