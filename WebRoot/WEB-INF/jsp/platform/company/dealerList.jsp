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
			<a href="#">企业管理</a>
			<span class="icon-angle-right"></span>
		</li>
		<li>
			<a href="#">分配经销商</a>
		</li>
	</ul>
</div>
<!-- END PAGE HEADER-->

<div class="row-fluid">
	<div class="span12">
		<div class="alert alert-block alert-info fade in">
			<h5>当前企业 编码：<b>${company.company_code }</b>, 名称：<b>${company.name }</b></h5>
		</div>
	</div>
</div>

<div class="row-fluid tabbable">
	<div class="span12 tabbable tabbable-custom">
		<ul class="nav nav-tabs">
			<li class="active"><a href="#portlet_tab1" data-toggle="tab">已分配</a></li>
			<li><a href="#portlet_tab2" data-toggle="tab">未分配</a></li>
		</ul>
		<div class="tab-content">
			<div class="tab-pane active" id="portlet_tab1">
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
								<button class="btn red doCancel">
									取消分配
									<i class="m-icon-swapright m-icon-white"></i>
								</button>
								<button class="btn red doSearchHas">
									刷新
									<i class="m-icon-swapright m-icon-white"></i>
								</button>
								<button class="btn red backToList">
									返回
									<i class="m-icon-swapright m-icon-white"></i>
								</button>
							</div>
						</div>
						<div class="portlet-body form form-horizontal">
							<div class="control-group">
								<label class="control-label">
									模糊检索：
								</label>
								<div class="controls">
									<div class="input-append">
										<input type="text" placeholder="编码、名称或联系人关键字" class="m-wrap" id="searchParamHas" />
										<input type="hidden" id="compId" value="${company.id }" />
										<span class="add-on doSearchHas">
											<i class="icon-search"></i>
										</span>
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
								<i class="icon-globe"></i><span class="titleExcel">经销商列表</span>
							</div>
						</div>
				
						<div class="portlet-body" id="has-part">
							
						</div>
					</div>
				</div>
			</div>
			<div class="tab-pane" id="portlet_tab2">
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
								<button class="btn red doSave">
									保存
									<i class="m-icon-swapright m-icon-white"></i>
								</button>
								<button class="btn red doSearchNone">
									刷新
									<i class="m-icon-swapright m-icon-white"></i>
								</button>
								<button class="btn red backToList">
									返回
									<i class="m-icon-swapright m-icon-white"></i>
								</button>
								
							</div>
						</div>
						<div class="portlet-body form form-horizontal">
							<div class="control-group">
								<label class="control-label">
									模糊检索：
								</label>
								<div class="controls">
									<div class="input-append">
										<input type="text" placeholder="编码、名称或联系人关键字" class="m-wrap" id="searchParamNone" />
										<span class="add-on doSearchNone">
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
				
						<div class="portlet-body" id="none-part">
							<table id="noneTable" class="table table-striped table-bordered table-hover table-full-width" data-click-to-select="true" data-single-select="true">
								<thead>
									<tr>
										<th data-field="id" data-visible="false"></th>
										<th data-formatter="isDistributedFormatter"></th>
										<th data-field="dealer_code">
											编码
										</th>
										<th data-field="dealer_name">
											名称
										</th>
										<th data-field="contact" class="no-wrap">
											联系人
										</th>
										<th data-field="phone" class="no-wrap">
											联系电话
										</th>
										<th data-field="areaCode" class="no-wrap">
											区号
										</th>
										<th data-field="tel" class="no-wrap">
											电话
										</th>
										<th data-field="address">
											地址
										</th>
									</tr>
								</thead>
							</table>
				
						</div>
				
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">

	var $hasTable, $noneTable;
	
	$().ready(function()
	{
		$(".doSearchHas").bind("click", function()
		{
			doSearchHas();
		});
		
		$(".doSearchNone").bind("click", function()
		{
			doSearchNone();
		});
		
		$(".doCancel").bind("click", function()
		{
			doCancel();
		});
		
		$(".doSave").bind("click", function()
		{
			doSave();
		});
		
		$(".backToList").bind("click", function()
		{
			backToList();
		});
		
		doSearchHas();
		doSearchNone();
		
		$(".input-append input").keypress(function (e) {
			if (e.which == 13) {
				doSearch();
			}
		});
	});
	
	function doSearchHas()
	{
		App.block();
		
		var table = "<table id='hasTable' class='table table-striped table-bordered table-hover table-full-width'></table>";
		$("#has-part").empty();
		$("#has-part").html(table);
		
		$hasTable = $("#hasTable").bootstrapTable({
			 url: "company/hasDealers.do?searchParam=" + $("#searchParamHas").val() + "&compId=" + $("#compId").val(),
			 pagination: true,
			 sidePagination: "server",
			 clickToSelect: true,
			 singleSelect: true ,
			 columns: [
				{
					formatter : function(value, row, index) 
					{
						return "<input type='checkbox' name='isSelect' class='isSelect'>";
					}
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
					title : "电话",
					field : "areaCode"
				}, 
				{
					title : "包装定义",
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
	
	function doSearchNone()
	{
		App.block();
		var table = "<table id='noneTable' class='table table-striped table-bordered table-hover table-full-width'></table>";
		$("#none-part").empty();
		$("#none-part").html(table);
		
		$hasTable = $("#noneTable").bootstrapTable({
			 url: "company/dealerList.do?searchParam=" + $("#searchParamNone").val() + "&compId=" + $("#compId").val(),
			 pagination: true,
			 sidePagination: "server",
			 clickToSelect: true,
			 singleSelect: true ,
			 columns: [
				{
					formatter : function(value, row, index) 
					{
						return "<input type='checkbox' name='isSelect' class='isSelect'>";
					}
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
					title : "电话",
					field : "areaCode"
				}, 
				{
					title : "包装定义",
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
	
	function doCancel()
	{
		var data = "";
		var compId = $("#compId").val();
		$("input[name='isSelect']:checkbox").each(function()
		{
			if ($(this).attr("checked") == "checked")
			{
				data += $(this).closest("tr").find(".dealerId").val() + ",";
			}
		});
		
		if(data == "")
		{
			App.alert("请选择经销商！");
			return;
		}
		
		if(confirm("确定取消分配吗？") == false)
		{
			return;
		}
		
		App.block();
		$.post("company/cancelDistribute.do", {dealers:data, compId:compId}, function(data)
		{
			App.alert(data);
			App.unblock();
			
			doSearchNone();
			doSearchHas();
		});
	}
	
	function doSave()
	{
		var data = "";
		var compId = $("#compId").val();
		$("input[name='isSelect']:checkbox").each(function()
		{
			if ($(this).attr("checked") == "checked")
			{
				data += $(this).closest("tr").find(".dealerId").val() + ",";
			}
		});
		
		if(data == "")
		{
			App.alert("请选择经销商！");
			return;
		}
		
		App.block();
		
		$.post("company/distributeDealer.do", {dealers:data, companyId:compId}, function(data)
		{
			App.alert(data);
			App.unblock();
			
			doSearchNone();
			doSearchHas();
		});
		
	}
	
	function backToList()
	{
		App.loadPage("company/comPage.do");
	}

</script>
