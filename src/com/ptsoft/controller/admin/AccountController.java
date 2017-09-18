package com.ptsoft.controller.admin;

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
import com.ptsoft.pts.account.model.vo.SysRole;
import com.ptsoft.pts.account.model.vo.SysUser;
import com.ptsoft.pts.account.service.PermissionService;
import com.ptsoft.pts.account.service.RoleService;
import com.ptsoft.pts.account.service.UserPdaService;
import com.ptsoft.pts.account.service.UserService;
import com.ptsoft.pts.basic.model.vo.Dealer;
import com.ptsoft.pts.basic.model.vo.Supplier;
import com.ptsoft.pts.basic.service.DealerService;
import com.ptsoft.pts.basic.service.SupplierService;
import com.ptsoft.pts.system.model.vo.MolRoleTree;
import com.ptsoft.pts.system.service.SysDataTypeService;

@Controller("AdminAccountController")
@RequestMapping("/admin/account/*")
public class AccountController
{
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private DealerService dealerService;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private SysDataTypeService dataTypeService;
	@Autowired
	private UserPdaService userPdaService;

	/** 进入角色页面 */
	@RequestMapping("/rolePage.do")
	public String rolePage(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		model.addAttribute("sysTpSelect", PisConstants.SystemType.ToOptionString());
		model.addAttribute("statusSelect", PisConstants.Available.ToOptionString());

		return "admin/userInfo/role";
	}

	/** 角色列表 */
	@RequestMapping("/roleList.do")
	public void roleList(HttpServletRequest request, HttpServletResponse response)
	{
		List<SysRole> list = roleService.findAll();
		ResponseUtils.renderJsons(response, list);
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
		return "admin/userInfo/permission";
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
		SysUser user = PisUtils.getCurrentUser(request);
		List<SysRole> roleList = roleService.findAll();
		model.addAttribute("roleOption", PisUtils.list2Option(roleList, "getRlId", "getRlNm", "", false));
		model.addAttribute("statusSelect", PisConstants.Available.ToOptionString());
		model.addAttribute("companyId", user.getCompany_id());
		return "admin/userInfo/user";
	}
	
	/** 进入一级经销商页面 */
	@RequestMapping("/topDealerPage.do")
	public String topDealerPage(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		List<SysRole> roleList = roleService.findAll();
		SysUser user = PisUtils.getCurrentUser(request);
		List<Dealer> dealerList = this.dealerService.findByCompanyId(user.getCompany_id());
		model.addAttribute("roleOption", PisUtils.list2Option(roleList, "getRlId", "getRlNm", "", false));
		model.addAttribute("statusSelect", PisConstants.Available.ToOptionString());
		model.addAttribute("companyId", user.getCompany_id());
		model.addAttribute("dealerOption", PisUtils.list2Option(dealerList, "getId", "getDealer_name", "", false));
		
		return "admin/userInfo/topDealer";
	}
	
	/** 进入其他经销商页面 */
	@RequestMapping("/otherDealerPage.do")
	public String otherDealerPage(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "admin/userInfo/otherDealer";
	}
	
	/** 进入生产供应商页面 */
	@RequestMapping("/supplierPage.do")
	public String supplierPage(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		List<SysRole> roleList = roleService.findAll();
		SysUser user = PisUtils.getCurrentUser(request);
		List<Supplier> supplierList = this.supplierService.findByCompanyId(user.getCompany_id());
		model.addAttribute("roleOption", PisUtils.list2Option(roleList, "getRlId", "getRlNm", "", false));
		model.addAttribute("statusSelect", PisConstants.Available.ToOptionString());
		model.addAttribute("companyId", user.getCompany_id());
		model.addAttribute("supplierOption", PisUtils.list2Option(supplierList, "getId", "getSupplier_name", "", false));
		return "admin/userInfo/supplier";
	}

	/**
	 * 根据公司和角色查询用户列表
	 */
	@RequestMapping("/userList.do")
	public void userList(HttpServletResponse response, HttpServletRequest request, int roleId, String searchParam, Pageable pageable)
	{
		SysUser user = PisUtils.getCurrentUser(request);
		int count = this.userService.getCountByCompId(String.valueOf(user.getCompany_id()), null, String.valueOf(roleId), searchParam);
		List<Object> list = this.userService.findByCompId(String.valueOf(user.getCompany_id()), String.valueOf(roleId), null, searchParam, pageable);
		ResponseUtils.renderJson(response, new Page<Object>(list, count));
	}
	
