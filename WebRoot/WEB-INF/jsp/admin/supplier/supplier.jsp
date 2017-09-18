<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!-- BEGIN PAGE HEADER-->
<div class="row-fluid">
	<h3 class="page-title">
		<!--
			生产供应商管理
			<small>生产供应商查询</small>
		-->
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
			<a href="#">生产供应商管理</a>
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
				<i class="icon-globe"></i><span class="titleExcel">生产供应商列表</span>
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
		App.loadActions("SupplierManager", loadActionsBack);

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
			 url: "supplier/supplierList.do?searchParam=" + $("#searchParam").val(),
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
					title : "供应商编码",
					field : "supplier_code"
				},
				{
					title : "供应商名称",
					field : "supplier_name"
				}, 
				{
					title : "企业内部编码",
					field : "innerCode"
				}, 
				{
					title : "是否有系统支撑",
					field : "sysName"
				}, 
				{
					title : "联系人",
					field : "contact"
				}, 
				{
					title : "联系电话",
					field : "phone"
				}, 
				{
					title : "状态",
					field : "stsName"
				}, 
				{
					title : "地址",
					field : "address"
				}
			]
		});
		App.unblock();
	}
	
	function doAddNew()
	{
		App.loadPage("supplier/supplierEditPage.do?id=");
	}
	
	function doEdit() 
	{
		
		var id = App.getTableSelection($table).id;
		if (id == undefined)
		{
			App.alert("请选择一条记录！");
			return;
		}
		App.loadPage("supplier/supplierEditPage.do?id=" + id);
		
	}
	
</script>
