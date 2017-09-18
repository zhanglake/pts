<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/media/css/daterangepicker.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/media/css/datepicker.css"/>" />
<div class="row-fluid">
	<h3 class="page-title">
		
	</h3>

	<ul class="breadcrumb">
		<li>
			<i class="icon-home"></i>
			<a href="index.do">我的工作室</a>
			<span class="icon-angle-right"></span>
		</li>
		<li>
			<a href="#">订货查询</a>
		</li>
</div>

<!-- END PAGE HEADER-->
<div class="row-fluid">
	<div class="span12 portlet box yellow">
		<div class="portlet-title">
			<div class="caption">
				<i class="icon-reorder"></i>查询条件
			</div>
			<div class="tools">
				<a href="javascript:;" class="collapse"></a>
			</div>
			<div class="actions">
			</div>
		</div>
		<div class="portlet-body form">
			<div class="form-horizontal">
				<div class="row-fluid">
					<div class="span4">
						<div class="control-group input-append">
							<label class="control-label">
								时间范围：
							</label>
							<div class="controls">
								<div id="date-range" class="btn">
									<i class="icon-calendar"></i>
									&nbsp;<span></span> 
									<b class="caret"></b>
								</div>
							</div>
						</div>
					</div>
					<div class="span4">
						<div class="control-group">
							<label class="control-label">
								模糊检索：
							</label>
							<div class="controls">
								<div class="input-append">
									<input type="text" class="m-wrap" width="300px" placeholder="订单编号或联系人或电话关键字" id="searchItems" class="like-search" />
									<span class="add-on doSearch">
										<i class="icon-search"></i>
									</span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 列表开始 -->
<div class="row-fluid">
	<div class="span12 portlet box blue">
		<div class="portlet-title">
			<div class="caption">
				<i class="icon-globe"></i><span class="titleExcel">订货单列表</span>
			</div>
		</div>
		<div class="portlet-body" id="table-part">
			
		</div>
	</div>
</div>

<div id="orderModel" class="modal hide fade" data-backdrop="static">
	<div class="modal-header">
		<button data-dismiss="modal" class="close" type="button"></button>
		<h3 class="page-title" id="modal-header"></h3>
	</div>
	<div class="modal-body">
		<table id="detailOrder" class="table table-bordered table-hover">
			<thead>
				<tr>
					<th data-field="productNo">物品编码</th>
					<th data-field="productName">物品名称</th>
					<th data-field="count">数量</th>
					<th data-field="unit">单位</th>
					<th data-field="box">箱数</th>
					<th data-field="location">货位</th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="modal-footer">
		<button type="button" data-dismiss="modal" class="btn green">
			确定
		</button>
	</div>
</div>

<script src="<c:url value="/media/fm/date.js"/>" type="text/javascript" ></script>
<script src="<c:url value="/media/fm/daterangepicker.js"/>"	type="text/javascript"></script>
<script src="<c:url value="/media/fm/bootstrap-datepicker.js"/>"	type="text/javascript"></script>
<script type="text/javascript">

	var $table;
	$().ready(function()
	{
		
		$(".form_datetime").datepicker();
		App.loadActions("OrderRecord", loadActionsBack);
		App.initDateRange($("#date-range"), 1);
		
		function loadActionsBack(){
			$(".doSearch").bind("click", function()
			{
				doSearch();
			});
		}
		
		//回车查询
		$(".input-append input").keypress(function (e) 
		{
			if (e.which == 13) 
			{
				doSearch();
			}
		});
		
		doSearch();
	});
	
	function doSearch()
	{
		App.block();
		$("#table-part").empty();
		var table = "<table id='tbList' class='table table-striped table-bordered table-hover table-full-width'></table>";
		$("#table-part").html(table);
		
		var dates = $("#date-range span").html().split('~');
		var fmtm = $.trim(dates[0]);
		var totm = $.trim(dates[1]);
		
		$table = $("#tbList").bootstrapTable({
			 url: "order/orderList.do?fmtm=" + fmtm + "&totm=" + totm + "&searchParam=" + $("#searchItems").val(),
			 pagination: true,
			 sidePagination: "server",
			 clickToSelect: true,
			 singleSelect: true ,
			 columns: [
                  {
                      title: "订单编号",
                      field: "orderNo"
                  },
                  {
                      title: "购货单位名称",
                      field: "unitName"
                  },
                  {
                      title: "收货地址",
                      field: "shippingAddress"
                  },
                  {
                      title: "联系人",
                      field: "contact"
                  },
                  {
                      title: "电话",
                      field: "tel"
                  },
                  {
                      title: "发送方式",
                      field: "deliveryWay"
                  },
                  {
                      title: "订货日期",
                      field: "orderDate"
                  },
                  {
                      title: "同步日期",
                      field: "syncDate"
                  },
                  {
                      title: "备注",
                      formatter: function (value, row, index)
                      {
                      	return '<span style="color: red;">' + row.remark + '</span>';
                      }
                  }, 
                  {
                      title: "状态",
                      formatter: function (value, row, index)
                      {
                      	 if(row.status == 1)
                      	 {
                      	 	return '已出库';
                      	 }
                      	 else
                      	 {
                      	 	return '未出库';
                      	 }
                      }
                  },
				  {
					  title : "详情",
					  events: "operateEvents",
					  formatter : function(value, row, index) 
					  {
						 return '<a class="view" href="javascript:void(0)">查看</a>';
					  }
				  }
             ]
		});
		
		App.unblock();
	}
	
	window.operateEvents = 
	{
	    'click .view': function (e, value, row, index) 
	    {
			var orderNo = row.orderNo;
			var orderId = row.id;
			var $detailOrder = App.initTable($("#detailOrder"));
			$("#orderModel>.modal-header>.page-title").text(orderNo + "销售订单明细");
			
			$.get("order/orderDetails.do?orderId=" + orderId, function (data, status)
			{
				$detailOrder.bootstrapTable("load", data);
			});
			
			$("#orderModel").modal({keyboard: true});
	    }
	};
	
</script>
