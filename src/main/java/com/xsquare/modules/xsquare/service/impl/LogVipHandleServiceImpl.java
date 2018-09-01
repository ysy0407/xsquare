package com.xsquare.modules.xsquare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.xsquare.modules.xsquare.dao.LogVipHandleDao;
import com.xsquare.modules.xsquare.entity.LogVipHandleEntity;
import com.xsquare.modules.xsquare.service.LogVipHandleService;



@Service("logVipHandleService")
public class LogVipHandleServiceImpl implements LogVipHandleService {
	@Autowired
	private LogVipHandleDao logVipHandleDao;
	
	@Override
	public LogVipHandleEntity queryObject(Integer id){
		return logVipHandleDao.queryObject(id);
	}
	
	@Override
	public List<LogVipHandleEntity> queryList(Map<String, Object> map){
		return logVipHandleDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return logVipHandleDao.queryTotal(map);
	}
	
	@Override
	public void save(LogVipHandleEntity logVipHandle){
		logVipHandleDao.save(logVipHandle);
	}
	
	@Override
	public void update(LogVipHandleEntity logVipHandle){
		logVipHandleDao.update(logVipHandle);
	}
	
	@Override
	public void delete(Integer id){
		logVipHandleDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		logVipHandleDao.deleteBatch(ids);
	}
	
}
