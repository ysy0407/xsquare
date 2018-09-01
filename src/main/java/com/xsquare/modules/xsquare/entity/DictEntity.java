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
public class DictEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//字典数据描述
	private String describe;
	//父类型ID
	private Integer pType;
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
	 * 设置：字典数据描述
	 */
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	/**
	 * 获取：字典数据描述
	 */
	public String getDescribe() {
		return describe;
	}
	/**
	 * 设置：父类型ID
	 */
	public void setPType(Integer pType) {
		this.pType = pType;
	}
	/**
	 * 获取：父类型ID
	 */
	public Integer getPType() {
		return pType;
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
