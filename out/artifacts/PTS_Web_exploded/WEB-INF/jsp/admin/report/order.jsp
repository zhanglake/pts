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
			<a href="#">数据报表</a>
			<span class="icon-angle-right"></span>
		</li>
		<li>
			<a href="#">销售报表</a>
		</li>
</div>

<!-- 列表开始 -->
<div class="row-fluid">
	<div class="span12 portlet box blue">
		<div class="portlet-title">
			<div class="caption">
				<i class="icon-globe"></i><span class="titleExcel">unitName</span>
				<input type="hidden" id="unitName" value="${unitName }" />
				<input type="hidden" id="type" value="${type }" />
				<input type="hidden" id="fmTm" value="${fmTm }" />
				<input type="hidden" id="toTm" value="${toTm }" />
				<input type="hidden" id="unitNo" value="${unitNo }" />
			</div>
			<div class="actions">
				<button class="btn red doExport">
					导出
					<i class="m-icon-swapright m-icon-white"></i>
				</button>
				<button class="btn red return">
					返回
					<i class="m-icon-swapright m-icon-white"></i>
				</button>
			</div>
		</div>
		<div class="portlet-body" id="table-part">
			<table id='tbList' class='table table-striped table-bordered table-hover table-full-width'></table>
		</div>
	</div>
</div>

<div id="detailModel" class="modal hide fade" data-backdrop="static">
	<div class="modal-header">
		<button data-dismiss="modal" class="close" type="button"></button>
		<h3 class="page-title" id="modal-header"></h3>
	</div>
	<div class="modal-body">
		<table id="detailOrder" class="table table-bordered table-hover">
			<thead>
				<tr>
					<th data-field="PCODE">物品编码</th>
					<th data-field="PNAME">物品名称</th>
					<th data-field="PCOUNT">物品数量</th>
					<th data-field="OUTNAME">包装规格</th>
					<th data-formatter="qrnumFormatter">二维码数量</th>
				</tr>
			</thead>
		</table>
		<table id="xls-order" class="table table-striped table-bordered table-hover table-full-width" style="display: none;">
			<thead>
				<tr>
					<th data-field="PCODE">物品编码</th>
					<th data-field="PNAME">物品名称</th>
					<th data-field="PCOUNT">物品数量</th>
					<th data-field="OUTNAME">包装规格</th>
					<th data-field="QRNUM">二维码数量</th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="modal-footer">
		<input type="hidden" id="orderId" />
		<input type="hidden" id="orderNo" />
		<button type="button" class="btn green exportOrder">
			导出
		</button>
		<button type="button" data-dismiss="modal" class="btn">
			确定
		</button>
	</div>
</div>

<div id="qrcodeModel" class="modal hide fade" data-backdrop="static" style="width: 700px;">
	<div class="modal-header">
		<button data-dismiss="modal" class="close" type="button"></button>
		<h3 class="page-title">二维码列表</h3>
	</div>
	<div class="modal-body">
		<table id="detailQrcode" class="table table-bordered table-hover">
			<thead>
				<tr>
					<th data-formatter="indexFormatter">序号</th>
					<th data-field="SERIALNO">流水号</th>
					<th data-field="PCODE">物品编码</th>
					<th data-field="PNAME">物品名称</th>
					<th data-field="INCOUNT">包装数量</th>
					<th data-field="PLEVEL">包装层级</th>
					<th data-field="OUTSERIALNO">外包装流水号</th>
					<th data-formatter="traceFormatter">追踪</th>
				</tr>
			</thead>
		</table>
		<table id="xls-qrcode" class="table table-striped table-bordered table-hover table-full-width" style="display: none;">
			<thead>
				<tr>
					<th data-field="SERIALNO">流水号</th>
					<th data-field="PCODE">物品编码</th>
					<th data-field="PNAME">物品名称</th>
					<th data-field="INCOUNT">包装数量</th>
					<th data-field="PLEVEL">包装层级</th>
					<th data-field="OUTSERIALNO">外包装流水号</th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="modal-footer">
		<input type="hidden" id="productName" />
		<button type="button" class="btn green exportQrcode">
			导出
		</button>
		<button type="button" data-dismiss="modal" class="btn">
			确定
		</button>
	</div>
