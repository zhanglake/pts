package com.ptsoft.pts.business.model.vo;

import com.ptsoft.common.base.BaseEntity;

public class ToMsQrcodeObj extends BaseEntity
{

	private static final long serialVersionUID = 436041710595609852L;
	
	private String qrCode;
	
	private String productCode;
	
	private String packageRule;
	
	private int packageLevel;
	
	private String createDate;
	
	private String sapNo;
	
	private String name;
	
	private String orderNo;
	
	public ToMsQrcodeObj(String qrCode, String productCode, String packageRule, int packageLevel, String createDate, String sapNo, String name, String orderNo) {
		super();
		this.qrCode = qrCode;
		this.productCode = productCode;
		this.packageRule = packageRule;
		this.packageLevel = packageLevel;
		this.createDate = createDate;
		this.sapNo = sapNo;
		this.name = name;
		this.orderNo = orderNo;
	}
	
	public ToMsQrcodeObj() {
		super();
	}



	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getPackageRule() {
		return packageRule;
	}

	public void setPackageRule(String packageRule) {
		this.packageRule = packageRule;
	}

	public int getPackageLevel() {
		return packageLevel;
	}

	public void setPackageLevel(int packageLevel) {
		this.packageLevel = packageLevel;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getSapNo() {
		return sapNo;
	}

	public void setSapNo(String sapNo) {
		this.sapNo = sapNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
}
