package com.ptsoft.controller.dealer;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ptsoft.common.util.Page;
import com.ptsoft.common.util.Pageable;
import com.ptsoft.common.util.ResponseUtils;
import com.ptsoft.pts.PisUtils;
import com.ptsoft.pts.business.model.vo.SalesOrder;
import com.ptsoft.pts.business.service.SaleOrderService;

@Controller("DealerOrderController")
@RequestMapping("/dealer/order/*")
public class OrderController 
{
	@Autowired
	private SaleOrderService saleOrderService;
	
	/**
	 * 订单页面
	 * @author jqi.can
	 * 2016-6-8下午01:52:15
	 */
	@RequestMapping("/order.do")
	public String salesOrder()
	{
		return "dealer/order/order";
	}
	
	/**
	 * 订单列表
	 * @author jqi.can
	 * 2016-6-8下午01:58:36
	 */
	@RequestMapping("/orderList.do")
	public void orderList(HttpServletRequest request, HttpServletResponse response, String fmtm, String totm, String searchParam, Pageable pageable)
	{
		int dealerId = PisUtils.getCurrentUser(request).getDealer_id();
		int count = this.saleOrderService.getCountByDealer(dealerId, fmtm, totm,searchParam);
		List<SalesOrder> list = this.saleOrderService.getCountByDealer(dealerId, fmtm, totm,searchParam, pageable);
		ResponseUtils.renderJson(response, new Page<SalesOrder>(list, count));
	}
	
	/**
	 * 订单明细
	 * @author jqi.can
	 * 2016-6-8下午02:04:54
	 */
	@RequestMapping("/orderDetails.do")
	public void orderDetails(HttpServletRequest request, HttpServletResponse response, String orderId)
	{
		ResponseUtils.renderJsons(response, this.saleOrderService.geByOrderId(orderId));
	}
}
