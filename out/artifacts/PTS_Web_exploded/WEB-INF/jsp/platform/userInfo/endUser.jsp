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
				<i class="icon-globe"></i><span class="titleExcel">用户列表</span>
			</div>
		</div>

		<div class="portlet-body" id="table-part">
			<table id='tbList' class='table table-striped table-bordered table-hover table-full-width'></table>
		</div>
	</div>
</div>

<script type="text/javascript">
	var $table = App.initTable($("#tbList"));

	$().ready(function()
	{
		doSearch();
		App.loadActions("DeviceUser", loadActionsBack);
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
	});

	function doSearch()
	{
		App.block();
		
		/*var table = "<table id='tbList' class='table table-striped table-bordered table-hover table-full-width'></table>";
		$("#table-part").empty();
		$("#table-part").html(table);*/
		
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
</script>
