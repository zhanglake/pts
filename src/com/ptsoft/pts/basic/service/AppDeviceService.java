package com.ptsoft.pts.basic.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptsoft.common.base.BaseService;
import com.ptsoft.common.base.EntityDao;
import com.ptsoft.common.util.Pageable;
import com.ptsoft.pts.basic.dao.AppDeviceDao;
import com.ptsoft.pts.basic.model.vo.AppDevice;

@Service
public class AppDeviceService extends BaseService<AppDevice, Integer> {

	@Autowired
	private AppDeviceDao appDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected EntityDao getEntityDao() {
		return this.appDao;
	}

	@Override
	public void save(AppDevice entity) 
	{
		AppDevice device = appDao.getByDeviceNo(entity.getDeviceNo());
		if(device == null)
		{
			this.appDao.insert(entity);
		}
		else
		{
			this.appDao.update(entity);
		}
	}

	public int getCount() 
	{
		return this.appDao.getCount();
	}
	
	public List<Object> getAllByPage(Pageable pageable) 
	{
		int begin = pageable.getOffset() + 1;
		int end = pageable.getOffset() + pageable.getLimit();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("begin", String.valueOf(begin));
		map.put("end", String.valueOf(end));		
		return this.appDao.getAllByPage(map);
		
	}
}
