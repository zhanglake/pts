package com.ptsoft.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil 
{
	/**
	 * 
	 * 读取Excel的内容
	 * 第一维数组存储的是一行中格列的值
	 * 二维数组存储的是 多少行
	 * file 读取数据的源excel
	 * ignoreRows 读取数据忽略的行数，比喻行头不需要读入，忽略行数为1
	 * @Description  
	 * @author jqi.can
	 * @return String[][] 读出的excel数据内容
	 */
	public static  String[][] getData(File file, int ignoreRows) throws Exception
	{
		if (file == null || !file.exists() || !(isExcel2003(file.getPath()) || isExcel2007(file.getPath())))  
        {  
            return null;  
        } 
		boolean isExcel2003 = false;
		if(isExcel2003(file.getPath()))
		{
			isExcel2003 = true;
		}
		
		List<String[]> result = new ArrayList<String[]>();
		int rowSize = 0;
		
		InputStream fs = new FileInputStream(file);
		Workbook wb = null;
		
		if(isExcel2003)
		{
			wb = new HSSFWorkbook(fs);
		}
		else
		{
			wb = new XSSFWorkbook(fs);
		}
		
		Cell cell = null;
		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++)
		{
			Sheet st = wb.getSheetAt(sheetIndex);
			//第一行为标题　不取
			for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++)
			{
				Row row = st.getRow(rowIndex);
				if(row == null)
				{
					continue;
				}
				int tempRowSize = row.getLastCellNum() + 1;
				if(tempRowSize > rowSize)
				{
					rowSize = tempRowSize;
				}
				String[] values = new String[rowSize];
				Arrays.fill(values, "");
				boolean hasValue = false;
				for (short columnIndex = 0; columnIndex < row.getLastCellNum(); columnIndex++)
				{
					String value = "";
					cell = row.getCell(columnIndex);
					if(cell != null){
						switch(cell.getCellType())
						{
						
							case HSSFCell.CELL_TYPE_STRING: //字符串
								value = cell.getStringCellValue();
								break;
							case HSSFCell.CELL_TYPE_NUMERIC: //数字
								if(HSSFDateUtil.isCellDateFormatted(cell))
								{
									Date date = cell.getDateCellValue();
									if(date != null)
									{
										value = new SimpleDateFormat("yyyy-MM-dd").format(date);
									}
									else
									{
										value = "";
									}
								}
								else
								{
									value = new DecimalFormat("0").format(cell.getNumericCellValue());
								}
								break;
							case HSSFCell.CELL_TYPE_FORMULA: //公式
								if(!cell.getStringCellValue().equals(""))
								{
									value = cell.getStringCellValue();
								}
								else
								{
									value = cell.getNumericCellValue() + "";
								}
								break;
							case HSSFCell.CELL_TYPE_BLANK: break;
							case HSSFCell.CELL_TYPE_ERROR: value = ""; break;
							case HSSFCell.CELL_TYPE_BOOLEAN:
								value = (cell.getBooleanCellValue() == true ? "Y" : "N");
								break;
							default: value = "";
						}
					}
					if(columnIndex == 0 && value.trim().equals(""))
					{
						break;
					}
					values[columnIndex] = rightTrim(value);
					hasValue = true;
				}
				if(hasValue)
				{
					result.add(values);
				}
			}
		}
		
		String[][] returnArray = new String[result.size()][rowSize];
		for (int i = 0; i < returnArray.length; i++)
		{
			returnArray[i] = (String[]) result.get(i);
		}
		return returnArray;
	}

	/**
	 * 
	 * 去掉字符串右边的空格 
	 * @Description value--要处理的字符串 
	 * @author jqi.can
	 * @return String--处理后的字符串
	 */
	private static String rightTrim(String value)
	{
		if(value == null)
		{
			return "";
		}
		int length = value.length();
		for (int i = length - 1; i >= 0 ; i--)
		{
			if (value.charAt(i) != 0x20)
			{
				break;
			}
			length--;
		}
		return value.substring(0, length);
	}
	
	public static boolean isExcel2007(String filePath)  
    {  
		return filePath.matches("^.+\\.(?i)(xlsx)$");  
    } 
	
	public static boolean isExcel2003(String filePath)  
    {  
		return filePath.matches("^.+\\.(?i)(xls)$");  
    }
}
