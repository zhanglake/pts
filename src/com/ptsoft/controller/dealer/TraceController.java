package com.ptsoft.controller.dealer;

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
import com.ptsoft.pts.basic.service.TraceService;

@Controller("DealerTraceController")
@RequestMapping("/dealer/trace/*")
public class TraceController 
{
	@Autowired
	private TraceService traceService;

	/**
	 * 收货页面
	 * @author jqi.can
	 * @date 2016-3-31下午02:33:34
	 */
	@RequestMapping("/receiveQuery.do")
	public String receiveQuery(Model model)
	{
		model.addAttribute("actionType", PisConstants.ActionType.DealerIn.getKey());
		return "dealer/trace/receive";
	}
	
	/**
	 * 收货记录
	 * @author jqi.can
	 * @date 2016-3-31下午02:40:19
	 */
	@RequestMapping("/receiveList.do")
	public void receiveList(HttpServletRequest request, HttpServletResponse response, Pageable pageable, String frmTm, String toTm, String actionType, String searchParam)
	{
		int dealerId = PisUtils.getCurrentUser(request).getDealer_id();
		int count = this.traceService.findRecordCount(dealerId, 0, frmTm, toTm, actionType, searchParam);
		List<Object> list = this.traceService.findRecordByAction(dealerId, 0, frmTm, toTm, actionType, searchParam, pageable);
		ResponseUtils.renderJson(response, new Page<Object>(list, count));
	}
	
}
