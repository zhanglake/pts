package com.ptsoft.pts.basic.model.vo;

import com.ptsoft.common.base.BaseEntity;

public class AppDevice extends BaseEntity{

	private static final long serialVersionUID = -1982863375654893951L;
	
	private int id;
	/**用户ID*/
	private int userId;
	/**设备号 IMEI*/
	private String deviceNo;
	/**操作系统 1-Android 2-iOS*/
	private String optSystem;
	/**手机型号*/
	private String model;
	/**版本*/
	private String version;
	/**创建时间*/
	private String createTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public String getOptSystem() {
		return optSystem;
	}
	public void setOptSystem(String optSystem) {
		this.optSystem = optSystem;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
