package com.ptsoft.pts.basic.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptsoft.common.base.BaseService;
import com.ptsoft.common.base.EntityDao;
import com.ptsoft.pts.basic.dao.AppVersionDao;
import com.ptsoft.pts.basic.model.vo.AppVersion;

@Service
public class AppVersionService extends BaseService<AppVersion, Integer>
{
	
	@Autowired
	private AppVersionDao appVersionDao;

	@SuppressWarnings("rawtypes")
	@Override
	protected EntityDao getEntityDao()
	{
		return this.appVersionDao;
	}

	@Override
	public void save(AppVersion entity) 
	{
		
	}

	public HashMap<String, Object> checkUpdate(String company, String version, String os)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("company", company);
		param.put("os", os);
		
		AppVersion appVersion = this.appVersionDao.checkUpdate(param);
		if(null != appVersion)
		{
			int isUpdate = version.compareTo(appVersion.getVersion());
			
			if(isUpdate == 0)
			{
				map.put("code", 1);
				map.put("src", "");
				map.put("msg", "当前版本已是最新版本.");
				map.put("msg_en", "The latest version.");
				map.put("update", 1);
			}
			else if (isUpdate == -1)
			{
				map.put("code", 1);
				map.put("src", appVersion.getUrl());
				map.put("msg", "当前版本不是最新版本.");
				map.put("msg_en", "It isn't the latest version.");
				map.put("update", 0);
			}
			/*else if (isUpdate == 1)
			{
				map.put("code", 1);
				map.put("src", "");
				map.put("msg", "当前版本已是最新版本.");
				map.put("msg_en", "The latest version.");
				map.put("update", 0);
			}*/
		}
		else
		{
			map.put("code", 1);
			map.put("src", "");
			map.put("msg", "当前无版本信息");
			map.put("msg_en", "There's no any info about version.");
			map.put("update", 2);
		}
		return map;
	}

}