</div>

<div id="qrCodeTimeLine" class="modal hide fade" data-backdrop="static">
	<div class="modal-header">
		<button data-dismiss="modal" class="close" type="button"></button>
		<h3 class="page-title" id="modal-header"></h3>
	</div>
	<div class="modal-body flip-scroll">
		<div class="row-fluid">
			<div class="span12">
				<ul class="timeline" id="timeline">
				</ul>
			</div>
		</div>
	</div>
	<div class="modal-footer">
		<button type="button" data-dismiss="modal" class="btn green">
			确定
		</button>
	</div>
</div>

<script type="text/javascript">

	var $table;

	$().ready(function()
	{
		
		$(".doSearch").bind("click", function()
		{
			doSearch();
		});
		
		$(".return").bind("click", function()
		{
			doReturn();	
		});
		
		$(".doExport").bind("click", function()
		{
			doExport();	
		});
		
		$(".exportOrder").bind("click", function()
		{
			exportOrder();
		});
		
		$(".exportQrcode").bind("click", function()
		{
			exportQrcode();
		});
		
		initTitle();
		doSearch();
	});
	
	function initTitle()
	{
		var type = $("#type").val();
		var fmTm = $("#fmTm").val();
		var toTm = $("#toTm").val();
		var unitName = $("#unitName").val();
		var str = unitName;
		if(type == 1)
		{
			str += "-已出库销售单";
		}
		else
		{
			str += "-未出库销售单";
		}
		str += "(" + fmTm + "～" + toTm + ")"
		$(".titleExcel").html(str);
	}
	
	function doSearch()
	{
		App.block();
		
		$table = $("#tbList").bootstrapTable("destroy").bootstrapTable({
			 url: "report/orderList.do?fmTm=${fmTm}&toTm=${toTm}&unitNo=${unitNo}&type=${type}",
			 pagination: true,
			 sidePagination: "server",
			 clickToSelect: true,
			 singleSelect: true ,
			 columns: [
                  {
                  	title: "出库号",
                  	field: "KINGID"
                  },
                  {
                      title: "单据号",
                      field: "ORDERNO"
                  },
                  {
                      title: "订货日期",
                      field: "ORDERDATE"
                  },
                  {
                      title: "同步日期",
                      field: "SYNCDATE"
                  },
				  {
					  title : "二维码数量",
					  events: "operateEvents",
					  formatter : function(value, row, index) 
					  {
						  if(row.QRNUM == undefined)
						  {
							  return '<a class="view" href="javascript:void(0)" model="0">查看</a>';
						  }
						  return '<a class="view" href="javascript:void(0)" model="1">' + row.QRNUM + '</a>';
					  }
				  }
             ]
		});
		
		App.unblock();
	}
	
	function doReturn()
	{
		App.loadPage("report/salesReport.do?fmTm=${fmTm}&toTm=${toTm}");
	}
	
	function doExport()
	{
		var title = $(".titleExcel").html();
		var fmTm = $("#fmTm").val();
		var toTm = $("#toTm").val();
		var type = $("#type").val();
		var unitNo = $("#unitNo").val();
		App.executeExport("report/exportOrders.do", {type: type, fmTm: fmTm, toTm: toTm, unitNo: unitNo, title: title});
	}
	
	window.operateEvents = 
	{
	    'click .view': function (e, value, row, index) 
	    {
			var type = $(this).attr("model");
			var $detailOrder = App.initTable($("#detailOrder"));
			var $xlsOrder = App.initTable($("#xls-order")) ;
			$("#detailModel>.modal-header>.page-title").text(row.ORDERNO + "-明细");
			
			$.get("report/orderDetail.do?orderId=" + row.ID + "&type=" + type, function (data, status)
			{
				$detailOrder.bootstrapTable("load", data);
				$xlsOrder.bootstrapTable("load", data);
				$("#orderId").val(row.ID);
				$("#orderNo").val(row.ORDERNO);
				$(".qrcode").bind("click", function(){
					showQrcode($(this).attr("model"), $(this).attr("data"));
				});
			});
			
			$("#detailModel").modal({keyboard: true});
	    }
	};
	
	function showQrcode(productId, productName)
	{
		var orderId = $("#orderId").val();
		var orderNo = $("#orderNo").val();
		var $detailQrcode = App.initTable($("#detailQrcode"));
		var $xlsQrcode = App.initTable($("#xls-qrcode"));
		$("#detailQrcode>.modal-header>.page-title").text(orderNo + "-二维码列表");
		$.get("report/qrcodeDetail.do?orderId=" + orderId + "&productId=" + productId, function (data, status)
		{
			$detailQrcode.bootstrapTable("load", data);
			$xlsQrcode.bootstrapTable("load", data);
			$("#productName").val(productName);
			$(".trace").bind("click", function(){
				showTrace($(this).attr("model"), $(this).attr("data"));
			});
		});
		$("#qrcodeModel").modal({keyboard: true});
	}
	
	function showTrace(qrcode, serialNo)
	{
		$("#qrCodeTimeLine>.modal-header>.page-title").text("二维码追踪: " + serialNo);
		var html = "";
		$.get("trace/following.do?qrCode=" + qrcode, function (data, status)
		{
			for ( var index = 0; index < data.length; index++) 
			{
				html += "<li class='timeline-blue'>";	
				html += 	"<div class='timeline-icon'>";
				html += 	"</div>";
				html += 	"<div class='timeline-body'>";
				html += 		"<div class='timeline-content'>";
				html +=				"【" + data[index]["actionName"] + "】";
				html += 			data[index]["operator"] + "于" + data[index]["createTime"] + "扫码";
				html += 			isOut(data[index]["qrcode"], data[index]["actionType"]);
				html += 		"</div>";
				html += 	"</div>";
				html += "</li>";
			}
			$("#timeline").empty();
			$("#timeline").append(html);
		});
		
		$("#qrCodeTimeLine").modal({keyboard: true});
	}
	
	function isOut(qrcode, actionType)
	{
		var html = "";
		if(actionType == "5")
		{
			$.ajax({
				url: "trace/getOutInfo.do?qrCode=" + qrcode,
				async: false,
		     	success : function(data) 
		     	{	
		     		if(data != null)
		     		{
		     			html = "<br/>&ensp;出库号：" + data.kingId + "<br/>&ensp;销售单位：" + data.unitName;
		     		}
		     	},
		     	error : function(response) {
		     		App.alert("网络异常,请联系管理员");
		     	}
	    	});
	    }
	    return html;
	}
	
	function exportOrder()
	{
		var title = $("#detailModel>.modal-header>.page-title").html();
		App.handleExport($("#xls-order"), title);
	}
	
	function exportQrcode()
	{
		var title = $("#orderNo").val() + "-产品" + $("#productName").val() + "二维码";
		App.handleExport($("#xls-qrcode"), title);
	}
	
	function qrnumFormatter(value, row, index)
	{
		if(row.QRNUM == undefined)
		{
			return '-';
		}
		return '<a class="qrcode" href="javascript:void(0)" model="' + row.PID + '"data="' + row.PNAME+ '">' + row.QRNUM + '</a>';
	}
	
	function indexFormatter(value, row, index)
	{
		return index + 1;
	}
	
	function traceFormatter(value, row, index)
	{
		return '<a class="trace" href="javascript:void(0)" model="' + row.QRCODE + '" data="' + row.SERIALNO + '">追踪</a>';
	}
</script>
