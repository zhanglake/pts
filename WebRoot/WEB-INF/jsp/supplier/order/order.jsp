<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<link rel="stylesheet" type="text/css" href="<c:url value="/media/css/daterangepicker.css"/>" />

<!-- BEGIN PAGE HEADER-->
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
			<a href="#">订单查询</a>
		</li>
	</ul>
</div>

<!-- END PAGE HEADER-->

<div class="row-fluid">
	<div class="span12 portlet box yellow">
		<div class="portlet-title">
			<div class="caption">
				<i class="icon-reorder"></i>查询条件
			</div>
		
			<div class="actions">
				
			</div>
		</div>
		<div class="portlet-body form form-horizontal">
			<div class="row-fluid">
				<div class="span4">
					<div class="control-group input-append">
						<label class="control-label">
							日期范围：
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
								<input type="text" placeholder="SAP号、编号、名称" class="m-wrap" id="searchParam" />
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

<!-- 列表开始 -->
<div class="row-fluid">
	<div class="span12 portlet box blue">
		<div class="portlet-title">
			<div class="caption">
				<i class="icon-globe"></i><span class="titleExcel">订单明细</span>
			</div>
		</div>

		<div class="portlet-body" id="table-part">
			<table id='tbList' class='table table-striped table-bordered table-hover table-full-width'>
			</table>
		</div>
	</div>
</div>

<script src="<c:url value="/media/fm/date.js"/>" type="text/javascript" ></script>
<script src="<c:url value="/media/fm/daterangepicker.js"/>"	type="text/javascript"></script>
<script src="<c:url value="/media/js/config.js"/>"	type="text/javascript"></script>

<script type="text/javascript">
	var $table = App.initTable($("#tbList"));

	$().ready(function()
	{
		App.loadActions("OrderQuery", loadActionsBack);
		//初始化日期选择
		App.initDateRange($("#date-range"), 1);

		function loadActionsBack(){
			$(".doSearch").bind("click", function()
			{
				doSearch();
			});
			
			$(".doExport").bind("click", function()
			{
				doExport();
			});
		}
		doSearch();
		
		$(".input-append input").keypress(function (e) {
			if (e.which == 13) {
				doSearch();
			}
		});
	});

	function doSearch()
	{
		App.block();
		
		/*var table = "<table id='tbList' class='table table-striped table-bordered table-hover table-full-width'></table>";
		$("#table-part").empty();
		$("#table-part").html(table);*/
		
		var dates = $("#date-range span").html().split('~');
		var fmtm = $.trim(dates[0]);
		var totm = $.trim(dates[1]);
		
		$table = $("#tbList").bootstrapTable("destroy").bootstrapTable({
			 url: "order/orderList.do?searchParam=" + $("#searchParam").val() + "&fmtm=" + fmtm +"&totm=" + totm,
			 pagination: true,
			 sidePagination: "server",
			 columns: [	
				{
					title : "序号",
					formatter : function(value, row, index) 
					{
						return index + 1;
					}
				},
				{
					title : "id",
					field : "id",
					visible : false
					
				},
				{
					title : "SAP号",
					field : "SAPNO"
				}, 
				{
					title : "物品编号",
					field : "PRODUCTCODE"
				}, 
				{
					title : "物品名称",
					field : "PRODUCTNAME"
				}, 
				{
					title : "包装规则",
					field : "PACKAGENAME"
				}, 
				{
					title : "数量",
					field : "PRODUCTCOUNT"
				}, 
				{
					title : "订单编号",
					field : "ORDERNO"
				}, 
				{
					title : "订单创建时间",
					field : "CREATETIME"
				}
			]
		});
		App.unblock();
	}
	
	function doExport()
	{
		var dates = $("#date-range span").html().split('~');
		var fmtm = $.trim(dates[0]);
		var totm = $.trim(dates[1]);
		var searchParam = $("#searchParam").val();
		
		App.executeExport("order/orderExport.do",{searchParam: searchParam, fmtm: fmtm, totm: totm, title: config.title_xls_order_supplier});
	}
	
</script>
