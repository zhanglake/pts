package com.ptsoft.controller.admin;

import java.util.HashMap;
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
import com.ptsoft.pts.system.model.vo.SysLog;
import com.ptsoft.pts.system.service.SysLogService;

@Controller("AdminLogController")
@RequestMapping("/admin/log/*")
public class LogController 
{
	@Autowired
	private SysLogService logService;
	
	/** 进入日志页面 */
	@RequestMapping("/logPage.do")
	public String logPage(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		model.addAttribute("sActionType", PisConstants.LogActionType.ToOptionString());
		return "admin/log/log";
	}
	
	/**
	 * 业务日志
	 * @author jqi.can
	 * @date 2016-5-4下午02:49:05
	 */
	@RequestMapping("/businessList.do")
	public void business(HttpServletRequest request, HttpServletResponse response, Pageable pageable)
	{
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		int compId = PisUtils.getCurrentUser(request).getCompany_id();
		
		map.put("compId", compId);
		map.put("logType", 3);
		map.put("fmtm", request.getParameter("fmtm"));
		map.put("totm", request.getParameter("totm"));
		map.put("actionType", request.getParameter("actionType"));
		
		int count = this.logService.getCountByTp(map);
		List<SysLog> list = this.logService.findByLogTp(map, pageable);
		ResponseUtils.renderJson(response, new Page<SysLog>(list, count));
	}
	
	/**
	 * 日志导出excel
	 * @author jqi.can
	 * 2016-7-20下午01:34:31
	 */
	@RequestMapping("/businessListXls.do")
	public void businessListXls(HttpServletRequest request, HttpServletResponse response)
	{
		String actionType = request.getParameter("actionType");
		String totm = request.getParameter("totm");
		String fmtm = request.getParameter("fmtm");
		int companyId = PisUtils.getCurrentUser(request).getCompany_id();
		
		List<SysLog> list = this.logService.getListXls(companyId, actionType, 3, totm, fmtm);
		
		ResponseUtils.renderJsons(response, list);
	}
}
