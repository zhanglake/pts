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
			<a href="#">订单管理</a>
			<span class="icon-angle-right"></span>
		</li>
		<li>
			<a href="#">采购订单</a>
		</li>
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
							供应商：
						</label>
						<div class="controls">
							<select class="m-wrap" id="supplier">
								<option value="0">全部</option>
								${sSupplier }
							</select>
						</div>
					</div>
				</div>
			</div>
			<div class="row-fluid">
				<div class="span4">
					<div class="control-group">
						<label class="control-label">
							订单来源：
						</label>
						<div class="controls">
							<select class="m-wrap" id="orderFrom">
								<option value="">全部</option>
								<option value="1">手动创建</option>
								<option value="2">系统同步</option>
							</select>
						</div>
					</div>
				</div>
				<div class="span4">
					<div class="control-group">
						<label class="control-label">
							订单编号：
						</label>
						<div class="controls">
							<input type="text" class="m-wrap" id="orderNo" />
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
				<i class="icon-globe"></i><span class="titleExcel">采购订单列表</span>
			</div>
		</div>

		<div class="portlet-body" id="table-part">
			<table id="tbList" class="table table-striped table-bordered table-hover table-full-width">
			</table>
		</div>
	</div>
</div>

<div id="orderModel" class="modal hide fade" data-backdrop="static">
	<div class="modal-header">
		<button data-dismiss="modal" class="close" type="button"></button>
		<h3 class="page-title" id="modal-header"></h3>
	</div>
	<div class="modal-body">
		<table id="detailOrder" class="table table-bordered table-hover">
			<thead>
				<tr>
					<th data-formatter="pcodeFormatter">
						产品编码
					</th>
					<th data-formatter="pnameFormatter">
						产品名称
					</th>
					<th data-formatter="snameFormatter">
						生产供应商名称
					</th>
					<th data-field="count">
						采购数量(个)
					</th>
					<th data-field="packageNm">
						包装规则
					</th>
					<th data-field="capacity">
						包装容量(个)
					</th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="modal-footer">
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

	$().ready(function()
	{
		App.loadActions("PurchaseOrder", loadActionsBack);
		App.initDateRange($("#date-range"), 1);

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
			
			$(".doSync").bind("click", function()
			{
				doSync();
			});
			
			$(".doCreate").bind("click", function()
			{
				doCreate();
			});
			
			$(".doIssue").bind("click", function()
			{
				doIssue();
			});
		}	
		
		doSearch();	
	});
	
	function doSearch()
	{
		App.block();
		/*var table = "<table id='tbList' class='table table-striped table-bordered table-hover table-full-width'></table>";
		$("#table-part").empty();
		$("#table-part").html(table);*/
		
		var orderFrom = $("#orderFrom").val();
		var orderNo = $("#orderNo").val();
		var supplierId = $("#supplier").val();
		var dates = $("#date-range span").html().split('~');
		var fmtm = $.trim(dates[0]);
		var totm = $.trim(dates[1]);
		var url = "order/orderList.do?orderFrom=" + orderFrom + "&orderNo=" + orderNo + "&supplierId=" + supplierId + "&fmtm=" + fmtm + "&totm=" + totm;
		
		$table = $("#tbList").bootstrapTable("destroy").bootstrapTable({
			 url: url,
			 pagination: true,
			 clickToSelect: true,
			 singleSelect: false,
			 sidePagination: "server",
			 columns: [	
				{
					checkbox: true
				},
				{
					title : "订单编号",
					field : "orderNo"
				}, 
				{
					title : "订单来源",
					formatter : function(value, row, index)
					{
						if(row.orderFrom == "1")
						{
							return '手动创建';
						}
						else
						{
							return '金算盘同步';
						}
					}
				}, 
				{
					title : "创建人",
					field : "creator"
				}, 
				{
					title : "创建时间",
					formatter : function(value, row, index)
					{
						if(row.orderFrom == "1")
						{
							return row.createTime;
						}
						else
						{
							return row.kingDate;
						}
					}
				},
				{
					title : "同步时间",
					formatter : function(value, row, index)
					{
						if(row.orderFrom == "1")
						{
							return '-';
						}
						else
						{
							return row.createTime;
						}
					}
				}, 
				{
					title : "是否生成二维码",
					events : "operateEvents",
					formatter : function(value, row, index)
					{
						if(row.status == 1)
						{
							return config.order_sts_hasCreated;
						}
						else if(row.status == 2)
						{
							return '<a class="toProduct" href="javascript:void(0)" style="color: red; cursor: pointer;">' + config.order_sts_addProduct + '</a>';
						}
						else if(row.status == 3)
						{
							return '<a class="toPackage" href="javascript:void(0)" style="color: red; cursor: pointer;">' + config.order_sts_noPackage + '</a>';
						}
						else if(row.status == 5)
						{
							return '<a class="toSupplier" href="javascript:void(0)" style="color: red; cursor: pointer;">' + config.order_sts_addSupplier + '</a>';
						}
						else if(row.status == 6)
						{
							return '<sapn style="color: red;">' + config.order_sts_addProductSupplier + '</sapn>';
						}
						else if(row.status == 8)
						{
							return '<span style="color: red;">' + config.order_sts_issue_failed + '</span>';
						}
						else
						{
							return config.order_sts_noneCreated;
						}
					}
				}, 
				{
					title : "备注",
					field : "remark"
				},
				{
					title : "操作",
					events : "operateEvents", 
					formatter : function(value, row, index) 
					{
						return '<a class="view" href="javascript:void(0)">明细</a>' + '&emsp;' + '<a class="edit" href="javascript:void(0)">编辑</a>';
					}
				}
			]
		});
		
		App.unblock();
	}
	
	function doAddNew()
	{
		App.loadPage("order/orderEditPage.do?id=");
	}
	
	function doEdit(id, status)
	{
		if (id == undefined)
		{
			App.alert("请选择一条记录！");
			return;
		}
		
		if(status == 1)
		{
			App.alert("订单已生成二维码，无法再编辑！");
			return;
		}
		
		App.loadPage("order/orderEditPage.do?id=" + id);
	}
	
	function doSync()
	{
		App.block();
		$.post("order/syncOrder.do", function(data)
		{
			var code = data["code"];
			if(code == "200")
			{
				App.alert("同步采购订单成功！");
			}
			else if(code == "300")
			{
				App.alert("当前没有订单需同步！");
			}
			else
			{
				App.alert("同步采购订单失败！");
			}
			App.unblock();
			doSearch();
		});
	}
	
	function doCreate()
	{
		var orderArray = App.getTableSelections($table);
		var len = orderArray.length;
		if (len == 0)
		{
			App.alert("请选择至少一条记录生成！");
			return;
		}
		
		App.block();
		var ids = new Array();
		$.each(orderArray, function(i, e)
		{
			ids.push(e.id);
		});
		
		$.post("order/createBat.do", {ids:ids.join(",")}, function(data)
		{
			if(data == "1")
			{
				App.alert("二维码生成成功！");
			}
			else if(data == "0")
			{
				App.alert("二维码生成失败！");
			}
			else
			{
				App.alert(data);
			}
			doSearch();
			App.unblock();
		});
	}
	
	function doIssue()
	{
		var orderArray = App.getTableSelections($table);
		var len = orderArray.length;
		if (len == 0)
		{
			App.alert("请选择至少一条记录生成！");
			return;
		}
		
		App.block();
		var ids = new Array();
		$.each(orderArray, function(i, e)
		{
			ids.push(e.id);
		});
		
		$.post("order/issueQrcode.do", {ids:ids.join(",")}, function(data)
		{
			if(data == "1")
			{
				App.alert("二维码下发成功！");
			}
			else if(data == "0")
			{
				App.alert("二维码下发失败！");
			}
			else
			{
				App.alert(data);
			}
			doSearch();
			App.unblock();
		});
	}
	
	window.operateEvents = 
	{
	    'click .view': function (e, value, row, index) 
	    {
	        var orderId = row.id;
			var $detailOrder = App.initTable($("#detailOrder"));
			$("#orderModel>.modal-header>.page-title").text(row.orderNo + "采购订单明细");
			
			$.get("order/orderItemList.do?orderId=" + orderId, function (data, status)
			{
				$detailOrder.bootstrapTable("load", data);
			});
			
			$("#orderModel").modal({keyboard: true});
	    },
	    
	    'click .edit': function(e, value, row, index)
	    {
	    	doEdit(row.id, row.status);
	    },
	    
	    'click .toProduct': function (e, value, row, index) 
	    {
	    	App.loadPage("product/productPage.do?orderId=" + row.id);
	    },
	    
	    'click .toPackage': function (e, value, row, index) 
	    {
	    	App.loadPage("package/multiPkg.do");
	    },
	    
	    'click .toSupplier': function (e, value, row, index)
	    {
	    	App.loadPage("supplier/supplierPage.do");
	    }
	};
	
	function pcodeFormatter(value, row, index)
	{
		return '<span>' + row.product.code + '</span>';
	}
	
	function pnameFormatter(value, row, index)
	{
		return '<span>' + row.product.name + '</span>';
	}
	
	function snameFormatter(value, row, index)
	{
		return '<span>' + row.supplier.supplier_name + '</span>';
	}
	
</script>
