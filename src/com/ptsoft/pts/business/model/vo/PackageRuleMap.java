package com.ptsoft.pts.business.model.vo;

import com.ptsoft.common.base.BaseEntity;

public class PackageRuleMap extends BaseEntity
{

	private static final long serialVersionUID = -4444328684233534483L;
	
	/**索引ID*/
	private int id;
	/**包装规则ID*/
	private int rule_id;
	/**包装定义ID*/
	private int package_id;
	/**层级*/
	private int pkgLevel;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRule_id() {
		return rule_id;
	}
	public void setRule_id(int rule_id) {
		this.rule_id = rule_id;
	}
	public int getPackage_id() {
		return package_id;
	}
	public void setPackage_id(int package_id) {
		this.package_id = package_id;
	}
	public int getPkgLevel() {
		return pkgLevel;
	}
	public void setPkgLevel(int pkgLevel) {
		this.pkgLevel = pkgLevel;
	}
	
}
