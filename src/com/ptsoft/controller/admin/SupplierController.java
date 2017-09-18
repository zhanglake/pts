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
import com.ptsoft.pts.basic.model.vo.Supplier;
import com.ptsoft.pts.basic.service.SupplierService;
import com.ptsoft.pts.system.model.vo.SysArea;
import com.ptsoft.pts.system.service.AreaService;

@Controller("AdminSupplierController")
@RequestMapping("/admin/supplier/*")
public class SupplierController {
	
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private AreaService areaService;
	
	/**
	 * 进入生产供应商管理页面 
	 * @author zumin.yang
	 * @date 2016-1-28
	 * @return
	 */
	@RequestMapping("/supplierPage.do")
	public String supplierPage(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		
		return "admin/supplier/supplier";
	}
	
	/**
	 * 查询生产供应商 有分页
	 * @author zumin.yang
	 * @date 2016-1-28
	 */
	@RequestMapping("/supplierList.do")
	public void supplierList(HttpServletRequest request, HttpServletResponse response, String searchParam, Pageable pageable)
	{
		SysUser user = PisUtils.getCurrentUser(request);
		int count = this.supplierService.findCountByCompIdAndSearch(user.getCompany_id(), searchParam);
		List<Object> list = this.supplierService.findByCompAndSearch(user.getCompany_id(), searchParam, pageable);
		ResponseUtils.renderJson(response, new Page<Object>(list, count));
	}
	
	/**
	 * 无分页
	 * @author jqi.can
	 * @date 2016-4-26下午07:55:49
	 */
	@RequestMapping("/suppliersList.do")
	public void suppliersList(HttpServletRequest request, HttpServletResponse response)
	{
		SysUser user = PisUtils.getCurrentUser(request);
		List<Supplier> list = this.supplierService.findByCompanyId(user.getCompany_id());
		ResponseUtils.renderJsons(response, list);
	}
	
	/**
	 * 生产供应商编辑页
	 * @author zumin.yang
	 * @date 2016-1-28下午06:03:34
	 */
	@RequestMapping("/supplierEditPage.do")
	public String supplierEditPage(HttpServletResponse response, Model model, String id)
	{
		List<PisConstants.Available> availables = Arrays.asList(PisConstants.Available.values());
		List<PisConstants.Status> has = Arrays.asList(PisConstants.Status.values());
		List<SysArea> provinces = this.areaService.findRoots();
		
		
		model.addAttribute("id", id);
		model.addAttribute("sSts",  PisUtils.list2Option(availables, "getKey", "getText", null, false));
		model.addAttribute("has",  PisUtils.list2Option(has, "getKey", "getText", null, false));
		model.addAttribute("sProvince",  PisUtils.list2Option(provinces, "getName", "getName", null, false));
		return "admin/supplier/supplierEditor";
	}
	
	/**
	 * 生产供应商信息
	 * @author zumin.yang
	 * @date 2016-1-28下午06:03:26
	 */
	@RequestMapping("/supplierEdit.do")
	public void supplierEdit(HttpServletRequest request, HttpServletResponse response, Model model, String id)
	{
		Supplier supplier = null;
		if (!id.equals(""))
		{
			int companyId = PisUtils.getCurrentUser(request).getCompany_id();
			supplier = this.supplierService.getByCompanyIdAndId(companyId, Integer.parseInt(id));
		}
		if (supplier == null)
		{
			supplier = new Supplier();
		}
		
		ResponseUtils.renderJson(response, supplier);
	}
	
	/**
	 * 保存生产供应商信息
	 * @author zumin.yang
	 * @date 2016-1-28下午06:03:41
	 */
	@RequestMapping("/supplierSave.do")
	public void supplierSave(HttpServletRequest request, HttpServletResponse response, Supplier supplier)
	{
		String msg = this.supplierService.saveCompanySupplier(PisUtils.getCurrentUser(request).getCompany_id(), supplier);
		ResponseUtils.renderText(response, msg);
	}
}
