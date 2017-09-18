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
import com.ptsoft.pts.PisConstants;
import com.ptsoft.pts.PisUtils;
import com.ptsoft.pts.basic.service.TraceService;

@Controller("AdminTraceController")
@RequestMapping("/admin/trace/*")
public class TraceController 
{
	
	@Autowired
	private TraceService traceService;
	
	/**
	 * 进入入库记录页面
	 * @author jqi.can
	 * @date 2016-3-31下午02:11:03
	 */
	@RequestMapping("/inQuery.do")
	public String toInQuery(Model model)
	{
		model.addAttribute("actionType", PisConstants.ActionType.In.getKey());
		return "admin/trace/in";
	}
	
	/**
	 * 进入出库记录页面
	 * @author jqi.can
	 * @date 2016-3-31下午02:11:43
	 */
	@RequestMapping("/outQuery.do")
	public String toOutQuery(Model model)
	{
		model.addAttribute("actionType", PisConstants.ActionType.Out.getKey());
		return "admin/trace/out";
	}
	
	/**
	 * 出入库列表
	 * actionType 4-入库 5-出库
	 * @author jqi.can
	 * @date 2016-3-29下午02:10:02
	 */
	@RequestMapping("/inOutList.do")
	public void inOutList(HttpServletRequest request, HttpServletResponse response, String frmTm, String toTm, String actionType, String searchParam, Pageable pageable)
	{
		int compId = PisUtils.getCurrentUser(request).getCompany_id();
		int count = this.traceService.findRecordCount(0, compId, frmTm, toTm, actionType, searchParam);
		List<Object> list = this.traceService.findRecordByAction(0, compId, frmTm, toTm, actionType, searchParam, pageable);
		ResponseUtils.renderJson(response, new Page<Object>(list, count));
	}
	
	/**
	 * excel表
	 * @author jqi.can
	 * 2016-7-20下午02:29:41
	 */
	@RequestMapping("/inOutListXls.do")
	public void inOutListXls(HttpServletRequest request, HttpServletResponse response)
	{
		int compId = PisUtils.getCurrentUser(request).getCompany_id();
		String frmTm = request.getParameter("frmTm");
		String toTm = request.getParameter("toTm");
		String actionType = request.getParameter("actionType");
		String searchParam = request.getParameter("searchParam");
		
		List<Object> list = this.traceService.getActionRecordXls(0, compId, frmTm, toTm, actionType, searchParam);
		
		ResponseUtils.renderJsons(response, list);
	}
	
	/**
	 * 追踪记录
	 * @author jqi.can
	 * @date 2016-3-30下午02:13:10
	 */
	@RequestMapping("/following.do")
	public void following(HttpServletRequest request, HttpServletResponse response, String qrCode)
	{
		ResponseUtils.renderJsons(response, traceService.getRecordByQrcode(qrCode));
	}
	
	/**
	 * 获取出库信息
	 * @author jqi.can
	 * 2016-8-17下午05:03:38
	 */
	@RequestMapping("/getOutInfo.do")
	public void getOutInfo(HttpServletRequest request, HttpServletResponse response)
	{
		String qrCode = request.getParameter("qrCode");
		ResponseUtils.renderJson(response, traceService.getOutInfo(qrCode));
	}
	
}
