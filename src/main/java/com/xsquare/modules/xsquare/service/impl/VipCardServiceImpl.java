package com.xsquare.modules.xsquare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.xsquare.modules.xsquare.dao.VipCardDao;
import com.xsquare.modules.xsquare.entity.VipCardEntity;
import com.xsquare.modules.xsquare.service.VipCardService;



@Service("vipCardService")
public class VipCardServiceImpl implements VipCardService {
	@Autowired
	private VipCardDao vipCardDao;
	
	@Override
	public VipCardEntity queryObject(String id){
		return vipCardDao.queryObject(id);
	}
	
	@Override
	public List<VipCardEntity> queryList(Map<String, Object> map){
		return vipCardDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return vipCardDao.queryTotal(map);
	}
	
	@Override
	public void save(VipCardEntity vipCard){
		vipCardDao.save(vipCard);
	}
	
	@Override
	public void update(VipCardEntity vipCard){
		vipCardDao.update(vipCard);
	}
	
	@Override
	public void delete(String id){
		vipCardDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		vipCardDao.deleteBatch(ids);
	}
	
}
