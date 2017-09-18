package com.ptsoft.pts.system.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ptsoft.common.base.BaseMybatisDao;
import com.ptsoft.pts.system.model.vo.SysLog;

@Repository
public class SysLogDao extends BaseMybatisDao<SysLog, java.lang.Integer>
{
	/**
	 * SQL前缀
	 * */
	public String getMybatisMapperPrefix()
	{
		return "SysLog";
	}
	
	public List<SysLog> getLogs(SysLog log)
	{
		return this.getSqlSession().selectList("getLogs", log);
	}
	
	public List<SysLog> findAll()
	{
		return null;
	}

	public List<SysLog> findByLogTp(HashMap<Object, Object> map) 
	{
		return this.getSqlSession().selectList("SysLog_findByLogTp", map);
	}

	public int getCountByTp(HashMap<Object, Object> map) {
		return (Integer) this.getSqlSession().selectOne("SysLog_getCountByTp", map);
	}

	public List<SysLog> getListXls(HashMap<Object, Object> map) 
	{
		return this.getSqlSession().selectList("SysLog_getListXls", map);
	}
}
