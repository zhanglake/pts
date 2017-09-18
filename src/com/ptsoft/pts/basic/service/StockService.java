package com.ptsoft.pts.basic.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptsoft.common.annotation.ServiceLog;
import com.ptsoft.common.base.BaseService;
import com.ptsoft.common.base.EntityDao;
import com.ptsoft.common.util.Pageable;
import com.ptsoft.pts.basic.dao.StockDao;
import com.ptsoft.pts.basic.model.vo.Stock;

@Service
public class StockService extends BaseService<Stock, Integer>{
	
	@Autowired
	private StockDao stockDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected EntityDao getEntityDao() {
		return this.stockDao;
	}

	@Override
	public void save(Stock entity) {
		
	}

	public List<Stock> findByCompanyId(Integer companyId) {
		return stockDao.findByCompanyId(companyId);
	}

	public List<Stock> findByCompIdAndSearch(String companyId, String searchParam,Pageable pageable) {
		int begin = pageable.getOffset() + 1;
		int end = pageable.getOffset() + pageable.getLimit();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("companyId", companyId);
		map.put("searchParam", "%" + searchParam + "%");
		map.put("begin", String.valueOf(begin));
		map.put("end", String.valueOf(end));		
		return stockDao.findByCompIdAndSearch(map);
	}
	

	public int getstockCount(String companyId, String searchParam) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("companyId", companyId);
		map.put("searchParam", "%" + searchParam + "%");
		return this.stockDao.getstockCount(map);
	}
	
	@ServiceLog
	public String saveStock(Stock stock)
	{
		String msg = null;
		int num = this.stockDao.getByStockId(stock.getId());
		Stock entity = this.stockDao.getByCode(stock.getCode());
		
		if(entity != null && entity.getId() != stock.getId())
			return "库位编码已存在，请重新填写！";
		
		try
		{
			if (num > 0)
			{
				this.stockDao.update(stock);
				msg = "库位信息更新成功！";
			}
			else
			{
				//新增经销商基础数据
				this.stockDao.insert(stock);
				msg = "库位信息保存成功！";
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			msg = "库位信息保存失败！";
		}
		return msg;
	}

}
