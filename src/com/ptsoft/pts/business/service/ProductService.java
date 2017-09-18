package com.ptsoft.pts.business.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptsoft.common.Constants;
import com.ptsoft.common.base.BaseService;
import com.ptsoft.common.base.EntityDao;
import com.ptsoft.common.util.ExcelUtil;
import com.ptsoft.common.util.Pageable;
import com.ptsoft.common.util.SysConfig;
import com.ptsoft.pts.PisConstants;
import com.ptsoft.pts.PisUtils;
import com.ptsoft.pts.account.model.vo.SysUser;
import com.ptsoft.pts.business.dao.PackageRuleDao;
import com.ptsoft.pts.business.dao.PackageRuleMapDao;
import com.ptsoft.pts.business.dao.ProductDao;
import com.ptsoft.pts.business.model.vo.PackageRule;
import com.ptsoft.pts.business.model.vo.PackageRuleMap;
import com.ptsoft.pts.business.model.vo.Product;
import com.ptsoft.pts.system.dao.ProductLogDao;
import com.ptsoft.pts.system.model.vo.ProductLog;
import com.ptsoft.pts.system.model.vo.SysLog;
import com.ptsoft.pts.system.service.SysLogService;

@Service
public class ProductService extends BaseService<Product, Integer>
{

	@Autowired
	private ProductDao productDao;
	@Autowired
	private SysLogService logService;
	@Autowired
	private ProductLogDao productLogDao;
	@Autowired
	private PackageRuleDao ruleDao;
	@Autowired
	private PackageRuleMapDao ruleMapDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected EntityDao getEntityDao() 
	{
		return this.productDao;
	}

	@Override
	public void save(Product entity)
	{
		
	}

	public List<Product> findByCompIdAndLike(int compId, String searchParam, Pageable pageable) 
	{
		int begin = pageable.getOffset() + 1;
		int end = pageable.getOffset() + pageable.getLimit();
		return this.productDao.findByCompIdAndLike(compId, searchParam, begin, end);
	}

	public String saveProduct(Product product, SysUser user)
	{
		Product pro = this.productDao.getByCode(product.getCode());
		Product oldProduct = this.productDao.getById(product.getId());
		SysLog log = null;
		ProductLog productLog = null;
		
		String msg = "";
		String logInfo = "";
		String actionType = String.valueOf(PisConstants.LogActionType.UpdateProduct.getKey());
		int logType = PisConstants.LogType.Business.getKey();
		
		if(pro != null && pro.getId() != product.getId())
		{
			return "-1";
		}
		
		try
		{
			if (oldProduct != null)
			{
				//更新产品
				this.productDao.update(product);
				
				/**包装规则变化*/
				String oldPkgId = String.valueOf(oldProduct.getPackageID());
				String newPkgId = String.valueOf(product.getPackageID());
				
				PackageRule newRule = null;
				PackageRule oldRule = null;
				if(null != newPkgId && !"".equals(newPkgId))
				{
					newRule = this.ruleDao.getById(product.getPackageID());
				}
				if(null != oldPkgId && !"".equals(oldPkgId))
				{
					oldRule = this.ruleDao.getById(oldProduct.getPackageID());
				}
				
				/**修改前为空 修改后添加规则*/
				if(null == oldRule && null != newRule)
				{
					logInfo = ("包装规则未设置改为" + newRule.getName() + ";");
				}
				/**修改前不为空 修改为空*/
				if(null != oldRule && null == newRule)
				{
					logInfo = ("包装规则由" + oldRule.getName() + "改为无;");
				}
				/**修改前后不一样*/
				if(null != oldRule && null != newRule && oldRule.getId() != newRule.getId())
				{
					logInfo = ("包装规则由" + oldRule.getName() + "改为" + newRule.getName() + ";");
				}				
				
				
				/**产品价格修改*/
				String oldPrice = String.valueOf(oldProduct.getPrice());
				String newPrice = String.valueOf(product.getPrice());
				/**修改前为空 修改后添加价格*/
				if(null == oldPrice && null != newPrice)
				{
					logInfo += ("价格无改为" + newPrice + ";");
				}
				/**修改前不为空 修改为空*/
				if(null != oldPrice && null == newPrice)
				{
					logInfo += ("价格由" + oldPrice + "改为无;");
				}
				/**修改前后不一样*/
				if(null != oldPrice && null != newPrice && !newPrice.equals(oldPrice))
				{
					logInfo += ("价格由" + oldProduct.getPrice() + "改为" + product.getPrice() + ";");
				}
				
				productLog = new ProductLog(user.getUsrId(), product.getId(), logInfo);
				this.productLogDao.insert(productLog);
				
				log = new SysLog(user.getLgnNm(), user.getUsrId(), String.valueOf(product.getId()), product.getName() + logInfo, 1, actionType, logType);
				this.logService.insert(log);
			}
			else
			{
				//新增产品
				product.setCompany_id(user.getCompany_id());
				this.productDao.insert(product);
			}
			msg = String.valueOf(product.getId());
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			msg = "0";
		}
		return msg;
	}

