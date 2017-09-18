package com.ptsoft.pts.business.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ptsoft.common.base.BaseMybatisDao;
import com.ptsoft.pts.business.model.vo.Order;

@Repository
public class OrderDao extends BaseMybatisDao<Order, Integer>{

	@Override
	protected String getMybatisMapperPrefix() 
	{
		return "Order";
	}

	@Override
	public List<Order> findAll() 
	{
		return null;
	}

	public List<Order> findByCompanyId(int companyId) 
	{
		return this.getSqlSession().selectList("Order_findByCompanyId", companyId);
	}

	public List<Object> findByCompId(HashMap<Object, Object> map) {
		return this.getSqlSession().selectList("Order_findByCompId", map);
	}
	public int getCountByCompId(HashMap<Object, Object> map)
	{
		return (Integer) this.getSqlSession().selectOne("Order_getCountByCompId", map);
	}

	public int getByOrderId(int id) 
	{
		return (Integer) this.getSqlSession().selectOne("Order_getByOrderId", id);
	}

	public Order getByOrderNo(String orderNo)
	{
		return (Order) this.getSqlSession().selectOne("Order_getByOrderNo", orderNo);
	}

	public Order getByKindId(String kingId) 
	{
		return (Order) this.getSqlSession().selectOne("Order_getByKingId", Integer.parseInt(kingId));
	}

	public void updateStatus(int orderId, int status) 
	{
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("orderId", orderId);
		map.put("status", status);
		
		this.getSqlSession().update("Order_updateStatus", map);
	}

	
}
