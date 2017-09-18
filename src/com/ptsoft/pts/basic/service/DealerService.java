package com.ptsoft.pts.basic.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptsoft.common.Constants;
import com.ptsoft.common.base.BaseService;
import com.ptsoft.common.base.EntityDao;
import com.ptsoft.common.util.ExcelUtil;
import com.ptsoft.common.util.Pageable;
import com.ptsoft.common.util.SysConfig;
import com.ptsoft.pts.basic.dao.CompanyDealerDao;
import com.ptsoft.pts.basic.dao.DealerDao;
import com.ptsoft.pts.basic.model.vo.CompanyDealerMap;
import com.ptsoft.pts.basic.model.vo.Dealer;

@Service
public class DealerService extends BaseService<Dealer, Integer> {

	@Autowired
	private DealerDao dealerDao;
	@Autowired
	private CompanyDealerDao cdDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected EntityDao getEntityDao()
	{
		return this.dealerDao;
	}

	@Override
	public void save(Dealer entity)
	{
	}

	public List<Object> findBySearchParam(String searchParam,Pageable pageable) 
	{
		int begin = pageable.getOffset() + 1;
		int end = pageable.getOffset() + pageable.getLimit();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("searchParam", "%" + searchParam + "%");
		map.put("begin", String.valueOf(begin));
		map.put("end", String.valueOf(end));		
		return this.dealerDao.findBySearchParam(map);
	}
	/**
	 * 根据条件查询经销商记录数
	 * @param searchItems（条件）
	 * @return
	 */
	public int getdealerCount(String searchParam){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("searchParam", "%" + searchParam + "%");
		return this.dealerDao.getdealerCountBySearchParam(map);
	}

	public List<Dealer> findByCompanyId(Integer companyId){
		return this.dealerDao.findByCompanyId(companyId);
	}
	
