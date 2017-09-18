package com.ptsoft.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ptsoft.common.util.ResponseUtils;
import com.ptsoft.pts.PisConstants;
import com.ptsoft.pts.business.service.OrderService;
import com.ptsoft.pts.business.service.ProductInfoService;
import com.ptsoft.pts.business.service.QRCodeService;
import com.ptsoft.pts.system.model.vo.SysLog;
import com.ptsoft.pts.system.service.SysLogService;
import com.ptsoft.pts.util.DesUtil;

@Controller("ApiOrderController")
@RequestMapping("/api/order/*")
public class OrderController 
{
	@Autowired
	private OrderService orderService;
	@Autowired
	private QRCodeService qrCodeService;
	@Autowired
	private ProductInfoService productInfoService;
	@Autowired
	private SysLogService logService;
	
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	/**
	 * 同步采购订单
	 * @author jqi.can
	 * @date 2016-4-25下午02:36:54
	 */
	@RequestMapping(value="syncOrder", method=RequestMethod.POST)
	public void syncOrder(HttpServletRequest request, HttpServletResponse response)
	{
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try 
		{
			map = orderService.syncPurchase(null);			
		} 
		catch (Exception e) 
		{
			SysLog sysLog = new SysLog("系统同步", 0, "同步采购订单", e.getMessage(), 0, String.valueOf(PisConstants.LogActionType.SyncOrder.getKey()), PisConstants.LogType.Exception.getKey());
			logger.error("----syncOrder---" + e.toString());
			this.logService.insert(sysLog);
			map.put("code", 401);
			map.put("msg", "Failed");
		}		
		ResponseUtils.renderJson(response, map);
	}
	
	/**
	 * 同步出库单
	 * @author jqi.can
	 * @date 2016-4-25下午02:36:38
	 */
	@RequestMapping(value="syncSalesOrder", method=RequestMethod.POST)
	public void syncSalesOrder(HttpServletRequest request, HttpServletResponse response)
	{
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map = orderService.syncSalesOrder(null);
		
		ResponseUtils.renderJson(response, map);
	}
	
	/**
	 * 下发二维码
	 * @author jqi.can
	 * @date 2016-4-26上午10:19:55
	 */
	@RequestMapping(value="issuedQrcode", method=RequestMethod.POST)
	public void issuedQrcode(HttpServletRequest request, HttpServletResponse response)
	{
		response.setHeader("Access-Control-Allow-Origin", "*");

		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try
		{
			map = qrCodeService.issuedQrcodeToMS(null);
		}
		catch (Exception e) 
		{
			logger.error("----下发MS异常---" + e.toString());
			SysLog log = new SysLog("系统", 2, "下发二维码", "二维码下发MS异常", 0, String.valueOf(PisConstants.LogActionType.IssueQrcode.getKey()), PisConstants.LogType.Exception.getKey());
			this.logService.insert(log);
			map.put("code", 404);
			map.put("msg", "ERROR");
		}
		
		ResponseUtils.renderJson(response, map);
	}
	
	/**
	 * 获取生产信息
	 * @author jqi.can
	 * @date 2016-4-26上午10:21:35
	 */
	@RequestMapping(value="getProductInfo", method=RequestMethod.POST)
	public void getProductInfo(HttpServletRequest request, HttpServletResponse response)
	{
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		String qrcodes = request.getParameter("qrcodes");
		
		map = productInfoService.getProductInfoFromMS(qrcodes);
		
		ResponseUtils.renderJson(response, map);
	}
	
	/**
	 * 获取加密后二维码-测试用
	 * @author jqi.can
	 * @date 2016-4-28下午03:40:06
	 */
	@RequestMapping(value="getEncryptQrcodes", method=RequestMethod.POST)
	public void getEncryptQrcodes(HttpServletRequest request, HttpServletResponse response)
	{
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		String qrcodes = request.getParameter("qrcodes");
		List<String> list = new ArrayList<String>();
		for (String qrcode : qrcodes.split(","))
		{
			list.add(DesUtil.encrypt(qrcode, PisConstants.QRSalt));
		}
		map.put("qrcodes", list);
		ResponseUtils.renderJson(response, map);
	}
	
	/**
	 * 获取需匹配是否更新的销售单
	 * @author jqi.can
	 * 2016-7-28下午02:31:05
	 */
	@RequestMapping(value="salesToUpdate", method=RequestMethod.POST)
	public void getSalesNoToUpdate(HttpServletRequest request, HttpServletResponse response)
	{
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		HashMap<String, Object> map = this.orderService.updateSalesNo(null);
		ResponseUtils.renderJson(response, map);
	}
}
