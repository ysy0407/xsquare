package com.xsquare.modules.xsquare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.xsquare.modules.xsquare.dao.VipCardTypeDao;
import com.xsquare.modules.xsquare.entity.VipCardTypeEntity;
import com.xsquare.modules.xsquare.service.VipCardTypeService;



@Service("vipCardTypeService")
public class VipCardTypeServiceImpl implements VipCardTypeService {
	@Autowired
	private VipCardTypeDao vipCardTypeDao;
	
	@Override
	public VipCardTypeEntity queryObject(Integer id){
		return vipCardTypeDao.queryObject(id);
	}
	
	@Override
	public List<VipCardTypeEntity> queryList(Map<String, Object> map){
		return vipCardTypeDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return vipCardTypeDao.queryTotal(map);
	}
	
	@Override
	public void save(VipCardTypeEntity vipCardType){
		vipCardTypeDao.save(vipCardType);
	}
	
	@Override
	public void update(VipCardTypeEntity vipCardType){
		vipCardTypeDao.update(vipCardType);
	}
	
	@Override
	public void delete(Integer id){
		vipCardTypeDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		vipCardTypeDao.deleteBatch(ids);
	}
	
}
