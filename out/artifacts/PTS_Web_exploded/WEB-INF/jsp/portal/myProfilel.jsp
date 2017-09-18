<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!-- BEGIN PAGE HEADER-->
<div class="row-fluid">
	<div class="span12">
		<!-- BEGIN PAGE TITLE & BREADCRUMB-->
		<h3 class="page-title">
		<!--
			个人信息
			<small>我的个人信息</small>
		-->
		</h3>

		<ul class="breadcrumb">
			<li>
				<i class="icon-home"></i>
				<a href="index.do">首页</a>
				<span class="icon-angle-right"></span>
			</li>
			<li>
				<a href="#">个人信息</a>
			</li>
		</ul>
	</div>

</div>

<!-- END PAGE HEADER-->
<div id="dashboard">
	<!-- BEGIN DASHBOARD STATS -->
	<div class="row-fluid">
		<div class="span12">
			<div class="portlet box blue tabbable">
				<div class="portlet-title">
					<div class="caption"><i class="icon-reorder"></i>个人信息</div>
				</div>
				<div class="portlet-body">
					<div class="tabbable portlet-tabs">
						<ul class="nav nav-tabs">
							<li><a data-toggle="tab" href="#tab_2-2"><i class="icon-lock"></i> 修改密码</a></li>
							<li class="active"><a data-toggle="tab" href="#tab_1-1"> <i class="icon-cog"></i> 个人信息 </a></li>
						</ul>
						<div class="tab-content">
							<div id="tab_1-1" class="tab-pane active">
								<form action="#" id="fmProfilel" class="form-horizontal">
									<div class="row-fluid">
										<div class="span8">
											<div class="control-group">
												<label class="control-label">
													登录名
												</label>
												<div class="controls">
													<input type="text" class="m-wrap span8" disabled value="${info.lgnNm}">
													<input type="hidden" id="lgnNm" name="lgnNm" value="${info.lgnNm}">
												</div>
											</div>
										</div>
									</div>
									<div class="row-fluid">
										<div class="span8">
											<div class="control-group">
												<label class="control-label">
													用户名
												</label>
												<div class="controls">
													<input type="text" class="m-wrap span8" name="usrNm" value="${info.usrNm}">
												</div>
											</div>
										</div>
									</div>
									<div class="row-fluid">
										<div class="span8">
											<div class="control-group">
												<label class="control-label">
													邮箱地址
												</label>
												<div class="controls">
													<div class="input-icon left">
														<i class="icon-envelope"></i>
														<input class="wrap span8" type="text" name="email" value="${info.email}">
													</div>
												</div>
											</div>
										</div>
									</div>
									<div class="form-actions">
										<a href="#" class="btn green profilelSave">保存</a>
									</div>
								</form>
							</div>
							<div id="tab_2-2" class="tab-pane">
								<form action="#" id="fmPassword" class="form-horizontal">
									<div class="row-fluid">
										<div class="span8">
											<div class="control-group">
												<label class="control-label">
													当前密码
												</label>
												<div class="controls">
													<input type="password" class="m-wrap span8" id="opsd" name="opsd">
													<input type="hidden" value="${info.pswd}"   id="pswd" name="pswd">
												</div>
											</div>
										</div>
									</div>
									<div class="row-fluid">
										<div class="span8">
											<div class="control-group">
												<label class="control-label">
													新密码
												</label>
												<div class="controls">
													<input type="password" class="m-wrap span8" id="npsd" name="npsd">
												</div>
											</div>
										</div>
									</div>
									<div class="row-fluid">
										<div class="span8">
											<div class="control-group">
												<label class="control-label">
													确认新密码
												</label>
												<div class="controls">
													<input type="password" class="m-wrap span8" id="cpsd" name="cpsd">
												</div>
											</div>
										</div>
									</div>
									<div class="form-actions">
										<a href="#" class="btn green passwordSave">保存</a>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script src="<c:url value="/media/fm/jquery.validate.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.validate.messages_cn.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/md5.js"/>" type="text/javascript"></script>

<script>
	jQuery(document).ready(function()
	{
		$(".profilelSave").bind("click", function()
		{
			profilelSave();
		});

		$(".passwordSave").bind("click", function()
		{
			passwordSave();
		});
		
		$("#fmPassword").validate(
		{
			rules :
			{
				opsd :
				{
					required : true
				},
				npsd :
				{
					minlength : 6,
					required : true
				},
				cpsd :
				{
					equalTo : "#npsd",
					required : true
				}
			}
		});
	});

	function profilelSave()
	{
		$.post("account/profilelSave.do", $("#fmProfilel").serialize(), function(data)
		{
			App.alert(data);
		});
	}

	function passwordSave()
	{
		if ($("#fmPassword").valid() == false)
		{
			return;
		}

		var opsd = hex_md5($("#opsd").val());
		if ($("#pswd").val() != opsd)
		{
			App.alert("旧密码输入不正确！");
			return;
		}
		
		var npsd = hex_md5($("#npsd").val());
		var formParam =
		{
			"lgnNm" : $("#lgnNm").val(),
			"pswd" : npsd
		};
		
		$.post("account/passwordSave.do", formParam, function(data)
		{
			App.alert(data);
		});
	}
</script>

