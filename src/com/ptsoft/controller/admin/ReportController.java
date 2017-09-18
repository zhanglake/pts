package com.ptsoft.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ptsoft.common.util.ExportUtil;
import com.ptsoft.common.util.Page;
import com.ptsoft.common.util.Pageable;
import com.ptsoft.common.util.ResponseUtils;
import com.ptsoft.pts.PisUtils;
import com.ptsoft.pts.account.service.UserService;
import com.ptsoft.pts.business.service.SaleOrderService;
import com.ptsoft.pts.business.service.ScanRecordService;

@Controller("AdminController")
@RequestMapping("/admin/report/*")
public class ReportController 
{
	@Autowired
	private UserService userService;
	@Autowired
	private ScanRecordService scanService;
	@Autowired
	private SaleOrderService saleService;
	
	/**
	 * 用户积分报表
	 * @author jqi.can
	 * 2016-8-19上午10:02:31
	 */
	@RequestMapping("/endUserPoints.do")
	public String endUserPoint()
	{
		return "admin/report/endUserPoint";
	}
	
	/**
	 * 获取用户
	 * @author jqi.can
	 * 2016-8-19上午10:11:26
	 */
	@RequestMapping("/getScanUser.do")
	public void getScanUser(HttpServletRequest request, HttpServletResponse response, Pageable pageable)
	{
		String searchParam = request.getParameter("searchParam");
		String fmTm = request.getParameter("fmTm");
		String toTm = request.getParameter("toTm");
		String rlId = request.getParameter("rlId");
		int companyId = PisUtils.getCurrentUser(request).getCompany_id();
		
		List<Object> users =  this.userService.getScanUser(fmTm, toTm, rlId, searchParam, companyId, pageable);
		int count = this.userService.getScanUserConut(fmTm, toTm, rlId, searchParam, companyId);
		
		ResponseUtils.renderJson(response, new Page<Object>(users, count));
	}
	
	/**
	 * 积分报表导出
	 * @user jqi.can
	 * 2016年10月24日上午10:59:11
	 */
	@RequestMapping("/exportPoints.do")
	public void exportPoints(HttpServletRequest request, HttpServletResponse response)
	{
		HashMap<String, String> result = new HashMap<String, String>();
		String url = "";
		ArrayList<Object> list;
		
		int companyId = PisUtils.getCurrentUser(request).getCompany_id();
		String searchParam = request.getParameter("searchParam");
		String fmTm = request.getParameter("fmTm");
		String toTm = request.getParameter("toTm");
		String rlId = request.getParameter("rlId");
		String xlsTitle = request.getParameter("title");
		
		try 
		{
			list = this.userService.exportPoints(fmTm, toTm, rlId, searchParam, companyId);
			
			LinkedHashMap<String,String> title = new LinkedHashMap<String, String>();
			
			title.put("LGNNM", "登录名");
			title.put("USRNM", "用户名");
			title.put("MOBILE", "手机号");
			title.put("ADDRESS", "地址");
			title.put("RLNM", "用户类型");
			title.put("CURPOINT", "本期积分");
			title.put("POINT", "总积分");

			url = ExportUtil.ExportToExcel(title, list, xlsTitle);
			
			result.put("message", "1");
			result.put("url", url);
		}
		catch (Exception e) 
		{
			result.put("message", "0");
			result.put("url", url);
			System.out.println(e.toString());
		}
		ResponseUtils.renderJson(response, result);
	}
	
	/**
	 * 积分明细
	 * @author jqi.can
	 * 2016-8-19上午11:23:11
	 */
	@RequestMapping("/getPointDetails.do")
	public void getPointDetails(HttpServletRequest request, HttpServletResponse response)
	{
		String userId = request.getParameter("usrId");
		String fmTm = request.getParameter("fmTm");
		String toTm = request.getParameter("toTm");
		
		List<Object> list = this.scanService.getPointDetails(userId, fmTm, toTm);
		
		ResponseUtils.renderJsons(response, list);
	}
	
	/**
	 * 销售报表页面
	 * @user jqi.can
	 * 2016年10月13日下午2:18:33
	 */
	@RequestMapping("/salesReport.do")
	public String salesReport(HttpServletRequest request, Model model)
	{
		String fmTm = request.getParameter("fmTm");
		String toTm = request.getParameter("toTm");
		if(StringUtils.isNotEmpty(fmTm) && StringUtils.isNotEmpty(toTm))
		{
			model.addAttribute("fmTm", fmTm);
			model.addAttribute("toTm", toTm);
		}
		return "admin/report/sales";
	}
	