	public List<Product> getByCompIdAndSts(int compId, int sts) 
	{
		return this.productDao.getByCompIdAndSts(compId, sts);
	}

	public String setPackage(String packageID, String products) 
	{
		String msg = "";
		try 
		{
			for(String proId : products.split(","))
			{
				if("".equals(proId))
				{
					continue;
				}
				this.productDao.updatePackage(proId, packageID);
			}
			msg = "1";
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage() + "---------------批量设置包装");
			msg = "0";
		}
		return msg;
	}

	public List<Product> findBySupplierId(int supplierId)
	{
		return this.productDao.findBySupplierId(supplierId);
	}

	public List<Product> findAll(int companyId, String searchParam) 
	{
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("companyId", String.valueOf(companyId));
		map.put("searchParam", searchParam);
		return this.productDao.findAll(map);
	}

	public HashMap<String,Object> getByInCode(String qrcode) 
	{
		return this.productDao.getByInCode(qrcode);
	}

	public int getSizeByParam(int companyId, String searchParam) 
	{
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("companyId", String.valueOf(companyId));
		map.put("searchParam", "%" + searchParam + "%");
		
		return this.productDao.getSizeByParam(map);
	}

	public List<Product> findIdAndCode(int companyId)
	{
		return this.productDao.findIdAndCode(companyId);
	}

	public List<ProductLog> getUpdateRecord(String productId)
	{
		return this.productLogDao.getUpdateRecord(Integer.parseInt(productId));
	}

	public int getCountByOrderId(String orderId)
	{
		return this.productDao.getCountByOrderId(orderId);
	}

	public List<Product> getListByOrderId(String orderId, Pageable pageable)
	{
		Map<String, String> map = new HashMap<String, String>();
		int begin = pageable.getOffset() + 1;
		int end = pageable.getOffset() + pageable.getLimit();
		
		map.put("orderId", orderId);
		map.put("begin", String.valueOf(begin));
		map.put("end", String.valueOf(end));
		
		return this.productDao.getListByOrderId(map);
	}

	public HashMap<String, Object> getPackageRule(String productId)
	{
		Product product = this.productDao.getById(Integer.parseInt(productId));
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(null != product)
		{
			PackageRule rule = this.ruleDao.getById(product.getPackageID());
			List<PackageRuleMap> ruleMap = this.ruleMapDao.getRuleMap(product.getPackageID());
			
			if(null != rule && (null != ruleMap && ruleMap.size() > 0))
			{
				map.put("packageRule", rule.getName());
				map.put("ruleMap", PisUtils.list2Option(ruleMap, "getPackage_id", "getPkgLevel", "1", false));
			}
		}
		else
		{
			map.put("packageRule", "");
			map.put("ruleMap", "");
		}
		return map;
	}

	public ArrayList<Object> exportByOrderId(String orderId) 
	{
		return this.productDao.exportByOrderId(orderId);
	}

	public ArrayList<Object> exportList(int companyId, String searchParam) 
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("searchParam", "%" + searchParam + "%");
		
		return this.productDao.exportList(map);
	}

	public String importData(String fileName, int companyId) 
	{
		File file = new File(SysConfig.getRootPath() + Constants.UPLOAD_XLS_PATH + File.separator + fileName);
		String[][] result = null;
		String msg = "";
		try 
		{
			result = ExcelUtil.getData(file, 3);
			if(null == result || "".equals(result))
			{
				return "";
			}
			
			int rowLength = result.length;
			Product product;
			PackageRule rule;
			for (int i = 0; i < rowLength; i++) 
			{
				product = new Product();
				rule = new PackageRule();
				for (int j = 0; j < result[i].length; j++) 
				{
					if(j == 0)
					{
						product.setCode(result[i][j]);
					}
					if(j == 1)
					{
						product.setName(result[i][j]);
					}
					if(j == 2)
					{
						product.setSapNo(result[i][j]);
					}
					if(j == 3)
					{
						if(null != result[i][j] && !"".equals(result[i][j]))
						{
							product.setPoints(Integer.parseInt(result[i][j]));
						}
					}
					if(j == 4)
					{
						if(null != result[i][j] && !"".equals(result[i][j]))
						{
							rule = this.ruleDao.getByCode(result[i][j]);
							product.setPackageID(rule.getId());
						}
					}
					if(j == 5)
					{
						if(null != result[i][j] && !"".equals(result[i][j]))
						{
							product.setPrice(Float.parseFloat(result[i][j]));
						}
					}
					if(j == 6)
					{
						product.setSpecNo(result[i][j]);
					}
					if(j == 7)
					{
						if(null != result[i][j] && !"".equals(result[i][j]))
						{
							product.setPrintQR(Integer.parseInt((result[i][j])));
						}
					}
				}
				
				product.setCompany_id(companyId);
				product.setSts(1);
				this.productDao.insert(product);
			}
			
			System.out.println("--产品批量导入成功--");
			msg = "1";
		} 
		catch (Exception e) 
		{
			System.out.println("--产品批量导入异常--" + e.toString() + "----");
			msg = "0";
		}
		return msg;
	}

}
