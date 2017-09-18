package com.ptsoft.pts.basic.model.vo;

import com.ptsoft.common.base.BaseEntity;
import com.ptsoft.pts.PisConstants;

public class Supplier extends BaseEntity {

	private static final long serialVersionUID = 3237605629951528732L;
	
	/**自增长ID*/
	private int id;
	/**编码*/
	private String supplier_code;
	/**名称*/
	private String supplier_name;
	/**企业内部编码*/
	private String innerCode;
	/**是否有系统支撑  1—有系统支撑 0-无系统支撑*/
	private Integer has_system;
	/**联系人*/
	private String contact;
	/**联系电话*/
	private String phone;
	/**传真*/
	private String fax;
	/**邮箱*/
	private String email;
	/**地区*/
	private String province;
	/**备注*/
	private String remark;
	/**是否可用 1-可用 0-不可用*/
	private int status;
	/**详细地址*/
	private String address;
	/**是否有打印权限 1-有 0-没有*/
	private int canPrint;
	
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
	
	/**状态名称*/
	public String getSysName()
	{
		PisConstants.Status sts = PisConstants.Status.FromKey(this.has_system);
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
	public String getSupplier_code() {
		return supplier_code;
	}
	public void setSupplier_code(String supplier_code) {
		this.supplier_code = supplier_code;
	}
	public String getSupplier_name() {
		return supplier_name;
	}
	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}
	public Integer getHas_system() {
		return has_system;
	}
	public void setHas_system(Integer has_system) {
		this.has_system = has_system;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}

	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddress() {
		return address;
	}

	public int getCanPrint() {
		return canPrint;
	}

	public void setCanPrint(int canPrint) {
		this.canPrint = canPrint;
	}

	public void setInnerCode(String innerCode) {
		this.innerCode = innerCode;
	}

	public String getInnerCode() {
		return innerCode;
	}
}
