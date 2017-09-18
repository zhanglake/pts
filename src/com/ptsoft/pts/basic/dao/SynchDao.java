package com.ptsoft.pts.basic.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ptsoft.common.base.BaseMybatisDao;
import com.ptsoft.pts.basic.model.vo.Synch;

@Repository
public class SynchDao extends BaseMybatisDao<Synch, Integer>
{

	@Override
	protected String getMybatisMapperPrefix() 
	{
		return "Synch";
	}

	@Override
	public List<Synch> findAll()
	{
		return null;
	}

	public Synch getMaxByType(int syncType)
	{
		return (Synch) this.getSqlSession().selectOne("Synch_getMaxByType", syncType);
	}

}
