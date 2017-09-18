package com.ptsoft.common.util;

import java.io.Serializable;

/**
 * 分页信息
 * 
 */
public class Pageable implements Serializable
{
	private static final long serialVersionUID = -3930180379790344299L;

	/** 页面记录数 默认10个 */
	private int limit;

	/** 分页时数据的偏移量 */
	private int offset;
	
	/**
	 * 获取每页记录数
	 * 
	 * @return 每页记录数
	 */
	public int getLimit()
	{
		return limit;
	}

	/**
	 * 设置每页记录数
	 * 
	 * @param pageSize
	 *            每页记录数
	 */
	public void setLimit(int limit)
	{
		this.limit = limit;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
	
}