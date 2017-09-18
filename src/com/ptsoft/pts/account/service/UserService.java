/**
 * 
 */
package com.ptsoft.pts.account.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.ptsoft.common.annotation.ServiceLog;
import com.ptsoft.common.base.BaseService;
import com.ptsoft.common.base.EntityDao;
import com.ptsoft.common.util.Pageable;
import com.ptsoft.pts.PisConstants;
import com.ptsoft.pts.account.dao.SysRoleDao;
import com.ptsoft.pts.account.dao.SysUserDao;
import com.ptsoft.pts.account.dao.SysUserPdaDao;
import com.ptsoft.pts.account.model.vo.SysRole;
import com.ptsoft.pts.account.model.vo.SysUser;
import com.ptsoft.pts.account.model.vo.SysUserPDA;

/**
 * 用户管理业务类
 * 
 * @author
 */
@Service
@SuppressWarnings("unused")
public class UserService extends BaseService<SysUser, java.lang.Integer>
{
	@Autowired
	private SysUserDao userDao;
	@Autowired
	private SysRoleDao roleDao;
	@Autowired
	private SysUserPdaDao userPdaDao;
	
	private static final Logger logger = Logger.getLogger(UserService.class);

	@SuppressWarnings("rawtypes")
	public EntityDao getEntityDao()
	{
		return this.userDao;
	}

	@Override
	@Cacheable(cacheName = "sysUserCache")
	public List<SysUser> findAll()
	{
		return this.userDao.findAll();
	}

	@Cacheable(cacheName = "sysUserCache")
	public List<SysUser> findAll(int userType)
	{
		return this.userDao.findAll(userType);
	}
	
	/**
	 * 保存用户
	 * 
	 * @param user
	 * @param checkedRoleIds
	 */
	@Override
	@TriggersRemove(cacheName = "sysUserCache", removeAll = true)
	public void save(SysUser user)
	{
		
		if (user.getUsrId() == 0)
		{
			userDao.insert(user);
		}
		else
		{
			// 更新User表
			userDao.update(user);
		}
		
	}
	
	/**
	 * 删除用户
	 * 
	 * @param user 用户对象
	 */
	@TriggersRemove(cacheName = "sysUserCache", removeAll = true)
	public void deleteUser(SysUser user)
	{
		userDao.deleteById(user.getUsrId());
	}

	/**
	 * 检查用户列表
	 */
	@SuppressWarnings("rawtypes")
	public Page findByPageRequest(PageRequest pr)
	{
		return userDao.findByPageRequest(pr);
	}

	/**
	 * 登录名是否唯一
	 * 
	 * @param loginName
	 *            新登录名
	 * @param oldLoginName
	 *            原始登录名
	 * @return 是否唯一
	 */
	public boolean isNameUnique(String loginName, String oldLoginName)
	{
		if (loginName == null || loginName.equals(oldLoginName))
		{
			return true;
		}
		List<SysUser> users = this.userDao.getUserByName(loginName);
		return users.size() == 0;
	}

	/**
	 * 检查用户记录数
	 */
	public int getUserCount(String compId, String rlId)
	{
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("compId", compId);
		map.put("rlId", rlId);
		return userDao.getUserCount(map);
	}

	/**
	 * 根据登录名查用户列表
	 */
	public List<SysUser> getUserByName(String loginName)
	{
		return userDao.getUserByName(loginName);
	}

	/**
	 * 用户登录
	 * 
	 * @param lgnNm
	 * @param pswd
	 * @return
	 */
	@ServiceLog(description = "用户认证")
	public SysUser getUserLogin(String lgnNm, String pswd)
	{
		return userDao.getUserLogin(lgnNm, pswd);
	}
	
	/**
	 * 保存用户
	 * 
	 * @param user
	 */
	@TriggersRemove(cacheName = "sysUserCache", removeAll = true)
	public String profilelSave(SysUser user)
	{
		try
		{
			this.userDao.profilelSave(user);
			return "个人信息保存成功！";
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "人个信息保存失败！";
		}
	}
	
