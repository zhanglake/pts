<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/media/css/chosen.css"/>" />

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
			<a href="#">采购订单管理</a>
			<span class="icon-angle-right"></span>
		</li>
		<li>
			<a href="#">采购订单信息</a>
		</li>
	</ul>
</div>

<!-- END PAGE HEADER-->

<div class="row-fluid">
	<div class="portlet box blue tabbable">
		<div class="portlet-title">
			<div class="caption">
				<i class="icon-reorder"></i>
				<span class="hidden-480">采购订单信息</span>
			</div>
			
		</div>

		<div class="portlet-body form">
			<div class="portlet-tabs">
				<div class="tab-content">
					<div class="active">
						<!-- BEGIN FORM-->
						<form id="fmOrderInfo" class="form-horizontal" style="padding-top:20px;">
							<div class="row-fluid">
								<div class="span6 ">
									<div class="control-group">
										<label class="control-label">
											订单编号
											<span class="required">*</span>
										</label>
										<div class="controls">
											<input type="hidden" name="id" id="id" value="${id }" />
											<input type="text" class="m-wrap span12" id="orderNo" name="orderNo" />
										</div>
									</div>
								</div>
								<div class="span6 ">
									<div class="control-group">
										<label class="control-label">
											订单类型
										</label>
										<div class="controls">
											<input type="text" class="m-wrap span12" id="orderType" name="orderType" />
										</div>
									</div>
								</div>
							</div>
							
							<div class="row-fluid">
								<div class="span6 ">
									<div class="control-group">
										<label class="control-label">
											创建人
										</label>
										<div class="controls">
											<input type="text" class="m-wrap span12" id="creator" name="creator" disabled="disabled"/>
										</div>
									</div>
								</div>
								<div class="span6 ">
									<div class="control-group">
										<label class="control-label">
											创建时间
										</label>
										<div class="controls">
											<input type="text" class="m-wrap span12" id="createTime" name="createTime" disabled="disabled"/>
										</div>
									</div>
								</div>
							</div>
							<div class="row-fluid">
								<div class="span6 ">
									<div class="control-group">
										<label class="control-label">
											备注
										</label>
										<div class="controls">
											<input type="text" class="m-wrap span12" id="remark" name="remark" />
											<input style="display:none" type="text" class="m-wrap span12" id="status" name="status" />
										</div>
									</div>
								</div>
							</div>
						</form>
						<div class="form-actions">
							<button class="btn blue saveOrder" onclick="saveOrder()"> <i class="icon-ok"></i> 保存 </button>
							<button class="btn cancelSave"> 返回 </button>
						</div>
						<!-- END FORM-->
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="row-fluid" id="orderItemsTb" style="display: none;">
	<div class="span12 portlet box blue">
		<div class="portlet-title">
			<div class="caption">
				<i class="icon-globe"></i><span class="titleExcel">采购订单明细列表</span>
			</div>
			<div class="actions" style="display: none;">
				<button class="btn red doAddNew">
					添加产品
					<i class="m-icon-swapright m-icon-white"></i>
				</button>
				<button class="btn red doCreate">
					生成二维码
					<i class="m-icon-swapright m-icon-white"></i>
				</button>
			</div>
		</div>

		<div class="portlet-body">
			
			<table id="tbList" class="table table-striped table-bordered table-hover table-full-width" data-click-to-select="true" data-single-select="true">
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
	</div>
</div>

<div id="addOrderItem" style="width: 760px;" class="modal hide fade" data-backdrop="static">
	<div class="modal-header">
		<button data-dismiss="modal" class="close" type="button"></button>
		<h3 class="page-title">
			添加采购产品
		</h3>
		<input id="orderId" type="hidden" />
	</div>
	
	<div class="modal-body" style="height: 360px;">
		<div class="controls" style="display: inline-block;">
			<select id="selectProducts" class="chosen" data-placeholder="选择采购产品" tabindex="1" style="width: 300px;"></select>
		</div>
		<input type="hidden" id="supplierId"  />
		<table id="orderItemTbl" class="table table-bordered table-hover">
			<thead>
				<tr>
					<th data-formatter="codeFormatter">
						产品编码
					</th>
					<th data-field="product.name">
						产品名称
					</th>
					<th data-formatter="supplierFormatter">
						生产供应商名称
					</th>
					<th data-formatter="numFormatter">
						采购数量
					</th>
					<th data-field="packageNm">
						包装规则
					</th>
					<th data-field="capacity">
						包装容量
					</th>
					<th data-formatter="operateFormatter">
						操作
					</th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="modal-footer">
		<button type="button" data-dismiss="modal" class="btn">
			取消
		</button>
		<button type="button" class="btn green orderItemConfirm">
			确认
		</button>
	</div>
