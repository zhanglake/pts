
package com.ptsoft.pts.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * Http Demo for Java
 * 参考文档《短信综合信息管理系统_Http接口文档.doc》
 *
 */
public class MsgUtil {

	
	static String cdkey = "3SDK-EMS-0130-LHWRO";
	
	static String password = "138117";
	
	final static String HTTP_URL = "http://sdk4http.eucp.b2m.cn:80/sdkproxy";
	
	public static String sendTextSms(String mobile,String content) throws Exception{
		
		String address = HTTP_URL + "/sendsms.action";
		
		StringBuilder params = new StringBuilder();
		params.append("cdkey=").append(cdkey);
		params.append("&password=").append(password);
		params.append("&phone=").append(mobile);
		params.append("&message=").append(content);
		
		URL url = new URL(address);
		URLConnection connection = url.openConnection();
		connection.setDoOutput(true);
		OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "utf-8");
		out.write(params.toString()); 
		out.flush();
		out.close();
		String temp = "";
		String result = "";
		InputStream is = connection.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		while (( temp = br.readLine()) != null) {
			result += temp;
		}
		return result;
	}
	
	public static String queryReport() throws Exception{
		
		String address = HTTP_URL + "/getreport.action";
		
		StringBuilder params = new StringBuilder();
		params.append("cdkey=").append(cdkey);
		params.append("&password=").append(password);
		
		URL url = new URL(address);
		URLConnection connection = url.openConnection();
		connection.setDoOutput(true);
		OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "utf-8");
		out.write(params.toString());  //post的关键所在！
		out.flush();
		out.close();
		String temp = "";
		String result = "";
		InputStream is = connection.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		while (( temp = br.readLine()) != null) {
			result += temp;
		}
		return result;
	}
	
	public static void main(String[] args) throws Exception{
		String result = "";
		result = sendTextSms("18061474098", "【无锡威孚】威孚溯源短信验证码:0001");
		System.out.println("========sendMsg result========" + result);
		
		result = queryReport();
		System.out.println("========query result========" + result);
	}

}
