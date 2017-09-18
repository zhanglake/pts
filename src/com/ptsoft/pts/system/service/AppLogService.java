package com.ptsoft.pts.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptsoft.common.base.BaseService;
import com.ptsoft.common.base.EntityDao;
import com.ptsoft.pts.system.dao.AppLogDao;
import com.ptsoft.pts.system.model.vo.AppLog;

@Service
public class AppLogService extends BaseService<AppLog, Integer> 
{

	@Autowired
	private AppLogDao appLogDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected EntityDao getEntityDao()
	{
		return this.appLogDao;
	}

	@Override
	public void save(AppLog entity)
	{
		this.appLogDao.insert(entity);
	}

}
