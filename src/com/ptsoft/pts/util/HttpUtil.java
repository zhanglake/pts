package com.ptsoft.pts.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.ptsoft.common.util.ExcelUtil;

public class HttpUtil 
{
	public static String executeGet(String url) throws Exception 
	{
		HttpClient client = new HttpClient();
		String sb = "";

		InputStream ins = null;
		// Create a method instance.
		GetMethod method = new GetMethod(url);
		try {
			// Execute the method.
			int statusCode = client.executeMethod(method);
			// System.out.println("======http exec get status code=========" +
			// statusCode);
			if (statusCode == HttpStatus.SC_OK) 
			{
				// ins = method.getResponseBodyAsStream();
				byte[] b = method.getResponseBody();
				sb = new String(b, "UTF-8");

				/**
				 * byte[] b = new byte[1024]; int r_len = 0; while ((r_len =
				 * ins.read(b)) > 0) { sb.append(new String(b, 0, r_len,
				 * method.getResponseCharSet())); }
				 */
			} 
			else 
			{
				System.err.println("Response Code: " + statusCode);
			}
		} 
		catch (HttpException e) 
		{
			System.err.println("Fatal protocol violation: " + e.getMessage());
		} 
		catch (IOException e) 
		{
			System.err.println("Fatal transport error: " + e.getMessage());
		} 
		finally 
		{
			method.releaseConnection();
			if (ins != null) 
			{
				ins.close();
			}
		}
		return sb;

	}
	
