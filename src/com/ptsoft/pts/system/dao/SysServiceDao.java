package com.ptsoft.pts.system.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.ptsoft.common.base.BaseMybatisDao;
import com.ptsoft.pts.system.model.vo.SysService;

@Repository
public class SysServiceDao extends BaseMybatisDao<SysService, String>
{
	/**
	 * SQL前缀
	 * */
	public String getMybatisMapperPrefix()
	{
		return "SysService";
	}

	@Override
	public List<SysService> findAll()
	{
		return this.getSqlSession().selectList("SysService_findall");
	}
}
