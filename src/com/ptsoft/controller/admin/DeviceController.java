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
import com.ptsoft.pts.basic.model.vo.PdaDevice;
import com.ptsoft.pts.basic.model.vo.Stock;
import com.ptsoft.pts.basic.service.AppDeviceService;
import com.ptsoft.pts.basic.service.DeviceService;
import com.ptsoft.pts.basic.service.StockService;

@Controller("AdminDeviceController")
@RequestMapping("/admin/device/*")
public class DeviceController {
	@Autowired
	private DeviceService deviceService;
	
	@Autowired
	private AppDeviceService appService;
	
	@Autowired
	private StockService stockService;
	
	/** 进入PDA设备列表页面 */
	@RequestMapping("/pdaDevicePage.do")
	public String pdaDevicePage(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		
		return "admin/device/pdaDevice";
	}
	
	/**
	 * 查询PDA设备列表数据
	 * @author zumin.yang
	 * @date 2016-2-15
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/pdaList.do")
	public void pdaDeviceList(HttpServletRequest request, HttpServletResponse response, Pageable pageable)
	{
		SysUser user = PisUtils.getCurrentUser(request);
		int count = this.deviceService.getPdaDeviceListCount(user.getCompany_id());
		List<Object> list = this.deviceService.findByCompanyIdAndPage(user.getCompany_id(),pageable);
		ResponseUtils.renderJson(response, new Page<Object>(list, count));	
	}
	
	/** 进入PDA设备编辑页面 */
	@RequestMapping("/pdaDeviceEditPage.do")
	public String pdaDeviceEditPage(HttpServletRequest request, HttpServletResponse response, Model model, String id)
	{
		SysUser user = PisUtils.getCurrentUser(request);
		List<Stock> stocks = stockService.findByCompanyId(user.getCompany_id());
		List<PisConstants.Available> availables = Arrays.asList(PisConstants.Available.values());
		model.addAttribute("id", id);
		model.addAttribute("companyId", user.getCompany_id());
		model.addAttribute("stockSelect", PisUtils.list2Option(stocks, "getId", "getName", null, true));
		model.addAttribute("sSts",  PisUtils.list2Option(availables, "getKey", "getText", null, false));
		return "admin/device/pdaDeviceEditor";
	}
	
	/**
	 * PDA设备信息
	 * @author zumin.yang
	 * @date 2016-1-28下午06:03:26
	 */
	@RequestMapping("/pdaEdit.do")
	public void pdaEdit(HttpServletResponse response, Model model, String id)
	{
		PdaDevice pda = null;
		if (!id.equals(""))
		{
			pda = this.deviceService.getById(Integer.parseInt(id));
		}
		if (pda == null)
		{
			pda = new PdaDevice();
		}
		
		ResponseUtils.renderJson(response, pda);
	}
	
	/**
	 * 保存PDA设备信息
	 * @author zumin.yang
	 * @date 2016-2-15下午06:03:41
	 */
	@RequestMapping("/pdaSave.do")
	public void dealerSave(HttpServletRequest request,HttpServletResponse response, PdaDevice pda)
	{
		String msg = this.deviceService.savePDA(pda);
		ResponseUtils.renderText(response, msg);
	}
	
	/** 进入APP设备列表页面 */
	@RequestMapping("/appDevicePage.do")
	public String appDevicePage(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		
		return "admin/device/appDevice";
	}
	
	/**
	 * 查询APP设备列表数据
	 * @author zumin.yang
	 * @date 2016-2-15
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/appDeviceList.do")
	public void appDeviceList(HttpServletRequest request, HttpServletResponse response, Pageable pageable)
	{
		//SysUser user = PisUtils.getCurrentUser(request);
		int count = this.appService.getCount();
		List<Object> list = this.appService.getAllByPage(pageable);
		ResponseUtils.renderJson(response, new Page<Object>(list, count));	
	}
}
