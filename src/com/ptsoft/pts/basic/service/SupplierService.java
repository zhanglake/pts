package com.ptsoft.pts.basic.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptsoft.common.base.BaseService;
import com.ptsoft.common.base.EntityDao;
import com.ptsoft.common.util.Pageable;
import com.ptsoft.pts.basic.dao.CompanySupplierDao;
import com.ptsoft.pts.basic.dao.SupplierDao;
import com.ptsoft.pts.basic.model.vo.CompanySupplierMap;
import com.ptsoft.pts.basic.model.vo.Supplier;

@Service
public class SupplierService extends BaseService<Supplier, Integer> {

	@Autowired
	private SupplierDao supDao;
	@Autowired
	private CompanySupplierDao csDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected EntityDao getEntityDao() 
	{
		return this.supDao;
	}

	@Override
	public void save(Supplier entity)
	{
		
	}
	
	public List<Supplier> findByCompanyId(Integer companyId){
		return this.supDao.findByCompanyId(companyId);
	}
	
	public List<Object> findByCompAndSearch(Integer companyId, String searchParam, Pageable pageable)
	{
		int begin = pageable.getOffset() + 1;
		int end = pageable.getOffset() + pageable.getLimit();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		
		map.put("searchParam", "%" + searchParam + "%");
		map.put("companyId", companyId);
		map.put("begin", String.valueOf(begin));
		map.put("end", String.valueOf(end));		
		
		return this.supDao.findByCompIdAndSearch(map);
	}
	
	public int findCountByCompIdAndSearch(Integer companyId,String searchParam)
	{
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		
		map.put("searchParam", "%" + searchParam + "%");
		map.put("companyId", companyId);
		
		return this.supDao.getCountByCompIdAndSearch(map);
	}
	
	public String saveSupplier(Supplier entity)
	{
		String msg = null;
		int num = this.supDao.getBySupplierId(entity.getId());
		Supplier supp = this.supDao.getByCode(entity.getSupplier_code());
		
		if(supp != null && supp.getId() != entity.getId())
			return "生产供应商编码已存在，请重新填写！";
		
		try
		{
			if (num > 0)
			{
				this.supDao.update(entity);
				msg = "生产供应商信息更新成功！";
			}
			else
			{
				//新增经销商基础数据
				this.supDao.insert(entity);
				msg = "生产供应商信息保存成功！";
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			msg = "生产供应商信息保存失败！";
		}
		return msg;
	}
	
	public String saveCompanySupplier(Integer companyId, Supplier supplier)
	{
		String msg = this.saveSupplier(supplier);
		Supplier entity = supDao.getByCode(supplier.getSupplier_code());
		HashMap<String, Integer> map = new HashMap<String, Integer>();
	
		map.put("companyId", companyId);
		map.put("supplierId", entity.getId());
		CompanySupplierMap cs = csDao.findByCompIdAndSupplierId(map);
		
		if(cs == null)
		{
			cs = new CompanySupplierMap();
			cs.setCompanyId(companyId);
			cs.setSupplierId(entity.getId());
			cs.setInnerCode(supplier.getInnerCode());
			csDao.insert(cs);
		}
		else
		{
			cs.setInnerCode(supplier.getInnerCode());
			csDao.update(cs);
		}
		
		return msg;
	}

	public Supplier findByCode(String code) 
	{
		return this.supDao.getByCode(code);
	}

	public Supplier getByCompanyIdAndId(int companyId, int supplierId) 
	{

		HashMap<Object, Object> map = new HashMap<Object, Object>();
		
		map.put("supplierId", supplierId);
		map.put("companyId", companyId);
		
		return this.supDao.getByCompanyIdAndId(map);
	}

}
