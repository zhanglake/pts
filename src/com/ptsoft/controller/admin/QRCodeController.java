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
import com.ptsoft.pts.PisUtils;
import com.ptsoft.pts.account.model.vo.SysUser;
import com.ptsoft.pts.business.service.QRCodeService;

@Controller("AdminQRCodeController")
@RequestMapping("/admin/qrCode/*")
public class QRCodeController 
{
	@Autowired
	private QRCodeService qrCodeService;
	
	/**
	 * 进入二维码查询页面
	 */
	@RequestMapping("/qrCodePage.do")
	public String qrCodePage(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "admin/qrCode/qrCode";
	}
	
	/**
	 * 获取二维码统计
	 * @author jqi.can
	 * @date 2016-4-1下午11:19:52
	 */
	@RequestMapping("/qrCodeCount.do")
	public void qrCodeCount(HttpServletRequest request, HttpServletResponse response, Pageable pageable, String searchParam)
	{
		int companyId = PisUtils.getCurrentUser(request).getCompany_id();
		int count = this.qrCodeService.getCount(companyId, searchParam);
		List<HashMap<String, Object>> list = this.qrCodeService.getList(companyId, searchParam, pageable);
		
		ResponseUtils.renderJson(response, new Page<HashMap<String, Object>>(list, count));
	}
	
	/**
	 * 
	 * @author jqi.can
	 * 2016-7-20上午10:57:56
	 */
	@RequestMapping("/qrCodeCountXls.do")
	public void qrCodeCountXls(HttpServletRequest request, HttpServletResponse response)
	{
		String searchParam = request.getParameter("searchParam");
		int companyId = PisUtils.getCurrentUser(request).getCompany_id();
		List<HashMap<String, Object>> list = qrCodeService.getXls(searchParam, companyId);
		ResponseUtils.renderJsons(response, list);
	}
	
	/**
	 * 回收二维码
	 * @author jqi.can
	 * @date 2016-4-1下午01:19:42
	 */
	@RequestMapping("/recoveryQrcode.do")
	public void recoveryQrcode(HttpServletRequest request, HttpServletResponse response)
	{
		SysUser user = PisUtils.getCurrentUser(request);
		int supplierId = Integer.parseInt(request.getParameter("supplierId"));
		int pkgLevel = Integer.parseInt(request.getParameter("pkgLevel"));
		int number = Integer.parseInt(request.getParameter("number"));
		
		ResponseUtils.renderText(response, this.qrCodeService.recoveryQrcode(user, supplierId, pkgLevel, number));
	}
	
	/**
	 * 获取剩余数量
	 * @author jqi.can
	 * @date 2016-5-5下午03:28:12
	 */
	@RequestMapping("/getRemaining.do")
	public void getRemaining(HttpServletRequest request, HttpServletResponse response)
	{
		int supplierId = Integer.parseInt(request.getParameter("supplierId"));
		int pkgLevel = Integer.parseInt(request.getParameter("pkgLevel"));
		
		ResponseUtils.renderJson(response, this.qrCodeService.getRemaining(supplierId, pkgLevel));
	}
}
