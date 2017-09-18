package com.ptsoft.pts.business.model.vo;

import java.io.Serializable;
import java.util.List;

public class OrderDto implements Serializable{

	private static final long serialVersionUID = -1341177280764442177L;
	
	private int orderId;
	
	private List<OrderItem> items;

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	public List<OrderItem> getItems() {
		return items;
	}
	
	
}
