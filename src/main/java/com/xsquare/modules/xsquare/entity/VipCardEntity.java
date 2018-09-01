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
public class VipCardEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//会员卡ID
	private Integer id;
	//会员卡号
	private String vipCardNum;
	//vip用户ID
	private String vipUserId;
	//vip用户信息
	private VipUserEntity vipUser;
	//会员卡类型ID
	private Integer vipCardTypeId;
	//会员卡类型信息
	private VipCardTypeEntity vipCardType;
	//出售价格
	private Double price;
	//积分
	private Integer integral;
	//扣费方式，2：余额扣费，3：按次扣费，4：有效期内免费
	private String deductionType;
	//扣费方式信息
	private DictEntity dictDeductionType;
	//剩余金额
	private Double balanceMoney;
	//剩余次数
	private Integer balanceNumber;
	//生效时间
	private String takeEffectDate;
	//有效期至
	private String effectiveDate;
	//开卡日期
	private String openDate;
	//会员卡状态，1：正常，2：冻结，3：过期，4：换卡
	private String status;

	@Override
	public String toString() {
		return "VipCardEntity{" +
				"id=" + id +
				", vipCardNum='" + vipCardNum + '\'' +
				", vipUserId='" + vipUserId + '\'' +
				", vipUser=" + vipUser +
				", vipCardTypeId=" + vipCardTypeId +
				", vipCardType=" + vipCardType +
				", price=" + price +
				", integral=" + integral +
				", deductionType='" + deductionType + '\'' +
				", dictDeductionType=" + dictDeductionType +
				", balanceMoney=" + balanceMoney +
				", balanceNumber=" + balanceNumber +
				", takeEffectDate='" + takeEffectDate + '\'' +
				", effectiveDate='" + effectiveDate + '\'' +
				", openDate='" + openDate + '\'' +
				", status='" + status + '\'' +
				'}';
	}

	/**
	 * 设置：会员卡ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：会员卡ID
	 */
	public Integer getId() {
		return id;
	}

	public String getVipCardNum() {
		return vipCardNum;
	}

	public void setVipCardNum(String vipCardNum) {
		this.vipCardNum = vipCardNum;
	}

	/**
	 * 设置：vip用户ID
	 */
	public void setVipUserId(String vipUserId) {
		this.vipUserId = vipUserId;
	}
	/**
	 * 获取：vip用户ID
	 */
	public String getVipUserId() {
		return vipUserId;
	}

	public VipUserEntity getVipUser() {
		return vipUser;
	}

	public void setVipUser(VipUserEntity vipUser) {
		this.vipUser = vipUser;
	}

	/**
	 * 设置：会员卡类型ID
	 */
	public void setVipCardTypeId(Integer vipCardTypeId) {
		this.vipCardTypeId = vipCardTypeId;
	}
	/**
	 * 获取：会员卡类型ID
	 */
	public Integer getVipCardTypeId() {
		return vipCardTypeId;
	}

	public VipCardTypeEntity getVipCardType() {
		return vipCardType;
	}

	public void setVipCardType(VipCardTypeEntity vipCardType) {
		this.vipCardType = vipCardType;
	}

	/**
	 * 设置：出售价格
	 */
	public void setPrice(Double price) {
		this.price = price;
	}
	/**
	 * 获取：出售价格
	 */
	public Double getPrice() {
		return price;
	}
	/**
	 * 设置：积分
	 */
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	/**
	 * 获取：积分
	 */
	public Integer getIntegral() {
		return integral;
	}
	/**
	 * 设置：扣费方式，2：余额扣费，3：按次扣费，4：有效期内免费
	 */
	public void setDeductionType(String deductionType) {
		this.deductionType = deductionType;
	}
	/**
	 * 获取：扣费方式，2：余额扣费，3：按次扣费，4：有效期内免费
	 */
	public String getDeductionType() {
		return deductionType;
	}

	public DictEntity getDictDeductionType() {
		return dictDeductionType;
	}

	public void setDictDeductionType(DictEntity dictDeductionType) {
		this.dictDeductionType = dictDeductionType;
	}

	/**
	 * 设置：剩余金额
	 */
	public void setBalanceMoney(Double balanceMoney) {
		this.balanceMoney = balanceMoney;
	}
	/**
	 * 获取：剩余金额
	 */
	public Double getBalanceMoney() {
		return balanceMoney;
	}
	/**
	 * 设置：剩余次数
	 */
	public void setBalanceNumber(Integer balanceNumber) {
		this.balanceNumber = balanceNumber;
	}
	/**
	 * 获取：剩余次数
	 */
	public Integer getBalanceNumber() {
		return balanceNumber;
	}

	public String getTakeEffectDate() {
		return takeEffectDate;
	}

	public void setTakeEffectDate(String takeEffectDate) {
		this.takeEffectDate = takeEffectDate;
	}

	/**
	 * 设置：有效期至
	 */
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	/**
	 * 获取：有效期至
	 */
	public String getEffectiveDate() {
		return effectiveDate;
	}
	/**
	 * 设置：开卡日期
	 */
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	/**
	 * 获取：开卡日期
	 */
	public String getOpenDate() {
		return openDate;
	}
	/**
	 * 设置：会员卡状态，1：正常，2：冻结，3：过期，4：换卡
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：会员卡状态，1：正常，2：冻结，3：过期，4：换卡
	 */
	public String getStatus() {
		return status;
	}
}
