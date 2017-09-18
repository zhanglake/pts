/**
 * 
 */
package com.ptsoft.pts.system.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.ptsoft.common.annotation.ServiceLog;
import com.ptsoft.common.base.BaseService;
import com.ptsoft.common.base.EntityDao;
import com.ptsoft.common.util.Pageable;
import com.ptsoft.common.util.StringUtil;
import com.ptsoft.pts.PisConstants;
import com.ptsoft.pts.business.dao.PackageDao;
import com.ptsoft.pts.system.dao.SysDataTypeDao;
import com.ptsoft.pts.system.model.vo.SysDataType;

@Service
public class SysDataTypeService extends BaseService<SysDataType, String>
{
	@Autowired
	private SysDataTypeDao dataTypeDao;
	@Autowired
	private PackageDao packageDao;

	@SuppressWarnings("rawtypes")
	protected EntityDao getEntityDao()
	{
		return dataTypeDao;
	}

	/** 取全部 */
	@Cacheable(cacheName = "dataTypeCache")
	public List<SysDataType> findByType(int type)
	{
		return dataTypeDao.findByType(type);
	}

	/** 取可用+dicCode */
	@Cacheable(cacheName = "dataTypeCache")
	public List<SysDataType> findByType(int type, String dicCode)
	{
		return dataTypeDao.findByType(type, dicCode);
	}

	/**
	 * 保存
	 */
	@Override
	@ServiceLog(description = "保存字典数据")
	@TriggersRemove(cacheName = "dataTypeCache", removeAll = true)
	public void save(SysDataType item)
	{
		if (item.getDctcd() != null && item.getDctcd() != "")
		{
			dataTypeDao.update(item);
		}
		else
		{
			String dictCd = "T" + StringUtil.padLeft(String.valueOf(item.getTpid()), 3, "0") + new SimpleDateFormat("HHmmss").format(Calendar.getInstance().getTime());
			item.setDctcd(dictCd);
			dataTypeDao.insert(item);
		}
		/*
		 * // 编码转换为大写 item.setTpcd(item.getTpcd().toUpperCase()); String msg = null; // 验证是否存在 int cout = this.dataTypeDao.getDataTypeCount(item.getTpid(), item.getTpcd(), item.getDctcd()); if (cout
		 * == 0) { if (item.getOperTag() == 0) { } else { } msg = "数据保存成功！"; } else { msg = "数据保存失败，编码已存在！"; }
		 */
		// return msg;
	}

	/**
	 * 把列表转换为下拉选项内容
	 * 
	 * @param list 数据源
	 * @param valueIsDctCd false：取dctCd字段, true：取Tpcd字段
	 */
	public String listToOptions(List<SysDataType> list, boolean valueIsDctCd)
	{
		if (list == null)
			return "";

		StringBuilder builder = new StringBuilder();
		builder.append("<option value=0 >-请选择-</option>");
		for (SysDataType type : list)
		{
			// <option value="WY">Wyoming</option>
			/*
			 * if (valueIsDctCd) { builder.append("<option value=\"" + type.getDctcd() + "\">" + type.getTpnm() + "</option>"); } else { builder.append("<option value=\"" + type.getTpcd() + "\">" +
			 * type.getTpnm() + "</option>"); }
			 */
			if (valueIsDctCd)
			{
				builder.append("<option value=\"" + type.getTpcd() + "\">" + type.getTpnm() + "</option>");

			}
			else
			{
				builder.append("<option value=\"" + type.getDctcd() + "\">" + type.getTpnm() + "</option>");
			}
		}
		return builder.toString();
	}

	@Cacheable(cacheName = "dataTypeCache")
	public List<SysDataType> getStoreType(Integer tpid, String stcd)
	{
		return this.dataTypeDao.getStoreType(tpid, stcd);
	}

