package com.ptsoft.pts.system.dao;

import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.ptsoft.common.base.BaseMybatisDao;
import com.ptsoft.pts.system.model.vo.SysActionFunctionMap;

@Repository
public class SysActionFunctionMapDao extends BaseMybatisDao<SysActionFunctionMapDao, java.lang.Integer>
{
	@Override
	protected String getMybatisMapperPrefix()
	{
		return "SysActionFunctionMap";
	}

	@Override
	public List<SysActionFunctionMapDao> findAll()
	{
		return null;
	}

	public List<SysActionFunctionMap> getRoleActionFunctionMap(String rlId)
	{
		return this.getSqlSession().selectList("getRoleActionFunctionMap", rlId);
	}

	public List<SysActionFunctionMap> getRoleActionFunctionMap(HashMap<String, String> map) {
		
		return this.getSqlSession().selectList("getRoleActionFunctionMapByFunction", map);
	}

}
