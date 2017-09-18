package com.ptsoft.pts.system.dao;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.ptsoft.common.base.BaseMybatisDao;
import com.ptsoft.pts.system.model.vo.SysAction;

@Repository
public class SysActionDao extends BaseMybatisDao<SysAction, java.lang.Integer>
{
	/**
	 * SQL前缀
	 * */
	public String getMybatisMapperPrefix()
	{
		return "SysAction";
	}
	
	public List<SysAction> getUserActions(Map<String, String> map)
	{
		return this.getSqlSession().selectList("getUserActions", map);
	}

	public List<SysAction> findAll()
	{
		return this.getSqlSession().selectList("getFunctionList", null);
	}
}
