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
public class LogMoneyEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//金额类型，1：收入，0：支出
	private Integer moneyType;
	//金额大小，金额减少：负数，金额增加：整数，方便到时候金额计算
	private Double moneyNumber;
	//具体事项
	private String describe;
	//操作时间
	private String handleTime;
	//操作人
	private String handlePerson;
	//备注
	private String note;

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
	 * 设置：金额类型，1：收入，0：支出
	 */
	public void setMoneyType(Integer moneyType) {
		this.moneyType = moneyType;
	}
	/**
	 * 获取：金额类型，1：收入，0：支出
	 */
	public Integer getMoneyType() {
		return moneyType;
	}
	/**
	 * 设置：金额大小，金额减少：负数，金额增加：整数，方便到时候金额计算
	 */
	public void setMoneyNumber(Double moneyNumber) {
		this.moneyNumber = moneyNumber;
	}
	/**
	 * 获取：金额大小，金额减少：负数，金额增加：整数，方便到时候金额计算
	 */
	public Double getMoneyNumber() {
		return moneyNumber;
	}
	/**
	 * 设置：具体事项
	 */
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	/**
	 * 获取：具体事项
	 */
	public String getDescribe() {
		return describe;
	}
	/**
	 * 设置：操作时间
	 */
	public void setHandleTime(String handleTime) {
		this.handleTime = handleTime;
	}
	/**
	 * 获取：操作时间
	 */
	public String getHandleTime() {
		return handleTime;
	}
	/**
	 * 设置：操作人
	 */
	public void setHandlePerson(String handlePerson) {
		this.handlePerson = handlePerson;
	}
	/**
	 * 获取：操作人
	 */
	public String getHandlePerson() {
		return handlePerson;
	}
	/**
	 * 设置：备注
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * 获取：备注
	 */
	public String getNote() {
		return note;
	}
}
