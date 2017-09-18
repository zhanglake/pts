package com.ptsoft.controller.platform;

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
import com.ptsoft.pts.basic.model.vo.Company;
import com.ptsoft.pts.basic.model.vo.Dealer;
import com.ptsoft.pts.basic.service.CompanyDealerService;
import com.ptsoft.pts.basic.service.CompanyService;
import com.ptsoft.pts.basic.service.DealerService;
import com.ptsoft.pts.system.model.vo.SysArea;
import com.ptsoft.pts.system.service.AreaService;

@Controller
@RequestMapping("/platform/company/*")
public class CompanyController {
	
	@Autowired
	private CompanyService companyService;
	@Autowired
	private DealerService dealerService;
	@Autowired
	private CompanyDealerService companyDealerService;
	@Autowired
	private AreaService areaService;
	
	/**
	 * 进入企业列表页面
	 * @author jqi.can
	 * @date 2016-1-27下午05:54:23
	 */
	@RequestMapping("/comPage.do")
	public String ComPage()
	{
		return "platform/company/companyList";
	}
	
	/**
	 * 企业列表
	 * @author jqi.can
	 * @date 2016-1-27下午05:54:39
	 */
	@RequestMapping("/companyList.do")
	public void companyList(HttpServletResponse response, String searchItems, Pageable pageable)
	{
		int count = this.companyService.getCompanyCount(searchItems);
		List<Object> list = this.companyService.findBySearchItems(searchItems,pageable);
		ResponseUtils.renderJson(response, new Page<Object>(list, count));
	}
	/**
	 * 企业编辑页面
	 * @author jqi.can
	 * @date 2016-1-27下午06:08:35
	 */
	@RequestMapping("/companyEditPage.do")
	public String companyEditPage(HttpServletResponse response, Model model, String id)
	{
		List<PisConstants.Available> availables = Arrays.asList(PisConstants.Available.values());
		List<Dealer> dealers = this.dealerService.findAll();
		String selectDealers = this.companyDealerService.getByCompanyId(id);
		List<SysArea> provinces = this.areaService.findRoots();
		
		model.addAttribute("id", id);
		model.addAttribute("sSts",  PisUtils.list2Option(availables, "getKey", "getText", null, false));
		model.addAttribute("sDealer", PisUtils.list2Option(dealers, "getId", "getDealer_name", selectDealers, false));
		model.addAttribute("sProvince", PisUtils.list2Option(provinces, "getName", "getName", null, false));
		
		return "platform/company/companyEditor";
	}
	
	/**
	 * 获取企业信息
	 * @author jqi.can
	 * @date 2016-1-28下午02:58:45
	 */
	@RequestMapping("/companyEdit.do")
	public void companyEdit(HttpServletResponse response, Model model, String id)
	{
		Company company = null;
		if (!id.equals(""))
		{
			company = this.companyService.getById(Integer.parseInt(id));
		}
		if (company == null)
		{
			company = new Company();
		}
		
		ResponseUtils.renderJson(response, company);
	}
	
	/**
	 * 保存企业信息
	 * @author jqi.can
	 * @date 2016-1-27下午06:19:08
	 */
	@RequestMapping("/companySave.do")
	public void companySave(HttpServletResponse response, Company company)
	{
		String msg = this.companyService.saveCompany(company);
		ResponseUtils.renderText(response, msg);
	}
	
	/**
	 * 进入分配经销商页面
	 * @author jqi.can
	 * @date 2016-2-18下午03:41:26
	 */
	@RequestMapping("/distributDealersPage.do")
	public String distributDealersPage(HttpServletResponse response, Model model, int id)
	{
		Company company = this.companyService.getById(id);
		
		if (company == null)
		{
			return null;
		}
		model.addAttribute("company", company);
		return "platform/company/dealerList";
	}
	
	/**
	 * 经销商列表
	 * @author jqi.can
	 * @date 2016-2-18下午04:43:22
	 */
	@RequestMapping("/dealerList.do")
	public void dealerList(HttpServletResponse response, Pageable pageable, String compId, String searchParam)
	{
		int count = dealerService.notInCount(compId, searchParam);
		List<Dealer> list = dealerService.notInDealers(pageable, compId, searchParam);
		
		ResponseUtils.renderJson(response, new Page<Dealer>(list, count));
	}
	
	/**
	 * 分配经销商
	 * @author jqi.can
	 * @date 2016-2-2下午02:51:49
	 */
	@RequestMapping("/distributeDealer.do")
	public void distributeDealer(HttpServletResponse response, HttpServletRequest request, String companyId, String dealers)
	{
		String message = "";
		try 
		{
			this.companyDealerService.distributeDealer(companyId, dealers.split(","));
			message = "分配经销商成功！";
		} 
		catch (Exception e) 
		{
			message = "分配经销商失败！";
			e.printStackTrace();
		}
		ResponseUtils.renderText(response, message);
	}
	
	/**
	 * 已分配经销商列表
	 * @author jqi.can
	 * @date 2016-3-1下午02:56:40
	 */
	@RequestMapping("/hasDealers.do")
	public void hasDealers(HttpServletResponse response, Pageable pageable, String compId, String searchParam)
	{
		int count = dealerService.inCount(compId, searchParam);
		List<Dealer> list = dealerService.getHasDealers(pageable, compId, searchParam);
		
		ResponseUtils.renderJson(response, new Page<Dealer>(list, count));
	}
	
	/**
	 * 取消分配
	 * @author jqi.can
	 * @date 2016-3-2下午02:30:37
	 */
	@RequestMapping("/cancelDistribute.do")
	public void cancelDistribute(HttpServletResponse response, String compId, String dealers)
	{
		String message = "";
		try 
		{
			this.companyDealerService.cancelByCompId(compId, dealers.split(","));
			message = "取消分配成功！";
		} 
		catch (Exception e) 
		{
			message = "取消分配失败！";
			e.printStackTrace();
		}
		ResponseUtils.renderText(response, message);
	}

}
