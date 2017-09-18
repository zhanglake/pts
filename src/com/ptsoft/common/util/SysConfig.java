package com.ptsoft.common.util;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SysConfig
{
	/**威孚地址*/
	private static String weifu_url = "";
	/**MS接口地址*/
	private static String ms_url = "";
	/**金算盘接口地址*/
	private static String ga_url = "";
	/**当前Web系统地址*/
	private static String pts_url = "";
	
	/**
	 * 获取配置的当前Web系统地址
	 * @return
	 */
	public static String get_pts_url()
	{
		if (pts_url.equals(""))
		{
			getSysConfigInfo();
		}
		if (!pts_url.endsWith("/"))
		{
			pts_url = pts_url + "/";
		}
		return pts_url;
	}
	
	/**
	 * 获取金算盘接口地址
	 * @author jqi.can
	 * @date 2016-4-28下午03:28:53
	 */
	public static String get_ga_url()
	{
		if(ga_url.equals(""))
		{
			getSysConfigInfo();
		}
		if (!ga_url.endsWith("/"))
		{
			ga_url = ga_url + "/";
		}
		return ga_url;
	}
	
	/**
	 * 获取MS接口地址
	 * @author jqi.can
	 * @date 2016-4-28下午03:27:41
	 */
	public static String get_ms_url()
	{
		if(ms_url.equals(""))
		{
			getSysConfigInfo();
		}
		if (!ms_url.endsWith("/"))
		{
			ms_url = ms_url + "/";
		}
		return ms_url;
	}
	
	
	/**
	 * 获取威孚地址
	 * @author jqi.can
	 * @date 2016-4-26下午04:26:42
	 */
	public static String get_weifu_url()
	{
		if(weifu_url.equals(""))
		{
			getSysConfigInfo();
		}
		return weifu_url;
	}
	
	/**
	 * 获取Web容器下导出模板文件的实际路径
	 * @return
	 * @throws IOException
	 */
	public static String getExportTemplateFilePath() throws IOException
	{
		return getRootPath() + "template/template.xls";
	}

	/**
	 * 获取Web容器的实际路径
	 * @return
	 */
	public static String getRootPath()
	{
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String path = request.getSession().getServletContext().getRealPath("/");
		return path;
	}
	
	/**
	 * 分析SysConfig文件
	 */
	private static void getSysConfigInfo()
	{
		try
		{
			String url = Thread.currentThread().getContextClassLoader().getResource("SysConfig.xml").toString();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(url);

			Element root = doc.getDocumentElement();

			NodeList nodelist = root.getChildNodes();

			if (nodelist != null)
			{
				for (int i = 0; i < nodelist.getLength(); i++)
				{
					Node node = nodelist.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE)
					{
						if(node.getNodeName().equals("weifu_url"))
						{
							weifu_url = node.getTextContent().toString().trim();
						}
						else if(node.getNodeName().equals("ms_url"))
						{
							ms_url = node.getTextContent().toString().trim();
						}
						else if(node.getNodeName().equals("ga_url"))
						{
							ga_url = node.getTextContent().toString().trim();
						}
						else if(node.getNodeName().equals("pts_url"))
						{
							pts_url = node.getTextContent().toString().trim();
						}
					}
				}
			}
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		System.out.println(get_weifu_url());
	}
}
