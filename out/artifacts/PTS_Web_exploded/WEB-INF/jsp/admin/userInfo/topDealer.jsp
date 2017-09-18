<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!-- BEGIN PAGE HEADER-->
<div class="row-fluid">
	<h3 class="page-title"><!--
		用户管理
		<small>一级经销商用户</small>
	--></h3>

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
			<a href="#">一级经销商用户</a>
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
						<input type="text" placeholder="登录名、姓名或手机号关键字" class="m-wrap" id="searchParam" />
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
				<i class="icon-globe"></i><span class="titleExcel">一级经销商用户列表</span>
			</div>
		</div>

		<div class="portlet-body" id="table-part">
			<table id='tbList' class='table table-striped table-bordered table-hover table-full-width'>
			
			</table>
			
			<table id="xls-table" class="table table-striped table-bordered table-hover table-full-width" style="display: none;">
				<thead>
					<tr>
						<th data-field="lgnNm">登录名</th>
						<th data-field="usrNm">姓名</th>
						<th data-field="mobile">手机号</th>
						<th data-field="point">积分</th>
						<th data-field="role.rlNm">角色</th>
						<th data-field="dealer_name">所属经销商</th>
						<th data-formatter="loginFormatter">登录平台权限</th>
						<th data-field="stsName">状态</th>
					</tr>
				</thead>
			</table>
		</div>

		<div id="formEdit" class="modal hide fade" tabindex="-1" data-backdrop="static" data-keyboard="false">
			<div class="modal-header">
				<button data-dismiss="modal" class="close" type="button"></button>
				<h3 class="page-title" id="tileBiao">
					用户信息
				</h3>
			</div>
			<div class="modal-body">
				<form action="#" id="fmUserEdit" class="form-horizontal">
					<input type="hidden" value="0" id="usrId">
					<input type="hidden" value="0" id="opsd">
					<input type="hidden" value="${companyId }" id="companyId">
					<div class="control-group">
						<label class="control-label">
							登录名
							<span class="required">*</span>
						</label>
						<div class="controls">
							<input type="text" class="form-control" id="lgnNm" name="lgnNm" placeholder="不少于6个字符"/>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">
							姓名
							<span class="required">*</span>
						</label>
						<div class="controls">
							<input type="text" class="form-control" id="usrNm" name="usrNm" />
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">
							密码
							<span class="required">*</span>
						</label>
						<div class="controls">
							<input type="password" class="form-control" id="pswd" name="pswd" placeholder="不少于6个字符"/>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">
							确认密码
							<span class="required">*</span>
						</label>
						<div class="controls">
							<input type="password" class="form-control" id="pswdOK" name="pswdOK" />
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">
							手机号
							<span class="required">*</span>
						</label>
						<div class="controls">
							<input type="text" class="form-control" id="mobile" name="mobile" />
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">
							经销商
							<span class="required">*</span>
						</label>
						<div class="controls">
							<select id="dealerId">
								${dealerOption}
							</select>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">
							登录平台权限
							<span class="required">*</span>
						</label>
						<div class="controls">
							<select id="isLgn">
								<option value="1">可登录</option>
								<option value="0">不可登录</option>
							</select>
						</div>
					</div>
					<div class="control-group" style="display: none;">
						<label class="control-label">
							角色
							<span class="required">*</span>
						</label>
						<div class="controls">
							<select id="rlId">
								${roleOption}
							</select>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">
							状态
							<span class="required">*</span>
						</label>
						<div class="controls">
							<select id="sts">
								${statusSelect}
							</select>
						</div>
					</div>
				</form>
			</div>

			<div class="modal-footer">
				<button type="button" data-dismiss="modal" class="btn red">
					取消
				</button>
				<button type="button" onclick="doSave()" class="btn green">
					保存
				</button>
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
<script src="<c:url value="/media/js/config.js"/>" type="text/javascript"></script>

