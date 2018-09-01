package com.xsquare.modules.xsquare.service;

import com.xsquare.modules.xsquare.entity.LogVipHandleEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author yusongyuan
 * @email ysy_0407@foxmail.com
 * @date 2018-02-22 16:13:09
 */
public interface LogVipHandleService {
	
	LogVipHandleEntity queryObject(Integer id);
	
	List<LogVipHandleEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(LogVipHandleEntity logVipHandle);
	
	void update(LogVipHandleEntity logVipHandle);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
