<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<link rel="stylesheet" type="text/css" href="<c:url value="/media/css/select2_metro.css"/>" />

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
			<a href="#">包装规则</a>
		</li>
	</ul>
</div>

<!-- END PAGE HEADER-->

<div class="row-fluid">
	<div class="portlet box blue tabbable">
		<div class="portlet-title">
			<div class="caption">
				<i class="icon-reorder"></i>
				<span class="hidden-480">包装规则信息</span>
			</div>
		</div>

		<div class="portlet-body form">
			<div class="portlet-tabs">
				<div class="tab-content">
					<div class="tab-pane active" id="portlet_tab1">
						<!-- BEGIN FORM-->
						<form action="#" id="fmPackageRuleInfo" class="form-horizontal" style="padding-top:20px;">
							<div class="row-fluid">
								<div class="span6 ">
									<div class="control-group">
										<label class="control-label">
											包装规则编码
											<span class="required">*</span>
										</label>
										<div class="controls">
											<input type="hidden" name="id" id="id" value="${ruleId }" />
											<input type="text" placeholder="必填项" class="m-wrap span12" id="code" name="code" />
										</div>
									</div>
								</div>
							</div>
							<div class="row-fluid">
								<div class="span6 ">
									<div class="control-group">
										<label class="control-label">
											包装规则名称
											<span class="required">*</span>
										</label>
										<div class="controls">
											<input type="text" placeholder="必填项" class="m-wrap span12" id="name" name="name" />
										</div>
									</div>
								</div>
							</div>
							<div class="row-fluid">
								<div class="span6">
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
								<a class="btn blue savePackageRule"> <i class="icon-ok"></i> 保存 </a>
								<a class="btn cancelSave"> 返回 </a>
							</div>
						</form>
						<!-- END FORM-->
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script src="<c:url value="/media/fm/select2.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.validate.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.validate.messages_cn.js"/>" type="text/javascript"></script>

<script type="text/javascript">
	
	$().ready(function()
	{
		$("#product_id").select2({
			allowClear: true
		});
		
		$(".savePackageRule").bind("click", function()
		{
			savePackageRule();
		});

		$(".cancelSave").bind("click", function()
		{
			backToList();
		});
		
		$("#fmPackageRuleInfo").validate(
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
				product_id :
				{
					required : true
				}
			}
		});
		
		doGetPackgeRuleInfo();
		
	});
	
	function doGetPackgeRuleInfo()
	{
		var id = $("#id").val();
		if (id == undefined || id == "")
		{
			$("#id").val(0);
			$("#product_id").val("");
			$("#sts").val(1);
			return;
		}
		var url = "package/packageRuleEdit.do?id=" + id;
		$.get(url, function(data, status)
		{
			$("#id").val(data.id);
			$("#code").val(data.code);
			$("#name").val(data.name);
			$("#sts").val(data.sts);
			$("#product_id").select2().select2("val", null);
			$("#product_id").select2().select2("val", data.product_id);
		});
	}
	
	function savePackageRule()
	{
		if ($("#fmPackageRuleInfo").valid() == false)
		{
			return;
		}

		App.block();
		
		var formParam = $("#fmPackageRuleInfo").serialize();
		$.post("package/pacakgeRuleSave.do", formParam, function(data)
		{
			App.alert(data);

			App.unblock();
		});
	}
	
	function backToList()
	{
		App.loadPage("package/multiPkg.do");
	}

</script>
