package com.ptsoft.pts.business.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptsoft.common.Constants;
import com.ptsoft.common.util.DateUtil;
import com.ptsoft.common.util.Pageable;
import com.ptsoft.common.util.StringUtil;
import com.ptsoft.common.util.SysConfig;
import com.ptsoft.pts.PisConstants;
import com.ptsoft.pts.account.model.vo.SysUser;
import com.ptsoft.pts.basic.dao.DealerDao;
import com.ptsoft.pts.basic.dao.SupplierDao;
import com.ptsoft.pts.basic.dao.SynchDao;
import com.ptsoft.pts.basic.model.vo.Dealer;
import com.ptsoft.pts.basic.model.vo.Supplier;
import com.ptsoft.pts.basic.model.vo.Synch;
import com.ptsoft.pts.business.dao.OrderDao;
import com.ptsoft.pts.business.dao.OrderItemDao;
import com.ptsoft.pts.business.dao.PackageDao;
import com.ptsoft.pts.business.dao.ProductDao;
import com.ptsoft.pts.business.dao.QRDao;
import com.ptsoft.pts.business.dao.SaleOrderDetailDao;
import com.ptsoft.pts.business.dao.SalesOrderDao;
import com.ptsoft.pts.business.model.vo.Order;
import com.ptsoft.pts.business.model.vo.OrderItem;
import com.ptsoft.pts.business.model.vo.Product;
import com.ptsoft.pts.business.model.vo.QRCode;
import com.ptsoft.pts.business.model.vo.SaleOrderDetail;
import com.ptsoft.pts.business.model.vo.SalesOrder;
import com.ptsoft.pts.system.dao.SysDataTypeDao;
import com.ptsoft.pts.system.dao.SysLogDao;
import com.ptsoft.pts.system.model.vo.SysDataType;
import com.ptsoft.pts.system.model.vo.SysLog;
import com.ptsoft.pts.util.GenerateCode;
import com.ptsoft.pts.util.HttpUtil;

@Service
public class OrderService
{
	Logger logger = Logger.getLogger(OrderService.class);
	
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private SupplierDao supplierDao;
	@Autowired
	private OrderItemDao orderItemDao;
	@Autowired
	private PackageDao pkgDao;
	@Autowired
	private QRDao qrDao;
	@Autowired
	private SynchDao synchDao;
	@Autowired
	private SalesOrderDao salesOrderDao;
	@Autowired
	private SaleOrderDetailDao saleOrderDetailDao;
	@Autowired
	private SysLogDao sysLogDao;
	@Autowired
	private SysDataTypeDao dataTypeDao;
	@Autowired
	private DealerDao dealerDao;

	public List<Order> findByCompanyId(int companyId) {
		return orderDao.findByCompanyId(companyId);
	}

	public List<Object> findByCompId(HashMap<Object, Object> map, Pageable pageable)
	{
		int begin = pageable.getOffset() + 1;
		int end = pageable.getOffset() + pageable.getLimit();
		
		map.put("begin", String.valueOf(begin));
		map.put("end", String.valueOf(end));
		
		return orderDao.findByCompId(map);
	}
	public int getCountByCompId(HashMap<Object, Object> map) 
	{		
		return this.orderDao.getCountByCompId(map);
	}

	public String saveOrder(SysUser user, Order order) 
	{
		String msg = "";
		int num = this.orderDao.getByOrderId(order.getId());
		int logType = PisConstants.LogType.Business.getKey();
		String logActionType = String.valueOf(PisConstants.LogActionType.CreateOrder.getKey());
		Order entity = this.orderDao.getByOrderNo(order.getOrderNo());
		SysLog sysLog = null;
		
		if(entity != null && entity.getId() != order.getId())
		{
			return "-0";
		}
		
		try
		{
			if (num > 0)
			{
				//更新订单
				this.orderDao.update(order);
				
				sysLog = new SysLog(user.getLgnNm(), user.getUsrId(), "采购订单管理", "更新订单"+ order.getOrderNo(), 1, logActionType, logType);
			}
			else
			{
				//新增订单
				order.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				
				this.orderDao.insert(order);
				sysLog = new SysLog(user.getLgnNm(), user.getUsrId(), "采购订单管理", "新增订单" + order.getOrderNo(), 1, logActionType, logType);
			}
			msg = String.valueOf(order.getId());
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			msg = "-1";
			sysLog = new SysLog(user.getLgnNm(), user.getUsrId(), "采购订单管理", ex.getMessage(), 0, logActionType, PisConstants.LogType.Exception.getKey());
		}
		this.sysLogDao.insert(sysLog);
		return msg;
	}

