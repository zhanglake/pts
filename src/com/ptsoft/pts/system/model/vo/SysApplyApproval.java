package com.ptsoft.pts.system.model.vo;

import com.ptsoft.common.base.BaseEntity;

public class SysApplyApproval extends BaseEntity{

	private static final long serialVersionUID = 5518652343017516071L;

	/**
	 * 自增长ID
	 */
	private int id;
	/**
	 * 申请人ID
	 */
	private int applyId;
	/**
	 * 审批人ID
	 */
	private int approvalId;
	/**
	 * 流程类型 1-申请二维码
	 */
	private int type;
	/**
	 * 流程状态 0-已申请 1-已审批 
	 */
	private int status;
	/**
	 * 审批comments
	 */
	private String comments;
	/**
	 * 生产供应商ID
	 */
	private int suplierId;
	/**
	 * 生产供应商名称
	 */
	private int supplierName;
	/**
	 * 产品ID
	 */
	private int productId;
	/**
	 * 产品数量
	 */
	private int count;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getApplyId() {
		return applyId;
	}
	public void setApplyId(int applyId) {
		this.applyId = applyId;
	}
	public int getApprovalId() {
		return approvalId;
	}
	public void setApprovalId(int approvalId) {
		this.approvalId = approvalId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public void setSuplierId(int suplierId) {
		this.suplierId = suplierId;
	}
	public int getSuplierId() {
		return suplierId;
	}
	public void setSupplierName(int supplierName) {
		this.supplierName = supplierName;
	}
	public int getSupplierName() {
		return supplierName;
	}
	
}
