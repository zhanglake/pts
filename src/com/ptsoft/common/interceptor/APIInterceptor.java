package com.ptsoft.common.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.ptsoft.pts.account.model.vo.SysUser;
import com.ptsoft.pts.account.service.UserService;

public class APIInterceptor implements HandlerInterceptor {
	
	private static final Logger logger = Logger.getLogger(APIInterceptor.class);
	private static final String[] NoFilters = new String[] { "login.do", "signin.do", "sendMsg.do", "forget_password.do", "checkUpdate.do", "test.do"};
	
	@Autowired
	private UserService userService;
	
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2, ModelAndView arg3) throws Exception {

	}

	@SuppressWarnings("unused")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		
		String uri[] = request.getRequestURI().split("/");
		if (uri.length < 3)
		{
			return false;
		}
		
		//String ctrl = uri[2];
		String page = uri[uri.length-1];
		
		boolean beFilter = true;
		for (String s : NoFilters)
		{
			s = s.replace("/", "");
			if (page.equals(s))
			{
				beFilter = false;
				break;
			}
		}
		if(beFilter){
			String token = request.getParameter("token");
			logger.info("=================token=================" + token);
			if(token == null) {
				response.setContentType("application/json");
				PrintWriter out = response.getWriter();
				out.write("{code: 0, msg: 'Token为空，请先登录'}");
				out.flush();
				//out.close();
				return false;
			}
			String username = request.getParameter("username");
			SysUser tmpUser = userService.findByNameAndToken(username, token);
			System.out.println(tmpUser.getUsrNm() + "=========");
			if(tmpUser == null)
			{
				response.setContentType("application/json");
				PrintWriter out = response.getWriter();
				out.write("{code: 0, msg: 'Token无效，请重新登录获取token'}");
				out.flush();
				out.close();
				return false;
			}
		}
		return true;
	}

}
