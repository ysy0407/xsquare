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
public class CourseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//课程名称
	private String name;
	//签到添加的积分
	private Integer signIntegral;
	//每节课时长
	private Integer time;
	//最高预约人数，不填则为无限制
	private Integer maxPerson;
	//可上课会员卡类别，使用“|”分隔
	private String vipCardType;
	//课程描述
	private String describe;
	//累计上课次数
	private Integer countClassNumber;
	//平均上课人次
	private double avgPersonNumberTime;
	//累计上课人次
	private Integer countPersonNumberTime;
	//课程状态，1：正常，0：删除
	private Integer status;

	@Override
	public String toString() {
		return "CourseEntity{" +
				"id=" + id +
				", name='" + name + '\'' +
				", signIntegral=" + signIntegral +
				", time=" + time +
				", maxPerson=" + maxPerson +
				", vipCardType='" + vipCardType + '\'' +
				", describe='" + describe + '\'' +
				", countClassNumber=" + countClassNumber +
				", avgPersonNumberTime=" + avgPersonNumberTime +
				", countPersonNumberTime=" + countPersonNumberTime +
				", status=" + status +
				'}';
	}

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
	 * 设置：课程名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：课程名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：签到添加的积分
	 */
	public void setSignIntegral(Integer signIntegral) {
		this.signIntegral = signIntegral;
	}
	/**
	 * 获取：签到添加的积分
	 */
	public Integer getSignIntegral() {
		return signIntegral;
	}
	/**
	 * 设置：每节课时长
	 */
	public void setTime(Integer time) {
		this.time = time;
	}
	/**
	 * 获取：每节课时长
	 */
	public Integer getTime() {
		return time;
	}
	/**
	 * 设置：最高预约人数，不填则为无限制
	 */
	public void setMaxPerson(Integer maxPerson) {
		this.maxPerson = maxPerson;
	}
	/**
	 * 获取：最高预约人数，不填则为无限制
	 */
	public Integer getMaxPerson() {
		return maxPerson;
	}
	/**
	 * 设置：可上课会员卡类别，使用“|”分隔
	 */
	public void setVipCardType(String vipCardType) {
		this.vipCardType = vipCardType;
	}
	/**
	 * 获取：可上课会员卡类别，使用“|”分隔
	 */
	public String getVipCardType() {
		return vipCardType;
	}
	/**
	 * 设置：课程描述
	 */
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	/**
	 * 获取：课程描述
	 */
	public String getDescribe() {
		return describe;
	}
	/**
	 * 设置：累计上课次数
	 */
	public void setCountClassNumber(Integer countClassNumber) {
		this.countClassNumber = countClassNumber;
	}
	/**
	 * 获取：累计上课次数
	 */
	public Integer getCountClassNumber() {
		return countClassNumber;
	}
	/**
	 * 设置：平均上课人次
	 */
	public void setAvgPersonNumberTime(double avgPersonNumberTime) {
		this.avgPersonNumberTime = avgPersonNumberTime;
	}
	/**
	 * 获取：平均上课人次
	 */
	public double getAvgPersonNumberTime() {
		return avgPersonNumberTime;
	}
	/**
	 * 设置：累计上课人次
	 */
	public void setCountPersonNumberTime(Integer countPersonNumberTime) {
		this.countPersonNumberTime = countPersonNumberTime;
	}
	/**
	 * 获取：累计上课人次
	 */
	public Integer getCountPersonNumberTime() {
		return countPersonNumberTime;
	}
	/**
	 * 设置：课程状态，1：正常，0：删除
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：课程状态，1：正常，0：删除
	 */
	public Integer getStatus() {
		return status;
	}
}
