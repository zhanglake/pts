<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<link rel="stylesheet" type="text/css" href="<c:url value="/media/css/jquery.treegrid.css"/>" />

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
			<a href="#">系统角色管理</a>
		</li>
	</ul>
</div>

<!-- END PAGE HEADER-->

<!-- 列表开始 -->
<div class="row-fluid">
	<div class="span12 portlet box blue">
		<div class="portlet-title">
			<div class="caption">
				<i class="icon-globe"></i><span class="titleExcel">系统角色列表</span>
			</div>
			<div class="actions">

			</div>
		</div>

		<div class="portlet-body">
			<div id="table-part"></div>

			<div id="formEdit" class="modal hide fade" tabindex="-1" data-backdrop="static" data-keyboard="false">
				<div class="modal-header">
					<button data-dismiss="modal" class="close" type="button"></button>
					<h3 class="page-title" id="tileBiao">
						角色信息
					</h3>
				</div>
				<div class="modal-body" style="text-align: center;">
					<form id="fmRoleEdit" action="#" class="form-horizontal">
						<input type="hidden" value="0" id="rlId">
						<div class="control-group">
							<label class="control-label">角色名称<span class="required">*</span></label>
							<div class="controls">
								<input type="text" placeholder="必填项" class="large m-wrap" id="rlNm" name="rlNm">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">角色类型<span class="required">*</span></label>
							<div class="controls">
								<select class="large m-wrap" id="sysTp">
									${sysTpSelect}
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">状态<span class="required">*</span></label>
							<div class="controls">
								<select class="large m-wrap" id="sts">
									${statusSelect}
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">描述<span class="required">*</span></label>
							<div class="controls">
								<textarea class="large m-wrap" placeholder="必填项" id="cmnts" rows="6"></textarea>
							</div>
						</div>
					</form>
				</div>

				<div class="modal-footer">
					<button type="button" data-dismiss="modal" class="btn red">取消</button>
					<button type="button" onclick="doSave()" class="btn green">保存</button>
				</div>
			</div>


			<div id="roleChoonse" class="modal hide fade flip-scroll" tabindex="-1" data-backdrop="static" data-keyboard="false">
				<input type="hidden" id="rlId" name="rlId" value="${roleId}" />

				<table class="table table-striped table-bordered">
					<tr>
						<td valign="top">
							<table id="permissonList" title="权限管理" class="tree">
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="2" class="buttons" align="center">
							<input type="submit" value="保 存" class="button" data-dismiss="modal" />
						</td>
					</tr>
				</table>
			</div>
		</div>

	</div>
</div>

<script src="<c:url value="/media/fm/daterangepicker.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/bootstrap-datetimepicker.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.validate.min.js"/>"	type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.validate.messages_cn.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/treegrid.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.treegrid.bootstrap3.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.treeview.js"/>"	type="text/javascript"></script>

<script type="text/javascript">

	var $table = App.initTable($("#tbList"));

	$().ready(function() 
	{
		doSearch();
		
		App.loadActions("RoleMgr", loadActionsBack);

		function loadActionsBack(){
			$(".doSearch").bind("click", function()
			{
				doSearch();
			});
			
			$(".doAddNew").bind("click", function()
			{
				doAddNew();
			});
	
			$(".doEdit").bind("click", function()
			{
				doEdit();
			});
			
			$(".doAuthority").bind("click", function() 
			{
				doAuthority();
			});
			
		}
		
		$("#fmRoleEdit").validate
		({
			rules: 
			{
				rlNm: 
				{
                    minlength: 2,
                    required: true
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
		$table = $("#tbList").bootstrapTable({
			 url: "account/roleList.do",
			 pagination: true,
			 sidePagination: "server",
			 clickToSelect: true,
			 singleSelect: true ,
			 columns: [
				{
					checkbox : true
				},
				{
					title : "角色编码",
					field : "rlId",
					visible : false
					
				},
				{
					title : "角色名称",
					field : "rlNm"
				},
				{
					title : "状态",
					field : "stsName"
				}, 
				{
					title : "类型",
					field : "typeName"
				}, 
				{
					title : "备注",
					field : "cmnts"
				}
			]
		});
		App.unblock();
	}
	
	function doAddNew()
	{
		$("#sts").val(1);
		$("#rlId").val(0);
		$("#rlNm").val("");
		$("#cmnts").val("");
		
		$("#formEdit").modal({keyboard : true});
	}
	
	function doEdit() 
	{
		var rlId = App.getTableSelection($table).rlId;
		if (rlId == undefined) 
		{
			App.alert("请选择一条记录！");
			return;
		}
		
		var url = "account/roleEdit.do?rlId=" + rlId;
		$.get(url, function(data, status) 
		{
			$("#sts").val(data.sts);
			$("#rlId").val(data.rlId);
			$("#rlNm").val(data.rlNm);
			$("#cmnts").val(data.cmnts);
			$("#sysTp").val(data.sysTp);
		});
		
		$("#formEdit").modal({keyboard : true});
	}
	
	function doSave() 
	{
		if ($("#fmRoleEdit").valid() == false)
		{
			return;
		}
		
		var formParam = 
		{
			"sts" : $("#sts").val(),
			"rlId" : $("#rlId").val(),
			"rlNm" : $("#rlNm").val(),
			"cmnts" : $("#cmnts").val(),
			"sysTp" : $("#sysTp").val()
		};

		$.post("account/roleSave.do", formParam, function(data) 
		{
			App.alert(data);
			
			doSearch();
			$("#formEdit").modal("hide");
		});
	}
	
	function doAuthority() 
	{
		var rlId = App.getTableSelection($table).rlId;
		if (rlId == undefined) 
		{
			App.alert("请选择一条记录！");
			return;
		}
		
		App.loadPage("account/permissionPage.do?rlId=" + rlId);
	}

	
</script>
