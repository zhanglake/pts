package com.ptsoft.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ptsoft.common.util.Page;
import com.ptsoft.common.util.Pageable;
import com.ptsoft.common.util.ResponseUtils;
import com.ptsoft.common.util.StringUtil;
import com.ptsoft.pts.PisConstants;
import com.ptsoft.pts.PisUtils;
import com.ptsoft.pts.account.model.vo.SysUser;
import com.ptsoft.pts.basic.model.vo.Dealer;
import com.ptsoft.pts.basic.model.vo.Supplier;
import com.ptsoft.pts.basic.service.DealerService;
import com.ptsoft.pts.basic.service.SupplierService;
import com.ptsoft.pts.business.model.vo.Order;
import com.ptsoft.pts.business.model.vo.SalesOrder;
import com.ptsoft.pts.business.service.OrderService;
import com.ptsoft.pts.business.service.QRCodeService;
import com.ptsoft.pts.business.service.SaleOrderService;
import com.ptsoft.pts.system.model.vo.SysLog;
import com.ptsoft.pts.system.service.SysLogService;

@Controller("AdminOrderController")
@RequestMapping("/admin/order/*")
public class OrderController {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;
	@Autowired
	private SaleOrderService saleOrderService;	
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private DealerService dealerService;
	@Autowired
	private QRCodeService qrCodeService;
	@Autowired
	private SysLogService logService;
	
	/** 
	 * 进入订单页面
	 * @author zumin.yang
	 * @date 2016-02-24
	 */
	@RequestMapping("/orderPage.do")
	public String orderPage(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		int companyId = PisUtils.getCurrentUser(request).getCompany_id();
		List<Supplier> list = this.supplierService.findByCompanyId(companyId);
		model.addAttribute("sSupplier", PisUtils.list2Option(list, "getId", "getSupplier_name", null, false));
		return "admin/order/order";
	}
	
	
	/**
	 * 同步采购单
	 * @author jqi.can
	 * @date 2016-4-29下午02:44:42
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/syncOrder.do")
	public void syncOrder(HttpServletRequest request, HttpServletResponse response)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		SysUser user = PisUtils.getCurrentUser(request);
		
		try 
		{
			map = orderService.syncPurchase(user);
			List<String> orderNoList = (List<String>) map.get("orderNoList");
			if (null != orderNoList && orderNoList.size() > 0) {
				qrCodeService.issuedQrcodeToMS(orderNoList);
			}
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
	 * 同步销售单
	 * @author jqi.can
	 * @date 2016-5-3下午06:05:18
	 */
	@RequestMapping("/syncSalesOrder.do")
	public void syncSalesOrder(HttpServletRequest request, HttpServletResponse response)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		SysUser user = PisUtils.getCurrentUser(request);
		
		map = orderService.syncSalesOrder(user);
		
		orderService.updateSalesNo(user);
		
