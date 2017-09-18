package com.ptsoft.controller.supplier;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ptsoft.common.util.ExportUtil;
import com.ptsoft.common.util.Page;
import com.ptsoft.common.util.Pageable;
import com.ptsoft.common.util.ResponseUtils;
import com.ptsoft.pts.PisConstants;
import com.ptsoft.pts.PisUtils;
import com.ptsoft.pts.account.model.vo.SysUser;
import com.ptsoft.pts.account.service.UserPdaService;
import com.ptsoft.pts.account.service.UserService;
import com.ptsoft.pts.system.service.SysDataTypeService;

@Controller("SupplierAccountController")
@RequestMapping("/supplier/account/*")
public class AccountController
{
	@Autowired
	private UserService userService;
	@Autowired
	private SysDataTypeService dataTypeService;
	@Autowired
	private UserPdaService userPdaService;
	
	/**
	 * 进入包装用户页面
	 * @author jqi.can
	 * @date 2016-3-31下午04:20:53
	 */
	@RequestMapping("/pkgUser.do")
	public String pkgUser(Model model)
	{
		model.addAttribute("statusSelect", PisConstants.Available.ToOptionString());
		return "supplier/userInfo/pkgUser";
	}
	
	/**
	 * 包装用户列表
	 * @author jqi.can
	 * @date 2016-3-31下午04:21:37
	 */
	@RequestMapping("/pkgUserList.do")
	public void pkgUserList(HttpServletRequest request, HttpServletResponse response, String searchParam,Pageable pageable)
	{
		int supplierId = PisUtils.getCurrentUser(request).getSupplier_id();
		int count = this.userService.getPkgUserCountBySearchParam(supplierId, PisConstants.SystemType.PDA.getKey(), searchParam);
		List<SysUser> list = this.userService.findBySupplierId(supplierId, PisConstants.SystemType.PDA.getKey(), searchParam, pageable);
		ResponseUtils.renderJson(response, new Page<SysUser>(list, count));
	}
	
	/**
	 * 导出包装用户
	 * @author jqi.can
	 * 2016-7-20下午03:44:38
	 */
	@RequestMapping("/userExport.do")
	public void userExport(HttpServletRequest request, HttpServletResponse response)
	{
		
		HashMap<String, String> result = new HashMap<String, String>();
		String url = "";
		
		String xlsTitle = request.getParameter("title");
		String searchParam = request.getParameter("searchParam");
		int supplierId = PisUtils.getCurrentUser(request).getSupplier_id();
		
		try 
		{
			ArrayList<Object> list = this.userService.pkgUserExport(supplierId, PisConstants.SystemType.PDA.getKey(), searchParam);
			
			LinkedHashMap<String,String> title = new LinkedHashMap<String, String>();
			title.put("LGNNM", "登录名");
			title.put("USRNM", "姓名");
			title.put("RLNM", "角色");
			
			url = ExportUtil.ExportToExcel(title, list, xlsTitle);
			
			result.put("message", "1");
			result.put("url", url);
		}
		catch (Exception e) 
		{
			result.put("message", "0");
			result.put("url", url);
			System.out.println(e.toString());
		}
		ResponseUtils.renderJson(response, result);
	}
	
	/**
	 * 加载包装用户权限
	 * @author jqi.can
	 * @date 2016-4-1上午09:59:52
	 */
	@RequestMapping("/loadPkgActions.do")
	public void pkgActions(HttpServletResponse response)
	{
		ResponseUtils.renderJsons(response, this.dataTypeService.findByType(PisConstants.DataType.PkgAction.getKey()));
	}
	
	/**
	 * 包装用户保存
	 * @author jqi.can
	 * @date 2016-4-1下午10:06:07
	 */
	@RequestMapping("/pkgUserSave.do")
	public void pdaUserSave(HttpServletResponse response, HttpServletRequest request, SysUser user, int rlId)
	{
		String msg = "";
		msg = this.userService.savePdaUser(user, rlId);
		ResponseUtils.renderText(response, msg);
	}
	
	/**
	 * 显示编辑页面
	 */
	@RequestMapping("/userEdit.do")
	public void userEdit(HttpServletResponse response, HttpServletRequest request)
	{
		String usrId = request.getParameter("usrId");
		SysUser user = this.userService.getById(Integer.parseInt(usrId));
		List<String> actions = this.userPdaService.getByUserId(user.getUsrId());
		user.setActions(actions);
		ResponseUtils.renderJson(response, user);
	}

	/**
	 * 编辑个人信息
	 * 
	 * @param response
	 * @param request
	 */
	@RequestMapping("/profilelEdit.do")
	public String profilelEdit(HttpServletResponse response, HttpServletRequest request, Model model)
	{
		SysUser tUser = PisUtils.getCurrentUser(request);
		SysUser user = this.userService.getById(tUser.getUsrId());
		model.addAttribute("info", user);
		
		return "portal/myProfilel";
	}
	
	/**
	 * 保存个人信息
	 * */
	@RequestMapping("/profilelSave.do")
	public void profilelSave(HttpServletResponse response, SysUser user)
	{
		ResponseUtils.renderText(response, this.userService.profilelSave(user));
	}
	
	/**
	 * 保存个人密码
	 * */
	@RequestMapping("/passwordSave.do")
	public void passwordSave(HttpServletResponse response, SysUser user)
	{
		ResponseUtils.renderText(response, this.userService.passwordSave(user));
	}
}
