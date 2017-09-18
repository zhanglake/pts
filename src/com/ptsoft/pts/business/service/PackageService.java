package com.ptsoft.pts.business.service;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptsoft.common.base.BaseService;
import com.ptsoft.common.base.EntityDao;
import com.ptsoft.common.util.DateUtil;
import com.ptsoft.common.util.SysConfig;
import com.ptsoft.common.util.Pageable;
import com.ptsoft.pts.PisConstants;
import com.ptsoft.pts.PisConstants.QRCodeStatus;
import com.ptsoft.pts.account.model.vo.SysUser;
import com.ptsoft.pts.business.dao.PackageDao;
import com.ptsoft.pts.business.dao.PkgCodeDao;
import com.ptsoft.pts.business.dao.ProductInfoDao;
import com.ptsoft.pts.business.dao.QRDao;
import com.ptsoft.pts.business.dao.ScanDao;
import com.ptsoft.pts.business.model.vo.PkgCodeMap;
import com.ptsoft.pts.business.model.vo.ProductInfo;
import com.ptsoft.pts.business.model.vo.QRCode;
import com.ptsoft.pts.business.model.vo.ScanRecord;
import com.ptsoft.pts.business.model.vo.SyPackage;
import com.ptsoft.pts.system.dao.SysDataTypeDao;
import com.ptsoft.pts.util.DesUtil;

@Service
public class PackageService extends BaseService<SyPackage, Integer>
{

	@Autowired
	private PackageDao packageDao;
	@Autowired
	private SysDataTypeDao dataTypeDao;
	@Autowired
	private ScanDao scanDao;
	@Autowired
	private PkgCodeDao pkgCodeDao;
	@Autowired
	private QRDao qrDao;
	@Autowired
	private ProductInfoService productInfoService;
	@Autowired
	private ProductInfoDao productInfoDao;
	
	Logger logger = Logger.getLogger(PackageService.class);
	
	@SuppressWarnings("rawtypes")
	@Override
	protected EntityDao getEntityDao() 
	{
		return this.packageDao;
	}

	@Override
	public void save(SyPackage entity) 
	{
		
	}

