package com.ptsoft.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

import com.ptsoft.common.Constants;

public class ExportUtil
{

	@SuppressWarnings("all")
	public static String ExportToExcel(LinkedHashMap<String, String> title, ArrayList dataList, String xlsTitle) throws IOException
	{

		// 生成excel时间文件名
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String reportFileName = xlsTitle + formatter.format(new Date());
		// 生成Excel文件及路径
		String reportFullFile = Constants.EXPORT_XLS_PATH  + File.separator + reportFileName + ".xls";
		String reportFilePath = SysConfig.getRootPath() + File.separator + reportFullFile;
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		HSSFSheet sheet = hssfWorkbook.createSheet("Sheet1");
		// 头部样式
		HSSFCellStyle headCellStyle = getHeadStyle(hssfWorkbook);
		// 主体样式
		HSSFCellStyle bodyCellStyle = getBodyStyle(hssfWorkbook);
		
		// 第一行标题栏
		HSSFRow firstrow = sheet.createRow(0);

		// 循环迭代LinkedHashMap取出键值插入一二行
		Iterator titleIter = title.keySet().iterator();
		int columTag = 0; // 从第一列开始
		
		while (titleIter.hasNext())
		{
			String columnKey = (String) titleIter.next();
			// 在一行内循环
			HSSFCell ti = firstrow.createCell(columTag);
			ti.setCellValue(title.get(columnKey).toString());
			ti.setCellStyle(headCellStyle);
			columTag = columTag + 1;
		}
		
		int colNum=0;
		for (int i = 0; i < dataList.size(); i++)
		{
			// 创建一行
			HSSFRow row = sheet.createRow(i + 1);
			// 得到要插入的每一条记录
			LinkedHashMap<String, String> data = (LinkedHashMap<String, String>) dataList.get(i);
			Iterator j = data.entrySet().iterator();
			int col = 0;
			while (j.hasNext())
			{
				// 只遍历一次,速度快
				Map.Entry e = (Map.Entry) j.next();
				if (title.containsKey(e.getKey()))
				{
					// 在一行内循环
					HSSFCell xh = row.createCell(col);
					xh.setCellValue(e.getValue().toString());
					xh.setCellStyle(bodyCellStyle);
					col = col + 1;
				}
			}
			colNum = col;
		}
		for (int i = 0; i < colNum; i++) {
			sheet.autoSizeColumn(i, true);
		}
		// 创建文件输出流，准备输出电子表格
		OutputStream out = new FileOutputStream(reportFilePath);
		hssfWorkbook.write(out);
		out.close();
		return SysConfig.get_pts_url() + reportFullFile;
	}
	
	/**
	 * 设置表头样式
	 * @param hssfWorkbook
	 * @return
	 */
	public static HSSFCellStyle getHeadStyle(HSSFWorkbook hssfWorkbook){
		HSSFCellStyle headStyle = hssfWorkbook.createCellStyle();
		Font headerFont = hssfWorkbook.createFont();
		headerFont.setFontName("Arial");
		headerFont.setFontHeightInPoints((short)12);
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		
		headStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); //水平布局：居中
		headStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); //垂直居中
		headStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		headStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		headStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		headStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		headStyle.setWrapText(true);
		headStyle.setFont(headerFont);
		return headStyle;
	}
	
	/**
	 * 设置表体样式
	 * @param reportFilePath
	 */
	public static HSSFCellStyle getBodyStyle(HSSFWorkbook hssfWorkbook)
	{
		HSSFCellStyle bodyStyle = hssfWorkbook.createCellStyle();
		Font headerFont = hssfWorkbook.createFont();
		headerFont.setFontName("Arial");
		headerFont.setFontHeightInPoints((short)10);
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		
		bodyStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); //水平布局：居中
		bodyStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); //垂直居中
		bodyStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		bodyStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		bodyStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		bodyStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		return bodyStyle;
	}

	public static void deleteExcel(String reportFilePath)
	{
		File excel = new File(reportFilePath);
		if (excel.isFile() && excel.exists())
		{
			excel.delete(); // 把Excel文件删除
		}
	}
	
	public static String exportToExcel(String columns, String cnts, String title, HttpServletResponse response)
	{
		List<String> columnsList = Arrays.asList(columns.split(","));
		List<List<String>> cntsLists = new ArrayList<List<String>>();
		String[] cnt = cnts.split(";");
		List<String> tempList = new ArrayList<String>();
		for (int i = 0; i < cnt.length; i++)
		{
			tempList = Arrays.asList(cnt[i].split(","));
			cntsLists.add(tempList);
		}

		String url = "";
		try
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			String filePath = SysConfig.getRootPath() + File.separator  + "exportxls/";
			String fileName = title + formatter.format(new Date()) + ".xls";
			//fileName = java.net.URLEncoder.encode(fileName, "utf-8");
			
			File file = new File(filePath);
			if (!file.exists() && !file.isDirectory())
			{
				file.mkdir();
			}
			file = new File(filePath + fileName);
			if (!file.exists())
			{
				file.createNewFile();
			}

			WritableWorkbook workbook = Workbook.createWorkbook(file);
			createExcel(workbook, columnsList, cntsLists, title);

			url = SysConfig.get_pts_url() + "exportxls" + File.separator + fileName;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return url;
	}

	private static void createExcel(WritableWorkbook workbook, List<String> columnsList, List<List<String>> cntList, String title)
	{
		try
		{
			WritableSheet sheet = workbook.createSheet("Sheet1", 0);
			sheet.getSettings().setShowGridLines(true);
			sheet.getSettings().setProtected(false);

			for (int i = 0; i < columnsList.size(); i++)
			{
				sheet.setColumnView(i, 30);
			}

			Label titleLabel = null;
			for (int i = 0; i < columnsList.size(); i++)
			{
				titleLabel = new Label(i, 0, columnsList.get(i), getColumnsFormat());
				sheet.addCell(titleLabel);
			}

			Label cntLabel = null;
			for (int i = 0, k = 0; i < cntList.size(); i++, k++)
			{
				List<String> tempList = cntList.get(i);
				for (int j = 0; j < tempList.size(); j++)
				{
					cntLabel = new Label(j, k + 1, tempList.get(j), getCntsFormat());
					sheet.addCell(cntLabel);
				}
			}
			workbook.write();
			workbook.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static WritableCellFormat getColumnsFormat() throws Exception
	{
		//设置字体   
		WritableFont wf = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);

		//创建单元格FORMAT   
		WritableCellFormat wcf = new WritableCellFormat(wf);
		wcf.setAlignment(Alignment.CENTRE);
		wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
		wcf.setLocked(true);
		wcf.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		wcf.setBackground(Colour.GREY_25_PERCENT);
		return wcf;
	}

	private static CellFormat getCntsFormat() throws Exception
	{
		WritableCellFormat wcf = new WritableCellFormat();
		wcf.setAlignment(Alignment.CENTRE);
		wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
		wcf.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		return wcf;
	}
}
