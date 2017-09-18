package com.ptsoft.pts.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ptsoft.common.base.BaseMybatisDao;
import com.ptsoft.pts.system.model.vo.AppLog;

@Repository
public class AppLogDao extends BaseMybatisDao<AppLog, Integer> 
{

	@Override
	protected String getMybatisMapperPrefix() 
	{
		return "AppLog";
	}

	@Override
	public List<AppLog> findAll() 
	{
		return null;
	}

}