		ResponseUtils.renderJson(response, map);
	}
	
	/**
	 * 查询订单列表
	 * @author zumin.yang
	 * @date 2016-2-24
	 */
	@RequestMapping("/orderList.do")
	public void orderList(HttpServletRequest request, HttpServletResponse response, Pageable pageable)
	{
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		SysUser user = PisUtils.getCurrentUser(request);
		
		map.put("fmtm", request.getParameter("fmtm"));
		map.put("totm", request.getParameter("totm"));
		map.put("orderFrom", request.getParameter("orderFrom"));
		map.put("orderNo", "%" +  request.getParameter("orderNo") + "%");
		map.put("supplierId", request.getParameter("supplierId"));
		map.put("companyId", user.getCompany_id());
		
		int count = this.orderService.getCountByCompId(map);
		List<Object> list = this.orderService.findByCompId(map, pageable);
		ResponseUtils.renderJson(response, new Page<Object>(list, count));	
	}
	
	/**
	 * 进入订单编辑页面
	 * @author zumin.yang
	 * @date 2016-02-24
	 */
	@RequestMapping("/orderEditPage.do")
	public String orderEditPage(HttpServletResponse response, Model model, String id)
	{
		
		model.addAttribute("id", id);
		
		return "admin/order/orderEditor";
	}
	
	/**
	 * 订单信息查询
	 * @author zumin.yang
	 * @date 2016-02-24
	 */
	@RequestMapping("/orderEdit.do")
	public void orderEdit(HttpServletResponse response, Model model, String id)
	{
		Order order = null;
		if (!id.equals(""))
		{
			order = this.orderService.getById(Integer.parseInt(id));
		}
		if (order == null)
		{
			order = new Order();
		}
		
		ResponseUtils.renderJson(response, order);
	}
	
	/**
	 * 保存订单信息
	 * @author zumin.yang
	 * @date 2016-02-24
	 */
	@RequestMapping("/orderSave.do")
	public void orderSave(HttpServletRequest request,HttpServletResponse response, Order order)
	{
		SysUser user = PisUtils.getCurrentUser(request);
		order.setCompanyId(user.getCompany_id());
		if(order.getCreator() == null || order.getCreator() == "")
		{
			order.setCreator(user.getLgnNm());
		}
		String msg = this.orderService.saveOrder(user, order);
		ResponseUtils.renderText(response, msg);
	}
	
	/**
	 * 根据订单号查询订单明细
	 * @author zumin.yang
	 * @date 2016-02-26
	 */
	@RequestMapping("/orderItemList.do")
	public void orderItemList(HttpServletRequest request, HttpServletResponse response,int orderId)
	{
		ResponseUtils.renderJsons(response, this.orderService.findItemsByOrderId(orderId));
	}
	
	/**
	 * 保存订单明细
	 * @author zumin.yang
	 * @date 2016-02-26
	 */
	@RequestMapping("/orderItemsSave.do")
	public void orderItemsSave(HttpServletRequest request,HttpServletResponse response,int orderId, String itemstr)
	{
		String msg = this.orderService.saveOrderItems(orderId, itemstr);
		ResponseUtils.renderText(response, msg);
	}
	
	/**
	 * 生成二维码
	 * @author zumin.yang
	 * @date 2016-02-26
	 */
	@RequestMapping("/createQrcode.do")
	public void createQrcode(HttpServletRequest request, HttpServletResponse response, int orderId)
	{
		SysUser user = PisUtils.getCurrentUser(request);
		HashMap<String, Object> map = new HashMap<String, Object>();
		try 
		{
			this.orderService.createQrcode(user, orderId);
			List<String> list = new ArrayList<String>();
			list.add(String.valueOf(orderId));
			qrCodeService.issuedQrcodeToMS(list);
		} 
		catch (Exception e) 
		{
			logger.error("----syncOrder---" + e.toString());
			map.put("code", 0);
			map.put("msg", "二维码生成失败");
			map.put("msOrderId", null);
			SysLog sysLog = new SysLog(user == null ? "二维码生成" : user.getLgnNm(), user == null ? 2 : user.getUsrId(), "生成二维码", "订单" + orderId + "生成二维码", 0, String.valueOf(PisConstants.LogActionType.CreateQrcode.getKey()), PisConstants.LogType.Exception.getKey());
			this.logService.insert(sysLog);
		}
		
		ResponseUtils.renderJson(response, map);
	}
	
	/**
	 * 批量生成二维码
	 * @author jqi.can
	 * @date 2016-5-13下午02:33:52
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/createBat.do")
	public void createBat(HttpServletRequest request, HttpServletResponse response)
	{
		String ids = request.getParameter("ids");
		SysUser user = PisUtils.getCurrentUser(request);
		HashMap<String, Object> result = new HashMap<String, Object>();
		String msg = "";
		try
		{
			result = this.orderService.createBat(ids.split(","), user);
			
			List<String> list = (List<String>) result.get("msOrderIds");
			msg = result.get("msg").toString();
			if(null != list && list.size() > 0)
			{
				this.qrCodeService.issuedQrcodeToMS(list);
			}
		}
		catch (Exception e) 
		{
			logger.error("--createBat--" + e.getMessage());
			result.put("msg", "0");
			result.put("msOrderIds", null);
		}
		ResponseUtils.renderText(response, msg);
	}
	
	/**
	 * 下发二维码
	 * @author jqi.can
	 * 2016-7-21上午11:35:56
	 */
	@RequestMapping("/issueQrcode.do")
	public void issueQrcode(HttpServletRequest request, HttpServletResponse response)
	{
		String ids = request.getParameter("ids");
		List<String> list = StringUtil.getStringList(ids.split(","));
		HashMap<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		try 
		{
			map = qrCodeService.issuedQrcodeToMS(list);
			
			String noneOrder = map.get("noneOrder").toString();
			String failedOrder = map.get("failedOrder").toString();
			String code = map.get("code").toString();
			
			if(code.equals("404"))
			{
				msg = "下发二维码出现异常！";
			}
			else
			{
				if(null != noneOrder && !"".equals(noneOrder))
				{
					msg = "订单" + noneOrder + "无二维码可下发；";
				}
				if(null != failedOrder && !"".equals(failedOrder))
				{
					msg += "订单" + failedOrder + "下发失败";
				}
			}
			
			if("".equals(msg))
			{
				msg = "二维码下发成功！";
			}
		}
		catch (Exception e) 
		{
			logger.error("----下发MS异常---" + e.toString());
			msg = "下发MS异常";
		}
		ResponseUtils.renderText(response, msg);
	}
	
	/**
	 * 出库订单页面
	 * @author jqi.can
	 * @date 2016-4-12下午02:49:22
	 */
	@RequestMapping("/salesOrder.do")
	public String salesOrder()
	{
		return "admin/order/salesOrder";
	}
	
	/**
	 * 出库单列表
	 * @author jqi.can
	 * @date 2016-4-12下午02:56:42
	 */
	@RequestMapping("/salesOrderList.do")
	public void salesOrderList(HttpServletRequest request, HttpServletResponse response, Pageable pageable)
	{
		String fmtm = request.getParameter("fmtm");
		String totm = request.getParameter("totm");
		String searchParam = request.getParameter("searchParam");
		String dealerId = request.getParameter("dealerId");
		String orderType = request.getParameter("ordType");
		int companyId = PisUtils.getCurrentUser(request).getCompany_id();
		
		int count = this.saleOrderService.getSizeByParam(companyId, fmtm, totm,searchParam, dealerId, orderType);
		List<SalesOrder> list = this.saleOrderService.getByParam(companyId, fmtm, totm,searchParam, dealerId, orderType, pageable);
		ResponseUtils.renderJson(response, new Page<SalesOrder>(list, count));
	}
	
	/**
	 * 添加出库订单
	 */
	@RequestMapping("/saveSalesOrder.do")
	public void saveSalesOrder(HttpServletRequest request, HttpServletResponse response,SalesOrder salesOrder) 
	{
		SysUser user=PisUtils.getCurrentUser(request);
		int companyId = PisUtils.getCurrentUser(request).getCompany_id();
		HashMap<String, Object> map=new HashMap<String, Object>();
		try {
			map = this.saleOrderService.saveSalesOrder(user,salesOrder, companyId);
		} catch (Exception e) {
			map.put("msg", "出库订单保存失败！");
		}
		ResponseUtils.renderJson(response, map);
	}
	
	/**
	 * 获取出库单信息
	 * @author jqi.can
	 * 2016-8-4上午10:24:03
	 */
	@RequestMapping("/saleOrder.do")
	public void saleOrder(HttpServletRequest request, HttpServletResponse response)
	{
		String orderId = request.getParameter("orderId");
		SalesOrder order = this.saleOrderService.getById(Integer.parseInt(orderId));
		
		ResponseUtils.renderJson(response, order);
	}
	
	/**
	 * 添加出库单明细
	 */
	@RequestMapping("/saleOrderDetailsSave.do")
	public void saleOrderDetailsSave(HttpServletRequest request,HttpServletResponse response,int orderId, String itemstr)
	{
		String msg = this.orderService.saveSaleOrderDetails(orderId, itemstr);
		ResponseUtils.renderText(response, msg);
	}
	
	/**
	 * 出库单明细
	 * @author jqi.can
	 * @date 2016-4-13下午01:30:56
	 */
	@RequestMapping("/saleOrderDetails.do")
	public void saleOrderDetails(HttpServletRequest request, HttpServletResponse response, String orderId)
	{
		ResponseUtils.renderJsons(response, this.saleOrderService.geByOrderId(orderId));
	}

	/**
	 * 经销商
	 * @author jqi.can
	 * 2016-7-7上午11:36:42
	 */
	@RequestMapping("/loadDealers.do")
	public void loadDealers(HttpServletRequest request, HttpServletResponse response)
	{
		int companyId = PisUtils.getCurrentUser(request).getCompany_id();
		List<Dealer> list = this.dealerService.findByCompanyId(companyId);
		ResponseUtils.renderJsons(response, list);
	}
}
