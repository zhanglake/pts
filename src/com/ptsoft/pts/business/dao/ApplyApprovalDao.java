package com.ptsoft.pts.business.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ptsoft.common.base.BaseMybatisDao;
import com.ptsoft.pts.business.model.vo.ApplyApproval;

@Repository
public class ApplyApprovalDao extends BaseMybatisDao<ApplyApproval, Integer> 
{

	@Override
	protected String getMybatisMapperPrefix() 
	{
		return "ApplyApproval";
	}

	@Override
	public List<ApplyApproval> findAll() 
	{
		return null;
	}

	public List<ApplyApproval> getByParam(HashMap<String, String> map)
	{
		return this.getSqlSession().selectList("ApplyApproval_getByParam", map);
	}

	public int getSizeByParam(HashMap<String, String> map) 
	{
		return (Integer) this.getSqlSession().selectOne("ApplyApproval_getSizeByParam", map);
	}

	public int getSizeByCompanyId(HashMap<String, String> map) 
	{
		return (Integer) this.getSqlSession().selectOne("ApplyApproval_getSizeByCompanyId", map);
	}

	public List<ApplyApproval> getByParamCompId(HashMap<String, String> map)
	{
		return this.getSqlSession().selectList("ApplyApproval_getByParamCompId", map);
	}

	public void updateApply(HashMap<String, Integer> map) 
	{
		this.getSqlSession().update("ApplyApproval_updateApply", map);		
	}
}
