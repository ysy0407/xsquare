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
public class LogVipHandleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	//会员卡ID
	private String vipCardNum;
	//会员卡操作类型
	private Integer handleType;
	//操作类型信息
	private DictEntity handleTypeDict;
	//操作时间
	private String handleTime;
	//操作人
	private String handlePerson;
	//描述
	private String describe;
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
	 * 设置：会员卡ID
	 */
	public void setVipCardNum(String vipCardNum) {
		this.vipCardNum = vipCardNum;
	}
	/**
	 * 获取：会员卡ID
	 */
	public String getVipCardNum() {
		return vipCardNum;
	}
	/**
	 * 设置：会员卡操作类型
	 */
	public void setHandleType(Integer handleType) {
		this.handleType = handleType;
	}
	/**
	 * 获取：会员卡操作类型
	 */
	public Integer getHandleType() {
		return handleType;
	}

	public DictEntity getHandleTypeDict() {
		return handleTypeDict;
	}

	public void setHandleTypeDict(DictEntity handleTypeDict) {
		this.handleTypeDict = handleTypeDict;
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
	 * 设置：描述
	 */
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	/**
	 * 获取：描述
	 */
	public String getDescribe() {
		return describe;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