	public String saveOrderItems(int orderId, String itemstr) 
	{
		if(orderId == 0){
			return "请先保存采购订单主数据";
		}
		String[] items = itemstr.split(";");
		String msg = "";
		try 
		{
			orderItemDao.deleteByOrderId(orderId);
			for(String item : items){
				String[] paramVals = item.split(",");
				OrderItem entity = new OrderItem();
				Product product = productDao.getById(Integer.valueOf(paramVals[0]));
				entity.setOrderId(orderId);
				entity.setProduct(product);
				Supplier supplier = supplierDao.getById(Integer.valueOf(paramVals[2]));
				entity.setSupplier(supplier);
				entity.setCount(Integer.valueOf(paramVals[1]));
				orderItemDao.insert(entity);
			}
			msg = "1";
		} 
		catch (Exception e) 
		{
			msg = "0";
			e.printStackTrace();
		}
		return msg;
	}

	public Order getById(int id) {
		return orderDao.getById(id);
	}

	public List<OrderItem> findItemsByOrderId(int orderId) {
		return orderItemDao.findByOrderId(orderId);
	}

	@SuppressWarnings("unused")
	public HashMap<String, Object> createQrcode(SysUser user, int orderId) 
	{
		HashMap<String, Object> rmap = new HashMap<String, Object>();
		
		SysLog sysLog = null;
		String qrcode = "";
		String salt = PisConstants.QRSalt;
		String serialNo = "";
		int qrNum = 0;
		int logType = PisConstants.LogType.Business.getKey();
		String actionType = String.valueOf(PisConstants.LogActionType.CreateQrcode.getKey());
		
		Order order = orderDao.getById(orderId);
		if(order.getStatus() == 1)
		{
			rmap.put("code", 2);
			rmap.put("msg", "订单已生成二维码");
			rmap.put("orderNo", order.getOrderNo());
			rmap.put("msOrderId", "");
			return rmap;
		}
		
		List<OrderItem> items = orderItemDao.findByOrderId(orderId);
		if(null == items || items.size() < 1) 
		{
			rmap.put("code", 3);
			rmap.put("msg", "订单产品不存在，请添加订单产品后重新生成二维码");
			rmap.put("orderNo", order.getOrderNo());
			rmap.put("msOrderId", "");
			
			order.setStatus(4); //生成二维码失败
			order.setRemark("产品不存在");
			orderDao.update(order);
			
			return rmap;
		}
		
		serialNo = this.qrDao.getTodayMax(DateUtil.getCurrentDate());
		if(null == serialNo || "".equals(serialNo))
		{
			serialNo = "00000";
		}
		else
		{
			serialNo = serialNo.substring(6, serialNo.length());
		}
		
		int supplierId, num;
		List<Object> pkgItems;
		String msOrderId = "";
		Product product;
		int msSupplierId = this.supplierDao.getByCode(Constants.MS_SUPPLIER_CODE).getId();
		
		logger.info("----订单" + order.getOrderNo() + "生成二维码 begin----");
		List<QRCode> qrcodeList = new ArrayList<QRCode>();
		for(OrderItem item : items)
		{
			product = productDao.getById(item.getProduct().getId());
			supplierId = item.getSupplier().getId();
			
			if(String.valueOf(product.getPackageID()) == null || product.getPackageID() == 0)
			{
				rmap.put("code", 4);
				rmap.put("msg", "产品编号为" + product.getCode() + "的产品没有设置包装，请设置包装后生成二维码");
				rmap.put("orderNo", order.getOrderNo());
				rmap.put("msOrderId", "");
				
				sysLog = new SysLog(user == null ? "系统同步" : user.getLgnNm(), user == null ? 2 : user.getUsrId(), "生成二维码", "订单" + order.getOrderNo() + "生成二维码时，产品编号为" + product.getCode() + "的产品没有设置包装", 1, actionType, logType);
				this.sysLogDao.insert(sysLog);
				
				order.setStatus(3); //生成二维码失败
				order.setRemark("产品无包装");
				orderDao.update(order);
				
				return rmap;
			}
			
			if(supplierId == msSupplierId)
			{
				msOrderId = String.valueOf(orderId);
			}
			pkgItems = pkgDao.getByRuleID(product.getPackageID());
			
			for(Object obj : pkgItems)
			{
				//计算每层包装的二维码数量，生成二维码
				@SuppressWarnings("unchecked")
				HashMap<String, Object> map = (HashMap<String, Object>) obj;
				num = item.getCount()/Integer.valueOf(map.get("CAPACITY").toString());
				
				for(int i = 0; i < num; i++)
				{
					//生成每层包装的二维码
					qrNum++;
					QRCode code = new QRCode();
					
					serialNo = GenerateCode.getSerialNo(serialNo);
					qrcode = DateUtil.getCurrentDate() + "-" + UUID.randomUUID().toString();//二维码生成规则
					
					code.setPackageId(Integer.valueOf(map.get("ID").toString()));
					code.setCompanyId(order.getCompanyId());
					code.setPkgLevel(Integer.valueOf(map.get("PKGLEVEL").toString()));
					code.setProductId(product.getId());
					code.setStatus(PisConstants.QRCodeStatus.Created.getKey());
					code.setSupplierId(supplierId);
					code.setSalt(salt);//二维码加密的盐
					code.setSerialNo(DateUtil.getYearMonthDate() + serialNo);
					code.setQrcode(qrcode);
					code.setCreateTime(DateUtil.getCurrentDate());
					code.setCanPrint(0);
					
					if(msSupplierId == supplierId)
					{
						code.setOrderNo(String.valueOf(order.getId()));
					}
					qrcodeList.add(code);
				}
			}
		}
		if(null != qrcodeList && qrcodeList.size() > 0)
		{
			this.qrDao.insertLot(qrcodeList);
		}
		logger.info("----订单" + order.getOrderNo() + "生成二维码 end----");
		
		if(!"".equals(msOrderId))
		{
			order.setStatus(8);
		}
		else
		{
			order.setStatus(1); //已生成二维码
			order.setRemark("二维码已生成");
		}
		orderDao.update(order);
		
		rmap.put("code", 1);
		rmap.put("msg", "二维码生成成功");
		rmap.put("msOrderId", msOrderId);
		
		sysLog = new SysLog(user == null ? "系统同步" : user.getLgnNm(), user == null ? 2 : user.getUsrId(), "生成二维码", "订单" + order.getOrderNo() + "生成二维码", 1, actionType, logType);
		this.sysLogDao.insert(sysLog);
		
		return rmap;
	}

