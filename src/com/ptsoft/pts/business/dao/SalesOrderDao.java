package com.ptsoft.pts.business.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ptsoft.common.base.BaseMybatisDao;
import com.ptsoft.pts.business.model.vo.SalesOrder;

@Repository
public class SalesOrderDao extends BaseMybatisDao<SalesOrder, Integer> 
{

	@Override
	protected String getMybatisMapperPrefix() 
	{
		return "SalesOrder";
	}

	@Override
	public List<SalesOrder> findAll() 
	{
		return null;
	}

	public List<SalesOrder> getByParam(HashMap<String, String> map) 
	{
		return this.getSqlSession().selectList("SalesOrder_getByParam", map);
	}

	public int getSizeByParam(HashMap<String, String> map) 
	{
		return (Integer) this.getSqlSession().selectOne("SalesOrder_getSizeByParam", map);
	}

	public SalesOrder getByOrderNo(String orderNo) {
		return (SalesOrder) this.getSqlSession().selectOne("SalesOrder_getByOrderNo", orderNo);
	}

	public void updateByOrderNo(String orderNo, int status) 
	{
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("orderNo", orderNo);
		map.put("status", String.valueOf(status));
		
		this.getSqlSession().update("SalseOrder_updateByOrderNo", map);
	}

	public SalesOrder getByKingId(String kingId) 
	{
		return (SalesOrder) this.getSqlSession().selectOne("SalesOrder_getByKingId", kingId);
	}

	public List<Object> getSonDealers(String prntCode) 
	{
		return this.getSqlSession().selectList("SalesOrder_getSonDealers", prntCode);
	}

	public int getCountByDealer(HashMap<String, String> map) 
	{
		return (Integer) this.getSqlSession().selectOne("SalesOrder_getCountByDealer", map);
	}

	public List<SalesOrder> getByDealer(HashMap<String, String> map) 
	{
		return this.getSqlSession().selectList("SalesOrder_getByDealer", map);
	}

	public List<SalesOrder> getToUpdate(HashMap<String, Integer> map) 
	{
		return this.getSqlSession().selectList("SalesOrder_getToUpdate", map);
	}

	public void updateOrderNo(SalesOrder order)
	{
		this.getSqlSession().update("SalesOrder_updateOrderNo", order);
	}

	public void updateById(int orderId, int status)
	{
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("orderId", orderId);
		map.put("status", status);
		
		this.getSqlSession().update("SalseOrder_updateById", map);
	}

	public String getMaxKingId(String orderDate) 
	{
		return (String) this.getSqlSession().selectOne("SalesOrder_getMaxKingId", orderDate);
	}

	public SalesOrder getOutInfo(String qrCode)
	{
		return (SalesOrder) this.getSqlSession().selectOne("SalesOrder_getOutInfo", qrCode);
	}

	public List<Object> getSalesReport(HashMap<String, String> map) 
	{
		return this.getSqlSession().selectList("SalesOrder_getSalesReport", map);
	}

	public int getReportConut(HashMap<String, String> map)
	{
		return (Integer) this.getSqlSession().selectOne("SalesOrder_getReportConut", map);
	}

	public List<Object> reportList(HashMap<String, String> map)
	{
		return this.getSqlSession().selectList("SalesOrder_reportList", map);
	}

	public int reportListCount(HashMap<String, String> map) 
	{
		return (Integer) this.getSqlSession().selectOne("SalesOrder_reportListCount", map);
	}

	public ArrayList<Object> exportSales(HashMap<String, String> map) 
	{
		return (ArrayList<Object>) this.getSqlSession().selectList("SalesOrder_exportSales", map);
	}

	public ArrayList<Object> exportOrders(HashMap<String, String> map)
	{
		return (ArrayList<Object>) this.getSqlSession().selectList("SalesOrder_exportOrders", map);
	}

	public List<Object> orderDetail(HashMap<String, Integer> map)
	{
		return this.getSqlSession().selectList("SalesOrder_orderDetail", map);
	}

	public List<Object> qrcodeDetail(HashMap<String, Integer> map) 
	{
		return this.getSqlSession().selectList("SalesOrder_qrcodeDetail", map);
	}

}
