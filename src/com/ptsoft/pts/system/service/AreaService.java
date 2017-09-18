package com.ptsoft.pts.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptsoft.pts.system.dao.AreaDao;
import com.ptsoft.pts.system.model.vo.SysArea;

@Service
public class AreaService {
	@Autowired
	private AreaDao areaDao;
	
	public void setBaseDao(AreaDao areaDao)
	{
		this.setBaseDao(areaDao);
	}

	public List<SysArea> findRoots()
	{
		return areaDao.findRoots(null);
	}

	public List<SysArea> findRoots(Integer count)
	{
		return areaDao.findRoots(count);
	}
	
	public SysArea find(Integer id){
		return areaDao.getById(id);
	}

	public List<SysArea> findChildren(Integer parentId) {
		return areaDao.findChildren(parentId);
	}
}
