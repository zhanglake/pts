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
import com.ptsoft.common.util.SysConfig;
import com.ptsoft.pts.PisConstants;
import com.ptsoft.pts.account.model.vo.SysUser;
import com.ptsoft.pts.account.service.UserService;
import com.ptsoft.pts.basic.service.TraceService;
import com.ptsoft.pts.business.model.vo.ProductInfo;
import com.ptsoft.pts.business.model.vo.SyPackage;
import com.ptsoft.pts.business.service.PackageRuleService;
import com.ptsoft.pts.business.service.PackageService;
import com.ptsoft.pts.business.service.ProductInfoService;
import com.ptsoft.pts.business.service.ProductService;
import com.ptsoft.pts.business.service.QRCodeService;
import com.ptsoft.pts.util.DesUtil;

@Controller("ApiTraceController")
@RequestMapping("/api/trace/*")
public class TraceController {
	
	Logger logger = Logger.getLogger(TraceController.class);
	
	@Autowired
	private TraceService traceService;
	@Autowired
	private UserService userService;
	@Autowired
	private QRCodeService qrService;
	@Autowired
	private PackageService pkgService;
	@Autowired
	private ProductService productService;
	@Autowired
	private PackageRuleService packageRuleService;
	@Autowired
	private ProductInfoService productInfoService;
	
	public void queryQrcode(String qrcode){
		
	}
	
///////////////////////zxfupdate20160818
	/**
	 * 根据用户ID获取用积分明细分页
	 * @author zxf
	 * @date 2016-03-09
	 */
	@RequestMapping(value="queryPointDetailpage", method=RequestMethod.POST)
	public void queryPointDetailpage(HttpServletRequest request, HttpServletResponse response, String num, String begin, String userId)
	{
		response.setHeader("Access-Control-Allow-Origin", "*");
		System.out.println(userId + "---------用户id");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		SysUser user = userService.getById(Integer.valueOf(userId));
		
		if(null == user || "".equals(user))
		{
			map.put("code", 0);
			map.put("msg", "查询积分明细失败, 用户不存在");
			map.put("msg_en", "It's failed, the user isn't exist.");
		}
		else
		{
			map.put("code", 1);
			map.put("detail",  traceService.queryPointDetailpage(userId, Integer.valueOf(begin), Integer.valueOf(num)));
			map.put("msg", "查询积分明细成功");
			map.put("msg_en", "It's success to search user point information.");

		}
		ResponseUtils.renderJson(response, map);
		
	}
	
	/**
	 * 二维码扫描查询溯源信息
	 * @author zumin.yang
	 * @date 2016-03-14
	 * @param qrcode
	 * @param userId
	 * @param actionType
	 */
	@RequestMapping(value="scanQuery", method=RequestMethod.POST)
	public void scanQuery(HttpServletRequest request, HttpServletResponse response, String qrcode, String userId, String username, int scanType)
	{
		response.setHeader("Access-Control-Allow-Origin", "*");

		HashMap<String, Object> map = new HashMap<String, Object>();
		try 
		{
			SysUser user = userService.getById(Integer.valueOf(userId));
			String company = request.getParameter("company");
			
			if(null == company || "".equals(company))
			{
				map.put("code", 0);
				map.put("msg", "请下载最新版本");
				map.put("msg_en", "Please download the latest version.");
				map.put("msg_code", 4000);
			}
			else
			{
				//System.out.println(company + "---------企业标识");
				map = traceService.scanQuery(qrcode, user, scanType, company);
			}
		}
		catch (Exception e) 
		{
			logger.error(e.toString() + "----scan Query--");
		}
		ResponseUtils.renderJson(response, map);
	}
	
	/**
	 * 查询二维码信息
	 * @author zumin.yang
	 * @date 2016-3-14
	 * @param response
	 * @param qrcode
	 * @return
	 */
	@RequestMapping(value="qrcode", method=RequestMethod.POST)
	public void qrcodeQuery(HttpServletRequest request, HttpServletResponse response, String qrcode)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		try 
		{
			response.setHeader("Access-Control-Allow-Origin", "*");
			
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
				qrcode = qrcode.replace(SysConfig.get_weifu_url(), "");
				qrcode = DesUtil.decrypt(qrcode, PisConstants.QRSalt);
				
				HashMap<String, Object> qrCode = qrService.findByCodeMap(qrcode);
				
				if(qrCode == null)
				{
					map.put("code", 0);
					map.put("msg", "二维码不存在");
				}
				else
				{
					String remark = "";
					/*List<ScanRecord> list = new ArrayList<ScanRecord>();
					int status = Integer.parseInt(qrCode.get("STATUS").toString());
					list = this.traceService.getOperate(status, qrcode);
					if(null != list)
					{
						for (ScanRecord scanRecord : list) 
						{
							remark += scanRecord.getOperator() + "于" + scanRecord.getCreateTime() + "进行" + scanRecord.getActionName() + "操作；";
						}
					}*/
					
					List<ProductInfo> isExit = this.productInfoService.isExist(qrcode);
					if(null == isExit || isExit.size() < 1)
					{
						traceService.insertProduceInfoAndPackageRecordIfMs(qrcode, user, PisConstants.ActionType.Package, PisConstants.QRCodeStatus.Packaged, "");
					}
					
					SyPackage pkg = pkgService.getById(Integer.parseInt(qrCode.get("PACKAGE_ID").toString()));
					HashMap<String, Object> product = productService.getByInCode(qrcode);
					
					if(null == product)
					{
						map.put("product", "");
					}
					else
					{
						List<HashMap<String, Object>> ruleMap = this.packageRuleService.getByRuleIdForApi(Integer.parseInt(product.get("PACKAGEID").toString()));
						product.put("SERIALNO", qrCode.get("SERIALNO"));
						map.put("product", product);
						map.put("ruleMap", ruleMap);
					}
					
					map.put("remark", remark);
					map.put("qrcode", qrCode);
					map.put("package", pkg);
					map.put("code", 1);
					map.put("msg", "二维码查询成功");
				}
			}	
		} 
		catch (Exception e) 
		{
			logger.error(e.toString() + "----------qrcode.do");
			map.put("code", 0);
			map.put("msg", "二维码查询失败");
		}
		ResponseUtils.renderJson(response, map);
	}
}
