package com.ptsoft.pts.basic.model.vo;

import com.ptsoft.common.base.BaseEntity;
import com.ptsoft.pts.PisConstants;

public class Dealer extends BaseEntity {

	private static final long serialVersionUID = -1167761020336245381L;
	
	/**自增长ID*/
	private int id;
	/**税号*/
	private String revenueNO;
	/**编码*/
	private String dealer_code;
	/**名称*/
	private String dealer_name;
	/**企业内部编码*/
	private String innerCode;
	/**联系人*/
	private String contact;
	/**联系电话*/
	private String phone;
	/**电话*/
	private String tel;
	/**区号*/
	private String areaCode;
	/**省份*/
	private String province;
	/**地址*/
	private String address;
	/**备注*/
	private String remark;
	/**是否可用 1-可用 0-不可用*/
	private int status;
	
	/**用于判断经销商是否分配给企业 0未分配，其他值已分配*/
	private int isSelect;
	
	
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
	public String getRevenueNO() {
		return revenueNO;
	}
	public void setRevenueNO(String revenueNO) {
		this.revenueNO = revenueNO;
	}
	public String getDealer_code() {
		return dealer_code;
	}
	public void setDealer_code(String dealer_code) {
		this.dealer_code = dealer_code;
	}
	public String getDealer_name() {
		return dealer_name;
	}
	public void setDealer_name(String dealer_name) {
		this.dealer_name = dealer_name;
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
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public int getIsSelect() {
		return isSelect;
	}
	public void setIsSelect(int isSelect) {
		this.isSelect = isSelect;
	}

	public void setInnerCode(String innerCode) {
		this.innerCode = innerCode;
	}

	public String getInnerCode() {
		return innerCode;
	}
	
}
