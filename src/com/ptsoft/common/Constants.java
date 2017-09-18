package com.ptsoft.common;

public class Constants
{
	/** function表根节点 */
	public static final String MENU_ROOT = "SYS";

	/** 状态为1表示可用 */
	public static final String IMSDCSCHEDULELIST_STS = "1";

	/** 登录页面地址 */
	public static final String LOGIN_URL = "/login.do";
	/** 退出页面地址 */
	public static final String LOGOUT_URL = "/doLogout.do";

	//
	//以下几个Session信息，要在IndexController的logout方法中清除
	//
	/** 当前用户，Session Attribute ‘C_USR’ */
	public static final String SA_USER = "C_USR";
	/** 营业日期，Session Attribute ‘C_BDT’ */
	public static final String SA_BIZDT = "C_BDT";
	
	/** 企业用户所属企业，Session Attribute ‘C_COM’ */
	public static final String SA_COMP = "C_COM";
	/** 供应商用户所属供应商，Session Attribute ‘C_SUP’ */
	public static final String SA_SUPL = "C_SUP";
	/** 经销商用户所属经销商，Session Attribute ‘C_DEL’ */
	public static final String SA_DEAL = "C_DEL";

	/** Session Attribute ‘editData’ */
	public static final String SA_EDIT_DATA = "editData";
	/** Session Attribute ‘storeList’ */
	public static final String SA_STORE_LSIT = "storeList";

	/** Session Attribute ‘SA_SELECTED_DT’ */
	public static final String SA_SELECTED_DT = "Selected_DT";
	/** Session Attribute ‘FUNCTION_ID’ */
	public static final String SA_FUNCTION_ID = "FUNCTION_ID";
	/** Session Attribute ‘FUNCTION_NM’ */
	public static final String SA_FUNCTION_NM = "FUNCTION_NM";
	/** Session Attribute ‘LIST_PARAMTERS’ */
	public static final String SA_LIST_PARAMTERS = "LIST_PARAMTERS";
	/** Session Attribute ‘LIST_FMTP’ */
	public static final String SA_LIST_FMTP = "LIST_FMTP";
	/** Session Attribute ‘LIST_STCD’ */
	public static final String SA_LIST_STCD = "LIST_STCD";
	/** Session Attribute ‘LIST_BIZDT’ */
	public static final String SA_LIST_BIZDT = "LIST_BIZDT";

	/** 上传文件位置 */
	public static final String UPLOAD_PATH = "/upload";
	/** 上传LOGO位置 */
	public static final String UPLOAD_LOGO_PATH = "/upload/logo/";
	/** 上传excel文件位置 */
	public static final String UPLOAD_XLS_PATH = "/upload/xls/";
	/** 导出excel文件位置 */
	public static final String EXPORT_XLS_PATH = "exportxls";
	
	/** 威孚企业id */
	public static final int WEIFU_COMPANY_ID = 2;
	/** 供应商MS编码 */
	public static final String MS_SUPPLIER_CODE = "40000001";
	

	/** 每页记录数 */
	public static final int PAGE_SIZE = 30;

	/** Session Attribute ‘operTag’ */
	public static final String OPERTAG = "operTag";
	/** 编辑页面标志 */
	public static final int OPERTAG_EDIT = 1;

	/** 新增页面标志 */
	public static final int OPERTAG_ADD = 0;

	/**
	 * 服务状态
	 * */
	public enum ServiceState
	{
		/** 未创建 */
		Null
		{
			public int getValue()
			{
				return 0;
			};

			public String getName()
			{
				return "未创建";
			}
		},
		/** 已创建，未运行 */
		WAIT
		{
			public int getValue()
			{
				return 1;
			};

			public String getName()
			{
				return "未运行";
			}
		},
		/** 等待中 */
		SLEEP
		{
			public int getValue()
			{
				return 2;
			};

			public String getName()
			{
				return "等待中";
			}
		},
		/** 运行中 */
		RUNNING
		{
			public int getValue()
			{
				return 3;
			};

			public String getName()
			{
				return "运行中";
			}
		},
		/** 已停止 */
		STOPED
		{
			public int getValue()
			{
				return 4;
			};

			public String getName()
			{
				return "已停止";
			}
		};

		public abstract int getValue();

		public abstract String getName();
	}
}