	/**
	 * 把列表转换为checkbox
	 */
	public String listToCheckbox(List<SysDataType> list)
	{
		if (list == null)
			return "";
		StringBuilder builder = new StringBuilder();
		for (SysDataType dataType : list)
		{
			if (dataType.getChecked() == 1)
			{
				builder.append("<input type=\"checkbox\" name=\"sttp\" value=\"" + dataType.getDctcd() + "\" checked=\"checked\" />" + dataType.getTpnm() + "&emsp;");
			}
			else
			{
				builder.append("<input type=\"checkbox\" name=\"sttp\" value=\"" + dataType.getDctcd() + "\" />" + dataType.getTpnm() + "&emsp;");
			}
		}
		return builder.toString();
	}

	/**
	 * 获取市场信息组织成tab页 xml
	 * 
	 * @param itmcd
	 * @return
	 */
	public String listToTabXml(List<SysDataType> list)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("<?xml version='1.0' encoding='utf-8'?>");
		builder.append("<tabbar><row>");
		if (list != null)
		{
			for (SysDataType item : list)
			{
				builder.append("<tab id=" + item.getTpcd() + ">" + item.getTpnm() + "</tab>");
			}
		}
		builder.append("</row></tabbar>");
		return builder.toString();
	}

	public String listToJson(List<SysDataType> list)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		if (list != null)
		{
			for (int i = 0; i < list.size(); i++)
			{
				builder.append("{id:\"" + list.get(i).getDctcd() + "\",");
				builder.append("label:\"" + list.get(i).getTpnm() + "\",");
				if (i == 0)
				{
					builder.append("active: true,");
				}
				if (i == list.size() - 1)
				{
					builder.append("width:\"100px\"}");
				}
				else
				{
					builder.append("width:\"100px\"},");
				}
			}
		}
		builder.append("]");
		return builder.toString();
	}

	public SysDataType findByDctcd(String dctcd) {
		return this.dataTypeDao.findByDctcd(dctcd);
	}
	/**
	 * 获取类型的记录数
	 * @param type
	 * @param compId
	 * @return
	 */
	public int getTypeCount(int type,int compId){
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("type", type);
		map.put("compId", compId);
		return this.dataTypeDao.getTypeCount(map);
	}
	public List<Object> findByTypeAndCompID(int type, int compId, Pageable pageable)
	{
		int begin = pageable.getOffset() + 1;
		int end = pageable.getOffset() + pageable.getLimit();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("type", type);
		map.put("compId", compId);
		map.put("begin", String.valueOf(begin));
		map.put("end", String.valueOf(end));		
		return this.dataTypeDao.findByTypeAndCompID(map);
	}
	
	public List<SysDataType> findByTypeAndCompID(int type, int compId)
	{
		return this.dataTypeDao.findByTypeAndCompID(type, compId);
	}
	public String saveDataType(SysDataType item, int compId) 
	{
		String msg = "数据保存成功!";
		try 
		{
			if (item.getDctcd() != null && item.getDctcd() != "")
			{
				if(item.getTpid() == PisConstants.DataType.QRCodeSize.getKey())
				{
					SysDataType sysDataType = this.dataTypeDao.getById(item.getDctcd());
					if(sysDataType.getSts() == 1 && item.getSts() == 0)
					{
						int num = this.packageDao.getNumByQRSize(item.getDctcd());
						if(num > 0)
						{
							return "该二维码尺寸仍有可用状态的包装定义在使用，不能禁用！";
						}
					}
				}
				dataTypeDao.update(item);
			}
			else
			{
				String dictCd = "T" + StringUtil.padLeft(String.valueOf(item.getTpid()), 3, "0") + new SimpleDateFormat("HHmmss").format(Calendar.getInstance().getTime());
				item.setDctcd(dictCd);
				item.setCompany_id(compId);
				dataTypeDao.insert(item);
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			msg = "数据保存失败！";
		}
		return msg;
	}

	public List<Object> findByTypeNoPage(int type, int companyId)
	{
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("type", type);
		map.put("compId", companyId);
		
		return this.dataTypeDao.findByTypeNoPage(map);
	}
	
}
