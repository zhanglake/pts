package com.ptsoft.pts.account.model.vo;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.ptsoft.common.base.BaseEntity;
import com.ptsoft.pts.PisConstants;

@JsonIgnoreProperties(value = {"action"})
public class SysUser extends BaseEntity
{
	private static final long serialVersionUID = 560087008677615668L;
	
	private int usrId;
	//关联的角色
	private SysRole role;
	private String lgnNm;
	private String usrNm;
	private String pswd;
	private String email;
	private int sts = 0;
	private int company_id;
	private int supplier_id;
	private int dealer_id;
	private int isLgn = 0;
	
	private String company_name;
	private String supplier_name;
	private String dealer_name;
	
	private String mobile;
	private int point;
	private String address;
	
	private String token;
	private String expireTime;
	
	private List<String> actions;
	@JsonIgnore
	private String action;
	
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
	
	public int getIsLgn() {
		return isLgn;
	}

	public void setIsLgn(int isLgn) {
		this.isLgn = isLgn;
	}
	
	public int getCompany_id() {
		return company_id;
	}

	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}

	public int getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(int supplier_id) {
		this.supplier_id = supplier_id;
	}

	public int getDealer_id() {
		return dealer_id;
	}

	public void setDealer_id(int dealer_id) {
		this.dealer_id = dealer_id;
	}

	public int getUsrId()
	{
		return usrId;
	}

	public void setUsrId(int usrId)
	{
		this.usrId = usrId;
	}

	public String getLgnNm()
	{
		return lgnNm;
	}

	public void setLgnNm(String lgnNm)
	{
		this.lgnNm = lgnNm;
	}

	public String getUsrNm()
	{
		return usrNm;
	}

	public void setUsrNm(String usrNm)
	{
		this.usrNm = usrNm;
	}

	public String getPswd()
	{
		return pswd;
	}

	public void setPswd(String pswd)
	{
		this.pswd = pswd;
	}

	public int getSts()
	{
		return sts;
	}

	public void setSts(int sts)
	{
		this.sts = sts;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getSupplier_name() {
		return supplier_name;
	}

	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}

	public String getDealer_name() {
		return dealer_name;
	}

	public void setDealer_name(String dealer_name) {
		this.dealer_name = dealer_name;
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	/**  
	 * 获取role  
	 * @return role role  
	 */
	public SysRole getRole()
	{
		return role;
	}

	/**  
	 * 设置role  
	 * @param role role  
	 */
	public void setRole(SysRole role)
	{
		this.role = role;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

	public String getExpireTime() {
		return expireTime;
	}

	public List<String> getActions() {
		return actions;
	}

	public void setActions(List<String> actions) {
		this.actions = actions;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}