package com.ptsoft.controller.platform;


import java.util.ArrayList;
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
import com.ptsoft.pts.account.model.vo.SysRole;
import com.ptsoft.pts.account.model.vo.SysUser;
import com.ptsoft.pts.account.service.PermissionService;
import com.ptsoft.pts.account.service.RoleService;
import com.ptsoft.pts.account.service.UserService;
import com.ptsoft.pts.basic.model.vo.Company;
import com.ptsoft.pts.basic.service.CompanyService;
import com.ptsoft.pts.system.model.vo.MolRoleTree;

@Controller("PlatformAccountController")
@RequestMapping("/platform/account/*")
public class AccountController
{
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;
	@Autowired
	private CompanyService companyService; 
	@Autowired
	private PermissionService permissionService;

	/** 进入角色页面 */
	@RequestMapping("/rolePage.do")
	public String rolePage(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		model.addAttribute("sysTpSelect", PisConstants.SystemType.ToOptionString());
		model.addAttribute("statusSelect", PisConstants.Available.ToOptionString());

		return "platform/userInfo/role";
	}

	/** 角色列表 */
	@RequestMapping("/roleList.do")
	public void roleList(HttpServletRequest request, HttpServletResponse response,Pageable pageable)
	{
		int count = this.roleService.getRoleCount();
		List<Object> list = this.roleService.findRoleAll(pageable);
		ResponseUtils.renderJson(response, new Page<Object>(list, count));
	}

	/**
	 * 编辑角色
	 */
	@RequestMapping("/roleEdit.do")
	public void roleEdit(HttpServletRequest request, HttpServletResponse response)
	{
		String rlId = request.getParameter("rlId");
		ResponseUtils.renderJson(response, this.roleService.getById(Integer.parseInt(rlId)));
	}

	/** 保存角色 */
	@RequestMapping("/roleSave.do")
	public void roleSave(HttpServletRequest request, HttpServletResponse response, SysRole role)
	{
		String message = null;
		try
		{
			roleService.save(role);

			message = "角色保存成功！";
		}
		catch (Exception e)
		{
			e.printStackTrace();
			message = "角色保存失败！";
		}

		ResponseUtils.renderText(response, message);
	}

	/**
	 * 进入权限管理页面
	 */
	@RequestMapping("/permissionPage.do")
	public String permissionPage(HttpServletResponse response, HttpServletRequest request, Model model, String rlId)
	{
		model.addAttribute("roleId", rlId);
		return "platform/userInfo/permission";
	}

	/**
	 * 角色的权限管理
	 */
	@RequestMapping("/getPermission.do")
	public void getPermission(HttpServletResponse response, String rlId)
	{
		List<MolRoleTree> list = permissionService.getRoleActionFunctionMap(rlId);
		ResponseUtils.renderJsons(response, list);
	}

	/**
	 * 角色权限管理保存
	 */
	@RequestMapping("/savePermission.do")
	public void savePermission(HttpServletResponse response, String roleId, String permission)
	{
		String[] mpids = permission.split(",");
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < mpids.length; i++)
		{
			if (!mpids[i].equals(""))
			{
				list.add(mpids[i]);
			}
		}
		permissionService.savePermission(roleId, list);
	}

	/** 进入用户页面 */
	@RequestMapping("/userPage.do")
	public String userPage(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		List<Company> compList = companyService.findAll();
		model.addAttribute("compSelect", PisUtils.list2Option(compList, "getId", "getName", "", false));
		model.addAttribute("statusSelect", PisConstants.Available.ToOptionString());
		return "platform/userInfo/user";
	}
	
	/**
	 * 用户列表
	 */
	@RequestMapping("/userList.do")
	public void userList(HttpServletResponse response, HttpServletRequest request, Pageable pageable, String compId, String rlId)
	{
		int count = this.userService.getCountByCompId(compId, null, rlId, null);
		List<Object> list = this.userService.findByCompId(compId, rlId, null, "", pageable);
		ResponseUtils.renderJson(response, new Page<Object>(list, count));
	}
	
	/**
	 * 平台获取终端用户 角色为普通经销商或普通用户 通过APP注册上来的所有用户
	 * @author jqi.can
	 * @date 2016-4-26下午09:35:18
	 */
	@RequestMapping("/endUserList.do")
	public void endUserList(HttpServletRequest request, HttpServletResponse response, Pageable pageable)
	{
		String searchParam = request.getParameter("searchParam");
		String rlId = request.getParameter("rlId");
		int count = this.userService.getEndUserCount(null, searchParam, rlId);
		List<Object> list = this.userService.findEndUser(null, searchParam, rlId, pageable);
		ResponseUtils.renderJson(response, new Page<Object>(list, count));
	}

	/**
	 * 显示编辑页面
	 */
	@RequestMapping("/userEdit.do")
	public void userEdit(HttpServletResponse response, HttpServletRequest request)
	{
		String usrId = request.getParameter("usrId");
		SysUser user = this.userService.getById(Integer.parseInt(usrId));
		ResponseUtils.renderJson(response, user);
	}

	/**
	 * 保存用户
	 * 
	 * @param response
	 * @param request
	 */
	@RequestMapping("/userSave.do")
	public void userSave(HttpServletResponse response, HttpServletRequest request, SysUser user, int rlId)
	{
		String msg = "";
		msg = this.userService.saveOrUpdate(user, rlId);
		ResponseUtils.renderText(response, msg);
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
	
	/**
	 * 进入终端用户页面
	 * @author jqi.can
	 * @date 2016-3-21下午08:29:20
	 */
	@RequestMapping("/endUserPage.do")
	public String endUserPage()
	{
		return "platform/userInfo/endUser";
	}
}
