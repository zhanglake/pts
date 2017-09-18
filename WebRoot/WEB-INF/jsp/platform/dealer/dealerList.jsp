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
			<a href="#">经销商管理</a>
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
						<input type="text" placeholder="编码、名称或联系人关键字" class="m-wrap" id="searchParam" />
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
				<i class="icon-globe"></i><span class="titleExcel">经销商列表</span>
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
		
		App.loadActions("DealerManager", loadActionsBack);
		
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
			
			$(".doDistribute").bind("click", function()
			{
				doDistribute();
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
			 url: "dealer/dealerList.do?searchParam=" + $("#searchParam").val(),
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
					title : "税号",
					field : "revenueNO"
				},
				{
					title : "编码",
					field : "dealer_code"
				}, 
				{
					title : "名称",
					field : "dealer_name"
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
					title : "区号",
					field : "areaCode"
				},
				{
					title : "电话",
					field : "tel"
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
		App.loadPage("dealer/dealerEditPage.do?id=");
	}

	function doEdit()
	{
		var id = App.getTableSelection($table).id;
		if (id == undefined)
		{
			App.alert("请选择经销商！");
			return;
		}
		App.loadPage("dealer/dealerEditPage.do?id=" + id);
	}
	
	function doDistribute()
	{
		var id = App.getTableSelection($table).id;
		if (id == undefined)
		{
			App.alert("请选择经销商！");
			return;
		}
		App.loadPage("dealer/distributeCompPage.do?id=" + id);
	}
	
	/*function opFormatter(value, row, index) 
	{
		return '<a class="view" href="javascript:void(0)">查看</a>';
	}
	
	//查看经销商
	window.operateEvents = 
	{
	    'click .view': function (e, value, row, index) 
	    {
			App.loadPage("dealer/hasCompaniesPage.do?dealerId=" + row.id);
	    }
	};*/
	
</script>
