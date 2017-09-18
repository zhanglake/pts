package com.ptsoft.pts.basic.model.vo;

import com.ptsoft.common.base.BaseEntity;

public class CompanySupplierMap extends BaseEntity {

	private static final long serialVersionUID = -5323894003112083396L;
	
	/**
	 * 自增长ID
	 */
	private Integer id;
	
	/**
	 * 企业ID
	 */
	private Integer companyId;
	
	/**
	 * 生产供应商ID
	 */
	private Integer supplierId;
	
	/**
	 * 企业内部生产供应商编码
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
	public Integer getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}
	public void setInnerCode(String innerCode) {
		this.innerCode = innerCode;
	}
	public String getInnerCode() {
		return innerCode;
	}
	
}
