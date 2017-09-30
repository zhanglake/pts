package com.ptsoft.controller.api;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ptsoft.pts.basic.service.TraceService;
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
	@Autowired
	private TraceService traceService;
	
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
		// 测试数据
//		int type = 2;
//		String deviceNo = "001";
//		String outerCode = "2017-09-30-442f5187-bf5d-49b4-8ce4-98842193e0bb";
//		String innerCodes = "2017-09-30-c20fa7ee-02be-4f9c-9abc-d689cf778580,2017-09-30-b447f47a-a1c1-4a77-b12b-450be29ddca4,2017-09-30-c486673e-fcb2-46b2-b5fd-c24a4099ab00,2017-09-30-e00d99b0-a52d-441f-990f-5f27d2c5b3bc,2017-09-30-48ab067f-c8d7-459a-b156-5f430637cc5d,2017-09-30-4640374c-b736-46f8-998b-745fa5fd1fc8,2017-09-30-2b349e6f-255a-448d-b3a4-c964353d0831,2017-09-30-9b10e521-29ae-4f69-8ee2-f8f98adf156b,2017-09-30-6a2d3687-f4cf-4f49-b035-1ada92d487f3,2017-09-30-d5a88447-2c30-433f-9a75-6d0169a12d11";

		boolean isPackaged = false;
		response.setHeader("Access-Control-Allow-Origin", "*");
		HashMap<String, Object> map = new HashMap<String, Object>();
		SysUser user = null;
		try 
		{
			String username = request.getParameter("username");
			String token = request.getParameter("token");
//			String username = "zhangzh";
//			String token = "84302c00-2cf2-492a-83d1-d7868b1ac59b";
			user = userService.findByNameAndToken(username, token);
			if(null == user)
			{
				map.put("code", 0);
				map.put("msg", "登录已失效，请重新登录后操作");
			}
			else
			{
				map = pkgService.savePkgRecord(outerCode, innerCodes, type, user, deviceNo);
			}
			if ((Integer)map.get("code") == 1) {
				isPackaged = true;
			}
		} 
		catch (Exception e) 
		{
			logger.error("------包装异常-----" + e.toString());
			map.put("code", 0);
			map.put("msg", "包装失败，出现异常");
			map.put("msg_en", "Package failure. There are some exceptions.");
			map.put("msg_code", 1002);
			isPackaged = false;
		}
		ResponseUtils.renderJson(response, map);

		// 包装成功后入库
		if (isPackaged) {
			try {
				traceService.saveIn(user, outerCode, deviceNo);
			} catch (Exception e) {
				logger.error("------包装后自动入库异常------" + e.toString());
			}
		}
	}
	
}
