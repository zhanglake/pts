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
import com.ptsoft.pts.basic.model.vo.Dealer;
import com.ptsoft.pts.basic.service.CompanyDealerService;
import com.ptsoft.pts.basic.service.CompanyService;
import com.ptsoft.pts.basic.service.DealerService;
import com.ptsoft.pts.system.model.vo.SysArea;
import com.ptsoft.pts.system.service.AreaService;

@Controller("PlatformDealerController")
@RequestMapping("/platform/dealer/*")
public class DealerController {

	@Autowired
	private DealerService dealerService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private CompanyDealerService companyDealerService;
	@Autowired
	private AreaService areaService;
	
	/**
	 * 经销商页面
	 * @author jqi.can
	 * @date 2016-1-28下午05:48:01
	 */
	@RequestMapping("/dealerPage.do")
	public String dealerPage()
	{
		return "platform/dealer/dealerList";
	}
	
	/**
	 * 经销商列表
	 * @author jqi.can
	 * @date 2016-1-28下午05:48:49
	 */
	@RequestMapping("/dealerList.do")
	public void dealerList(HttpServletResponse response, String searchParam,Pageable pageable)
	{
		int count = this.dealerService.getdealerCount(searchParam);
		List<Object> list = this.dealerService.findBySearchParam(searchParam,pageable);
		ResponseUtils.renderJson(response, new Page<Object>(list, count));
	}
	
	/**
	 * 经销商编辑页
	 * @author jqi.can
	 * @date 2016-1-28下午06:03:34
	 */
	@RequestMapping("/dealerEditPage.do")
	public String dealerEditPage(HttpServletResponse response, Model model, String id)
	{
		List<PisConstants.Available> availables = Arrays.asList(PisConstants.Available.values());
		List<SysArea> provinces = this.areaService.findRoots();
		
		model.addAttribute("id", id);
		model.addAttribute("sSts",  PisUtils.list2Option(availables, "getKey", "getText", null, false));
		model.addAttribute("sProvince",  PisUtils.list2Option(provinces, "getName", "getName", null, false));
		
		return "platform/dealer/dealerEditor";
	}
	
	/**
	 * 经销商信息
	 * @author jqi.can
	 * @date 2016-1-28下午06:03:26
	 */
	@RequestMapping("/dealerEdit.do")
	public void dealerEdit(HttpServletResponse response, Model model, String id)
	{
		Dealer dealer = null;
		if (!id.equals(""))
		{
			dealer = this.dealerService.getById(Integer.parseInt(id));
		}
		if (dealer == null)
		{
			dealer = new Dealer();
		}
		
		ResponseUtils.renderJson(response, dealer);
	}
	
	/**
	 * 保存经销商信息
	 * @author jqi.can
	 * @date 2016-1-28下午06:03:41
	 */
	@RequestMapping("/dealerSave.do")
	public void dealerSave(HttpServletResponse response, Dealer dealer)
	{
		String msg = this.dealerService.saveDealer(dealer);
		ResponseUtils.renderText(response, msg);
	}
	
	/**
	 * 进入分配企业页面
	 * @author jqi.can
	 * @date 2016-2-19上午09:45:57
	 */
	@RequestMapping("/distributeCompPage.do")
	public String distributeCompPage(Model model, String id)
	{
		Dealer dealer = this.dealerService.getById(Integer.parseInt(id));
		
		if(dealer == null)
		{
			return null;
		}
		model.addAttribute("dealer", dealer);
		return "platform/dealer/companyList";
	}
	
	/**
	 * 企业列表
	 * @author jqi.can
	 * @date 2016-2-19上午09:53:53
	 */
	@RequestMapping("/notInCompanies.do")
	public void companyList(HttpServletResponse response, String searchItems, String dealerId)
	{
		ResponseUtils.renderJsons(response, companyService.notInCompanies(searchItems, dealerId));
	}
	
	/**
	 * 分配企业
	 * @author jqi.can
	 * @date 2016-2-19上午10:18:44
	 */
	@RequestMapping("/distributeCompany.do")
	public void distributeCompany(HttpServletResponse response, HttpServletRequest request, String dealerId, String companies)
	{
		String message = "";
		try 
		{
			this.companyDealerService.distributeCompany(dealerId, companies.split(","));
			message = "分配企业成功！";
		} 
		catch (Exception e) 
		{
			message = "分配企业失败！";
			e.printStackTrace();
		}
		ResponseUtils.renderText(response, message);
	}

	/**
	 * 已分配企业列表
	 * @author jqi.can
	 * @date 2016-3-2上午11:21:55
	 */
	@RequestMapping("/hasCompaniesPage.do")
	public String hasCompaniesPage(Model model, String dealerId)
	{
		model.addAttribute("dealerId", dealerId);
		return "platform/dealer/companiesHas";
	}
	
	/**
	 * 已分配企业列表
	 * @author jqi.can
	 * @date 2016-3-2下午01:53:35
	 */
	@RequestMapping("/hasCompanies.do")
	public void hasCompanies(HttpServletRequest request, HttpServletResponse response, String dealerId, String searchItems)
	{
		ResponseUtils.renderJsons(response, companyService.findHasCompanies(searchItems, dealerId));
	}
	
	/**
	 * 取消企业分配
	 * @author jqi.can
	 * @date 2016-3-2下午02:02:27
	 */
	@RequestMapping("/cancelDistribute.do")
	public void cancelDistribute(HttpServletResponse response, String dealerId, String companies)
	{
		String message = "";
		try 
		{
			this.companyDealerService.cancelDistribute(dealerId, companies.split(","));
			message = "取消成功！";
		} 
		catch (Exception e) 
		{
			message = "取消失败！";
			e.printStackTrace();
		}
		ResponseUtils.renderText(response, message);
	}
	
}
