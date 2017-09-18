package com.ptsoft.pts.business.model.vo;

import com.ptsoft.common.base.BaseEntity;
import com.ptsoft.pts.PisConstants;

public class Product extends BaseEntity
{

	private static final long serialVersionUID = 8747307753959134445L;
	
	/**ID索引*/
	private int id;
	/**编码*/
	private String code;
	/**名称*/
	private String name;
	/**SAP编号*/
	private String sapNo;
	/**产品类型-对应SysDataType中tpID为50*/
	private String category;
	/**企业id*/
	private int company_id;
	/**状态 0-禁用 1-可用*/
	private int sts;
	/**积分*/
	private int points;
	/**供应商ID*/
	private int supplierID;
	/**包装规则ID*/
	private int packageID;
	/**规格型号*/
	private String specNo;
	/**售价*/
	private float price;
	/**是否打印二维码 1-打印 0-不打印*/
	private int printQR;
	/**二维码是否下发给MS 1-下发 0-不下发*/
	private int isIssued;
	/**创建时间*/
	private String createTime;
	/**更新时间*/
	private String updateTime;
	
	/**产品类型名称*/
	private String categoryName;
	/**包装名称*/
	private String packageName;
	/**供应商名称*/
	private String supplierNM;
	/**包装规则容量*/
	private String capacity;
	
	/**状态名称*/
	public String getStsName()
	{
		PisConstants.Available sts = PisConstants.Available.FromKey(this.sts);
		if (sts != null)
		{
			return sts.getText();
		}
		else
		{
			return "-";
		}
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getSapNo() {
		return sapNo;
	}
	public void setSapNo(String sapNo) {
		this.sapNo = sapNo;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getCompany_id() {
		return company_id;
	}
	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}
	public int getSts() {
		return sts;
	}
	public void setSts(int sts) {
		this.sts = sts;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public int getSupplierID() {
		return supplierID;
	}
	public void setSupplierID(int supplierID) {
		this.supplierID = supplierID;
	}
	public int getPackageID() {
		return packageID;
	}
	public void setPackageID(int packageID) {
		this.packageID = packageID;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getSupplierNM() {
		return supplierNM;
	}
	public void setSupplierNM(String supplierNM) {
		this.supplierNM = supplierNM;
	}
	public String getSpecNo() {
		return specNo;
	}
	public void setSpecNo(String specNo) {
		this.specNo = specNo;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getIsIssued() {
		return isIssued;
	}
	public void setIsIssued(int isIssued) {
		this.isIssued = isIssued;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public int getPrintQR() {
		return printQR;
	}
	public void setPrintQR(int printQR) {
		this.printQR = printQR;
	}
	public String getCapacity() {
		return capacity;
	}
	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
}
