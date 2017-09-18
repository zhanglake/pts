package com.ptsoft.pts.basic.dao;

import java.util.HashMap;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.ptsoft.common.base.BaseMybatisDao;
import com.ptsoft.pts.basic.model.vo.CompanySupplierMap;

@Repository
public class CompanySupplierDao extends BaseMybatisDao<CompanySupplierMap, Integer> {

	@Override
	protected String getMybatisMapperPrefix() {
		return "CompanySupplier";
	}

	@Override
	public List<CompanySupplierMap> findAll() {
		return null;
	}
	
	public List<CompanySupplierMap> findByCompanyId(Integer companyId){
		return this.getSqlSession().selectList("CompanySupplier_getByCompanyId", companyId);
	}

	public CompanySupplierMap findByCompIdAndSupplierId(
			HashMap<String, Integer> map) {
		return (CompanySupplierMap) this.getSqlSession().selectOne("CompanySupplier_getByCompIdAndSupplierId", map);
	}

}
