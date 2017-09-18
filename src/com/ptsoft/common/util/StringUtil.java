package com.ptsoft.common.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtil
{
	/** 向左填充字符串到指定位数 */
	public static String padLeft(String s, int length, String symbol)
	{
		while (s.length() < length)
		{
			s = symbol + s;
		}
		return s;
	}

	/** 向右填充字符串到指定位数 */
	public static String padRight(String s, int length, String symbol)
	{
		while (s.length() < length)
		{
			s = s + symbol;
		}
		return s;
	}

	/**
	 * 二维码序列号
	 * @author jqi.can
	 * 2016-6-25下午06:45:21
	 */
	public static String getSerialNo(String createTime, int qrNum, String orderNo) 
	{
		String serialNo = "";
		String year = createTime.substring(2, 4);
		String month = createTime.substring(5, 7);
		serialNo = year + qrNum + month + orderNo;
		return serialNo;
	}

	public static List<String> getStringList(String[] ids) 
	{
		List<String> list = new ArrayList<String>();
		for (String id : ids) 
		{
			list.add(id);
		}
		return list;
	}
}
