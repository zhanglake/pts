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
			<a href="#">二维码申请记录</a>
		</li>
	</ul>
</div>

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
				<button class="btn red doSearch">
					刷新
					<i class="m-icon-swapright m-icon-white"></i>
				</button>
			</div>
		</div>
		<div class="portlet-body form form-horizontal">
			<div class="control-group">
				<label class="control-label">
						申请时间
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
	</div>
</div>
				
<!-- 列表开始 -->
<div class="row-fluid">
	<div class="span12 portlet box blue">
		<div class="portlet-title">
			<div class="caption">
				<i class="icon-globe"></i><span class="titleExcel">申请记录</span>
			</div>
		</div>
		<div class="portlet-body" id="table-part">
				
		</div>
	</div>
</div>

<div id="operateModel" class="modal hide fade" data-backdrop="static">
	<div class="modal-header">
		<button data-dismiss="modal" class="close" type="button"></button>
		<h3 class="page-title" id="modal-header">二维码申请处理</h3>
	</div>
	<div class="modal-body">
		<div class="alert alert-block alert-info fade in">
			<h5>物品编码：<b><span class="productCode"></span></b>, 名称：<b><span class="productName"></span></b></h5>
		</div>
		<form action="#" id="operateForm" class="form-horizontal">
			<input type="hidden" id="id">
			<div class="control-group">
				<label class="control-label">
					说明
					<span class="required">*</span>
				</label>
				<div class="controls">
					<input type="text" class="form-control" id="comments" name="comments" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">
					状态
					<span class="required">*</span>
				</label>
				<div class="controls">
					<select id="status" name="status">
						<option value="0">未处理</option>
						<option value="1">已处理</option>
					</select>
				</div>
			</div>
		</form>
	</div>
	<div class="modal-footer">
		<button type="button" data-dismiss="modal" class="btn">
			取消
		</button>
		<button type="button" class="btn green" onclick="doSubmit()">
			确定
		</button>
	</div>
</div>

<script src="<c:url value="/media/fm/jquery.validate.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.validate.messages_cn.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/date.js"/>" type="text/javascript" ></script>
<script src="<c:url value="/media/fm/daterangepicker.js"/>"	type="text/javascript"></script>

<script type="text/javascript">
	
	var $table;

	$().ready(function()
	{
		App.initDateRange($("#date-range"), 1);		
		doSearch();
		
		$(".doSearch").bind("click", function()
		{
			doSearch();
		});
		
		$("#operateForm").validate(
		{
			rules :
			{
				comments :
				{
					required : true
				},
				status :
				{
					required : true
				}
			}
		});
		
	});
	
    function doSearch()
    {
    	App.block();
		$("#table-part").empty();
		var table = "<table id='tbList' class='table table-striped table-bordered table-hover table-full-width'></table>";
		$("#table-part").html(table);
		
		var dates = $("#date-range span").html().split('~');
		var fmtm = $.trim(dates[0]);
		var totm = $.trim(dates[1]);
		
		$table = $("#tbList").bootstrapTable({
			 url: "portal/applyList.do?fmtm=" + fmtm + "&totm=" + totm,
			 pagination: true,
			 sidePagination: "server",
			 clickToSelect: true,
			 singleSelect: true ,
			 columns: [
                  
				{
					title : "物品编号",
					field : "code"
				}, 
				{
					title : "物品名称",
					field : "name"
				}, 
				{
					title : "申请人",
					field : "lgnNm"
				}, 
				{
					title : "申请数量",
					field : "count"
				}, 
				{
					title : "申请时间",
					field : "createTime"
				}, 
				{
					title : "状态",
					field : "stsName"
				}, 
				{
					title : "操作",
					events: "operateEvents",
					formatter : function(value, row, index) 
					{
						return '<a class="operate" href="javascript:void(0)">处理</a>';
					}
				} 
			]
		});
		App.unblock();
	}

	function backToPortal() {
		App.loadPage("portal/myPortal.do");
	}
	
	window.operateEvents = 
	{
	    'click .operate': function (e, value, row, index) 
	    {
	    	var status = row.status;
	    	if(status == 1)
	    	{
	    		App.alert("该记录已处理！");
	    		return;
	    	}
	    	else
	    	{
	    		$(".productCode").text(row.code);
		    	$(".productName").text(row.name);
		    	$("#id").val(row.id);
		    	$("#status").val(status);
		    	
				$("#operateModel").modal({keyboard: true});
	    	}
	    }
	};
	
	function doSubmit()
	{
		var id = $("#id").val();
		var status = $("#status").val();
		
		if ($("#operateForm").valid() == false)
		{
			return;
		}
		
		$.post("portal/operateApply.do?id=" + id + "&status=" + status, function(data)
		{
			if(data == "1")
			{
				App.alert("二维码申请处理成功！");
			}
			else
			{
				App.alert("二维码申请处理失败！");
			}
			
			$("#operateModel").modal("hide");
			doSearch();
		});
	}
</script>
