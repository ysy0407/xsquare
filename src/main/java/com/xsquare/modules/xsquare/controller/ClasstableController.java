package com.xsquare.modules.xsquare.controller;

import java.util.*;

import com.xsquare.common.annotation.SysLog;
import com.xsquare.common.utils.DateUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.xsquare.modules.xsquare.entity.ClasstableEntity;
import com.xsquare.modules.xsquare.service.ClasstableService;
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
@RequestMapping("/xsquare/classtable")
public class ClasstableController {
	@Autowired
	private ClasstableService classtableService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("xsquare:classtable:list")
	public R list(@RequestParam Map<String, Object> params){
		//如果不存在说明是刷新的时候查询的,应当将现在的年-W周,开始时间和结束时间放入
		if (!params.containsKey("startDate")) {
			Date nowDate = new Date();
			int year = Integer.valueOf(DateUtils.getDate().split("\\-")[0]);
			int weekOfYear = DateUtils.getWeekNoOfYear(nowDate);
			params.put("week", "\""+year+"-W"+weekOfYear+"\"");
			params.put("startDate", "\""+DateUtils.getDayNoOfWeekNo(year, weekOfYear, Calendar.MONDAY)+"\"");
			params.put("endDate", "\""+DateUtils.getDayNoOfWeekNo(year, weekOfYear, Calendar.SUNDAY)+"\"");
		}
		//放入选择的一周的时间
		String[] dateArray = DateUtils.getAllWeekDate(params.get("week").toString());
		params.put("dateArray", dateArray);
		//查询列表数据
        Query query = new Query(params);
		List<List<List<ClasstableEntity>>> classtableList = classtableService.queryList(query);

		int total = classtableService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(classtableList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

	/**
	 * 展示某一天特定的时间段的课程
	 * 	传入参数应有,日期和时间段
	 * @param params
	 * @return
	 */
	@RequestMapping("/theList")
	public R theList(@RequestParam Map<String, Object> params){
		//如果参数中没有date和startDate参数,说明是签到页面第一次查询,直接查询今日的课程表,
		if (!params.containsKey("startDate") && !params.containsKey("date")) {
			params.put("startDate", "\""+DateUtils.getDate()+"\"");
			params.put("endDate", "\""+DateUtils.getDate()+"\"");
		}
		//查询列表数据
		Query query = new Query(params);
		List<ClasstableEntity> classtableList = classtableService.queryTheList(query);
		int total = classtableService.queryTotal(query);

		PageUtils pageUtil = new PageUtils(classtableList, total, query.getLimit(), query.getPage());

		return R.ok().put("page", pageUtil);
	}


	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("xsquare:classtable:info")
	public R info(@PathVariable("id") Integer id){
		ClasstableEntity classtable = classtableService.queryObject(id);
		
		return R.ok().put("classtable", classtable);
	}
	
	/**
	 * 保存
	 */
	@SysLog("新增课程表")
	@RequestMapping("/save")
	@RequiresPermissions("xsquare:classtable:save")
	public R save(@RequestBody ClasstableEntity classtable){
		classtableService.save(classtable);
		
		return R.ok();
	}

	/**
	 * 复制课程表
	 */
	@SysLog("复制课程表")
	@Transactional
	@RequestMapping("/copy")
//	@RequiresPermissions("xsquare:classtable:save")
	public R copy(@RequestBody Map<String, Object> map){
		System.out.println("---------------map:"+map);
		String[] courseWeek = DateUtils.getAllWeekDate( "\""+map.get("courseWeek").toString()+"\"");
		String[] startWeek = DateUtils.getAllWeekDate( "\""+map.get("startWeek").toString()+"\"");
		String[] endWeek = DateUtils.getAllWeekDate( "\""+map.get("endWeek").toString()+"\"");
		//获取从选择的开始周的第一天的日期到结束周的最后一天
		List<String> array = DateUtils.getStartDateToEndDateFromDates(startWeek[0], endWeek[6]);
		//根据课程号和课程所在的开始和结束的日期查出当前课程的所有的课程表
		Map<String, Object> param = new HashMap<>();
		param.put("course", map.get("course"));
		param.put("startDate", "\""+courseWeek[0]+"\"");
		param.put("endDate", "\""+courseWeek[6]+"\"");
		List<ClasstableEntity> classtableList = classtableService.queryTheList(param);
		for (String date : array) {
			System.out.println("date:"+date);
			for (ClasstableEntity classtable : classtableList) {
				System.out.println("classtable.getDate():"+classtable.getDate());
				//原本的课程表星期数
				int classtableDayOfWeek = DateUtils.getDayOfWeek(classtable.getDate());
				//复制后的课程表的星期数
				int copyClasstableDayOfWeek = DateUtils.getDayOfWeek(date);
				System.out.println("classtableDayOfWeek:"+classtableDayOfWeek+"copyClasstableDayOfWeek:"+copyClasstableDayOfWeek);
				if (classtableDayOfWeek == copyClasstableDayOfWeek) {
					//初始化签到人数
					classtable.setSignNum(0);
					//设置上课日期
					classtable.setDate(date);
					//保存
					classtableService.save(classtable);
				}
			}
		}
		return R.ok();
	}

	/**
	 * 批量删除课程表
	 */
	@SysLog("批量删除课程表")
	@PostMapping("/del")
	public R del(@RequestBody Map<String, Object> map){
		//根据课程号和课程所在的开始和结束的日期查出当前课程的所有的课程表
		Map<String, Object> param = new HashMap<>();
		param.put("course", map.get("course"));
		param.put("startDate", "\""+map.get("startDate")+"\"");
		param.put("endDate", "\""+map.get("endDate")+"\"");
		List<ClasstableEntity> classtableList = classtableService.queryTheList(param);
		//将查出的课程表ID放入数组中
		Integer[] delClasstable = new Integer[classtableList.size()];
		for (int i = 0; i < classtableList.size(); i++) {
			delClasstable[i] = classtableList.get(i).getId();
		}
		//批量删除,修改状态
		classtableService.deleteBatch(delClasstable);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("xsquare:classtable:update")
	public R update(@RequestBody ClasstableEntity classtable){
		classtableService.update(classtable);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@SysLog("删除课程表")
	@RequestMapping("/delete")
	@RequiresPermissions("xsquare:classtable:delete")
	public R delete(@RequestBody Integer[] ids){
		classtableService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
