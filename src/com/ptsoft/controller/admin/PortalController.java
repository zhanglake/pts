package com.ptsoft.controller.admin;

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
import com.ptsoft.pts.PisUtils;
import com.ptsoft.pts.basic.service.DealerService;
import com.ptsoft.pts.business.model.vo.ApplyApproval;
import com.ptsoft.pts.business.service.ApplyApprovalService;

@Controller("AdminPortalController")
@RequestMapping("/admin/portal/*")
public class PortalController
{
	@Autowired
	private ApplyApprovalService applyApprovalService;
	@Autowired
	private DealerService dealerService;
	
	@RequestMapping("/myPortal.do")
	public String myPortal(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		int companyId = PisUtils.getCurrentUser(request).getCompany_id();
		/**二维码申请记录数*/
		int applyCount = this.applyApprovalService.getSizeByCompanyId(companyId, null, null, -1);
		/**扫描次数*/
		//int scanCount = this.scanService.getCountByComp(String.valueOf(companyId), PisConstants.ActionType.Out.getKey());
		/**终端用户数*/
		//int endCount = this.userService.getEndUserCount(String.valueOf(companyId), "", "0");
		/**经销商数*/
		int dealCount = this.dealerService.getDealerCount(null);
		/**未处理二维码申请记录数*/
		int newApplyCount = this.applyApprovalService.getSizeByCompanyId(companyId, null, null, 0);
		
		model.addAttribute("applyCount", applyCount);
		/*model.addAttribute("scanCount", scanCount);
		model.addAttribute("endCount", endCount);*/
		model.addAttribute("dealCount", dealCount);
		model.addAttribute("newApplyCount", newApplyCount);
		
		return "admin/portal/myPortal";
	}
	
	/**
	 * 二维码申请记录页面
	 * @author jqi.can
	 * @date 2016-4-12下午05:08:27
	 */
	@RequestMapping("/applyPage.do")
	public String applyPage()
	{
		return "admin/portal/apply";
	}
	
	/**
	 * 二维码申请记录列表
	 * @author jqi.can
	 * @date 2016-4-12下午05:10:16
	 */
	@RequestMapping("/applyList.do")
	public void applyList(HttpServletRequest request, HttpServletResponse response, Pageable pageable, String fmtm, String totm)
	{
		int companyId = PisUtils.getCurrentUser(request).getCompany_id();
		int count = this.applyApprovalService.getSizeByCompanyId(companyId, fmtm, totm, -1);
		List<ApplyApproval> list = this.applyApprovalService.getByParamCompId(companyId, fmtm, totm, pageable);
		
		ResponseUtils.renderJson(response, new Page<ApplyApproval>(list, count));
	}
	
	/**
	 * 二维码记录处理
	 * @author jqi.can
	 * @date 2016-4-12下午05:53:10
	 */
	@RequestMapping("/operateApply.do")
	public void operateApply(HttpServletRequest request, HttpServletResponse response, int id, int status)
	{
		int userId = PisUtils.getCurrentUser(request).getUsrId();
		String msg = this.applyApprovalService.updateApply(userId, id, status);
		ResponseUtils.renderJson(response, msg);
	}
	
	/**
	 * 获取经销商百分比
	 * @author jqi.can
	 * @date 2016-5-10下午02:57:48
	 */
	@RequestMapping("/dearlerPercent.do")
	public void dearlerPercent(HttpServletRequest request, HttpServletResponse response)
	{
		int compId = PisUtils.getCurrentUser(request).getCompany_id();
		List<Object> list = this.dealerService.dealerPercent(compId);
		
		ResponseUtils.renderJsons(response, list);
	}
}
