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
			<a href="#">包装管理</a>
			<span class="icon-angle-right"></span>
		</li>
		<li>
			<a href="#">包装定义</a>
		</li>
	</ul>
</div>

<!-- END PAGE HEADER-->

<div class="row-fluid">
	<div class="portlet box blue tabbable">
		<div class="portlet-title">
			<div class="caption">
				<i class="icon-reorder"></i>
				<span class="hidden-480">包装定义信息</span>
			</div>
		</div>

		<div class="portlet-body form">
			<div class="portlet-tabs">
				<!--<ul class="nav nav-tabs">
					<li class="">
						<a href="#portlet_tab2" data-toggle="tab">分配经销商</a>
					</li>
					<li class="active">
						<a href="#portlet_tab1" data-toggle="tab">基本信息</a>
					</li>
				</ul>
				-->
				<div class="tab-content">
					<div class="tab-pane active" id="portlet_tab1">
						<!-- BEGIN FORM-->
						<form action="#" id="fmPackageInfo" class="form-horizontal" style="padding-top:20px;">
							<!--<h3 class="form-section">
								基本信息
							</h3>
							-->
							<div class="row-fluid">
								<div class="span6 ">
									<div class="control-group">
										<label class="control-label">
											编码
											<span class="required">*</span>
										</label>
										<div class="controls">
											<input type="hidden" name="id" id="id" value="${ruleId }" />
											<input type="text" placeholder="必填项" class="m-wrap span12" id="code" name="code" />
										</div>
									</div>
								</div>
								<div class="span6 ">
									<div class="control-group">
										<label class="control-label">
											名称
											<span class="required">*</span>
										</label>
										<div class="controls">
											<input type="text" placeholder="必填项" class="m-wrap span12" id="name" name="name" />
										</div>
									</div>
								</div>
							</div>
							<div class="row-fluid">
								<div class="span6 ">
									<div class="control-group">
										<label class="control-label">
											包装类型
											<span class="required">*</span>
										</label>
										<div class="controls">
											<select class="m-wrap span12" id="package_type" name="package_type">
												${sPackageType }
											</select>
										</div>
									</div>
								</div>
								<div class="span6 ">
									<div class="control-group">
										<label class="control-label">
											包装规格
											<span class="required">*</span>
										</label>
										<div class="controls">
											<input type="text" placeholder="必填项" class="m-wrap span12" id="specifications" name="specifications" />
										</div>
									</div>
								</div>
							</div>
							<div class="row-fluid">
								<div class="span6 ">
									<div class="control-group">
										<label class="control-label">
											包装容量
											<span class="required">*</span>
										</label>
										<div class="controls">
											<input type="text" placeholder="必填项" class="m-wrap span12" id="capacity" name="capacity" />
										</div>
									</div>
								</div>
								<div class="span6">
									<div class="control-group">
										<label class="control-label">
											二维码尺寸
											<span class="required">*</span>
										</label>
										<div class="controls">
											<select id="qrarer_size" name="qrarer_size" class="m-wrap span12">
												${sQRSize }
											</select>
										</div>
									</div>
								</div>
							</div>
							<div class="row-fluid">
								<div class="span6 ">
									<div class="control-group">
										<label class="control-label">
											状态
											<span class="required">*</span>
										</label>
										<div class="controls">
											<select id="sts" name="sts" class="m-wrap span12">
												${sSts }
											</select>
										</div>
									</div>
								</div>
							</div>
							<div class="form-actions">
								<a class="btn blue savePackage"> <i class="icon-ok"></i> 保存 </a>
								<a class="btn cancelSave"> 返回 </a>
							</div>
						</form>
						<!-- END FORM-->
					</div>
					
					<!--<div class="tab-pane" id="portlet_tab2">
						<form action="#" id="fmBaseDealer" class="form-horizontal">
							<h3 class="form-section">
								分配经销商
							</h3>
							<div class="row-fluid">
								<div class="span12 ">
									<div class="control-group">
										<label class="control-label">
											选择经销商
										</label>
										<div class="controls">
											<select multiple="multiple" id="dealer" name="dealer">
												${sDealer }
											</select>
										</div>
									</div>
								</div>
							</div>

							<div class="form-actions">
								<a class="btn blue distributeDealer"> <i class="icon-ok"></i> 保存 </a>
								<a class="btn cancelSave"> 取消 </a>
							</div>
						</form>
						 END FORM
					</div>
					-->
				</div>
			</div>
		</div>

	</div>

</div>

<script src="<c:url value="/media/fm/jquery.validate.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.validate.messages_cn.js"/>" type="text/javascript"></script>

<script type="text/javascript">

	$().ready(function()
	{
		doGetPackageInfo();
		
		$(".savePackage").bind("click", function()
		{
			savePackage();
		});

		$(".cancelSave").bind("click", function()
		{
			backToList();
		});
		

		$("#fmPackageInfo").validate(
		{
			rules :
			{
				code :
				{
					required : true
				},
				name :
				{
					required : true
				},
				specifications :
				{
					required : true
				},
				capacity :
				{
					required : true
				},
				qrarer_size :
				{
					required : true
				}
			}
		});
		
	});
	
	function doGetPackageInfo()
	{
		var id = $("#id").val();
		if (id == undefined || id == "")
		{
			$("#id").val(0);
			$("#sts").val(1);
			return;
		}
		var url = "package/packageEdit.do?id=" + id;
		$.get(url, function(data, status)
		{
			$("#id").val(data.id);
			$("#code").val(data.code);
			$("#name").val(data.name);
			$("#package_type").val(data.package_type);
			$("#specifications").val(data.specifications);
			$("#capacity").val(data.capacity);
			$("#qrarer_size").val(data.qrarer_size);
			$("#sts").val(data.sts);
		});
	}

	function savePackage()
	{
		if ($("#fmPackageInfo").valid() == false)
		{
			return;
		}

		App.block();
		
		var formParam = $("#fmPackageInfo").serialize();
		$.post("package/pacakgeSave.do", formParam, function(data)
		{
			App.alert(data);

			App.unblock();
		});
	}
	
	function backToList()
	{
		App.loadPage("package/singlePkg.do");
	}
</script>
