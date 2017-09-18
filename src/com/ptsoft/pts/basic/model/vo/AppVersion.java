package com.ptsoft.pts.basic.model.vo;

import com.ptsoft.common.base.BaseEntity;

public class AppVersion extends BaseEntity
{

	private static final long serialVersionUID = -2089095375150645164L;
	
	private int id;
	/**企业标识*/
	private String comFlag;
	/**操作系统 1-iOS 2-Android*/
	private int os;
	/**版本号*/
	private String version;
	/**下载地址*/
	private String url;
	/**描述*/
	private String description;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getComFlag() {
		return comFlag;
	}
	public void setComFlag(String comFlag) {
		this.comFlag = comFlag;
	}
	public int getOs() {
		return os;
	}
	public void setOs(int os) {
		this.os = os;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
