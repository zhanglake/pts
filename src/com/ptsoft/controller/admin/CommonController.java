package com.ptsoft.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ptsoft.common.Constants;
import com.ptsoft.common.util.ExportUtil;
import com.ptsoft.common.util.FileUtil;
import com.ptsoft.common.util.ResponseUtils;
import com.ptsoft.pts.PisUtils;
import com.ptsoft.pts.account.model.vo.SysUser;
import com.ptsoft.pts.account.service.PermissionService;
import com.ptsoft.pts.system.model.vo.SysActionFunctionMap;
import com.ptsoft.pts.system.model.vo.SysArea;
import com.ptsoft.pts.system.service.AreaService;

@Controller("AdminCommonController")
@RequestMapping("/admin/common/*")
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
	
	/**
	 * 下载文件
	 * @author jqi.can
	 * 2016-7-18下午15:17:58
	 */
	@RequestMapping("/doDownload.do")
	public void doDownload(HttpServletRequest request, HttpServletResponse response,String file)
	{
		try {
			if(StringUtils.isNotEmpty(file))
			{
				String filePath = file.substring(file.indexOf(Constants.EXPORT_XLS_PATH));
				String fileName = filePath.substring(filePath.lastIndexOf("\\")+1);
				FileUtil.DownloadFile(response, request, request.getSession().getServletContext().getRealPath("/") + filePath, fileName);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 生成excel文件 并返回路径
	 * @author jqi.can
	 * 2016-7-20上午11:20:55
	 */
	@RequestMapping("/doExport.do")
	public void doExport(HttpServletRequest request, HttpServletResponse response, String columns, String cnts, String title)
	{
	    String url = ExportUtil.exportToExcel(columns, cnts, title, response);
	    ResponseUtils.renderText(response, url);
	}
}
