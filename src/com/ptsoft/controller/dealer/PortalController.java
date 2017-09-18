package com.ptsoft.controller.dealer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ptsoft.pts.PisUtils;
import com.ptsoft.pts.account.service.UserService;
import com.ptsoft.pts.business.service.ScanRecordService;

@Controller("DealerPortalController")
@RequestMapping("/dealer/portal/*")
public class PortalController
{
	@Autowired
	private ScanRecordService scanService;
	@Autowired
	private UserService userService;
	
	@RequestMapping("/myPortal.do")
	public String myPortal(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		int dealerId = PisUtils.getCurrentUser(request).getDealer_id();
		/**收货记录数*/
		int recordCount = this.scanService.getRecieveCount(dealerId);
		/**用户数*/
		int userCount = this.userService.getCountByDealerId(dealerId);
		/**积分*/
		int points = this.userService.getPoints(dealerId);
		
		model.addAttribute("recordCount", recordCount);
		model.addAttribute("userCount", userCount);
		model.addAttribute("point", points);
		
		return "dealer/portal/myPortal";
	}
	
}
