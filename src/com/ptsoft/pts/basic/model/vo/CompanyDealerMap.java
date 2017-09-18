package com.ptsoft.pts.basic.model.vo;

import com.ptsoft.common.base.BaseEntity;

public class CompanyDealerMap  extends BaseEntity {

	private static final long serialVersionUID = -2441655111950458450L;
	
	/**
	 * 自增长ID
	 */
	private Integer id;
	/**
	 * 企业ID
	 */
	private Integer companyId;
	/**
	 * 经销商ID
	 */
	private Integer dealerId;
	
	/**
	 * 企业内部经销商编码
	 */
	private String innerCode; 
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public Integer getDealerId() {
		return dealerId;
	}
	public void setDealerId(Integer dealerId) {
		this.dealerId = dealerId;
	}
	public void setInnerCode(String innerCode) {
		this.innerCode = innerCode;
	}
	public String getInnerCode() {
		return innerCode;
	}
}