	/**
	 * 销售报表
	 * @user jqi.can
	 * 2016年10月13日下午3:04:17
	 */
	@RequestMapping("/sales.do")
	public void sales(HttpServletRequest request, HttpServletResponse response, Pageable pageable)
	{
		String fmTm = request.getParameter("fmTm");
		String toTm = request.getParameter("toTm");
		String searchParam = request.getParameter("searchParam");
		int companyId = PisUtils.getCurrentUser(request).getCompany_id();
		
		List<Object> list =  this.saleService.getSalesReport(fmTm, toTm, searchParam, companyId, pageable);
		int count = this.saleService.getReportConut(fmTm, toTm, searchParam, companyId);
		
		ResponseUtils.renderJson(response, new Page<Object>(list, count));
	}
	
	/**
	 * 销售单列表页面
	 * @user jqi.can
	 * 2016年10月13日下午4:15:29
	 */
	@RequestMapping("/orderPage.do")
	public String orderPage(String unitNo, String unitName, String fmTm, String toTm, int type, Model model)
	{
		model.addAttribute("fmTm", fmTm);
		model.addAttribute("toTm", toTm);
		model.addAttribute("unitNo", unitNo);
		model.addAttribute("unitName", unitName);
		model.addAttribute("type", type);
		return "admin/report/order";
	}
	
	/**
	 * 销售单列表
	 * @user jqi.can
	 * 2016年10月13日下午4:17:58
	 */
	@RequestMapping("/orderList.do")
	public void orderList(HttpServletRequest request, HttpServletResponse response, Pageable pageable)
	{
		String fmTm = request.getParameter("fmTm");
		String toTm = request.getParameter("toTm");
		String type = request.getParameter("type");
		String unitNo = request.getParameter("unitNo");
		int companyId = PisUtils.getCurrentUser(request).getCompany_id();
		
		List<Object> list =  this.saleService.reportList(fmTm, toTm, unitNo, type, companyId, pageable);
		int count = this.saleService.reportListCount(fmTm, toTm, unitNo, type, companyId);
		
		ResponseUtils.renderJson(response, new Page<Object>(list, count));
	}
	
	/**
	 * 单据明细
	 * @user jqi.can
	 * 2016年10月14日下午1:49:18
	 */
	@RequestMapping("/orderDetail.do")
	public void orderDetail(HttpServletResponse response, int orderId, int type)
	{
		ResponseUtils.renderJsons(response, this.saleService.orderDetail(orderId, type));
	}
	
	/**
	 * 二维码明细
	 * @user jqi.can
	 * 2016年10月14日下午2:40:39
	 */
	@RequestMapping("/qrcodeDetail.do")
	public void qrcodeDetail(HttpServletResponse response, int orderId, int productId)
	{
		ResponseUtils.renderJsons(response, this.saleService.qrcodeDetail(orderId, productId));
	}
	
	/**
	 * 销售报表导出
	 * @user jqi.can
	 * 2016年10月14日上午10:12:42
	 */
	@RequestMapping("/exportSales.do")
	public void exportSales(HttpServletRequest request, HttpServletResponse response)
	{
		HashMap<String, String> result = new HashMap<String, String>();
		String url = "";
		ArrayList<Object> list;
		
		int companyId = PisUtils.getCurrentUser(request).getCompany_id();
		String fmTm = request.getParameter("fmTm");
		String toTm = request.getParameter("toTm");
		String searchParam = request.getParameter("searchParam");
		String xlsTitle = request.getParameter("title");
		
		try 
		{
			list = this.saleService.exportSales(fmTm, toTm, searchParam, companyId);
			
			LinkedHashMap<String,String> title = new LinkedHashMap<String, String>();
			
			title.put("UNITNO", "单位编码");
			title.put("UNITNAME", "单位名称");
			title.put("SALEDNUM", "已出库数量");
			title.put("UNSALEDNUM", "未出库数量");

			url = ExportUtil.ExportToExcel(title, list, xlsTitle);
			
			result.put("message", "1");
			result.put("url", url);
		}
		catch (Exception e) 
		{
			result.put("message", "0");
			result.put("url", url);
			System.out.println(e.toString());
		}
		ResponseUtils.renderJson(response, result);
	}
	
