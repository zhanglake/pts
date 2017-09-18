package com.ptsoft.pts.basic.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptsoft.common.annotation.ServiceLog;
import com.ptsoft.common.util.DateUtil;
import com.ptsoft.common.util.Pageable;
import com.ptsoft.common.util.SysConfig;
import com.ptsoft.pts.PisConstants;
import com.ptsoft.pts.PisConstants.ActionType;
import com.ptsoft.pts.PisConstants.QRCodeStatus;
import com.ptsoft.pts.account.dao.SysUserDao;
import com.ptsoft.pts.account.model.vo.SysUser;
import com.ptsoft.pts.basic.dao.PointFollowingDao;
import com.ptsoft.pts.basic.dao.SupplierDao;
import com.ptsoft.pts.basic.model.vo.PointFollowing;
import com.ptsoft.pts.business.dao.PackageDao;
import com.ptsoft.pts.business.dao.PkgCodeDao;
import com.ptsoft.pts.business.dao.ProductDao;
import com.ptsoft.pts.business.dao.ProductInfoDao;
import com.ptsoft.pts.business.dao.QRDao;
import com.ptsoft.pts.business.dao.SaleOrderDetailDao;
import com.ptsoft.pts.business.dao.SalesOrderDao;
import com.ptsoft.pts.business.dao.ScanDao;
import com.ptsoft.pts.business.model.vo.MSQrCode;
import com.ptsoft.pts.business.model.vo.PkgCodeMap;
import com.ptsoft.pts.business.model.vo.Product;
import com.ptsoft.pts.business.model.vo.ProductInfo;
import com.ptsoft.pts.business.model.vo.QRCode;
import com.ptsoft.pts.business.model.vo.SaleOrderDetail;
import com.ptsoft.pts.business.model.vo.SalesOrder;
import com.ptsoft.pts.business.model.vo.ScanRecord;
import com.ptsoft.pts.system.dao.SysDataTypeDao;
import com.ptsoft.pts.system.model.vo.SysDataType;
import com.ptsoft.pts.util.DBUtil;
import com.ptsoft.pts.util.DesUtil;
import com.ptsoft.pts.util.HttpUtil;

@Service
public class TraceService
{
	
	Logger logger = Logger.getLogger(TraceService.class);
	
	@Autowired
	private ScanDao scanDao;
	@Autowired
	private QRDao qrDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private SysUserDao userDao;
	@Autowired
	private PkgCodeDao pkgCodeDao;
	@Autowired
	private PackageDao packageDao; 
	@Autowired
	private SaleOrderDetailDao saleOrderDetailDao;
	@Autowired
	private SalesOrderDao salesOrderDao;
	@Autowired
	private SysDataTypeDao dataTypeDao;
	@Autowired
	private SupplierDao supplierDao;	
	@Autowired
	private ProductInfoDao productInfoDao;
	@Autowired
	private PointFollowingDao pointFollowingDao;
	
///////////////////////zxfupdate20160818
	/**
	 * 积分明细查询
	 * zxf
	 * 根据userID查询所有积分，一般用不到
	 **/
    @ServiceLog(description="积分明细")
	public List<Object> queryPointDetail(String userid)
	{
		return this.pointFollowingDao.query_Allpointbyuserid(userid);
	}

    /**
	 * 积分明细查询分页
	 * zxf
	 * 根据userID查询积分，传入三个参数
	 * @param userId 用户ID 
	 * @param page 第几页，比如第二页，第三页
	 * @param num  每页数量 
	 **/
    @ServiceLog(description="积分明细分页")
	public List<Object> queryPointDetailpage(String userId,int page,int num)
	{
    	int begin;
    	if(page==1)
    	{
    	   begin= 1;
    	}else {
    		begin= num*page-num+1;
		}
		int end = num*page;
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("begin", String.valueOf(begin));
		map.put("end", String.valueOf(end));
		map.put("userId",userId);
		return pointFollowingDao.query_Allpointbyuserid_count(map);
	}
///////////////////////zxfupdate20160818

	public void save(ScanRecord entity)
	{
		this.scanDao.insert(entity);
	}
	
	public int getScanCount(String qrcode, int actionType)
	{
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("qrcode", qrcode);
		map.put("actionType", String.valueOf(actionType));
		return this.scanDao.getScanCount(map);
	}

	@ServiceLog(description="扫码溯源")
	public HashMap<String, Object> scanQuery(String qrcode, SysUser user, int scanType, String company) throws Exception 
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		boolean isScanAll = false; //true 威孚集团 可扫任何企业二维码，false 其他集团 仅能扫自己企业二维码 
		qrcode = qrcode.replace(SysConfig.get_weifu_url(), "");
		qrcode = DesUtil.decrypt(qrcode, PisConstants.QRSalt);
		QRCode code = qrDao.getByCode(qrcode);
		
		SysDataType dataType = dataTypeDao.getById(company);
		if(dataType.getTpid() == 400)
		{
			isScanAll = true;
		}
		
