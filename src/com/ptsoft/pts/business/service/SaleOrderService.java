package com.ptsoft.pts.business.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptsoft.common.base.BaseService;
import com.ptsoft.common.base.EntityDao;
import com.ptsoft.common.util.Pageable;
import com.ptsoft.pts.account.model.vo.SysUser;
import com.ptsoft.pts.basic.dao.DealerDao;
import com.ptsoft.pts.basic.model.vo.Dealer;
import com.ptsoft.pts.business.dao.SaleOrderDetailDao;
import com.ptsoft.pts.business.dao.SalesOrderDao;
import com.ptsoft.pts.business.model.vo.SaleOrderDetail;
import com.ptsoft.pts.business.model.vo.SalesOrder;
import com.ptsoft.pts.system.dao.SysLogDao;
import com.ptsoft.pts.system.model.vo.SysLog;
import com.ptsoft.pts.util.GenerateCode;

@Service
public class SaleOrderService extends BaseService<SalesOrder, Integer> 
{
	@Autowired
	private SalesOrderDao salesOrderDao;
	@Autowired
	private SaleOrderDetailDao orderDetailDao;
	@Autowired
	private DealerDao dealerDao;
	@Autowired
	private SysLogDao sysLogDao;

	@SuppressWarnings("rawtypes")
	@Override
	protected EntityDao getEntityDao() 
	{
		return this.salesOrderDao;
	}

	@Override
	public void save(SalesOrder entity) 
	{
	}

	public List<Object> getDetailByOrderId(String orderId)
	{
		return this.orderDetailDao.getDetailByOrderId(orderId);
	}

	public int getSizeByParam(int companyId, String fmtm, String totm,String searchParam, String dealerId, String orderType) 
	{
		HashMap<String, String> map = new HashMap<String, String>();
		Dealer dealer = this.dealerDao.getById(Integer.parseInt(dealerId));
		String dealerCode = "";
		
		if(null != dealer)
		{
			dealerCode = dealer.getDealer_code();
		}
		
		map.put("searchParam", "%" + searchParam + "%");
		map.put("companyId", String.valueOf(companyId));
		map.put("fmtm", fmtm);
		map.put("totm", totm);
		map.put("dealerCode", dealerCode);
		map.put("ordType", orderType);
		
		return this.salesOrderDao.getSizeByParam(map);
	}

	public List<SalesOrder> getByParam(int companyId, String fmtm, String totm,String searchParam, String dealerId, String orderType, Pageable pageable) 
	{
		int begin = pageable.getOffset() + 1;
		int end = pageable.getOffset() + pageable.getLimit();
		HashMap<String, String> map = new HashMap<String, String>();
		Dealer dealer = this.dealerDao.getById(Integer.parseInt(dealerId));
		String dealerCode = "";
		
		if(null != dealer)
		{
			dealerCode = dealer.getDealer_code();
		}
		
		map.put("searchParam", "%" + searchParam + "%");
		map.put("companyId", String.valueOf(companyId));
		map.put("begin", String.valueOf(begin));
		map.put("end", String.valueOf(end));
		map.put("fmtm", fmtm);
		map.put("totm", totm);
		map.put("dealerCode", dealerCode);
		map.put("ordType", orderType);
		
		return this.salesOrderDao.getByParam(map);
	}

	public List<SaleOrderDetail> geByOrderNo(String orderNo) 
	{
		return this.orderDetailDao.getByOrderNo(orderNo);
	}
	
