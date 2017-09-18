package com.ptsoft.pts.business.model.vo;

import com.ptsoft.common.base.BaseEntity;

public class ScanRecord extends BaseEntity {

	private static final long serialVersionUID = -267878125787399725L;
	
	/**
	 * 自增长ID
	 */
	private int id;
	/**
	 * 二维码
	 */
	private String qrcode;
	
	/**
	 * 产品编码
	 */
	private String pcode;
	
	/**
	 * 产品名称 
	 */
	private String pname;
	
	/**
	 * 扫码时间
	 */
	private String createTime;
	/**
	 * 扫码用户名
	 */
	private String operator;
	/**
	 * 扫码用户ID
	 */
	private int operatorId;
	/**
	 * 扫码操作类型 1-包装 2-入库 3-出库 4-经销商入库 5-经销商溯源 6-终端用户溯源 7-内部溯源 8-退换货 9-作废
	 */
	private int actionType;
	/**
	 * 操作类型名称
	 */
	private String actionName;
	/**
	 * 设备号
	 */
	private String deviceNo;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQrcode() {
		return qrcode;
	}
	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public int getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(int operatorId) {
		this.operatorId = operatorId;
	}
	public int getActionType() {
		return actionType;
	}
	public void setActionType(int actionType) {
		this.actionType = actionType;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getPcode() {
		return pcode;
	}
	public void setPcode(String pcode) {
		this.pcode = pcode;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
}
