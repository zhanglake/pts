package com.ptsoft.controller.dealer;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ptsoft.common.Constants;
import com.ptsoft.common.util.Page;
import com.ptsoft.common.util.Pageable;
import com.ptsoft.common.util.ResponseUtils;
import com.ptsoft.pts.PisConstants;
import com.ptsoft.pts.PisUtils;
import com.ptsoft.pts.account.model.vo.SysUser;
import com.ptsoft.pts.account.service.UserService;
import com.ptsoft.pts.basic.model.vo.Dealer;

@Controller("DealerAccountController")
@RequestMapping("/dealer/account/*")
public class AccountController
{
	@Autowired
	private UserService userService;


	/** 进入用户页面 */
	@RequestMapping("/userPage.do")
	public String userPage(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		Dealer dealer = (Dealer) request.getSession().getAttribute(Constants.SA_DEAL);
		model.addAttribute("dealerId", dealer.getId());
		model.addAttribute("statusSelect", PisConstants.Available.ToOptionString());
		return "dealer/userInfo/user";
	}
	
	/**
	 * 用户列表
	 */
	@RequestMapping("/userList.do")
	public void userList(HttpServletResponse response, HttpServletRequest request,String sysTp,String isLgn,String dealerId,String searchParam,Pageable pageable)
	{
		int count = this.userService.getDealerUserCount(sysTp, isLgn, dealerId, searchParam);
		List<SysUser> list = this.userService.findDealerUser(sysTp, isLgn, dealerId, searchParam,pageable);
		ResponseUtils.renderJson(response, new Page<SysUser>(list, count));
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
}
