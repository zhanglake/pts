package com.ptsoft.pts.business.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ptsoft.common.base.BaseMybatisDao;
import com.ptsoft.pts.business.model.vo.SaleOrderDetail;

@Repository
public class SaleOrderDetailDao extends BaseMybatisDao<SaleOrderDetail, Integer> 
{

	@Override
	protected String getMybatisMapperPrefix()
	{
		return "SaleOrderDetail";
	}

	@Override
	public List<SaleOrderDetail> findAll() 
	{
		return null;
	}

	public List<Object> getDetailByOrderId(String orderId) 
	{
		return this.getSqlSession().selectList("SaleOrderDetail_getDetailByOrderId", orderId);
	}

	public List<SaleOrderDetail> getByOrderNo(String orderNo) 
	{
		return this.getSqlSession().selectList("SaleOrderDetail_getByOrderNo", orderNo);
	}

	public List<SaleOrderDetail> getByOrderId(String orderId)
	{
		return this.getSqlSession().selectList("SalesOrderDetail_getByOrderId", orderId);
	}

	public void deleteByOrderId(int orderId) 
	{
		this.getSqlSession().delete("SalesOrderDetail_deleteByOrderId", orderId);
	}


}
