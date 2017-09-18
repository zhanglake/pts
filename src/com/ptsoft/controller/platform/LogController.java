package com.ptsoft.controller.platform;

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
import com.ptsoft.pts.system.model.vo.SysLog;
import com.ptsoft.pts.system.service.SysLogService;

@Controller("PlatformLogController")
@RequestMapping("/platform/sysLog/*")
public class LogController 
{
	
	@Autowired
	private SysLogService logService;

	/**
	 * 操作日志
	 * @author jqi.can
	 * @date 2016-2-1下午03:15:19
	 */
	@RequestMapping("/operateLog.do")
	public String operateLog(Model model)
	{
		model.addAttribute("sActionType", PisConstants.LogActionType.ToOptionString());
		return "platform/log/operateLog";
	}
	
	/**
	 * 操作日志列表
	 * @author jqi.can
	 * @date 2016-2-1下午03:45:14
	 */
	@RequestMapping("/operateList.do")
	public void operateList(HttpServletRequest request, HttpServletResponse response, Pageable pageable)
	{
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		
		map.put("compId", -1);
		map.put("logType", 1);
		map.put("fmtm", request.getParameter("fmtm"));
		map.put("totm", request.getParameter("totm"));
		map.put("actionType", request.getParameter("actionType"));
		
		int count = this.logService.getCountByTp(map);
		List<SysLog> list = this.logService.findByLogTp(map, pageable);
		ResponseUtils.renderJson(response, new Page<SysLog>(list, count));
	}
	
	/**
	 * 异常日志
	 * @author jqi.can
	 * @date 2016-2-1下午03:15:30
	 */
	@RequestMapping("/exceptionLog.do")
	public String exceptionLog(Model model)
	{
		model.addAttribute("sActionType", PisConstants.LogActionType.ToOptionString());
		return "platform/log/exceptionLog";
	}
	
	/**
	 * 异常日志列表
	 * @author jqi.can
	 * @date 2016-2-1下午03:48:46
	 */
	@RequestMapping("/exceptionList.do")
	public void exceptionList(HttpServletRequest request, HttpServletResponse response, Pageable pageable)
	{
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		
		map.put("compId", -1);
		map.put("logType", 2);
		map.put("fmtm", request.getParameter("fmtm"));
		map.put("totm", request.getParameter("totm"));
		map.put("actionType", request.getParameter("actionType"));
		
		int count = this.logService.getCountByTp(map);
		List<SysLog> list = this.logService.findByLogTp(map, pageable);
		ResponseUtils.renderJson(response, new Page<SysLog>(list, count));
	}
	
	/**
	 * excel表
	 * @author jqi.can
	 * 2016-7-20下午01:57:32
	 */
	@RequestMapping("/listXls.do")
	public void listXls(HttpServletRequest request, HttpServletResponse response)
	{
		String fmtm = request.getParameter("fmtm");
		String totm = request.getParameter("totm");
		String actionType = request.getParameter("actionType");
		int logType = Integer.parseInt(request.getParameter("logType"));
		
		List<SysLog> list = this.logService.getListXls(-1, actionType, logType, totm, fmtm);
		ResponseUtils.renderJsons(response, list);
	}				
	
}
