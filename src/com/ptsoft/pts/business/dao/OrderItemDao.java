package com.ptsoft.pts.business.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ptsoft.common.base.BaseMybatisDao;
import com.ptsoft.pts.business.model.vo.OrderItem;

@Repository
public class OrderItemDao extends BaseMybatisDao<OrderItem, Integer>{

	@Override
	protected String getMybatisMapperPrefix() {
		return "OrderItem";
	}

	@Override
	public List<OrderItem> findAll() {
		return null;
	}

	public List<OrderItem> findByOrderId(int orderId) {
		return this.getSqlSession().selectList("OrderItem_findByOrderId", orderId);
	}

	public void deleteByOrderId(int orderId) {
		this.getSqlSession().delete("OrderItem_deleteByOrderId", orderId);
	}

	public List<Object> findBySupplierID(Map<String, String> map) 
	{
		return this.getSqlSession().selectList("OrderItem_findBySupplierID", map);
	}
	public int getCountBySupplierId(HashMap<Object, Object> map) 
	{
		return (Integer) this.getSqlSession().selectOne("OrderItem_getCountBySupplierId", map);
	}

	public ArrayList<Object> exportBySupplierId(Map<String, String> map)
	{
		return (ArrayList<Object>) this.getSqlSession().selectList("OrderItem_exportBySupplierId", map);
	}

}
