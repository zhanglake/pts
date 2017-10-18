package com.ptsoft.job.task;

import com.ptsoft.pts.business.dao.ScanDao;
import com.ptsoft.pts.business.model.vo.ScanRecord;
import com.ptsoft.pts.business.service.ScanRecordService;
import net.sf.ehcache.hibernate.management.impl.BeanUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ptsoft.pts.business.service.OrderService;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class OrderTask 
{
	Logger logger = Logger.getLogger(OrderTask.class);

	@Autowired
	private OrderService orderService;
	@Autowired
	private ScanDao scanDao;

	//@Scheduled(cron = "0 0 0 0/6 * *")
	//@Scheduled(cron = "0 0/2 * * * ?")
	@Scheduled(cron = "30 19-22 11 * * *")
	public void syncPurchase()
	{
		/*this.orderService.syncPurchase(null);
		this.orderService.syncSalesOrder(null);
		this.orderService.updateSalesNo(null);*/
	}

	/**
	 * 每天凌晨1点整删除7天前的日志文件
	 */
	@Scheduled(cron = "0 0 1 * * ?")
	public void deleteLosFiles () {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 7);
		Date today = calendar.getTime();
		File file = new File("/usr/local/tomcat6045/logs/");
		if (file.exists()) {
			deleteFile(file, today);
		}
	}

	private void deleteFile(File file, Date today) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(files[i].lastModified());
				Date time = calendar.getTime();
				if (time.before(today)) {
					logger.debug("----------删除日志文件" + files[i].getName() + "----------");
					files[i].delete();
				}
			}
		}
	}

	/**
	 * 每晚23点整
	 * 为MS二维码当天已出库的二维码增加包装和入库记录
	 */
	@Scheduled(cron = "0 0 23 * * ?")
	public void generatePackageAndStockInScanRecordForMS() {
		// 获取MS已出库的二维码扫描出库扫码记录
		List<ScanRecord> stockOutRecord = this.scanDao.getTodayMSScanRecord();
		List<ScanRecord> newRecord = new ArrayList<ScanRecord>();
		newRecord.addAll(this.generatePackageRecord(stockOutRecord));
		newRecord.addAll(this.generateStockInRecord(stockOutRecord));
		if (null != newRecord && newRecord.size() > 0) {
			this.scanDao.insertLot(newRecord);
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
