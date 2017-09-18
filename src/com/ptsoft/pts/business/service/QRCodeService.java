package com.ptsoft.pts.business.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptsoft.common.Constants;
import com.ptsoft.common.base.BaseService;
import com.ptsoft.common.base.EntityDao;
import com.ptsoft.common.util.Pageable;
import com.ptsoft.common.util.SysConfig;
import com.ptsoft.pts.PisConstants;
import com.ptsoft.pts.account.model.vo.SysUser;
import com.ptsoft.pts.basic.dao.SupplierDao;
import com.ptsoft.pts.basic.model.vo.Supplier;
import com.ptsoft.pts.business.dao.OrderDao;
import com.ptsoft.pts.business.dao.PackageRuleDao;
import com.ptsoft.pts.business.dao.PackageRuleMapDao;
import com.ptsoft.pts.business.dao.ProductDao;
import com.ptsoft.pts.business.dao.QRDao;
import com.ptsoft.pts.business.model.vo.Order;
import com.ptsoft.pts.business.model.vo.PackageRule;
import com.ptsoft.pts.business.model.vo.Product;
import com.ptsoft.pts.business.model.vo.QRCode;
import com.ptsoft.pts.system.dao.SysLogDao;
import com.ptsoft.pts.system.model.vo.SysLog;
import com.ptsoft.pts.util.DBUtil;
import com.ptsoft.pts.util.DesUtil;

@Service
public class QRCodeService extends BaseService<QRCode, Integer>
{
	
	private static final Logger logger = LoggerFactory.getLogger(QRCodeService.class);

	@Autowired
	private QRDao qrDao;
	@Autowired
	private PackageRuleMapDao packageRuleMapDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private SupplierDao supplierDao;
	@Autowired
	private SysLogDao sysLogDao;
	@Autowired
	private PackageRuleDao ruleDao;
	@Autowired
	private OrderDao orderDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected EntityDao getEntityDao() {
		return this.qrDao;
	}

	@Override
	public void save(QRCode entity) {
		this.qrDao.insert(entity);
	}
	
	public HashMap<String, Object> findByCodeMap(String qrcode){
		List<HashMap<String, Object>> list = this.qrDao.findByCodeMap(qrcode);
		return null == list || list.size() < 1 ? null : list.get(0);
	}
	
	public QRCode findByCode(String qrcode){
		return this.qrDao.getByCode(qrcode);
	}
	
	public List<Map<String, Object>> searchCodeToPrinted(int number, int productId, int pkgLevel, int pkgId, int supplierId)
	{
		List<Map<String, Object>> qrList = new ArrayList<Map<String, Object>>();
		String code = "";
		if(pkgLevel == 1)
		{
			qrList = this.qrDao.searchCodeToPrinted(number, pkgId, pkgLevel, productId, supplierId);
		}
		else
		{
			qrList = this.qrDao.searchCodeToPrintedOtherLevel(number, pkgId, pkgLevel, productId, supplierId);
		}
		
		for (int j = 0; j < qrList.size(); j++) 
		{
			code = qrList.get(j).get("QRCODE").toString();
			qrList.get(j).put("QRCODE", SysConfig.get_weifu_url() + DesUtil.encrypt(code, PisConstants.QRSalt));
		}
		return qrList;
	}

	public String recoveryQrcode(SysUser user, int supplierId, int pkgLevel, int number) 
	{
		String msg = "";
		SysLog sysLog = null;
		String cnt = "";
		String actionType = String.valueOf(PisConstants.LogActionType.RecoveryQrcode.getKey());
		
		try 
		{
			int status = PisConstants.QRCodeStatus.Void.getKey();
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			Supplier supplier = this.supplierDao.getById(supplierId);
			
			map.put("supplierId", supplierId);
			map.put("pkgLevel", pkgLevel);
			map.put("number", number);
			map.put("status", status);
			
			this.qrDao.recovery(map);
			msg = "1";
			cnt = "回收供应商" + supplier.getSupplier_name() + number + "个第" + pkgLevel + "层二维码";
			sysLog = new SysLog(user.getUsrNm(), user.getUsrId(), "回收二维码", cnt, 1, actionType, PisConstants.LogType.Business.getKey());
		} 
		catch (Exception e) 
		{
			msg = "0";
			e.printStackTrace();
			sysLog = new SysLog(user.getUsrNm(), user.getUsrId(), "回收二维码", e.getMessage(), 0, actionType, PisConstants.LogType.Exception.getKey());
		}
		this.sysLogDao.insert(sysLog);
		return msg;
	}

