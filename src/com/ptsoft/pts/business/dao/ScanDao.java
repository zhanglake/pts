package com.ptsoft.pts.business.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ptsoft.common.base.BaseMybatisDao;
import com.ptsoft.pts.business.model.vo.ScanRecord;

@Repository
public class ScanDao extends BaseMybatisDao<ScanRecord, Integer>{

	@Override
	protected String getMybatisMapperPrefix() {
		return "Scan";
	}

	@Override
	public List<ScanRecord> findAll() {
		return null;
	}
	
	public int getScanCount(HashMap<String, String> map){
		return (Integer)this.getSqlSession().selectOne("Scan_getScanCount",map);
	}
	
	public ScanRecord getLastByQrcode(HashMap<String,String> map){
		return (ScanRecord)this.getSqlSession().selectOne("Scan_getByQrcode", map);
	}
	
	public Object scanQuery(String qrcode){
		return this.getSqlSession().selectOne("Scan_getScanInfo", qrcode);
	}
	
	public ScanRecord getByCode(HashMap<String, String> map){
		return (ScanRecord)this.getSqlSession().selectOne("Scan_getByCode", map);
	}

	public List<Object> findPackageList(HashMap<String, String> map) 
	{
		return this.getSqlSession().selectList("Scan_findPackageList", map);
	}

	public List<Object> findRecordByAction(HashMap<String, String> map) 
	{
		return this.getSqlSession().selectList("Scan_findRecordByAction", map);
	}

	public List<ScanRecord> getRecordByQrcode(HashMap<String, String> map) 
	{
		return this.getSqlSession().selectList("Scan_getRecordByQrcode", map);
	}

	public int getCountByComp(String companyId, int actionType) 
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("companyId", companyId);
		map.put("actionType", String.valueOf(actionType));
		
		return (Integer) this.getSqlSession().selectOne("Scan_getCountByComp", map);
	}

	public int getRecieveCount(int dealerId) 
	{
		return (Integer) this.getSqlSession().selectOne("Scan_getRecieveCount", dealerId);
	}

	public int findPkgCount(HashMap<String, String> map) 
	{
		return (Integer) this.getSqlSession().selectOne("Scan_findPkgCount", map);
	}

	public int findRecordCount(HashMap<String, String> map)
	{
		return (Integer) this.getSqlSession().selectOne("Scan_findRecordCount", map);
	}

	public int getEndUser(int rlId, String qrcode) 
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("rlId", String.valueOf(rlId));
		map.put("qrCode", qrcode);
		
		return (Integer) this.getSqlSession().selectOne("Scan_getEndUser", map);
	}

	public List<Object> getActionRecordXls(HashMap<String, String> map)
	{
		return this.getSqlSession().selectList("Scan_getActionRecordXls", map);
	}

	public List<Object> packageListXls(HashMap<String, String> map)
	{
		return this.getSqlSession().selectList("Scan_pacakgeListXls", map);
	}

	public List<Object> getPointDetails(HashMap<String, String> map) 
	{
		return this.getSqlSession().selectList("Scan_getPointDetails", map);
	}

	public List<ScanRecord> getOperate(HashMap<String, String> map) 
	{
		return this.getSqlSession().selectList("Scan_getOperate", map);
	}

	public void deleteLot(HashMap<String, Object> param) 
	{
		this.getSqlSession().delete("Scan_deleteLot", param);
	}

	public void insertLot(List<ScanRecord> records) 
	{
		this.getSqlSession().insert("Scan_insertLot", records);
	}

	public void insertByProc(HashMap<String, Object> scanInfo)
	{
		this.getSqlSession().insert("Scan_insertByProc", scanInfo);
	}
	
}
