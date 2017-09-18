package com.ptsoft.pts.business.model.vo;

import com.ptsoft.common.base.BaseEntity;
import com.ptsoft.pts.basic.model.vo.Supplier;

public class OrderItem extends BaseEntity {

	private static final long serialVersionUID = -4928027648805323747L;
	
	private int id;
	
	private int orderId;
	
	private int productId;
	
	private Product product;
	
	private Supplier supplier;
	
	private int count;
	
	private String remark;
	/**包装规则名称*/
	private String packageNm;
	/**包装容量*/
	private String capacity;
	
	private int kingOrderId;
	private int kingDetailId;
	private String product_code;
	private String product_name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPackageNm() {
		return packageNm;
	}

	public void setPackageNm(String packageNm) {
		this.packageNm = packageNm;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getProductId() {
		return productId;
	}

	public int getKingOrderId() {
		return kingOrderId;
	}

	public void setKingOrderId(int kingOrderId) {
		this.kingOrderId = kingOrderId;
	}

	public int getKingDetailId() {
		return kingDetailId;
	}

	public void setKingDetailId(int kingDetailId) {
		this.kingDetailId = kingDetailId;
	}

	public String getProduct_code() {
		return product_code;
	}

	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
}
