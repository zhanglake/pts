package com.ptsoft.pts.basic.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ptsoft.common.base.BaseMybatisDao;
import com.ptsoft.pts.basic.model.vo.PointFollowing;

@Repository
public class PointFollowingDao extends BaseMybatisDao<PointFollowing, Integer>
{

	@Override
	protected String getMybatisMapperPrefix()
	{
		return "PointFollowing";
	}

	@Override
	public List<PointFollowing> findAll() 
	{
		return null;
	}
	
///////////////////////zxfupdate20160818
	public List<Object> query_Allpointbyuserid(String userId) 
	{
		return this.getSqlSession().selectList("query_Allpointbyuserid", userId);
    }
	
	public List<Object> query_Allpointbyuserid_count(HashMap<Object, Object> map) {
		return this.getSqlSession().selectList("query_Allpointbyuserid_count", map);
	}
///////////////////////zxfupdate20160818

}
