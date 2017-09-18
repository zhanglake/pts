package com.ptsoft.controller.supplier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ptsoft.common.util.ExportUtil;
import com.ptsoft.common.util.Page;
import com.ptsoft.common.util.Pageable;
import com.ptsoft.common.util.ResponseUtils;
import com.ptsoft.pts.PisUtils;
import com.ptsoft.pts.business.model.vo.Product;
import com.ptsoft.pts.business.service.ProductInfoService;
import com.ptsoft.pts.business.service.ProductService;

@Controller("SupplierProductInfoController")
@RequestMapping("/supplier/productInfo/*")
public class ProductInfoController 
{
	@Autowired
	private ProductInfoService productInfoService;
	@Autowired
	private ProductService productService;
	
	/** 进入产品页面 */
	@RequestMapping("/productInfoPage.do")
	public String productPage(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "supplier/productInfo/productInfo";
	}
	
	/**
	 * 生产信息列表
	 * @author jqi.can
	 * @date 2016-3-16下午02:41:10
	 */
	@RequestMapping("/productInfoList.do")
	public void productInfoList(HttpServletRequest request, HttpServletResponse response, String searchParam, String fmtm, String totm,Pageable pageable)
	{
		int supplierId = PisUtils.getCurrentUser(request).getSupplier_id();
		int count = this.productInfoService.getInfoCount(supplierId, searchParam, fmtm, totm);
		List<Object> list = this.productInfoService.findAllBySearchParam(supplierId, searchParam, fmtm, totm, pageable);
		ResponseUtils.renderJson(response, new Page<Object>(list, count));
	}
	
	/**
	 * 补录生产信息页面
	 * @author jqi.can
	 * @date 2016-3-16下午04:00:28
	 */
	@RequestMapping("/recruitPage.do")
	public String recruitPage(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		List<Product> list = this.productService.findBySupplierId(PisUtils.getCurrentUser(request).getSupplier_id());
		model.addAttribute("sProduct", PisUtils.list2Option(list, "getId", "getCode", null, false));
		
		return "supplier/productInfo/recruit";
	}
	
	/**
	 * 导出excel
	 * @author jqi.can
	 * 2016-7-22下午02:27:09
	 */
	@RequestMapping("/exportExcel.do")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response)
	{
		String searchParam = request.getParameter("searchParam");
		String fmtm = request.getParameter("fmtm");
		String totm = request.getParameter("totm");
		String xlsTitle = request.getParameter("title");
		int supplierId = PisUtils.getCurrentUser(request).getSupplier_id();
		
		HashMap<String, String> result = new HashMap<String, String>();
		String url = "";
		try 
		{
			ArrayList<Object> list = this.productInfoService.getForExcel(supplierId, searchParam, fmtm, totm);
			
			LinkedHashMap<String,String> title = new LinkedHashMap<String, String>();
			title.put("SERIALNO", "流水号");
			title.put("SAPNO", "SAP号");
			title.put("PRODUCT_CODE", "产品编码");
			title.put("PRODUCT_NAME", "产品名称");
			title.put("PACKAGETP", "包装");
			title.put("PACKAGELINE", "装配线");
			title.put("VALIDATEUSER", "校验员");
			title.put("PRODUCE_TIME", "生产时间");
			title.put("PACKAGE_TIME", "包装时间");
			
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
