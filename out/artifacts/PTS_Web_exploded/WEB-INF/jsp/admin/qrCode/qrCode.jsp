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
			<a href="#">二维码管理</a>
			<span class="icon-angle-right"></span>
		</li>
		<li>
			<a href="#">二维码查询</a>
		</li>
	</ul>
</div>
<div class="row-fluid">
	<div class="span12 portlet box yellow">
		<div class="portlet-title">
			<div class="caption">
				<i class="icon-reorder"></i>查询条件
			</div>
			<div class="actions">
			</div>
		</div>
		
		<div class="portlet-body form">
			<form action="#" class="form-horizontal">
				<div class="control-group input-append">
					<label class="control-label">
						模糊查询：
					</label>
					<div class="input-append">
						<input type="text" placeholder="编码或名称关键字" class="m-wrap" id="searchParam" />
						<span class="add-on doSearch">
							<i class="icon-search"></i>
						</span>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>

<div class="row-fluid">
	<div class="span12 portlet box blue">
		<div class="portlet-title">
			<div class="caption">
				<i class="icon-globe"></i><span class="titleExcel">二维码列表</span>
			</div>
		</div>
		
		<div class="portlet-body" id="table-part">
			<table id='qrcode-table' class='table table-striped table-bordered table-hover table-full-width'>
			</table>
			
			<table id="xls-table" class="table table-striped table-bordered table-hover table-full-width" style="display: none;">
				<thead>
					<tr>
						<th data-field="SUPPLIERCODE">供应商编码</th>
						<th data-field="SUPPLIERNAME">供应商名称</th>
						<th data-field="ALLQR">二维码总数</th>
						<th data-field="NOTUSEDQR">未使用</th>
						<th data-field="BOUNDQR">已绑定</th>
						<th data-field="PRINTQR">已打印</th>
						<th data-field="PACKINGQR">已包装</th>
						<th data-field="STORAGEQR">已入库</th>
						<th data-field="OUTQR">已出库</th>
						<th data-field="WASTEQR">已作废</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</div>

<div id="formRecovery" class="modal hide fade" tabindex="-1" data-backdrop="static" data-keyboard="false">
	<div class="modal-header">
		<button data-dismiss="modal" class="close" type="button"></button>
		<h3 class="page-title" id="tileBiao">
			回收二维码
		</h3>
	</div>
	<div class="modal-body">
		<form action="#" id="fmUserEdit" class="form-horizontal">
			<input type="hidden" id="supplierId">
			<div class="control-group">
				<label class="control-label">
					层级
					<span class="required">*</span>
				</label>
				<div class="controls">
					<select id="pkgLevel">
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
					</select>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">
					剩余数量
					<span class="required">*</span>
				</label>
				<div class="controls">
					<input type="text" class="form-control" id="remaining" disabled />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">
					回收数量
					<span class="required">*</span>
				</label>
				<div class="controls">
					<input type="text" class="form-control" id="number" />
				</div>
			</div>
		</form>
	</div>
	<div class="modal-footer">
		<button type="button" data-dismiss="modal" class="btn">
			取消
		</button>
		<button type="button" onclick="doRecovery()" class="btn green">
			确认
		</button>
	</div>
</div>

<script src="<c:url value="/media/js/config.js"/>" type="text/javascript"></script>

