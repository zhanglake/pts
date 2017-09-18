package com.ptsoft.pts.basic.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ptsoft.common.base.BaseMybatisDao;
import com.ptsoft.pts.basic.model.vo.Company;

@Repository
public class CompanyDao extends BaseMybatisDao<Company, Integer> {

	@Override
	protected String getMybatisMapperPrefix() {
		return "Company";
	}

	@Override
	public List<Company> findAll() {
		return this.getSqlSession().selectList("Company_findAll");
	}

	public List<Object> findBySearchItems(HashMap<String, String> map)
	{
		return this.getSqlSession().selectList("Company_findBySearchItems",map);
	}

	public int getCompanyCountBySearchItems(HashMap<String, String> map){
		return (Integer) this.getSqlSession().selectOne("getCompanyCount", map);
	}
	public int getByCid(int cid) 
	{
		return (Integer) this.getSqlSession().selectOne("Company_getByCid", cid);
	}

	public Company getByCode(String company_code) 
	{
		return (Company) this.getSqlSession().selectOne("Company_getByCode", company_code);
	}

	public List<Company> notInCompanies(String searchItems, String dealerId) 
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("searchItems", "%" + searchItems + "%");
		map.put("dealerId", dealerId);
		
		return this.getSqlSession().selectList("Company_notInCompanies", map);
	}

	public List<Company> findHasCompanies(String searchItems, String dealerId) 
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("searchItems", "%" + searchItems + "%");
		map.put("dealerId", dealerId);
		
		return this.getSqlSession().selectList("Company_findHasCompanies", map);
	}

}
