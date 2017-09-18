package com.ptsoft.pts.basic.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ptsoft.common.base.BaseMybatisDao;
import com.ptsoft.pts.basic.model.vo.Supplier;

@Repository
public class SupplierDao extends BaseMybatisDao<Supplier, Integer> {

	@Override
	protected String getMybatisMapperPrefix() {
		return "Supplier";
	}

	@Override
	public List<Supplier> findAll() {
		return this.getSqlSession().selectList("Supplier_findAll");
	}
	
	public List<Supplier> findBySearchParam(String searchParam) 
	{
		return this.getSqlSession().selectList("Supplier_findBySearchParam", searchParam);
	}
	
	public List<Supplier> findByCompanyId(Integer companyId){
		return this.getSqlSession().selectList("Supplier_findByCompanyID", companyId);
	}

	public int getBySupplierId(int id) {
		return (Integer) this.getSqlSession().selectOne("Supplier_getBySupplierId", id);
	}

	public Supplier getByCode(String supplier_code) {
		return (Supplier) this.getSqlSession().selectOne("Supplier_getByCode", supplier_code);
	}

	public List<Object> findByCompIdAndSearch(Map<Object, Object> map) {
		return this.getSqlSession().selectList("Supplier_findByCompIdAndSearch", map);
	}
	
	public int getCountByCompIdAndSearch(HashMap<Object, Object> map){
		return (Integer) this.getSqlSession().selectOne("Supplier_getCountByCompIdAndSearch", map);
	}

	public Supplier getByCompanyIdAndId(HashMap<Object, Object> map) 
	{
		return (Supplier) this.getSqlSession().selectOne("Supplier_getByCompanyIdAndId", map);
	}
}
