package com.xsquare.modules.xsquare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.xsquare.modules.xsquare.dao.VipUserDao;
import com.xsquare.modules.xsquare.entity.VipUserEntity;
import com.xsquare.modules.xsquare.service.VipUserService;



@Service("vipUserService")
public class VipUserServiceImpl implements VipUserService {
	@Autowired
	private VipUserDao vipUserDao;
	
	@Override
	public VipUserEntity queryObject(Integer id){
		return vipUserDao.queryObject(id);
	}
	
	@Override
	public List<VipUserEntity> queryList(Map<String, Object> map){
		return vipUserDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return vipUserDao.queryTotal(map);
	}
	
	@Override
	public void save(VipUserEntity vipUser){
		vipUserDao.save(vipUser);
	}
	
	@Override
	public void update(VipUserEntity vipUser){
		vipUserDao.update(vipUser);
	}
	
	@Override
	public void delete(Integer id){
		vipUserDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		vipUserDao.deleteBatch(ids);
	}
	
}
