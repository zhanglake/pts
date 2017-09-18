package com.ptsoft.pts.basic.model.vo;

import com.ptsoft.common.base.BaseEntity;
import com.ptsoft.pts.PisConstants;
import com.ptsoft.pts.system.model.vo.SysArea;

public class Stock extends BaseEntity {

	private static final long serialVersionUID = 1020728275605581223L;
	
	/**
	 * 自增长ID
	 */
	private int id;
	
	/**
	 * 库位编码
	 */
	private String code;
	
	/**
	 * 库位名称
	 */
	private String name;
	
	/**
	 * 企业ID
	 */
	private Integer companyId;
	
	private String areaID;
	/**地区*/
	private SysArea area;
	
	/**地址*/
	private String address;
	
	/**是否可用 1-可用 0-不可用*/
	private Integer status;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**状态名称*/
	public String getStsName()
	{
		PisConstants.Available sts = PisConstants.Available.FromKey(this.status);
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
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public SysArea getArea() {
		return area;
	}
	public void setArea(SysArea area) {
		this.area = area;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setAreaID(String areaID) {
		this.areaID = areaID;
	}

	public String getAreaID() {
		return areaID;
	}
	
	

}
