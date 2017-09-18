package com.ptsoft.job.task;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ptsoft.pts.business.service.OrderService;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

@Component
public class OrderTask 
{
	Logger logger = Logger.getLogger(OrderTask.class);

	@Autowired
	private OrderService orderService;
	
	//@Scheduled(cron = "0 0 0 0/6 * *")
	//@Scheduled(cron = "0 0/2 * * * ?")
	@Scheduled(cron = "30 19-22 11 * * *")
	public void syncPurchase()
	{
		System.out.println(123456566);
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

}
