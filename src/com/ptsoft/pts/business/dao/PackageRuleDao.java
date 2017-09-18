package com.ptsoft.pts.business.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ptsoft.common.base.BaseMybatisDao;
import com.ptsoft.pts.business.model.vo.PackageRule;

@Repository
public class PackageRuleDao extends BaseMybatisDao<PackageRule, Integer>
{

	@Override
	protected String getMybatisMapperPrefix() 
	{
		return "PackageRule";
	}

	@Override
	public List<PackageRule> findAll() 
	{
		return null;
	}

	public List<Object> findByCompIdAndLike(HashMap<Object, Object> map)
	{
		return this.getSqlSession().selectList("PackageRule_findByCompIdAndLike", map);
	}

	public int getByIdForNum(int id) 
	{
		return (Integer) this.getSqlSession().selectOne("PackageRule_getByIdForNum", id);
	}

	public PackageRule getByCode(String code)
	{
		return (PackageRule) this.getSqlSession().selectOne("PackageRule_getByCode", code);
	}

	public List<PackageRule> findAllCanUsed(Integer compId) 
	{
		return this.getSqlSession().selectList("PackageRule_findAllCanUsed", compId);
	}

	public int getpackageRuleCountByCompIdAndSearchParam(HashMap<Object, Object> map) {
		return (Integer) this.getSqlSession().selectOne("PackageRuleMap_getCountByCompIdAndSearch", map);
	}

}
