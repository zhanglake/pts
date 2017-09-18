package com.ptsoft.controller.platform;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ptsoft.common.util.ResponseUtils;
import com.ptsoft.pts.PisConstants;
import com.ptsoft.pts.account.service.UserService;
import com.ptsoft.pts.basic.service.AppDeviceService;
import com.ptsoft.pts.basic.service.DealerService;
import com.ptsoft.pts.business.service.ScanRecordService;

@Controller("PlatformPortalController")
@RequestMapping("/platform/portal/*")
public class PortalController
{
	@Autowired
	private ScanRecordService scanService;
	@Autowired
	private UserService userService;
	@Autowired
	private DealerService dealerService;
	@Autowired
	private AppDeviceService appDeviceService;
	
	@RequestMapping("/myPortal.do")
	public String myPortal(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		/**扫描次数*/
		int scanCount = this.scanService.getCountByComp(null, PisConstants.ActionType.Out.getKey());
		/**企业用户数*/
		int cmpCount = this.userService.getCountByType(2);
		/**装机量*/
		int appCount = this.appDeviceService.getCount();		
		/**经销商数*/
		int dealCount = this.dealerService.getDealerCount(null);
		
		model.addAttribute("scanCount", scanCount);
		model.addAttribute("dealCount", dealCount);
		model.addAttribute("cmpCount", cmpCount);
		model.addAttribute("appCount", appCount);
		
		return "platform/portal/myPortal";
	}
	
	/**
	 * 获取经销商百分比
	 * @author jqi.can
	 * @date 2016-5-10下午02:57:48
	 */
	@RequestMapping("/dearlerPercent.do")
	public void dearlerPercent(HttpServletRequest request, HttpServletResponse response)
	{
		List<Object> list = this.dealerService.dealerPercent(-1);
		
		ResponseUtils.renderJsons(response, list);
	}
	
}
