package com.ptsoft.controller.admin;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.ptsoft.common.util.FileUtil;
import com.ptsoft.common.util.Page;
import com.ptsoft.common.util.Pageable;
import com.ptsoft.common.util.ResponseUtils;
import com.ptsoft.common.util.SysConfig;
import com.ptsoft.pts.PisConstants;
import com.ptsoft.pts.PisUtils;
import com.ptsoft.pts.account.model.vo.SysUser;
import com.ptsoft.pts.basic.model.vo.Supplier;
import com.ptsoft.pts.basic.service.SupplierService;
import com.ptsoft.pts.business.model.vo.PackageRule;
import com.ptsoft.pts.business.model.vo.Product;
import com.ptsoft.pts.business.service.PackageRuleService;
import com.ptsoft.pts.business.service.ProductService;
import com.ptsoft.pts.system.model.vo.ProductLog;
import com.ptsoft.pts.system.model.vo.SysDataType;
import com.ptsoft.pts.system.service.SysDataTypeService;

@Controller("AdminProductController")
@RequestMapping("/admin/product/*")
public class ProductController 
{
	@Autowired
	private ProductService productService;
	@Autowired
	private SysDataTypeService dataTypeService;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private PackageRuleService packageRuleService;
	
	/** 进入产品页面 */
	@RequestMapping("/productPage.do")
	public String productPage(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		model.addAttribute("orderId", request.getParameter("orderId"));
		return "admin/product/product";
	}
	
	/**
	 * 产品list
	 * @author jqi.can
	 * @date 2016-2-23上午11:23:51
	 */
	@RequestMapping("/productList.do")
	public void productList(HttpServletResponse response, HttpServletRequest request, String searchParam, Pageable pageable)
	{
		int companyId = PisUtils.getCurrentUser(request).getCompany_id();
		String orderId = request.getParameter("orderId");
		List<Product> list = new ArrayList<Product>();
		int count = 0;
		
		if(null != orderId && !"".equals(orderId))
		{
			list = this.productService.getListByOrderId(orderId, pageable);
			count = this.productService.getCountByOrderId(orderId);
		}
		else
		{
			list = this.productService.findByCompIdAndLike(companyId, searchParam, pageable);
			count = this.productService.getSizeByParam(companyId, searchParam);
		}
		
		ResponseUtils.renderJson(response, new Page<Product>(list, count));
	}
	
