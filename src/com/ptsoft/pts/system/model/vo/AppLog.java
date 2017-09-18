package com.ptsoft.pts.system.model.vo;

import com.ptsoft.common.base.BaseEntity;

public class AppLog extends BaseEntity
{

	private static final long serialVersionUID = 4602780667976734830L;
	
	/**索引*/
	private int id;
	/**用户ID*/
	private int usrId;
	/**用户名*/
	private String usrName;
	/**登录时间*/
	private String loginTime;
	/**IMEI号*/
	private String deviceSeries;
	/**手机型号*/
	private String model;
	/**系统版本*/
	private String os;
	
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
	public String getUsrName() {
		return usrName;
	}
	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}
	public String getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	public String getDeviceSeries() {
		return deviceSeries;
	}
	public void setDeviceSeries(String deviceSeries) {
		this.deviceSeries = deviceSeries;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}

}