<script type="text/javascript">

	var $table;
	var $xlsTable = App.initTable($("#xls-table"));

	$().ready(function()
	{
		//setRadioButton();
		App.loadActions("DealerMgr", loadActionsBack);

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

		$("#fmUserEdit").validate(
		{
			rules :
			{
				lgnNm :
				{
					minlength : 6,
					required : true
				},
				usrNm :
				{
					required : true
				},
				pswd :
				{
					minlength : 6,
					required : true
				},
				pswdOK :
				{
					equalTo : "#pswd",
					required : true
				}
			}
		});
		
		doSearch();

	});

	function doSearch()
	{
		App.block();
		
		$table = $("#tbList").bootstrapTable("destroy").bootstrapTable({
			 url: "account/userList.do?roleId=" + 4 + "&searchParam=" + $("#searchParam").val(),
			 pagination: true,
			 sidePagination: "server",
			 clickToSelect: true,
			 singleSelect: true ,
			 columns: [	
				{
					checkbox: true
				},
				{
					title : "用户ID",
					field : "usrId",
					visible : false
					
				},
				{
					title : "登录名",
					field : "lgnNm"
				}, 
				{
					title : "姓名",
					field : "usrNm"
				}, 
				{
					title : "手机号",
					field : "mobile"
				}, 
				{
					title : "积分",
					field : "point"
				}, 
				{
					title : "角色",
					formatter : function(value, row, index)
					{
						return '<span>' + row.role.rlNm + '</span>';
					}
				}, 
				{
					title : "所属经销商",
					field : "dealer_name"
				},
				{
					title : "登录平台权限",
					formatter : function(value, row, index)
					{
						if(row.isLgn == 1)
						{
							return '<span>可登录</span>';
						}
						else
						{
							return '<span>不可登录</span>';
						}
					}
				},
				{
					title : "状态",
					field : "stsName"
				}
			]
		});
		App.unblock();
	}
	
	function doExport()
	{
		var searchParam = $("#searchParam").val();
		$.get("account/getByType.do?rlId=" + 4 + "&searchParam=" + searchParam + "&type=", function(data, status)
		{
			$xlsTable.bootstrapTable("load", data);
			
			App.handleExport($("#xls-table"), config.title_xls_TopDealerUser_comapny);
			
			App.unblock();
		});
	}	

	function doAddNew()
	{
		//setDivStore();

		$("#sts").val(1);
		$("#usrId").val(0);
		$("#lgnNm").val("");
		$("#usrNm").val("");
		$("#pswd").val("");
		$("#pswdOK").val("");
		$("#opsd").val("");
		$("#mobile").val("");
		$("#rlId").val("4");
		$("#isLgn").val(1);

		$("#formEdit").modal(
		{
			keyboard : true
		});
	}

	function doEdit()
	{
		var usrId = App.getTableSelection($table).usrId;
		if (usrId == undefined)
		{
			App.alert("请选择一条记录！");
			return;
		}
		//setDivStore();
		var url = "account/userEdit.do?usrId=" + usrId;
		$.get(url, function(data, status)
		{
			$("#sts").val(data.sts);
			$("#usrId").val(data.usrId);
			$("#lgnNm").val(data.lgnNm);
			$("#usrNm").val(data.usrNm);
			$("#pswd").val(data.pswd);
			$("#pswdOK").val(data.pswd);
			$("#opsd").val(data.pswd);
			$("#mobile").val(data.mobile);
			$("#rlId").val(data.role.rlId);
			$("#dealerId").val(data.dealer_id);
			$("#isLgn").val(data.isLgn);
		});

		$("#formEdit").modal(
		{
			keyboard : true
		});
	}

	function doSave()
	{
		if ($("#fmUserEdit").valid() == false)
		{
			return;
		}

		var xpsd = "";
		if ($("#pswd").val() != $("#opsd").val())
		{
			xpsd = hex_md5($("#pswd").val());
		} else
		{
			xpsd = $("#pswd").val();
		}

		var formParam =
		{
			"sts" : $("#sts").val(),
			"usrId" : $("#usrId").val(),
			"lgnNm" : $("#lgnNm").val(),
			"usrNm" : $("#usrNm").val(),
			"pswd" : xpsd,
			"mobile" : $("#mobile").val(),
			"company_id" : $("#companyId").val(),
			"dealer_id" : $("#dealerId").val(),
			"isLgn" : $("#isLgn").val(),
			"rlId" : 4
		};

		$.post("account/userSave.do", formParam, function(data)
		{
			if(data == "0")
			{
				App.alert("用户名已存在！");
			}
			else
			{
				if(data == "1")
				{
					App.alert("用户保存成功！");
				}
				if(data == "2")
				{
					App.alert("用户保存失败！");
				}
				doSearch();
				$("#formEdit").modal("hide");
			}
		});
	}
	
	function loginFormatter(value, row, index)
	{
		if(row.isLgn == 1)
		{
			return '<span>可登录</span>';
		}
		else
		{
			return '<span>不可登录</span>';
		}
	}
</script>