	/**
	 * 导出产品
	 * @author jqi.can
	 * 2016-7-20下午05:12:18
	 */
	@RequestMapping("/exportList.do")
	public void exportList(HttpServletRequest request, HttpServletResponse response)
	{
		HashMap<String, String> result = new HashMap<String, String>();
		String url = "";
		ArrayList<Object> list;
		
		int companyId = PisUtils.getCurrentUser(request).getCompany_id();
		String orderId = request.getParameter("orderId");
		String searchParam = request.getParameter("searchParam");
		String xlsTitle = request.getParameter("title");
		
		try 
		{
			if(null != orderId && !"".equals(orderId))
			{
				list = this.productService.exportByOrderId(orderId);
			}
			else
			{
				list = this.productService.exportList(companyId, searchParam);
			}
			
			LinkedHashMap<String,String> title = new LinkedHashMap<String, String>();
			
			title.put("CODE", "编码");
			title.put("NAME", "名称");
			title.put("SAPNO", "SAP号");
			title.put("CATEGORYNAME", "类型");
			title.put("PACKAGENAME", "包装规则");
			title.put("PRICE", "售价");
			title.put("POINTS", "积分");
			title.put("SPECNO", "规格");
			
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
	
	@RequestMapping("/productAllList.do")
	public void productAllList(HttpServletResponse response, HttpServletRequest request, String searchParam, Pageable pageable)
	{
		SysUser user = (SysUser) request.getSession().getAttribute("C_USR");
		List<Product> all = this.productService.findAll(user.getCompany_id(), searchParam);
		
		ResponseUtils.renderJsons(response, all);
	}
	
	/**
	 * 产品编辑页面
	 * @author jqi.can
	 * @date 2016-3-2上午09:37:05
	 */
	@RequestMapping("/editPage.do")
	public String editPage(HttpServletRequest request, Model model, String proId)
	{
		SysUser user = (SysUser) request.getSession().getAttribute("C_USR");
		List<PackageRule> packageRules = this.packageRuleService.findAllCanUsed(user.getCompany_id());
		List<Supplier> suppliers = this.supplierService.findByCompanyId(user.getCompany_id());
		List<PisConstants.Available> availables = Arrays.asList(PisConstants.Available.values());
		List<SysDataType> productTypes = this.dataTypeService.findByTypeAndCompID(PisConstants.DataType.ProductType.getKey(), user.getCompany_id());
		
		model.addAttribute("id", proId);
		model.addAttribute("sSts",  PisUtils.list2Option(availables, "getKey", "getText", null, false));
		model.addAttribute("sProductType", PisUtils.list2Option(productTypes, "getDctcd", "getTpnm", null, false));
		model.addAttribute("sSupplier", PisUtils.list2Option(suppliers, "getId", "getSupplier_name", null, false));
		model.addAttribute("sPackageRule", PisUtils.list2Option(packageRules, "getId", "getName", null, false));
		return "admin/product/productEditor";
	}
	
	/**
	 * 根据产品ID获取产品信息
	 * @author jqi.can
	 * @date 2016-2-23上午11:24:27
	 */
	@RequestMapping("/editProduct.do")
	public void editProduct(HttpServletResponse response, Integer proId)
	{
		ResponseUtils.renderJson(response, this.productService.getById(proId));
	}
	
	/**
	 * 保存产品信息
	 * @author jqi.can
	 * @date 2016-2-23上午11:28:11
	 */
	@RequestMapping("/saveProduct.do")
	public void saveProduct(HttpServletResponse response, HttpServletRequest request, Product product)
	{
		String msg = "";
		SysUser user = (SysUser) request.getSession().getAttribute("C_USR");
		msg = this.productService.saveProduct(product, user);
		
		ResponseUtils.renderText(response, msg);
	}
	
	/**
	 * 产品修改记录
	 * @author jqi.can
	 * 2016-6-28下午07:14:19
	 */
	@RequestMapping("/updateRecord.do")
	public void updateRecord(HttpServletRequest request, HttpServletResponse response)
	{
		String productId = request.getParameter("productId");
		List<ProductLog> list = this.productService.getUpdateRecord(productId);
		ResponseUtils.renderJsons(response, list);
	}
	
	/**
	 * 包装设置页面
	 * @author jqi.can
	 * 2016-7-20下午05:32:42
	 */
	@RequestMapping("/packagePage.do")
	public String packagePage(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		SysUser user = (SysUser) request.getSession().getAttribute("C_USR");
		List<PackageRule> packageRules = this.packageRuleService.findAllCanUsed(user.getCompany_id());
		
		model.addAttribute("sPackageRules", PisUtils.list2Option(packageRules, "getId", "getName", null, false));
		
		return "admin/product/packageSetting";
	}
	
	/**
	 * 批量设置包装
	 * @author jqi.can
	 * @date 2016-3-3下午01:53:36
	 */
	@RequestMapping("/packageSetting.do")
	public void packageSetting(HttpServletResponse response, String packageID, String products)
	{
		String msg = this.productService.setPackage(packageID, products);
		ResponseUtils.renderText(response, msg);
	}
	
	/**
	 * 加载产品
	 * @author jqi.can
	 * 2016-7-20下午06:07:20
	 */
	@RequestMapping("/loadProduct.do")
	public void loadProduct(HttpServletRequest request, HttpServletResponse response)
	{
		int companyId = PisUtils.getCurrentUser(request).getCompany_id();
		List<Product> list = this.productService.getByCompIdAndSts(companyId, 1);
		ResponseUtils.renderJsons(response, list);
	}
	
	/**
	 * 下载经销商模板文件
	 * @author jqi.can
	 * 2016-7-25上午11:25:38
	 */
	@RequestMapping("/downloadTpl.do")
	public void downloadTpl(HttpServletRequest request, HttpServletResponse response)
	{
		String url = SysConfig.get_pts_url() + "templates/tpl_product.xlsx";
		ResponseUtils.renderText(response, url);
	}
	
	/**
	 * 上传Excel
	 * @author jqi.can
	 * 2016-7-25上午11:25:45
	 */
	@RequestMapping("/importXls.do")
	public void importXls(HttpServletRequest request, HttpServletResponse response)
	{
		String message = "";
		message = FileUtil.fileUpload(request, 2);
		ResponseUtils.renderText(response, message);
	}
	
	/**
	 * 导入
	 * @author jqi.can
	 * 2016-7-25上午11:25:55
	 */
	@RequestMapping("/doImport.do")
	public void doImport(HttpServletRequest request, HttpServletResponse response)
	{
		String file = request.getParameter("file");
		int companyId = PisUtils.getCurrentUser(request).getCompany_id();
		String msg = this.productService.importData(file, companyId);
		ResponseUtils.renderText(response, msg);
	}
}