	/**
	 * 保存用户
	 * 
	 * @param user
	 */
	@ServiceLog(description = "修改密码")
	@TriggersRemove(cacheName = "sysUserCache", removeAll = true)
	public String passwordSave(SysUser user)
	{
		try
		{
			this.userDao.passwordSave(user);
			return "个人信息保存成功！";
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "人个信息保存失败！";
		}
	}

	public int getCountByCompId(String compId, String sysTp, String roleId, String searchParam) 
	{
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("searchParam", "%" + searchParam + "%");
		map.put("compId", compId);
		map.put("sysTp", sysTp);
		map.put("rlId", roleId);
		return this.userDao.getCountByCompId(map);
	}
	

	public List<SysUser> findDealerUser(String sysTp, String isLgn, String dealerId, String searchParam,Pageable pageable)
	{
		int begin = pageable.getOffset() + 1;
		int end = pageable.getOffset() + pageable.getLimit();
		Map<String, String> map = new HashMap<String, String>();
		map.put("sysTp", sysTp);
		map.put("isLgn", isLgn);
		map.put("dealerId", dealerId);
		map.put("searchParam", "%" + searchParam + "%");
		map.put("begin", String.valueOf(begin));
		map.put("end", String.valueOf(end));	
		return this.userDao.findDealerUser(map);
	}
	