<script type="text/javascript">

	var $table;
	var $xlsTable = App.initTable($("#xls-table"));

	$().ready(function()
	{
		App.loadActions("QRCodeList", loadActionsBack);
		
		function loadActionsBack(){
			$(".doSearch").bind("click", function()
			{
				doSearch();
			});
			
			$(".doExport").bind("click", function()
			{
				doExport();
			});
		}
		
		$("#pkgLevel").change(function()
		{
			getRemaining();
		});
		
		$("#number").bind("blur", function()
    	{
    		isNumber();			
    	});
		
		doSearch();
	});

	function doSearch() 
	{
		App.block();
		
		var searchParam = $("#searchParam").val();
		/*var table = "<table id='qrcode-table' class='table table-striped table-bordered table-hover table-full-width'></table>";
		$("#table-part").empty();
		$("#table-part").html(table);*/

		$table = $("#qrcode-table").bootstrapTable("destroy").bootstrapTable({
			 url: "qrCode/qrCodeCount.do?searchParam=" + searchParam,
			 pagination: true,
			 sidePagination: "server",
			 clickToSelect: true,
			 singleSelect: true,
			 columns: [
				{
					title : "供应商编号",
					field : "SUPPLIERCODE"
				}, 
				{
					title : "供应商名称",
					field : "SUPPLIERNAME"
				}, 
				{
					title : "二维码总数",
					field : "ALLQR"
				}, 
				{
					title : "未使用",
					field : "NOTUSEDQR"
				}, 
				{
					title : "已绑定",
					field : "BOUNDQR"
				}, 
				{
					title : "已打印",
					field : "PRINTQR"
				}, 
				{
					title : "已包装",
					field : "PACKINGQR"
				}, 
				{
					title : "已入库",
					field : "STORAGEQR"
				},
				{
					title : "已出库",
					field : "OUTQR"
				},
				{
					title : "已作废",
					field : "WASTEQR"
				},
				{
					title : "操作",
					events: "operateEvents",
					formatter: function(value, row, index)
					{
						return '<a class="recovery" href="javascript:void(0)">回收</a>';
					}
				} 
			]
		});
		
		App.unblock();
	}
	
	function doExport()
	{
		App.block();
		var searchParam = $("#searchParam").val();
		$.get("qrCode/qrCodeCountXls.do?searchParam=" + searchParam, function(data, status)
		{
			$xlsTable.bootstrapTable("load", data);
			
			App.handleExport($("#xls-table"), config.title_xls_qrcode);
			
			App.unblock();
		});
	}
	
	
	window.operateEvents = 
	{
	    'click .recovery': function (e, value, row, index) 
	    {
			if(row.NOTUSEDQR <= 0)
			{
				App.alert("当前供应商已无二维码可回收！");
				return;
			}
			
			$("#supplierId").val(row.SUPPLIERID);
			$("#number").val("");
			$("#formRecovery").modal(
			{
				keyboard : true
			});
			getRemaining();
	    }
	};
	
	function doRecovery()
	{
		
		if(!isNumber())
		{
			return;
		}
		
		var supplierId = $("#supplierId").val();
		var pkgLevel = $("#pkgLevel").val();
		var number = Number($("#number").val());
		var remaining = Number($("#remaining").val());
		
		if(number > remaining)
		{
			App.alert("请输入不超过剩余数量的回收数量！");
			return;
		}
		
		if(confirm("确定回收该供应商的二维码吗？") == false)
		{
			return;
		}
		
		App.block();
		
		$.get("qrCode/recoveryQrcode.do", {supplierId:supplierId, pkgLevel:pkgLevel, number:number}, function (data, status)
		{
			if(data == "1")
			{
				App.alert("二维码回收成功！");
			}
			else
			{
				App.alert("二维码回收失败！");
			}
			
			$("#formRecovery").modal("hide");
			App.unblock();
			doSearch();
		});
	}
	
	function getRemaining()
	{
		var supplierId = $("#supplierId").val();
		var pkgLevel = $("#pkgLevel").val();
		$.get("qrCode/getRemaining.do", {supplierId:supplierId, pkgLevel:pkgLevel}, function (data, status)
		{
			$("#remaining").val(data.RECRUITNUM);
		});
	}
	
	function isNumber()
    {
    	var number = $("#number").val();
    	var exp = /^[1-9]\d*$/;
    	
		if(number == "" || !exp.test(number))
		{ 
			App.alert("请输入正整数！");
			return false;
		}
		return true;
    }

</script>
