package com.ptsoft.pts.business.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ptsoft.common.base.BaseMybatisDao;
import com.ptsoft.pts.business.model.vo.ProductInfo;

@Repository
public class ProductInfoDao extends BaseMybatisDao<ProductInfo, Integer> 
{

	@Override
	protected String getMybatisMapperPrefix() 
	{
		return "ProductInfo";
	}
	@Override
	public List<ProductInfo> findAll() {
		return null;
	}

	public List<Object> findAllBySearchParam(HashMap<Object,Object> map) 
	{
		return this.getSqlSession().selectList("ProductInfo_findAllBySupplier", map);
	}
	
	public int getInfoCount(HashMap<Object, Object> map) {
		return (Integer) this.getSqlSession().selectOne("ProductInfo_getCountBySearch", map);
	}

	public void save(ProductInfo productInfo)
	{
		this.getSqlSession().insert("ProductInfo_insert", productInfo);
	}

	public int hasProductInfo(int productId, int supplierId) 
	{
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("productId", productId);
		map.put("supplierId", supplierId);
		
		return (Integer) this.getSqlSession().selectOne("ProductInfo_hasProductInfo", map);
	}

	public ProductInfo getByQrCode(String qrCode) 
	{
		return (ProductInfo) this.getSqlSession().selectOne("ProductInfo_getByQrCode", qrCode);
	}
	public ArrayList<Object> getForExcel(HashMap<Object, Object> map) 
	{
		return (ArrayList<Object>) this.getSqlSession().selectList("ProductInfo_getForExcel", map);
	}
	public int getCounter(String qrcode)
	{
		return (Integer) this.getSqlSession().selectOne("ProductInfo_getCounter", qrcode);
	}
	public void deleteLot(String[] deleteInfo)
	{
		this.getSqlSession().delete("ProductInfo_deleteLot", deleteInfo);
	}
	public void insertLot(List<ProductInfo> productInfoList) 
	{
		this.getSqlSession().insert("ProductInfo_insertLot", productInfoList);
	}
	public List<ProductInfo> isExist(String qrcode) 
	{
		return this.getSqlSession().selectList("ProductInfo_isExit", qrcode);
	}
}
