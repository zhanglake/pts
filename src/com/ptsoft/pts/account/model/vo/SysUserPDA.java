package com.ptsoft.pts.account.model.vo;

import com.ptsoft.common.base.BaseEntity;

public class SysUserPDA extends BaseEntity
{

	private static final long serialVersionUID = -7823400233660520012L;
	
	private int userId;
	private String actionId;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getActionId() {
		return actionId;
	}
	public void setActionId(String actionId) {
		this.actionId = actionId;
	}
	
}