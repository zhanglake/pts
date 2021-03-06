package com.ptsoft.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ptsoft.pts.business.dao.ScanDao;
import com.ptsoft.pts.business.model.vo.ScanRecord;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ptsoft.common.util.ResponseUtils;
import com.ptsoft.pts.account.model.vo.SysUser;
import com.ptsoft.pts.account.service.UserService;
import com.ptsoft.pts.basic.service.TraceService;
import com.ptsoft.pts.business.model.vo.SalesOrder;
import com.ptsoft.pts.business.service.SaleOrderService;

@Controller("ApiInoutController")
@RequestMapping("/api/inout/*")
public class InoutController {
	
	Logger logger = Logger.getLogger(InoutController.class);
	
	@Autowired
	private TraceService traceService;
	@Autowired
	private UserService userService;
	@Autowired
	private SaleOrderService saleOrderService;
	@Autowired
	private ScanDao scanDao;

	@RequestMapping(value="test", method=RequestMethod.POST)
	public void test(HttpServletResponse response) throws Exception
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("code", 1);
		traceService.test();
		ResponseUtils.renderJson(response, map);
	}
	
	
	/**
	 * 入库
	 * @author zumin.yang
	 * @date 2016-03-15
	 * @param qrcodes 二维码
	 * @param deviceNo 设备号 
	 * @throws Exception 
	 */
	@RequestMapping(value="in", method=RequestMethod.POST)
	public void in(HttpServletRequest request, HttpServletResponse response, String qrcodes, String deviceNo) throws Exception
	{
		response.setHeader("Access-Control-Allow-Origin", "*");
		HashMap<String, Object> map = new HashMap<String, Object>();
		try 
		{
			String username = request.getParameter("username");
			String token = request.getParameter("token");
			SysUser user = userService.findByNameAndToken(username, token);
			if(null == user)
			{
				map.put("code", 0);
				map.put("msg", "登录已失效，请重新登录后操作");
			}
			else
			{
				map = traceService.saveIn(user, qrcodes, deviceNo);
			}			
		} 
		catch (Exception e) 
		{
			logger.error(e.toString() + "----in controller");
			map.put("code", 0);
			map.put("msg", "入库失败");
			map.put("msg_en", "Instock failure");
			map.put("msg_code", "2002");
		}
		
		ResponseUtils.renderJson(response, map);
	}
	
	/**
	 * 出库
	 * @author zumin.yang
	 * @date 2016-03-16
	 * @param orderNo 出库单号
	 * @param qrcodes 出库扫描二维码
	 * @param deviceNo 设备号
	 * @throws Exception 
	 */
	@RequestMapping(value="out", method=RequestMethod.POST)
	public void out(HttpServletRequest request, HttpServletResponse response, String orderNo, String qrcodes, String deviceNo) throws Exception
	{
		response.setHeader("Access-Control-Allow-Origin", "*");

		String username = request.getParameter("username");
		String token = request.getParameter("token");

		SysUser user = userService.findByNameAndToken(username, token);
		HashMap<String, Object> map = new HashMap<String, Object>();
		try
		{
			if(null == user)
			{
				map.put("code", 0);
				map.put("msg", "登录已失效，请重新登录后操作");
			}
			else
			{
				map = traceService.saveOut(user, orderNo, qrcodes, deviceNo);
			}
		}
		catch(Exception ex)
		{
			logger.error("****出库 error****" + ex.getMessage());
			map=new HashMap<String, Object>();
			map.put("code", 0);
			map.put("msg", "出库失败，"+ex.getMessage());
			map.put("msg_en", "Outstock failure. "+ex.getMessage());
			map.put("msg_code", "3001");
		}

		ResponseUtils.renderJson(response, map);
	}

	@RequestMapping(value="generate", method=RequestMethod.POST)
	public void generateFullScanRecord() {
		this.generatePackageAndStockInScanRecordForMS();
	}
	
	/**
	 * 退货（不换二维码）洗白
	 * @author zumin.yang
	 * @date 2016-03-16
	 * @param qrcode 二维码
	 */
	@RequestMapping(value="return", method=RequestMethod.POST)
	public void returned(HttpServletRequest request, HttpServletResponse response, String qrcode)
	{
		response.setHeader("Access-Control-Allow-Origin", "*");

		String username = request.getParameter("username");
		String token = request.getParameter("token");
		SysUser user = userService.findByNameAndToken(username, token);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(null == user)
		{
			map.put("code", "0");
			map.put("msg", "登录已失效，请重新登录后操作");
		}
		else
		{
			map = traceService.returned(qrcode, user);
		}
		ResponseUtils.renderJson(response, map);
	}
	
	/**
	 * 作废二维码
	 * @author zumin.yang
	 * @date 2016-03-16
	 * @param qrcode 二维码
	 */
	@RequestMapping(value="void", method=RequestMethod.POST)
	public void toVoid(HttpServletRequest request, HttpServletResponse response, String qrcode)
	{
		response.setHeader("Access-Control-Allow-Origin", "*");

		String username = request.getParameter("username");
		String token = request.getParameter("token");
		SysUser user = userService.findByNameAndToken(username, token);
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(null == user)
		{
			map.put("code", "0");
			map.put("msg", "登录已失效，请重新登录后操作");
		}
		else
		{
			map = traceService.toVoid(qrcode, user);
		}
		ResponseUtils.renderJson(response, map);
	}
	
	/**
	 * 获取出库单明细
	 * @author jqi.can
	 * @date 2016-3-24下午09:21:02
	 * @param orderNo 出库单号
	 */
	@RequestMapping(value="saleOrderDetail", method = RequestMethod.POST)
	public void saleOrderDetail(HttpServletRequest request, HttpServletResponse response, String orderNo)
	{
		response.setHeader("Access-Control-Allow-Origin", "*");

		String username = request.getParameter("username");
		String token = request.getParameter("token");
		SysUser user = userService.findByNameAndToken(username, token);
		HashMap<String, Object> map = new HashMap<String, Object>();
		SalesOrder salesOrder = this.saleOrderService.getByKingId(orderNo);
		if(user == null)
		{
			map.put("code", "0");
			map.put("msg", "登录已失效，请重新登录后操作");
		}
		else
		{
			if(salesOrder == null)
			{
				map.put("detail", null);
				map.put("isRestrict", 1);
				map.put("code", "1");
				map.put("msg", "出库单不存在");
			}
			else
			{
				map.put("detail", this.saleOrderService.getDetailByOrderId(String.valueOf(salesOrder.getId())));
				map.put("isRestrict", salesOrder.getIsRestrict());
				map.put("code", "1");
				map.put("msg", "获取出库单明细成功！");
			}
		}
		ResponseUtils.renderJson(response, map);
	}

	private void generatePackageAndStockInScanRecordForMS() {
		// 获取MS已出库的二维码扫描出库扫码记录
		List<ScanRecord> stockOutRecord = this.scanDao.getTodayMSScanRecord();
		List<ScanRecord> newRecord = new ArrayList<ScanRecord>();
//		newRecord.addAll(this.generatePackageRecord(stockOutRecord));
		newRecord.addAll(this.generateStockInRecord(stockOutRecord));
		if (null != newRecord && newRecord.size() > 0) {
			try {
				this.scanDao.insertLot(newRecord);
			} catch (Exception e) {
				logger.error("-------17101904生成前置扫描记录失败:" + e.toString());
			}
		}
	}

	/**
	 * 保存包装记录 -- MS
	 * @param stockOutRecord
	 */
	private List<ScanRecord> generatePackageRecord(List<ScanRecord> stockOutRecord) {
		List<ScanRecord> pkgRecordList = new ArrayList<ScanRecord>();
		ScanRecord pkgRecord = null;
		for (ScanRecord scanRecord : stockOutRecord) {
			pkgRecord = new ScanRecord();
			pkgRecord.setQrcode(scanRecord.getQrcode());
			pkgRecord.setOperatorId(scanRecord.getOperatorId());
			pkgRecord.setOperator(scanRecord.getOperator());
			pkgRecord.setCreateTime(scanRecord.getCreateTime());
			pkgRecord.setActionType(3);
			pkgRecord.setActionName("包装");
			pkgRecord.setDeviceNo(scanRecord.getDeviceNo());
			pkgRecordList.add(pkgRecord);
		}
		return pkgRecordList;
	}

	/**
	 * 保存入库记录 -- MS
	 * @param stockOutRecord
	 */
	private List<ScanRecord> generateStockInRecord(List<ScanRecord> stockOutRecord) {
		List<ScanRecord> stockInRecordList = new ArrayList<ScanRecord>();
		ScanRecord stockInRecord = null;
		for (ScanRecord scanRecord : stockOutRecord) {
			stockInRecord = new ScanRecord();
			stockInRecord.setQrcode(scanRecord.getQrcode());
			stockInRecord.setDeviceNo(scanRecord.getDeviceNo());
			stockInRecord.setActionName("入库");
			stockInRecord.setActionType(4);
			stockInRecord.setCreateTime(scanRecord.getCreateTime());
			stockInRecord.setOperator(scanRecord.getOperator());
			stockInRecord.setOperatorId(scanRecord.getOperatorId());
			stockInRecordList.add(stockInRecord);
		}
		return stockInRecordList;
	}
}
