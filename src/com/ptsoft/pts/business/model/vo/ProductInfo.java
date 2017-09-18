package com.ptsoft.pts.business.model.vo;

import com.ptsoft.common.base.BaseEntity;

public class ProductInfo extends BaseEntity
{

	private static final long serialVersionUID = 1007933049744060860L;
	
	/**id*/
	private int id;
	/**sap号*/
	private String sapNo;
	/**序列号*/
	private String serialNo;
	/**产品ID*/
	private int product_id;
	/**产品编号*/
	private String product_code;
	/**产品名称*/
	private String product_name;
	/**包装*/
	private String packageTp;
	/**装配线*/
	private String packageLine;
	/**操作人*/
	private String operater;
	/**校验员工*/
	private String validateUser;
	/**包装时间*/
	private String package_time;
	/**生产时间*/
	private String produce_time;
	/**关联二维码*/
	private String qrCode;
	/**二维码打印时间*/
	private String printDate;
	/**创建时间*/
	private String createDate;
	
	/**
	 * 以下字段为MS接口字段
	 */
	/**采购单号*/
	private String orderNo;
	/**产品编码*/
	private String productCode;
	/**包装规则*/
	private String packageRule;
	/**包装层级*/
	private int packageLevel;
	/**外包装二维码ID*/
	private String outerQrCodeId;
	

	/**
	 * @return 
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return 
	 */
	public String getSapNo() {
		return sapNo;
	}
	/**
	 * @param sapNo
	 */
	public void setSapNo(String sapNo) {
		this.sapNo = sapNo;
	}
	/**
	 * @return 
	 */
	public String getSerialNo() {
		return serialNo;
	}
	/**
	 * @param serialNo
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	/**
	 * @return 
	 */
	public int getProduct_id() {
		return product_id;
	}
	/**
	 * @param product_id
	 */
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	/**
	 * @return 
	 */
	public String getProduct_code() {
		return product_code;
	}
	/**
	 * @param product_code
	 */
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	/**
	 * @return 
	 */
	public String getProduct_name() {
		return product_name;
	}
	/**
	 * @param product_name
	 */
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	/**
	 * @return 
	 */
	public String getPackageTp() {
		return packageTp;
	}
	/**
	 * @param packageTp
	 */
	public void setPackageTp(String packageTp) {
		this.packageTp = packageTp;
	}
	/**
	 * @return 
	 */
	public String getPackageLine() {
		return packageLine;
	}
	/**
	 * @param packageLine
	 */
	public void setPackageLine(String packageLine) {
		this.packageLine = packageLine;
	}
	/**
	 * @return 
	 */
	public String getOperater() {
		return operater;
	}
	/**
	 * @param operater
	 */
	public void setOperater(String operater) {
		this.operater = operater;
	}
	/**
	 * @return 
	 */
	public String getValidateUser() {
		return validateUser;
	}
	/**
	 * @param validateUser
	 */
	public void setValidateUser(String validateUser) {
		this.validateUser = validateUser;
	}
	/**
	 * @return 
	 */
	public String getPackage_time() {
		return package_time;
	}
	/**
	 * @param package_time
	 */
	public void setPackage_time(String package_time) {
		this.package_time = package_time;
	}
	/**
	 * @return 
	 */
	public String getProduce_time() {
		return produce_time;
	}
	/**
	 * @param produce_time
	 */
	public void setProduce_time(String produce_time) {
		this.produce_time = produce_time;
	}
	/**
	 * @return 
	 */
	public String getQrCode() {
		return qrCode;
	}
	/**
	 * @param qrCode
	 */
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	public String getPrintDate() {
		return printDate;
	}
	public void setPrintDate(String printDate) {
		this.printDate = printDate;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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
	public String getOuterQrCodeId() {
		return outerQrCodeId;
	}
	public void setOuterQrCodeId(String outerQrCodeId) {
		this.outerQrCodeId = outerQrCodeId;
	}
}
