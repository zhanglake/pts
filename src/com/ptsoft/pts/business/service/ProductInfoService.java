package com.ptsoft.pts.business.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptsoft.common.base.BaseService;
import com.ptsoft.common.base.EntityDao;
import com.ptsoft.common.util.Pageable;
import com.ptsoft.common.util.SysConfig;
import com.ptsoft.pts.PisConstants;
import com.ptsoft.pts.business.dao.ProductDao;
import com.ptsoft.pts.business.dao.ProductInfoDao;
import com.ptsoft.pts.business.dao.QRDao;
import com.ptsoft.pts.business.model.vo.Product;
import com.ptsoft.pts.business.model.vo.ProductInfo;
import com.ptsoft.pts.business.model.vo.QRCode;
import com.ptsoft.pts.util.HttpUtil;

@Service
public class ProductInfoService extends BaseService<ProductInfo, Integer> 
{

	@Autowired
	private ProductInfoDao productInfoDao;
	@Autowired
	private QRDao qrDao;
	@Autowired
	private ProductDao productDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected EntityDao getEntityDao() 
	{
		return this.productInfoDao;
	}

	@Override
	public void save(ProductInfo entity) 
	{
		if(entity.getId() == 0)
		{
			this.productInfoDao.insert(entity);
		}
	}

	public List<Object> findAllBySearchParam(int supplierId, String searchParam, String fmtm, String totm,Pageable pageable) 
	{
		int begin = pageable.getOffset() + 1;
		int end = pageable.getOffset() + pageable.getLimit();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		
		map.put("supplierId", String.valueOf(supplierId));
		map.put("searchParam", "%" + searchParam + "%");
		map.put("frmTm", fmtm);
		map.put("toTm", totm);
		map.put("begin", String.valueOf(begin));
		map.put("end", String.valueOf(end));
		
		return this.productInfoDao.findAllBySearchParam(map);
	}
	
	public int getInfoCount(int supplierId, String searchParam, String fmtm, String totm)
	{
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		
		map.put("supplierId", String.valueOf(supplierId));
		map.put("searchParam", "%" + searchParam + "%");
		map.put("frmTm", fmtm);
		map.put("toTm", totm);
		
		return this.productInfoDao.getInfoCount(map);
	}

	public String saveAndBindQRCode(ProductInfo productInfo, int number, int productId, int supplierId) throws Exception
	{
		String msg = "";
		/*try 
		{*/
			Product product = this.productDao.getById(productId);
			QRCode qrcode = new QRCode();
			List<QRCode> qrList = this.qrDao.getCanUsedByPkgId(product.getPackageID(), supplierId, number);
			if(qrList.size() < number)
			{
				return "1";
			}
					
			for (int j = 0; j < number; j++) 
			{
				qrcode = qrList.get(j);
				
				//保存生产信息
				productInfo.setPackage_time(productInfo.getProduce_time());
				productInfo.setQrCode(qrcode.getQrcode());
				productInfo.setSerialNo("PI" + product.getCode() + System.currentTimeMillis());
				this.productInfoDao.save(productInfo);
						
				//更新二维码状态及产品ID
				qrcode.setStatus(PisConstants.QRCodeStatus.Binded.getKey());
				qrcode.setProductId(product.getId());
				qrcode.setStsName(PisConstants.QRCodeStatus.Binded.getText());
				this.qrDao.update(qrcode);
			}
			msg = "2";
		/*} 
		catch (Exception e) 
		{
			msg = "0";
		}*/
		return msg;
	}

	public String hasProductInfo(int supplierId, int productId, int number) 
	{
		int count = this.productInfoDao.hasProductInfo(productId, supplierId);

		if(count >= number)
		{
			return "1";
		}
		else
		{
			return "0";
		}
	}

	public ProductInfo getByQrCode(String qrCode) 
	{
		return this.productInfoDao.getByQrCode(qrCode);
	}

	@SuppressWarnings({ "static-access", "unchecked" })
	public HashMap<String, Object> getProductInfoFromMS(String qrcodes)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		String url = SysConfig.get_ms_url() + "api/product/getProductInfo.do";
		try 
		{
			String response = HttpUtil.executePost(url, qrcodes, null);
			JSONObject jsonObj = JSONObject.fromObject(response);
			String code = jsonObj.getString("code");
			
			if(null != code && !"".equals(code) && code.equals("200"))
			{
				JSONArray productInfo = jsonObj.getJSONArray("productInfo");
				
				List<ProductInfo> productInfos = productInfo.toList(productInfo, ProductInfo.class);
				
				map.put("productInfos", productInfos);
				map.put("code", 200);
				map.put("msg", "Success");
			}
			else
			{
				map.put("code", 401);
				map.put("msg", "Failed");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return map;
	}

	public ArrayList<Object> getForExcel(int supplierId, String searchParam, String fmtm, String totm) 
	{
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("supplierId", String.valueOf(supplierId));
		map.put("searchParam", "%" + searchParam + "%");
		map.put("frmTm", fmtm);
		map.put("toTm", totm);
		
		return this.productInfoDao.getForExcel(map);
	}

	public int getCounter(String qrcode) 
	{
		return this.productInfoDao.getCounter(qrcode);
	}

	public List<ProductInfo> isExist(String qrcode) 
	{
		return this.productInfoDao.isExist(qrcode);
	}
}
