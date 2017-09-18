package com.ptsoft.pts.account.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.ptsoft.common.base.BaseMybatisDao;
import com.ptsoft.pts.account.model.vo.SysUserPDA;

@Repository
public class SysUserPdaDao extends BaseMybatisDao<SysUserPDA, java.lang.Integer>
{

	@Override
	protected String getMybatisMapperPrefix() 
	{
		return "SysUserPDA";
	}

	@Override
	public List<SysUserPDA> findAll()
	{
		return null;
	}

	public List<String> getByUserId(int usrId) 
	{
		return this.getSqlSession().selectList("SysUserPDA_getById", usrId);
	}

	public void deleteByUserId(int usrId) 
	{
		this.getSqlSession().delete("SysUserPDA_deleteById", usrId);
	}

}
