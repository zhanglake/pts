package com.ptsoft.controller.supplier;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ptsoft.common.util.Page;
import com.ptsoft.common.util.Pageable;
import com.ptsoft.common.util.ResponseUtils;
import com.ptsoft.pts.PisUtils;
import com.ptsoft.pts.account.model.vo.SysUser;
import com.ptsoft.pts.basic.model.vo.Supplier;
import com.ptsoft.pts.basic.service.SupplierService;
import com.ptsoft.pts.business.model.vo.ApplyApproval;
import com.ptsoft.pts.business.model.vo.PackageRuleMap;
import com.ptsoft.pts.business.model.vo.Product;
import com.ptsoft.pts.business.model.vo.ProductInfo;
import com.ptsoft.pts.business.model.vo.SyPackage;
import com.ptsoft.pts.business.service.ApplyApprovalService;
import com.ptsoft.pts.business.service.PackageRuleService;
import com.ptsoft.pts.business.service.PackageService;
import com.ptsoft.pts.business.service.ProductInfoService;
import com.ptsoft.pts.business.service.ProductService;
import com.ptsoft.pts.business.service.QRCodeService;
import com.ptsoft.pts.system.service.SysLogService;

@Controller("SupplierPortalController")
@RequestMapping("/supplier/portal/*")
public class PortalController
{
	@Autowired
	private QRCodeService qrCodeService;
	@Autowired
	private ProductService productService;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private PackageService packageService;
	@Autowired
	private PackageRuleService ruleService;
	@Autowired
	private ProductInfoService productInfoService;
	@Autowired
	private ApplyApprovalService applyApprovalService;
	@Autowired
	private SysLogService logService;
	
	@RequestMapping("/myPortal.do")
	public String myPortal(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		int supplierId = PisUtils.getCurrentUser(request).getSupplier_id();
		String productId = request.getParameter("productId");
		String number = request.getParameter("number");
		
		//HashMap<String, Object> obj = this.qrCodeService.getQrCodeCount(supplierId);
		//int printedNum = 0, canUsedNum = 0, total = 0;
		
		/*if(null != obj)
		{
			*//**已打印*//*
			printedNum = Integer.parseInt(obj.get("PRINTQR").toString());
			*//**可用*//*
			canUsedNum = Integer.parseInt(obj.get("CANPRINTQR").toString());
			*//**总数*//*
			total = Integer.parseInt(obj.get("ALLQR").toString());
		}*/
		
		/**产品*/
		List<Product> products = this.productService.findBySupplierId(supplierId);
		/**供应商*/
		Supplier supplier = this.supplierService.getById(supplierId);
		
		/*model.addAttribute("printedNum", printedNum);
		model.addAttribute("canUsedNum", canUsedNum);
		model.addAttribute("total", total);*/
		
		model.addAttribute("sProduct", PisUtils.list2Option(products, "getId", "getName", null, false));
		model.addAttribute("supplier", supplier);
		model.addAttribute("productId", productId == null ? 0 : productId);
		model.addAttribute("number", number == null ? 0 : number);
		
		return "supplier/portal/myPortal";
	}
	
	/**
	 * 显示可用二维码数
	 * @author jqi.can
	 * @date 2016-5-13下午03:15:54
	 */
	@RequestMapping("/showLeft.do")
	public void showLeft(HttpServletRequest request, HttpServletResponse response, int productId)
	{
		int supplierId = PisUtils.getCurrentUser(request).getSupplier_id();
		ResponseUtils.renderJsons(response, this.qrCodeService.showLeft(supplierId, productId));
	}
	
	/**
	 * 显示二维码
	 * @author jqi.can
	 * @date 2016-3-17下午04:44:07
	 */
	@RequestMapping("/showQRCode.do")
	public void showQRCode(HttpServletRequest request, HttpServletResponse response)
	{
		int number = Integer.parseInt(request.getParameter("number"));
		int productId = Integer.parseInt(request.getParameter("productId"));
		int pkgLevel = Integer.parseInt(request.getParameter("pkgLevel"));
		int pkgId = Integer.parseInt(request.getParameter("pkgId"));
		int supplierId = PisUtils.getCurrentUser(request).getSupplier_id();
		
		List<Map<String, Object>> codeList = this.qrCodeService.searchCodeToPrinted(number, productId, pkgLevel, pkgId, supplierId);
		/*for (int i = 0; i < codeList.size(); i++) 
		{
			String code = "";
			for (int j = 0; j < codeList.get(i).size(); j++) 
			{
				code = codeList.get(i).get(j).get("QRCODE").toString();
				System.out.println(code + "---------" + DesUtil.decrypt(code.replace(SysConfig.get_weifu_url(), ""), PisConstants.QRSalt));
			}
		}*/
		
		ResponseUtils.renderJsons(response, codeList);
	}
	
