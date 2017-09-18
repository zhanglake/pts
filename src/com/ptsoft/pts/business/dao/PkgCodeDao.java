package com.ptsoft.pts.business.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ptsoft.common.base.BaseMybatisDao;
import com.ptsoft.pts.business.model.vo.PkgCodeMap;

@Repository
public class PkgCodeDao extends BaseMybatisDao<PkgCodeMap, Integer> {

	@Override
	protected String getMybatisMapperPrefix() {
		return "PkgCodeMap";
	}

	@Override
	public List<PkgCodeMap> findAll() {
		return null;
	}
	
	public List<PkgCodeMap> findByOuterCode(String qrcode){
		return this.getSqlSession().selectList("PkgCodeMap_findByOuterCode", qrcode);
	}
	
	public int checkExitInnerCode(String qrcode){
		return (Integer) this.getSqlSession().selectOne("PkgCodeMap_countInnerCode", qrcode);
	}

	public List<PkgCodeMap> findAllInnerCode(String qrcode) {
		return this.getSqlSession().selectList("PkgCodeMap_findAllInnerCode", qrcode);
	}

	public List<String> getInnerCode(String qrcode) 
	{
		return this.getSqlSession().selectList("PkgCodeMap_getInnerCode", qrcode);
	}

	public List<String> findOutCode(String qrcode) 
	{
		return this.getSqlSession().selectList("PkgCodeMap_findOutCode", qrcode);
	}

	public void insertLot(List<PkgCodeMap> mapList)
	{
		this.getSqlSession().insert("PkgCodeMap_insertLot", mapList);
	}

}
