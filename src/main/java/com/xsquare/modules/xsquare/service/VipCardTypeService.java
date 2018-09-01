package com.xsquare.modules.xsquare.service;

import com.xsquare.modules.xsquare.entity.VipCardTypeEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author yusongyuan
 * @email ysy_0407@foxmail.com
 * @date 2018-02-22 16:13:09
 */
public interface VipCardTypeService {
	
	VipCardTypeEntity queryObject(Integer id);
	
	List<VipCardTypeEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(VipCardTypeEntity vipCardType);
	
	void update(VipCardTypeEntity vipCardType);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