	/**
	 * 判断输入数量是否达到最小包装容量
	 * @author jqi.can
	 * @date 2016-3-18下午02:04:46
	 */
	@RequestMapping("/judgeEnoughToMinPkg.do")
	public void judgeEnoughToMinPkg(HttpServletRequest request, HttpServletResponse response, Model model, int number, int productId)
	{
		Product product = this.productService.getById(productId);
		SyPackage pkg = this.packageService.findMinPackage(product.getPackageID());
		HashMap<String, String> map = new HashMap<String, String>();
		if(number < Integer.parseInt(pkg.getCapacity()))
		{
			map.put("capacity", pkg.getCapacity());
			map.put("code", "-1");
		}
		else
		{
			map.put("capacity", pkg.getCapacity());
			map.put("code", "1");
		}
		
		ResponseUtils.renderJson(response, map);
	}
	
	/**
	 * 判断是否有已绑定未打印二维码的生产信息
	 * @author jqi.can
	 * @date 2016-3-21下午10:09:03
	 */
	@RequestMapping("/hasProductInfo.do")
	public void hasProductInfo(HttpServletRequest request, HttpServletResponse response, int number, int productId)
	{
		int supplier_id = PisUtils.getCurrentUser(request).getSupplier_id();
		String msg = this.productInfoService.hasProductInfo(supplier_id, productId, number);
		ResponseUtils.renderText(response, msg);
	}
	
	/**
	 * 进入补录页面
	 * @author jqi.can
	 * @date 2016-3-17下午04:54:15
	 */
	@RequestMapping("/recruitPage.do")
	public String recruitPage(HttpServletRequest request, HttpServletResponse response, Model model, int number, int productId)
	{
		Product product = this.productService.getById(productId);
		
		model.addAttribute("product", product);
		model.addAttribute("number", number);
		
		return "supplier/portal/recruit";
	}
	
	/**
	 * 保存生产信息
	 * @author jqi.can
	 * @date 2016-3-18上午11:08:50
	 */
	@RequestMapping("/saveProductInfo.do")
	public void saveProductInfo(HttpServletRequest request, HttpServletResponse response, ProductInfo productInfo)
	{
		int minPkgNum = Integer.parseInt(request.getParameter("number"));
		int productId = Integer.parseInt(request.getParameter("productId"));
		int supplierId = PisUtils.getCurrentUser(request).getSupplier_id();
		
		String msg = "0";
		try 
		{
			msg = this.productInfoService.saveAndBindQRCode(productInfo, minPkgNum, productId, supplierId);
		}
		catch (Exception e) 
		{
			System.out.println(e.toString() + "save product info supplier");
		}
		
		ResponseUtils.renderText(response, msg);
	}
	
	/**
	 * 打印二维码页面
	 * @author jqi.can
	 * @throws UnsupportedEncodingException 
	 * @throws NumberFormatException 
	 * @date 2016-4-2下午05:01:03
	 */
	@RequestMapping("/printQRCode.do")
	public String printQRCode(HttpServletRequest request, HttpServletResponse response, Model model) throws NumberFormatException, UnsupportedEncodingException
	{
		int productId = Integer.parseInt(URLDecoder.decode(request.getParameter("productId"), "UTF-8"));
		int pkgLevel = Integer.parseInt(URLDecoder.decode(request.getParameter("pkgLevel"), "UTF-8"));
		int pkgId = Integer.parseInt(URLDecoder.decode(request.getParameter("pkgId"), "UTF-8"));
		int number = Integer.parseInt(URLDecoder.decode(request.getParameter("number"), "UTF-8"));
		//String ids = URLDecoder.decode(request.getParameter("ids"), "UTF-8");

		int isSingle = 1;
		Product product = this.productService.getById(productId);
		SyPackage syPackage = this.packageService.getById(pkgId);
		String supplierName = PisUtils.getCurrentUser(request).getSupplier_name();
		List<PackageRuleMap> ruleMap = this.ruleService.getRuleMap(product.getPackageID());
		
		if(null != ruleMap && ruleMap.size() > 1)
		{
			isSingle = ruleMap.size();
		}
		
		model.addAttribute("product", product);
		model.addAttribute("supplierName", supplierName);
		model.addAttribute("pkgLevel", pkgLevel);
		//model.addAttribute("ids", ids);
		model.addAttribute("capacity", syPackage.getCapacity());
		model.addAttribute("isSingle", isSingle);
		model.addAttribute("number", number);
		model.addAttribute("pkgId", pkgId);
		
		
		return "supplier/portal/print";
	}
	
	/**
	 * 显示一个层级的二维码
	 * @author jqi.can
	 * @date 2016-4-2下午06:20:59
	 */
	@RequestMapping("/showOneLevelQRCode.do")
	public void showOneLevelQRCode(HttpServletResponse response, HttpServletRequest request)
	{
		//String ids = request.getParameter("ids");
		int productId = Integer.parseInt(request.getParameter("productId"));
		int pkgLevel = Integer.parseInt(request.getParameter("pkgLevel"));
		int pkgId = Integer.parseInt(request.getParameter("pkgId"));
		int number = Integer.parseInt(request.getParameter("number"));
		int supplierId = PisUtils.getCurrentUser(request).getSupplier_id();
		
		List<Map<String, Object>> list = this.qrCodeService.searchCodeToPrinted(number, productId, pkgLevel, pkgId, supplierId);
		ResponseUtils.renderJsons(response, list);
	}
	
