package com.xsquare.modules.xsquare.service;

import com.xsquare.modules.xsquare.entity.DictEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author yusongyuan
 * @email ysy_0407@foxmail.com
 * @date 2018-02-22 16:13:09
 */
public interface DictService {
	
	DictEntity queryObject(Integer id);
	
	List<DictEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(DictEntity dict);
	
	void update(DictEntity dict);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
