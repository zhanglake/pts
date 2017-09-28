package com.ptsoft.pts.business.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ptsoft.common.base.BaseMybatisDao;
import com.ptsoft.pts.business.model.vo.QRCode;

@Repository
public class QRDao extends BaseMybatisDao<QRCode, Integer>{

	@Override
	protected String getMybatisMapperPrefix() {
		return "QRCode";
	}

	@Override
	public List<QRCode> findAll() {
		return null;
	}

	public List<HashMap<String, Object>> findByCodeMap(String qrcode) {
		return this.getSqlSession().selectList("QRCode_findByCodeMap", qrcode);
	}
	
	public QRCode getByCode(String qrcode) {
		return (QRCode) this.getSqlSession().selectOne("QRCode_getByCode", qrcode);
	}
	

	public List<QRCode> getCanUsedByPkgId(int packageID, int supplierId, int number) 
	{
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("packageID", packageID);
		map.put("supplierId", supplierId);
		map.put("number", number);
		
		return this.getSqlSession().selectList("QRCode_getCanUsedByPkgId", map);
	}

	public List<Map<String, Object>> searchCodeToPrinted(int qrNum, int pkgId, int pkgLevel, int productId, int supplierId) 
	{
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("qrNum", qrNum);
		map.put("pkgId", pkgId);
		map.put("pkgLevel", pkgLevel);
		map.put("productId", productId);
		map.put("supplierId", supplierId);

		return this.getSqlSession().selectList("QRCode_searchCodeToPrinted", map);
	}

