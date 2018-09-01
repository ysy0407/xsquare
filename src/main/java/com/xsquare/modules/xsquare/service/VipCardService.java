package com.xsquare.modules.xsquare.service;

import com.xsquare.modules.xsquare.entity.VipCardEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author yusongyuan
 * @email ysy_0407@foxmail.com
 * @date 2018-02-22 16:13:09
 */
public interface VipCardService {
	
	VipCardEntity queryObject(String id);
	
	List<VipCardEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(VipCardEntity vipCard);
	
	void update(VipCardEntity vipCard);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
}
