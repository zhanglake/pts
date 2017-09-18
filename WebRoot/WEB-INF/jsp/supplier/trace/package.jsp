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
			<a href="#">包装记录查询</a>
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
								<input type="text" placeholder="物品编号或名称" class="m-wrap" id="searchParam" />
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
			
			<table id="xls-table" class="table table-striped table-bordered table-hover table-full-width" style="display: none;">
				<thead>
					<tr>
						<th data-field="SERIALNO">流水号</th>
						<th data-field="PCODE">产品编码</th>
						<th data-field="PNAME">产品名称</th>
						<th data-field="PACKAGENUM">数量</th>
						<th data-field="PACKAGELEVEL">包装层级</th>
						<th data-field="PACKAGENAME">包装定义</th>
						<th data-field="PRNTSERIAL">外包装流水号</th>
						<th data-field="OPERATOR">包装人</th>
						<th data-field="CREATETIME">包装时间</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</div>

<script src="<c:url value="/media/fm/date.js"/>" type="text/javascript" ></script>
<script src="<c:url value="/media/fm/daterangepicker.js"/>"	type="text/javascript"></script>
<script src="<c:url value="/media/js/config.js"/>"	type="text/javascript"></script>

<script type="text/javascript">

	var $table;
	var $xlsTable = App.initTable($("#xls-table"));

	$().ready(function()
	{
		App.loadActions("PackageQuery", loadActionsBack);
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
			if (e.which == 13) {//回车
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
			 url: "trace/packageList.do?searchParam=" + $("#searchParam").val() + "&fmtm=" + fmtm +"&totm=" + totm,
			 pagination: true,
			 sidePagination: "server",
			 clickToSelect: true,
			 singleSelect: true ,
			 columns: [
				{
					title : "序号",
					formatter : function(value, row, index) 
					{
						return index + 1;
					}
				},
				{
					title : "流水号",
					field : "SERIALNO"
				},
				{
					title : "物品编号",
					field : "PCODE"
				}, 
				{
					title : "物品名称",
					field : "PNAME"
				}, 
				{
					title : "数量",
					field : "PACKAGENUM"
				}, 
				{
					title : "包装层级",
					field : "PACKAGELEVEL"
				}, 
				{
					title : "包装定义",
					field : "PACKAGENAME"
				}, 
				{
					title : "外包装流水号",
					field : "PRNTSERIAL"
				},
				{
					title : "包装人",
					field : "OPERATOR"
				}, 
				{
					title : "包装时间",
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
		
		App.block();
				
		$.get("trace/packageListXls.do", {frmTm: fmtm, toTm: totm, searchParam: searchParam}, function(data, status)
		{
			
			$xlsTable.bootstrapTable("load", data);
			
			App.handleExport($("#xls-table"), config.title_xls_package_record);
			
			App.unblock();
		});
	}
	
</script>
