package com.ptsoft.controller.admin;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ptsoft.common.util.FileUtil;
import com.ptsoft.common.util.Page;
import com.ptsoft.common.util.Pageable;
import com.ptsoft.common.util.ResponseUtils;
import com.ptsoft.common.util.SysConfig;
import com.ptsoft.pts.PisConstants;
import com.ptsoft.pts.PisUtils;
import com.ptsoft.pts.account.model.vo.SysUser;
import com.ptsoft.pts.basic.model.vo.Dealer;
import com.ptsoft.pts.basic.service.DealerService;
import com.ptsoft.pts.business.service.SaleOrderService;
import com.ptsoft.pts.system.model.vo.SysArea;
import com.ptsoft.pts.system.service.AreaService;

@Controller("AdminDealerController")
@RequestMapping("/admin/dealer/*")
public class DealerController {
	
	@Autowired
	private DealerService dealerService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private SaleOrderService saleOrderService;
	
	/**
	 * 进入一级经销商管理页面
	 * @author zumin.yang
	 * @date 2016-1-28
	 * @return
	 */
	@RequestMapping("/dealerPage.do")
	public String dealerPage(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "admin/dealer/dealer";
	}
	
	/**
	 * 查询企业一级经销商
	 * @author zumin.yang
	 */
	@RequestMapping("/dealerList.do")
	public void dealerList(HttpServletRequest request, HttpServletResponse response, String searchParam,Pageable pageable)
	{
		SysUser user = PisUtils.getCurrentUser(request);
		int count = this.dealerService.getDealerCount(searchParam, user.getCompany_id());
		List<Object> list = this.dealerService.findByCompId(searchParam, user.getCompany_id(), pageable);
		ResponseUtils.renderJson(response, new Page<Object>(list, count));	
	}
	
	/**
	 * 经销商编辑页
	 * @author zumin.yang
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
		
		return "admin/dealer/dealerEditor";
	}
	
	/**
	 * 经销商信息
	 * @author zumin.yang
	 * @date 2016-1-28下午06:03:26
	 */
	@RequestMapping("/dealerEdit.do")
	public void dealerEdit(HttpServletRequest request, HttpServletResponse response, Model model, String id)
	{
		int companyId = PisUtils.getCurrentUser(request).getCompany_id();
		Dealer dealer = null;
		if (!id.equals(""))
		{
			dealer = this.dealerService.getCompanyIdAndId(Integer.parseInt(id), companyId);
		}
		if (dealer == null)
		{
			dealer = new Dealer();
		}
		
		ResponseUtils.renderJson(response, dealer);
	}
	
	/**
	 * 保存经销商信息
	 * @author zumin.yang
	 * @date 2016-1-28下午06:03:41
	 */
	@RequestMapping("/dealerSave.do")
	public void dealerSave(HttpServletRequest request,HttpServletResponse response, Dealer dealer)
	{
		Integer companyId = PisUtils.getCurrentUser(request).getCompany_id();
		String msg = this.dealerService.saveComanyDealer(companyId, dealer);
		ResponseUtils.renderText(response, msg);
	}
	
	/**
	 * 子经销商
	 * @author jqi.can
	 * 2016-6-7下午02:43:23
	 */
	@RequestMapping("/sonDealers.do")
	public void sonDealers(HttpServletRequest request, HttpServletResponse response)
	{
		String code = request.getParameter("code");
		ResponseUtils.renderJsons(response, this.saleOrderService.getSonDealers(code));
	}
	
	/**
	 * 下载经销商模板文件
	 * @author jqi.can
	 * 2016-6-8下午03:09:34
	 */
	@RequestMapping("/downloadTpl.do")
	public void downloadTpl(HttpServletRequest request, HttpServletResponse response)
	{
		String url = SysConfig.get_pts_url() + "templates/tpl_dealer.xlsx";
		ResponseUtils.renderText(response, url);
	}
	
	/**
	 * 上传文件
	 * @author jqi.can
	 * 2016-6-8下午02:55:12
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
	 * 2016-6-8下午03:54:52
	 */
	@RequestMapping("/doImport.do")
	public void doImport(HttpServletRequest request, HttpServletResponse response)
	{
		String file = request.getParameter("file");
		int companyId = PisUtils.getCurrentUser(request).getCompany_id();
		String msg = this.dealerService.importData(file, companyId);
		ResponseUtils.renderText(response, msg);
	}
}
