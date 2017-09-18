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
			<span class="icon-angle-right"></span>
		</li>
		<li>
			<a href="#">分配企业</a>
		</li>
	</ul>
</div>
<!-- END PAGE HEADER-->

<div class="row-fluid">
	<div class="span12">
		<div class="alert alert-block alert-info fade in">
			<h5>当前经销商 编码：<b>${dealer.dealer_code }</b>, 名称：<b>${dealer.dealer_name }</b></h5>
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
										<input type="text" placeholder="编码、名称或联系人关键字" class="m-wrap" id="searchItemsHas" />
										<input type="hidden" id="dealerId" value="${dealer.id }" />
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
								<i class="icon-globe"></i><span class="titleExcel">企业列表</span>
							</div>
						</div>
				
						<div class="portlet-body">
							<table id="hasTable" class="table table-striped table-bordered table-hover table-full-width" data-click-to-select="true" data-single-select="true">
								<thead>
									<tr>
										<th data-field="id" data-visible="false"></th>
										<th data-formatter="isDistributedFormatter"></th>
										<th data-formatter="codeFormatter">
											企业编码
										</th>
										<th data-field="name">
											名称
										</th>
										<th data-field="contact">
											联系人
										</th>
										<th data-field="phone">
											联系电话
										</th>
										<th data-field="fax">
											传真
										</th>
										<th data-field="address">
											地址
										</th>
										<th data-field="stsName">
											状态
										</th>
										<th data-field="remark">
											备注
										</th>
									</tr>
								</thead>
							</table>
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
								<button class="btn red doSearchNone">
									刷新
									<i class="m-icon-swapright m-icon-white"></i>
								</button>
								<button class="btn red doSave">
									保存
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
										<input type="text" placeholder="编码、名称或联系人关键字" class="m-wrap" id="searchItemsNone" />
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
								<i class="icon-globe"></i><span class="titleExcel">企业列表</span>
							</div>
						</div>
				
						<div class="portlet-body">
							<table id="noneTable" class="table table-striped table-bordered table-hover table-full-width" data-click-to-select="true" data-single-select="true">
								<thead>
									<tr>
										<th data-field="id" data-visible="false"></th>
										<th data-formatter="isDistributedFormatter"></th>
										<th data-formatter="codeFormatter">
											企业编码
										</th>
										<th data-field="name">
											名称
										</th>
										<th data-field="contact">
											联系人
										</th>
										<th data-field="phone">
											联系电话
										</th>
										<th data-field="fax">
											传真
										</th>
										<th data-field="address">
											地址
										</th>
										<th data-field="stsName">
											状态
										</th>
										<th data-field="remark">
											备注
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

	var $hasTable = App.initTable($("#hasTable"));
	var $noneTable = App.initTable($("#noneTable"));
	
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
		
		$(".doSave").bind("click", function()
		{
			doSave();
		});
		
		$(".doCancel").bind("click", function()
		{
			doCancel();
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
	
	function doSearchNone()
	{
		App.block();
		var url = "dealer/notInCompanies.do?searchItems=" + $("#searchItemsNone").val() + "&dealerId=" + $("#dealerId").val();
		$.get(url, function(data, status)
		{
			$noneTable.bootstrapTable("load", data);

			App.unblock();
		});
	}
	
	function doSearchHas()
	{
		App.block();
		var url = "dealer/hasCompanies.do?searchItems=" + $("#searchItemsHas").val() + "&dealerId=" + $("#dealerId").val();
		$.get(url, function(data, status)
		{
			$hasTable.bootstrapTable("load", data);

			App.unblock();
		});
	}
	
	function doSave()
	{
		var data = "";
		var dealerId = $("#dealerId").val();
		$("input[name='isSelect']:checkbox").each(function()
		{
			if ($(this).attr("checked") == "checked")
			{
				data += $(this).closest("tr").find(".compId").val() + ",";
			}
		});
		
		if(data == "")
		{
			App.alert("请选择企业！");
			return;
		}
		
		App.block();
		
		$.post("dealer/distributeCompany.do", {companies:data, dealerId:dealerId}, function(data)
		{
			App.alert(data);
			App.unblock();
			
			doSearchHas();
			doSearchNone();
		});
		
	}
	
	function doCancel()
	{
		var data = "";
		var dealerId = $("#dealerId").val();
		$("input[name='isSelect']:checkbox").each(function()
		{
			if ($(this).attr("checked") == "checked")
			{
				data += $(this).closest("tr").find(".compId").val() + ",";
			}
		});
		
		if(data == "")
		{
			App.alert("请选择企业！");
			return;
		}
		
		if(confirm("确定取消吗？") == false)
		{
			return;
		}
		
		App.block();
		
		$.post("dealer/cancelDistribute.do", {companies:data, dealerId:dealerId}, function(data)
		{
			App.alert(data);
			App.unblock();
			
			doSearchHas();
			doSearchNone();
		});
	}
	
	function backToList()
	{
		App.loadPage("dealer/dealerPage.do");
	}
	
	function codeFormatter(value, row, index)
	{
		return "<input type='hidden' class='compId' value='" + row.id +"' />" + row.company_code;
	}
	
	function isDistributedFormatter(value, row, index)
	{
		return "<input type='checkbox' name='isSelect' class='isSelect'>";
	}
	
</script>
