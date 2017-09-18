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
			<a href="#">数据报表</a>
			<span class="icon-angle-right"></span>
		</li>
		<li>
			<a href="#">用户积分报表</a>
		</li>
	</ul>
</div>
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
							用户类型：
						</label>
						<div class="controls">
							<select id="rlId" class="m-wrap">
								<option value="0">全部</option>
								<option value="5">普通经销商</option>
								<option value="7">普通用户</option>
							</select>
						</div>
					</div>
				</div>
			</div>
			<div class="row-fluid">
				<div class="span4">
					<div class="control-group">
						<label class="control-label">
							关键字：
						</label>
						<div class="controls">
							<input type="text" class="m-wrap" id="searchParam" placeholder="登录名、用户名或手机号" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="row-fluid">
	<div class="span12 portlet box blue">
		<div class="portlet-title">
			<div class="caption">
				<i class="icon-globe"></i><span class="titleExcel">用户积分报表</span>
			</div>
		</div>
		
		<div class="portlet-body" id="table-part">
			<table id='user-table' class='table table-striped table-bordered table-hover table-full-width'>
			</table>
		</div>
	</div>
</div>

<div id="pointDetail" class="modal hide fade" tabindex="-1" data-backdrop="static" data-keyboard="false">
	<div class="modal-header">
		<button data-dismiss="modal" class="close" type="button"></button>
		<h3 class="page-title" id="tileBiao">
		</h3>
	</div>
	<div class="modal-body">
		<table id="detail-table" class="table table-striped table-bordered table-hover table-full-width">
			<thead>
				<tr>
					<th data-formatter="indexFormatter">序号</th>
					<th data-field="SERIALNO">二维码序列号</th>
					<th data-field="PRODUCTNO">产品编码</th>
					<th data-field="PRODUCTNM">产品名称</th>
					<th data-field="POINT">积分</th>
					<th data-field="CREATETIME">操作时间</th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="modal-footer">
		<button type="button" class="btn blue doExportDetail">
			导出
		</button>
		<button type="button" data-dismiss="modal" class="btn green">
			确定
		</button>
	</div>
</div>

<script src="<c:url value="/media/fm/date.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/daterangepicker.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/bootstrap-datetimepicker.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/js/config.js"/>" type="text/javascript"></script>

<script type="text/javascript">

	var $table;
	var $detailTable = App.initTable($("#detail-table"));

	$().ready(function()
	{
		App.initDateRange($("#date-range"), 1);
		App.loadActions("EndUserPointReport", loadActionsBack);
		
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
		
		$(".doExportDetail").bind("click", function()
		{
			doExportDetail();
		});
		
		doSearch();
	});

	function doSearch() 
	{
		App.block();
		
		var searchParam = $("#searchParam").val();
		var rlId = $("#rlId").val();
		var dates = $("#date-range span").html().split('~');
		var fmtm = $.trim(dates[0]);
		var totm = $.trim(dates[1]);

		$table = $("#user-table").bootstrapTable("destroy").bootstrapTable({
			 url: "report/getScanUser.do?searchParam=" + searchParam + "&fmTm=" + fmtm + "&toTm=" + totm + "&rlId=" + rlId,
			 pagination: true,
			 sidePagination: "server",
			 clickToSelect: true,
			 singleSelect: true,
			 columns: [
				{
					title : "登录名",
					field : "LGNNM"
				}, 
				{
					title : "用户名",
					field : "USRNM"
				},
				{
					title : "手机号",
					field : "MOBILE"
				}, 
				{
					title : "用户类型",
					field : "RLNM"
				}, 
				{
					title : "地址",
					field : "ADDRESS"
				},
				{
					title : "本期积分",
					field : "CURPOINT"
				}, 
				{
					title : "总积分",
					field : "POINT"
				}, 
				{
					title : "积分明细",
					events: "operateEvents",
					formatter: function(value, row, index)
					{
						return '<a class="view" href="javascript:void(0)">查看</a>';
					}
				}
			]
		});
		
		App.unblock();
	}
	
	function doExport()
	{
		var searchParam = $("#searchParam").val();
		var rlId = $("#rlId").val();
		var dates = $("#date-range span").html().split('~');
		var fmtm = $.trim(dates[0]);
		var totm = $.trim(dates[1]);
		var title = "用户积分报表";
		App.executeExport("report/exportPoints.do", {searchParam: searchParam, fmTm: fmtm, toTm: totm, rlId: rlId, title: title});
	}
	
	function doExportDetail()
	{
		App.block();
		var title = $("#tileBiao").html();
		App.handleExport($detailTable, title);
		App.unblock();
	}
	
	
	window.operateEvents = 
	{
	    'click .view': function (e, value, row, index) 
	    {
	    
	    	var usrId = row.USRID;
	    	var dates = $("#date-range span").html().split('~');
			var fmtm = $.trim(dates[0]);
			var totm = $.trim(dates[1]);
			
	    	var url = "report/getPointDetails.do?usrId=" + usrId + "&fmTm=" + fmtm + "&toTm=" + totm;
	    	
			$("#tileBiao").html(row.LGNNM + "-积分明细");	    	
	    	$.get(url, function(data, status)
	    	{
	    		$detailTable.bootstrapTable("load", data);
	    	});
	    	
			$("#pointDetail").modal(
			{
				keyboard : true
			});
	    }
	};
	
	function indexFormatter(value, row, index)
	{
		return index + 1;
	}

</script>