	public List<Object> findBySupplierID(String searchParam, String fmtm, String totm, int supplierID,Pageable pageable) 
	{
		int begin = pageable.getOffset() + 1;
		int end = pageable.getOffset() + pageable.getLimit();
		
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("searchParam", "%" + searchParam + "%");
		map.put("fmtm", fmtm);
		map.put("totm", totm);
		map.put("supplierID", String.valueOf(supplierID));
		map.put("begin", String.valueOf(begin));
		map.put("end", String.valueOf(end));	
		
		return this.orderItemDao.findBySupplierID(map);
	}
	
	/**
	 * 根据条件查询订单的总条数
	 * @return
	 */
	public int getCountBySupplierId(String searchParam, String fmtm, String totm, int supplierID) 
	{
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("searchParam", "%" + searchParam + "%");
		map.put("fmtm", fmtm);
		map.put("totm", totm);
		map.put("supplierID", String.valueOf(supplierID));
		return this.orderItemDao.getCountBySupplierId(map);
	}
	/**
	 * 同步采购订单
	 * @author jqi.can
	 * @date 2016-4-20下午01:41:48
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	public HashMap<String, Object> syncPurchase(SysUser user) throws Exception 
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		SysLog sysLog = null;
		Synch synch = synchDao.getMaxByType(1);
		int kingId = 0;
		int logType = PisConstants.LogType.Business.getKey();
		String actionType = String.valueOf(PisConstants.LogActionType.SyncOrder.getKey());
		
		if(null != synch)
		{
			kingId = synch.getKingId();
		}
		String url = SysConfig.get_ga_url() + "api/order/purchaseOrder.do?kingId=" + kingId + "&kingDate=";
		
		String response = HttpUtil.executeGet(url);
		JSONObject jsonObj = JSONObject.fromObject(response);
		String code = jsonObj.getString("code");
		
		if(null != code && !"".equals(code) && code.equals("200"))
		{
			JSONArray heads = jsonObj.getJSONArray("heads");
			JSONArray details = jsonObj.getJSONArray("details");
			
			List<Order> headList = heads.toList(heads, Order.class);
			List<OrderItem> detailsList = details.toList(details, OrderItem.class);
			
			if(null == headList || headList.size() < 1)
			{
				map.put("code", "300");
				map.put("msg", "Failure");
				return map;
			}
			
			synch = new Synch();
			synch.setKingId(Integer.parseInt(headList.get(headList.size() - 1).getKingId()));
			synch.setKingDate(headList.get(headList.size() - 1).getKingDate());
			synch.setSyncType(1);
			this.synchDao.insert(synch);
			
			boolean hasProduct, hasSupplier;
			String supplierCodes;
			List<String> orderNoList = new ArrayList<String>();
			int msSupplierId = this.supplierDao.getByCode(Constants.MS_SUPPLIER_CODE).getId();
			
			//插入采购订单及明细
			for (Order order : headList) 
			{
				hasProduct = false;
				hasSupplier = false;
				supplierCodes = "同步添加供应商编码:";
				
				Order odr = this.orderDao.getByKindId(order.getKingId());
				if(null == odr)
				{
					order.setCompanyId(2);
					order.setCreateTime(DateUtil.getCurrentTime());
					order.setOrderType("SYNC");
					order.setOrderFrom("2");
					order.setOrderNo(StringUtil.padLeft(order.getKingNo(), 4, "0"));
					order.setSyncDate(DateUtil.getCurrentTime());
					
					this.orderDao.insert(order);
					int orderId = order.getId();
					
					Supplier supplier = this.supplierDao.getByCode(order.getSupplierCode());
					if(null == supplier)
					{
						hasSupplier = true;
						supplierCodes += order.getSupplierCode();
						supplier = new Supplier();
						supplier.setSupplier_code(order.getSupplierCode());
						supplier.setSupplier_name(order.getSupplierName());
						supplier.setHas_system(0);
						supplier.setStatus(1);
						this.supplierDao.insert(supplier);
						
						sysLog = new SysLog(user == null ? "系统同步" : user.getLgnNm(), user == null ? 2 : user.getUsrId(), "同步采购订单", "同步采购订单" + order.getOrderNo() + "时，添加编码" + order.getSupplierCode() + "的生产供应商，请补全其信息", 1, actionType, logType);
						this.sysLogDao.insert(sysLog);
					}
					if(supplier.getId() == msSupplierId)
					{
						orderNoList.add(String.valueOf(orderId));
					}
					Product product;
					String name;
					
					//插入订单明细
					for (OrderItem orderItem : detailsList) 
					{
						if(orderItem.getKingOrderId() == Integer.parseInt(order.getKingId()) && orderItem.getCount() > 0)
						{
							product = this.productDao.getByCode(orderItem.getProduct_code());
							//product = this.productDao.getByCodeAndName(orderItem.getProduct_code(), orderItem.getProduct_name());
							
							if(null == product)
							{
								sysLog = new SysLog(user == null ? "系统同步" : user.getLgnNm(), user == null ? 2 : user.getUsrId(), "同步采购订单", "同步采购订单" + order.getOrderNo() + "时，添加编号为" + orderItem.getProduct_code() + "的产品，请补全该产品信息",1, actionType, logType);
								this.sysLogDao.insert(sysLog);
								
								product = new Product();
								name = (orderItem.getProduct_name() == null || "".equals(orderItem.getProduct_name())) ? orderItem.getProduct_code() : orderItem.getProduct_name();
								product.setCode(orderItem.getProduct_code());
								product.setName(name);
								product.setCompany_id(2);
								product.setSts(1);
								product.setPrintQR(1);
								this.productDao.insert(product);
								
								hasProduct = true;
							}
							
							orderItem.setSupplier(supplier);
							orderItem.setProduct(product);
							orderItem.setOrderId(orderId);
							this.orderItemDao.insert(orderItem);
						}
					}

					/**未添加产品可以生成二维码*/
					if(!hasProduct)
					{
						createQrcode(user, orderId);
					}
					
