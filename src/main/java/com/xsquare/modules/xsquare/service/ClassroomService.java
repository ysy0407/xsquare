package com.xsquare.modules.xsquare.service;

import com.xsquare.modules.xsquare.entity.ClassroomEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author yusongyuan
 * @email ysy_0407@foxmail.com
 * @date 2018-02-22 16:13:09
 */
public interface ClassroomService {
	
	ClassroomEntity queryObject(Integer id);
	
	List<ClassroomEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ClassroomEntity classroom);
	
	void update(ClassroomEntity classroom);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
