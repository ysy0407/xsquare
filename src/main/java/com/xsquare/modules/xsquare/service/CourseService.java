package com.xsquare.modules.xsquare.service;

import com.xsquare.modules.xsquare.entity.CourseEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author yusongyuan
 * @email ysy_0407@foxmail.com
 * @date 2018-02-22 16:13:09
 */
public interface CourseService {
	
	CourseEntity queryObject(Integer id);
	
	List<CourseEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(CourseEntity course);
	
	void update(CourseEntity course);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
