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
public class HandleSystemEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//操作类型
	private Integer handleType;
	//操作人
	private String handlePerson;
	//操作时间
	private String handleTime;
	//操作内容
	private String describe;

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
	 * 设置：操作类型
	 */
	public void setHandleType(Integer handleType) {
		this.handleType = handleType;
	}
	/**
	 * 获取：操作类型
	 */
	public Integer getHandleType() {
		return handleType;
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
	 * 设置：操作内容
	 */
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	/**
	 * 获取：操作内容
	 */
	public String getDescribe() {
		return describe;
	}
}