	public List<Map<String, Object>> searchOneLevelQr(String ids) 
	{
		List<Map<String, Object>> qrList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		String qrcode = "";
		int status = 0;
		
		String[] idArray = ids.split(",");
		
		for (int i = 0; i < idArray.length; i++) 
		{
			map = this.qrDao.getByIdForPrint(idArray[i]);
			status = Integer.parseInt(map.get("STATUS").toString());
			
			if(status <= 1)
			{
				qrcode = map.get("QRCODE").toString();
				map.put("QRCODE", SysConfig.get_weifu_url() + DesUtil.encrypt(qrcode, PisConstants.QRSalt));
				qrList.add(map);
			}
		}
		return qrList;
	}

	
	/**
	 * 下发二维码到MS
	 * @author jqi.can
	 * @date 2016-4-27下午03:31:23
	 */
	public HashMap<String, Object> issuedQrcodeToMS(List<String> orderNoList) throws Exception
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		SysLog log = null;
		List<QRCode> codeList = null;
		Order order = null;
		
		int action_type = PisConstants.LogActionType.IssueQrcode.getKey();
		int supplierId = this.supplierDao.getByCode(Constants.MS_SUPPLIER_CODE).getId();
		String orderId = "";
		String failedOrder = "";
		String noneOrder = "";
		Connection conn = DBUtil.getConn();
		for (int i = 0; i < orderNoList.size(); i++) 
		{
			if(null == conn)
			{
				/**
				 * 数据库连接失败 无法下发
				 */
				failedOrder += (order.getOrderNo() + ",");
				logger.error("----向MS下发二维码----订单" + order.getOrderNo() + "二维码下发MS失败...");
				log = new SysLog("系统", 2, "下发二维码", "订单" + order.getOrderNo() + "二维码下发MS失败", 1, String.valueOf(action_type), PisConstants.LogType.Business.getKey());
			}
			else
			{
				orderId = orderNoList.get(i);
				order = this.orderDao.getById(Integer.parseInt(orderId));
				codeList = this.qrDao.getForMs(orderId, supplierId, Constants.WEIFU_COMPANY_ID, 0);

				/**
				 * 有未下发的二维码 进行下发
				 */
				if(null != codeList && codeList.size() > 0)
				{
					String qrcode = "";
					String info = "";
					Product product = null;
					PackageRule rule = null;
					QRCode qrCode = null;
					int isExist;
					int successID;
					
					List<HashMap<String, Object>> updates = new ArrayList<HashMap<String,Object>>();
					HashMap<String, Object> update = new HashMap<String, Object>();
					
					for (int j = 0; j < codeList.size(); j++) 
					{
						successID = 0;
						qrCode = codeList.get(j);
						product = this.productDao.getById(qrCode.getProductId());
						//产品null 返回failure
						if(null == product)
						{
							map.put("code", "500");
							map.put("msg", "Failure");
							return map;
						}
						
						rule = this.ruleDao.getById(product.getPackageID());
						//包装规则null 返回failure
						if(null == rule)
						{
							map.put("code", "500");
							map.put("msg", "Failure");
							return map;
						}
							
						qrcode = SysConfig.get_weifu_url() + DesUtil.encrypt(qrCode.getQrcode(), PisConstants.QRSalt);
						//如果不存在 写到MS数据库
						isExist = DBUtil.queryIsExist(qrcode);
						if(isExist == 0)
						{
							//二维码 产品编码 包装规则 包装层级 创建日期 SAP号 产品名称
							info = qrcode + "," + product.getCode() + "," + rule.getCode() + "," + qrCode.getPkgLevel() + ","; 
							info += qrCode.getCreateTime() + "," + product.getSapNo() + "," + product.getName() + "," + order.getOrderNo();
							successID = DBUtil.insert(info);
							//写MS数据库成功 更新二维码状态并表示写入成功
							if(successID != 0)
							{
								/*this.qrDao.updateQrcodeStatus(qrCode.getQrcode(), 2);
								this.qrDao.updateToMsSuccess(qrCode.getQrcode(), 1);*/
								update = new HashMap<String, Object>();
								update.put("qrcode", qrCode.getQrcode());
								update.put("status", 2);
								update.put("msSuccess", 1);
								updates.add(update);
							}
						}
						else
						{
							logger.error("----向MS下发二维码----二维码" + qrCode.getQrcode() + "已下发...");
						}
					}
					
					logger.error("----向MS下发二维码----订单" + order.getOrderNo() + "二维码下发MS成功...");
					
					log = new SysLog("系统", 2, "下发二维码", "订单" + order.getOrderNo() + "二维码下发MS成功", 1, String.valueOf(action_type), PisConstants.LogType.Business.getKey());
					//二维码下发成功 更新订单状态
					this.orderDao.updateStatus(order.getId(), 1);
					if(null != updates && updates.size() > 0)
					{
						this.qrDao.updateMsQrcodes(updates);
					}
				}
				else
				{
					noneOrder +=  (order.getOrderNo() + ",");
					logger.error("----向MS下发二维码----订单" + order.getOrderNo() + "无二维码可下发至MS...");
					log = new SysLog("系统", 2, "下发二维码", "订单" + order.getOrderNo() + "无二维码可下发至MS", 0, String.valueOf(action_type), PisConstants.LogType.Business.getKey());
				}
				this.sysLogDao.insert(log);
			}
		}
		map.put("code", 200);
		map.put("msg", "OK");
		map.put("failedOrder", failedOrder);
		map.put("noneOrder", noneOrder);
		return map;
	}

	public HashMap<String, Object> getRemaining(int supplierId, int pkgLevel)
	{
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("supplierId", supplierId);
		map.put("pkgLevel", pkgLevel);
		map.put("pkgId", -1);
		
		return this.qrDao.getRemaining(map);
	}

	public List<HashMap<String, Object>> getList(int companyId, String searchParam, Pageable pageable) 
	{
		int begin = pageable.getOffset() + 1;
		int end = pageable.getOffset() + pageable.getLimit();
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("searchParam", searchParam);
		map.put("companyId", String.valueOf(companyId));
		map.put("begin", String.valueOf(begin));
		map.put("end", String.valueOf(end));
		
		return this.qrDao.getList(map);
	}
	
	public int getCount(int companyId, String searchParam) 
	{
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("searchParam", searchParam);
		map.put("companyId", String.valueOf(companyId));
		
		return this.qrDao.getCount(map);
	}

	public HashMap<String, Object> getQrCodeCount(int supplierId) 
	{
		return this.qrDao.getQrCodeCount(supplierId);
	}

	public List<HashMap<String, Object>> showLeft(int supplierId, int productId) 
	{
		List<HashMap<String, Object>> result = new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> map;
		HashMap<String, Integer> searchMap = new HashMap<String, Integer>();
		
		Product product = this.productDao.getById(productId);
		List<HashMap<String, Object>> list = packageRuleMapDao.getByRuleIdForApi(product.getPackageID());
		int pkgId, pkgLevel, recruitNum, bindedNum;
		searchMap.put("supplierId", supplierId);
		
		for (HashMap<String, Object> hashMap : list) 
		{
			pkgId = Integer.parseInt(hashMap.get("ID").toString());
			pkgLevel = Integer.parseInt(hashMap.get("PKGLEVEL").toString());
			
			searchMap.put("pkgId", pkgId);
			searchMap.put("pkgLevel", pkgLevel);
			recruitNum = Integer.parseInt(qrDao.getRemaining(searchMap).get("RECRUITNUM").toString());
			bindedNum = Integer.parseInt(qrDao.getRemaining(searchMap).get("BINDEDNUM").toString());
			
			map = new HashMap<String, Object>();
			map.put("pkgLevel", pkgLevel);
			map.put("recruitNum", recruitNum);
			map.put("bindedNum", bindedNum);
			result.add(map);
			
		}
		
		return result;
	}

	public HashMap<String, Object> getBySerialNo(String serialNo)
	{
		HashMap<String, Object> map = this.qrDao.getBySerialNo(serialNo);
		if(map != null)
		{
			map.put("QRCODE", SysConfig.get_weifu_url() + DesUtil.encrypt(map.get("QRCODE").toString(), PisConstants.QRSalt));
		}
		return map;
	}

	public String updatePrinted(String[] qrcodes)
	{
		String msg = "";
		try 
		{
			QRCode qrcode;
			for (String qrId : qrcodes) 
			{
				qrcode = new QRCode();
				qrcode.setId(Integer.parseInt(qrId));
				qrcode.setStatus(2);
				this.qrDao.update(qrcode);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return msg;
	}

	public List<HashMap<String, Object>> getXls(String searchParam, int companyId) 
	{
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("searchParam", searchParam);
		map.put("companyId", String.valueOf(companyId));
		
		return this.qrDao.getXls(map);
	}
	
}
