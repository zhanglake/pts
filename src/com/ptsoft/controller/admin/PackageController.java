package com.ptsoft.controller.admin;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ptsoft.common.util.Page;
import com.ptsoft.common.util.Pageable;
import com.ptsoft.common.util.ResponseUtils;
import com.ptsoft.pts.PisConstants;
import com.ptsoft.pts.PisUtils;
import com.ptsoft.pts.account.model.vo.SysUser;
import com.ptsoft.pts.business.model.vo.PackageRule;
import com.ptsoft.pts.business.model.vo.PackageRuleMap;
import com.ptsoft.pts.business.model.vo.Product;
import com.ptsoft.pts.business.model.vo.SyPackage;
import com.ptsoft.pts.business.service.PackageRuleService;
import com.ptsoft.pts.business.service.PackageService;
import com.ptsoft.pts.business.service.ProductService;
import com.ptsoft.pts.system.model.vo.SysDataType;
import com.ptsoft.pts.system.service.SysDataTypeService;

@Controller("AdminPackageController")
@RequestMapping("/admin/package/*")
public class PackageController
{
	
	@Autowired
	private PackageService packageService;
	@Autowired
	private SysDataTypeService dataTypeService;
	@Autowired
	private PackageRuleService ruleService;
	@Autowired
	private ProductService productService;
	
	/** 包装定义页面 */
	@RequestMapping("/singlePkg.do")
	public String packageDefine(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "admin/package/packageDefine";
	}
	
	/**
	 * 包装定义列表
	 * @author jqi.can
	 * @date 2016-2-23下午03:51:44
	 */
	@RequestMapping("/packageList.do")
	public void ruleList(HttpServletRequest request, HttpServletResponse response, String searchParam, Pageable pageable)
	{
		SysUser user = (SysUser) request.getSession().getAttribute("C_USR");
		int count = this.packageService.getCountByCompIdAndSearchParam(user.getCompany_id(), searchParam);
		List<Object> list = this.packageService.findByCompIdAndLike(user.getCompany_id(), searchParam, pageable);
		ResponseUtils.renderJson(response, new Page<Object>(list, count));
	}
	
	/**
	 * 包装定义编辑页面
	 * @author jqi.can
	 * @date 2016-2-23下午04:32:19
	 */
	@RequestMapping("/packageEditPage.do")
	public String packageEditPage(HttpServletRequest request, Model model, String id)
	{
		SysUser user = (SysUser) request.getSession().getAttribute("C_USR");
		List<PisConstants.Available> availables = Arrays.asList(PisConstants.Available.values());
		List<SysDataType> list = this.dataTypeService.findByTypeAndCompID(PisConstants.DataType.PackageType.getKey(), user.getCompany_id());
		List<SysDataType> qrSizeList = this.dataTypeService.findByTypeAndCompID(PisConstants.DataType.QRCodeSize.getKey(), user.getCompany_id());
		
		model.addAttribute("ruleId", id);
		model.addAttribute("sSts",  PisUtils.list2Option(availables, "getKey", "getText", null, false));
		model.addAttribute("sPackageType", PisUtils.list2Option(list, "getDctcd", "getTpnm", null, false));
		model.addAttribute("sQRSize", PisUtils.list2Option(qrSizeList, "getDctcd", "getTpnm", null, false));
		return "admin/package/defineEditor";
	}
	
	/**
	 * 获取包装定义信息
	 * @author jqi.can
	 * @date 2016-2-23下午04:55:20
	 */
	@RequestMapping("/packageEdit.do")
	public void packageEdit(HttpServletRequest request, HttpServletResponse response, String id)
	{
		SyPackage syPackage = null;
		if(null != id && !"".equals(id))
		{
			syPackage = this.packageService.getById(Integer.parseInt(id));
		}
		if(syPackage == null)
		{
			syPackage = new SyPackage();
		}
		ResponseUtils.renderJson(response, syPackage);
	}
	
	/**
	 * 包装定义保存
	 * @author jqi.can
	 * @date 2016-2-23下午04:52:54
	 */
	@RequestMapping("/pacakgeSave.do")
	public void pacakgeSave(HttpServletResponse response, HttpServletRequest request, SyPackage syPackage)
	{
		SysUser user = (SysUser) request.getSession().getAttribute("C_USR");
		String msg = this.packageService.savePackage(syPackage, user.getCompany_id());
		ResponseUtils.renderText(response, msg);
	}
	
	/**
	 * 包装规则页面
	 * @author jqi.can
	 * @date 2016-2-23下午02:02:46
	 */
	@RequestMapping("/multiPkg.do")
	public String packageRule(HttpServletRequest request, HttpServletResponse response)
	{
		return "admin/package/packageRule";
	}
	
