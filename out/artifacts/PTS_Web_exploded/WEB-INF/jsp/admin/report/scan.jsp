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
			<a href="#">扫码报表</a>
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
					<div class="control-group input-append">
						<label class="control-label"> 时间范围： </label>
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
						<label class="control-label"> 模糊检索： </label>
						<div class="controls">
							<input type="text" placeholder="登录名/用户名关键字" class="m-wrap" id="searchParam" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="row-fluid tabbable">
	<div class="span12 tabbable tabbable-custom">
		<ul class="nav nav-tabs">
			<li class="active"><a href="#portlet_tab1" data-toggle="tab" id="1">最后一级</a></li>
			<li><a href="#portlet_tab2" data-toggle="tab" id="2">倒数第二级</a></li>
			<li><a href="#portlet_tab3" data-toggle="tab" id="3">第二级</a></li>
		</ul>
		<div class="tab-content">
			<div class="tab-pane active" id="portlet_tab1">
				<div class="row-fluid">
					<div class="span12 portlet box blue">
						<div class="portlet-title">
							<div class="caption">
								<i class="icon-globe"></i><span class="titleExcel">用户列表</span>
							</div>
						</div>
				
						<div class="portlet-body">
							<table id="lastTable" class="table table-striped table-bordered table-hover table-full-width">
							</table>
						</div>
					</div>
				</div>
			</div>
			<div class="tab-pane" id="portlet_tab2">
				<div class="row-fluid">
					<div class="span12 portlet box blue">
						<div class="portlet-title">
							<div class="caption">
								<i class="icon-globe"></i><span class="titleExcel">用户列表</span>
							</div>
						</div>
				
						<div class="portlet-body">
							<table id="sndLast" class="table table-striped table-bordered table-hover table-full-width">
							</table>
						</div>
				
					</div>
				</div>
			</div>
			
			<div class="tab-pane" id="portlet_tab3">
				<div class="row-fluid">
					<div class="span12 portlet box blue">
						<div class="portlet-title">
							<div class="caption">
								<i class="icon-globe"></i><span class="titleExcel">用户列表</span>
							</div>
						</div>
				
						<div class="portlet-body">
							<table id="second" class="table table-striped table-bordered table-hover table-full-width">
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="userModel" class="modal hide fade" data-backdrop="static">
	<div class="modal-header">
		<button data-dismiss="modal" class="close" type="button"></button>
		<h3 class="page-title" id="modal-header">扫码关联用户</h3>
	</div>
	<div class="modal-body">
		<div id="relation">
			<IFRAME id="relationFrame" frameborder="0" scrolling="no" style="width:100%;height:100%;"></IFRAME>
		</div>
	</div>
	<div class="modal-footer">
		<input type="hidden" id="list" />
		<input type="hidden" id="lgnNm" />
		<button type="button" data-dismiss="modal" class="btn">
			确定
		</button>
	</div>
</div>

<script src="<c:url value="/media/fm/date.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/daterangepicker.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/bootstrap-datetimepicker.js"/>" type="text/javascript"></script>

<script type="text/javascript">

	var $table;
	var $detailTable = App.initTable($("#detail-table"));
	
	$().ready(function()
	{
		App.initDateRange($("#date-range"), 1);
		App.loadActions("ScanReport", loadActionsBack);
		
		function loadActionsBack(){
			
			$(".doSearch").bind("click", function()
			{
				doSearch($("ul.nav-tabs li.active a").attr("id"));
			});
			$(".doExport").bind("click", function()
			{
				doExport($("ul.nav-tabs li.active a").attr("id"));
			});
		}
		
		$(".nav-tabs li a").click(function()
		{
			doSearch($(this).attr("id"));
		});
		doSearch(1);
	});
	
	function doSearch(num)
	{
		App.block();
		
		var dates = $("#date-range span").html().split('~');
		var searchParam = $("#searchParam").val();
		var fmtm = $.trim(dates[0]);
		var totm = $.trim(dates[1]);
		var url = "report/userRecord.do?searchParam=" + searchParam + "&fmTm=" + fmtm + "&toTm=" + totm + "&type=" + num;
		if(num == 1)
		{
			$table = $("#lastTable");
		}
		else if(num == 2)
		{
			$table = $("#sndLast");
		}
		else if(num == 3)
		{
			$table = $("#second");
		}
		
		$table.bootstrapTable("destroy").bootstrapTable({
			 url: url,
			 pagination: true,
			 sidePagination: "server",
			 clickToSelect: true,
			 singleSelect: true ,
			 columns: [
				{
					title : "登录名",
					field : "LGNNM"
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
					title : "积分",
					field : "POINT"
				},
				{
					title : "关联用户",
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
	
	function doExport(num)
	{
		var dates = $("#date-range span").html().split('~');
		var searchParam = $("#searchParam").val();
		var fmtm = $.trim(dates[0]);
		var totm = $.trim(dates[1]);
		var title = "扫码报表";
		App.executeExport("report/exportUsers.do", {searchParam: searchParam, fmTm: fmtm, toTm: totm, type: num, title: title});
	}
	
	window.operateEvents = 
	{
		'click .view': function (e, value, row, index)
		{
		   	var usrId = row.USRID;
			$.get("report/getRelation.do?usrId=" + usrId, function(data, status)
			{
				
				$("#lgnNm").val(row.LGNNM);
				$("#list").val(JSON.stringify(data));
				$("#relation").height(600);
				$("#relation").width(600);
	    		$("#relationFrame").attr("src", "../media/kforce/relation.html");
			});
		   	
		    $("#userModel").modal({keyboard: true});
		}	    
	};
	
</script>
