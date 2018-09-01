package com.xsquare.modules.xsquare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.xsquare.modules.xsquare.dao.HandleSystemDao;
import com.xsquare.modules.xsquare.entity.HandleSystemEntity;
import com.xsquare.modules.xsquare.service.HandleSystemService;



@Service("handleSystemService")
public class HandleSystemServiceImpl implements HandleSystemService {
	@Autowired
	private HandleSystemDao handleSystemDao;
	
	@Override
	public HandleSystemEntity queryObject(Integer id){
		return handleSystemDao.queryObject(id);
	}
	
	@Override
	public List<HandleSystemEntity> queryList(Map<String, Object> map){
		return handleSystemDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return handleSystemDao.queryTotal(map);
	}
	
	@Override
	public void save(HandleSystemEntity handleSystem){
		handleSystemDao.save(handleSystem);
	}
	
	@Override
	public void update(HandleSystemEntity handleSystem){
		handleSystemDao.update(handleSystem);
	}
	
	@Override
	public void delete(Integer id){
		handleSystemDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		handleSystemDao.deleteBatch(ids);
	}
	
}