	/**
	 * 获取要打印的油品的二维码信息
	 * 2017-09-28 张正华
	 * @return
	 */
	public List<Map<String, Object>> searchOilCodeToPrinted(int qrNum, int pkgId, int pkgLevel, int productId, int supplierId) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("qrNum", qrNum);
		map.put("pkgId", pkgId);
		map.put("pkgLevel", pkgLevel);
		map.put("productId", productId);
		map.put("supplierId", supplierId);
		return this.getSqlSession().selectList("QRCode_searchCodeToPrinted_forOil", map);
	}

	public List<Map<String, Object>> searchCodeToPrintedOtherLevel(int qrNum, int pkgId, int pkgLevel, int productId, int supplierId) 
	{
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("qrNum", qrNum);
		map.put("pkgId", pkgId);
		map.put("pkgLevel", pkgLevel);
		map.put("productId", productId);
		map.put("supplierId", supplierId);

		return this.getSqlSession().selectList("QRCode_searchCodeToPrintedOtherLevel", map);
	}
	
	public void updateProductId(int productId, String qrCode) 
	{
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("qrCode", qrCode);
		map.put("productId", String.valueOf(productId));
		
		this.getSqlSession().update("QRCode_updateProductId", map);
	}

	public List<QRCode> getBySupplierId(int supplierId)
	{
		return this.getSqlSession().selectList("QRCode_getBySupplierId", supplierId);
	}

	public List<QRCode> getBySupplierIdAndCompId(int supplierId, int compId) 
	{
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("supplierId", supplierId);
		map.put("compId", compId);
		
		return this.getSqlSession().selectList("QRCode_getBySupplierIdAndCompId", map);
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getRemaining(HashMap<String, Integer> map) 
	{
		return (HashMap<String, Object>) this.getSqlSession().selectOne("QRCode_getRemaining", map);
	}

	public void recovery(HashMap<String, Integer> map)
	{
		this.getSqlSession().update("QRCode_recovery", map);
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getQrCodeCount(int supplierId) 
	{
		return (HashMap<String, Object>) this.getSqlSession().selectOne("QRCode_getQrCodeCount", supplierId);
	}

	public List<HashMap<String, Object>> getList(HashMap<String, String> map)
	{
		return this.getSqlSession().selectList("QRCode_getList", map);
	}

	public int getCount(HashMap<String, String> map) 
	{
		return (Integer) this.getSqlSession().selectOne("QRCode_getCount", map);
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getBySerialNo(String serialNo) 
	{
		return (HashMap<String, Object>) this.getSqlSession().selectOne("QRCode_getBySerialNo", serialNo);
	}

	public String getTodayMax(String today) 
	{
		return (String) this.getSqlSession().selectOne("QRCode_getTodayMax", today);
	}

	public int codeCanPrinted(int qrNum, int pkgId, int pkgLevel, int supplierId) 
	{
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("supplierId", supplierId);
		map.put("qrNum", qrNum);
		map.put("pkgId", pkgId);
		map.put("pkgLevel", pkgLevel);
		
		return (Integer) this.getSqlSession().selectOne("QRCode_codeCanPrinted", map);
	}

	public int otherLevelCanUsed(int qrNum, int pkgId, int pkgLevel, int supplierId) 
	{
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("supplierId", supplierId);
		map.put("qrNum", qrNum);
		map.put("pkgId", pkgId);
		map.put("pkgLevel", pkgLevel);
		
		return (Integer) this.getSqlSession().selectOne("QRCode_otherLevelCanUsed", map);
	}

	public void updateCanPrint(int qrNum, int pkgId, int pkgLevel, int supplierId) 
	{
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("supplierId", supplierId);
		map.put("qrNum", qrNum);
		map.put("pkgId", pkgId);
		map.put("pkgLevel", pkgLevel);
		
		this.getSqlSession().update("QRCode_updateCanPrint", map);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getByIdForPrint(String id)
	{
		return (Map<String, Object>) this.getSqlSession().selectOne("QRCode_getByIdForPrint", id);
	}

	public List<HashMap<String, Object>> getXls(HashMap<String, String> map)
	{
		return this.getSqlSession().selectList("QRCode_getXls", map);
	}

	public List<QRCode> getForMs(String orderNo, int supplierId, int companyId, int isSuccess) 
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("supplierId", supplierId);
		map.put("compId", companyId);
		map.put("orderNo", orderNo);
		map.put("isSuccess", isSuccess);
		
		return this.getSqlSession().selectList("QRCode_getForMs", map);
	}

	public void updateToMsSuccess(String qrcode, int isSuccess) 
	{
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("qrCode", qrcode);
		map.put("isSuccess", String.valueOf(isSuccess));
		
		this.getSqlSession().selectOne("QRCode_updateToMsSuccess", map);
	}

	public void updateSaleOrderId(String code, int saleOrderId) 
	{
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("qrCode", code);
		map.put("saleOrderId", String.valueOf(saleOrderId));
		
		this.getSqlSession().selectOne("QRCode_updateSaleOrderId", map);
	}

	public void updateLot(List<HashMap<String, Object>> list)
	{
		this.getSqlSession().update("QRCode_updateLot", list);
	}

	public void updateLotSts(List<HashMap<String, Object>> list)
	{
		this.getSqlSession().update("QRCode_updateLotSts", list);
	}

	public void updateMsQrcodes(List<HashMap<String, Object>> parameter) 
	{
		this.getSqlSession().update("Qrcode_updateMsQrcodes", parameter);
	}

	public void insertLot(List<QRCode> qrcodeList) 
	{
		this.getSqlSession().insert("Qrcode_insertLot", qrcodeList);
	}

	public void updateSaleOrderIdLot(List<HashMap<String, Object>> updates) 
	{
		this.getSqlSession().update("Qrcode_updateSaleOrderIdLot", updates);
	}

	public void test(HashMap<String, Object> map) 
	{
		this.getSqlSession().selectOne("Qrcode_test", map);
		String outs = map.get("outInfo").toString();
		System.out.println(outs);
	}

	public void updateByProc(HashMap<String, Object> updateQrCode)
	{
		this.getSqlSession().update("Qrcode_updateByProc", updateQrCode);
	}

}
