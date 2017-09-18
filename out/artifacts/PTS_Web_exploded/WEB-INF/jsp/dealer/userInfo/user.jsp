<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!-- BEGIN PAGE HEADER-->
<div class="row-fluid">
	<h3 class="page-title">
		用户信息
		<small>用户管理</small>
	</h3>

	<ul class="breadcrumb">
		<li>
			<i class="icon-home"></i>
			<a href="index.do">我的工作室</a>
			<span class="icon-angle-right"></span>
		</li>
		<li>
			<a href="#">用户管理</a>
		</li>
	</ul>
</div>

<!-- END PAGE HEADER-->
<!-- 列表开始 -->
<div class="row-fluid">
	<div class="span12 portlet box blue">
		<div class="portlet-title">
			<div class="caption">
				<i class="icon-globe"></i><span class="titleExcel">用户列表</span>
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
						<input type="text" class="m-wrap" id="searchParam" />
						<span class="add-on doSearch">
							<i class="icon-search"></i>
						</span>
					</div>
				</div>
			</div>
		</div>

		<div class="portlet-body" id="table-part">
			
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
					<input type="hidden" value="${dealerId }" id="dealer_id">
					<input type="hidden" value="0" id="opsd">
					<div class="control-group">
						<label class="control-label">
							登录名
							<span class="required">*</span>
						</label>
						<div class="controls">
							<input type="text" placeholder="必填项" class="form-control" id="lgnNm" name="lgnNm" />
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">
							姓名
							<span class="required">*</span>
						</label>
						<div class="controls">
							<input type="text" placeholder="必填项" class="form-control" id="usrNm" name="usrNm" />
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">
							密码
							<span class="required">*</span>
						</label>
						<div class="controls">
							<input type="password" class="form-control" id="pswd" name="pswd" />
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
							电子邮箱
						</label>
						<div class="controls">
							<input type="text" placeholder="请输入正确的邮箱" class="form-control" id="email" name="email" />
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">
							联系电话
						</label>
						<div class="controls">
							<input type="text" placeholder="请输入正确的联系电话" class="form-control" id="mobile" name="mobile" />
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

<script src="<c:url value="/media/fm/jquery.validate.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/additional-methods.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.validate.messages_cn.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/md5.js"/>" type="text/javascript"></script>

<script type="text/javascript">
	var $table = App.initTable($("#tbList"));

	$().ready(function()
	{
		//setRadioButton();

		App.loadActions("DealerUserMgr", loadActionsBack);

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
			
		}
		$("#fmUserEdit").validate(
		{
			rules :
			{
				lgnNm :
				{
					minlength : 5,
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
				},
				email :
				{
					email : true
				},
				mobile : 
				{
					phone : true
				}
			}
		});
		
		doSearch();

	});

	function doSearch()
	{
		App.block();
		
		var searchParam = $("#searchParam").val();
		var dealerId = $("#dealer_id").val();
		
		$("#table-part").empty();
		var table = "<table id='tbList' class='table table-striped table-bordered table-hover table-full-width'></table>";
		$("#table-part").html(table);
		$table = $("#tbList").bootstrapTable({
			 url: "account/userList.do?sysTp=4&isLgn=0&searchParam=" + searchParam + "&dealerId=" + dealerId,
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
					title : "邮箱",
					field : "email"
				}, 
				{
					title : "联系电话",
					field : "mobile"
				}, 
				{
					title : "状态",
					field : "stsName"
				}
			]
		});
		App.unblock();
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
		$("#email").val("");
		$("#mobile").val("");
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
			$("#email").val(data.email);
			$("#mobile").val(data.mobile);
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
			"email" : $("#email").val(),
			"mobile" : $("#mobile").val(),
			"dealer_id" : $("#dealer_id").val(),
			"rlId" : 4,
			"isLgn" : 0
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
	
	function roleFormatter(value, row, index)
	{
		return '<span>' + row.role.rlNm + '</span>';
	}
</script>
