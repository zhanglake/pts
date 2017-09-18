package com.ptsoft.pts.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ptsoft.common.base.BaseMybatisDao;
import com.ptsoft.pts.system.model.vo.SysArea;

@Repository
public class AreaDao extends BaseMybatisDao<SysArea, java.lang.Integer>{

	@Override
	protected String getMybatisMapperPrefix() {
		return "SysArea";
	}

	@Override
	public List<SysArea> findAll() {
		return this.getSqlSession().selectList("findAll", null);
	}
	
	public List<SysArea> findRoots(Integer count) {
		return this.getSqlSession().selectList("findRoots", count);
	}

	public List<SysArea> findChildren(Integer parentId) {
		return this.getSqlSession().selectList("findChildren", parentId);
	}
}