	/**
	 * 包装规则列表
	 * @author jqi.can
	 * @date 2016-2-23下午06:03:40
	 */
	@RequestMapping("/packageRuleList.do")
	public void packageRuleList(HttpServletRequest request, HttpServletResponse response, String searchParam,Pageable pageable)
	{
		SysUser user = (SysUser) request.getSession().getAttribute("C_USR");
		int count = this.ruleService.getpackageRuleCountByCompIdAndSearchParam(user.getCompany_id(), searchParam);
		List<Object> list = this.ruleService.findByCompIdAndLike(user.getCompany_id(), searchParam, pageable);
		ResponseUtils.renderJson(response, new Page<Object>(list, count));
	}
	
	/**
	 * 包装规则编辑页面
	 * @author jqi.can
	 * @date 2016-2-24上午10:13:15
	 */
	@RequestMapping("/packageRuleEditPage.do")
	public String packageRuleEditPage(HttpServletRequest request, Model model, String id)
	{
		SysUser user = (SysUser) request.getSession().getAttribute("C_USR");
		List<PisConstants.Available> availables = Arrays.asList(PisConstants.Available.values());
		List<Product> list = this.productService.getByCompIdAndSts(user.getCompany_id(), 1);
		
		model.addAttribute("ruleId", id);
		model.addAttribute("sSts",  PisUtils.list2Option(availables, "getKey", "getText", null, false));
		model.addAttribute("sProduct", PisUtils.list2Option(list, "getId", "getName", null, false));
		return "admin/package/ruleEditor";
	}
	
	/**
	 * 获取包装规则信息
	 * @author jqi.can
	 * @date 2016-2-24上午10:14:22
	 */
	@RequestMapping("/packageRuleEdit.do")
	public void packageRuleEdit(HttpServletResponse response, String id)
	{
		PackageRule packageRule = null;
		if(null != id && !"".equals(id))
		{
			packageRule = this.ruleService.getById(Integer.parseInt(id));
		}
		if(packageRule == null)
		{
			packageRule = new PackageRule();
		}
		ResponseUtils.renderJson(response, packageRule);
	}
	
	/**
	 * 保存包装规则
	 * @author jqi.can
	 * @date 2016-2-24上午10:43:46
	 */
	@RequestMapping("/pacakgeRuleSave.do")
	public void pacakgeRuleSave(HttpServletResponse response, HttpServletRequest request, PackageRule entity)
	{
		SysUser user = (SysUser) request.getSession().getAttribute("C_USR");
		String msg = this.ruleService.saveRule(entity, user.getCompany_id());
		ResponseUtils.renderText(response, msg);
	}
	
	/**
	 * 包装设置页面
	 * @author jqi.can
	 * @date 2016-2-24下午05:54:33
	 */
	@RequestMapping("/packageSetPage.do")
	public String packageSetPage(Model model, String id)
	{
		PackageRule rule = this.ruleService.getById(Integer.parseInt(id));
		if (rule == null) 
		{
			return null;
		}
		model.addAttribute("rule", rule);
		model.addAttribute("sPackage", PisUtils.list2Option(this.packageService.findAllBySts(1), "getId", "getName", null, false));
		return "admin/package/packageRuleMap";
	}
	
	/**
	 * 保存包装设置
	 * @author jqi.can
	 * @date 2016-2-24下午06:03:53
	 */
	@RequestMapping("/savePkgMap.do")
	public void savePkgMap(HttpServletResponse response, PackageRuleMap entity)
	{
		String msg = this.ruleService.savePkgMap(entity);
		ResponseUtils.renderText(response, msg);
	}
	
	/**
	 * 根据包装规则ID获取包装定义列表
	 * @author jqi.can
	 * @date 2016-2-24上午10:34:15
	 */
	@RequestMapping("/packageGetByRuleID.do")
	public void packageGetByRuleID(HttpServletResponse response, Integer ruleId)
	{
		ResponseUtils.renderJsons(response, this.ruleService.getByRuleId(ruleId));
	}
	
	/**
	 * 删除包装设置
	 * @author jqi.can
	 * @date 2016-2-25上午10:42:36
	 */
	@RequestMapping("/deletePkgMap.do")
	public void deletePkgMap(HttpServletResponse response, Integer ruleId, Integer pkgId)
	{
		String msg = this.ruleService.removeByRuleAndPkg(ruleId, pkgId);
		ResponseUtils.renderText(response, msg);
	}
	
}
