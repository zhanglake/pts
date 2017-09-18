package com.ptsoft.pts.account.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

import com.ptsoft.common.base.BaseMybatisDao;
import com.ptsoft.pts.account.model.vo.SysUser;

@Repository
public class SysUserDao extends BaseMybatisDao<SysUser, java.lang.Integer>
{
	/**
	 * SQL前缀
	 * */
	public String getMybatisMapperPrefix()
	{
		return "SysUser";
	}
	
	@SuppressWarnings("rawtypes")
	public Page findByPageRequest(PageRequest pageRequest)
	{
		return pageQuery("getUserList", "getUserListCount", pageRequest);
	}

	public List<String> getUserAuthoritiesName(String userName)
	{
		return this.getSqlSession().selectList("getUserAuthoritiesName", userName);
	}

	public List<SysUser> getUserByName(String userName)
	{
		return this.getSqlSession().selectList("getUserByName", userName);
	}

	public int getUserCount(HashMap<String, String> map)
	{
		return (Integer) this.getSqlSession().selectOne("getUserListCount", map);
	}
	
	@Override
	public List<SysUser> findAll()
	{
		return this.getSqlSession().selectList("findAllUser");
	}
	
	public List<SysUser> findAll(int userType)
	{
		return this.getSqlSession().selectList("findAllUserByType", userType);
	}
	
	public SysUser getUserLogin(String lgnNm,String pswd)
	{
		Map<String, String> para =new HashMap<String, String>();
		para.put("lgnNm", lgnNm);
		para.put("pswd", pswd);
		return (SysUser)this.getSqlSession().selectOne("getUserLogin", para);
	}
	
	
	public void profilelSave(SysUser user)
	{
		this.getSqlSession().update("SysUser_profilelSave", user);
	}
	
	public void passwordSave(SysUser user)
	{
		this.getSqlSession().update("SysUser_passwordSave", user);
	}

	public List<SysUser> findDealerUser(Map<String, String> map) 
	{
		return this.getSqlSession().selectList("SysUser_findDealerUser", map);
	}
	
	public int getDealerUserCount(Map<String, String> map) {
		return (Integer) this.getSqlSession().selectOne("SysUser_getDealerUserCount", map);
	}

	public List<Object> findByCompId(HashMap<Object, Object> map) 
	{
		return this.getSqlSession().selectList("SysUser_findByCompId", map); 
	}
	
	public int getCountByCompId(HashMap<Object, Object> map) {
		return (Integer) this.getSqlSession().selectOne("SysUser_getCountByCompId", map);
	}

	public SysUser findByNameAndToken(HashMap<String, String> map) {
		return (SysUser)this.getSqlSession().selectOne("SysUser_findByNameAndToken", map);
	}

	public SysUser findByLgNm(String lgnNm) {
		return (SysUser)this.getSqlSession().selectOne("SysUser_findByLgNm", lgnNm);
	}

	public List<SysUser> getAllPDAUser() 
	{
		return this.getSqlSession().selectList("SysUser_getAllPDAUser");
	}

	public SysUser getLastUser(String qrcode, int actionType) 
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("actionType", String.valueOf(actionType));
		map.put("qrcode", qrcode);
		
		return (SysUser) this.getSqlSession().selectOne("SysUser_getLastUser", map);
	}

	public List<SysUser> findBySupplierId(HashMap<String, String> map)
	{
		return this.getSqlSession().selectList("SysUser_findBySupplierId", map);
	}
	
	public int getPkgUserCountBySearchParam(HashMap<String, String> map) {
		return (Integer) this.getSqlSession().selectOne("SysUser_getPkgUserCount", map);
	}

	public List<Object> findOtherDealers(HashMap<Object, Object> map) 
	{
		return this.getSqlSession().selectList("SysUser_findByTypeUser", map);
	}
	
	public int getCountByType(int sysTp)
	{
		return (Integer) this.getSqlSession().selectOne("SysUser_getCountByType", sysTp);
	}

	public int getCountByDealerId(int dealerId) 
	{
		return (Integer) this.getSqlSession().selectOne("SysUser_getCountByDealerId", dealerId);
	}

	public List<Object> findEndUser(HashMap<Object, Object> map)
	{
		return this.getSqlSession().selectList("SysUser_findEndUser", map);
	}

	public int getEndUserCount(HashMap<String, String> map) 
	{
		return (Integer) this.getSqlSession().selectOne("SysUser_getEndUserCount", map);
	}

	public int findByTypeCount(HashMap<String, Object> map) 
	{
		return (Integer) this.getSqlSession().selectOne("SysUser_findByTypeCount", map);
	}

	public List<SysUser> findBySysType(HashMap<String, Object> map)
	{
		return this.getSqlSession().selectList("SysUser_findBySysType", map);
	}

	public int getPoints(int dealerId) 
	{
		return (Integer) this.getSqlSession().selectOne("SysUser_getPoints", dealerId);
	}

	public ArrayList<Object> getUserForExport(HashMap<Object, Object> map) 
	{
		return (ArrayList<Object>) this.getSqlSession().selectList("SysUser_getUserForExport", map);
	}

	public ArrayList<Object> pkgUserExport(HashMap<String, String> map)
	{
		return (ArrayList<Object>) this.getSqlSession().selectList("SysUser_pkgUserExport", map);
	}

	public ArrayList<Object> exportEndUser(HashMap<String, String> map)
	{
		return (ArrayList<Object>) this.getSqlSession().selectList("SysUser_exportEndUser", map);
	}

	public List<SysUser> getByType(HashMap<String, Object> map) 
	{
		return this.getSqlSession().selectList("SysUser_getByType", map);
	}

	public List<Object> getScanUser(HashMap<String, Object> map) 
	{
		return this.getSqlSession().selectList("SysUser_getScanUser", map);
	}

	public int getScanUserConut(HashMap<String, Object> map)
	{
		return (Integer) this.getSqlSession().selectOne("SysUser_getScanUserConut", map);
	}

	public int reportListCount(HashMap<String, Object> map) 
	{
		return (Integer) this.getSqlSession().selectOne("SysUser_reportListCount", map);
	}

	public List<Object> reportList(HashMap<String, Object> map) 
	{
		return this.getSqlSession().selectList("SysUser_reportList", map);
	}

	public List<Object> getRelation(HashMap<String, Object> map) 
	{
		return this.getSqlSession().selectList("SysUser_getRelation", map);
	}

	public ArrayList<Object> exportUsers(HashMap<String, Object> map)
	{
		return (ArrayList<Object>) this.getSqlSession().selectList("SysUser_exportUsers", map);
	}

	public ArrayList<Object> exportPoints(HashMap<String, Object> map) 
	{
		return (ArrayList<Object>) this.getSqlSession().selectList("SysUser_exportPoints", map);
	}

}
