package com.ptsoft.pts.business.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ptsoft.common.base.BaseMybatisDao;
import com.ptsoft.pts.business.model.vo.PackageRuleMap;

@Repository
public class PackageRuleMapDao extends BaseMybatisDao<PackageRuleMap, Integer>
{

	@Override
	protected String getMybatisMapperPrefix() 
	{
		return "PackageRuleMap";
	}

	@Override
	public List<PackageRuleMap> findAll()
	{
		return null;
	}

	public int getByRuleIDAndPkgID(int ruleId, int packageId) 
	{
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("ruleId", ruleId);
		map.put("packageId", packageId);
		
		return (Integer) this.getSqlSession().selectOne("PackageRuleMap_getByRuleIDAndPkgID", map);
	}

	public void deleteByRuleAndPkg(Integer ruleId, Integer pkgId) 
	{
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("ruleId", ruleId);
		map.put("pkgId", pkgId);
		
		this.getSqlSession().delete("PackageRuleMap_deleteByRuleAndPkg", map);
	}

	public List<HashMap<String, Object>> getByRuleId(Integer ruleId) 
	{
		return this.getSqlSession().selectList("PackageRuleMap_getByRuleId", ruleId);
	}

	public List<PackageRuleMap> getRuleMap(int ruleId) 
	{
		return this.getSqlSession().selectList("PackageRuleMap_getRuleMap", ruleId);
	}

	public List<HashMap<String, Object>> getByRuleIdForApi(int ruleId) 
	{
		return this.getSqlSession().selectList("PackageRuleMap_getByRuleIdForApi", ruleId);
	}
}
