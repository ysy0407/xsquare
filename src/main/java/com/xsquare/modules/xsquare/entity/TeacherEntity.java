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
public class TeacherEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//老师姓名
	private String name;
	//老师手机号
	private String phone;
	//入职日期
	private String entryDate;
	//离职日期
	private String leaveDate;
	//当前工资
	private Double currentSalary;
	//老师状态
	private int status;
	//备注
	private String note;

	@Override
	public String toString() {
		return "TeacherEntity{" +
				"id=" + id +
				", name='" + name + '\'' +
				", phone='" + phone + '\'' +
				", entryDate='" + entryDate + '\'' +
				", leaveDate='" + leaveDate + '\'' +
				", currentSalary=" + currentSalary +
				", status=" + status +
				", note='" + note + '\'' +
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
	 * 设置：老师姓名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：老师姓名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：老师手机号
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：老师手机号
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置：入职日期
	 */
	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}
	/**
	 * 获取：入职日期
	 */
	public String getEntryDate() {
		return entryDate;
	}
	/**
	 * 设置：离职日期
	 */
	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}
	/**
	 * 获取：离职日期
	 */
	public String getLeaveDate() {
		return leaveDate;
	}
	/**
	 * 设置：当前工资
	 */
	public void setCurrentSalary(Double currentSalary) {
		this.currentSalary = currentSalary;
	}
	/**
	 * 获取：当前工资
	 */
	public Double getCurrentSalary() {
		return currentSalary;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
