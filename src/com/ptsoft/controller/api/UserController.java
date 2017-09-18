package com.ptsoft.controller.api;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ptsoft.common.util.DateUtil;
import com.ptsoft.common.util.ResponseUtils;
import com.ptsoft.pts.PisConstants;
import com.ptsoft.pts.account.model.vo.SysRole;
import com.ptsoft.pts.account.model.vo.SysUser;
import com.ptsoft.pts.account.service.PermissionService;
import com.ptsoft.pts.account.service.UserPdaService;
import com.ptsoft.pts.account.service.UserService;
import com.ptsoft.pts.basic.model.vo.AppDevice;
import com.ptsoft.pts.basic.model.vo.Message;
import com.ptsoft.pts.basic.service.AppDeviceService;
import com.ptsoft.pts.basic.service.AppVersionService;
import com.ptsoft.pts.basic.service.MessageService;
import com.ptsoft.pts.system.model.vo.AppLog;
import com.ptsoft.pts.system.model.vo.SysLog;
import com.ptsoft.pts.system.service.AppLogService;
import com.ptsoft.pts.system.service.SysLogService;
import com.ptsoft.pts.util.MD5Util;
import com.ptsoft.pts.util.MsgUtil;
import com.ptsoft.pts.util.Tools;

@Controller("ApiUserController")
@RequestMapping("/api/user/*")
public class UserController {
	
	Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	UserService userService;
	@Autowired
	MessageService msgService;
	@Autowired
	PermissionService permissionService;
	@Autowired
	UserPdaService userPdaService; 
	@Autowired
	AppDeviceService appDeviceService;
	@Autowired
	AppVersionService versionService;
	@Autowired
	AppLogService appLogService;
	@Autowired
	private SysLogService logService;
	
	/**
	 * 登录接口
	 * @author zumin.yang
	 * @date 2016-03-03
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value="login", method=RequestMethod.POST)
	public void login(HttpServletRequest request, HttpServletResponse response, String username, String password)
	{
		response.setHeader("Access-Control-Allow-Origin", "*");

		logger.info("==============login 接口调用中============用户名：" + username + " 密码：" + MD5Util.getMd5(password));
		HashMap<String, Object> map = new HashMap<String, Object>();
		SysUser user = userService.getUserLogin(username, MD5Util.getMd5(password));
		String deviceNo = request.getParameter("deviceNo");
		String imei = request.getParameter("IMEI");
		String optSystem = request.getParameter("optSystem");
		String model = request.getParameter("model");
		String version = request.getParameter("version");
		String address = request.getParameter("address");
		
		if(user == null)
		{
			map.put("code", 0);
			map.put("msg", "用户名或密码错误");
			map.put("msg_en", "Username or password is error.");
		}
		else
		{
			String token = UUID.randomUUID().toString();
			user.setToken(token);
			Date now = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
			user.setExpireTime(formatter.format(now.getTime() + 1800000));
			user.setAddress(address);
			userService.save(user);
			
			List<String> actions = this.userPdaService.getByUserId(user.getUsrId());
			user.setActions(actions);
			
			if(null != imei && !"".equals(imei))
			{
				/**写入APP用户设备信息*/
				AppDevice app = new AppDevice();
				app.setCreateTime(formatter.format(now.getTime()));
				app.setDeviceNo(imei);
				app.setModel(model);
				app.setVersion(version);
				app.setOptSystem(optSystem);
				app.setUserId(user.getUsrId());
				appDeviceService.save(app);
				
