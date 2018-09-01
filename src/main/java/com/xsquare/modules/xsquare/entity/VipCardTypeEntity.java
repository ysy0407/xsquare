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
public class VipCardTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//会员卡类型名称
	private String name;
	//售价
	private Double price;
	//会员卡积分
	private Integer integral;
	//扣费方式，2：余额扣费，3：按次扣费，4：有效期内免费
	private Integer deductionType;
	//扣费方式信息
	private DictEntity dictDeductionType;
	//初始金额，按金额扣费时使用
	private Double initialMoney;
	//初始次数，按次数扣费时使用
	private Integer initialNumber;
	//初始有效时间，可以在新建会员卡的时候，进行修改
	private Integer initialEffectiveDate;
	//有效期类型，1：天，2：周，3：月
	private Integer effectiveType;
	//初始生效时间
	private String takeEffectDate;
	//会员卡类型状态，1：正常，0：删除
	private Integer status;

	public VipCardTypeEntity() { }

	//用于从reduceStr获取扣费信息
	public VipCardTypeEntity(Integer id, Double initialMoney, Integer initialNumber, Integer status) {
		this.id = id;
		this.initialMoney = initialMoney;
		this.initialNumber = initialNumber;
		this.status = status;
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
	 * 设置：会员卡类型名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：会员卡类型名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：售价
	 */
	public void setPrice(Double price) {
		this.price = price;
	}
	/**
	 * 获取：售价
	 */
	public Double getPrice() {
		return price;
	}
	/**
	 * 设置：会员卡积分
	 */
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	/**
	 * 获取：会员卡积分
	 */
	public Integer getIntegral() {
		return integral;
	}
	/**
	 * 设置：扣费方式，2：余额扣费，3：按次扣费，4：有效期内免费
	 */
	public void setDeductionType(Integer deductionType) {
		this.deductionType = deductionType;
	}
	/**
	 * 获取：扣费方式，2：余额扣费，3：按次扣费，4：有效期内免费
	 */
	public Integer getDeductionType() {
		return deductionType;
	}

	public DictEntity getDictDeductionType() {
		return dictDeductionType;
	}

	public void setDictDeductionType(DictEntity dictDeductionType) {
		this.dictDeductionType = dictDeductionType;
	}

	/**
	 * 设置：初始金额，按金额扣费时使用
	 */
	public void setInitialMoney(Double initialMoney) {
		this.initialMoney = initialMoney;
	}
	/**
	 * 获取：初始金额，按金额扣费时使用
	 */
	public Double getInitialMoney() {
		return initialMoney;
	}
	/**
	 * 设置：初始次数，按次数扣费时使用
	 */
	public void setInitialNumber(Integer initialNumber) {
		this.initialNumber = initialNumber;
	}
	/**
	 * 获取：初始次数，按次数扣费时使用
	 */
	public Integer getInitialNumber() {
		return initialNumber;
	}
	/**
	 * 设置：初始有效时间，可以在新建会员卡的时候，进行修改
	 */
	public void setInitialEffectiveDate(Integer initialEffectiveDate) {
		this.initialEffectiveDate = initialEffectiveDate;
	}
	/**
	 * 获取：初始有效时间，可以在新建会员卡的时候，进行修改
	 */
	public Integer getInitialEffectiveDate() {
		return initialEffectiveDate;
	}
	/**
	 * 设置：有效期类型，1：天，2：周，3：月
	 */
	public void setEffectiveType(Integer effectiveType) {
		this.effectiveType = effectiveType;
	}
	/**
	 * 获取：有效期类型，1：天，2：周，3：月
	 */
	public Integer getEffectiveType() {
		return effectiveType;
	}

	public String getTakeEffectDate() {
		return takeEffectDate;
	}

	public void setTakeEffectDate(String takeEffectDate) {
		this.takeEffectDate = takeEffectDate;
	}

	/**
	 * 设置：会员卡类型状态，1：正常，0：删除
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：会员卡类型状态，1：正常，0：删除
	 */
	public Integer getStatus() {
		return status;
	}
}
