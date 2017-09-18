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
			<a href="#">包装管理</a>
			<span class="icon-angle-right"></span>
		</li>
		<li>
			<a href="#">包装定义</a>
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
			<div class="control-group">
				<label class="control-label">
					模糊检索：
				</label>
				<div class="controls">
					<div class="input-append">
						<input type="text" placeholder="编号或名称关键字" class="m-wrap" id="searchParam" />
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
				<i class="icon-globe"></i><span class="titleExcel">包装定义列表</span>
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
		App.loadActions("SinglePackage", loadActionsBack);

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
			 url: "package/packageList.do?searchParam=" + $("#searchParam").val(),
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
					title : "包装编号(新)",
					field : "code"
				},
				{
					title : "包装编号(旧)",
					field : "code_old"
				}, 
				{
					title : "包装名称",
					field : "name"
				}, 
				{
					title : "包装类型",
					field : "pkgTypeNm"
				}, 
				{
					title : "包装规格",
					field : "specifications"
				}, 
				{
					title : "包装容量(个)",
					field : "capacity"
				}, 
				{
					title : "二维码尺寸",
					field : "qrarer_size"
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
		App.loadPage("package/packageEditPage.do?id=");
	}

	function doEdit()
	{
		var id = App.getTableSelection($table).id;
		if (id == undefined)
		{
			App.alert("请选择包装定义！");
			return;
		}
		App.loadPage("package/packageEditPage.do?id=" + id);
	}

</script>
