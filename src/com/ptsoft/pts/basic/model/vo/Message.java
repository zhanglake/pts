package com.ptsoft.pts.basic.model.vo;

import com.ptsoft.common.base.BaseEntity;

public class Message extends BaseEntity {

	private static final long serialVersionUID = -4057512043503211151L;
	
	/**
	 * 自增长ID
	 */
	private int id;
	/**
	 * 手机号
	 */
	private String mobile;
	
	/**
	 * 验证码
	 */
	private String code;
	
	/**
	 * 短信内容
	 */
	private String content;
	
	/**
	 * 发送时间
	 */
	private String sendTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	
}	
