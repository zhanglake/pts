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
			<a href="#">基础数据</a>
			<span class="icon-angle-right"></span>
		</li>
		<li>
			<a href="#">PDA终端管理</a>
		</li>
	</ul>
</div>

<!-- END PAGE HEADER-->
<!-- 列表开始 -->
<div class="row-fluid">
	<div class="span12 portlet box blue">
		<div class="portlet-title">
			<div class="caption">
				<i class="icon-globe"></i><span class="titleExcel">PDA终端列表</span>
			</div>
			<div class="actions">
				<!--<a class="btn red doExport">
					导出
					<i class="m-icon-swapright m-icon-white"></i>
				</a>
			--></div>
		</div>

		<div class="portlet-body">
			<input type="hidden" name="company_id" id="company_id" value="${companyId}" />
			<div id="table-part">
			
			</div>
		</div>

		

<script src="<c:url value="/media/fm/daterangepicker.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/bootstrap-datetimepicker.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.uniform.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.validate.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.validate.messages_cn.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/md5.js"/>" type="text/javascript"></script>

<script type="text/javascript">
	var $table = App.initTable($("#tbList"));

	$().ready(function()
	{
		App.loadActions("PDADeviceMgr", loadActionsBack);

		function loadActionsBack(){
			$(".doSearch").bind("click", function()
			{
				doSearch();
			});
			
			$(".doAddNew").bind("click", function()
			{
				doAddNew();
			});
	
			$(".doEdit").bind("click", function()
			{
				doEdit();
			});
			
		}
		
		doSearch();

	});

	function doSearch()
	{
		App.block();
		$("#table-part").empty();
		var table = "<table id='tbList' class='table table-striped table-bordered table-hover table-full-width'></table>";
		$("#table-part").html(table);
		$table = $("#tbList").bootstrapTable({
			 url: "device/pdaList.do",
			 pagination: true,
			 sidePagination: "server",
			 clickToSelect: true,
			 singleSelect: true ,
			 columns: [	
				{
					checkbox: true
				},
				{
					title : "id",
					field : "id",
					visible : false
					
				},
				{
					title : "终端编号",
					field : "deviceNo"
				},
				{
					title : "库位名称",
					formatter : function (value, row, index)
					{
						return '<span>' + row.stock.name + '</span>';
					}
				}, 
				{
					title : "领用人",
					field : "register"
				}, 
				{
					title : "领用日期",
					field : "regDate"
				}, 
				{
					title : "IP地址",
					field : "ip"
				}, 
				{
					title : "状态",
					field : "stsName"
				}, 
				{
					title : "备注",
					field : "remark"
				}/*, 
				{
					title : "监控",
					field : "ctrl"
				}*/
			]
		});
		App.unblock();
	}
	
	function doAddNew()
	{
		App.loadPage("device/pdaDeviceEditPage.do?id=");
	}

	function doEdit()
	{
		var id = App.getTableSelection($table).id;
		if (id == undefined)
		{
			App.alert("请选择一条记录！");
			return;
		}
		App.loadPage("device/pdaDeviceEditPage.do?id=" + id);
	}
	
	

</script>
