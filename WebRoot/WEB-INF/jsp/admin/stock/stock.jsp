<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
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
			<a href="#">库位管理</a>
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
			<div class="control-group">
				<label class="control-label">
					模糊检索：
				</label>
				<div class="controls">
					<div class="input-append">
						<input type="text" placeholder="编码或名称关键字" class="m-wrap" id="searchParam" />
						<span class="add-on doSearch">
							<i class="icon-search"></i>
						</span>
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
				<i class="icon-globe"></i><span class="titleExcel">库位列表</span>
			</div>
		</div>

		<div class="portlet-body" id="table-part">
			
		</div>

	</div>
</div>

<script type="text/javascript">

	var $table = App.initTable($("#tbList"));
	
	$().ready(function()
	{
		doSearch();
		App.loadActions("StockMgr", loadActionsBack);

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
		$(".input-append input").keypress(function (e) {
			if (e.which == 13) {
				doSearch();
			}
		});	
	});
	
	function doSearch()
	{
		App.block();
		$("#table-part").empty();
		var table = "<table id='tbList' class='table table-striped table-bordered table-hover table-full-width'></table>";
		$("#table-part").html(table);
		$table = $("#tbList").bootstrapTable({
			 url: "stock/stockList.do?searchParam=" + $("#searchParam").val(),
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
					title : "库位编码",
					field : "code"
				}, 
				{
					title : "库位名称",
					field : "name"
				}, 
				{
					title : "地址",
					formatter : function(value, row, index)
					{
						if(row.areaID == 0)
							return '—';
						else
							return '<span>' + row.area.fullName + row.address + '</span>';
					}
				}, 
				{
					title : "状态",
					field : "stsName"
				}
			]
		});
		App.unblock();
	}
	
	function doAddNew()
	{
		App.loadPage("stock/stockEditPage.do?id=");
	}

	function doEdit()
	{
		var id = App.getTableSelection($table).id;
		if (id == undefined)
		{
			App.alert("请选择一条记录！");
			return;
		}
		App.loadPage("stock/stockEditPage.do?id=" + id);
	}
</script>
