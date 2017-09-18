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
			<a href="#">收货记录</a>
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
		<div class="portlet-body form">
			<input type="hidden" id="actionType" value="${actionType }" />
			<div class="row-fluid form-horizontal">
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
							模糊检索：
						</label>
						<div class="controls">
							<div class="input-append">
								<input type="text" class="m-wrap" width="300px" placeholder="流水号或外包装流水号关键字" id="searchItems" class="like-search" />
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
				<i class="icon-globe"></i><span class="titleExcel">收货记录</span>
			</div>
		</div>
		<div class="portlet-body" id="table-part">
			
		</div>
	</div>
</div>

<script src="<c:url value="/media/fm/date.js"/>" type="text/javascript" ></script>
<script src="<c:url value="/media/fm/daterangepicker.js"/>"	type="text/javascript"></script>

<script type="text/javascript">
	var $table;

	$().ready(function()
	{
		App.loadActions("RecieveQuery", loadActionsBack);
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
		var frmTm = $.trim(dates[0]);
		var toTm = $.trim(dates[1]);
		var actionType = $("#actionType").val();
		var searchParam = $("#searchItems").val();

		$table = $("#tbList").bootstrapTable({
			 url: "trace/receiveList.do?frmTm=" + frmTm + "&toTm=" + toTm + "&actionType=" + actionType + "&searchParam=" + searchParam,
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
					title : "物品编码",
					field : "PRODUCTCODE"
				}, 
				{
					title : "物品名称",
					field : "PRODUCTNAME"
				}, 
				{
					title : "数量",
					field : "CAPACITY"
				}, 
				{
					title : "外包装流水号",
					field : "PRNTSERIAL"
				},
				{
					title : "操作人",
					field : "OPERATOR"
				},
				{
					title : "操作时间",
					field : "OPERATE_TIME"
				}, 
				{
					title : "操作设备",
					field : "OPERATE_DEVICE"
				}, 
				{
					title : "操作类型",
					field : "OPERATE_TYPE"
				} 
			]
		});
		App.unblock();
	}
	
</script>
