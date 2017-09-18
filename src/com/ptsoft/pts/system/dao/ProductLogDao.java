package com.ptsoft.pts.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ptsoft.common.base.BaseMybatisDao;
import com.ptsoft.pts.system.model.vo.ProductLog;

@Repository
public class ProductLogDao extends BaseMybatisDao<ProductLog, Integer> 
{

	@Override
	protected String getMybatisMapperPrefix() 
	{
		return "ProductLog";
	}

	@Override
	public List<ProductLog> findAll() 
	{
		return null;
	}

	public List<ProductLog> getUpdateRecord(int productId) 
	{
		return this.getSqlSession().selectList("ProductLog_getUpdateRecord", productId);
	}

}
