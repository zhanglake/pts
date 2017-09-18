package com.ptsoft.pts.business.model.vo;

import com.ptsoft.common.base.BaseEntity;

public class QRCode extends BaseEntity {

	private static final long serialVersionUID = -5970045690203441877L;
	
	/**
	 * 自增长ID
	 */
	private int id;
	/**
	 * 加密后的二维码
	 */
	private String qrcode;
	/**
	 * 企业ID
	 */
	private int companyId;
	/**
	 * 产品ID
	 */
	private int productId;
	/**
	 * 包装层级
	 */
	private int pkgLevel;
	/**
	 * 生产供应商ID
	 */
	private int supplierId;
	/**
	 * 包装ID
	 */
	private int packageId;
	/**
	 * 二维码状态 0-已生成 1-已绑定商品 2-已打印 3-已包装 4-已入库 5-已出库 6-流通中 7-已使用 8-已作废 9-已拆分
	 */
	private int status;
	/**
	 * 状态名称
	 */
	private String stsName;
	/**
	 * 生成时间
	 */
	private String createTime;
	/**
	 * 打印时间
	 */
	private String printTime;
	/**
	 * 加密salt
	 */
	private String salt;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 随机码
	 */
	private String serialNo;
	
	/**
	 * 打印标记
	 */
	private int canPrint;
	/**
	 * 下发MS是否成功
	 */
	private int isSuccess;
	/**
	 * 订单ID
	 */
	private String orderNo;
	/**
	 * 销售单ID
	 */
	private int saleOrderId;
	
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
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getPkgLevel() {
		return pkgLevel;
	}
	public void setPkgLevel(int pkgLevel) {
		this.pkgLevel = pkgLevel;
	}
	public int getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}
	public int getPackageId() {
		return packageId;
	}
	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getStsName() {
		return stsName;
	}
	public void setStsName(String stsName) {
		this.stsName = stsName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getSalt() {
		return salt;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setPrintTime(String printTime) {
		this.printTime = printTime;
	}
	public String getPrintTime() {
		return printTime;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public int getCanPrint() {
		return canPrint;
	}
	public void setCanPrint(int canPrint) {
		this.canPrint = canPrint;
	}
	public int getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(int isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public int getSaleOrderId() {
		return saleOrderId;
	}
	public void setSaleOrderId(int saleOrderId) {
		this.saleOrderId = saleOrderId;
	}
	
}
