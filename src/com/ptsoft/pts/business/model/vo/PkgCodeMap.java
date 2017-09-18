package com.ptsoft.pts.business.model.vo;

import com.ptsoft.common.base.BaseEntity;

public class PkgCodeMap extends BaseEntity {

	private static final long serialVersionUID = -2446329208046883524L;
	
	private int id;
	private String innerCode;
	private String outerCode;
	
	public PkgCodeMap(String innerCode, String outerCode) {
		super();
		this.innerCode = innerCode;
		this.outerCode = outerCode;
	}
	
	public PkgCodeMap() {
		super();
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getInnerCode() {
		return innerCode;
	}
	public void setInnerCode(String innerCode) {
		this.innerCode = innerCode;
	}
	public String getOuterCode() {
		return outerCode;
	}
	public void setOuterCode(String outerCode) {
		this.outerCode = outerCode;
	}
	
	
}
