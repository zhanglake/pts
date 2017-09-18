package com.ptsoft.pts.business.model.vo;

import com.ptsoft.common.base.BaseEntity;

public class Order extends BaseEntity {

	private static final long serialVersionUID = 11253389196767610L;
	
	private int id;
	/**订单号*/
	private String orderNo;
	/**企业ID*/
	private int companyId;
	/**订单类型*/
	private String orderType;
	/**订单来源*/
	private String orderFrom;
	/**创建人*/
	private String creator;
	/**创建时间*/
	private String createTime;
	/**状态 0-未生成二维码 1-已生成*/
	private int status;
	/**备注*/
	private String remark;
	/**同步时间*/
	private String syncDate;
	/**金算盘订单日期*/
	private String kingDate;
	/**金算盘ID*/
	private String kingId;
	
	private String kingNo;
	private String supplierCode;
	private String supplierName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreator() {
		return creator;
	}

	public void setOrderFrom(String orderFrom) {
		this.orderFrom = orderFrom;
	}

	public String getOrderFrom() {
		return orderFrom;
	}

	public String getSyncDate() {
		return syncDate;
	}

	public void setSyncDate(String syncDate) {
		this.syncDate = syncDate;
	}

	public String getKingDate() {
		return kingDate;
	}

	public void setKingDate(String kingDate) {
		this.kingDate = kingDate;
	}

	public String getKingId() {
		return kingId;
	}

	public void setKingId(String kingId) {
		this.kingId = kingId;
	}

	public String getKingNo() {
		return kingNo;
	}

	public void setKingNo(String kingNo) {
		this.kingNo = kingNo;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
}
