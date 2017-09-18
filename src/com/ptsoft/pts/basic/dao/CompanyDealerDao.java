package com.ptsoft.pts.basic.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ptsoft.common.base.BaseMybatisDao;
import com.ptsoft.pts.basic.model.vo.CompanyDealerMap;

@Repository
public class CompanyDealerDao extends BaseMybatisDao<CompanyDealerMap, Integer> {

	@Override
	protected String getMybatisMapperPrefix() {
		return "CompanyDealer";
	}

	@Override
	public List<CompanyDealerMap> findAll() {
		return null;
	}
	
	public List<CompanyDealerMap> findByCompanyId(Integer companyId){
		
		return this.getSqlSession().selectList("CompanyDealer_getByCompanyId", companyId);
	}

	public CompanyDealerMap findByCompIdAndDealerId(Integer companyId, Integer dealerId) 
	{
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("companyId", companyId);
		map.put("dealerId", dealerId);
		return (CompanyDealerMap) this.getSqlSession().selectOne("CompanyDealer_getByCompanyIdAndDealerId", map);
	}

	/**
	 * 根据company id删除经销商与企业map关系
	 * @author jqi.can
	 * @date 2016-2-2下午02:55:39
	 */
	public void deleteByCompanyId(String companyId) 
	{
		this.getSqlSession().delete("CompanyDealer_deleteByCompanyId", companyId);
	}

	public void deleteByDealerId(String dealerId) 
	{
		this.getSqlSession().delete("CompanyDealer_deleteByDealerId", dealerId);
	}

	public void cancelDistribute(String dealerId, String compId) 
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("compId", compId);
		map.put("dealerId", dealerId);
		
		this.getSqlSession().delete("CompanyDealer_cancelDistribute", map);
	}

}
