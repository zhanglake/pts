package com.ptsoft.pts.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ptsoft.common.base.BaseMybatisDao;
import com.ptsoft.pts.system.model.vo.SysFunction;

@Repository
public class SysFunctionDao extends BaseMybatisDao<SysFunction, java.lang.Integer>
{
	/**
	 * SQL前缀
	 * */
	public String getMybatisMapperPrefix()
	{
		return "SysFunction";
	}
	
	public List<SysFunction> getUserFunctions(int rlId)
	{
		return this.getSqlSession().selectList("getUserFunctions", rlId);
	}
	
	public List<SysFunction> getSysFunctions(String rlId) 
	{
		return this.getSqlSession().selectList("getSysFunctions", rlId);
	}

	@Override
	public List<SysFunction> findAll()
	{
		return null;
	}
}
