package com.ptsoft.pts.basic.model.vo;

import com.ptsoft.common.base.BaseEntity;

public class Synch extends BaseEntity
{
	private static final long serialVersionUID = 3592337598079068381L;
	
	private int id;
	/**金算盘订单ID*/
	private int kingId;
	/**金算盘订单日期*/
	private String kingDate;
	/**同步类型 1-采购订单 2-销售订单*/
	private int syncType;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getKingId() {
		return kingId;
	}
	public void setKingId(int kingId) {
		this.kingId = kingId;
	}
	public String getKingDate() {
		return kingDate;
	}
	public void setKingDate(String kingDate) {
		this.kingDate = kingDate;
	}
	public int getSyncType() {
		return syncType;
	}
	public void setSyncType(int syncType) {
		this.syncType = syncType;
	}
	
}
