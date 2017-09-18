package com.ptsoft.controller.supplier;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ptsoft.common.util.Page;
import com.ptsoft.common.util.Pageable;
import com.ptsoft.common.util.ResponseUtils;
import com.ptsoft.pts.PisUtils;
import com.ptsoft.pts.account.model.vo.SysUser;
import com.ptsoft.pts.basic.service.TraceService;

@Controller("SupplierTraceController")
@RequestMapping("/supplier/trace/*")
public class TraceController {
	@Autowired
	private TraceService traceService;
	
	@RequestMapping(value="packagePage.do", method=RequestMethod.GET)
	public String toPackagePage(HttpServletRequest request, HttpServletResponse response, Model model){
		return "supplier/trace/package";
	}
	
	@RequestMapping(value="packageList.do", method=RequestMethod.GET)
	public void findPackageList(HttpServletRequest request, HttpServletResponse response, Pageable pageable, String searchParam, String fmtm, String totm)
	{
		SysUser user = PisUtils.getCurrentUser(request);
		int count = this.traceService.findPkgCount(user, searchParam, fmtm, totm);
		List<Object> list = traceService.findPackageList(user, searchParam, fmtm, totm, pageable);
		ResponseUtils.renderJson(response, new Page<Object>(list, count));
	}
	
	@RequestMapping("/packageListXls.do")
	public void packageListXls(HttpServletRequest request, HttpServletResponse response)
	{
		SysUser user = PisUtils.getCurrentUser(request);
		String frmTm = request.getParameter("frmTm");
		String toTm = request.getParameter("toTm");
		String searchParam = request.getParameter("searchParam");
		
		List<Object> list = traceService.packageListXls(user, searchParam, frmTm, toTm);
		ResponseUtils.renderJsons(response, list);
	}
}
