package com.ptsoft.pts;

import java.util.HashMap;
import java.util.Map;

public class PisConstants
{
	public static String QRSalt = "WEIFU_PTS";
	/**
	 * 系统类型
	 * */
	public enum SystemType
	{
		Platform(1, "平台"),
		Company(2, "企业"),
		Supplier(3, "供应商"),
		Dealer(4, "经销商"),
		PDA(5, "PDA"),
		Terminal(6, "终端");
		
		private int key;
		private String text;
		private static String optionStr = "";
		private static final Map<Integer, SystemType> intToEnum = new HashMap<Integer, SystemType>();
		private static final Map<String, SystemType> stringToEnum = new HashMap<String, SystemType>();
		static 
	    {
	        for(SystemType type : values()) 
	        {
	        	intToEnum.put(type.key, type);
	            stringToEnum.put(type.toString(), type);
	            optionStr += "<option value=\"" + type.getKey() + "\">" + type.getText() + "</option>";
	        }
	    }
		SystemType(int key, String text) 
		{
			this.key = key;
	        this.text = text;
	    }
		public int getKey()
		{
			return this.key;
		}
	    public String getText() 
	    {
	        return this.text;
	    }
	    public static SystemType FromString(String symbol) 
	    {
	        return stringToEnum.get(symbol);
	    }
	    public static SystemType FromKey(Integer key)
	    {
	    	return intToEnum.get(key);
	    }
	    /**下拉列表数据源*/
	    public static String ToOptionString()
	    {
	    	return optionStr;
	    }
	    @Override
	    public String toString() 
	    {
	        return text;
	    }
	}
	
	/**系统中SysDataType表中使用的key*/
	public enum DataType
	{
		ProductType(			50,		"物品类型"),
		PackageType(			51, 	"包装类型"),
		QRCodeSize(				52,		"二维码尺寸"),
		PDAAction(				100,	"PDA操作"),
		PkgAction(				200, 	"包装操作"),
		Restriction(			300, 	"销售单出库约束"),
		WFCompany(				400, 	"威孚集团"),
		TLCompany(				401, 	"天力集团");
		
		
		private int key;
		private String text;
		private static final Map<Integer, DataType> intToEnum = new HashMap<Integer, DataType>();
		private static final Map<String, DataType> stringToEnum = new HashMap<String, DataType>();
		static 
	    {
	        for(DataType type : values()) 
	        {
	        	intToEnum.put(type.key, type);
	            stringToEnum.put(type.toString(), type);
	        }
	    }
		DataType(int key, String text) 
		{
			this.key = key;
	        this.text = text;
	    }
		public int getKey()
		{
			return this.key;
		}
	    public String getText() 
	    {
	        return this.text;
	    }
	    public static DataType FromString(String symbol) 
	    {
	        return stringToEnum.get(symbol);
	    }
	    public static DataType FromKey(Integer key)
	    {
	    	return intToEnum.get(key);
	    }
	    @Override
	    public String toString() 
	    {
	        return text;
	    }
	};
	
	/**可用/禁用*/
	public enum Available
	{
		No(0, "禁用"),
		Yes(1, "可用");
		
		private int key;
		private String text;
		private static String optionStr = "";
		private static final Map<Integer, Available> intToEnum = new HashMap<Integer, Available>();
		private static final Map<String, Available> stringToEnum = new HashMap<String, Available>();
		static 
	    {
	        for(Available type : values()) 
	        {
	        	intToEnum.put(type.key, type);
	            stringToEnum.put(type.toString(), type);
	            optionStr += "<option value=\"" + type.getKey() + "\">" + type.getText() + "</option>";
	        }
	    }
		Available(int key, String text) 
		{
			this.key = key;
	        this.text = text;
	    }
		public int getKey()
		{
			return this.key;
		}
	    public String getText() 
	    {
	        return this.text;
	    }
	    public static Available FromString(String symbol) 
	    {
	        return stringToEnum.get(symbol);
	    }
	    public static Available FromKey(Integer key)
	    {
	    	return intToEnum.get(key);
	    }
	    /**下拉列表数据源*/
	    public static String ToOptionString()
	    {
	    	return optionStr;
	    }
	    @Override
	    public String toString() 
	    {
	        return text;
	    }
	};
	
	/**是/否*/
	public enum Status
	{
		No(0, "否"),
		Yes(1, "是");
		
		private int key;
		private String text;
		private static String optionStr = "";
		private static final Map<Integer, Status> intToEnum = new HashMap<Integer, Status>();
		private static final Map<String, Status> stringToEnum = new HashMap<String, Status>();
		static 
	    {
	        for(Status type : values()) 
	        {
	        	intToEnum.put(type.key, type);
	            stringToEnum.put(type.toString(), type);
	            optionStr += "<option value=\"" + type.getKey() + "\">" + type.getText() + "</option>";
	        }
	    }
		Status(int key, String text) 
		{
			this.key = key;
	        this.text = text;
	    }
		public int getKey()
		{
			return this.key;
		}
	    public String getText() 
	    {
	        return this.text;
	    }
	    public static Status FromString(String symbol) 
	    {
	        return stringToEnum.get(symbol);
	    }
	    public static Status FromKey(Integer key)
	    {
	    	return intToEnum.get(key);
	    }
	    public static String ToOptionString()
	    {
	    	return optionStr;
	    }
	    @Override
	    public String toString() 
	    {
	        return text;
	    }
	};
	
