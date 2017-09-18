package com.ptsoft.pts.basic.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ptsoft.common.base.BaseMybatisDao;
import com.ptsoft.pts.basic.model.vo.AppDevice;

@Repository
public class AppDeviceDao extends BaseMybatisDao<AppDevice, Integer> {

	@Override
	protected String getMybatisMapperPrefix() {
		return "AppDevice";
	}

	@Override
	public List<AppDevice> findAll() {
		return this.getSqlSession().selectList("AppDevice_findAll");
	}

	public AppDevice getByDeviceNo(String deviceNo)
	{
		return (AppDevice) this.getSqlSession().selectOne("AppDevice_getByDeviceNo", deviceNo);
	}

	public int getCount() 
	{
		return (Integer) this.getSqlSession().selectOne("AppDevice_getCount");
	}
	
	public List<Object> getAllByPage(Map<Object, Object> map) 
	{
		return this.getSqlSession().selectList("AppDevice_getAllByPage", map);
	}
}
