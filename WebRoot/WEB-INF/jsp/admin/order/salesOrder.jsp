<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/media/css/daterangepicker.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/media/css/datepicker.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/media/css/chosen.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/media/css/select2_metro.css"/>" />
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
			<a href="#">销售订单</a>
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
		<div class="portlet-body form">
			<div class="form-horizontal">
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
								购货单位：
							</label>
							<div class="controls">
								<select class="m-wrap" id="dealers" style="width: 220px;">
									<option value="0">全部</option>
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
								模糊检索：
							</label>
							<div class="controls">
								<input type="text" class="m-wrap" placeholder="单据号或出库号" id="searchItems" />
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
				<i class="icon-globe"></i><span class="titleExcel">销售订单列表</span>
			</div>
		</div>
		<div class="portlet-body" id="table-part">
			<table id='tbList' class='table table-striped table-bordered table-hover table-full-width'></table>
		</div>
	</div>
</div>

<div id="salesOrderModel" class="modal hide fade" data-backdrop="static">
	<div class="modal-header">
		<button data-dismiss="modal" class="close" type="button"></button>
		<h3 class="page-title" id="modal-header"></h3>
	</div>
	<div class="modal-body">
		<table id="detailOrder" class="table table-bordered table-hover">
			<thead>
				<tr>
					<th data-field="productNo">物品编码</th>
					<th data-field="productName">物品名称</th>
					<th data-field="count">数量</th>
					<th data-field="unit">单位</th>
					<th data-field="box">箱数</th>
					<th data-field="location">货位</th>
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

<!-- 添加-->
<div id="formAddSalesOrder" class="modal hide fade tabbable" tabindex="-1" data-backdrop="static" data-keyboard="false" style="width: 620px;">
	<div class="modal-header">
		<button data-dismiss="modal" class="close" type="button"></button>
		<h3 class="page-title">添加销售订单</h3>
	</div>
	<div class="modal-body">
		<div class="tabbable tabbable-custom">
			<ul class="nav nav-tabs">
				<li class="active"><a href="#portlet_tab1" data-toggle="tab" id="basic-info">基本信息</a></li>
				<li><a href="#portlet_tab2" data-toggle="tab" id="add-product">物品明细</a></li>
			</ul>
			<div class="tab-content">
				<div class="tab-pane active" id="portlet_tab1">
					<form action="#" id="fmSalesOrder" class="form-horizontal">
						<div class="control-group">
							<label class="control-label">
								订单编号
								<span class="required">*</span>
							</label>
							<div class="controls">
								<input type="text" class="form-control" id="orderNo" name="orderNo"/>
								<input type="hidden" name="id" id="id" value="0" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">
								购货单位
								<span class="required">*</span>
							</label>
							<div class="controls">
								<select id="orderUnitNo" name="orderUnitNo" class="m-wrap" style="width: 220px;">
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">
								收货地址
								<span class="required">*</span>
							</label>
							<div class="controls">
								<input type="text" class="form-control" id="shippingAddress" name="shippingAddress" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">
								联系人
								<span class="required">*</span>
							</label>
							<div class="controls">
								<input type="text" class="form-control" id="contact" name="contact" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">
								电话
								<span class="required">*</span>
							</label>
							<div class="controls">
								<input type="text" class="form-control" id="tel" name="tel" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">
								发送方式
								<span class="required">*</span>
							</label>
							<div class="controls">
								<input type="text" class="form-control" id="deliveryWay" name="deliveryWay" />
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label">
								订货日期
								<span class="required">*</span>
							</label>
							<div class="controls">
								<div class="input-icon left">
									<i class="icon-calendar"></i>
									<input id="orderDate" name="orderDate" class="form_datetime m-wrap" type="text" style="width: 180px;">
								</div>
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label">
								状态
							</label>
							<div class="controls">
								<select id="status" class="m-wrap">
									<option value="0">未出库</option>
									<option value="1">已出库</option>
								</select>
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label">
								备注
							</label>
							<div class="controls">
								<input type="text" class="form-control" id="remark" name="remark" />
							</div>
						</div>
					</form>
				</div>
	
				<!-- 添加订单详情 -->
				<div class="tab-pane" id="portlet_tab2" style="min-height: 360px;">
					<input id="orderId" type="hidden" />
					<form action="#" class="form-horizontal">
						<div class="control-group">
							<label class="control-label">
								选择物品:
							</label>
							<div class="controls">
								<select id="selectProducts" class="chosen" data-placeholder="选择销售物品" tabindex="-1"></select>
							</div>
						</div>
					</form>
					<table id="orderItemTbl" class="table table-bordered table-hover">
						<thead>
							<tr>
								<th data-formatter="codeFormatter">产品编码</th>
								<th data-field="productName">产品名称</th>
								<th data-formatter="countFormatter">销售数量</th>
								<th data-formatter="boxFormatter">箱数</th>
								<th data-formatter="locationFormatter">货位</th>
								<th data-formatter="unitFormatter">计量单位</th>
								<th data-formatter="operateFormatter">操作</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div class="modal-footer">
		<button type="button" data-dismiss="modal" class="btn red">
			取消
		</button>
		<button type="button" onclick="doSave()" class="btn green" id="save-basic">
			保存
		</button>
		<button type="button" onclick="orderItemConfirm()" class="btn green" id="save-product">
			确认
		</button>
	</div>
