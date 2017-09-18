package com.ptsoft.controller.admin;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ptsoft.common.util.Page;
import com.ptsoft.common.util.Pageable;
import com.ptsoft.common.util.ResponseUtils;
import com.ptsoft.pts.PisConstants;
import com.ptsoft.pts.PisUtils;
import com.ptsoft.pts.account.model.vo.SysUser;
import com.ptsoft.pts.basic.model.vo.Stock;
import com.ptsoft.pts.basic.service.StockService;

@Controller("AdminStockController")
@RequestMapping("/admin/stock/*")
public class StockController {
	@Autowired
	private StockService stockService;

	/**
	 * 
	 * @author zumin.yang
	 * @date 2016-02-23
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/stockPage.do")
	public String stockPage(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		
		return "admin/stock/stock";
	}
	
	/**
	 * 
	 * @author zumin.yang
	 * @date 2016-02-23
	 * @param request
	 * @param response
	 * @param searchParam
	 * @param model
	 */
	@RequestMapping("/stockList.do")
	public void stockList(HttpServletRequest request, HttpServletResponse response, String searchParam,Pageable pageable)
	{
		SysUser user = PisUtils.getCurrentUser(request);
		int count = this.stockService.getstockCount(String.valueOf(user.getCompany_id()), searchParam);
		List<Stock> list = this.stockService.findByCompIdAndSearch(String.valueOf(user.getCompany_id()), searchParam, pageable);
		ResponseUtils.renderJson(response, new Page<Stock>(list, count));
	}
	
	/**
	 * 
	 * @author zumin.yang
	 * @date 2016-02-23
	 * @param response
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/stockEditPage.do")
	public String stockEditPage(HttpServletResponse response, Model model, String id)
	{
		List<PisConstants.Available> availables = Arrays.asList(PisConstants.Available.values());
		
		model.addAttribute("id", id);
		model.addAttribute("sSts",  PisUtils.list2Option(availables, "getKey", "getText", null, false));
		
		return "admin/stock/stockEditor";
	}
	
	/**
	 * 
	 * @author zumin.yang
	 * @date 2016-02-23
	 * @param response
	 * @param model
	 * @param id
	 */
	@RequestMapping("/stockEdit.do")
	public void stockEdit(HttpServletResponse response, Model model, String id)
	{
		Stock stock = null;
		if (!id.equals(""))
		{
			stock = this.stockService.getById(Integer.parseInt(id));
		}
		if (stock == null)
		{
			stock = new Stock();
		}
		
		ResponseUtils.renderJson(response, stock);
	}
	
	/**
	 * 
	 * @author zumin.yang
	 * @date 2016-02-23
	 * @param request
	 * @param response
	 * @param stock
	 */
	@RequestMapping("/stockSave.do")
	public void stockSave(HttpServletRequest request,HttpServletResponse response, Stock stock)
	{
		Integer companyId = PisUtils.getCurrentUser(request).getCompany_id();
		stock.setCompanyId(companyId);
		String msg = this.stockService.saveStock(stock);
		ResponseUtils.renderText(response, msg);
	}
}