	public List<Object> findByCompIdAndLike(int compId, String searchParam, Pageable pageable) 
	{
		int begin = pageable.getOffset() + 1;
		int end = pageable.getOffset() + pageable.getLimit();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("searchParam", "%" + searchParam + "%");
		map.put("compId", compId);
		map.put("begin", String.valueOf(begin));
		map.put("end", String.valueOf(end));		
		return this.packageDao.findByCompIdAndLike(map);
	}
	/**
	 * 根据条件查询总条数
	 * @param compId
	 * @param searchParam
	 * @return
	 */
	public int getCountByCompIdAndSearchParam(int compId, String searchParam) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("searchParam", "%" + searchParam + "%");
		map.put("compId", compId);
		return this.packageDao.getCountByCompIdAndSearchParam(map);
	}
	public String savePackage(SyPackage syPackage, int compId) 
	{
		String msg = "包装定义保存成功！";
		try 
		{
			int num = this.packageDao.getByIdForNum(syPackage.getId());
			SyPackage pkg = this.packageDao.getByCode(syPackage.getCode());
			
			if(pkg != null && pkg.getId() != syPackage.getId())
			{
				return "编码已存在，请重新输入！";
			}
			
			if(num > 0)
			{
				/**
				 * 禁用包装定义 判断是否有可用状态的包装规则
				 * 启用包装定义 判断二维码尺寸是否可用状态
				 */
				int canUpdateSts = this.packageDao.getByIDCanUpdateSts(syPackage.getId());
				int qrAvailable = this.dataTypeDao.getNumByDctcd(syPackage.getQrarer_size());
				SyPackage pack = this.packageDao.getById(syPackage.getId());
				if(canUpdateSts > 0 && syPackage.getSts() == 0)
				{
					return "使用该包装定义的包装规则处于可用状态，不能禁用！";
				}
				if (pack.getSts() == 0 && syPackage.getSts() == 1) 
				{
					if(qrAvailable > 0)
					{
						return "该包装定义的二维码尺寸仍处于禁用状态，无法更新包装定义为可用状态！";
					}
				}
				this.packageDao.update(syPackage);
			}
			else
			{
				syPackage.setCompany_id(compId);
				this.packageDao.insert(syPackage);
			}
		} 
		catch (Exception e) 
		{
			msg = "包装定义保存失败！";
			e.printStackTrace();
		}
		return msg;
	}

	public List<SyPackage> findAllBySts(int sts)
	{
		return this.packageDao.findAllBySts(sts);
	}

	public HashMap<String, Object> savePkgRecord(String outerCode, String innerCodes, int type, SysUser user, String deviceNo) throws Exception
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		//HashMap<String, String> param = new HashMap<String, String>();
		ScanRecord entity = new ScanRecord();
		
		if(type != 3)
		{
			outerCode = outerCode.replace(SysConfig.get_weifu_url(), "");
			outerCode = DesUtil.decrypt(outerCode, PisConstants.QRSalt);
			QRCode qrCode = this.qrDao.getByCode(outerCode);
			
			if(outerCode.equals("0") || qrCode == null)
			{
				System.out.println("========包装失败，条码无效========");
				map.put("code", 0);
				map.put("msg", "包装失败，条码无效");
				map.put("msg_en", "Package failure. Invalid barcode.");
				map.put("msg_code", 1001);
				return map;
			}
			
			if(qrCode.getStatus() >= PisConstants.QRCodeStatus.Packaged.getKey())
			{
				logger.error("========外包装状态不正确========");
				map.put("code", 0);
				map.put("msg", "包装失败，条码已包装");
				map.put("msg_en", "Package failure. The product was packed.");
				map.put("msg_code", 1002);
				return map;
			}
			
			//更新二维码状态为已包装
			logger.error(qrCode.getStatus() + "=======外包装二维码状态---包装更新前");
			qrCode.setStatus(QRCodeStatus.Packaged.getKey());
			qrCode.setStsName(QRCodeStatus.Packaged.getText());		
			qrDao.update(qrCode);
			logger.error(qrCode.getStatus() + "=======外包装二维码状态---包装更新后");
			
			entity.setQrcode(outerCode);
			entity.setOperatorId(user.getUsrId());
			entity.setOperator(user.getLgnNm());
			entity.setCreateTime(DateUtil.getCurrentTime());
			entity.setActionType(PisConstants.ActionType.Package.getKey());//actionType 包装
			entity.setActionName(PisConstants.ActionType.Package.getText());
			entity.setDeviceNo(deviceNo);
			scanDao.insert(entity);
		}
		
		//判断条码是否是MS装配系统且企业为威孚
		//InsertProductInfoIsMs(outerCode, innerCodes);
		
		if(type == 2 || type == 3) //多层包装
		{
			logger.error("---内包判断开始--");
			for (String code : innerCodes.split(",")) 
			{
				code = code.replace(SysConfig.get_weifu_url(), "");
				code = DesUtil.decrypt(code, PisConstants.QRSalt);
				QRCode qr = this.qrDao.getByCode(code);
				
				if(qr.getPkgLevel() == 1)
				{
					if(qr.getStatus() >= PisConstants.QRCodeStatus.Packaged.getKey())
					{
						System.out.println("========第一层已包装========");
						map.put("code", 0);
						map.put("msg", "包装失败，内包装已包装");
						map.put("msg_en", "Package failure. The product was packed.");
						map.put("msg_code", 1002);
						return map;
					}
				}
				else if(qr.getPkgLevel() >= 2)
				{
					if(qr.getStatus() != PisConstants.QRCodeStatus.Packaged.getKey())
					{
						System.out.println("====三层及以上====内层包装未包装========");
						map.put("code", 0);
						map.put("msg", "包装失败，内包装未包装");
						map.put("msg_en", "Package failure. The product was unpacked.");
						map.put("msg_code", 1002);
						return map;
					}
				}
			}
			logger.error("---内包判断结束--");
			
			logger.error("---内包包装开始--");
			PkgCodeMap codeMap = new PkgCodeMap();
			String lastCode = "";
			for(String code : innerCodes.split(","))
			{
				code = code.replace(SysConfig.get_weifu_url(), "");
				code = DesUtil.decrypt(code, PisConstants.QRSalt);
				lastCode = code;
				QRCode qr = this.qrDao.getByCode(code);
				//更新二维码状态为已包装
				qr.setStatus(QRCodeStatus.Packaged.getKey());
				qr.setStsName(QRCodeStatus.Packaged.getText());
				qrDao.update(qr);
				
				/*param.put("qrcode", code);
				param.put("userId", String.valueOf(user.getUsrId()));
				param.put("actionType", String.valueOf(PisConstants.ActionType.Package.getKey()));
				
				entity = this.scanDao.getByCode(param);
				if(null == entity)
				{*/
				// 插入包装记录
				entity = new ScanRecord();
				entity.setQrcode(code);
				entity.setOperatorId(user.getUsrId());
				entity.setOperator(user.getLgnNm());
				entity.setCreateTime(DateUtil.getCurrentTime());
				entity.setActionType(PisConstants.ActionType.Package.getKey());
				entity.setActionName(PisConstants.ActionType.Package.getText());
				scanDao.insert(entity);
				//插入包装关系
				codeMap.setInnerCode(code);
				codeMap.setOuterCode(outerCode);
				pkgCodeDao.insert(codeMap);
				//}
			}
			if(type == 2)
			{
				
				ProductInfo outInfo = this.productInfoService.getByQrCode(outerCode);
				if(outInfo == null)
				{
					ProductInfo productInfo = this.productInfoService.getByQrCode(lastCode);
					if(productInfo != null)
					{
						//插入一条生产信息
						ProductInfo pInfo = productInfo;
						pInfo.setId(0);
						pInfo.setQrCode(outerCode);
						productInfo.setSerialNo("PI" + productInfo.getProduct_code() + System.currentTimeMillis());
						this.productInfoService.save(pInfo);
						
						//更新二维码绑定产品ID
						this.qrDao.updateProductId(productInfo.getProduct_id(), outerCode);
					}
				}
			}
			logger.error("---内包包装结束--");
		}
		
		map.put("code", 1);
		map.put("msg", "包装成功！");
		map.put("msg_en", "Packing success.");
		map.put("msg_code", "1003");
		
		return map;
	}

	/**
	 * 判断条码是否是MS装配系统且企业为威孚
	 * 调MS接口取生产信息数据并插入
	 * 同时更新二维码产品ID
	 * @author jqi.can
	 * @date 2016-4-28下午03:01:51
	 *//*
	@SuppressWarnings({ "static-access", "unchecked" })
	public void InsertProductInfoIsMs(String outerCode, String innerCodes)
	{
		QRCode qrCode = this.qrDao.getByCode(outerCode);
		int supplierId = this.supplierDao.getByCode("40000001").getId();
		if(qrCode.getCompanyId() == 2 && qrCode.getSupplierId() == supplierId)
		{
			String url = SysConfig.get_ms_url() + "api/product/getProductInfo.do";
			String encryptQrCode = "";
			encryptQrCode = SysConfig.get_weifu_url() + DesUtil.encrypt(outerCode, PisConstants.QRSalt);
			try 
			{
				String response = HttpUtil.executePost(url, encryptQrCode, null);
				JSONObject urlJsonObj = JSONObject.fromObject(response);
				String urlCode = urlJsonObj.getString("code");
				System.out.println("===========MS外层二维码查询============");
				if(null != urlCode && !"".equals(urlCode) && urlCode.equals("200"))
				{
					ProductInfo outerProductInfo = (ProductInfo) JSONObject.toBean((JSONObject) urlJsonObj.get("productInfo"), ProductInfo.class);
					JSONArray innersArray;
					List<ProductInfo> innerProductInfoList;
					Product product;
					System.out.println("===========MS外层二维码查询成功============");
					
					*//**
					 * 从MS那边获取的信息 根据ID查找父级codeID即outerQrcodeId是该二维码的 抽取其内层生产信息
					 * 如果内层生产信息为空 表示该二维码为单层包装二维码 即写入该二维码生产信息 
					 * 不为空 表示多层包装二维码 
					 * 需写入内层二维码生产信息 同时写入二维码包装关系
					 *//*
					url = SysConfig.get_ms_url() + "api/product/getInner.do?outId=" + outerProductInfo.getId();
					response = HttpUtil.executePost(url, null, null);
					urlJsonObj = JSONObject.fromObject(response);
					urlCode = urlJsonObj.getString("code");
					System.out.println("===========MS内层二维码查询============");
					
					if (null != urlCode && !"".equals(urlCode) && urlCode.equals("200"))
					{
						innersArray = urlJsonObj.getJSONArray("inners");
						innerProductInfoList = innersArray.toList(innersArray, ProductInfo.class);
						System.out.println("===========MS内层二维码查询成功============");
						
						 判断是否有内层二维码 有则逐条写入生产信息 否则写入当前二维码生产信息 
						if (null != innerProductInfoList && innerProductInfoList.size() > 0) 
						{
							System.out.println("===========MS查询内层二维码不为空 写入内层二维码生产信息============");
							for (ProductInfo innerProductInfo : innerProductInfoList) 
							{
								product = this.productDao.getByCode(innerProductInfo.getProductCode());
								this.insertProduceInfo(innerProductInfo);
								this.insertPackageQrcodeMap(innerProductInfo.getQrCode(), outerCode);
								this.qrDao.updateProductId(product.getId(), innerProductInfo.getQrCode());
							}
							System.out.println("===========MS内层二维码生产信息写入成功============");
						} 
						//写入当前层生产信息、包装记录 同时更新二维码绑定产品ID
						product = this.productDao.getByCode(outerProductInfo.getProductCode());
						this.insertProduceInfo(outerProductInfo);
						this.qrDao.updateProductId(product.getId(), outerCode);
						findInToInsertScanRecord(outerCode, user, actionType, qrcodeStatus, deviceNo);
					}
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}*/

	public SyPackage findMinPackage(int packageID)
	{
		return this.packageDao.findMinPackage(packageID);
	}

}
