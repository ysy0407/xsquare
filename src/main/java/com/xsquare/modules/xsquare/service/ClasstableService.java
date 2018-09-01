package com.xsquare.modules.xsquare.service;

import com.xsquare.modules.xsquare.entity.ClasstableEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author yusongyuan
 * @email ysy_0407@foxmail.com
 * @date 2018-02-22 16:13:09
 */
public interface ClasstableService {
	
	ClasstableEntity queryObject(Integer id);

	List<List<List<ClasstableEntity>>> queryList(Map<String, Object> map);

	List<ClasstableEntity> queryTheList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ClasstableEntity classtable);
	
	void update(ClasstableEntity classtable);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
