package com.ptsoft.pts.basic.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ptsoft.common.base.BaseMybatisDao;
import com.ptsoft.pts.basic.model.vo.PdaDevice;

@Repository
public class DeviceDao extends BaseMybatisDao<PdaDevice, Integer>{

	@Override
	protected String getMybatisMapperPrefix() {
		return "Device";
	}

	@Override
	public List<PdaDevice> findAll() {
		return this.getSqlSession().selectList("Device_findAll");
	}

	public int getByPdaId(int id) {
		return (Integer) this.getSqlSession().selectOne("Device_getByPdaId", id);
	}

	public PdaDevice getByDeviceNo(String deviceNo) {
		return (PdaDevice) this.getSqlSession().selectOne("Device_getByDeviceNo", deviceNo);
	}

	public List<PdaDevice> findByCompanyId(Integer companyId) {
		return this.getSqlSession().selectList("Device_findByCompanyID", companyId);
	}
	
	public List<Object> findByCompanyIdAndPage(Map<Object, Object> map){
		return this.getSqlSession().selectList("Device_findByCompanyIdAndPage", map);
	}
	public int findCountByCompanyId(Integer company_Id){
		return (Integer) this.getSqlSession().selectOne("Device_getCount",company_Id);
	}
	
}
