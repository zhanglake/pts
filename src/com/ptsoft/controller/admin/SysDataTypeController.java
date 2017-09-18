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
import com.ptsoft.pts.account.model.vo.SysUser;
import com.ptsoft.pts.system.model.vo.SysDataType;
import com.ptsoft.pts.system.service.SysDataTypeService;

@Controller
@RequestMapping("/admin/sysDataType/*")
public class SysDataTypeController
{
	@Autowired
	private SysDataTypeService dataTypeService;

	/**
	 * 进入字典表界面
	 * @param type 字典数据类型
	 * */
	@RequestMapping("/toPage.do")
	public String toPage(HttpServletRequest request, HttpServletResponse response, Model model, int type)
	{
		model.addAttribute("typeID", type);
		model.addAttribute("typeName", PisConstants.DataType.FromKey(type).getText());
		model.addAttribute("statusSelect", PisConstants.Available.ToOptionString());
		
		return "admin/dataType/dataTypeList";
	}
	
	/**
	 * 字典数据列表 带分页
	 * */
	@RequestMapping("/dataList.do")
	public void dataList(HttpServletRequest request, HttpServletResponse response, int type, Pageable pageable)
	{	
		SysUser user = (SysUser) request.getSession().getAttribute("C_USR");
		int count = this.dataTypeService.getTypeCount(type,user.getCompany_id());
		List<Object> list = this.dataTypeService.findByTypeAndCompID(type, user.getCompany_id(), pageable);
		ResponseUtils.renderJson(response, new Page<Object>(list, count));
	}
	
	/**
	 * 字典数据列表 无分页
	 * @author jqi.can
	 * 2016-8-17下午04:23:31
	 */
	@RequestMapping("/dataListNoPage.do")
	public void dataList(HttpServletRequest request, HttpServletResponse response)
	{
		int companyId = PisUtils.getCurrentUser(request).getCompany_id();
		int type = Integer.parseInt(request.getParameter("type"));
		List<Object> list = this.dataTypeService.findByTypeNoPage(type, companyId);
		ResponseUtils.renderJsons(response, list);
	}

	/**
	 * 显示编辑页面
	 */
	@RequestMapping("/editData.do")
	public void editData(HttpServletRequest request, HttpServletResponse response, String dicCode)
	{
		SysDataType dataType = this.dataTypeService.getById(dicCode);
		ResponseUtils.renderJson(response, dataType);
	}
	
	/** 保存公告 */
	@RequestMapping("/saveData.do")
	public void saveData(HttpServletRequest request, HttpServletResponse response, SysDataType dataType)
	{
		SysUser user = (SysUser) request.getSession().getAttribute("C_USR");
		String message = this.dataTypeService.saveDataType(dataType, user.getCompany_id());
		ResponseUtils.renderText(response, message);
	}

	/**
	 * 进入二维码尺寸页面
	 * @author jqi.can
	 * @date 2016-2-25下午03:03:54
	 */
	@RequestMapping("/toQRPage.do")
	public String toQRPage(HttpServletRequest request, HttpServletResponse response, Model model, int type)
	{
		model.addAttribute("typeID", type);
		model.addAttribute("typeName", PisConstants.DataType.FromKey(type).getText());
		model.addAttribute("statusSelect", PisConstants.Available.ToOptionString());
		return "admin/dataType/qrcode";
	}
}