	public static void main(String[] args) 
	{
		/*System.out.println(new Date());
		String response = HttpUtil.executePost("http://127.0.0.1:8020/MSServer/api/product/getInner.do?outId=72969", null, null);
		JSONObject urlJsonObj = JSONObject.fromObject(response);
		String urlCode = urlJsonObj.getString("code");
		List<Integer> codes = new ArrayList<Integer>();
		
		if (null != urlCode && !"".equals(urlCode) && urlCode.equals("200"))
		{
			JSONArray jsonArray = urlJsonObj.getJSONArray("inners");
			List<ProductInfo> list = jsonArray.toList(jsonArray, ProductInfo.class);
			for (ProductInfo productInfo : list) 
			{
				codes.add(productInfo.getId());
			}
		}
		System.out.println(new Date());
		MSQrCode msQrCode = new MSQrCode(codes);
		JSONObject map = JSONObject.fromObject(msQrCode);
		expostPost("http://127.0.0.1:8020/MSServer/api/product/updateLotUsed.do", map.toString());
		System.out.println(new Date());*/
		
		/*List<ToMsQrcodeObj> msQrcodes = new ArrayList<ToMsQrcodeObj>();
		ToMsQrcodeObj msQrcodeObj = new ToMsQrcodeObj();
		for (int i = 0; i < 100; i++) 
		{
			msQrcodeObj = new ToMsQrcodeObj("http://pts.weifuusert.com.cn/PTS/Qr.html?/c4LRKrTuPgP0VGJEOEkgSw+RR7j25gYJFGDzS/nh6M1pdMU+T8cgnk2VjfS7WSm", "00ZP193", "PTS_012_120", 1, "1", "1", "XI字新品油嘴", "1");
			msQrcodes.add(msQrcodeObj);
		}
		
		if(null != msQrcodes && msQrcodes.size() > 0)
		{
			HashMap<String, Object> json = new HashMap<String, Object>();
			json.put("data", JSONArray.fromObject(msQrcodes));
			System.out.println(new Date() + "---begin---");
			String response = HttpUtil.expostPost("http://127.0.0.1:8020/MSServer/api/product/insertLot.do", json.toString());
			JSONObject object = JSONObject.fromObject(response);
			if(null != response && !"".equals(response) && object.getString("code").equals("200"))
			{
				System.out.println(new Date() + "---end---");
			}
		}*/
		/*String response = HttpUtil.executePost("http://127.0.0.1:8020/MSServer/api/product/isExist.do", "http://pts.weifu.com.cn/PTS/Qr.html?/c4LRKrTuPiO27SY+YNTU63iuni0imLH7rBIzIXu3iPFbFD9eP0LPSltJzL8vqjk", null);
		if(!"".equals(response))
		{
			JSONObject object = JSONObject.fromObject(response);
			if(object.getString("code").equals("200") && object.getString("data").equals("1"))
			{
				System.out.println("success");
			}
		}*/
		
		File file = new File("E:\\Project\\qrcode.xlsx");
    	String[][] data;
		try
		{
			String url = "http://localhost:8090/PTS/api/package/pkgRecord.do";
			String innercodes = "";
			String outerCode = "http://pts.weifu.com.cn/PTS/Qr.html?/c4LRKrTuPiqKEZzLmxjBGJJLIAmk6ZGbhcv2WMY1EbniEt33aeQ6FQXxUFBd1gO";
			
			data = ExcelUtil.getData(file, 1);
			for (String[] obj : data) 
	    	{
				innercodes += "http://pts.weifu.com.cn/PTS/Qr.html?" + DesUtil.encrypt(obj[0], "WEIFU_PTS") + ",";
			}
			innercodes = innercodes.substring(0, innercodes.length() - 1);
			
			String[] paramName = {"outerCode", "innerCodes", "type", "username", "token"};
			String[] paramValue = {outerCode, innercodes, "2", "admin", "9381ae05-b207-4742-adaf-66f5446d91f4"};
			System.out.println(new Date());
			String post = executePost(url, paramName, paramValue);
			System.out.println(new Date());
			System.out.println(post);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static String executePost(String url, String[] paramNames, String[] paramValues) 
	{
		HttpClient client = new HttpClient();
		String msg = "";
		try
		{
			PostMethod method = new PostMethod(url);
			
			method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
			if(null != paramNames && null != paramValues && paramNames.length > 0 && paramValues.length > 0)
			{
				for (int i = 0; i < paramValues.length; i++) 
				{
					method.setParameter(paramNames[i], paramValues[i]);
				}
			}
			
			int statusCode = client.executeMethod(method);
			if (statusCode == HttpStatus.SC_OK) 
			{
				byte[] b = method.getResponseBody();
				msg = new String(b, "UTF-8");
			} 
			else 
			{
				System.err.println("Response Code: " + statusCode);
			}

		} 
		catch (MalformedURLException ex) 
		{
			ex.printStackTrace();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		
		return msg;
	}
	
	public static String expostPost(String url, String jsonData)
	{
		String msg = "";
		try
		{
			HttpClient client = new HttpClient();
			PostMethod method = new PostMethod(url);
			
			RequestEntity entity = new StringRequestEntity(jsonData, "application/json","GBK");
			method.setRequestEntity(entity);
	        
			int statusCode = client.executeMethod(method);
			if (statusCode == HttpStatus.SC_OK) 
			{
				byte[] b = method.getResponseBody();
				msg = new String(b, "UTF-8");
				System.out.println(msg);
			} 
			else 
			{
				System.err.println("Response Code: " + statusCode + "----" + url);
			}
			
		} 
		catch (Exception e) 
		{
			System.out.println(e.toString() + "-----post json");
		}
		return msg;
	}

	public static String executePost(String url, String info, String createtimes) 
	{
		HttpClient client = new HttpClient();
		String msg = "";
		try
		{
			PostMethod method = new PostMethod(url);
			
			method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
			
			if(null != info && !"".equals(info))
			{
				method.setParameter("qrcodes", info);
			}
			if(null != createtimes && !"".equals(createtimes))
			{
				method.setParameter("createtimes", createtimes);
			}
			
			int statusCode = client.executeMethod(method);
			if (statusCode == HttpStatus.SC_OK) 
			{
				byte[] b = method.getResponseBody();
				msg = new String(b, "UTF-8");
			} 
			else 
			{
				System.err.println("Response Code: " + statusCode);
			}

		} 
		catch (MalformedURLException ex) 
		{
			ex.printStackTrace();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		
		return msg;
	}
	
}