	public int getDealerUserCount(String sysTp, String isLgn, String dealerId, String searchParam) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("sysTp", sysTp);
		map.put("isLgn", isLgn);
		map.put("dealerId", dealerId);
		map.put("searchParam", "%" + searchParam + "%");
		return this.userDao.getDealerUserCount(map);
	}

	public List<Object> findByCompId(String companyId, String roleId, String sysTp, String searchParam, Pageable pageable)
	{
		int begin = pageable.getOffset() + 1;
		int end = pageable.getOffset() + pageable.getLimit();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		
		map.put("rlId", roleId);
		map.put("sysTp", sysTp);
		map.put("companyId", companyId);
		map.put("searchParam", "%" + searchParam + "%");
		map.put("begin", String.valueOf(begin));
		map.put("end", String.valueOf(end));	
		
		return this.userDao.findByCompId(map);
	}
	
	public SysUser findByNameAndToken(String lgnNm, String token)
	{
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("lgnNm", lgnNm);
		map.put("token", token);
		return this.userDao.findByNameAndToken(map);
	}

	public SysUser findByLgNm(String lgnm) 
	{
		return this.userDao.findByLgNm(lgnm);
	}
	
	public String savePdaUser(SysUser user, int rlId) 
	{
		String msg = "";
		try
		{
			List<SysUser> sameList = this.userDao.getUserByName(user.getLgnNm());
			if(null != sameList && sameList.size() > 0 && sameList.get(0).getUsrId() != user.getUsrId())
			{
				msg = "0";
			}
			else
			{
				SysRole role = new SysRole();
				role.setRlId(rlId);
				user.setRole(role);
				
				if (user.getUsrId() == 0)
				{
					int userId = userDao.insert(user);
					insertActions(user.getUsrId(), user.getActions());
				}
				else
				{
					// 更新User表
					userDao.update(user);
					this.userPdaDao.deleteByUserId(user.getUsrId());
					insertActions(user.getUsrId(), user.getActions());
				}
				msg = "1";
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			msg = "2";
		}
		return msg;
	}

	private void insertActions(int usrId, List<String> actions) 
	{
		SysUserPDA userPDA;
		if(actions == null || actions.size() <= 0)
		{
			return;
		}
		for (int i = 0; i < actions.size(); i++) 
		{
			userPDA = new SysUserPDA();
			userPDA.setUserId(usrId);
			userPDA.setActionId(actions.get(i));
			this.userPdaDao.insert(userPDA);
		}
	}

	public HashMap<Integer, SysUser> getAllPDAUser()
	{
		HashMap<Integer, SysUser> map = new HashMap<Integer, SysUser>();
		List<SysUser> list = this.userDao.getAllPDAUser();
		SysUser user = new SysUser();
		if(!list.contains(user))
		{
			
		}
		for (int i = 0; i < list.size(); i++) 
		{
			user = list.get(i);
			if(!map.containsKey(list.get(i).getUsrId()))
			{
				user.setActions(new ArrayList<String>());
				map.put(user.getUsrId(), user);
			}
			map.get(user.getUsrId()).getActions().add(user.getAction());
		}
		return map;
	}

	public List<SysUser> findBySupplierId(int supplierId, int sysTp, String searchParam,Pageable pageable)
	{
		int begin = pageable.getOffset() + 1;
		int end = pageable.getOffset() + pageable.getLimit();
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("supplierId", String.valueOf(supplierId));
		map.put("sysTp", String.valueOf(sysTp));
		map.put("searchParam", "%" + searchParam + "%");
		map.put("begin", String.valueOf(begin));
		map.put("end", String.valueOf(end));
		
		return this.userDao.findBySupplierId(map);
	}
	
	public int getPkgUserCountBySearchParam(int supplierId, int sysTp, String searchParam) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("supplierId", String.valueOf(supplierId));
		map.put("sysTp", String.valueOf(sysTp));
		map.put("searchParam", "%" + searchParam + "%");
		return this.userDao.getPkgUserCountBySearchParam(map);
	}

	public List<Object> findEndUser(String companyId, String searchParam, String rlId, Pageable pageable)
	{
		int begin = pageable.getOffset() + 1;
		int end = pageable.getOffset() + pageable.getLimit();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		
		map.put("searchParam", "%" + searchParam + "%");
		map.put("companyId", companyId);
		map.put("rlId", rlId);
		map.put("begin", String.valueOf(begin));
		map.put("end", String.valueOf(end));
		
		return userDao.findEndUser(map);
	}
	
	public int getCountByType(int sysTp) 
	{
		return this.userDao.getCountByType(sysTp);
	}

	public int getCountByDealerId(int dealerId) 
	{
		return this.userDao.getCountByDealerId(dealerId);
	}

	public int getEndUserCount(String companyId, String searchParam, String rlId) 
	{
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("searchParam", "%" + searchParam + "%");
		map.put("companyId", companyId);
		map.put("rlId", rlId);
		
		return this.userDao.getEndUserCount(map);
	}

	public String saveOrUpdate(SysUser user, int rlId) 
	{
		String msg = "";
		try
		{
			List<SysUser> sameList = this.userDao.getUserByName(user.getLgnNm());
			if(null != sameList && sameList.size() > 0 && sameList.get(0).getUsrId() != user.getUsrId())
			{
				msg = "0";
			}
			else
			{
				SysRole role = new SysRole();
				role.setRlId(rlId);
				user.setRole(role);
				if (user.getUsrId() == 0)
				{
					userDao.insert(user);
				}
				else
				{
					// 更新User表
					userDao.update(user);
				}
				msg = "1";
			}
		}
		catch (Exception e)
		{
			msg = "2";
			e.printStackTrace();
		}
		
		return msg;
	}

	public int findByTypeCount(int type, int companyId, String searchParam) 
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("searchParam", "%" + searchParam + "%");
		map.put("companyId", companyId);
		map.put("type", type);
		
		return this.userDao.findByTypeCount(map);
	}

	public List<SysUser> findBySysType(int type, int companyId, String searchParam, Pageable pageable) 
	{
		int begin = pageable.getOffset() + 1;
		int end = pageable.getOffset() + pageable.getLimit();
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("searchParam", "%" + searchParam + "%");
		map.put("companyId", companyId);
		map.put("type", type);
		map.put("begin", String.valueOf(begin));
		map.put("end", String.valueOf(end));
		
		return this.userDao.findBySysType(map);
	}

	public int getPoints(int dealerId) 
	{
		return this.userDao.getPoints(dealerId);
	}

	public ArrayList<Object> getUserForExport(int companyId, String searchParam, String rlId, int sysTp) 
	{
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		
		map.put("rlId", rlId);
		map.put("sysTp", sysTp);
		map.put("companyId", companyId);
		map.put("searchParam", "%" + searchParam + "%");
		
		return this.userDao.getUserForExport(map);
	}

	public ArrayList<Object> pkgUserExport(int supplierId, int sysTp, String searchParam)
	{

		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("supplierId", String.valueOf(supplierId));
		map.put("sysTp", String.valueOf(sysTp));
		map.put("searchParam", "%" + searchParam + "%");
		
		return this.userDao.pkgUserExport(map);
	}

	public ArrayList<Object> exportEndUser(String companyId, String searchParam, String rlId) 
	{

		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("companyId", null);
		map.put("rlId", String.valueOf(rlId));
		map.put("searchParam", "%" + searchParam + "%");
		
		return this.userDao.exportEndUser(map);
	}

	public List<SysUser> getByType(String rlId, String type, int companyId, String searchParam) 
	{
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("searchParam", "%" + searchParam + "%");
		map.put("companyId", companyId);
		map.put("type", type);
		map.put("rlId", rlId);
		
		return this.userDao.getByType(map);
	}

	public List<Object> getScanUser(String fmTm, String toTm, String rlId, String searchParam, int companyId, Pageable pageable)
	{
		int begin = pageable.getOffset() + 1;
		int end = pageable.getOffset() + pageable.getLimit();
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("searchParam", "%" + searchParam + "%");
		map.put("fmTm", fmTm);
		map.put("toTm", toTm);
		map.put("rlId", rlId);
		map.put("begin", begin);
		map.put("end", end);
		map.put("companyId", companyId);
		
		return this.userDao.getScanUser(map);
	}

	public int getScanUserConut(String fmTm, String toTm, String rlId, String searchParam, int companyId) 
	{

		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("searchParam", "%" + searchParam + "%");
		map.put("fmTm", fmTm);
		map.put("toTm", toTm);
		map.put("rlId", rlId);
		map.put("companyId", companyId);
		
		return this.userDao.getScanUserConut(map);
	}

	public List<Object> reportList(String fmTm, String toTm, String type, String searchParam, int companyId, Pageable pageable) 
	{
		int begin = pageable.getOffset() + 1;
		int end = pageable.getOffset() + pageable.getLimit();
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("searchParam", "%" + searchParam + "%");
		map.put("fmTm", fmTm);
		map.put("toTm", toTm);
		map.put("type", type);
		map.put("begin", begin);
		map.put("end", end);
		map.put("actionType", PisConstants.QRCodeStatus.InFlow.getKey());
		map.put("companyId", companyId);
		
		return this.userDao.reportList(map);
	}

	public int reportListCount(String fmTm, String toTm, String searchParam, String type, int companyId)
	{

		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("searchParam", "%" + searchParam + "%");
		map.put("fmTm", fmTm);
		map.put("toTm", toTm);
		map.put("type", type);
		map.put("companyId", companyId);
		map.put("actionType", PisConstants.QRCodeStatus.InFlow.getKey());
		
		return this.userDao.reportListCount(map);
	}

	public List<Object> getRelation(int usrId)
	{

		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("usrId", usrId);
		map.put("actionType", PisConstants.QRCodeStatus.InFlow.getKey());
		
		return this.userDao.getRelation(map);
	}

	public ArrayList<Object> exportUsers(String searchParam, String fmTm, String toTm, int companyId, int type) 
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("searchParam", "%" + searchParam + "%");
		map.put("fmTm", fmTm);
		map.put("toTm", toTm);
		map.put("type", type);
		map.put("companyId", companyId);
		map.put("actionType", PisConstants.QRCodeStatus.InFlow.getKey());
		
		return this.userDao.exportUsers(map);
	}

	public ArrayList<Object> exportPoints(String fmTm, String toTm, String rlId, String searchParam, int companyId)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("searchParam", "%" + searchParam + "%");
		map.put("fmTm", fmTm);
		map.put("toTm", toTm);
		map.put("rlId", rlId);
		map.put("companyId", companyId);
		
		return this.userDao.exportPoints(map);
	}
	
}
