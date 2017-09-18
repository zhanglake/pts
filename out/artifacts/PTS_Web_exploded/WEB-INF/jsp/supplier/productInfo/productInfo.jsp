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
			<a href="#">生产信息</a>
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
							生产时间：
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
				<i class="icon-globe"></i><span class="titleExcel">生产信息列表</span>
			</div>
		</div>

		<div class="portlet-body" id="table-part">
			<table id='tbList' class='table table-striped table-bordered table-hover table-full-width'></table>
		</div>
	</div>
</div>

<script src="<c:url value="/media/fm/date.js"/>" type="text/javascript" ></script>
<script src="<c:url value="/media/fm/daterangepicker.js"/>"	type="text/javascript"></script>
<script src="<c:url value="/media/js/config.js"/>"	type="text/javascript"></script>

<script type="text/javascript">
	var $table;

	$().ready(function()
	{
		App.loadActions("ProductManager", loadActionsBack);
		//初始化日期选择
		App.initDateRange($("#date-range"), 1);

		function loadActionsBack(){
			$(".doSearch").bind("click", function()
			{
				doSearch();
			});
			
			$(".doExport").bind("click", function(){
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
		var dates = $("#date-range span").html().split('~');
		var fmtm = $.trim(dates[0]);
		var totm = $.trim(dates[1]);
		var searchParam = $("#searchParam").val();
				
		$table = $("#tbList").bootstrapTable("destroy").bootstrapTable({
			 url: "productInfo/productInfoList.do?searchParam=" + searchParam + "&fmtm=" + fmtm +"&totm=" + totm,
			 pagination: true,
			 sidePagination: "server",
			 columns: [	
				{
					title : "流水号",
					field : "serialNo"
				}, 
				{
					title : "SAP号",
					field : "sapNo"
				}, 
				{
					title : "物品编号",
					field : "product_code"
				}, 
				{
					title : "物品名称",
					field : "product_name"
				}, 
				{
					title : "包装",
					field : "packageTp"
				}, 
				{
					title : "装配线",
					field : "packageLine"
				}, 
				{
					title : "校验员",
					field : "validateUser"
				}, 
				{
					title : "生产时间",
					field : "produce_time"
				}, 
				{
					title : "包装时间",
					field : "package_time"
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
		
		App.executeExport("productInfo/exportExcel.do", {searchParam: searchParam, fmtm: fmtm, totm: totm, title: config.title_xls_produce_info});
	}
	
</script>
