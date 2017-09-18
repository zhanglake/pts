package com.ptsoft.pts.business.model.vo;

import com.ptsoft.common.base.BaseEntity;
import com.ptsoft.pts.PisConstants;

public class SyPackage extends BaseEntity 
{

	private static final long serialVersionUID = 4700644783029201877L;
	
	/**索引ID*/
	private int id;
	/**编码*/
	private String code;
	private String code_old;
	/**名称*/
	private String name;
	/**包装类型-大包装或小包装*/
	private String package_type;
	/**包装规格*/
	private String specifications;
	/**容量*/
	private String capacity;
	/**二维码尺寸*/
	private String qrarer_size;
	/**企业Id*/
	private int company_id;
	/**状态 0-禁用 1-可用*/
	private int sts;
	
	/**包装类型名称*/
	private String pkgTypeNm;
	
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
	public String getCode_old() {
		return code_old;
	}
	public void setCode_old(String code_old) {
		this.code_old = code_old;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPackage_type() {
		return package_type;
	}
	public void setPackage_type(String package_type) {
		this.package_type = package_type;
	}
	
	public String getSpecifications() {
		return specifications;
	}
	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}
	public String getCapacity() {
		return capacity;
	}
	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	public String getQrarer_size() {
		return qrarer_size;
	}
	public void setQrarer_size(String qrarer_size) {
		this.qrarer_size = qrarer_size;
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
	public String getPkgTypeNm() {
		return pkgTypeNm;
	}
	public void setPkgTypeNm(String pkgTypeNm) {
		this.pkgTypeNm = pkgTypeNm;
	}
}
