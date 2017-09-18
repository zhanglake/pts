package com.ptsoft.pts.business.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptsoft.common.base.BaseService;
import com.ptsoft.common.base.EntityDao;
import com.ptsoft.common.util.DateUtil;
import com.ptsoft.common.util.Pageable;
import com.ptsoft.pts.account.model.vo.SysUser;
import com.ptsoft.pts.business.dao.ApplyApprovalDao;
import com.ptsoft.pts.business.model.vo.ApplyApproval;

@Service
public class ApplyApprovalService extends BaseService<ApplyApproval, Integer> 
{

	@Autowired
	private ApplyApprovalDao applyApprovalDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected EntityDao getEntityDao() 
	{
		return this.applyApprovalDao;
	}

	@Override
	public void save(ApplyApproval entity)
	{		
	}

	public int getSizeByParam(int supplierId, String fmtm, String totm)
	{
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("supplierId", String.valueOf(supplierId));
		map.put("fmtm", fmtm);
		map.put("totm", totm);
		
		return this.applyApprovalDao.getSizeByParam(map);
	}

	public List<ApplyApproval> getByParam(int supplierId, String fmtm, String totm, Pageable pageable) 
	{
		int begin = pageable.getOffset() + 1;
		int end = pageable.getOffset() + pageable.getLimit();
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("supplierId", String.valueOf(supplierId));
		map.put("begin", String.valueOf(begin));
		map.put("end", String.valueOf(end));
		map.put("fmtm", fmtm);
		map.put("totm", totm);
		
		return this.applyApprovalDao.getByParam(map);
	}

	public String commitApply(SysUser user, int productId, int count) 
	{
		String msg = "";
		try 
		{
			ApplyApproval applyApproval = new ApplyApproval();
			applyApproval.setApply_id(user.getUsrId());
			applyApproval.setSupplier_id(user.getSupplier_id());
			applyApproval.setType(1);
			applyApproval.setStatus(0);
			applyApproval.setComments("申请二维码");
			applyApproval.setProduct_id(productId);
			applyApproval.setCount(count);
			applyApproval.setCreateTime(DateUtil.getCurrentDate());
			
			this.applyApprovalDao.insert(applyApproval);
			
			msg = "1";
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			msg = "0";
		}
		return msg;
	}

	public int getSizeByCompanyId(int companyId, String fmtm, String totm, int status) 
	{
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("companyId", String.valueOf(companyId));
		map.put("fmtm", fmtm);
		map.put("totm", totm);
		map.put("status", String.valueOf(status));
		
		return this.applyApprovalDao.getSizeByCompanyId(map);
	}

	public List<ApplyApproval> getByParamCompId(int companyId, String fmtm, String totm, Pageable pageable) 
	{
		int begin = pageable.getOffset() + 1;
		int end = pageable.getOffset() + pageable.getLimit();
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("companyId", String.valueOf(companyId));
		map.put("begin", String.valueOf(begin));
		map.put("end", String.valueOf(end));
		map.put("fmtm", fmtm);
		map.put("totm", totm);
		
		return this.applyApprovalDao.getByParamCompId(map);
	}

	public String updateApply(int userId, int id, int status) 
	{
		String msg = "";
		try
		{
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			map.put("userId", userId);
			map.put("id", id);
			map.put("status", status);
			
			this.applyApprovalDao.updateApply(map);
			
			msg = "1";
		} 
		catch (Exception e) 
		{
			msg = "0";
			e.printStackTrace();
		}
		return msg;
	}

}
