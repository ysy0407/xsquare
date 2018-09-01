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
public class ClassroomEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//教室名称
	private String name;
	//教室状态，1：使用中，0：删除
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
	 * 设置：教室名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：教室名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：教室状态，1：使用中，0：删除
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：教室状态，1：使用中，0：删除
	 */
	public Integer getStatus() {
		return status;
	}
}
