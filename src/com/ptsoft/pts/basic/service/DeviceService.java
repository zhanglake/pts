package com.ptsoft.pts.basic.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptsoft.common.base.BaseService;
import com.ptsoft.common.base.EntityDao;
import com.ptsoft.common.util.Pageable;
import com.ptsoft.pts.basic.dao.DeviceDao;
import com.ptsoft.pts.basic.model.vo.PdaDevice;

@Service
public class DeviceService extends BaseService<PdaDevice, Integer> {

	@Autowired
	private DeviceDao deviceDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected EntityDao getEntityDao() {
		return this.deviceDao;
	}

	@Override
	public void save(PdaDevice entity) {
		
	}
	
	public String savePDA(PdaDevice entity){
		String msg = null;
		int num = this.deviceDao.getByPdaId(entity.getId());
		PdaDevice pda = this.deviceDao.getByDeviceNo(entity.getDeviceNo());
		
		if(pda != null && pda.getId() != entity.getId())
			return "PDA设备编号已存在，请重新填写！";
		
		try
		{
			if (num > 0)
			{
				this.deviceDao.update(entity);
				msg = "PDA设备信息更新成功！";
			}
			else
			{
				//新增经销商基础数据
				this.deviceDao.insert(entity);
				msg = "PDA设备信息保存成功！";
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			msg = "PDA设备信息保存失败！";
		}
		return msg;
	}

	public List<PdaDevice> findByCompanyId(int company_id) {
		return this.deviceDao.findByCompanyId(company_id);
	}
	
	public List<Object> findByCompanyIdAndPage(int company_id,Pageable pageable) 
	{
		int begin = pageable.getOffset() + 1;
		int end = pageable.getOffset() + pageable.getLimit();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("company_id", company_id);
		map.put("begin", String.valueOf(begin));
		map.put("end", String.valueOf(end));		
		return this.deviceDao.findByCompanyIdAndPage(map);
	}
	
	public int getPdaDeviceListCount(int company_id){
		return this.deviceDao.findCountByCompanyId(company_id);
	} 
}