	public String saveDealer(Dealer dealer)
	{
		String msg = null;
		int num = this.dealerDao.getByDealId(dealer.getId());
		Dealer del = this.dealerDao.getByCode(dealer.getDealer_code());
		Dealer revNo = this.dealerDao.getByRevenueNo(dealer.getRevenueNO());
		
		try
		{
			if (num > 0)
			{
				if((del != null  && del.getId() != dealer.getId()) || (revNo != null && revNo.getId() != dealer.getId()))
				{
					return "2"; //经销商编码或税号已存在，请重新填写！
				}

				this.dealerDao.update(dealer);
				msg = "3"; //经销商信息更新成功！
			}
			else
			{
				//新增经销商基础数据
				if(del != null || revNo != null)
				{
					return "2"; //经销商编码或税号已存在，请重新填写！
				}
				this.dealerDao.insert(dealer);
				msg = "1"; //经销商信息保存成功！
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			msg = "0"; //经销商信息保存失败！
		}
		return msg;
	}
	
	public String saveComanyDealer(Integer companyId, Dealer dealer)
	{
		String innerCode = dealer.getInnerCode();
		String msg = saveDealer(dealer);
		if(msg.equals("1") || msg.equals("3"))
		{
			Dealer entity = dealerDao.getByCode(dealer.getDealer_code());
			CompanyDealerMap cd = cdDao.findByCompIdAndDealerId(companyId, dealer.getId());
		
			if(cd == null)
			{
				cd = new CompanyDealerMap();
				cd.setCompanyId(companyId);
				cd.setDealerId(entity.getId());
				cd.setInnerCode(innerCode);
				cdDao.insert(cd);
			}
			else
			{
				cd.setInnerCode(innerCode);
				cdDao.update(cd);
			}
		}
		return msg;
	}

	public List<Object> findByCompId(String searchParam, int compId, Pageable pageable) 
	{
		int begin = pageable.getOffset() + 1;
		int end = pageable.getOffset() + pageable.getLimit();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		
		map.put("searchParam", "%" + searchParam + "%");
		map.put("begin", String.valueOf(begin));
		map.put("end", String.valueOf(end));
		map.put("compId", compId);
		
		return this.dealerDao.findByCompId(map);
	}

	public List<Dealer> getHasDealers(Pageable pageable, String compId, String searchParam)
	{
		Map<String, String> map = new HashMap<String, String>();
		int begin = pageable.getOffset() + 1;
		int end = pageable.getOffset() + pageable.getLimit();
		
		map.put("compId", compId);
		map.put("searchParam", "%" + searchParam + "%");
		map.put("begin", String.valueOf(begin));
		map.put("end", String.valueOf(end));
		
		return this.dealerDao.getHasDealers(map);
	}
	
	public int inCount(String compId, String searchParam)
	{
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("compId", compId);
		map.put("searchParam", "%" + searchParam + "%");
		
		return this.dealerDao.inCount(map);
	}

	public List<Dealer> notInDealers(Pageable pageable, String compId, String searchParam) 
	{
		Map<String, String> map = new HashMap<String, String>();
		int begin = pageable.getOffset() + 1;
		int end = pageable.getOffset() + pageable.getLimit();
		
		map.put("compId", compId);
		map.put("searchParam", "%" + searchParam + "%");
		map.put("begin", String.valueOf(begin));
		map.put("end", String.valueOf(end));
		
		return this.dealerDao.notInDealers(map);
	}
	
	public int notInCount(String compId, String searchParam) 
	{
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("compId", compId);
		map.put("searchParam", "%" + searchParam + "%");
		
		return this.dealerDao.notInCount(map);
	}

	public int getDealerCount(String companyId) 
	{
		return this.dealerDao.getDealerCount(companyId);
	}
	public int getDealerCount(String searchParam, int companyId) 
	{
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("searchParam", "%" + searchParam + "%");
		map.put("companyId", companyId);
		
		return this.dealerDao.getDealersCount(map);
	}

	public List<Object> dealerPercent(int compId) 
	{
		return this.dealerDao.dealerPercent(compId);
	}

	public String importData(String fileName, int companyId) 
	{
		File file = new File(SysConfig.getRootPath() + Constants.UPLOAD_XLS_PATH + File.separator + fileName);
		String[][] result = null;
		String msg = "";
		try
		{
			result = ExcelUtil.getData(file, 3);
			if(null == result || "".equals(result))
			{
				return "";
			}
			
			int rowLength = result.length;
			Dealer dealer;
			CompanyDealerMap cd;
			for (int i = 0; i < rowLength; i++) 
			{
				dealer = new Dealer();
				cd = new CompanyDealerMap();
				for (int j = 0; j < result[i].length; j++) 
				{
					if(j == 0)
					{
						dealer.setRevenueNO(result[i][j]);
					}
					if(j == 1)
					{
						dealer.setDealer_code(result[i][j]);
					}
					if(j == 2)
					{
						dealer.setDealer_name(result[i][j]);
					}
					if(j == 3)
					{
						dealer.setProvince(result[i][j]);
					}
					if(j == 4)
					{
						dealer.setAddress(result[i][j]);
					}
					if(j == 5)
					{
						dealer.setContact(result[i][j]);
					}
					if(j == 6)
					{
						dealer.setPhone(result[i][j]);
					}
					if(j == 7)
					{
						dealer.setAreaCode(result[i][j]);
					}
					if(j == 8)
					{
						dealer.setTel(result[i][j]);
					}
					if(j == 9)
					{
						dealer.setRemark(result[i][j]);
					}
					if(j == 10)
					{
						cd.setInnerCode(result[i][j]);
					}
				}
				
				Dealer existByCode = this.dealerDao.getByCode(dealer.getDealer_code());
				Dealer existByRevenueNo = this.dealerDao.getByRevenueNo(dealer.getRevenueNO());
				
				if(null == existByCode && null == existByRevenueNo)
				{
					dealer.setStatus(1);
					dealerDao.insert(dealer);
					
					cd.setCompanyId(companyId);
					cd.setDealerId(dealer.getId());
					cdDao.insert(cd);
				}
				
			}
			msg = "1";
		}
		catch(Exception e)
		{
			System.out.println("--经销商批量导入--" + e.toString() + "----");
			msg = "0";
		}
		return msg;
	}

	public Dealer getCompanyIdAndId(int dealerId, int companyId)
	{
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("dealerId", dealerId);
		map.put("companyId", companyId);
		
		return this.dealerDao.getCompanyIdAndId(map);
	}

}
