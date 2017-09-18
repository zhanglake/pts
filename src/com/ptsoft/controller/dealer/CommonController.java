package com.ptsoft.controller.dealer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ptsoft.common.util.FileUtil;
import com.ptsoft.common.util.ResponseUtils;
import com.ptsoft.pts.PisUtils;
import com.ptsoft.pts.account.model.vo.SysUser;
import com.ptsoft.pts.account.service.PermissionService;
import com.ptsoft.pts.system.model.vo.SysActionFunctionMap;
import com.ptsoft.pts.system.model.vo.SysArea;
import com.ptsoft.pts.system.service.AreaService;

@Controller("DealerCommonController")
@RequestMapping("/dealer/common/*")
public class CommonController
{
	@Autowired
	private AreaService areaService;
	@Autowired
	private PermissionService permissionService;

	/**
	 * 地区
	 */
	@RequestMapping(value = "/area", method = RequestMethod.GET)
	public void area(HttpServletRequest request, HttpServletResponse response, Integer parentId)
	{
		List<SysArea> areas = new ArrayList<SysArea>();
		if (parentId == null) {
			parentId = 0;
		}
		SysArea parent = areaService.find(parentId);
		if (parent != null)
		{
			areas = new ArrayList<SysArea>(areaService.findChildren(parentId));
		}
		else
		{
			areas = areaService.findRoots();
		}
		Map<Integer, String> options = new HashMap<Integer, String>();
		for (SysArea area : areas)
		{
			options.put(area.getId(), area.getName());
		}
		ResponseUtils.renderJsons(response, options);
	}
	
	/**
	 * 获取用户制定页面的按钮
	 */
	@RequestMapping("/getPageActions.do")
	public void getPageActions(HttpServletRequest request, HttpServletResponse response, String funcId)
	{
		SysUser user = PisUtils.getCurrentUser(request);
		if(user == null || user.getRole() == null) return;
		String rlId = String.valueOf(PisUtils.getCurrentUser(request).getRole().getRlId());
		List<SysActionFunctionMap> list = permissionService.getRoleActionFunctionMap(rlId, funcId);
		ResponseUtils.renderJsons(response, list);
	}
	
	/**
	 * 上传图片
	 * @author jqi.can
	 * @date 2015-12-24下午05:36:01
	 */
	@RequestMapping("uploadImg.do")
	public void uploadImg(HttpServletRequest request, HttpServletResponse response) {
		String message = null;
		try 
		{
			message = FileUtil.fileUpload(request, 1);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			message = "";
		}
		ResponseUtils.renderText(response, message);
	}
}
