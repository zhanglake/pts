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
			<a href="#">日志管理</a>
			<span class="icon-angle-right"></span>
		</li>
		<li>
			<a href="#">异常日志</a>
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
			<div class="tools">
				<a href="javascript:;" class="collapse"></a>
			</div>
			<div class="actions">
			
			</div>
		</div>
		<div class="portlet-body form form-horizontal">
			<div class="row-fluid">
				<div class="span4">
					<div class="control-group">
						<label class="control-label">
							操作时间：
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
							操作类型：
						</label>
						<div class="controls">
							<select id="actionType" class="m-wrap">
								${sActionType }
							</select>
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
				<i class="icon-globe"></i><span class="titleExcel">日志列表</span>
			</div>
		</div>

		<div class="portlet-body" id="table-part">
			<table id='tbList' class='table table-striped table-bordered table-hover table-full-width'>
			</table>
			
			<table id="xls-table" class="table table-striped table-bordered table-hover table-full-width" style="display: none;">
				<thead>
					<tr>
						<th data-field="operator">操作人</th>
						<th data-field="content">操作内容</th>
						<th data-field="create_time">操作时间</th>
						<th data-field="logActionType">操作类型</th>
						<th data-formatter="stsFormatter">操作状态</th>
					</tr>
				</thead>
			</table>
		</div>

	</div>
</div>

<script src="<c:url value="/media/fm/date.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/daterangepicker.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/js/config.js"/>" type="text/javascript"></script>

<script type="text/javascript">

	var $table;
	var $xlsTable = App.initTable($("#xls-table"));
	
	$().ready(function()
	{
		
		App.loadActions("ExceptionLog", loadActionsBack);
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
	});
	
	function doSearch()
	{
		App.block();
		
		/*$("#table-part").empty();
		var table = "<table id='tbList' class='table table-striped table-bordered table-hover table-full-width'></table>";
		$("#table-part").html(table);*/
		
		var dates = $("#date-range span").html().split('~');
		var fmtm = $.trim(dates[0]);
		var totm = $.trim(dates[1]);
		var actionType = $("#actionType").val();
		
		$table = $("#tbList").bootstrapTable("destroy").bootstrapTable({
			 url: "sysLog/exceptionList.do?fmtm=" + fmtm +"&totm=" + totm + "&actionType=" + actionType,
			 pagination: true,
			 sidePagination: "server",
			 clickToSelect: true,
			 singleSelect: true ,
			 columns: [	
				{
					title : "操作人",
					field : "operator"
				}, 
				{
					title : "操作内容",
					field : "content"
				}, 
				{
					title : "操作时间",
					field : "create_time"
				}, 
				{
					title : "操作类型",
					field : "logActionType"
				}, 
				{
					title : "操作状态",
					formatter : function(value, row, index)
					{
						if(row.status == 0)
						{
							return '失败';
						}
						else
						{
							return '成功';
						}
					}
				}
			]
		});
		App.unblock();
	}
	
	function doExport()
	{
		App.block();
		var dates = $("#date-range span").html().split('~');
		var fmtm = $.trim(dates[0]);
		var totm = $.trim(dates[1]);
		var actionType = $("#actionType").val();
		
		$.get("sysLog/listXls.do?fmtm=" + fmtm +"&totm=" + totm + "&actionType=" + actionType + "&logType=2", function(data, status)
		{
			$xlsTable.bootstrapTable("load", data);
			
			App.handleExport($("#xls-table"), config.title_xls_exception_log_platform);
			
			App.unblock();
		});
	}
	
	function stsFormatter(value, row, index)
	{
		if(row.status == 0)
		{
			return '失败';
		}
		else
		{
			return '成功';
		}
	}
</script>