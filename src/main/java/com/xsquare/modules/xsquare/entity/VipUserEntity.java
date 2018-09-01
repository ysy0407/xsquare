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
public class VipUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//会员姓名
	private String name;
	//会员性别
	private Integer gender;
	//会员电话
	private String phone;
	//会员生日
	private String birthday;
	//会员密码，用于签到
	private String password;
	//会员备注
	private String note;

	/**
	 * 设置：
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：会员姓名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：会员姓名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：会员性别
	 */
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	/**
	 * 获取：会员性别
	 */
	public Integer getGender() {
		return gender;
	}
	/**
	 * 设置：会员电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：会员电话
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置：会员生日
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	/**
	 * 获取：会员生日
	 */
	public String getBirthday() {
		return birthday;
	}
	/**
	 * 设置：会员密码，用于签到
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 获取：会员密码，用于签到
	 */
	public String getPassword() {
		return password;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
