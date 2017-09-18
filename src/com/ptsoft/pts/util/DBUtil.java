package com.ptsoft.pts.util;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ptsoft.pts.business.model.vo.ProductInfo;

/**
 * 连接SQL server Util
 * @author jqi.can
 * @date 2016-07-14
 */
public class DBUtil 
{
	private static final Logger logger = LoggerFactory.getLogger(DBUtil.class);
	
	public static Connection getConn()
	{
		String driver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
		
		String url = "jdbc:microsoft:sqlserver://10.1.10.92:1433;DatabaseName=wf";
		String username = "wfpts";
		String password = "wfpts123456";
		
		/*String url = "jdbc:microsoft:sqlserver://192.168.1.3:1433;DatabaseName=wfpts";
		String username = "sa";
		String password = "2wsx3edc";*/
		
		Connection conn = null;
		try 
		{
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);
		}
		catch (Exception e) 
		{
			logger.error("--getConn--" +e.toString());
		}
		return conn;
	}
	
	public static int insert(String info)
	{
		int result = 0;
		String sql = "Insert Into TB_PTS_PRODUCT(qrCode, productCode, packageRule, packageLevel, createDate, sapNo, name, orderNo) " +
				"Values(?, ?, ?, ?, ?, ?, ?, ?)";
		String[] detail = info.split(",");
		
		Connection conn = getConn();
		PreparedStatement pstmt;
		
		try 
		{
			//二维码 产品编码 包装规则 包装层级 创建日期 SAP号 产品名称 订单号
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, detail[0]);
			pstmt.setString(2, detail[1]);
			pstmt.setString(3, detail[2]);
			pstmt.setInt(4, Integer.parseInt(detail[3]));
			pstmt.setString(5, detail[4]);
			pstmt.setString(6, detail[5]);
			pstmt.setString(7, detail[6]);
			pstmt.setString(8, detail[7]);
			
			result = pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		}
		catch (Exception e) 
		{
			logger.error("--insert--" + e.toString());
		}
		return result;
	}
	
	public static String select(int id)
	{
		String result = "";
		String sql = "Select qrcode From TB_PTS_PRODUCT Where id = ?";
		Connection conn = getConn();
		PreparedStatement pstmt;
		try 
		{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rst = pstmt.executeQuery();
			while (rst.next()) 
			{
				result = rst.getString("qrcode");
			}
			pstmt.close();
			conn.close();
		} catch (Exception e) 
		{
			logger.error("--select--" + e.toString());
		}
		return result;
	}
	
	public static void main(String[] args)
	{
		//System.out.println(select(6084));
		//System.out.println(queryIsExist("http://pts.weifu.com.cn/PTS/Qr.html?Y2PnbH9tHXX3Obebrc2f2D1HA6e2YHTlTjACBwXE/w0eYaI9n7+9A/p8kUkxQyCD"));
		/*System.out.println(new Date());
		ProductInfo byQrcode = getByQrcode("http://pts.weifu.com.cn/PTS/Qr.html?/c4LRKrTuPhAiVQYOCTldIueMI2Wvw3X4oLBeU2nJQZ5cMzd3tvMu0vTqEgq1AFe");
		System.out.println(byQrcode.getQrCode());
		System.out.println(new Date());
		String result = HttpUtil.executePost("http://127.0.0.1:8020/MSServer/api/product/getProductInfo.do", "http://pts.weifu.com.cn/PTS/Qr.html?/c4LRKrTuPhAiVQYOCTldIueMI2Wvw3X4oLBeU2nJQZ5cMzd3tvMu0vTqEgq1AFe", null);
		System.out.println(result);
		System.out.println(new Date());*/
		
		testOracle();
	}
	
	public static int queryIsExist(String qrcode) 
	{
		int result = 0;
		String sql = "Select id From TB_PTS_PRODUCT Where qrcode = ?";
		Connection conn = getConn();
		PreparedStatement pstmt;
		try 
		{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, qrcode);
			ResultSet rst = pstmt.executeQuery();
			while (rst.next()) 
			{
				result = rst.getInt("id");
			}
			pstmt.close();
			conn.close();
		} catch (Exception e) 
		{
			logger.error("--select--" + e.toString());
		}
		return result;
	}
	
	public static ProductInfo getByQrcode(String qrcode)
	{
		ProductInfo info = new ProductInfo();
		String sql = "Select p.id id, p.qrcode qrCode, p.printDate printDate, p.sapNo sapNo, p.serialNo serialNo, p.name product_name, " + 
		"p.produceDate produce_time, p.packageDate package_time, p.validateUser validateUser, p.package packageTp, p.packageLine packageLine, " + 
		"p.productCode productCode From TB_PTS_PRODUCT p Where p.qrCode = ?";
		
		Connection conn = getConn();
		PreparedStatement pstmt;
		try 
		{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, qrcode);
			ResultSet rst = pstmt.executeQuery();
			while (rst.next()) 
			{
				info.setId(rst.getInt("id"));
				info.setQrCode(rst.getString("qrCode"));
				info.setPrintDate(rst.getString("printDate"));
				info.setSapNo(rst.getString("sapNo"));
				info.setSerialNo(rst.getString("serialNo"));
				info.setProduct_name(rst.getString("product_name"));
				info.setProduce_time(rst.getString("produce_time"));
				info.setPackage_time(rst.getString("package_time"));
				info.setValidateUser(rst.getString("validateUser"));
				info.setPackageTp(rst.getString("packageTp"));
				info.setPackageLine(rst.getString("packageLine"));
				info.setProduct_code(rst.getString("productCode"));
			}
			pstmt.close();
			conn.close();
		} catch (Exception e) 
		{
			logger.error("--getByQrcode--" + e.toString());
		}
		
		return info;
	}
	
	
	public static Connection getOracleConn()
	{
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:PTS";
		String username = "pts";
		String password = "ptspassword";		
		Connection conn = null;
		try 
		{
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);
		}
		catch (Exception e) 
		{
			logger.error("--getConn--" +e.toString());
		}
		return conn;
	}
	
	public static Array getDBArray(String[] resource, String descriptor) throws SQLException
	{
		Connection conn = DBUtil.getOracleConn();
		ArrayDescriptor arrDesc = ArrayDescriptor.createDescriptor(descriptor, conn);
		return new ARRAY(arrDesc, conn, resource);
	}
	
	public static void testOracle()
	{
	    Connection con = null;
	    PreparedStatement pre = null;
	    ResultSet result = null;
	    try
	    {
	        con = getOracleConn();
	        String sql = "select * from sy_qrcode where qrcode = ?";
	        pre = con.prepareStatement(sql);
	        pre.setString(1, "2016-07-15-ab44dc36-d130-4d56-b515-0bf9d2db9d6d");
	        result = pre.executeQuery();
	        while (result.next())
	        {
	        	System.out.println("ID: " + result.getInt("id") + ", qrcode: "+ result.getString("qrcode"));
	        }
	        pre.close();
	        con.close();
	        
	    }
	    catch (Exception e)
	    {
	        e.printStackTrace();
	    }
	}
}
