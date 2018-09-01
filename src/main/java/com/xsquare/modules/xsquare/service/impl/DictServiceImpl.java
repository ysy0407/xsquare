package com.xsquare.modules.xsquare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.xsquare.modules.xsquare.dao.DictDao;
import com.xsquare.modules.xsquare.entity.DictEntity;
import com.xsquare.modules.xsquare.service.DictService;



@Service("dictService")
public class DictServiceImpl implements DictService {
	@Autowired
	private DictDao dictDao;
	
	@Override
	public DictEntity queryObject(Integer id){
		return dictDao.queryObject(id);
	}
	
	@Override
	public List<DictEntity> queryList(Map<String, Object> map){
		return dictDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return dictDao.queryTotal(map);
	}
	
	@Override
	public void save(DictEntity dict){
		dictDao.save(dict);
	}
	
	@Override
	public void update(DictEntity dict){
		dictDao.update(dict);
	}
	
	@Override
	public void delete(Integer id){
		dictDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		dictDao.deleteBatch(ids);
	}
	
}
