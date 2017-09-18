package com.ptsoft.pts.business.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptsoft.common.base.BaseService;
import com.ptsoft.common.base.EntityDao;
import com.ptsoft.pts.business.dao.ScanDao;
import com.ptsoft.pts.business.model.vo.ScanRecord;

@Service
public class ScanRecordService extends BaseService<ScanRecord, Integer> 
{
	@Autowired
	private ScanDao scanDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected EntityDao getEntityDao() 
	{
		return this.scanDao;
	}

	@Override
	public void save(ScanRecord entity) 
	{
		
	}

	public int getCountByComp(String companyId, int actionType) 
	{
		return this.scanDao.getCountByComp(companyId, actionType);
	}

	public int getRecieveCount(int dealerId) 
	{
		return this.scanDao.getRecieveCount(dealerId);
	}

	public List<Object> getPointDetails(String userId, String fmTm, String toTm) 
	{
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("userId", userId);
		map.put("fmTm", fmTm);
		map.put("toTm", toTm);
		
		return this.scanDao.getPointDetails(map);
	}

}
