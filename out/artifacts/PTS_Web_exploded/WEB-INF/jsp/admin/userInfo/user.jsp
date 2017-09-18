<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!-- BEGIN PAGE HEADER-->
<div class="row-fluid">
	<h3 class="page-title">
	<!--
		用户信息
		<small>用户管理</small>
	-->
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
			<a href="#">终端用户</a>
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
			<div class="row-fluid">
				<div class="span4">
					<div class="control-group">
						<label class="control-label">
							用户类型：
						</label>
						<div class="controls">
							<select id="rlId" class="m-wrap">
								<option value="0">全部</option>
								<option value="5">普通经销商</option>
								<option value="7">普通用户</option>
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
							<div class="input-append">
								<input type="text" placeholder="用户名或手机号关键字" class="m-wrap" id="searchParam" />
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
<!-- 列表开始 -->
<div class="row-fluid">
	<div class="span12 portlet box blue">
		<div class="portlet-title">
			<div class="caption">
				<i class="icon-globe"></i><span class="titleExcel">终端用户列表</span>
			</div>
		</div>

		<div class="portlet-body" id="table-part">
			<table id='tbList' class='table table-striped table-bordered table-hover table-full-width'></table>
		</div>

	</div>
</div>

<script src="<c:url value="/media/js/config.js"/>" type="text/javascript"></script>
<script type="text/javascript">
	var $table = App.initTable($("#tbList"));

	$().ready(function()
	{
		//setRadioButton();
		App.loadActions("UserMgr", loadActionsBack);

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

	function roleFormatter(value, row, index)
	{
		return '<span>' + row.role.rlNm + '</span>';
	}

	function doSearch()
	{
		App.block();
		
		var searchParam = $("#searchParam").val();
		var rlId = $("#rlId").val();
		
		$table = $("#tbList").bootstrapTable("destroy").bootstrapTable({
			 url: "account/endUserList.do?searchParam=" + searchParam + "&rlId=" + rlId,
			 pagination: true,
			 sidePagination: "server",
			 clickToSelect: true,
			 singleSelect: true ,
			 columns: [	
				{
					title : "登录名",
					field : "LGNNM"
					
				},
				{
					title : "手机号",
					field : "MOBILE"
				},
				{
					title : "积分",
					field : "POINT"
				}, 
				{
					title : "详细地址",
					field : "ADDRESS"
				},
				{
					title : "用户类型",
					field : "RLNM"
				}
			]
		});
		App.unblock();
	}
	
	function doExport()
	{
		var searchParam = $("#searchParam").val();
		var rlId = $("#rlId").val();
		
		App.executeExport("account/exportEndUser.do", {searchParam: searchParam, rlId: rlId, title: config.title_xls_EndUser_comapny});
	}

</script>