	/**
	 * 销售单导出
	 * @user jqi.can
	 * 2016年10月14日上午10:53:02
	 */
	@RequestMapping("/exportOrders.do")
	public void exportOrders(HttpServletRequest request, HttpServletResponse response, String title, String toTm, String fmTm, String unitNo, int type)
	{
		HashMap<String, String> result = new HashMap<String, String>();
		String url = "";
		ArrayList<Object> list;
		
		int companyId = PisUtils.getCurrentUser(request).getCompany_id();
		
		try 
		{
			list = this.saleService.exportOrders(fmTm, toTm, unitNo, companyId, type);
			
			LinkedHashMap<String,String> column = new LinkedHashMap<String, String>();
			
			column.put("KINGID", "出库号");
			column.put("ORDERNO", "单据号");
			column.put("ORDERDATE", "订货日期");
			column.put("SYNCDATE", "同步日期");
			column.put("QRNUM", "二维码数量");

			url = ExportUtil.ExportToExcel(column, list, title);
			
			result.put("message", "1");
			result.put("url", url);
		}
		catch (Exception e) 
		{
			result.put("message", "0");
			result.put("url", url);
			System.out.println(e.toString());
		}
		ResponseUtils.renderJson(response, result);
	}
	
	/**
	 * 扫码报表页面
	 * @user jqi.can
	 * 2016年10月13日下午2:21:53
	 */
	@RequestMapping("/scanReport.do")
	public String scan()
	{
		return "admin/report/scan";
	}
	
	/**
	 * 扫码用户 
	 * @param type 1-最后一级 2-倒数第二级 3-第二级
	 * @user jqi.can
	 * 2016年10月14日下午4:55:04
	 */
	@RequestMapping("/userRecord.do")
	public void userRecord(HttpServletRequest request, HttpServletResponse response, Pageable pageable)
	{
		String fmTm = request.getParameter("fmTm");
		String toTm = request.getParameter("toTm");
		String type = request.getParameter("type");
		String searchParam = request.getParameter("searchParam");
		int companyId = PisUtils.getCurrentUser(request).getCompany_id();
		
		List<Object> list =  this.userService.reportList(fmTm, toTm, type, searchParam, companyId, pageable);
		int count = this.userService.reportListCount(fmTm, toTm, searchParam, type, companyId);
		
		ResponseUtils.renderJson(response, new Page<Object>(list, count));
	}
	
	/**
	 * 关联用户
	 * @user jqi.can
	 * 2016年10月17日下午2:16:49
	 */
	@RequestMapping("/getRelation.do")
	public void getRelation(HttpServletRequest request, HttpServletResponse response)
	{
		String usrId = request.getParameter("usrId");
		ResponseUtils.renderJsons(response, this.userService.getRelation(Integer.parseInt(usrId)));
	}
	
	/**
	 * 扫码报表导出
	 * @param type 1-最后一级 2-倒数第二级 3-第二级
	 * @user jqi.can
	 * 2016年10月17日下午3:55:07
	 */
	@RequestMapping("/exportUsers.do")
	public void exportUsers(HttpServletRequest request, HttpServletResponse response)
	{
		HashMap<String, String> result = new HashMap<String, String>();
		String url = "";
		ArrayList<Object> list;
		String fmTm = request.getParameter("fmTm");
		String toTm = request.getParameter("toTm");
		String type = request.getParameter("type");
		String title = request.getParameter("title");
		String searchParam = request.getParameter("searchParam");		
		int companyId = PisUtils.getCurrentUser(request).getCompany_id();
		
		try 
		{
			list = this.userService.exportUsers(searchParam, fmTm, toTm, companyId, Integer.parseInt(type));
			
			LinkedHashMap<String,String> column = new LinkedHashMap<String, String>();
			
			column.put("LGNNM", "登录名");
			column.put("MOBILE", "手机号");
			column.put("RLNM", "用户类型");
			column.put("ADDRESS", "地址");
			column.put("POINT", "积分");

			url = ExportUtil.ExportToExcel(column, list, title);
			
			result.put("message", "1");
			result.put("url", url);
		}
		catch (Exception e) 
		{
			result.put("message", "0");
			result.put("url", url);
			System.out.println(e.toString());
		}
		ResponseUtils.renderJson(response, result);
	}
}