	/**
	 * 获取包装容量
	 * @author jqi.can
	 * @date 2016-4-2下午07:06:41
	 */
	@RequestMapping("/getCapacity.do")
	public void getCapacity(HttpServletResponse response, int pkgId)
	{
		SyPackage syPackage = this.packageService.getById(pkgId);
		ResponseUtils.renderText(response, syPackage.getCapacity());
	}
	
	/**
	 * 二维码申请页面
	 * @author jqi.can
	 * @date 2016-4-12上午11:15:20
	 */
	@RequestMapping("/applyPage.do")
	public String toApplyPage(HttpServletRequest request, Model model)
	{
		int supplierId = PisUtils.getCurrentUser(request).getSupplier_id();
		List<Product> products = this.productService.findBySupplierId(supplierId);
		
		model.addAttribute("sProduct", PisUtils.list2Option(products, "getId", "getName", null, false));
		return "supplier/portal/apply";
	}
	
	/**
	 * 申请列表
	 * @author jqi.can
	 * @date 2016-4-12上午11:17:11
	 */
	@RequestMapping("/applyList.do")
	public void applyList(HttpServletRequest request, HttpServletResponse response, Pageable pageable, String fmtm, String totm)
	{
		int supplierId = PisUtils.getCurrentUser(request).getSupplier_id();
		int count = this.applyApprovalService.getSizeByParam(supplierId, fmtm, totm);
		List<ApplyApproval> list = this.applyApprovalService.getByParam(supplierId, fmtm, totm, pageable);
		
		ResponseUtils.renderJson(response, new Page<ApplyApproval>(list, count));
	}
	
	/**
	 * 二维码申请
	 * @author jqi.can
	 * @date 2016-4-12下午01:42:28
	 */
	@RequestMapping("/applyQRCode.do")
	public void applyQRCode(HttpServletRequest request, HttpServletResponse response, int productId, int count)
	{
		SysUser currentUser = PisUtils.getCurrentUser(request);
		String msg = this.applyApprovalService.commitApply(currentUser, productId, count);
		
		ResponseUtils.renderText(response, msg);
	}
	
	/**
	 * 补打
	 * @author jqi.can
	 * @date 2016-5-25下午02:57:09
	 */
	@RequestMapping("/recruitPrint.do")
	public String recruitPrint()
	{
		return "supplier/portal/recruitPrint";
	}
	
	/**
	 * 序列号显示二维码
	 * @author jqi.can
	 * @date 2016-5-25下午03:15:25
	 */
	@RequestMapping("/showBySerialNo.do")
	public void showBySerialNo(HttpServletRequest request, HttpServletResponse response, String serialNo)
	{
		ResponseUtils.renderJson(response, this.qrCodeService.getBySerialNo(serialNo));
	}
	
	/**
	 * 加载产品
	 * @author jqi.can
	 * 2016-6-15下午02:38:19
	 */
	@RequestMapping("/loadProduct.do")
	public void loadProduct(HttpServletRequest request, HttpServletResponse response)
	{
		int supplierId = PisUtils.getCurrentUser(request).getSupplier_id();
		List<Product> products = this.productService.findBySupplierId(supplierId);
		ResponseUtils.renderJsons(response, products);
	}
	
	/**
	 * 更新二维码已打印
	 * @author jqi.can
	 * 2016-6-25下午04:06:52
	 */
	@RequestMapping("/updateQr.do")
	public void updateQr(HttpServletRequest request, HttpServletResponse response)
	{
		String[] qrcodes = request.getParameterValues("printQr");
		ResponseUtils.renderText(response, this.qrCodeService.updatePrinted(qrcodes));
	}
	
	/**
	 * 打印二维码写入日志
	 * @author jqi.can
	 * 2016-6-29下午07:23:11
	 */
	@RequestMapping("/insertLog.do")
	public void insertLog(HttpServletRequest request, HttpServletResponse response)
	{
		String number = request.getParameter("number");
		String pkgLevel = request.getParameter("pkgLevel");
		String productName = request.getParameter("productName");
		String productCode = request.getParameter("productCode");
		SysUser user = PisUtils.getCurrentUser(request);
		this.logService.insertPrintLog(number, pkgLevel, user, productName, productCode);
	}
	
	/**
	 * 加载包装规则
	 * @author jqi.can
	 * 2016-7-13下午02:38:51
	 */
	@RequestMapping("/loadRuleMap.do")
	public void loadRuleMap(HttpServletRequest request, HttpServletResponse response)
	{
		String productId = request.getParameter("productId");
		HashMap<String, Object> map = this.productService.getPackageRule(productId);
		ResponseUtils.renderJson(response, map);
	}
}
