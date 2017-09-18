package com.ptsoft.pts.account.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptsoft.common.base.BaseService;
import com.ptsoft.common.base.EntityDao;
import com.ptsoft.pts.account.dao.SysUserPdaDao;
import com.ptsoft.pts.account.model.vo.SysUserPDA;

@Service
public class UserPdaService extends BaseService<SysUserPDA, java.lang.Integer>
{

	@Autowired
	private SysUserPdaDao userPdaDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected EntityDao getEntityDao()
	{
		return this.userPdaDao;
	}

	@Override
	public void save(SysUserPDA entity) 
	{
	}

	public List<String> getByUserId(int usrId)
	{
		return this.userPdaDao.getByUserId(usrId);
	}

}
