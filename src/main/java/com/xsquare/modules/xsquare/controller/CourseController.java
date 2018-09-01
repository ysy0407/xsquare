package com.xsquare.modules.xsquare.controller;

import java.util.List;
import java.util.Map;

import com.xsquare.common.annotation.SysLog;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xsquare.modules.xsquare.entity.CourseEntity;
import com.xsquare.modules.xsquare.service.CourseService;
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
@RequestMapping("/xsquare/course")
public class CourseController {
	@Autowired
	private CourseService courseService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("xsquare:course:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<CourseEntity> courseList = courseService.queryList(query);
		int total = courseService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(courseList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("xsquare:course:info")
	public R info(@PathVariable("id") Integer id){
		CourseEntity course = courseService.queryObject(id);
		
		return R.ok().put("course", course);
	}
	
	/**
	 * 保存
	 */
	@SysLog("新增课程")
	@RequestMapping("/save")
	@RequiresPermissions("xsquare:course:save")
	public R save(@RequestBody JSONObject jsonObject){
		StringBuffer vipCardTypeStr = new StringBuffer();
		JSONObject courseJson = jsonObject.getJSONObject("course");
		JSONArray vipCardTypeArray = jsonObject.getJSONArray("vipCardType");
		CourseEntity course = (CourseEntity)JSONObject.toBean(courseJson, CourseEntity.class);
		if(vipCardTypeArray.size()>0){
			for(int i=0;i<vipCardTypeArray.size();i++){
				JSONObject vipCardType = vipCardTypeArray.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
				vipCardTypeStr.append(vipCardType.get("id")+"+"+vipCardType.get("initialMoney")+"+"+vipCardType.get("initialNumber")+"+"+vipCardType.get("status")+"|");
			}
		}
		course.setVipCardType(vipCardTypeStr.toString());

		courseService.save(course);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("xsquare:course:update")
	public R update(@RequestBody JSONObject jsonObject){
		StringBuffer vipCardTypeStr = new StringBuffer();
		JSONObject courseJson = jsonObject.getJSONObject("course");
		JSONArray vipCardTypeArray = jsonObject.getJSONArray("vipCardType");
		CourseEntity course = (CourseEntity)JSONObject.toBean(courseJson, CourseEntity.class);
		if(vipCardTypeArray.size()>0){
			for(int i=0;i<vipCardTypeArray.size();i++){
				JSONObject vipCardType = vipCardTypeArray.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
				vipCardTypeStr.append(vipCardType.get("id")+"+"+vipCardType.get("initialMoney")+"+"+vipCardType.get("initialNumber")+"+"+vipCardType.get("status")+"|");
			}
		}
		course.setVipCardType(vipCardTypeStr.toString());
		courseService.update(course);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@SysLog("删除课程")
	@RequestMapping("/delete")
	@RequiresPermissions("xsquare:course:delete")
	public R delete(@RequestBody Integer[] ids){
		courseService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
