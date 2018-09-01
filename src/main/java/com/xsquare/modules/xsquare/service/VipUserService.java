package com.xsquare.modules.xsquare.service;

import com.xsquare.modules.xsquare.entity.VipUserEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author yusongyuan
 * @email ysy_0407@foxmail.com
 * @date 2018-02-22 16:13:09
 */
public interface VipUserService {
	
	VipUserEntity queryObject(Integer id);
	
	List<VipUserEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(VipUserEntity vipUser);
	
	void update(VipUserEntity vipUser);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
