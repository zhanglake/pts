package com.ptsoft.pts.basic.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ptsoft.common.base.BaseMybatisDao;
import com.ptsoft.pts.basic.model.vo.Dealer;

@Repository
public class DealerDao extends BaseMybatisDao<Dealer, Integer> {

	@Override
	protected String getMybatisMapperPrefix() 
	{
		return "Dealer";
	}

	@Override
	public List<Dealer> findAll() {
		return this.getSqlSession().selectList("Dealer_findAll");
	}

	public List<Object> findBySearchParam(HashMap<String, String> map) 
	{
		return this.getSqlSession().selectList("Dealer_findBySearchParam",map);
	}
	
	public int getdealerCountBySearchParam(HashMap<String, String> map){
		return (Integer) this.getSqlSession().selectOne("getdealerCount", map);
	}
	
	public List<Dealer> findByCompanyId(Integer companyId){
		return this.getSqlSession().selectList("Dealer_findByCompanyID", companyId);
	}

	public int getByDealId(int id) 
	{
		return (Integer) this.getSqlSession().selectOne("Dealer_getByDealId", id);
	}

	public Dealer getByCode(String dealer_code) 
	{
		return (Dealer) this.getSqlSession().selectOne("Dealer_getByCode", dealer_code);
	}

	public List<Object> findByCompId(Map<Object, Object> map) 
	{
		return this.getSqlSession().selectList("Dealer_findByCompIdAndSearch", map);
	}

	public Dealer getByRevenueNo(String revenueNO)
	{
		return (Dealer) this.getSqlSession().selectOne("Dealer_getByRevenueNo", revenueNO);
	}

	public List<Dealer> getHasDealers(Map<String, String> map)
	{
		return this.getSqlSession().selectList("Dealer_getHasDealers", map);
	}

	public List<Dealer> notInDealers(Map<String, String> map)
	{
		return this.getSqlSession().selectList("Dealer_notInDealers", map);
	}
	
	public int inCount(Map<String, String> map) 
	{
		return (Integer) this.getSqlSession().selectOne("Dealer_inCount", map);
	}

	public int notInCount(Map<String, String> map)
	{
		return (Integer) this.getSqlSession().selectOne("Dealer_notInCount", map);
	}
	
	public int getDealerCount(String companyId) 
	{
		return (Integer) this.getSqlSession().selectOne("Dealer_getDealerCount", companyId);
	}

	public int getDealersCount(HashMap<Object, Object> map) 
	{
		return (Integer) this.getSqlSession().selectOne("Dealer_getDealersCount", map);
	}

	public List<Object> dealerPercent(int compId) 
	{
		return this.getSqlSession().selectList("Dealer_percent", compId);
	}

	public Dealer getCompanyIdAndId(HashMap<Object, Object> map) 
	{
		return (Dealer) this.getSqlSession().selectOne("Dealer_getCompanyIdAndId", map);
	}

}