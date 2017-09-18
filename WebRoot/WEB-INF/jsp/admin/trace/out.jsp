<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<link rel="stylesheet" type="text/css" href="<c:url value="/media/css/daterangepicker.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/media/css/timeline.css"/>" />

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
			<a href="#">出入库记录</a>
			<span class="icon-angle-right"></span>
		</li>
		<li>
			<a href="#">出库记录</a>
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
		<div class="portlet-body form">
			<div class="form-horizontal">
				<input type="hidden" id="actionType" value="${actionType }" />
				<div class="row-fluid">
					<div class="span4">
						<div class="control-group input-append">
							<label class="control-label">
								操作时间：
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
								模糊检索：
							</label>
							<div class="controls">
								<div class="input-append">
									<input type="text" class="m-wrap" width="300px" placeholder="流水号或外包装流水号关键字" id="searchItems" class="like-search" />
									<span class="add-on doSearch">
										<i class="icon-search"></i>
									</span>
								</div>
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
				<i class="icon-globe"></i><span class="titleExcel">出库记录</span>
			</div>
		</div>
		<div class="portlet-body" id="table-part">
			<table id='tbList' class='table table-striped table-bordered table-hover table-full-width'>
			</table>
		
			<table id="xls-table" class="table table-striped table-bordered table-hover table-full-width" style="display: none;">
				<thead>
					<tr>
						<th data-field="SERIALNO">流水号</th>
						<th data-field="PRODUCTCODE">产品编码</th>
						<th data-field="PRODUCTNAME">产品名称</th>
						<th data-field="CAPACITY">数量</th>
						<th data-field="PKGLEVEL">包装层级</th>
						<th data-field="PRNTSERIAL">外包装流水号</th>
						<th data-field="OPERATOR">操作人</th>
						<th data-field="OPERATE_TIME">操作时间</th>
						<th data-field="OPERATE_DEVICE">操作设备</th>
						<th data-field="OPERATE_TYPE">操作类型</th>
						<th data-field="STSNAME">当前状态</th>
					</tr>
				</thead>
			</table>
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
	</div>
</div>

<script src="<c:url value="/media/fm/date.js"/>" type="text/javascript" ></script>
<script src="<c:url value="/media/fm/daterangepicker.js"/>"	type="text/javascript"></script>
<script src="<c:url value="/media/js/config.js"/>"	type="text/javascript"></script>

<script type="text/javascript">

	var $table;
	var $xlsTable = App.initTable($("#xls-table"));

	$().ready(function()
	{
		App.loadActions("OutQuery", loadActionsBack);
		App.initDateRange($("#date-range"), 1);
		
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
		
		$(".input-append input").keypress(function (e) {
			if (e.which == 13) {
				doSearch();
			}
		});
		
		doSearch();
	});

	function doSearch()
	{
		App.block();
		
		/*var table = "<table id='tbList' class='table table-striped table-bordered table-hover table-full-width'></table>";
		$("#table-part").empty();
		$("#table-part").html(table);*/
		
		var dates = $("#date-range span").html().split('~');
		var frmTm = $.trim(dates[0]);
		var toTm = $.trim(dates[1]);
		var actionType = $("#actionType").val();

		$table = $("#tbList").bootstrapTable("destroy").bootstrapTable({
			 url: "trace/inOutList.do?frmTm=" + frmTm + "&toTm=" + toTm + "&actionType=" + actionType + "&searchParam=" + $("#searchItems").val(),
			 pagination: true,
			 sidePagination: "server",
			 clickToSelect: true,
			 singleSelect: true ,
			 columns: [
				{
					title : "序号",
					formatter : function(value, row, index) 
					{
						return index + 1;
					}
				},
				{
					title : "流水号",
					field : "SERIALNO"
				},
				{
					title : "物品编码",
					field : "PRODUCTCODE"
				}, 
				{
					title : "物品名称",
					field : "PRODUCTNAME"
				}, 
				{
					title : "数量",
					field : "CAPACITY"
				}, 
				{
					title : "包装层级",
					field : "PKGLEVEL"
				}, 
				{
					title : "生产流水号",
					field : "PRODUCESERIALNO"
				}, 
				{
					title : "外包装流水号",
					field : "PRNTSERIAL"
				},
				{
					title : "操作人",
					field : "OPERATOR"
				},
				{
					title : "操作时间",
					field : "OPERATE_TIME"
				}, 
				{
					title : "操作设备",
					field : "OPERATE_DEVICE"
				}, 
				{
					title : "操作类型",
					field : "OPERATE_TYPE"
				}, 
				{
					title : "当前状态",
					field : "STSNAME"
				},
				{
					title : "操作",
					events : "operateEvents", 
					formatter : function(value, row, index) 
					{
						return '<a class="view" href="javascript:void(0)">追踪</a>';
					}
				}  
			]
		});
		
		App.unblock();
	}
	
	function doExport()
	{
		var dates = $("#date-range span").html().split('~');
		var frmtm = $.trim(dates[0]);
		var totm = $.trim(dates[1]);
		var actionType = $("#actionType").val();
		var searchParam = $("#searchItems").val();
		
		App.block();
		
		$.get("trace/inOutListXls.do", {frmTm: frmtm, toTm: totm, actionType: actionType, searchParam: searchParam}, function(data, status)
		{
			
			$xlsTable.bootstrapTable("load", data);
			
			App.handleExport($("#xls-table"), config.title_xls_out_record);
			
			App.unblock();
		});
	}
	
	window.operateEvents = 
	{
	    'click .view': function (e, value, row, index) 
	    {
	        //App.block();
			var qrcode = row.QRCODE;
			var productName = row.PRODUCTNAME;
			$("#qrCodeTimeLine>.modal-header>.page-title").text("产品追踪: " + productName);
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
	};
	
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
	
</script>