</div>

<script src="<c:url value="/media/fm/select2.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/hashMap.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/date.js"/>" type="text/javascript" ></script>
<script src="<c:url value="/media/fm/daterangepicker.js"/>"	type="text/javascript"></script>
<script src="<c:url value="/media/fm/chosen.jquery.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/bootstrap-datepicker.js"/>"	type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.validate.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.validate.messages_cn.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.inputmask.bundle.min.js"/>" type="text/javascript"></script>

<script type="text/javascript">

	var $table;

	$().ready(function()
	{
		
		$("#save-product").hide();
		App.loadActions("SalesOrder", loadActionsBack);
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
			$(".doSync").bind("click", function()
			{
				doSync();
			});
		}
		
		$(".form_datetime").datepicker();
		
		//切换Tab切换按钮
		$("a[data-toggle='tab']").on("shown.bs.tab", function (e)
	   	{
	   		var activeTab = $(this).attr("id"); 
	   		
	   		if(activeTab == "basic-info")
	   		{
	   			$("#save-product").hide();
	   			$("#save-basic").show();
	   		}
	   		else if(activeTab == "add-product")
	   		{
	   			$("#save-product").show();
	   			$("#save-basic").hide();
	   		}
	   	});
		
		//回车查询
		$(".enter-search input").keypress(function (e) 
		{
			if (e.which == 13) 
			{
				doSearch();
			}
		});
		
		//下拉框改变事件
		$("#selectProducts").change( function ()
		{
			productItems($(this).val());
		});
		
		$("#fmSalesOrder").validate(
		{
			rules :
			{
				orderNo :
				{
					required : true
				},
				orderUnitNo :
				{
					required : true
				},
				shippingAddress :
				{
					required : true
				},
				contact :
				{
					required : true
				},
				tel :
				{
					required : true
				},
				deliveryWay :
				{
					required : true
				},
				orderDate :
				{
					required : true
				}
			}
		});
		
		doSearch();
		loadDealers();
	});
	
	function loadDealers()
	{
		$.get("order/loadDealers.do", function(data, status)
    	{
			var trHtml = '';
			var dealerHtml = '';
			$.each(data, function(i, dealer) 
			{
				trHtml += '<option value="' + dealer.dealer_code + '">' + dealer.dealer_name + '</option>';
				dealerHtml += '<option value="' + dealer.id + '">' + dealer.dealer_name + '</option>';
			});
			
			$("#orderUnitNo").html(trHtml);
			$("#orderUnitNo").select2();
			$("#dealers").append(dealerHtml);
	    	$("#dealers").select2();
	    	
			var t = setTimeout(function(){
				clearTimeout(t);
				$("#orderUnitNo").select2("val", data[0].dealer_code);	
			}, 300);
	    	
    	});
	}
	
	function doSearch()
	{
		App.block();
		
		var dates = $("#date-range span").html().split('~');
		
		var fmtm = $.trim(dates[0]);
		var totm = $.trim(dates[1]);
		var searchParam = $("#searchItems").val();
		var dealerId = $("#dealers").val();
		var orderType = $("#orderFrom").val();
		
		var url = "order/salesOrderList.do?fmtm=" + fmtm + "&totm=" + totm + "&searchParam=" + searchParam + "&dealerId=" + dealerId + "&ordType=" + orderType;
		
		$table = $("#tbList").bootstrapTable("destroy").bootstrapTable({
			 url: url,
			 pagination: true,
			 sidePagination: "server",
			 clickToSelect: true,
			 singleSelect: true ,
			 columns: [
                  {
                  	title: "出库号",
                  	field: "kingId"
                  },
                  {
                      title: "单据号",
                      field: "orderNo"
                  },
                  {
                      title: "购货单位名称",
                      field: "unitName"
                  },
                  {
                      title: "收货地址",
                      field: "shippingAddress"
                  },
                  {
                      title: "联系人",
                      field: "contact"
                  },
                  {
                      title: "电话",
                      field: "tel"
                  },
                  {
                      title: "发送方式",
                      field: "deliveryWay"
                  },
                  {
                      title: "订货日期",
                      field: "orderDate"
                  },
                  {
                      title: "同步日期",
                      field: "syncDate"
                  },
                  {
                      title: "备注",
                      formatter: function (value, row, index)
                      {
                      	return '<span style="color: red;">' + row.remark + '</span>';
                      }
                  }, 
                  {
                      title: "状态",
                      formatter: function (value, row, index)
                      {
                      	 if(row.status == 1)
                      	 {
                      	 	return '已出库';
                      	 }
                      	 else
                      	 {
                      	 	return '未出库';
                      	 }
                      }
                  },
				  {
					  title : "操作",
					  events: "operateEvents",
					  formatter : function(value, row, index) 
					  {
						 return '<a class="view" href="javascript:void(0)">查看</a>&emsp;' + '<a class="edit" href="javascript:void(0)">编辑</a>';
					  }
				  }
             ]
		});
		
		App.unblock();
	}
	
	window.operateEvents = 
	{
	    'click .view': function (e, value, row, index) 
	    {
			var orderNo = row.orderNo;
			var $detailOrder = App.initTable($("#detailOrder"));
			var orderId = row.id;
			$("#salesOrderModel>.modal-header>.page-title").text(orderNo + "销售订单明细");
			
			$.get("order/saleOrderDetails.do?orderId=" + orderId, function (data, status)
			{
				$detailOrder.bootstrapTable("load", data);
			});
			
			$("#salesOrderModel").modal({keyboard: true});
	    },
	    
	    'click .edit': function (e, value, row, index) 
	    {
			var orderId = row.id;
			var orderNo = row.orderNo;
			
			doEdit(orderId);
			doLoadDetail(orderId);
			orderProducts();
			
			$("#formAddSalesOrder>.modal-header>.page-title").text("编辑销售订单-" + orderNo);
			$("#formAddSalesOrder").modal({keyboard: true});
	    }
	};
	
	function doLoadDetail(orderId)
	{
		var $orderItemTbl = App.initTable($("#orderItemTbl"));
		$.get("order/saleOrderDetails.do?orderId=" + orderId, function (data, status)
		{
			$orderItemTbl.bootstrapTable("load", data);
			
			numberControl();
			bindDelete();
		});
	}
	
	function doEdit(orderId)
	{
		$.get("order/saleOrder.do?orderId=" + orderId, function(data, status)
		{
			$("#id").val(data.id);
			$("#orderNo").val(data.orderNo);
			$("#shippingAddress").val(data.shippingAddress);
			$("#contact").val(data.contact);
			$("#tel").val(data.tel);
			$("#deliveryWay").val(data.deliveryWay);
			$("#orderDate").val(data.orderDate);
			$("#remark").val(data.remark);
			$("#status").val(data.status);
			
			$("#orderUnitNo").select2("val", data.unitNo);	
			
			$("#orderNo,#orderUnitNo,#shippingAddress,#contact,#tel,#deliveryWay,#orderDate").attr("disabled", true);
		});
	}
	
	function doAddNew()
	{
		$("#id").val(0);
		$("#orderNo").val("");
		$("#orderUnitNo").val("");
		$("#shippingAddress").val("");
		$("#contact").val("");
		$("#tel").val("");
		$("#deliveryWay").val("");
		$("#orderDate").val("");
		$("#syncDate").val("");
		$("#remark").val("");
		$("#status").val(0);
		
		$("#orderNo,#orderUnitNo,#shippingAddress,#contact,#tel,#deliveryWay,#orderDate,#remark").attr("disabled", false);
		$("#status").attr("disabeld", true);
		
		$("#formAddSalesOrder").modal(
		{
			keyboard : true
		});
		
		orderProducts();
		
		$("#orderItemTbl tbody").empty();
	}
	
	function doSave()
	{
		if ($("#fmSalesOrder").valid() == false)
		{
			return;
		}

		var formParam =
		{
			"orderNo" : $("#orderNo").val(),
			"orderUnitNo" : $("#orderUnitNo").val(),
			"shippingAddress" : $("#shippingAddress").val(),
			"contact" : $("#contact").val(),
			"tel" : $("#tel").val(),
			"deliveryWay" : $("#deliveryWay").val(),
			"orderDate" : $("#orderDate").val(),
			"syncDate" : $("#syncDate").val(),
			"remark" : $("#remark").val(),
			"status" : $("#status").val(),
			"id" : $("#id").val()
		};

		$.post("order/saveSalesOrder.do", formParam, function(data)
		{
			App.alert(data.msg);
			
			if(null != data.order)
			{
				$("#id").val(data.order.id);
			}
			
			//doAddNew();
			doSearch(); 
		});
	}
	
	//订单详细信息确认按钮
	function orderItemConfirm()
	{
		var orderId = $("#id").val();
		if(orderId == 0 || orderId=="")
		{
			App.alert("请先保存销售订单主数据！");
		}
		else
		{
			var itemstr = "";
			var mtrCd = "";
			var rqsCnt = "";
			var doSave = true;
			var box="";
			var location="";
			var unit="";
			$("#orderItemTbl>tbody").find("tr").each(function()
			{
				rqsCnt = $(this).find(".rqsCnt").val();
				mtrCd = $(this).find(".mtrCd").text();
				productId = $(this).find(".productId").val();
				box = $(this).find(".box").val();
				location = $(this).find(".location").val();
				unit = $(this).find(".unit").val();
				if(rqsCnt == "")
				{
					doSave = false;
				}
				if (mtrCd != undefined && mtrCd != "")
				{
					itemstr += productId;
					itemstr += ",";
					itemstr += rqsCnt;
					itemstr += ",";
					itemstr += box;
					itemstr += ",";
					itemstr += location;
					itemstr += ",";
					itemstr += unit;
					itemstr += ";";
				}
			});
			
			if (itemstr == "")
			{
				return;
			}
			if(doSave)
			{
				$.post("order/saleOrderDetailsSave.do", {orderId : orderId, itemstr: itemstr}, function(data)
				{
					if(data == "1")
					{
						App.alert("添加物品成功！");
						$("#id").val("");
						$("#formAddSalesOrder").modal("hide");
						doSearch();
					}
					else
					{
						App.alert("添加物品失败！");
					}
				});
			}
			else
			{
				App.alert("有未完成的输入项，请检查！");
			}
		}
	}
	
	//产品列表下拉框
	function orderProducts()
	{
		var url = "product/productAllList.do?searchParam=";
		$.get(url, function (data, status)
		{
			productMap = new HashMap();
			var trHtml = '<option value=""></option>';
			$.each(data, function(i, product) 
			{
				trHtml += '<option value="' + product.id + '">' + product.code + " " + product.name + '</option>';
				productMap.put(product.id, product);
			});
			$("#selectProducts").html(trHtml);
				
			var $chosen = $("#selectProducts").next();
			if ($chosen.hasClass("chzn-container"))
			{
				$("#selectProducts").removeClass("chzn-done");
				$chosen.remove();
			}
			
			$("#selectProducts").chosen();
		});
	}
	
	function productItems(productItem)
	{
		if ($("#orderItemTbl").find("tr").length == 2)
		{
			$("#orderItemTbl").find(".no-records-found").remove();
		}
		var product = productMap.get(productItem);
		var exists = false;
		$(".mtrCd").each(function()
		{
			if ($(this).text() == product.code)
			{
				exists = true;
				return false;
			}
		});
		if (exists == true)
		{
			App.alert("已存在该项！");
			return;
		}
		var tr = ['<tr>', 
		        '<td><span class="mtrCd">' + product.code + '</span></td>',
		        '<input type="hidden" class="productId" value="' + product.id + '">',
				'<td>' + product.name + '</td>',
				'<td><input class="rqsCnt m-wrap" style="width:50px;"></td>',
				'<td><input class="box m-wrap" style="width:50px;"></td>',
				'<td><input class="location m-wrap" style="width:50px;"></td>',
				'<td><input class="unit m-wrap" style="width:50px;"></td>',
				'<td><a class="deleteOrderItem">删除</a></td>',
			    '</tr>'].join("");
		$("#orderItemTbl").append(tr);
		numberControl();
		bindDelete();
	}
	
	function numberControl()
	{
		$(".rqsCnt").inputmask({alias: "decimal"});
		$(".rqsCnt").bind("input propertychange change", function()
		{
			$(".rqsCnt").inputmask({alias: "decimal"});
		});
	}
	
	function bindDelete()
	{
		$(".deleteOrderItem").bind("click", function(e) 
		{
			e.preventDefault();
			$(this).closest("tr").remove();
		});
	}
	
	function doSync()
	{
		App.block();
		$.post("order/syncSalesOrder.do", function(data)
		{
			var code = data["code"];
			console.log(code);
			if(code == "200")
			{
				App.alert("同步销售订单成功！");
			}
			else if(code == "300")
			{
				App.alert("当前没有订单需同步！");
			}
			else
			{
				App.alert("同步销售订单失败！");
			}
			App.unblock();
			doSearch();
		});
	}
	
	function codeFormatter(value, row, index)
	{
		return '<span class="mtrCd">' + row.productNo + '</span>';
	}
	
	function countFormatter(value, row, index)
	{
		var str = '<input type="hidden" class="productId" value="' + row.productId + '">';
		str += '<input class="rqsCnt m-wrap" style="width:50px;" value="' + row.count + '">';
		return str;
	}
	
	function boxFormatter(value, row, index)
	{
		return '<input class="box m-wrap" style="width:50px;" value="' + row.box + '">';
	}
	
	function locationFormatter(value, row, index)
	{
		return '<input class="location m-wrap" style="width:50px;" value="' + row.location + '">';
	}
	
	function unitFormatter(value, row, index)
	{
		return '<input class="unit m-wrap" style="width:50px;" value="' + row.unit + '">';
	}
	
	function operateFormatter(value, row, index)
	{
		return '<a class="deleteOrderItem">删除</a>';
	}
</script>
