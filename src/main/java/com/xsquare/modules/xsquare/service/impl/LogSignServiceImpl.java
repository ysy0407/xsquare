package com.xsquare.modules.xsquare.service.impl;

import com.xsquare.common.exception.RRException;
import com.xsquare.common.utils.DateUtils;
import com.xsquare.common.utils.SendSmsUtils;
import com.xsquare.modules.sys.entity.SysUserEntity;
import com.xsquare.modules.xsquare.entity.*;
import com.xsquare.modules.xsquare.service.*;
import org.apache.shiro.SecurityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xsquare.modules.xsquare.dao.LogSignDao;
import org.springframework.transaction.annotation.Transactional;


@Service("logSignService")
public class LogSignServiceImpl implements LogSignService {

	private static Logger LOG = LoggerFactory.getLogger(LogSignServiceImpl.class);
	@Autowired
	private LogSignDao logSignDao;
	@Autowired
	private ClasstableService classtableService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private VipCardService vipCardService;
	@Autowired
	private DictService dictService;
	
	@Override
	public LogSignEntity queryObject(Integer id){
		return logSignDao.queryObject(id);
	}
	
	@Override
	public List<LogSignEntity> queryList(Map<String, Object> map){
		return logSignDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return logSignDao.queryTotal(map);
	}

	@Transactional
	@Override
	public void save(LogSignEntity logSign){
		//检查当前会员卡在当前课程表表中是否已经签到过了
		Map<String, Object> map = new HashMap<>();
		map.put("classtableId", logSign.getClasstableId());
		map.put("vipCardId", logSign.getVipCardId());
		List<LogSignEntity> logSignList = logSignDao.queryList(map);
		//大于0说明已经签到过了
		if (logSignList.size() > 0) {
			throw new RRException("您的会员卡已经签到过当前课程，<br>签到时间："+logSignList.get(0).getSignTime());
		}
		//获取课程表信息
		ClasstableEntity classtable = classtableService.queryObject(Integer.valueOf(logSign.getClasstableId()));
		//修改课程信息
		CourseEntity course = classtable.getCourseEntity();
		//当课程表的签到人数为0的时候说明是第一个人签到,此时应当将这节课程的上课次数加1
		if (classtable.getSignNum() == 0) {
			course.setCountClassNumber(course.getCountClassNumber()+1);
		}
		//增加累计上课人次
		course.setCountPersonNumberTime(course.getCountPersonNumberTime()+1);
		//修改平均上课人次,累计上课人次/上课次数
		course.setAvgPersonNumberTime(course.getCountPersonNumberTime()/course.getCountClassNumber());
		//修改课程信息
		courseService.update(course);
		//获取会员卡信息
		VipCardEntity vipCard = vipCardService.queryObject(logSign.getVipCardId());
		//获取扣费信息
		List<VipCardTypeEntity> vipCardReduceInfo = getVipCardReduceInfo(course.getVipCardType());
		for (VipCardTypeEntity vipCardType : vipCardReduceInfo) {
			//将所有的会员卡类型和当前签到的会员卡类型进行比较
			if (vipCardType.getId() == vipCard.getVipCardTypeId()) {
				//说明当前课程这种会员卡类型不可用
				if (vipCardType.getStatus() == 0) {
					throw new RRException("您的会员卡类型不允许学习签到课程");
				//扣除会员卡金额,次数,增加积分
				} else {
					//会员卡余额=原有余额-当前课程所需金额
					double balanceMoney = vipCard.getBalanceMoney();
					vipCard.setBalanceMoney(vipCard.getBalanceMoney()-vipCardType.getInitialMoney());
					if (vipCard.getBalanceMoney() < 0) {
						throw new RRException("您的会员卡余额不足,<br>剩余金额:"+balanceMoney+", 课程所需金额:"+vipCardType.getInitialMoney());
					}
					//会员卡剩余次数=原有次数-当前课程所需次数
					int balanceNumber = vipCard.getBalanceNumber();
					vipCard.setBalanceNumber(vipCard.getBalanceNumber()-vipCardType.getInitialNumber());
					if (vipCard.getBalanceNumber() < 0) {
						throw new RRException("您的会员卡次数不足,<br>剩余次数:"+balanceNumber+", 课程所需次数:"+vipCardType.getInitialNumber());
					}
					//会员卡积分=原有积分+课程奖励积分
					vipCard.setIntegral(vipCard.getIntegral()+course.getSignIntegral());
				}
			}
		}
		//修改会员卡信息
		vipCardService.update(vipCard);
		//修改当前课程的签到人数
		classtable.setSignNum(classtable.getSignNum() + 1);
		classtableService.update(classtable);

		//保存签到日志
		logSign.setSignTime(DateUtils.getTime());
		//操作人姓名
		String handlePerson = ((SysUserEntity) SecurityUtils.getSubject().getPrincipal()).getName();
		logSign.setSignHandlePerson(handlePerson);
		//todo 短信发送，短信内容，尊敬的埃克斯方舞馆会员，您的卡号为{0}的会员卡，于{1}签到{2}课程，您的会员卡{3}。
		//{0}：卡号，{1}：签到时间，{2}：课程名称，{3}：剩余次数/金额，有效期至。。。
		try {
			JSONObject result = sendSms(vipCard.getVipUser().getPhone(), true);
			if (result != null && result.getInt("success") == 1) {
                logSign.setSmsStatus(-1);
                logSign.setMsgId(result.getString("msgid"));
            } else {
				logSign.setSmsStatus(-2);
			}
		} catch (Exception e) {
			LOG.error("----发送短信出现错误, 具体错误：{}", e);
		}
		logSignDao.save(logSign);
	}

