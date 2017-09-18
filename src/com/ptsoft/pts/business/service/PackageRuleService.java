package com.ptsoft.pts.business.service;


import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptsoft.common.base.BaseService;
import com.ptsoft.common.base.EntityDao;
import com.ptsoft.common.util.Pageable;
import com.ptsoft.pts.business.dao.PackageDao;
import com.ptsoft.pts.business.dao.PackageRuleDao;
import com.ptsoft.pts.business.dao.PackageRuleMapDao;
import com.ptsoft.pts.business.model.vo.PackageRule;
import com.ptsoft.pts.business.model.vo.PackageRuleMap;

@Service
public class PackageRuleService extends BaseService<PackageRule, Integer> 
{

	@Autowired
	private PackageRuleDao packageRuleDao;
	@Autowired
	private PackageRuleMapDao mapDao;
	@Autowired
	private PackageDao packageDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected EntityDao getEntityDao() 
	{
		return this.packageRuleDao;
	}

	@Override
	public void save(PackageRule entity)
	{
		
	}

	public List<Object> findByCompIdAndLike(int compId, String searchParam, Pageable pageable) 
	{
		int begin = pageable.getOffset() + 1;
		int end = pageable.getOffset() + pageable.getLimit();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("searchParam", "%" + searchParam + "%");
		map.put("compId", compId);
		map.put("begin", String.valueOf(begin));
		map.put("end", String.valueOf(end));		
		return this.packageRuleDao.findByCompIdAndLike(map);
	}

	public String saveRule(PackageRule entity, int compId) 
	{
		String msg = "包装规则保存成功！";
		try 
		{
			int num = this.packageRuleDao.getByIdForNum(entity.getId());
			PackageRule rule = this.packageRuleDao.getByCode(entity.getCode());
			
			if(rule != null && rule.getId() != entity.getId())
			{
				return "编码已存在，请重新输入！";
			}
			
			if(num > 0)
			{
				PackageRule packageRule = this.packageRuleDao.getById(entity.getId());
				if(packageRule.getSts() == 0 && entity.getSts() == 1)
				{
					int isAvaliable = this.packageDao.getNumUnAvaliable(entity.getId());
					if(isAvaliable > 0)
					{
						return "该包装规则中有使用的包装定义处于禁用状态，不能更新该包装规则为可用状态！";
					}
				}
				this.packageRuleDao.update(entity);
			}
			else
			{
				entity.setCompany_id(compId);
				this.packageRuleDao.insert(entity);
			}
		} 
		catch (Exception e) 
		{
			msg = "包装规则保存失败！";
			e.printStackTrace();
		}
		return msg;
	}

	public String savePkgMap(PackageRuleMap entity) 
	{
		String msg = "包装规则设置成功！";
		try 
		{
			int num = this.mapDao.getByRuleIDAndPkgID(entity.getRule_id(), entity.getPackage_id());
			
			if(num > 0)
			{
				msg = "该包装层级已选择相同的包装，请重新选择！";
			}
			else
			{
				this.mapDao.insert(entity);
			}
		} 
		catch (Exception e) 
		{
			msg = "包装规则设置失败！";
			e.printStackTrace();
		}
		return msg;
	}

	public String removeByRuleAndPkg(Integer ruleId, Integer pkgId)
	{
		String msg = "删除包装设置成功！";
		try 
		{
			this.mapDao.deleteByRuleAndPkg(ruleId, pkgId);
		} 
		catch (Exception e) 
		{
			msg = "删除包装设置失败！";
			e.printStackTrace();
		}
		return msg;
	}

	public List<PackageRule> findAllCanUsed(Integer compId)
	{
		return this.packageRuleDao.findAllCanUsed(compId);
	}

	public List<HashMap<String, Object>> getByRuleId(Integer ruleId) 
	{
		return this.mapDao.getByRuleId(ruleId);
	}

	public List<PackageRuleMap> getRuleMap(int ruleId) 
	{
		return this.mapDao.getRuleMap(ruleId);
	}

	public List<HashMap<String, Object>> getByRuleIdForApi(int ruleId) 
	{
		return this.mapDao.getByRuleIdForApi(ruleId);
	}
	/**
	 * 根据条件查询总条数
	 * @param compId
	 * @param searchParam
	 * @return
	 */
	public int getpackageRuleCountByCompIdAndSearchParam(int compId, String searchParam) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("searchParam", "%" + searchParam + "%");
		map.put("compId", compId);
		return this.packageRuleDao.getpackageRuleCountByCompIdAndSearchParam(map);
	}

}
