package com.ptsoft.pts.system.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ptsoft.common.base.BaseMybatisDao;
import com.ptsoft.pts.system.model.vo.SysDataType;

@Repository
public class SysDataTypeDao extends BaseMybatisDao<SysDataType, String>
{
	/**
	 * SQL前缀
	 * */
	public String getMybatisMapperPrefix()
	{
		return "SysDataType";
	}
	
	public List<SysDataType> findAll()
	{
		return null;
	}
	
	public List<SysDataType> findByType(int type)
	{
		return getSqlSession().selectList("SysDataType_getByType", type);
	}
	
	public List<SysDataType> findByType(int type, String dicCode)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("typeId", String.valueOf(type));
		map.put("dicCode", dicCode);
		
		return getSqlSession().selectList("SysDataType_getAvailableByType", map);
	}
	
	public List<SysDataType> getStoreType(Integer tpid,String stcd)
	{	
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("tpid", tpid);
		map.put("stcd", stcd);
		return getSqlSession().selectList("SysDataType_getStoreType", map);
	}
	
	public int getDataTypeCount(int tpid,String tpcd,String dctcd)
	{	
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("tpid", tpid);
		map.put("tpcd", tpcd);
		map.put("dctcd", dctcd);
		return (Integer)getSqlSession().selectOne("SysDataType_getDataTypeCount", map);
	}
	
	
	public List<SysDataType> getPriceSystemList(int tpid,String itmcd){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("tpid", tpid);
		map.put("itmcd", itmcd);
		return getSqlSession().selectList("getPriceSystemList", map);
	}

	public SysDataType findByDctcd(String dctcd) {
		return (SysDataType) this.getSqlSession().selectOne("SysDataType_findByDctcd", dctcd);
	}

	
	public int getTypeCount(HashMap<Object, Object> map)
	{
		return (Integer) this.getSqlSession().selectOne("getTypeListCount", map);
	} 
	public List<Object> findByTypeAndCompID(HashMap<Object, Object> map)
	{
		return this.getSqlSession().selectList("SysDataType_findByTypeAndCompID", map);
	}
	public List<SysDataType> findByTypeAndCompID(int type, int compId)
	{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type", type);
		map.put("compId", compId);
		return this.getSqlSession().selectList("SysDataType_getByTypeAndCompID", map);
	}
	public int getNumByDctcd(String dctcd) 
	{
		return (Integer) this.getSqlSession().selectOne("SysDataType_getNumByDctcd", dctcd);
	}

	public List<Object> findByTypeNoPage(HashMap<Object, Object> map) {
		return this.getSqlSession().selectList("SysDataType_findByTypeNoPage", map);
	}
}
