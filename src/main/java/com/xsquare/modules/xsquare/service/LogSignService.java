package com.xsquare.modules.xsquare.service;

import com.xsquare.modules.xsquare.entity.LogSignEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author yusongyuan
 * @email ysy_0407@foxmail.com
 * @date 2018-02-22 16:13:09
 */
public interface LogSignService {
	
	LogSignEntity queryObject(Integer id);
	
	List<LogSignEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(LogSignEntity logSign);
	
	void update(LogSignEntity logSign);
	
	void delete(Map<String, Object> map);
	
	void deleteBatch(Integer[] ids);
}
