package com.xsquare.modules.xsquare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.xsquare.modules.xsquare.dao.LogMoneyDao;
import com.xsquare.modules.xsquare.entity.LogMoneyEntity;
import com.xsquare.modules.xsquare.service.LogMoneyService;



@Service("logMoneyService")
public class LogMoneyServiceImpl implements LogMoneyService {
	@Autowired
	private LogMoneyDao logMoneyDao;
	
	@Override
	public LogMoneyEntity queryObject(Integer id){
		return logMoneyDao.queryObject(id);
	}
	
	@Override
	public List<LogMoneyEntity> queryList(Map<String, Object> map){
		return logMoneyDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return logMoneyDao.queryTotal(map);
	}
	
	@Override
	public void save(LogMoneyEntity logMoney){
		logMoneyDao.save(logMoney);
	}
	
	@Override
	public void update(LogMoneyEntity logMoney){
		logMoneyDao.update(logMoney);
	}
	
	@Override
	public void delete(Integer id){
		logMoneyDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		logMoneyDao.deleteBatch(ids);
	}
	
}