					order = this.orderDao.getById(order.getId());
					/**只添加产品*/
					if(hasProduct && !hasSupplier)
					{
						order.setStatus(2);
						order.setRemark("添加产品");
						orderDao.update(order);
					}
					/**只添加供应商*/
					if(!hasProduct && hasSupplier)
					{
						order.setRemark(order.getRemark() + ";" + supplierCodes);
						orderDao.update(order);
					}
					/**同时添加产品、生产供应商*/
					if(hasProduct && hasSupplier)
					{
						order.setStatus(6); 
						order.setRemark(supplierCodes);
						orderDao.update(order);
					}
				}
			}
			
			map.put("orderNoList", orderNoList);
			map.put("code", 200);
			map.put("msg", "Success");
			map.put("heads", headList);
			map.put("details", detailsList);
			sysLog = new SysLog(user == null ? "系统同步" : user.getLgnNm(), user == null ? 2 : user.getUsrId(), "同步采购订单", "同步采购订单成功", 1, actionType, logType);
			this.sysLogDao.insert(sysLog);
		}
		else
		{
			map.put("code", 401);
			map.put("msg", "Failed");
		}
		return map;
	}

	/**
	 * 同步出库单
	 * @author jqi.can
	 * @date 2016-4-25下午02:37:50
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	public HashMap<String, Object> syncSalesOrder(SysUser user)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		Synch synch = synchDao.getMaxByType(2);
		SysLog sysLog = new SysLog();
		int kingId = 0;
		int logType = PisConstants.LogType.Business.getKey();
		String actionType = String.valueOf(PisConstants.LogActionType.SyncOrder.getKey());
		String kingDate = "";
		
		if(null != synch)
		{
			kingId = synch.getKingId();
			kingDate = synch.getKingDate();
		}
		
		String url = SysConfig.get_ga_url() + "api/order/salesOrder.do?kingId=" + kingId + "&kingDate=" + kingDate;
		
		try 
		{
			String response = HttpUtil.executeGet(url);
			JSONObject jsonObj = JSONObject.fromObject(response);
			String code = jsonObj.getString("code");
			List<SysDataType> dataType = null;
			/*if(null != user)
			{
				dataType = dataTypeDao.findByTypeAndCompID(PisConstants.DataType.Restriction.getKey(), user.getCompany_id());
			}*/
			dataType = dataTypeDao.findByTypeAndCompID(PisConstants.DataType.Restriction.getKey(), Constants.WEIFU_COMPANY_ID);
			
			if(null != code && !"".equals(code) && code.equals("200"))
			{
				JSONArray heads = jsonObj.getJSONArray("heads");
				JSONArray details = jsonObj.getJSONArray("details");
				
				List<SalesOrder> headList = heads.toList(heads, SalesOrder.class);
				List<SaleOrderDetail> detailsList = details.toList(details, SaleOrderDetail.class);
				
				if(null == headList || headList.size() < 1)
				{
					map.put("code", "300");
					map.put("msg", "Failure");
					return map;
				}
				
				boolean hasDealer; //false-经销商存在  true-有经销商不存在
				String dealerCode;
				for (SalesOrder salesOrder : headList)
				{
					hasDealer = false;
					SalesOrder order = this.salesOrderDao.getByKingId(salesOrder.getKingId());
					if(order == null)
					{
						dealerCode = salesOrder.getOrderUnitNo().substring(0, 7);
						Dealer dealer = this.dealerDao.getByCode(dealerCode);
						if(null == dealer)
						{
							hasDealer = true;
							/** TODO 无经销商时经销商名称 */
							salesOrder.setUnitName(salesOrder.getOrderUnitName());
							salesOrder.setUnitNo(dealerCode);
						}
						else
						{
							salesOrder.setUnitName(dealer.getDealer_name());
							salesOrder.setUnitNo(dealer.getDealer_code());
						}
						
						salesOrder.setCompanyId(2);
						salesOrder.setOrderNo(salesOrder.getiKingNo() + StringUtil.padLeft(String.valueOf(salesOrder.getKingNo()), 4, "0"));
						salesOrder.setOrderDate(DateUtil.getCurrentDate());
						salesOrder.setSyncDate(DateUtil.getCurrentDate());
						salesOrder.setOrdType(2);
						/** TODO 是否约束dataType无数据默认为1 */
						salesOrder.setIsRestrict(dataType == null ? 1 : Integer.parseInt(dataType.get(0).getDctcd()));
						salesOrder.setRemark(hasDealer ? "需添加经销商编码为" + dealerCode : "");
						
						/** TODO 先插入了订单，判断无产品信息时，订单依旧添加，订单明细加入不了 */
						this.salesOrderDao.insert(salesOrder);
						int saleOrderId = salesOrder.getId();
						Product product;
						String name;
						
						//插入出库单明细
						for (SaleOrderDetail saleOrderDetail : detailsList)
						{
							if(saleOrderDetail.getKingOrderId() == Integer.parseInt(salesOrder.getKingId()))
							{
								saleOrderDetail.setSaleOrderId(saleOrderId);
								product = this.productDao.getByCode(saleOrderDetail.getProductNo());
								
								if(null == product)
								{
									sysLog = new SysLog(user == null ? "系统同步" : user.getLgnNm(), user == null ? 2 : user.getUsrId(), "同步销售订单", "同步销售订单" + salesOrder.getOrderNo() + "时，添加编号为" + saleOrderDetail.getProductNo() + "的产品，请补全该产品信息", 1, actionType, logType);
									this.sysLogDao.insert(sysLog);
									
									/** TODO 产品企业ID */
									product = new Product();
									name = (saleOrderDetail.getProductName() == null || "".equals(saleOrderDetail.getProductName())) ? saleOrderDetail.getProductNo() : saleOrderDetail.getProductName();
									product.setCode(saleOrderDetail.getProductNo());
									product.setName(name);
									product.setCompany_id(2);
									product.setSts(1);
									product.setPrintQR(1);
									this.productDao.insert(product);
								}
								
								saleOrderDetail.setProductId(product.getId());
								saleOrderDetail.setProductName(product.getName());
								this.saleOrderDetailDao.insert(saleOrderDetail);
							}						
						}
					}
					
				}
				
				synch = new Synch();
				synch.setKingId(Integer.parseInt(headList.get(headList.size() - 1).getKingId()));
				synch.setKingDate(headList.get(headList.size() - 1).getOrderDate());
				synch.setSyncType(2);
				this.synchDao.insert(synch);
				
				map.put("code", 200);
				map.put("msg", "Success");
				map.put("heads", headList);
				map.put("details", detailsList);
				sysLog = new SysLog(user == null ? "系统同步" : user.getLgnNm(), user == null ? 2 : user.getUsrId(), "同步销售订单", "同步销售订单成功", 1, actionType, logType);
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
			sysLog = new SysLog("系统同步", 0, "同步销售订单", e.getMessage(), 0, actionType, logType);
			map.put("code", 401);
			map.put("msg", "Failed");
		}
		this.sysLogDao.insert(sysLog);
		return map;
	}
	
	public String saveSaleOrderDetails(int orderId, String itemstr)
	{
		String[] items = itemstr.split(";");
		String msg = "";
		try 
		{
			this.saleOrderDetailDao.deleteByOrderId(orderId);
			
			for(String item : items)
			{
				String[] paramVals = item.split(",");
				SaleOrderDetail entity = new SaleOrderDetail();
				Product product = productDao.getById(Integer.valueOf(paramVals[0]));
				entity.setSaleOrderId(orderId);
				entity.setProductNo(product.getCode());
				entity.setCount(paramVals[1]);
				entity.setBox(paramVals[2]);
				entity.setLocation(paramVals[3]);
				entity.setUnit(paramVals[4]);
				entity.setProductId(product.getId());
				entity.setProductName(product.getName());
				saleOrderDetailDao.insert(entity);
			}
			msg = "1";
		} 
		catch (Exception e) 
		{
			msg = "0";
			e.printStackTrace();
		}
		return msg;
	}

	public HashMap<String, Object> createBat(String[] ids, SysUser user)
	{
		String msg = "";
		HashMap<String, Object> result = new HashMap<String, Object>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<String> msOrderIds = new ArrayList<String>();
		int hasCreated = 0, noProduct = 0, noPackage = 0; //已经生产二维码 订单无产品 产品无包装
		String createdNo = "", noProductNo = "", noPackageNo = "", msOrderId = ""; 
		
		for (String id : ids) 
		{
			map = this.createQrcode(user, Integer.parseInt(id));
			msOrderId = map.get("msOrderId").toString();
			if(map.get("code").toString().equals("2"))
			{
				hasCreated++;
				createdNo += map.get("orderNo").toString() + ",";
			}
			if(map.get("code").toString().equals("3"))
			{
				noProduct++;
				noProductNo += map.get("orderNo").toString() + ",";
			}
			if(map.get("code").toString().equals("4"))
			{
				noPackage++;
				noPackageNo += map.get("orderNo").toString() + ",";
			}
			
			if(null != msOrderId && !"".equals(msOrderId))
			{
				msOrderIds.add(msOrderId);
			}
		}
		
		if(hasCreated > 0)
		{
			msg += "订单" + createdNo.substring(0, createdNo.length() - 1) + "二维码已生成, 不能重复生成；";
		}
		if(noPackage > 0)
		{
			msg += "订单" + noPackageNo.substring(0, noPackageNo.length() - 1) + "中有产品未设置包装，请先设置包装；";
		}
		if(noProduct > 0)
		{
			msg += "订单" + noProductNo.substring(0, noProductNo.length() - 1) + "无明细，请添加明细再生成；";
		}
		if(hasCreated == 0 && noPackage == 0 && noProduct == 0)
		{
			msg = "1";
		}
		result.put("msg", msg);
		result.put("msOrderIds", msOrderIds);
		return result;
	}

	public ArrayList<Object> exportBySupplierId(int supplierID, String searchParam, String fmtm, String totm) 
	{
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("searchParam", "%" + searchParam + "%");
		map.put("fmtm", fmtm);
		map.put("totm", totm);
		map.put("supplierID", String.valueOf(supplierID));
		
		return this.orderItemDao.exportBySupplierId(map);
	}

	@SuppressWarnings({ "unchecked", "static-access" })
	public HashMap<String, Object> updateSalesNo(SysUser user) 
	{
		HashMap<String, Object> result = new HashMap<String, Object>();
		int logType = PisConstants.LogType.Business.getKey();
		String actionType = String.valueOf(PisConstants.LogActionType.SyncOrder.getKey());
		
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		SysLog log = null;
		String kingIds = "";
		String orderNo = "";
		
		try 
		{
			map.put("companyId", Constants.WEIFU_COMPANY_ID);
			map.put("status", 0);
			
			List<SalesOrder> list = this.salesOrderDao.getToUpdate(map);
			List<SalesOrder> urlList = null;
			String ordNo = "";
			int length = 0;
			
			for (SalesOrder salesOrder : list) 
			{
				ordNo = salesOrder.getOrderNo();
				length = ordNo.length();
				
				kingIds += ( salesOrder.getKingId() + ",");
				orderNo += ( Integer.parseInt(ordNo.substring(length - 4, length)) + ",");
			}
			
			String url = SysConfig.get_ga_url() + "api/order/getToUpdate.do";
			String response = HttpUtil.executePost(url, kingIds, orderNo);
			JSONObject urlJsonObj = JSONObject.fromObject(response);
			String urlCode = urlJsonObj.getString("code");
			JSONArray array = urlJsonObj.getJSONArray("list");
			
			System.out.println("====同步销售单 更新订单号开始====");
			if(null != urlCode && !"".equals(urlCode) && urlCode.equals("200"))
			{
				urlList = array.toList(array, SalesOrder.class);
				
				for (SalesOrder order : urlList) 
				{
					SalesOrder salesOrder = this.salesOrderDao.getByKingId(order.getKingId());
					order.setRemark("原订单编号是" + salesOrder.getOrderNo());
					order.setOrderNo(order.getiKingNo() + StringUtil.padLeft(String.valueOf(order.getKingNo()), 4, "0"));
					this.salesOrderDao.updateOrderNo(order);
				}
			}
			System.out.println("====同步销售单 更新订单号成功====");
			log = new SysLog(user == null ? "系统" : user.getLgnNm(), user == null ? 2 : user.getUsrId(), "更新销售单", "更新销售单成功", 1, actionType, logType);
			
			result.put("toCompare", list);
			result.put("toUpdate", urlList);
		}
		catch (Exception e) 
		{
			System.out.println("----同步销售单更新订单号异常----" + e.toString() + "----");
			log = new SysLog(user == null ? "系统" : user.getLgnNm(), user == null ? 2 : user.getUsrId(), "更新销售单", e.getMessage(), 0, actionType, logType);
		}
		
		this.sysLogDao.insert(log);
		return result;
	}
	
}
