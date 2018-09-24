package com.xsquare.modules.xsquare.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 
 * @author yusongyuan
 * @email ysy_0407@foxmail.com
 * @date 2018-02-22 16:13:09
 */
public class LogSignEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//会员卡ID
	private String vipCardId;
	//会员卡信息
	private VipCardEntity vipCardEntity;
	//课程表ID
	private String classtableId;
	//签到时间
	private String signTime;
	//短信发送状态,-2：提交失败，-1：提交成功，1：发送成功，2：发送失败
	private Integer smsStatus;
	//短信流水号
	private String msgId;
	//手机号
	private String phone;
	//签到的执行人员
	private String signHandlePerson;
	//签到日志状态，1：正常，0：被撤销
	private Integer status;

	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：会员卡ID
	 */
	public void setVipCardId(String vipCardId) {
		this.vipCardId = vipCardId;
	}
	/**
	 * 获取：会员卡ID
	 */
	public String getVipCardId() {
		return vipCardId;
	}

	public VipCardEntity getVipCardEntity() {
		return vipCardEntity;
	}

	public void setVipCardEntity(VipCardEntity vipCardEntity) {
		this.vipCardEntity = vipCardEntity;
	}

	public String getClasstableId() {
		return classtableId;
	}

	public void setClasstableId(String classtableId) {
		this.classtableId = classtableId;
	}

	/**
	 * 设置：签到时间
	 */
	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}
	/**
	 * 获取：签到时间
	 */
	public String getSignTime() {
		return signTime;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getSmsStatus() {
		return smsStatus;
	}

	public void setSmsStatus(Integer smsStatus) {
		this.smsStatus = smsStatus;
	}

	public String getSignHandlePerson() {
		return signHandlePerson;
	}

	public void setSignHandlePerson(String signHandlePerson) {
		this.signHandlePerson = signHandlePerson;
	}

	/**
	 * 设置：签到日志状态，1：正常，0：被撤销
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：签到日志状态，1：正常，0：被撤销
	 */
	public Integer getStatus() {
		return status;
	}
}
