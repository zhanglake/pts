package com.ptsoft.pts.basic.model.vo;

import com.ptsoft.common.base.BaseEntity;

public class PointFollowing extends BaseEntity
{

	private static final long serialVersionUID = 4185341391596525988L;
	
	private int id;
	/**用户ID*/
	private int userId;
	/**产品ID*/
	private int productId;
	/**用户名*/
	private String userName;
	/**积分*/
	private String point;
	/**时间*/
	private String operateDate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public String getOperateDate() {
		return operateDate;
	}
	public void setOperateDate(String operateDate) {
		this.operateDate = operateDate;
	}

}
