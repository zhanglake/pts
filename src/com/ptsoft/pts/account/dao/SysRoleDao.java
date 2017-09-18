/**
 * 
 */
package com.ptsoft.pts.account.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ptsoft.common.base.BaseMybatisDao;
import com.ptsoft.pts.account.model.vo.SysRole;

/**
 * 角色操作DAO类
 */
@Repository
public class SysRoleDao extends BaseMybatisDao<SysRole, java.lang.Integer>
{
	@Override
	protected String getMybatisMapperPrefix()
	{
		return "SysRole";
	}
	@Override
	public List<SysRole> findAll() {
		return this.getSqlSession().selectList("findAllRole");
	}
	
	public List<Object> findRoleAll(HashMap<String, String> map) 
	{
		return this.getSqlSession().selectList("findAllRole", map);
	}
	
	public int getRoleCount()
	{
		return (Integer) this.getSqlSession().selectOne("getRoleListCount");
	}
	public List<SysRole> findByType(int type) 
	{
		return this.getSqlSession().selectList("SysRole_findByType", type);
	}

	
}
