package com.ptsoft.controller.supplier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ptsoft.common.util.ExportUtil;
import com.ptsoft.common.util.Page;
import com.ptsoft.common.util.Pageable;
import com.ptsoft.common.util.ResponseUtils;
import com.ptsoft.pts.PisUtils;
import com.ptsoft.pts.business.service.OrderService;

@Controller
@RequestMapping("/supplier/order/*")
public class OrderController 
{
	
	@Autowired
	private OrderService orderService;

	/**
	 * 订单查询页面
	 * @author jqi.can
	 * @date 2016-3-15上午10:25:52
	 */
	@RequestMapping("/orderPage.do")
	public String orderPage(HttpServletRequest request, HttpServletResponse response)
	{
		return "supplier/order/order";
	}
	
	@RequestMapping("/orderList.do")
	public void orderList(HttpServletRequest request, HttpServletResponse response, String searchParam, String fmtm, String totm,Pageable pageable)
	{
		int supplierID = PisUtils.getCurrentUser(request).getSupplier_id();
		int count = this.orderService.getCountBySupplierId(searchParam, fmtm, totm, supplierID);
		List<Object> list = this.orderService.findBySupplierID(searchParam, fmtm, totm, supplierID, pageable);
		ResponseUtils.renderJson(response, new Page<Object>(list, count));
	}
	
	/**
	 * 导出excel
	 * @author jqi.can
	 * 2016-7-20下午03:22:30
	 */
	@RequestMapping("/orderExport.do")
	public void orderExport(HttpServletRequest request, HttpServletResponse response)
	{
		HashMap<String, String> result = new HashMap<String, String>();
		String url = "";
		
		String xlsTitle = request.getParameter("title");
		String searchParam = request.getParameter("searchParam");
		String fmtm = request.getParameter("fmtm");
		String totm = request.getParameter("totm");
		int supplierID = PisUtils.getCurrentUser(request).getSupplier_id();
		
		try 
		{
			ArrayList<Object> list = this.orderService.exportBySupplierId(supplierID, searchParam, fmtm, totm);
			
			LinkedHashMap<String,String> title = new LinkedHashMap<String, String>();
			
			title.put("ORDERNO", "订单号");
			title.put("SAPNO", "SAP号");
			title.put("PRODUCTCODE", "产品编码");
			title.put("PRODUCTNAME", "产品名称");
			title.put("PACKAGENAME", "包装规则");
			title.put("PRODUCTCOUNT", "数量");
			title.put("CREATETIME", "订单创建时间");
			
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
	
}