				/**写入APP用户登录信息*/
				AppLog appLog = new AppLog();
				appLog.setUsrId(user.getUsrId());
				appLog.setUsrName(user.getLgnNm());
				appLog.setDeviceSeries(imei);
				appLog.setOs(version);
				appLog.setModel(model);
				appLogService.save(appLog);
			}
			
			if(user.getRole().getRlId() == 4)
			{
				SysRole role = new SysRole();
				role.setRlId(5);
				user.setRole(role);
			}
			
			map.put("code", 1);
			map.put("user", user);
			map.put("token", token);
			map.put("msg", "登录成功");
			map.put("msg_en", "Login successfully.");
		}
		
		SysLog log = new SysLog(username, null == user ? 0 : user.getUsrId(), "login.do", "用户登录", 1, String.valueOf(PisConstants.LogActionType.PlatLogin.getKey()), PisConstants.LogType.Operate.getKey());
		this.logService.insert(log);
		ResponseUtils.renderJson(response, map);
	}
	
	/**
	 * 注册接口
	 * @author zumin.yang
	 * @date 2016-03-03
	 */
	@RequestMapping(value="signin", method=RequestMethod.POST)
	public void signin(HttpServletResponse response, String username,String mobile, String password, int roleId, String code)
	{
		response.setHeader("Access-Control-Allow-Origin", "*");

		HashMap<String, Object> map = new HashMap<String, Object>();
		try 
		{
			SysUser tempUser = userService.findByLgNm(username);
			SysUser mobileUser = userService.findByLgNm(mobile);
			Message msg = msgService.findByMobile(mobile);
			if(username == null || username == "")
			{
				map.put("code", 0);
				map.put("msg", "请输入手机号");
				map.put("msg_en", "Please enter your phone number.");
			}
			else if(tempUser != null || mobileUser != null)
			{
				map.put("code", 0);
				map.put("msg", "用户名或手机号已注册,请重新输入");
				map.put("msg_en", "Username or phone number is exist, please re-type.");
			}
			else if(msg == null || msg.getCode() == null || !msg.getCode().equalsIgnoreCase(code))
			{
				map.put("code", 0);
				map.put("msg", "短信验证码错误，请重新发送");
				map.put("msg_en", "Validate code is error, please send again.");
			}
			else if(!Tools.timeOver(Timestamp.valueOf(msg.getSendTime()), 2))
			{
				map.put("code", 0);
				map.put("msg", "验证码已超时，请重新发送验证码");
				map.put("msg_en", "It's over time for your code, please send again.");
			}
			else
			{
				SysUser user = new SysUser();
				user.setLgnNm(username);
				user.setPswd(MD5Util.getMd5(password));
				user.setIsLgn(0);
				user.setSts(1);
				user.setMobile(mobile);
				SysRole role = new SysRole();
				role.setRlId(roleId);
				user.setRole(role);
				userService.save(user);
				map.put("code", 1);
				map.put("msg", "注册成功");
				map.put("msg_en", "Registration completed.");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			map.put("code", 0);
			map.put("msg", "注册失败");
			map.put("msg_en", "Exception.");
		}
		
		ResponseUtils.renderJson(response, map);
	}
	
	/**
	 * 检查用户名或手机号是否已注册
	 * @author zumin.yang
	 * @date 2016-03-09
	 */
	@RequestMapping(value="checkExists", method=RequestMethod.POST)
	public void checkExists(HttpServletResponse response, HttpServletRequest request, String subject)
	{
		response.setHeader("Access-Control-Allow-Origin", "*");

		HashMap<String, Object> map = new HashMap<String, Object>();
		SysUser user = userService.findByLgNm(subject);
		if(user == null)
		{
			map.put("code", 1);
			map.put("msg", "用户名或手机号未注册");
			map.put("msg_en", "Username or phone number is not exist");
		}
		else
		{
			map.put("code", 0);
			map.put("msg", "用户名或手机号已存在");
			map.put("msg_en", "Username or phone number is exist.");
		}
	}
	
	
	/**
	 * 发送短信
	 * @author zumin.yang
	 * @date 2016-03-08
	 */
	@RequestMapping(value="sendMsg", method=RequestMethod.POST)
	public void sendMsg(HttpServletResponse response, String mobile)
	{
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		String result = "";
		try
		{
			String code = Tools.Random6digitsCode();
			String content = "【无锡威孚】威孚集团产品溯源短信验证码:" + code;
			result = MsgUtil.sendTextSms(mobile, content);
			Message msg = new Message();
			msg.setCode(code);
			msg.setMobile(mobile);
			msg.setContent(content);
			msg.setSendTime(DateUtil.getDateTime(new Date()));
			msgService.save(msg);
			
			map.put("code", 1);
			map.put("msg", "短信发送成功");
			map.put("msg_en", "SMS OK.");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			map.put("code", 0);
			map.put("msg", "短信发送失败");
			map.put("msg_en", "SMS failed.");
		}
		map.put("result", result);
		ResponseUtils.renderJson(response, map);
	}
	
	/**
	 * 忘记密码
	 * @author zumin.yang
	 * @date 2016-03-08
	 */
	@RequestMapping(value="forget_password", method=RequestMethod.POST)
	public void setPassword(HttpServletResponse response, String username, String password, String code)
	{
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		try 
		{
			SysUser user = userService.findByLgNm(username);
			Message msg = msgService.findByMobile(username);
			
			if(user == null)
			{
				map.put("code", 0);
				map.put("msg", "用户不存在");
				map.put("msg_en", "User is not exist.");
			}
			else if(msg == null || msg.getCode() == null || !msg.getCode().equalsIgnoreCase(code))
			{
				map.put("code", 0);
				map.put("msg", "短信验证码不正确，请重新发送验证码");
				map.put("msg_en", "Validate code is error, please send again.");
			}
			else if(!Tools.timeOver(Timestamp.valueOf(msg.getSendTime()), 2))
			{
				map.put("code", 0);
				map.put("msg", "验证码已超时，请重新发送验证码");
				map.put("msg_en", "It's over time for your code, please send again.");
			}
			else
			{
				user.setPswd(MD5Util.getMd5(password));
				userService.save(user);
				map.put("code", 1);
				map.put("msg", "设置新密码成功");
				map.put("msg_en", "The new password establishes the success.");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			map.put("code", 0);
			map.put("msg", "修改密码失败");
			map.put("msg_en", "Exception.");
		}
		
		ResponseUtils.renderJson(response, map);
	}
	
	/**
	 * 根据旧密码修改密码
	 * @author zumin.yang
	 * @date 2016-03-08
	 */
	@RequestMapping(value="modify_password", method=RequestMethod.POST)
	public void modifyPassword(HttpServletRequest request, HttpServletResponse response, String newPassword, String oldPassword)
	{
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		String username = request.getParameter("username");
		SysUser user = userService.getUserLogin(username, MD5Util.getMd5(oldPassword));
		if(user != null)
		{
			user.setPswd(MD5Util.getMd5(newPassword));
			userService.save(user);
			map.put("code", 1);
			map.put("msg", "密码修改成功");
			map.put("msg_en", "Password is changed successfully.");
		}
		else
		{
			map.put("code", 0);
			map.put("msg", "旧密码输入不正确，请重新输入");
			map.put("msg_en", "Old password is error, please re-type.");
		}
		
		ResponseUtils.renderJson(response, map);
	}
	
	/**
	 * 根据用户ID获取用户信息
	 * @author zumin.yang
	 * @date 2016-03-09
	 */
	@RequestMapping(value="getUser", method = RequestMethod.POST)
	public void getUser(HttpServletResponse response, String userId)
	{
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		SysUser user = userService.getById(Integer.valueOf(userId));
		if(user == null)
		{
			map.put("code", 0);
			map.put("msg", "查询用户信息失败");
			map.put("msg_en", "It's failed to search user information.");
		}
		else
		{
			map.put("code", 1);
			map.put("user", user);
			map.put("msg", "查询用户信息成功");
			map.put("msg_en", "It's success to search user information.");
		}
		
		ResponseUtils.renderJson(response, map);
	}
	
	
	/**
	 * 获取所有PDA用户
	 * @author jqi.can
	 * @date 2016-3-24上午11:08:58
	 */
	@RequestMapping(value="getAllPdaUser", method = RequestMethod.POST)
	public void getAllPDAUser(HttpServletRequest request, HttpServletResponse response)
	{
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<Integer, SysUser> allPDAUser = this.userService.getAllPDAUser();
		if(null == allPDAUser || allPDAUser.size() <= 0)
		{
			map.put("msg", "所有PDA用户查询失败");
			map.put("result", "");
		}
		else
		{
			map.put("result", allPDAUser);
			map.put("msg", "所有PDA用户查询成功");
		}
		map.put("code", 1);
		ResponseUtils.renderJson(response, map);
	}
	
	/**
	 * 版本更新
	 * @author jqi.can
	 * 2016-6-3上午10:48:21
	 */
	@RequestMapping(value="checkUpdate", method = RequestMethod.POST)
	public void checkUpdate(HttpServletRequest request, HttpServletResponse response)
	{
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		String company = request.getParameter("company");
		String version = request.getParameter("version");
		String os = request.getParameter("os");
		
		if(null == company || "".equals(company))
		{
			map.put("code", 0);
			map.put("src", null);
			map.put("msg", "请下载最新版本");
			map.put("msg_en", "Please download the latest version.");
			map.put("update", 0);
		}
		else
		{
			map = this.versionService.checkUpdate(company, version, os);
		}
		
		ResponseUtils.renderJson(response, map);
	}
}
