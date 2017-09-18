package com.ptsoft.pts.business.model.vo;

import com.ptsoft.common.base.BaseEntity;

public class SaleOrderDetail extends BaseEntity
{

	private static final long serialVersionUID = 4081959390576646774L;
	
	private int id;
	/**销售订单ID*/
	private int saleOrderId;
	/**物品ID*/
	private int productId;
	/**物品编号*/
	private String productNo;
	/**物品名称*/
	private String productName;
	/**数量*/
	private String count;
	/**箱数*/
	private String box;
	/**货位*/
	private String location;
	/**计量单位*/
	private String unit;
	
	private int kingDetailId;
	private int kingOrderId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSaleOrderId() {
		return saleOrderId;
	}
	public void setSaleOrderId(int saleOrderId) {
		this.saleOrderId = saleOrderId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getBox() {
		return box;
	}
	public void setBox(String box) {
		this.box = box;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public int getKingDetailId() {
		return kingDetailId;
	}
	public void setKingDetailId(int kingDetailId) {
		this.kingDetailId = kingDetailId;
	}
	public int getKingOrderId() {
		return kingOrderId;
	}
	public void setKingOrderId(int kingOrderId) {
		this.kingOrderId = kingOrderId;
	}
	
}
