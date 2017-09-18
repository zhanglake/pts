package com.ptsoft.pts.business.model.vo;

import com.ptsoft.common.base.BaseEntity;
import com.ptsoft.pts.PisConstants;

public class PackageRule extends BaseEntity
{

	private static final long serialVersionUID = -7336508013572687511L;
	
	/**索引ID*/
	private int id;
	/**编码*/
	private String code;
	/**名称*/
	private String name;
	/**企业*/
	private int company_id;
	/**状态 0-禁用 1-可用*/
	private int sts;
	
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
}