	/**处理状态*/
	public enum ProcessStatus
	{
		Draft (0 , "未处理"),
		Finish(1 , "已处理"),
		Fault (2 , "处理失败");
		
		private int key;
		private String text;
		private static String optionStr = "";
		private static final Map<Integer, ProcessStatus> intToEnum = new HashMap<Integer, ProcessStatus>();
		private static final Map<String, ProcessStatus> stringToEnum = new HashMap<String, ProcessStatus>();
		static 
		{	
			optionStr+="<option value=-1 >----请选择----</option>";
			for(ProcessStatus type : values()) 
			{
				intToEnum.put(type.key, type);
				stringToEnum.put(type.toString(), type);
				optionStr += "<option value=\"" + type.getKey() + "\">" + type.getText() + "</option>";
			}
		}
		ProcessStatus(int key, String text) 
		{
			this.key = key;
			this.text = text;
		}
		public int getKey()
		{
			return this.key;
		}
		public String getText() 
		{
			return this.text;
		}
		public static ProcessStatus FromString(String symbol) 
		{
			return stringToEnum.get(symbol);
		}
		public static ProcessStatus FromKey(Integer key)
		{
			return intToEnum.get(key);
		}
		public static String ToOptionString()
		{
			return optionStr;
		}
		@Override
		public String toString() 
		{
			return text;
		}
	};
	
	public enum QRCodeStatus{
		Created(0, "已生成"),
		Binded(1, "已绑定"),
		Printed(2, "已打印"),
		Packaged(3, "已包装"),
		In(4, "已入库"),
		Out(5, "已出库"),
		InFlow(6, "流通中"),
		Used(7, "已使用"),
		Void(8,"已作废"),
		Divided(9, "已拆分");		
		
		private int key;
		private String text;
		
		QRCodeStatus(int key, String text){
			this.key = key;
			this.text = text;
		}
		
		public int getKey() {
			return key;
		}
		
		public String getText(){
			return text;
		}
		
	}
	
	public enum ActionType{
		Created(0, "生成"),
		Binded(1, "绑定"),
		Printed(2, "打印"),
		Package(3, "包装"),
		In(4, "入库"),
		Out(5, "出库"),
		DealerIn(6, "经销商收货"),
		DealerTrace(7, "经销商溯源"),
		UserTrace(8, "终端用户溯源"),
		Returned(9, "退换货"),
		Void(10, "作废");
		
		private int key;
		private String text;
		
		ActionType(int key, String text){
			this.key = key;
			this.text = text;
		}
		
		public int getKey() {
			return key;
		}
		
		public String getText(){
			return text;
		}
	}
	
	/**日志类型*/
	public enum LogType
	{
		Operate(1, 		"普通操作"),
		Exception(2, 	"异常"),
		Business(3, 	"业务相关");
		
		private int key;
		private String text;
		private static String optionStr = "";
		private static final Map<Integer, LogType> intToEnum = new HashMap<Integer, LogType>();
		private static final Map<String, LogType> stringToEnum = new HashMap<String, LogType>();
		static 
	    {
	        for(LogType type : values()) 
	        {
	        	intToEnum.put(type.key, type);
	            stringToEnum.put(type.toString(), type);
	            optionStr += "<option value=\"" + type.getKey() + "\">" + type.getText() + "</option>";
	        }
	    }
		LogType(int key, String text) 
		{
			this.key = key;
	        this.text = text;
	    }
		public int getKey()
		{
			return this.key;
		}
	    public String getText() 
	    {
	        return this.text;
	    }
	    public static LogType FromString(String symbol) 
	    {
	        return stringToEnum.get(symbol);
	    }
	    public static LogType FromKey(Integer key)
	    {
	    	return intToEnum.get(key);
	    }
	    public static String ToOptionString()
	    {
	    	return optionStr;
	    }
	    @Override
	    public String toString() 
	    {
	        return text;
	    }
	};
	
	/**日志类型*/
	public enum LogActionType
	{
		PlatLogin(1, 		"平台登录"),
		UpdateProduct(2,	"产品修改"),
		SyncOrder(4,		"同步订单"),
		CreateOrder(5, 		"创建订单"),	
		CreateQrcode(6, 	"生成二维码"),
		RecoveryQrcode(7,	"回收二维码"),
		PrintQrcode(8,		"打印二维码"),
		IssueQrcode(9, 		"下发二维码");
		
		private int key;
		private String text;
		private static String optionStr = "";
		private static final Map<Integer, LogActionType> intToEnum = new HashMap<Integer, LogActionType>();
		private static final Map<String, LogActionType> stringToEnum = new HashMap<String, LogActionType>();
		static 
	    {
			optionStr += "<option value=\"-1\">----请选择----</option>";
	        for(LogActionType type : values()) 
	        {
	        	intToEnum.put(type.key, type);
	            stringToEnum.put(type.toString(), type);
	            optionStr += "<option value=\"" + type.getKey() + "\">" + type.getText() + "</option>";
	        }
	    }
		LogActionType(int key, String text) 
		{
			this.key = key;
	        this.text = text;
	    }
		public int getKey()
		{
			return this.key;
		}
	    public String getText() 
	    {
	        return this.text;
	    }
	    public static LogActionType FromString(String symbol) 
	    {
	        return stringToEnum.get(symbol);
	    }
	    public static LogActionType FromKey(Integer key)
	    {
	    	return intToEnum.get(key);
	    }
	    public static String ToOptionString()
	    {
	    	return optionStr;
	    }
	    @Override
	    public String toString() 
	    {
	        return text;
	    }
	};
	
}
