package com.ptsoft.pts.business.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ptsoft.common.base.BaseMybatisDao;
import com.ptsoft.pts.business.model.vo.SyPackage;

@Repository
public class PackageDao extends BaseMybatisDao<SyPackage, Integer> 
{

	@Override
	protected String getMybatisMapperPrefix() 
	{
		return "Package";
	}

	@Override
	public List<SyPackage> findAll()
	{
		return null;
	}

	public List<Object> findByCompIdAndLike(HashMap<Object,Object> map)
	{
		return this.getSqlSession().selectList("Package_findByCompIdAndLike", map);
	}

	public int getByIdForNum(int id)
	{
		return (Integer) this.getSqlSession().selectOne("Package_getByIdForNum", id);
	}

	public SyPackage getByCode(String code) 
	{
		return (SyPackage) this.getSqlSession().selectOne("Package_getByCode", code);
	}

	public List<SyPackage> findAllBySts(int sts) 
	{
		return this.getSqlSession().selectList("Package_findAllBySts", sts);
	}

	public int getByIDCanUpdateSts(int pkgId)
	{
		return (Integer) this.getSqlSession().selectOne("Package_getByIDCanUpdateSts", pkgId);
	}

	public int getNumUnAvaliable(int ruleId) 
	{
		return (Integer) this.getSqlSession().selectOne("Package_getNumUnAvaliable", ruleId);
	}

	public int getNumByQRSize(String dctcd)
	{
		return (Integer) this.getSqlSession().selectOne("Package_getNumByQRSize", dctcd);
	}
	
	public List<Object> getByRuleID(Integer ruleId) 
	{
		return this.getSqlSession().selectList("Package_getByRuleID", ruleId);
	}

	public SyPackage findMinPackage(int packageID) 
	{
		return (SyPackage) this.getSqlSession().selectOne("Package_findMinPackage", packageID);
	}

	public int getCountByCompIdAndSearchParam(HashMap<Object, Object> map) {
		return (Integer) this.getSqlSession().selectOne("Package_getCountByCompIdAndSearch", map);
	}
}
