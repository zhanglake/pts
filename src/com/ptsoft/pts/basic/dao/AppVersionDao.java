package com.ptsoft.pts.basic.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ptsoft.common.base.BaseMybatisDao;
import com.ptsoft.pts.basic.model.vo.AppVersion;

@Repository
public class AppVersionDao extends BaseMybatisDao<AppVersion, Integer>
{

	@Override
	protected String getMybatisMapperPrefix() 
	{
		return "AppVersion";
	}

	@Override
	public List<AppVersion> findAll() 
	{
		return null;
	}

	public AppVersion checkUpdate(HashMap<String, String> map) 
	{
		return (AppVersion) this.getSqlSession().selectOne("AppVersion_checkUpdate", map);
	}

}
