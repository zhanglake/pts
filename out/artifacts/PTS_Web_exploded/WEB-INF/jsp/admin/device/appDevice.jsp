<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/media/css/daterangepicker.css"/>" />

<!-- BEGIN PAGE HEADER-->
<div class="row-fluid">
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
			<a href="#">APP终端管理</a>
		</li>
	</ul>
</div>

<!-- END PAGE HEADER-->
<!-- 列表开始 -->
<div class="row-fluid">
	<div class="span12 portlet box blue">
		<div class="portlet-title">
			<div class="caption">
				<i class="icon-globe"></i><span class="titleExcel">APP终端列表</span>
			</div>
			<div class="actions">
			</div>
		</div>

		<div class="portlet-body ">
			<input type="hidden" name="company_id" id="company_id" value="${companyId}" />
			<div id="table-part">
			
			</div>
		</div>
	</div>
</div>

<script src="<c:url value="/media/fm/daterangepicker.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/bootstrap-datetimepicker.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.uniform.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.validate.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.validate.messages_cn.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/md5.js"/>" type="text/javascript"></script>

<script type="text/javascript">
	var $table = App.initTable($("#tbList"));

	$().ready(function()
	{
		App.loadActions("APPDeviceMgr", loadActionsBack);

		function loadActionsBack(){
			$(".doSearch").bind("click", function()
			{
				doSearch();
			});			
		}
		
		doSearch();

	});

	function doSearch()
	{
		App.block();
		
		$("#table-part").empty();
		var table = "<table id='tbList' class='table table-striped table-bordered table-hover table-full-width'></table>";
		$("#table-part").html(table);
		$table = $("#tbList").bootstrapTable({
			 url:"device/appDeviceList.do",
			 pagination: true,
			 sidePagination: "server",
			 clickToSelect: true,
			 singleSelect: true ,
			 columns: [		
				{
					title : "手机型号",
					field : "model"
				},
				{
					title : "IMEI号",
					field : "deviceNo"
				}, 
				{
					title : "操作系统",
					formatter : function (value, row, index)
					{
						if(row.optSystem == 1)
						{
							return "Android";
						}
						else
						{
							return "iOS";
						}
					}
				}, 
				{
					title : "APP版本号",
					field : "version"
				}, 
				{
					title : "备注",
					field : "remark"
				}
			]
		});
		App.unblock();
	}
	
	

</script>
