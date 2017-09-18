package com.ptsoft.controller.platform;

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
import com.ptsoft.pts.basic.service.AppDeviceService;

@Controller("PlatformDeviceController")
@RequestMapping("/platform/device/*")
public class DeviceController 
{

	@Autowired
	private AppDeviceService appService;
	
	/** 进入APP设备列表页面 */
	@RequestMapping("/appDevicePage.do")
	public String appDevicePage(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		
		return "platform/device/appDevice";
	}
	
	/**
	 * 查询APP设备列表数据
	 * @author zumin.yang
	 * @date 2016-2-15
	 */
	@RequestMapping("/appDeviceList.do")
	public void appDeviceList(HttpServletRequest request, HttpServletResponse response, Pageable pageable)
	{
		//SysUser user = PisUtils.getCurrentUser(request);
		int count = this.appService.getCount();
		List<Object> list = this.appService.getAllByPage(pageable);
		ResponseUtils.renderJson(response, new Page<Object>(list, count));	
	}
}