	public HashMap<String, Object> saveSalesOrder(SysUser user,SalesOrder salesOrder, int companyId) throws Exception
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		SalesOrder oldOrder  = this.salesOrderDao.getById(salesOrder.getId());
		SysLog sysLog = new SysLog();
//		try
//		{
			if(null == oldOrder)
			{
				Dealer dealer = this.dealerDao.getByCode(salesOrder.getOrderUnitNo());
				String kingId = this.salesOrderDao.getMaxKingId(salesOrder.getOrderDate());
				
				if(null == kingId || "".equals(kingId))
				{
					kingId = "P00001";
				}
				else
				{
					kingId = kingId.substring(1, kingId.length());
					kingId = "P" + GenerateCode.getSerialNo(kingId);
				}
				
				salesOrder.setCompanyId(companyId);
				salesOrder.setUnitNo(dealer.getDealer_code());
				salesOrder.setUnitName(dealer.getDealer_name());
				salesOrder.setOrderUnitNo(dealer.getDealer_code());
				salesOrder.setOrderUnitName(dealer.getDealer_name());
				salesOrder.setKingId(kingId);
				salesOrder.setOrdType(1);
				this.salesOrderDao.insert(salesOrder);
				sysLog = new SysLog(user.getLgnNm().toString(), user.getUsrId(), "add销售单", "ID["+salesOrder.getKingId().toString()+"]-订号["+salesOrder.getOrderNo(), 1, "添加", 3);
				
			}
			else
			{
				this.salesOrderDao.update(salesOrder);
				sysLog = new SysLog(user.getLgnNm().toString(), user.getUsrId(), "edit销售单", "ID["+oldOrder.getKingId().toString()+"]-订号["+oldOrder.getOrderNo()+"]-原状态["+oldOrder.getStatus()+"]-新状态["+salesOrder.getStatus()+"]", 1, "修改", 3);
			}
			this.sysLogDao.insert(sysLog);
			map.put("msg", "出库订单保存成功！");
			map.put("order", this.salesOrderDao.getByOrderNo(salesOrder.getOrderNo()));
//		}
//		catch (Exception ex)
//		{
//			ex.printStackTrace();
//			map.put("msg", "出库订单保存失败！");
//		}
		return map;
	}

	public SalesOrder getByOrderNo(String orderNo) 
	{
		return this.salesOrderDao.getByOrderNo(orderNo);
	}

	public List<Object> getSonDealers(String code) 
	{
		return this.salesOrderDao.getSonDealers(code);
	}

	public int getCountByDealer(int dealerId, String fmtm, String totm, String searchParam) 
	{
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("searchParam", "%" + searchParam + "%");
		map.put("dealerId", String.valueOf(dealerId));
		map.put("fmtm", fmtm);
		map.put("totm", totm);
		return this.salesOrderDao.getCountByDealer(map);
	}

	public List<SalesOrder> getCountByDealer(int dealerId, String fmtm, String totm, String searchParam, Pageable pageable) 
	{
		int begin = pageable.getOffset() + 1;
		int end = pageable.getOffset() + pageable.getLimit();
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("searchParam", "%" + searchParam + "%");
		map.put("dealerId", String.valueOf(dealerId));
		map.put("begin", String.valueOf(begin));
		map.put("end", String.valueOf(end));
		map.put("fmtm", fmtm);
		map.put("totm", totm);
		
		return this.salesOrderDao.getByDealer(map);
	}

	public List<SaleOrderDetail> geByOrderId(String orderId)
	{
		return this.orderDetailDao.getByOrderId(orderId);
	}

	public SalesOrder getByKingId(String orderNo) 
	{
		return this.salesOrderDao.getByKingId(orderNo);
	}

	public List<Object> getSalesReport(String fmTm, String toTm, String searchParam, int companyId, Pageable pageable) 
	{
		int begin = pageable.getOffset() + 1;
		int end = pageable.getOffset() + pageable.getLimit();
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("searchParam", "%" + searchParam + "%");
		map.put("begin", String.valueOf(begin));
		map.put("end", String.valueOf(end));
		map.put("companyId", String.valueOf(companyId));
		map.put("fmTm", fmTm);
		map.put("toTm", toTm);
		
		return this.salesOrderDao.getSalesReport(map);
	}

	public int getReportConut(String fmTm, String toTm, String searchParam, int companyId) 
	{
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("searchParam", "%" + searchParam + "%");
		map.put("companyId", String.valueOf(companyId));
		map.put("fmTm", fmTm);
		map.put("toTm", toTm);
		
		return this.salesOrderDao.getReportConut(map);
	}

	public List<Object> reportList(String fmTm, String toTm, String unitNo, String type, int companyId, Pageable pageable) 
	{
		int begin = pageable.getOffset() + 1;
		int end = pageable.getOffset() + pageable.getLimit();
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("begin", String.valueOf(begin));
		map.put("end", String.valueOf(end));
		map.put("companyId", String.valueOf(companyId));
		map.put("unitNo", unitNo);
		map.put("type", type);
		map.put("fmTm", fmTm);
		map.put("toTm", toTm);
		
		return this.salesOrderDao.reportList(map);
	}

	public int reportListCount(String fmTm, String toTm, String unitNo, String type, int companyId) 
	{
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("companyId", String.valueOf(companyId));
		map.put("unitNo", unitNo);
		map.put("type", type);
		map.put("fmTm", fmTm);
		map.put("toTm", toTm);
		
		return this.salesOrderDao.reportListCount(map);
	}

	public ArrayList<Object> exportSales(String fmTm, String toTm, String searchParam, int companyId) 
	{

		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("companyId", String.valueOf(companyId));
		map.put("searchParam", searchParam);
		map.put("fmTm", fmTm);
		map.put("toTm", toTm);
		
		return this.salesOrderDao.exportSales(map);
	}

	public ArrayList<Object> exportOrders(String fmTm, String toTm, String unitNo, int companyId, int type) 
	{
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("companyId", String.valueOf(companyId));
		map.put("unitNo", unitNo);
		map.put("fmTm", fmTm);
		map.put("toTm", toTm);
		map.put("type", String.valueOf(type));
		
		return this.salesOrderDao.exportOrders(map);
	}

	public List<Object> orderDetail(int orderId, int type)
	{
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("orderId", orderId);
		map.put("type", type);
		return this.salesOrderDao.orderDetail(map);
	}

	public List<Object> qrcodeDetail(int orderId, int productId) 
	{
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("orderId", orderId);
		map.put("productId", productId);
		return this.salesOrderDao.qrcodeDetail(map);
	}

}
