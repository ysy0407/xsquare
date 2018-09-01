package com.xsquare.modules.xsquare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.xsquare.modules.xsquare.dao.CourseDao;
import com.xsquare.modules.xsquare.entity.CourseEntity;
import com.xsquare.modules.xsquare.service.CourseService;



@Service("courseService")
public class CourseServiceImpl implements CourseService {
	@Autowired
	private CourseDao courseDao;
	
	@Override
	public CourseEntity queryObject(Integer id){
		return courseDao.queryObject(id);
	}
	
	@Override
	public List<CourseEntity> queryList(Map<String, Object> map){
		return courseDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return courseDao.queryTotal(map);
	}
	
	@Override
	public void save(CourseEntity course){
		courseDao.save(course);
	}
	
	@Override
	public void update(CourseEntity course){
		courseDao.update(course);
	}
	
	@Override
	public void delete(Integer id){
		courseDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		courseDao.deleteBatch(ids);
	}
	
}
