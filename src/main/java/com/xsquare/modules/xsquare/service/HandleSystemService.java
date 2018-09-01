package com.xsquare.modules.xsquare.service;

import com.xsquare.modules.xsquare.entity.HandleSystemEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author yusongyuan
 * @email ysy_0407@foxmail.com
 * @date 2018-02-22 16:13:09
 */
public interface HandleSystemService {
	
	HandleSystemEntity queryObject(Integer id);
	
	List<HandleSystemEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(HandleSystemEntity handleSystem);
	
	void update(HandleSystemEntity handleSystem);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
