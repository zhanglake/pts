package com.ptsoft.pts.business.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ptsoft.common.base.BaseMybatisDao;
import com.ptsoft.pts.business.model.vo.Product;

@Repository
public class ProductDao extends BaseMybatisDao<Product, Integer>
{

	@Override
	protected String getMybatisMapperPrefix() 
	{
		return "Product";
	}

	@Override
	public List<Product> findAll() 
	{
		return this.getSqlSession().selectList("Product_findAll");
	}

	public List<Product> findByCompIdAndLike(int compId, String searchParam, int begin, int end) 
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("compId", String.valueOf(compId));
		map.put("searchParam", "%" + searchParam + "%");
		map.put("begin", String.valueOf(begin));
		map.put("end", String.valueOf(end));
		
		return this.getSqlSession().selectList("Product_findByCompIdAndLike", map);
	}

	public int getByPId(int proId) 
	{
		return (Integer) this.getSqlSession().selectOne("Product_getByPId", proId);
	}

	public Product getByCode(String code) 
	{
		return (Product) this.getSqlSession().selectOne("Product_getByCode", code);
	}

	public List<Product> getByCompIdAndSts(int compId, int sts) 
	{
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("compId", compId);
		map.put("sts", sts);
		
		return this.getSqlSession().selectList("Product_getByCompIdAndSts", map);
	}

	public void updatePackage(String proId, String packageID) 
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("packageID", packageID);
		map.put("proId", proId);
		
		this.getSqlSession().update("Product_updatePackage", map);
	}

	public List<Product> findBySupplierId(int supplierId)
	{
		return this.getSqlSession().selectList("Product_findBySupplierId", supplierId);
	}

	public List<Product> findAll(HashMap<String, String> map) 
	{
		return this.getSqlSession().selectList("Product_findAllByCompId", map);
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getByInCode(String inCode) 
	{
		return (HashMap<String, Object>) this.getSqlSession().selectOne("Product_getByInCode", inCode);
	}

	public List<Product> findItemsForModel(int orderId) 
	{
		return this.getSqlSession().selectList("Product_findItemsForModel", orderId);
	}

	public int getSizeByParam(HashMap<String, String> map) 
	{
		return (Integer) this.getSqlSession().selectOne("Product_getSizeByParam", map);
	}

	public List<Product> findIdAndCode(int companyId) 
	{
		return this.getSqlSession().selectList("Product_findIdAndCode", companyId);
	}

	public Product getByIdForApi(int productId) 
	{
		return (Product) this.getSqlSession().selectOne("Product_getByIdForApi", productId);
	}

	public Product getByCodeAndName(String productCode, String productName)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("productCode", productCode);
		map.put("productName", productName);
		return (Product) this.getSqlSession().selectOne("Product_getByCodeAndName", map);
	}

	public Product getBySapAndName(String sapNo, String productName) 
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("sapNo", sapNo);
		map.put("productName", productName);
		return (Product) this.getSqlSession().selectOne("Product_getBySapAndName", map);
	}

	public int getCountByOrderId(String orderId) 
	{
		return (Integer) this.getSqlSession().selectOne("Product_getCountByOrderId", orderId);
	}

	public List<Product> getListByOrderId(Map<String, String> map)
	{
		return this.getSqlSession().selectList("Product_getListByOrderId", map);
	}

	public ArrayList<Object> exportList(HashMap<String, Object> map) 
	{
		return (ArrayList<Object>) this.getSqlSession().selectList("Product_exportList", map);
	}

	public ArrayList<Object> exportByOrderId(String orderId) 
	{
		return (ArrayList<Object>) this.getSqlSession().selectList("Product_exportByOrderId", orderId);
	}

}
