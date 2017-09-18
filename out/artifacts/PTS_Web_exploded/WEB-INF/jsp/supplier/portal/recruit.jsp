<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<link rel="stylesheet" type="text/css" href="<c:url value="/media/css/datepicker.css"/>" />

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
			<a href="#">打印二维码</a>
			<span class="icon-angle-right"></span>
		</li>
		<li>
			<a href="#">补录生产信息</a>
		</li>
	</ul>
</div>

<!-- END PAGE HEADER-->

<div class="row-fluid">
	<div class="portlet box blue tabbable">
		<div class="portlet-title">
			<div class="caption">
				<i class="icon-reorder"></i>
				<span class="hidden-480">生产信息</span>
			</div>
		</div>

		<div class="portlet-body form">
			<div class="portlet-tabs">
				<div class="tab-content">
					<div class="active">
						<!-- BEGIN FORM-->
						<form action="#" id="productInfo" class="form-horizontal" style="padding-top:20px;">
							<div class="row-fluid">
								<div class="span6 ">
									<div class="control-group">
										<label class="control-label">
											SAP号
										</label>
										<div class="controls">
											<input type="text" disabled class="m-wrap span12" id="sapNo" name="sapNo" value="${product.sapNo }" />
										</div>
									</div>
								</div>
								<div class="span6 ">
									<div class="control-group">
										<label class="control-label">
											产品编码
										</label>
										<div class="controls">
											<input type="hidden" id="product_id" name="product_id" value="${product.id }" />
											<input type="text" disabled class="m-wrap span12" id="product_code" name="product_code" value="${product.code }" />
										</div>
									</div>
								</div>
							</div>
							<div class="row-fluid">
								<div class="span6">
									<div class="control-group">
										<label class="control-label">
											名称
										</label>
										<div class="controls">
											<input type="text" disabled id="product_name" name="product_name" value="${product.name }" class="m-wrap span12" />
										</div>
									</div>
								</div>
								<div class="span6 ">
									<div class="control-group">
										<label class="control-label">
											包装规则
										</label>
										<div class="controls">
											<input type="text" disabled class="m-wrap span12" id="packageName" value="${product.packageName }" />
										</div>
									</div>
								</div>
							</div>
							<div class="row-fluid">
								<div class="span6 ">
									<div class="control-group">
										<label class="control-label">
											规格型号
										</label>
										<div class="controls">
											<input type="text" disabled class="m-wrap span12" id="specNo" value="${product.specNo }" />
										</div>
									</div>
								</div>
								<div class="span6 ">
									<div class="control-group">
										<label class="control-label">
											二维码数量
										</label>
										<div class="controls">
											<input type="text" disabled class="m-wrap span12" id="number" value="${number }" />
										</div>
									</div>
								</div>
							</div>
							<div class="row-fluid">
								<div class="span6 ">
									<div class="control-group">
										<label class="control-label">
											包装
											<span class="required">*</span>
										</label>
										<div class="controls">
											<input type="text" class="m-wrap span12" id="packageTp" name="packageTp"  />
										</div>
									</div>
								</div>
								<div class="span6 ">
									<div class="control-group">
										<label class="control-label">
											装配线
											<span class="required">*</span>
										</label>
										<div class="controls">
											<input type="text" class="m-wrap span12" id="packageLine" name="packageLine" />
										</div>
									</div>
								</div>
							</div>
							<div class="row-fluid">
								<div class="span6 ">
									<div class="control-group">
										<label class="control-label">
											校验员
											<span class="required">*</span>
										</label>
										<div class="controls">
											<input type="text" class="m-wrap span12" id="validateUser" name="validateUser" />
										</div>
									</div>
								</div>
								<div class="span6 ">
									<div class="control-group">
										<label class="control-label">
											生产时间
											<span class="required">*</span>
										</label>
										<div class="controls">
											<div class="input-append date">
												<input class="m-wrap m-ctrl-medium date-picker" name="produce_time" readonly size="16" type="text" />
												<span class="add-on"><i class="icon-calendar"></i></span>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="form-actions">
								<a class="btn blue doSave"> <i class="icon-ok"></i> 保存 </a>
								<a class="btn backToPortal"> 取消 </a>
							</div>
						</form>
						<!-- END FORM-->
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script src="<c:url value="/media/fm/jquery.validate.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.validate.messages_cn.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/date.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/bootstrap-datepicker.js"/>" type="text/javascript"></script>

<script type="text/javascript">

	$().ready(function()
	{
		$(".date-picker").datepicker({
			rtl : App.isRTL(),
			language : "cn",
			format : "yyyy-mm-dd"
		});
		$(".date-picker").val(new Date().toString("yyyy-MM-dd"));
		
		$(".doSave").bind("click", function()
		{
			doSave();
		});
		
		$(".backToPortal").bind("click", function()
		{
			App.loadPage("portal/myPortal.do");
		});
				
		$("#productInfo").validate(
		{
			rules :
			{
				packageTp :
				{
					required : true
				},
				packageLine :
				{
					required : true
				},
				produce_time :
				{
					required : true
				},
				validateUser :
				{
					required : true
				}
			}
		});
	});
	
	function doSave()
	{
		if ($("#productInfo").valid() == false)
		{
			return;
		}
		
		$("input").removeAttr("disabled");
		var productId = $("#product_id").val();
		var number = $("#number").val();
		var form = $("#productInfo").serialize();
		
		App.block();
		$.post("portal/saveProductInfo.do?number=" + number + "&productId=" + productId, form, function(data)
		{
			if(data == "2")
			{
				App.alert("补录生产信息成功！");
				App.loadPage("portal/myPortal.do?productId=" + productId + "&number=" + number);
			}
			else if(data == "1")
			{
				App.alert("该产品二维码不足，请先申请！");
				$("#product_code,#product_name,#packageName,#specNo,#sapNo,#lineNum").attr("disabled","disabled");
			}			
			else
			{
				App.alert("补录生产信息失败！");
				$("#product_code,#product_name,#packageName,#specNo,#sapNo,#lineNum").attr("disabled","disabled");
			}
			
			App.unblock();
		});
	}
	
</script>