</div>

<script src="<c:url value="/media/fm/hashMap.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/bootstrap-table.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.validate.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.validate.messages_cn.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/chosen.jquery.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.inputmask.bundle.min.js"/>" type="text/javascript"></script>

<script type="text/javascript">

	var $table = App.initTable($("#tbList"));

	$().ready(function()
	{
		doGetOrderInfo();
		$(".doAddNew").bind("click", function()
		{
			doAddNew();
		});

		$(".cancelSave").bind("click", function()
		{
			backToList();
		});

		$("#fmOrderInfo").validate(
		{
			rules :
			{
				orderNo :
				{
					required : true
				}
			}
		});
		
		
		$("#selectProducts").change( function ()
		{
			productItems($(this).val());
		});
		
		$(".orderItemConfirm").bind("click", function()
		{
			orderItemConfirm();
		});
	});
	
	function doGetOrderInfo()
	{
		var id = $("#id").val();
		if (id == undefined || id == "")
		{
			$("#id").val(0);
			return;
		}
		$(".doAddNew").show();
		$("#orderItemsTb").show();
		initOrderItemsTb(id);
		orderProducts();
		var url = "order/orderEdit.do?id=" + id;
		$.get(url, function(data, status)
		{
			$("#id").val(data.id);
			$("#orderNo").val(data.orderNo);
			$("#orderType").val(data.orderType);
			$("#remark").val(data.remark);
			$("#creator").val(data.creator);
			$("#createTime").val(data.createTime);
			$("#status").val(data.status);
			if($("#status").val() != "1")
			{
				$(".actions").show();
				$(".doCreate").bind("click", function()
				{
					createQrcode(id);
				});
			}
			else
			{
				$(".saveOrder").attr("disabled", true);				
			}
		});
		
	}
	
	function initOrderItemsTb(orderId){
		App.block();
		var url = "order/orderItemList.do?orderId=" + orderId;
		$.get(url, function(data, status)
		{
			$table.bootstrapTable("load", data);

			App.unblock();
		});
	}

	function saveOrder()
	{
		if ($("#fmOrderInfo").valid() == false)
		{
			return;
		}

		App.block();
		var formParam =
		{
			"id" : $("#id").val(),
			"orderNo" : $("#orderNo").val(),
			"orderType" : $("#orderType").val(),
			"orderFrom" : "1",
			"status" : 0,
			"createTime" : $("#createTime").val(),
			"remark" : $("#remark").val()
		};
		$.post("order/orderSave.do", formParam, function(data)
		{
			if(data == "-0")
			{
				App.alert("采购订单号已存在，请重新填写！");
			}
			else if(data == "-1")
			{
				App.alert("采购订单保存失败！");
			}
			else 
			{
				App.alert("采购订单保存成功！");
				$("#id").val(data);
			}
			var orderId = $("#id").val();
			
			App.loadPage("order/orderEditPage.do?id=" + orderId);

			App.unblock();
		});
	}
	
	function doAddNew()
	{
		App.blockUI($(".page-header-fixed"));
		var $orderItemTbl = App.initTable($("#orderItemTbl"));
		
		$.get("order/orderItemList.do?orderId=" + $("#id").val(), function(data, status)
		{
			$orderItemTbl.bootstrapTable("load", data);
			
			loadSuppliers();
			numberControl();
			bindDelete();
			changeSupplier();
			
			$("#addOrderItem").modal(
			{
				keyboard : true
			});
		});
		
		App.unblockUI($(".page-header-fixed"));
	}
	
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
		
		//var splCd = product.supplier == undefined ? "" : product.supplier.supplier_code;
		var splNm = product.supplierNM == undefined ? "" : product.supplierNM;
		var tr = ['<tr>', 
		        '<td><span class="mtrCd">' + product.code + '</span></td>',
		        '<input type="hidden" class="productId" value="' + product.id + '">',
				'<td>' + product.name + '</td>',
				'<td><select class="supplier" style="margin-bottom: 0px;"></select></td>',
				'<td><input class="rqsCnt m-wrap" style="width:50px;"></td>',
				'<td>' + product.packageName + '</td>',
				'<td>' + product.capacity + '</td>',
				'<td><a class="deleteOrderItem">删除</a></td>',
			    '</tr>'].join("");
		//alert(tr);
		$("#orderItemTbl").append(tr);
		
		loadSuppliers();
		numberControl();
	}
	
	function numberControl()
	{
		$(".rqsCnt").inputmask({alias: "decimal"});
		$(".rqsCnt").bind("input propertychange change", function()
		{
			$(".rqsCnt").inputmask({alias: "decimal"});
		});
	}
	
	function loadSuppliers()
	{
		var url = "supplier/suppliersList.do?searchParam=";
		$.get(url, function (data, status)
		{
			var supplierId = $(".supplier").val();
			var trHtml = '<option value=""></option>';
			$.each(data, function(i, supplier) 
			{
				trHtml += '<option value="' + supplier.id + '">' + supplier.supplier_name + '</option>';
			});
			$(".supplier").html(trHtml);
			
			if(supplierId != null)
			{
				$(".supplier").val(supplierId);
			}
			else
			{
				$(".supplier").val($("#supplierId").val());
			}
			
		});
		
		bindDelete();
		changeSupplier();		
	}
	
	function bindDelete()
	{
		$(".deleteOrderItem").bind("click", function(e) 
		{
			e.preventDefault();
			$(this).closest("tr").remove();
		});
	}
	
	function changeSupplier()
	{
		$(".supplier").change(function()
		{
			var supplierId = $(this).val();
			if(supplierId != null || supplierId != '')
			{
				$(".supplier").val(supplierId);
			}
		});
	}
	
	function orderItemConfirm()
	{
		var itemstr = "";
		var mtrCd = "";
		var rqsCnt = "";
		var spid = "";
		var doSave = true;
	
		$("#orderItemTbl>tbody").find("tr").each(function()
		{
			rqsCnt = $(this).find(".rqsCnt").val();
			mtrCd = $(this).find(".mtrCd").text();
			productId = $(this).find(".productId").val();
			spid = $(this).find(".supplier").val();
			if(rqsCnt == "")
			{
				doSave = false;
			}
			if(spid == "" || spid == undefined)
			{
				doSave = false;
			}
			
			if (mtrCd != undefined && mtrCd != "")
			{
				itemstr += productId;
				itemstr += ",";
				itemstr += rqsCnt;
				itemstr += ",";
				itemstr += spid;
				itemstr += ";";
			}
		});
		
		if (itemstr == "")
		{
			return;
		}
		if(doSave)
		{
			var orderId = $("#id").val();
			$.post("order/orderItemsSave.do", {orderId : orderId, itemstr: itemstr}, function(data)
			{
				if(data == "1")
				{
					App.alert("添加物品成功！");
				}
				else
				{
					App.alert("添加物品失败！");
				}
				$("#addOrderItem").modal("hide");
				initOrderItemsTb(orderId);
			});
		}
		else
		{
			App.alert("有未完成的输入项，请检查！");
		}
	}
	
	function createQrcode(orderId)
	{
		App.block();
		var url = "order/createQrcode.do?orderId=" + orderId;
		$.getJSON(url, function(data, status)
		{
			App.alert(data.msg);
			if(data.code == 1){
				$(".actions").hide();
				$(".saveOrder").attr("disabled", true);
			}
			App.unblock();
		});
	}
	
	function backToList()
	{
		App.loadPage("order/orderPage.do");
	}
	
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
	
	function codeFormatter(value, row, index)
	{
		return '<span class="mtrCd">' + row.product.code + '</span>';
	}
	
	function supplierFormatter(value, row, index)
	{
		$("#supplierId").val(row.supplier.id);
		return '<select class="supplier" style="margin-bottom: 0px;"></select>';
	}
	
	function numFormatter(value, row, index)
	{
		return '<input class="rqsCnt m-wrap" value="' + row.count + '" style="width:50px;">' +
			'<input type="hidden" class="productId" value="' + row.product.id + '">';
	}
	
	function operateFormatter(value, row, index)
	{
		return '<a class="deleteOrderItem">删除</a>';
	}
</script>
