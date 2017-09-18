package com.ptsoft.pts.business.model.vo;

import com.ptsoft.common.base.BaseEntity;

public class SalesOrder extends BaseEntity 
{

	private static final long serialVersionUID = -4080025374693476794L;
	
	private int id;
	/**企业ID*/
	private int companyId;
	/**单据号*/
	private String orderNo;
	/**日期*/
	private String orderDate;
	/**同步日期*/
	private String syncDate;
	/**父级购货单位名称*/
	private String unitNo;
	/**父级购货单位名称*/
	private String unitName;
	/**子级购货单位编号*/
	private String orderUnitNo;
	/**子级购货单位名称*/
	private String orderUnitName;
	/**收货地址*/
	private String shippingAddress;
	/**联系人*/
	private String contact;
	/**电话*/
	private String tel;
	/**发送方式*/
	private String deliveryWay;
	/**备注*/
	private String remark;
	/**状态 0-未出库 1-已出库*/
	private int status;
	/**约束判断 1-判断 0-不判断*/
	private int isRestrict;
	/**订单类型 1-手动创建 2-金算盘同步*/
	private int ordType;
	
	private String kingId;
	private Integer kingNo;
	private String iKingNo;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getSyncDate() {
		return syncDate;
	}
	public void setSyncDate(String syncDate) {
		this.syncDate = syncDate;
	}
	public String getUnitNo() {
		return unitNo;
	}
	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getOrderUnitNo() {
		return orderUnitNo;
	}
	public void setOrderUnitNo(String orderUnitNo) {
		this.orderUnitNo = orderUnitNo;
	}
	public String getOrderUnitName() {
		return orderUnitName;
	}
	public void setOrderUnitName(String orderUnitName) {
		this.orderUnitName = orderUnitName;
	}
	public String getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getDeliveryWay() {
		return deliveryWay;
	}
	public void setDeliveryWay(String deliveryWay) {
		this.deliveryWay = deliveryWay;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getIsRestrict() {
		return isRestrict;
	}
	public void setIsRestrict(int isRestrict) {
		this.isRestrict = isRestrict;
	}
	public int getOrdType() {
		return ordType;
	}
	public void setOrdType(int ordType) {
		this.ordType = ordType;
	}
	public String getKingId() {
		return kingId;
	}
	public void setKingId(String kingId) {
		this.kingId = kingId;
	}
	public Integer getKingNo() {
		return kingNo;
	}
	public void setKingNo(Integer kingNo) {
		this.kingNo = kingNo;
	}
	public String getiKingNo() {
		return iKingNo;
	}
	public void setiKingNo(String iKingNo) {
		this.iKingNo = iKingNo;
	}
}
