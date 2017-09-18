package com.ptsoft.controller.api;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ptsoft.common.util.ResponseUtils;
import com.ptsoft.pts.account.model.vo.SysUser;
import com.ptsoft.pts.account.service.UserService;
import com.ptsoft.pts.business.model.vo.SyPackage;
import com.ptsoft.pts.business.service.PackageRuleService;
import com.ptsoft.pts.business.service.PackageService;
import com.ptsoft.pts.business.service.ProductService;
import com.ptsoft.pts.business.service.QRCodeService;

@Controller("ApiPkgController")
@RequestMapping("/api/package/*")
public class PkgController {
	
	@Autowired
	private QRCodeService qrService;
	@Autowired
	private PackageService pkgService;
	@Autowired
	private ProductService productService;
	@Autowired
	private UserService userService;
	@Autowired
	private PackageRuleService packageRuleService;
	
	Logger logger = Logger.getLogger(PkgController.class);

	/**
	 * 包装二维码查询
	 * @author zumin.yang
	 * @date 2016-03-15
	 * @param qrcode
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value="qrcode", method=RequestMethod.POST)
	public void qrcode(HttpServletResponse response, String qrcode)
	{
		response.setHeader("Access-Control-Allow-Origin", "*");
		logger.error("------包装二维码查询开始------");
		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, Object> entity = qrService.findByCodeMap(qrcode);
		SyPackage pkg = pkgService.getById(Integer.parseInt(entity.get("PACKAGE_ID").toString()));
		HashMap<String, Object> product = productService.getByInCode(qrcode);
		List<HashMap<String, Object>> ruleMap = this.packageRuleService.getByRuleId(Integer.parseInt(product.get("PACKAGEID").toString()));
		if(entity == null)
		{
			map.put("code", 0);
			map.put("msg", "二维码不存在");
		}
		else
		{
			map.put("code", 1);
			map.put("qrcode", entity);
			map.put("package", pkg);
			if(null == product)
			{
				map.put("product", "");
			}
			else
			{
				map.put("product", product);
				map.put("ruleMap", ruleMap);
			}
			map.put("msg", "二维码查询成功");
		}
		logger.error("------包装二维码查询结束------");
		ResponseUtils.renderJson(response, map);
	}
	
	/**
	 * 保存包装记录
	 * @author zumin.yang
	 * @date 2016-03-15
	 */
	@RequestMapping(value="pkgRecord", method=RequestMethod.POST)
	public void savePkgRecord(HttpServletRequest request, HttpServletResponse response, String outerCode, String innerCodes, int type, String deviceNo)
	{
		response.setHeader("Access-Control-Allow-Origin", "*");
		HashMap<String, Object> map = new HashMap<String, Object>();
		try 
		{
			String username = request.getParameter("username");
			String token = request.getParameter("token");
			SysUser user = userService.findByNameAndToken(username, token);
			if(null == user)
			{
				map.put("code", 0);
				map.put("msg", "登录已失效，请重新登录后操作");
			}
			else
			{
				map = pkgService.savePkgRecord(outerCode, innerCodes, type, user, deviceNo);
			}
		} 
		catch (Exception e) 
		{
			logger.error("------包装异常-----" + e.toString());
			map.put("code", 0);
			map.put("msg", "包装失败，出现异常");
			map.put("msg_en", "Package failure. There are some exceptions.");
			map.put("msg_code", 1002);
		}
		ResponseUtils.renderJson(response, map);
	}
	
}