		if(qrcode.equals("0") || null == code)
		{
			map.put("code", 0);
			map.put("msg", "查无此产品数据，谨防假冒");
			map.put("msg_en", "Incorrect barcode. Beware of imitations.");
			map.put("msg_code", "4001");
			return map;
		}
		
		if(!isScanAll)
		{
			if(dataType.getCompany_id() != code.getCompanyId())
			{
				map.put("code", 0);
				map.put("msg", "二维码非本企业二维码！");
				map.put("msg_en", "It isn't belongs to your company.");
				map.put("msg_code", "4000");
				return map;
			}
		}
		
		if(code.getStatus() == PisConstants.QRCodeStatus.Void.getKey())
		{
			map.put("code", 0);
			map.put("msg", "此产品为假冒伪劣产品");
			map.put("msg_en", "Incorrect barcode. Beware of imitations.");
			map.put("msg_code", "4002");
			return map;
		}
		else if(code.getStatus() < PisConstants.QRCodeStatus.Out.getKey())
		{//二维码未出库
			map.put("code", 0);
			map.put("msg", "此产品未通过正常渠道流转，谨防假冒");
			map.put("msg_en", "Unofficial transferring barcode. Beware of imitations.");
			map.put("msg_code", "4003");
			return map;
		}
		else
		{
			int actionType = 0;
			int count = 0;
			String msg = "";
			String msg_en = "";
			String msg_code = "";
			String actionName = "";
			
			if(scanType == 1) //扫码收货
			{
				actionType = PisConstants.ActionType.DealerIn.getKey();
				actionName = PisConstants.ActionType.DealerIn.getText();
			}
			else //扫码溯源
			{
				if(user.getRole().getRlId() == 5 || user.getRole().getRlId() == 4) //经销商用户
				{
					actionType = PisConstants.ActionType.DealerTrace.getKey();
					actionName = PisConstants.ActionType.DealerTrace.getText();
				}
				else if(user.getRole().getRlId() == 7) //最终用户
				{
					actionType = PisConstants.ActionType.UserTrace.getKey();
					actionName = PisConstants.ActionType.UserTrace.getText();
				}
			}
			
			ScanRecord entity = new ScanRecord();
			HashMap<String, String> param = new HashMap<String, String>();
			param.put("qrcode", qrcode);
			param.put("userId", String.valueOf(user.getUsrId()));
			param.put("actionType", String.valueOf(actionType));
			
			Product product = productDao.getByIdForApi(code.getProductId());
			ScanRecord scanRecord = this.scanDao.getByCode(param);
			count = this.scanDao.getScanCount(param);
			SysUser lastUser = this.userDao.getLastUser(qrcode, actionType);
			
			QRCode out = this.qrDao.getByCode(qrcode);
			if(null != out)
			{
				if(out.getStatus() == PisConstants.QRCodeStatus.Void.getKey())
				{
					msg = "此产品为假冒伪劣产品";
					msg_en = "Incorrect barcode.Beware of imitations.";
					msg_code = "4002";
				}
				else if(out.getStatus() == PisConstants.QRCodeStatus.Divided.getKey())
				{
					msg = "产品已拆分！";
					msg_en = "The Product was divided.";
					msg_code = "4004";
				}
				else
				{
					if (actionType == PisConstants.ActionType.DealerIn.getKey()) 
					{
						if (out.getStatus() > PisConstants.QRCodeStatus.InFlow.getKey()) 
						{
							msg = "该产品已使用！";
							msg_en = "The product is sold and used.";
							msg_code = "5004";
						} 
						else 
						{

							if (out.getStatus() == PisConstants.QRCodeStatus.InFlow.getKey()) 
							{
								if (count < 10) 
								{
									msg = "该产品已经被扫描" + count + "次，上次扫描用户: " + lastUser.getLgnNm();
									msg_en = "The product has been scanned " + count + " times, and the last user who scanned is " + lastUser.getLgnNm();
									msg_code = "5002";
								} 
								else
								{
									msg = "此产品流转已经超过10次，谨防假冒";
									msg_en = "The product has been scanned " + count + " times, beware of imitations.";
									msg_code = "5003";
								}
							} 
							else
							{
								msg = "扫码收货成功！";
								msg_en = "Receiving success.";
								msg_en = "5001";
							}
						}
					}
					else 
					{
						if (actionType == PisConstants.ActionType.DealerTrace.getKey())// 经销商用户溯源
						{
							if (scanRecord != null) 
							{
								msg = "您于" + scanRecord.getCreateTime() + "扫描查询过";
								msg_en = "You have scanned it at " + scanRecord.getCreateTime();
								msg_code = "6002";
							}
							else 
							{
								if (lastUser == null)
								{
									int num = this.scanDao.getEndUser(7, qrcode);
									if(num > 0)
									{
										msg = "产品已使用，谨防假冒！";
										msg_en = "The product is sold and used.Beware of imitations.";
										msg_code = "6000";
									}
									else
									{
										msg = "该产品尚未被经销商溯源！";
										msg_en = "The product hasn't been scanned by dealers.";
										msg_code = "6001";
									}
								} 
								else
								{
									msg = "该产品已经被扫描" + count + "次，上次扫描用户: " + lastUser.getLgnNm();
									msg_en = "The product has been scanned " + count + " times, and the last user who scanned is " + lastUser.getLgnNm();
									msg_code = "6003";
								}
							}
						} 
						else if (actionType == PisConstants.ActionType.UserTrace.getKey())// 终端用户溯源
						{
							if (count == 0) // 判断二维码是否被最终用户扫描过
							{
								// 更新二维码状态为最终用户已扫描
								code.setStatus(QRCodeStatus.Used.getKey());
								code.setStsName(QRCodeStatus.Used.getText());
								qrDao.update(code);

								// 更新用户积分
								user.setPoint(user.getPoint() + product.getPoints());
								userDao.update(user);
								
								//插入积分记录
								PointFollowing pointFollowing = new PointFollowing();
								pointFollowing.setUserId(user.getUsrId());
								pointFollowing.setUserName(user.getUsrNm());
								pointFollowing.setProductId(product.getId());
								pointFollowing.setPoint(String.valueOf(product.getPoints()));
								this.pointFollowingDao.insert(pointFollowing);								
								
								msg = "感谢您购买" + product.getName() + "产品，本产品积分" + product.getPoints() + ", 当前累计积分" + user.getPoint();
								msg_en = "Thanks for buying " + product.getName() + ". The award point is " + product.getPoints()
										+ ", and your current point is " + user.getPoint();
								msg_code = "6004";
							}
							else 
							{
								if (scanRecord != null)
								{
									msg = "您于" + scanRecord.getCreateTime() + "扫描查询过";
									msg_en = "You have scanned it at " + scanRecord.getCreateTime();
									msg_code = "6005";
								}
								else
								{
									msg = "该产品已被" + lastUser.getLgnNm() + "使用, 谨防假冒";
									msg_en = lastUser.getLgnNm() + " has used this product. Beware of imitations";
									msg_code = "6006";
								}
							}
						}
					}
				}
			}
			
			if(actionType == PisConstants.ActionType.DealerIn.getKey())
			{
				findInToInsertScanRecord(qrcode, user, PisConstants.ActionType.DealerIn, QRCodeStatus.InFlow, null);
			}
			scanRecord = this.scanDao.getByCode(param);
			if(scanRecord == null)
			{
				entity.setQrcode(qrcode);
				entity.setOperatorId(user.getUsrId());
				entity.setOperator(user.getLgnNm());
				entity.setCreateTime(DateUtil.getCurrentTime());
				entity.setActionType(actionType);
				entity.setActionName(actionName);
				this.save(entity);
			}
			
			//扫小包装二维码，大包装二维码失效
			findOutToDivide(code.getQrcode());
			
			Object object = this.scanDao.scanQuery(qrcode);
			
			map.put("msg", msg);
			map.put("msg_en", msg_en);
			map.put("msg_code", msg_code);
			map.put("code", 1);
			map.put("result", object);
			return map;
		}
		
	}
	
	@ServiceLog(description="入库")
	public HashMap<String, Object> saveIn(SysUser user, String qrcodes, String deviceNo) throws Exception
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		String[] codes = qrcodes.split(",");
		
		for(String code : codes)
		{
			//外包装入库
			code = code.replace(SysConfig.get_weifu_url(), "");
			code = DesUtil.decrypt(code, PisConstants.QRSalt);
			QRCode qrCode = this.qrDao.getByCode(code);
			
			if(code.equals("0") || null == qrCode)
			{
				//logger.info("========入库条码无效========");
				map.put("code", 0);
				map.put("msg", "入库失败，条码无效");
				map.put("msg_en", "Instock failure. Invalid barcode.");
				map.put("msg_code", "2001");
				return map;
			}
			
			if(qrCode.getStatus() < PisConstants.QRCodeStatus.Packaged.getKey())
			{
				//logger.info("========" + qrCode.getStatus() + "========入库状态不正确，条码未包装========" + qrCode.getQrcode());
				map.put("code", 0);
				map.put("msg", "入库失败，条码未包装");
				map.put("msg_en", "Instock failure. Incorrect barcode status.");
				map.put("msg_code", "2002");
				return map;
			}
			
			if(qrCode.getStatus() >= PisConstants.QRCodeStatus.In.getKey())
			{
				//logger.info("========" + qrCode.getStatus() + "========入库状态不正确，条码已入库========" + qrCode.getQrcode());
				map.put("code", 0);
				map.put("msg", "入库失败，条码已入库");
				map.put("msg_en", "Instock failure. Incorrect barcode status.");
				map.put("msg_code", "2002");
				return map;
			}
			
			/**判断条码是否是MS装配系统且企业为威孚*/
			//insertProduceInfoAndPackageRecordIfMs(code, user, PisConstants.ActionType.Package, PisConstants.QRCodeStatus.Packaged, deviceNo);
			
			/**外包装二维码查询所有层级内包装并写入扫码记录*/
			findInToInsertScanRecord(code, user, PisConstants.ActionType.In, PisConstants.QRCodeStatus.In, deviceNo);
		}
		map.put("code", 1);
		map.put("msg", "入库成功");
		map.put("msg_en", "Instock success.");
		map.put("msg_code", "2003");
		return map;
	}

	@ServiceLog(description="出库")
	public HashMap<String, Object> saveOut(SysUser user, String orderNo, String qrcodes, String deviceNo) throws Exception  
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		String[] codes = qrcodes.split(",");
		
		if(orderNo == null || orderNo == "")
		{
			//logger.info("========出库单号未输入========");
			map.put("code", 0);
			map.put("msg", "出库失败，未输入出库单号");
			map.put("msg_en", "Outstock failure. Please enter delivery order No.");
			map.put("msg_code", "3001");
			return map;
		}
		
		/**
		 * 校验出库单产品数量是否一致
		 */
		SalesOrder salesOrder = this.salesOrderDao.getByKingId(orderNo);
		if(null == salesOrder)
		{
			map.put("code", 0);
			map.put("msg", "出库失败，出库单不存在");
			map.put("msg_en", "Outstock failure. There is no this delivery order.");
			map.put("msg_code", "3000");
			return map;
		}
		else if(salesOrder.getStatus() == 1)
		{
			map.put("code", 0);
			map.put("msg", "出库失败，出库单已出库");
			map.put("msg_en", "Outstock failure. This delivery order is delivered.");
			map.put("msg_code", "3000");
			return map;
		}
		
		List<SaleOrderDetail> list = this.saleOrderDetailDao.getByOrderId(String.valueOf(salesOrder.getId()));
		
		if(null == list || list.size() < 1)
		{
			//logger.info("========出库单无明细========");
			map.put("code", 0);
			map.put("msg", "出库失败，出库单无明细");
			map.put("msg_en", "Outstock failure. There is no details about this delivery order.");
			map.put("msg_code", "3002");
			return map;
		}
		
		/*for (SaleOrderDetail saleOrderDetail : list) 
		{
			int count = 0;
			int capacity = 0;
			int miniCapacity = 0;
			int saleCount = 0;
			int scanCount = 0;
			Product product;
			
			for (String code : codes) 
			{
				code = code.replace(SysConfig.get_weifu_url(), "");
				code = DesUtil.decrypt(code, PisConstants.QRSalt);
				logger.info(code + "-----");
				QRCode qrCode = this.qrDao.getByCode(code);
				
				if(code.equals("0") || null == qrCode)
				{
					logger.info("=======条码异常-匹配出库单=======");
					map.put("code", 0);
					map.put("msg", "出库失败，条码无效");
					map.put("msg_en", "Outstock failure. Invalid barcode.");
					map.put("msg_code", "3003");
					return map;
				}
				
				if(qrCode.getStatus() != PisConstants.QRCodeStatus.In.getKey())
				{
					
					logger.info("=========" + qrCode.getQrcode() + "=======当前二维码状态不是入库状态--出库单匹配=======");
					map.put("code", 0);
					map.put("msg", "出库失败，条码状态错误");
					map.put("msg_code", "Outstock failure. Incorrect barcode status.");
					map.put("msg_code", "3004");
					return map;
				}
				
				*//**产品ID等于出库单中的产品ID 获取包装定义的容量 相加 *//*
				if(qrCode.getProductId() == saleOrderDetail.getProductId())
				{
					capacity = Integer.parseInt(this.packageDao.getById(qrCode.getPackageId()).getCapacity());
					count += capacity;
				}
			}
			
			if(count != 0)
			{
				*//**数量相同继续匹配 不同返回出库失败*//*
				*//**匹配最小包装数量是否相同*//*
				product = productDao.getById(saleOrderDetail.getProductId());
				miniCapacity = Integer.parseInt(packageDao.findMinPackage(product.getPackageID()).getCapacity());
				
				scanCount = (int) count / miniCapacity;
				saleCount = (int) Math.floor(Integer.parseInt(saleOrderDetail.getCount()) / miniCapacity);
				
				if(scanCount == saleCount)
				{
					continue;
				}
				else
				{
					logger.info(count + "=======数量不相同=======" + Integer.parseInt(saleOrderDetail.getCount()));
					map.put("code", 0);
					map.put("msg", "出库失败, 与出库单数量不一致");
					map.put("msg_en", "Outstock failure. Incorrect delivery quantity.");
					map.put("msg_code", "3005");
					return map;
				}
			}			
		}*/
			
		for(String code : codes)
		{
			code = code.replace(SysConfig.get_weifu_url(), "");
			code = DesUtil.decrypt(code, PisConstants.QRSalt);
			
			QRCode qrCode = this.qrDao.getByCode(code);
			
			if(code.equals("0") || null == qrCode)
			{
				//logger.info("=======出库失败，条码无效--出库=======" + code);
				map.put("code", 0);
				map.put("msg", "出库失败，条码无效");
				map.put("msg_en", "Outstock failure. Invalid barcode.");
				map.put("msg_code", "3003");
				throw new Exception("条码不存在");
			}
			
			if(qrCode.getStatus() != PisConstants.QRCodeStatus.In.getKey())
			{
				//logger.info("=======当前二维码状态不是入库状态--出库=======" + qrCode.getQrcode());
				map.put("code", 0);
				map.put("msg", "出库失败，条码状态错误");
				map.put("msg_en", "Outstock failure. Incorrect barcode status.");
				map.put("msg_code", "3004");
				throw new Exception("流水号["+qrCode.getSerialNo()+"]不是入库状态");
				//return map;
			}
						
			//外包装查内包装更新销售单ID
			findInToUpdateSalesOrderId(code, salesOrder.getId());
			//外包装查内包装写入扫码记录
			findInToInsertScanRecord(code, user, PisConstants.ActionType.Out, PisConstants.QRCodeStatus.Out, deviceNo);
			//扫小包装二维码，大包装二维码失效
			findOutToDivide(code);
		}
		
		/**更新出库单为出库状态*/
		this.salesOrderDao.updateById(salesOrder.getId(), 1);
		
		map.put("code", 1);
		map.put("msg", "出库成功");
		map.put("msg_en", "Outstock success.");
		map.put("msg_code", "3006");
		return map;
	}

	@ServiceLog(description="退货洗白")
	public HashMap<String, Object> returned(String qrcode, SysUser user)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		qrcode = qrcode.replace(SysConfig.get_weifu_url(), "");
		qrcode = DesUtil.decrypt(qrcode, PisConstants.QRSalt);
		QRCode code = qrDao.getByCode(qrcode);
		
		if(qrcode.equals("0") || null == code)
		{
			map.put("code", 0);
			map.put("msg", "退货失败, 条码无效");
			map.put("msg_en", "Return failure. invalid barcode.");
			map.put("msg_code", "7001");
			return map;
		}
		
		if(code == null || (null != code && code.getStatus() == PisConstants.QRCodeStatus.Void.getKey()))
		{
			map.put("code", 0);
			map.put("msg", "退货失败, 条码无效或已作废");
			map.put("msg_en", "Return failure. invalid barcode");
			map.put("msg_code", "7002");
		}
		else
		{
			ScanRecord entity = new ScanRecord();
			entity.setQrcode(qrcode);
			entity.setOperatorId(null == user ? 0 : user.getUsrId());
			entity.setOperator(null == user ? "" : user.getLgnNm());
			entity.setCreateTime(DateUtil.getCurrentTime());
			entity.setActionType(PisConstants.ActionType.Returned.getKey()); //退货
			entity.setActionName(PisConstants.ActionType.Returned.getText());
			this.save(entity);
			
			code.setStatus(PisConstants.QRCodeStatus.In.getKey()); //入库
			code.setStsName(QRCodeStatus.In.getText());
			qrDao.update(code);
			
			map.put("code", 1);
			map.put("msg", "退货成功");
			map.put("msg_en", "Return success.");
			map.put("msg_code", "7003");
		}
		
		return map;
	}
	
	@ServiceLog(description="作废二维码")
	public HashMap<String, Object> toVoid(String qrcode, SysUser user)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		qrcode = qrcode.replace(SysConfig.get_weifu_url(), "");
		qrcode = DesUtil.decrypt(qrcode, PisConstants.QRSalt);
		QRCode code = qrDao.getByCode(qrcode);
		
		if(qrcode.equals("0") || null == code)
		{
			map.put("code", 0);
			map.put("msg", "作废失败， 条码无效");
			map.put("msg_en", "Void failure, invalid barcode");
			map.put("msg_code", "8001");
			return map;
		}
		
		if(code == null || (null != code && code.getStatus() == PisConstants.QRCodeStatus.Void.getKey()))
		{
			map.put("code", 0);
			map.put("msg", "作废失败，条码无效或已作废");
			map.put("msg_en", "Void failure, invalid barcode");
			map.put("msg_code", "8002");
		}
		else
		{
			ScanRecord entity = new ScanRecord();
			entity.setQrcode(qrcode);
			entity.setOperatorId(null == user ? 0 : user.getUsrId());
			entity.setOperator(null == user ? "" : user.getLgnNm());
			entity.setCreateTime(DateUtil.getCurrentTime());
			entity.setActionType(PisConstants.ActionType.Void.getKey()); //作废
			entity.setActionName(PisConstants.ActionType.Void.getText());
			this.save(entity);
			
			code.setStatus(PisConstants.QRCodeStatus.Void.getKey());
			code.setStsName(QRCodeStatus.Void.getText());
			qrDao.update(code);
			
			map.put("code", 1);
			map.put("msg", "作废成功");	
			map.put("msg_en", "Void success.");
			map.put("msg_code", "8003");
		}
		
		return map;
	}

	public List<Object> findPackageList(SysUser user, String searchParam, String fmtm, String totm, Pageable pageable) 
	{
		int begin = pageable.getOffset() + 1;
		int end = pageable.getOffset() + pageable.getLimit();
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("supplierId", String.valueOf(user.getSupplier_id()));
		map.put("actionType", String.valueOf(PisConstants.ActionType.Package.getKey()));
		map.put("searchParam", "%" + searchParam + "%");
		map.put("fmtm", fmtm);
		map.put("totm", totm);
		map.put("begin", String.valueOf(begin));
		map.put("end", String.valueOf(end));
		
		return scanDao.findPackageList(map);
	}

	public List<Object> findRecordByAction(int dealerId, int compId, String frmTm, String toTm, String actionType,String searchParam, Pageable pageable) 
	{
		HashMap<String, String> map = new HashMap<String, String>();
		int begin = pageable.getOffset() + 1;
		int end = pageable.getOffset() + pageable.getLimit();
		
		map.put("frmTm", frmTm);
		map.put("toTm", toTm);
		map.put("compId", String.valueOf(compId));
		map.put("actionType", actionType);
		map.put("dealerId", String.valueOf(dealerId));
		map.put("searchParam", "%" + searchParam + "%");
		map.put("begin", String.valueOf(begin));
		map.put("end", String.valueOf(end));
		
		return scanDao.findRecordByAction(map);
	}

	public List<ScanRecord> getRecordByQrcode(String qrCode) 
	{
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("qrCode", qrCode);
		
		return scanDao.getRecordByQrcode(map);
	}

	public int findPkgCount(SysUser user, String searchParam, String fmtm, String totm)
	{
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("supplierId", String.valueOf(user.getSupplier_id()));
		map.put("actionType", String.valueOf(PisConstants.ActionType.Package.getKey()));
		map.put("searchParam", "%" + searchParam + "%");
		map.put("fmtm", fmtm);
		map.put("totm", totm);
		
		return this.scanDao.findPkgCount(map);
	}

	public int findRecordCount(int dealerId, int compId, String frmTm, String toTm, String actionType,String searchParam)
	{
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("frmTm", frmTm);
		map.put("toTm", toTm);
		map.put("compId", String.valueOf(compId));
		map.put("actionType", actionType);
		map.put("dealerId", String.valueOf(dealerId));
		map.put("searchParam", "%" + searchParam + "%");
		
		return scanDao.findRecordCount(map);
	}
	
	/**
	 * 外包装二维码查询所有层级内包装
	 * 写入扫码记录同时更新二维码状态
	 * @author jqi.can
	 * @date 2016-4-25上午10:31:48
	 */
	private void findInToInsertScanRecord(String code, SysUser user, ActionType actionType, QRCodeStatus qrStatus, String deviceNo) throws Exception
	{
		List<String> inCodes = pkgCodeDao.getInnerCode(code);
		List<HashMap<String, Object>> updateQrcode = new ArrayList<HashMap<String,Object>>();
		List<ScanRecord> records = new ArrayList<ScanRecord>();
		List<String> qrcodes = new ArrayList<String>();
		HashMap<String, Object> update = new HashMap<String, Object>();
		ScanRecord entity;
		
		//logger.info("-----find in to insert scan record begin-----");
		if(null != inCodes && inCodes.size() != 0)
		{
			for (String inCode : inCodes) 
			{
				//findInToInsertScanRecord(inCode, user, actionType, qrStatus, deviceNo);
				qrcodes.add(inCode);
				
				entity = new ScanRecord();
				entity.setQrcode(inCode);
				entity.setOperatorId(null == user ? 0: user.getUsrId());
				entity.setOperator(null == user ? "" : user.getLgnNm());
				entity.setCreateTime(DateUtil.getCurrentTime());
				entity.setActionType(actionType.getKey()); 
				entity.setActionName(actionType.getText());
				entity.setDeviceNo(deviceNo);
				records.add(entity);
				
				update = new HashMap<String, Object>();
				update.put("qrCode", inCode);
				update.put("status", qrStatus.getKey());
				update.put("stsName", qrStatus.getText());
				updateQrcode.add(update);
			}
		}
		
		entity = new ScanRecord();
		entity.setQrcode(code);
		entity.setOperatorId(null == user ? 0 : user.getUsrId());
		entity.setOperator(null == user ? "" : user.getLgnNm());
		entity.setCreateTime(DateUtil.getCurrentTime());
		entity.setActionType(actionType.getKey()); 
		entity.setActionName(actionType.getText());
		entity.setDeviceNo(deviceNo);
		records.add(entity);
		
		/**更新二维码状态*/
		update = new HashMap<String, Object>();
		update.put("qrCode", code);
		update.put("status", qrStatus.getKey());
		update.put("stsName", qrStatus.getText());
		updateQrcode.add(update);
		
		
		/*if(null != qrcodes && qrcodes.size() > 0)
		{

			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("qrcodes", qrcodes);
			param.put("userId", String.valueOf(user.getUsrId()));
			param.put("actionType", String.valueOf(actionType.getKey()));
			
			this.scanDao.deleteLot(param);
		}*/
		if(null != records && records.size() > 0)
		{
			this.scanDao.insertLot(records);
		}		
		if(null != updateQrcode && updateQrcode.size() > 0)
		{
			this.qrDao.updateLotSts(updateQrcode);
		}
		
		//logger.info("-----find in to insert scan record end-----");
	}
	
	/**
	 * 内包装二维码查外包装二维码更新为分拆状态
	 * @author jqi.can
	 * @date 2016-4-25下午01:44:40
	 */
	private void findOutToDivide(String qrcode) 
	{
		List<String> outCodes = pkgCodeDao.findOutCode(qrcode);
		List<HashMap<String, Object>> updates = new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> obj = new HashMap<String, Object>();
		if(null != outCodes && outCodes.size() != 0)
		{
			for (String outCode : outCodes) 
			{
				//findOutToDivide(outCode);
				
				//QRCode qr = this.qrDao.getByCode(outCode);
				obj.put("qrCode", outCode);
				obj.put("status", PisConstants.QRCodeStatus.Divided.getKey());
				obj.put("stsName", QRCodeStatus.Divided.getText());
				updates.add(obj);
				/*qr.setStatus(PisConstants.QRCodeStatus.Divided.getKey()); //分拆
				qr.setStsName(QRCodeStatus.Divided.getText());
				qrDao.update(qr);*/
			}
			
			if(null != updates && updates.size() > 0)
			{
				this.qrDao.updateLotSts(updates);
			}
		}
	}
	
	/**
	 * 查找内层二维码并更新销售单ID
	 * @author jqi.can
	 * 2016-8-22上午09:45:27
	 */
	private void findInToUpdateSalesOrderId(String code, int salesOrderId)
	{
		List<String> inCodes = pkgCodeDao.getInnerCode(code);
		List<HashMap<String, Object>> updates = new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> obj = new HashMap<String, Object>();
		if(null != inCodes && inCodes.size() != 0)
		{
			for (String inCode : inCodes) 
			{
				//findInToUpdateSalesOrderId(inCode, salesOrderId);
				obj.put("QRCODE", inCode);
				obj.put("SALEORDERID", salesOrderId);
				updates.add(obj);
			}
		}
		obj.put("QRCODE", code);
		obj.put("SALEORDERID", salesOrderId);
		updates.add(obj);
		if(null != updates && updates.size() > 0)
		{
			this.qrDao.updateSaleOrderIdLot(updates);
		}
		//this.qrDao.updateSaleOrderId(code, salesOrderId);
	}
	
	/**
	 * MS装配系统写入包装记录和生产信息
	 * @author jqi.can
	 * 2016-6-29下午05:35:03
	 * @param qrcodeStatus 
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	public void insertProduceInfoAndPackageRecordIfMs(String decryptQrCode, SysUser user, ActionType actionType, QRCodeStatus qrcodeStatus, String deviceNo) throws Exception 
	{
		QRCode qrCodeObj = this.qrDao.getByCode(decryptQrCode);
		int supplierId = this.supplierDao.getByCode("40000001").getId();
		
		//判断是否供应商编码是否是MS、企业是威孚
		if(qrCodeObj.getCompanyId() == 2 && qrCodeObj.getSupplierId() == supplierId)
		{
			//logger.info("========MS入库写入包装记录和生产信息==========");
			String url = SysConfig.get_ms_url() + "api/product/getProductInfo.do";
			String encryptQrCode = "";
			encryptQrCode = SysConfig.get_weifu_url() + DesUtil.encrypt(decryptQrCode, PisConstants.QRSalt);
			String response = HttpUtil.executePost(url, encryptQrCode, null);
			JSONObject urlJsonObj = JSONObject.fromObject(response);
			String urlCode = urlJsonObj.getString("code");
			if(null != urlCode && !"".equals(urlCode) && urlCode.equals("200"))
			{
				ProductInfo outerProductInfo = (ProductInfo) JSONObject.toBean((JSONObject) urlJsonObj.get("productInfo"), ProductInfo.class);
				JSONArray innersArray;
				List<ProductInfo> innerProductInfoList;
				Product product = this.productDao.getByCode(outerProductInfo.getProductCode());
				List<HashMap<String, Object>> toUpdateQrcodeList = new ArrayList<HashMap<String,Object>>();
				List<ProductInfo> productInfoList = new ArrayList<ProductInfo>();
				List<PkgCodeMap> mapList = new ArrayList<PkgCodeMap>();
				List<Integer> msCodes = new ArrayList<Integer>();
				String[] deleteInfo = null;
				HashMap<String, Object> toUpdate;
				PkgCodeMap codeMap;
				int index = 0;
				
				/**
				 * 从MS那边获取的信息 根据ID查找父级codeID即outerQrcodeId是该二维码的 抽取其内层生产信息
				 * 如果内层生产信息为空 表示该二维码为单层包装二维码 即写入该二维码生产信息 
				 * 不为空 表示多层包装二维码 
				 * 需写入内层二维码生产信息 同时写入二维码包装关系
				 */
				//logger.info("=====获取MS内层二维码 ====" + outerProductInfo.getId() + "---" + decryptQrCode);
				url = SysConfig.get_ms_url() + "api/product/getInner.do?outId=" + outerProductInfo.getId();
				response = HttpUtil.executePost(url, "", "");
				urlJsonObj = JSONObject.fromObject(response);
				urlCode = urlJsonObj.getString("code");
				
				if (null != urlCode && !"".equals(urlCode) && urlCode.equals("200"))
				{
					innersArray = urlJsonObj.getJSONArray("inners");
					innerProductInfoList = innersArray.toList(innersArray, ProductInfo.class);
					String innerCode = "";
					/* 判断是否有内层二维码 */
					if (null != innerProductInfoList && innerProductInfoList.size() > 0) 
					{
						deleteInfo = new String[innerProductInfoList.size()];
						for (ProductInfo innerProductInfo : innerProductInfoList) 
						{
							innerCode = DesUtil.decrypt(innerProductInfo.getQrCode().replace(SysConfig.get_weifu_url(), ""), PisConstants.QRSalt);
							
							toUpdate = new HashMap<String, Object>();
							toUpdate.put("productId", product.getId());
							toUpdate.put("qrcode", innerCode);
							toUpdateQrcodeList.add(toUpdate);
							
							innerProductInfo.setProduct_id(product.getId());
							innerProductInfo.setProduct_code(product.getCode());
							innerProductInfo.setProduct_name(product.getName());
							innerProductInfo.setQrCode(innerCode);
							productInfoList.add(innerProductInfo);
							
							deleteInfo[index++] = innerCode;
							codeMap = new PkgCodeMap(innerCode, decryptQrCode);
							mapList.add(codeMap);
							
							msCodes.add(innerProductInfo.getId());
						}
					} 
					
					//写入当前层生产信息 更新二维码绑定产品ID
					toUpdate = new HashMap<String, Object>();
					toUpdate.put("productId", product.getId());
					toUpdate.put("qrcode", decryptQrCode);
					toUpdateQrcodeList.add(toUpdate);
					
					outerProductInfo.setQrCode(decryptQrCode);
					outerProductInfo.setProduct_id(product.getId());
					outerProductInfo.setProduct_code(product.getCode());
					outerProductInfo.setProduct_name(product.getName());
					productInfoList.add(outerProductInfo);
					
					msCodes.add(outerProductInfo.getId());
					
					//logger.info("====批量 插入包装关系/删除生产信息/插入生产信息 + 更新二维码产品ID===begin");
					//批量 插入包装关系/删除生产信息/插入生产信息 + 更新二维码产品ID
					if(null != mapList && mapList.size() > 0)
					{
						this.pkgCodeDao.insertLot(mapList);
					}
					if(null != deleteInfo && deleteInfo.length > 0)
					{
						this.productInfoDao.deleteLot(deleteInfo);
					}
					if(null != productInfoList && productInfoList.size() > 0)
					{
						this.productInfoDao.insertLot(productInfoList);
					}
					if(null != toUpdateQrcodeList && toUpdateQrcodeList.size() > 0)
					{
						this.qrDao.updateLot(toUpdateQrcodeList);
					}
					if(null != msCodes && msCodes.size() > 0)
					{
						HttpUtil.expostPost(SysConfig.get_ms_url() + "api/product/updateLotUsed.do", JSONObject.fromObject(new MSQrCode(msCodes)).toString());
					}
					
					findInToInsertScanRecord(decryptQrCode, user, actionType, qrcodeStatus, deviceNo);
					//logger.info("====批量 插入包装关系/删除生产信息/插入生产信息 + 更新二维码产品ID===end");
				}
			}
		}
	}

	public List<Object> getActionRecordXls(int dealerId, int compId, String frmTm, String toTm, String actionType, String searchParam)
	{
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("frmTm", frmTm);
		map.put("toTm", toTm);
		map.put("compId", String.valueOf(compId));
		map.put("actionType", actionType);
		map.put("dealerId", String.valueOf(dealerId));
		map.put("searchParam", "%" + searchParam + "%");
		
		return this.scanDao.getActionRecordXls(map);
	}

	public List<Object> packageListXls(SysUser user, String searchParam, String frmTm, String toTm) 
	{
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("supplierId", String.valueOf(user.getSupplier_id()));
		map.put("actionType", String.valueOf(PisConstants.ActionType.Package.getKey()));
		map.put("searchParam", "%" + searchParam + "%");
		map.put("fmtm", frmTm);
		map.put("totm", toTm);
		
		return scanDao.packageListXls(map);
	}

	public SalesOrder getOutInfo(String qrCode) 
	{
		return this.salesOrderDao.getOutInfo(qrCode);
	}

	public List<ScanRecord> getOperate(int status, String qrcode)
	{

		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("status", String.valueOf(status));
		map.put("qrcode", qrcode);
		
		return this.scanDao.getOperate(map);
	}

	public void test() throws Exception 
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		String[] codes = new String[]{"1", "2", "3", "4", "5", "6"};
		//String[] outs = null;
		map.put("codes", DBUtil.getDBArray(codes, "CODE_ARRAY"));
		map.put("outInfo", null);
		this.qrDao.test(map);
	}
}
