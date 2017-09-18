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
			<a href="#">用户管理</a>
			<span class="icon-angle-right"></span>
		</li>
		<li>
			<a href="#">其它经销商用户</a>
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
			<div class="tools">
				<a href="javascript:;" class="collapse"></a>
			</div>
			<div class="actions">
			</div>
		</div>
		<div class="portlet-body form form-horizontal">
			<div class="control-group">
				<label class="control-label">
					模糊检索：
				</label>
				<div class="controls">
					<div class="input-append">
						<input type="text" placeholder="手机号或名称" class="m-wrap" id="searchParam" />
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
				<i class="icon-globe"></i><span class="titleExcel">其它经销商用户列表</span>
			</div>
		</div>

		<div class="portlet-body" id="table-part">
			
		</div>

	</div>
</div>

<script type="text/javascript">
	var $table = App.initTable($("#tbList"));

	$().ready(function()
	{
		//setRadioButton();
		App.loadActions("OtherDealerMgr", loadActionsBack);
		
		function loadActionsBack(){
			$(".doSearch").bind("click", function()
			{
				doSearch();
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
		$("#table-part").empty();
		var table = "<table id='tbList' class='table table-striped table-bordered table-hover table-full-width'></table>";
		$("#table-part").html(table);
		$table = $("#tbList").bootstrapTable({
			 url: "account/userList.do?roleId=5&searchParam=" + $("#searchParam").val(),
			 pagination: true,
			 sidePagination: "server",
			 clickToSelect: true,
			 singleSelect: true ,
			 columns: [	
				{
					title : "手机号",
					field : "mobile"
				},
				{
					title : "经销商名称",
					field : "usrNm"
				}, 
				{
					title : "积分",
					field : "point"
				},
				{
					title : "详细地址",
					field : "addr"
				}
			]
		});
		App.unblock();
	}

</script>
