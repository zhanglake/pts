package com.ptsoft.pts.system.model.vo;

import com.ptsoft.common.base.BaseEntity;

public class ProductLog extends BaseEntity
{

	private static final long serialVersionUID = 1367054338175590337L;
	
	/**索引*/
	private int id;
	/**用户ID*/
	private int usrId;
	/**产品ID*/
	private int productId;
	/**修改信息*/
	private String logInfo;
	/**修改时间*/
	private String createTime;
	/**操作人*/
	private String usrNm;
	/**产品名称*/
	private String productNm;
	
	
	public ProductLog() 
	{
	}

	public ProductLog(int usrId, int productId, String logInfo)
	{
		this.usrId = usrId;
		this.productId = productId;
		this.logInfo = logInfo;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUsrId() {
		return usrId;
	}
	public void setUsrId(int usrId) {
		this.usrId = usrId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getLogInfo() {
		return logInfo;
	}
	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUsrNm() {
		return usrNm;
	}

	public void setUsrNm(String usrNm) {
		this.usrNm = usrNm;
	}

	public String getProductNm() {
		return productNm;
	}

	public void setProductNm(String productNm) {
		this.productNm = productNm;
	}

}
