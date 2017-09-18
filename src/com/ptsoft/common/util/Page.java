package com.ptsoft.common.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页
 * 
 */
public class Page<T> implements Serializable
{
	private static final long serialVersionUID = -2053800594583879853L;

	/** 内容 */
	private final List<T> rows = new ArrayList<T>();

	/** 总记录数 */
	private final long total;

	/**
	 * @param row
	 *            内容
	 * @param total
	 *            总记录数
	 * @param pageable
	 *            分页信息
	 */
	public Page(List<T> row, long total)
	{
		this.rows.addAll(row);
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public long getTotal() {
		return total;
	}
	
	
}