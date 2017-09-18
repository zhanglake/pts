package com.ptsoft.pts.basic.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ptsoft.common.base.BaseMybatisDao;
import com.ptsoft.pts.basic.model.vo.Stock;

@Repository
public class StockDao extends BaseMybatisDao<Stock, Integer> {

	@Override
	protected String getMybatisMapperPrefix() {
		return "Stock";
	}

	@Override
	public List<Stock> findAll() {
		return this.getSqlSession().selectList("Stock_findAll");
	}

	public List<Stock> findByCompanyId(Integer companyId) {
		return this.getSqlSession().selectList("Stock_findByCompanyId", companyId);
	}

	public List<Stock> findByCompIdAndSearch(HashMap<String, String> map) {
		return this.getSqlSession().selectList("Stock_findByCompIdAndSearch", map);
	}
	
	public int getstockCount(HashMap<String, String> map) {
		return (Integer) this.getSqlSession().selectOne("Stock_getstockCount", map);
	}

	public int getByStockId(Integer id) {
		return (Integer) this.getSqlSession().selectOne("Stock_getByStockId", id);
	}

	public Stock getByCode(String code) {
		return (Stock) this.getSqlSession().selectOne("Stock_getByCode", code);
	}
}
