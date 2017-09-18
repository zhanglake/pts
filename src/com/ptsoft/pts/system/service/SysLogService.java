package com.ptsoft.pts.system.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptsoft.common.base.BaseService;
import com.ptsoft.common.base.EntityDao;
import com.ptsoft.common.util.Pageable;
import com.ptsoft.pts.PisConstants;
import com.ptsoft.pts.account.model.vo.SysUser;
import com.ptsoft.pts.system.dao.SysLogDao;
import com.ptsoft.pts.system.model.vo.SysLog;

@Service
public class SysLogService extends BaseService<SysLog, Integer> 
{
	
	@Autowired
	private SysLogDao logDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected EntityDao getEntityDao() 
	{
		return this.logDao;
	}

	@Override
	public void save(SysLog entity) 
	{
		
	}

	public List<SysLog> findByLogTp(HashMap<Object, Object> map, Pageable pageable) 
	{
		int begin = pageable.getOffset() + 1;
		int end = pageable.getOffset() + pageable.getLimit();
		
		map.put("begin", String.valueOf(begin));
		map.put("end", String.valueOf(end));
		
		return this.logDao.findByLogTp(map);
	}

	public int getCountByTp(HashMap<Object, Object> map)
	{
		return this.logDao.getCountByTp(map);
	}

	public void insertPrintLog(String number, String pkgLevel, SysUser user, String productName, String productCode) 
	{
		String loginfo = "打印产品" + productName + "(" + productCode + ")第" + pkgLevel + "层二维码" + number + "张";
		String actionType = String.valueOf(PisConstants.LogActionType.PrintQrcode.getKey());
		int logType = PisConstants.LogType.Business.getKey();
		SysLog entity = new SysLog(user.getLgnNm(), user.getUsrId(), "打印二维码", loginfo, 1, actionType, logType);
		this.logDao.insert(entity);
	}

	public List<SysLog> getListXls(int companyId, String actionType, int logType, String totm, String fmtm)
	{
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		
		map.put("compId", companyId);
		map.put("logType", logType);
		map.put("fmtm", fmtm);
		map.put("totm", totm);
		map.put("actionType", actionType);
		
		return this.logDao.getListXls(map);
	}

}
