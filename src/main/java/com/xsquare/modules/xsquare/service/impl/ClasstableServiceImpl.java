package com.xsquare.modules.xsquare.service.impl;

import com.xsquare.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.xsquare.modules.xsquare.dao.ClasstableDao;
import com.xsquare.modules.xsquare.entity.ClasstableEntity;
import com.xsquare.modules.xsquare.service.ClasstableService;



@Service("classtableService")
public class ClasstableServiceImpl implements ClasstableService {
	@Autowired
	private ClasstableDao classtableDao;
	
	@Override
	public ClasstableEntity queryObject(Integer id){
		return classtableDao.queryObject(id);
	}
	
	@Override
	public List<List<List<ClasstableEntity>>> queryList(Map<String, Object> map){
		/**
		 * @Author: YuSongYuan
		 * @Description:
		 *		课程表展示思路
		 *			总list中放入三个表示上午下午晚上的长度为7的list，表示7天，每一个时间段的list每一个元素分别同样为list，该list中放入某一天的该时间段的所有课程
		 *			先用sql查出，该7天日期内所有三个时间段的课程，用java确定放入到哪天的list中
		 *		数据存储样式
		 *			allList: {
		 *			 	morningClassTableList: {
		 *			 		timeSlot: {
		 *			 		    Classtable1,
		 *			 		},
		 *			 		2018-03-10: {
	 	 *						Classtable1,
	 	 *						Classtable2,
	 	 *						Classtable3
		 *			 		},
		 *			 		2018-03-12: {
	 	 *						Classtable1,
	 	 *						Classtable2,
	 	 *						Classtable3
		 *			 		},
		 *			 		2018-03-14: {
	 	 *						Classtable1,
	 	 *						Classtable2,
	 	 *						Classtable3
		 *			 		},
		 *			 		...
		 *			 	},
		 *			 	afternoonClassTableList: {},
		 *			 	eveningClassTableList: {},
		 *			}
		 * @Date: 2018/3/10 14:50
		 **/
		//获取选择的一周的日期
		String[] dateArray = ((String[]) map.get("dateArray"));
		//上午的课程
		map.put("timeSlot", 1);
		List<ClasstableEntity> morningClassTableList = classtableDao.queryList(map);
		//已经按照7天的日期分好的课程
		List<List<ClasstableEntity>> morningClassTable = equalsDatePut(dateArray, morningClassTableList,1, "上午");
		//构建一个课程表list,用于展示表格中第一列的时间段
		List<ClasstableEntity> morningDesc = new ArrayList<>();
		morningDesc.add(new ClasstableEntity("上午"));
		morningClassTable.set(0, morningDesc);
		//下午的课程
		map.put("timeSlot", 2);
		List<ClasstableEntity> afternoonClassTableList = classtableDao.queryList(map);
		//已经按照7天的日期分好的课程
		List<List<ClasstableEntity>> afternoonClassTable = equalsDatePut(dateArray, afternoonClassTableList,2, "下午");
		//构建一个课程表list,用于展示表格中第一列的时间段
		List<ClasstableEntity> afternoonDesc = new ArrayList<>();
		afternoonDesc.add(new ClasstableEntity("下午"));
		afternoonClassTable.set(0, afternoonDesc);
		map.put("timeSlot", 3);
		List<ClasstableEntity> eveningClassTableList = classtableDao.queryList(map);
		//已经按照7天的日期分好的课程
		List<List<ClasstableEntity>> eveningClassTable = equalsDatePut(dateArray, eveningClassTableList,3, "晚上");
		//构建一个课程表list,用于展示表格中第一列的时间段
		List<ClasstableEntity> eveningDesc = new ArrayList<>();
		eveningDesc.add(new ClasstableEntity("晚上"));
		eveningClassTable.set(0, eveningDesc);
		List<List<List<ClasstableEntity>>> classTable = new ArrayList<List<List<ClasstableEntity>>>();
		classTable.add(0,morningClassTable);
		classTable.add(1,afternoonClassTable);
		classTable.add(2,eveningClassTable);
		return classTable;
	}

	//循环比较日期,相同的说明是这一天的课程,放入对应顺序的list集合中
	public static List<List<ClasstableEntity>> equalsDatePut(String[] dateArray, List<ClasstableEntity> classtableEntities, Integer timeSolt, String describe){
		List<List<ClasstableEntity>> ClassTables = new ArrayList<List<ClasstableEntity>>();
		ClassTables.add(0, new ArrayList<ClasstableEntity>());
		for (int i = 0; i < dateArray.length; i++) {
			ClassTables.add(i+1, new ArrayList<ClasstableEntity>());
			//将一天的每个时间段的课程表的第一个放入为这个时间的信息,date,timeSolt,前端以取出并进行必要的查询
			ClasstableEntity classtableInfo = new ClasstableEntity(dateArray[i], timeSolt, describe);
			ClassTables.get(i+1).add(0, classtableInfo);
			for (int j = 0; j < classtableEntities.size(); j++) {
				System.out.println("dateArray[i]:"+dateArray[i]+"\tclasstableEntity.getDate():"+classtableEntities.get(j).getDate());
				if (dateArray[i].equals(classtableEntities.get(j).getDate())) {
					System.out.println("放入"+(i+1));
					//当相等的时候添加并将该课程移除,减少后续的循环次数
					//移除后应当将j-1,否则数据是错误的
					ClassTables.get(i+1).add(classtableEntities.get(j));
					classtableEntities.remove(j);
					j--;
				}
			}
		}
		return ClassTables;
	}

	@Override
	public List<ClasstableEntity> queryTheList(Map<String, Object> map) {
		return classtableDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return classtableDao.queryTotal(map);
	}
	
	@Override
	public void save(ClasstableEntity classtable){
		classtableDao.save(classtable);
	}
	
	@Override
	public void update(ClasstableEntity classtable){
		classtableDao.update(classtable);
	}
	
	@Override
	public void delete(Integer id){
		classtableDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		classtableDao.deleteBatch(ids);
	}
	
}
