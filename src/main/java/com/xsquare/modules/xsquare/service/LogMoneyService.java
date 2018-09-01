package com.xsquare.modules.xsquare.service;

import com.xsquare.modules.xsquare.entity.LogMoneyEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author yusongyuan
 * @email ysy_0407@foxmail.com
 * @date 2018-02-22 16:13:09
 */
public interface LogMoneyService {
	
	LogMoneyEntity queryObject(Integer id);
	
	List<LogMoneyEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(LogMoneyEntity logMoney);
	
	void update(LogMoneyEntity logMoney);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