	private JSONObject sendSms(String phone, boolean isSign){
		String url = "";
		String appkey = "";
		String appid = "";
		String templetID = "";
		String authToken = "";
		//将pType为18的全部查出来
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pType", 18);
		List<DictEntity> list = dictService.queryList(map);
		for (DictEntity dictEntity : list) {
			if (19 == dictEntity.getId()) {
				url = dictEntity.getDescribe();
			} else if (20 == dictEntity.getId()) {
				appkey = dictEntity.getDescribe();
			} else if (21 == dictEntity.getId()) {
				appid = dictEntity.getDescribe();
			//当为签到，且id为22，则将模板ID赋值为签到模板ID
			} else if (isSign && 22 == dictEntity.getId()) {
				templetID = dictEntity.getDescribe();
			//当为不签到，且id为23，则将模板ID赋值为撤销签到模板ID
			} else if (!isSign && 23 == dictEntity.getId()) {
				templetID = dictEntity.getDescribe();
			} else if (24 == dictEntity.getId()) {
				authToken = dictEntity.getDescribe();
			}
		}
		//模板参数 todo 添加参数
		String[] templateArgs = new String[5];
		return SendSmsUtils.sendSms(appkey, appid, templetID, templateArgs, phone, authToken, url);
	}

	//从课程的vipCardType字段获取当前课程的扣费信息 initialMoney:扣除金额,initialNumber扣除次数, status:是否可用
	private List<VipCardTypeEntity> getVipCardReduceInfo(String reduceStr){
		List<VipCardTypeEntity> vipCardReduceInfo = new ArrayList<>();
		//用|分割获取每一种类型信息
		String[] reduceArray = reduceStr.split("\\|");
		for (String s : reduceArray) {
			//用|分割每一个信息 0:id,1:扣除金额,2:扣除次数,3:是否可用
			String[] vipCardTypeArray = s.split("\\+");
			//表示正常可用
			int status = 1;
			//表示当前会员卡类型不可用
			if ("false".equals(vipCardTypeArray[3])) {
				status = 0;
			}
			vipCardReduceInfo.add(new VipCardTypeEntity(Integer.valueOf(vipCardTypeArray[0]), Double.valueOf(vipCardTypeArray[1]), Integer.valueOf(vipCardTypeArray[2]), status));
		}
		return vipCardReduceInfo;
	}
	
	@Override
	public void update(LogSignEntity logSign){
		logSignDao.update(logSign);
	}

	@Transactional
	@Override
	public void delete(Map<String, Object> map){
		//获取课程表信息
		ClasstableEntity classtable = classtableService.queryObject(Integer.valueOf(map.get("classtableId").toString()));
		//修改课程信息
		CourseEntity course = classtable.getCourseEntity();
		//当课程表的签到人数为1的时候说明是只有签到,此时应当将这节课程的上课次数减1
		if (classtable.getSignNum() == 1) {
			course.setCountClassNumber(course.getCountClassNumber()-1);
		}
		//减少累计上课人次
		course.setCountPersonNumberTime(course.getCountPersonNumberTime()-1);
		//修改平均上课人次,累计上课人次/上课次数
		if (course.getCountClassNumber() != 0) {
			course.setAvgPersonNumberTime(course.getCountPersonNumberTime()/course.getCountClassNumber());
		} else {
			course.setAvgPersonNumberTime(0);
		}
		//修改课程信息
		courseService.update(course);
		//获取会员卡信息
		VipCardEntity vipCard = vipCardService.queryObject(map.get("vipCardId").toString());
		//获取扣费信息
		List<VipCardTypeEntity> vipCardReduceInfo = getVipCardReduceInfo(course.getVipCardType());
		for (VipCardTypeEntity vipCardType : vipCardReduceInfo) {
			//将所有的会员卡类型和当前签到的会员卡类型进行比较
			if (vipCardType.getId() == vipCard.getVipCardTypeId()) {
				System.out.println("---------扣除会员卡金额,次数,增加积分");
				//扣除会员卡金额,次数,增加积分
				//会员卡余额=原有余额+当前课程所需金额
				double balanceMoney = vipCard.getBalanceMoney();
				vipCard.setBalanceMoney(vipCard.getBalanceMoney()+vipCardType.getInitialMoney());
				//会员卡剩余次数=原有次数+当前课程所需次数
				int balanceNumber = vipCard.getBalanceNumber();
				vipCard.setBalanceNumber(vipCard.getBalanceNumber()+vipCardType.getInitialNumber());
				//会员卡积分=原有积分-课程奖励积分
				vipCard.setIntegral(vipCard.getIntegral()-course.getSignIntegral());
			}
		}
		//修改会员卡信息
		vipCardService.update(vipCard);
		//修改当前课程的签到人数
		classtable.setSignNum(classtable.getSignNum() - 1);
		classtableService.update(classtable);

		//修改签到状态
		logSignDao.delete(map.get("logSignId"));
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		logSignDao.deleteBatch(ids);
	}
	
}
