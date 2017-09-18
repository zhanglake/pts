package com.ptsoft.pts.business.model.vo;

import com.ptsoft.common.base.BaseEntity;

public class ApplyApproval extends BaseEntity 
{

	private static final long serialVersionUID = 2527439839049106062L;
	
	/**id*/
	private int id;
	/**申请人ID*/
	private int apply_id;
	/**生产供应商ID*/
	private int supplier_id;
	/**审批人ID*/
	private int approval_id;
	/**类型*/
	private int type;
	/**状态 0-未处理 1-已处理*/
	private int status;
	/**产品ID*/
	private int product_id;
	/**申请数量*/
	private int count;
	/**备注*/
	private String comments;
	/**申请时间*/
	private String createTime;
	
	/**产品编码*/
	private String code;
	/**产品名称*/
	private String name;
	/**申请人登录名*/
	private String lgnNm;
	
	/**状态名称*/
	public String getStsName()
	{
		if(status == 0)
		{
			return "未处理";
		}
		else
		{
			return "已处理";
		}
	}	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getApply_id() {
		return apply_id;
	}
	public void setApply_id(int apply_id) {
		this.apply_id = apply_id;
	}
	public int getSupplier_id() {
		return supplier_id;
	}
	public void setSupplier_id(int supplier_id) {
		this.supplier_id = supplier_id;
	}
	public int getApproval_id() {
		return approval_id;
	}
	public void setApproval_id(int approval_id) {
		this.approval_id = approval_id;
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
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLgnNm() {
		return lgnNm;
	}
	public void setLgnNm(String lgnNm) {
		this.lgnNm = lgnNm;
	}
	
}