	/**
	 * 根据公司和用户角色类型查询用户列表
	 */
	@RequestMapping("/userTpList.do")
	public void userTpList(HttpServletResponse response, HttpServletRequest request, int sysTp, String searchParam, Pageable pageable)
	{
		SysUser user = PisUtils.getCurrentUser(request);
		int count = this.userService.getCountByCompId(String.valueOf(user.getCompany_id()), String.valueOf(sysTp), null, searchParam);
		List<Object> list = this.userService.findByCompId(String.valueOf(user.getCompany_id()), null, String.valueOf(sysTp), searchParam, pageable);
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
		List<String> actions = this.userPdaService.getByUserId(user.getUsrId());
		user.setActions(actions);
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
		
		int company_id = PisUtils.getCurrentUser(request).getCompany_id();
		user.setCompany_id(company_id);
		
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
	 * 企业出入库用户
	 * @author jqi.can
	 * @date 2016-3-21下午09:08:19
	 */
	@RequestMapping("/pdaUserPage.do")
	public String pdaUserPage(Model model)
	{
		model.addAttribute("statusSelect", PisConstants.Available.ToOptionString());
		return "admin/userInfo/pdaUser";
	}
	
	/**
	 * 加载PDA操作
	 * @author jqi.can
	 * @date 2016-3-22下午01:58:50
	 */
	@RequestMapping("/loadPdaActions.do")
	public void pdaActions(HttpServletResponse response)
	{
		ResponseUtils.renderJsons(response, this.dataTypeService.findByType(PisConstants.DataType.PDAAction.getKey()));
	}
	
	/**
	 * PDA出入库用户列表
	 * @author jqi.can
	 * @date 2016-3-21下午09:21:38
	 */
	@RequestMapping("/padUserList.do")
	public void padUserList(HttpServletRequest request, HttpServletResponse response,String searchParam,Pageable pageable)
	{
		int companyId = PisUtils.getCurrentUser(request).getCompany_id();
		int count = this.userService.getCountByCompId(String.valueOf(companyId), String.valueOf(PisConstants.SystemType.PDA.getKey()), null, searchParam);
		List<Object> list = this.userService.findByCompId(String.valueOf(companyId), null, String.valueOf(PisConstants.SystemType.PDA.getKey()), searchParam, pageable);
		ResponseUtils.renderJson(response, new Page<Object>(list, count));
	}
	
	/**
	 * PDA用户保存
	 * @author jqi.can
	 * @date 2016-3-22下午03:31:07
	 */
	@RequestMapping("/pdaUserSave.do")
	public void pdaUserSave(HttpServletResponse response, HttpServletRequest request, SysUser user, int rlId)
	{
		String msg = "";
		msg = this.userService.savePdaUser(user, rlId);
			
		ResponseUtils.renderText(response, msg);
	}
	
	
	/**
	 * 终端用户列表
	 * @author jqi.can
	 * @date 2016-3-31下午06:28:50
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
	 * 终端用户导出
	 * @author jqi.can
	 * 2016-7-22下午03:47:07
	 */
	@RequestMapping("/exportEndUser.do")
	public void exportEndUser(HttpServletRequest request, HttpServletResponse response)
	{
		HashMap<String, String> result = new HashMap<String, String>();
		String url = "";
		
		String xlsTitle = request.getParameter("title");
		String searchParam = request.getParameter("searchParam");
		String rlId = request.getParameter("rlId");
		
		try 
		{
			ArrayList<Object> list = this.userService.exportEndUser(null, searchParam, rlId);
			
			LinkedHashMap<String,String> title = new LinkedHashMap<String, String>();
			title.put("LGNNM", "登录名");
			title.put("RLNM", "用户类型");
			title.put("MOBILE", "手机号");
			title.put("POINT", "积分");
			title.put("ADDRESS", "地址");
			
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
	 * 其他操作用户 如出入库记录查看员
	 * @author jqi.can
	 * 2016-7-4下午04:25:51
	 */
	@RequestMapping("/otherUser.do")
	public String otherUser(Model model)
	{
		List<SysRole> list = roleService.findByType(PisConstants.SystemType.Company.getKey());
		model.addAttribute("sRole", PisUtils.list2Option(list, "getRlId", "getRlNm", null, false));
		model.addAttribute("statusSelect", PisConstants.Available.ToOptionString());
		return "admin/userInfo/otherUser";
	}
	
	/**
	 * 根据用户类型查询 1-平台 2-企业  3-供应商 4-者经销商
	 * @author jqi.can
	 * 2016-7-4下午04:40:21
	 */
	@RequestMapping("/userListByType.do")
	public void userListByType(HttpServletRequest request, HttpServletResponse response, Pageable pageable)
	{
		int type = Integer.parseInt(request.getParameter("type"));
		int companyId = PisUtils.getCurrentUser(request).getCompany_id();
		String searchParam = request.getParameter("searchParam");
		int count = this.userService.findByTypeCount(type, companyId, searchParam);
		List<SysUser> list = this.userService.findBySysType(type, companyId, searchParam, pageable);
		
		ResponseUtils.renderJson(response, new Page<SysUser>(list, count));
	}
	
	/**
	 * 根据用户类型查询 无分页 1-平台 2-企业  3-供应商 4-者经销商
	 * @author jqi.can
	 * 2016-7-22下午04:09:26
	 */
	@RequestMapping("/getByType.do")
	public void getByType(HttpServletRequest request, HttpServletResponse response)
	{
		String rlId = request.getParameter("rlId");
		String type = request.getParameter("type");
		int companyId = PisUtils.getCurrentUser(request).getCompany_id();
		String searchParam = request.getParameter("searchParam");
		
		List<SysUser> list = this.userService.getByType(rlId, type, companyId, searchParam);
		
		ResponseUtils.renderJsons(response, list);
	}
	
	
	@RequestMapping("/exportPDAUserList.do")
	public void exportPDAUserList(HttpServletRequest request, HttpServletResponse response)
	{
		HashMap<String, String> result = new HashMap<String, String>();
		String url = "";
		
		String xlsTitle = request.getParameter("title");
		String searchParam = request.getParameter("searchParam");
		int companyId = PisUtils.getCurrentUser(request).getCompany_id();
		
		try 
		{
			ArrayList<Object> list = this.userService.getUserForExport(companyId, searchParam, null, PisConstants.SystemType.PDA.getKey());
			
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
}
