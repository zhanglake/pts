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
			<a href="#">数据报表</a>
			<span class="icon-angle-right"></span>
		</li>
		<li>
			<a href="#">销售报表</a>
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
							<input type="hidden" id="fmTm" value="${fmTm }" />
							<input type="hidden" id="toTm" value="${toTm }" />
						</div>
					</div>
				</div>
				<div class="span4">
					<div class="control-group">
						<label class="control-label">
							关键字：
						</label>
						<div class="controls">
							<input type="text" class="m-wrap" id="searchParam" />
						</div>
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
				<i class="icon-globe"></i><span class="titleExcel">销售报表</span>
			</div>
		</div>
		
		<div class="portlet-body" id="table-part">
			<table id='user-table' class='table table-striped table-bordered table-hover table-full-width'>
			</table>
		</div>
	</div>
</div>

<script src="<c:url value="/media/fm/date.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/daterangepicker.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/bootstrap-datetimepicker.js"/>" type="text/javascript"></script>

<script type="text/javascript">

	var $table;

	$().ready(function()
	{
		App.initDateRange($("#date-range"), 1);
		/* $("#date-range").daterangepicker({
        	opens: (App.isRTL() ? 'left' : 'right'),
        	format: 'yyyy-MM-dd',
        	separator: ' ~ ',
        	minDate: '2014-01-01',
        	maxDate: Date.today(),
        	startDate: Date.today().set({day:1}),
        	endDate: Date.today()
        	
		},
		function (start, end) 
    	{
			$("#date-range span").html(start.toString("yyyy-MM-dd") + ' ~ ' + end.toString("yyyy-MM-dd"));
    	}); */
    	
		App.loadActions("SalesReport", loadActionsBack);
		
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
		
		initDate();
		doSearch();
	});
	
	function initDate()
	{
		var fmTm = $("#fmTm").val();
		var toTm = $("#toTm").val();
		if(null != fmTm && fmTm != "")
		{
			$("#date-range span").html(fmTm + " ~ " + toTm);
			/* $("#date-range").daterangepicker({
				startDate: fmTm,
				endDate: toTm
			},
			function (start, end) 
	    	{
				$("#date-range span").html(start.toString("yyyy-MM-dd") + ' ~ ' + end.toString("yyyy-MM-dd"));
	    	}); */
		}
	}
	
	function doSearch() 
	{
		App.block();
		
		var searchParam = $("#searchParam").val();
		var dates = $("#date-range span").html().split('~');
		var fmtm = $.trim(dates[0]);
		var totm = $.trim(dates[1]);

		$table = $("#user-table").bootstrapTable("destroy").bootstrapTable({
			 url: "report/sales.do?searchParam=" + searchParam + "&fmTm=" + fmtm + "&toTm=" + totm,
			 pagination: true,
			 sidePagination: "server",
			 clickToSelect: true,
			 singleSelect: true,
			 columns: [
				[
					{
						title : "单位编码",
						field : "UNITNO",
						rowspan: 2,
						colspan: 1,
						valign: "middle"
					}, 
					{
						title : "单位名称",
						field : "UNITNAME",
						rowspan: 2,
						colspan: 1,
						valign: "middle"
					},
					{
						title : "已出库",
						colspan: 2,
						rowspan: 1,
						valign: "middle",
						align: "center"
					},
					{
						title : "未出库",
						colspan: 2,
						rowspan: 1,
						valign: "middle",
						align: "center"
					}
				], 
				[
					{
						title : "数量",
						field : "SALEDNUM",
						valign: "middle",
						align: "center"
					},
					{
						title : "详情",
						valign: "middle",
						align: "center",
						events: "operateEvents",
						formatter : function(value, row, index)
						{
							if(row.SALEDNUM == 0)
							{
								return '-';
							}
							return '<a class="view" href="javascript:void(0)" model="1">查看</a>';
						}
					},
					{
						title : "数量",
						field : "UNSALEDNUM",
						valign: "middle",
						align: "center"
					},
					{
						title : "详情",
						valign: "middle",
						align: "center",
						events: "operateEvents",
						formatter : function(value, row, index)
						{
							if(row.UNSALEDNUM == 0)
							{
								return '-';
							}
							return '<a class="view" href="javascript:void(0)" model="2">查看</a>';
						}
					}
				]
			]
		});
		
		App.unblock();
	}
	
	function doExport()
	{
		var searchParam = $("#searchParam").val();
		var dates = $("#date-range span").html().split('~');
		var fmtm = $.trim(dates[0]);
		var totm = $.trim(dates[1]);
		var title = "销售报表(" + fmtm + "~" + totm + ")";
		App.executeExport("report/exportSales.do", {searchParam: searchParam, fmTm: fmtm, toTm: totm, title: title});
	}
	
	
	window.operateEvents = 
	{
	    'click .view': function (e, value, row, index) 
	    {
	    	var unitNo = row.UNITNO;
	    	var unitName = row.UNITNAME;
	    	var dates = $("#date-range span").html().split('~');
			var fmtm = $.trim(dates[0]);
			var totm = $.trim(dates[1]);
			var type = $(this).attr("model");
	    	var url = "report/orderPage.do?unitNo=" + unitNo + "&unitName=" + unitName + "&fmTm=" + fmtm + "&toTm=" + totm + "&type=" + type;
	    	App.loadPage(url);
	    }
	};
	
	function indexFormatter(value, row, index)
	{
		return index + 1;
	}

</script>
