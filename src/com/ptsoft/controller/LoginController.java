package com.ptsoft.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ptsoft.common.Constants;
import com.ptsoft.common.util.ResponseUtils;
import com.ptsoft.pts.account.model.vo.SysUser;
import com.ptsoft.pts.account.service.UserService;
import com.ptsoft.pts.basic.service.CompanyService;
import com.ptsoft.pts.basic.service.DealerService;
import com.ptsoft.pts.basic.service.SupplierService;
import com.ptsoft.pts.system.model.vo.SysLog;
import com.ptsoft.pts.system.service.SysLogService;

/**
 * 登录系统，验证用户，成功后跳转到相应页面
 */
@Controller
public class LoginController
{
	@Autowired
	private UserService userService;
	@Autowired
	private CompanyService compService;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private DealerService dealerService;
	@Autowired
	private SysLogService logService;
	
	/**
	 * 验证用户登录
	 */
	@RequestMapping("/doLogin.do")
	public void doLogin(HttpServletResponse response, HttpServletRequest request, Model model, String username, String password)
	{
		SysUser sysUser = userService.getUserLogin(username, password);
		String msg = "";
		String url = "";
		if (sysUser != null)
		{
			if(sysUser.getIsLgn() == 0)
			{
				msg = "不具有登录权限，请联系管理员！";
			}
			else
			{
				request.getSession().setAttribute(Constants.SA_USER, sysUser);
				SysLog log = new SysLog(username, sysUser.getUsrId(), "Do Login", "用户登录", 1, "移动端登录", 0);
				this.logService.insert(log);
				url = this.toPage(request);
			}
		}
		else
		{
			msg = "登录失败，请重试！";
		}
		
		/**不具有系统视图操作权限*/
		if(url.equals("") && msg.equals(""))
		{
			msg = "不具有登录权限，请联系管理员！";
		}
			
		String str = "{\"msg\":\"" + msg + "\",\"url\":\"" + url + "\"}";  
		ResponseUtils.renderJson(response, str);
	}
	
	/**
	 * 跳转到指定页面
	 * */
	private String toPage(HttpServletRequest request)
	{
		SysUser sysUser = (SysUser) request.getSession().getAttribute(Constants.SA_USER);
		int sysTp = sysUser.getRole().getSysTp();
		if (sysTp == 1) //平台用户
		{
			request.getSession().setAttribute(Constants.SA_COMP, null);
			request.getSession().setAttribute(Constants.SA_SUPL, null);
			request.getSession().setAttribute(Constants.SA_DEAL, null);
			return "platform/index.do";
		}
		else if (sysTp == 2) //企业用户
		{
			request.getSession().setAttribute(Constants.SA_COMP, compService.getById(sysUser.getCompany_id()));
			request.getSession().setAttribute(Constants.SA_SUPL, null);
			request.getSession().setAttribute(Constants.SA_DEAL, null);
			return "admin/index.do";
		}
		else if (sysTp == 3) //供应商用户
		{
			request.getSession().setAttribute(Constants.SA_SUPL, supplierService.getById(sysUser.getSupplier_id()));
			request.getSession().setAttribute(Constants.SA_COMP, null);
			request.getSession().setAttribute(Constants.SA_DEAL, null);
			return "supplier/index.do";
		}
		else if (sysTp == 4) //一级经销商用户
		{
			System.out.println(sysUser.getDealer_id());
			request.getSession().setAttribute(Constants.SA_DEAL, dealerService.getById(sysUser.getDealer_id()));
			request.getSession().setAttribute(Constants.SA_COMP, null);
			request.getSession().setAttribute(Constants.SA_SUPL, null);
			return "dealer/index.do";
		}
		else
		{
			return "";
		}
	}
}
