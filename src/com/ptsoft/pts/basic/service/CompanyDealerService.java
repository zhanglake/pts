package com.ptsoft.pts.basic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptsoft.common.base.BaseService;
import com.ptsoft.common.base.EntityDao;
import com.ptsoft.pts.basic.dao.CompanyDealerDao;
import com.ptsoft.pts.basic.model.vo.CompanyDealerMap;

@Service
public class CompanyDealerService extends BaseService<CompanyDealerMap, Integer> {

	@Autowired
	private CompanyDealerDao cdDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected EntityDao getEntityDao() {
		return this.cdDao;
	}

	@Override
	public void save(CompanyDealerMap entity) 
	{
	}

	public String getByCompanyId(String cid) 
	{
		if(cid != null && cid != "" && Integer.parseInt(cid) != 0)
		{

			List<CompanyDealerMap> list = this.cdDao.findByCompanyId(Integer.parseInt(cid));
			String data = "";
			for (CompanyDealerMap map : list) {
				data += map.getDealerId() + ",";
			}
			return data;
		}
		else
		{
			return "";
		} 
	}

	public void distributeDealer(String companyId, String[] dealers) 
	{
		CompanyDealerMap cdMap;
		
		for (String dealerId : dealers) 
		{
			if (dealerId.equals("")) continue;
			
			cdMap = new CompanyDealerMap();
			cdMap.setCompanyId(Integer.parseInt(companyId));
			cdMap.setDealerId(Integer.parseInt(dealerId));
			
			this.cdDao.insert(cdMap);
		}
	}

	public void distributeCompany(String dealerId, String[] companies) 
	{
		CompanyDealerMap cdMap;

		for (String compId : companies) 
		{
			if (compId.equals("")) 
			{
				continue;
			}
			
			cdMap = new CompanyDealerMap();
			cdMap.setCompanyId(Integer.parseInt(compId));
			cdMap.setDealerId(Integer.parseInt(dealerId));
			
			this.cdDao.insert(cdMap);
		}
	}

	public void cancelDistribute(String dealerId, String[] companies) 
	{
		for (String compId : companies) 
		{
			if (compId.equals("")) 
			{
				continue;
			}
			
			this.cdDao.cancelDistribute(dealerId, compId);
		}
	}

	public void cancelByCompId(String compId, String[] dealers) 
	{
		for (String dealerId : dealers) 
		{
			if (dealerId.equals("")) 
			{
				continue;
			}
			
			this.cdDao.cancelDistribute(dealerId, compId);
		}
	}

}
