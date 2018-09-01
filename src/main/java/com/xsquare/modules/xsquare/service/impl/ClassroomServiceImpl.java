package com.xsquare.modules.xsquare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.xsquare.modules.xsquare.dao.ClassroomDao;
import com.xsquare.modules.xsquare.entity.ClassroomEntity;
import com.xsquare.modules.xsquare.service.ClassroomService;



@Service("classroomService")
public class ClassroomServiceImpl implements ClassroomService {
	@Autowired
	private ClassroomDao classroomDao;
	
	@Override
	public ClassroomEntity queryObject(Integer id){
		return classroomDao.queryObject(id);
	}
	
	@Override
	public List<ClassroomEntity> queryList(Map<String, Object> map){
		return classroomDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return classroomDao.queryTotal(map);
	}
	
	@Override
	public void save(ClassroomEntity classroom){
		classroomDao.save(classroom);
	}
	
	@Override
	public void update(ClassroomEntity classroom){
		classroomDao.update(classroom);
	}
	
	@Override
	public void delete(Integer id){
		classroomDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		classroomDao.deleteBatch(ids);
	}
	
}
