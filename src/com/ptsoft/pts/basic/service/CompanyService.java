package com.ptsoft.pts.basic.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptsoft.common.base.BaseService;
import com.ptsoft.common.base.EntityDao;
import com.ptsoft.common.util.Pageable;
import com.ptsoft.pts.basic.dao.CompanyDao;
import com.ptsoft.pts.basic.model.vo.Company;

@Service
public class CompanyService extends BaseService<Company, Integer> {

	@Autowired
	private CompanyDao compDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected EntityDao getEntityDao() {
		return compDao;
	}

	@Override
	public void save(Company company) 
	{
		
	}
	public int getCompanyCount(String searchItems){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("searchItems", "%" + searchItems + "%");
		return this.compDao.getCompanyCountBySearchItems(map);
	}
	
	public List<Object> findBySearchItems(String searchItems,Pageable pageable) 
	{
		int begin = pageable.getOffset() + 1;
		int end = pageable.getOffset() + pageable.getLimit();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("searchItems", "%" + searchItems + "%");
		map.put("begin", String.valueOf(begin));
		map.put("end", String.valueOf(end));		
		return this.compDao.findBySearchItems(map);
	}

	public String saveCompany(Company company)
	{
		String msg = null;
		int num = this.compDao.getByCid(company.getId());
		Company comp = this.compDao.getByCode(company.getCompany_code());
		
		if(comp != null && comp.getId() != company.getId())
			return "企业编码已存在，请重新填写！";
		
		try
		{
			if (num > 0)
			{
				this.compDao.update(company);
				msg = "企业信息更新成功！";
			}
			else
			{
				//新增企业基础数据
				this.compDao.insert(company);
				msg = "企业信息保存成功！";
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			msg = "企业信息保存失败！";
		}
		return msg;
	}

	public List<Company> notInCompanies(String searchItems, String dealerId) 
	{
		return this.compDao.notInCompanies(searchItems, dealerId);
	}

	public List<Company> findHasCompanies(String searchItems, String dealerId) 
	{
		return this.compDao.findHasCompanies(searchItems, dealerId);
	}

}
