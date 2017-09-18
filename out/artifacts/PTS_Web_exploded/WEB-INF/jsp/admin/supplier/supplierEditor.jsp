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
			<a href="#">基础数据</a>
			<span class="icon-angle-right"></span>
		</li>
		<li>
			<a href="#">生产供应商管理</a>
		</li>
	</ul>
</div>

<!-- END PAGE HEADER-->

<div class="row-fluid">
	<div class="portlet box blue tabbable">
		<div class="portlet-title">
			<div class="caption">
				<i class="icon-reorder"></i>
				<span class="hidden-480">生产供应商信息</span>
			</div>
		</div>

		<div class="portlet-body form">
			<div class="portlet-tabs">
				<div class="tab-content">
					<div class="active">
						<!-- BEGIN FORM-->
						<form action="#" id="fmSupplierInfo" class="form-horizontal" style="padding-top:20px;">
							<div class="row-fluid">
								<div class="span6 ">
									<div class="control-group">
										<label class="control-label">
											供应商编码
											<span class="required">*</span>
										</label>
										<div class="controls">
											<input type="hidden" name="id" id="id" value="${id }" />
											<input type="text" placeholder="必填" class="m-wrap span12" id="supplier_code" name="supplier_code" />
										</div>
									</div>
								</div>
								<div class="span6 ">
									<div class="control-group">
										<label class="control-label">
											联系人
											<span class="required">*</span>
										</label>
										<div class="controls">
											<input type="text" placeholder="必填" class="m-wrap span12" id="contact" name="contact" />
										</div>
									</div>
								</div>
							</div>
							<div class="row-fluid">
								<div class="span6 ">
									<div class="control-group">
										<label class="control-label">
											名称
											<span class="required">*</span>
										</label>
										<div class="controls">
											<input type="text" placeholder="必填" class="m-wrap span12" id="supplier_name" name="supplier_name" />
										</div>
									</div>
								</div>
								<div class="span6 ">
									<div class="control-group">
										<label class="control-label">
											是否有系统支撑
											<span class="required">*</span>
										</label>
										<div class="controls">
											<select class="m-wrap span12" id="has_system" name="has_system">
												${has}
											</select>
										</div>
									</div>
								</div>
								
							</div>
							<div class="row-fluid">
								<div class="span6">
									<div class="control-group">
										<label class="control-label">企业内部编码</label>
										<div class="controls">
											<input type="text" id="innerCode" name="innerCode" class="m-wrap span12" />
										</div>
									</div>
								</div>
								<div class="span6 ">
									<div class="control-group">
										<label class="control-label">
											是否有打印权限
											<span class="required">*</span>
										</label>
										<div class="controls">
											<select class="m-wrap span12" id="canPrint" name="canPrint">
												${has}
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
										</label>
										<div class="controls">
											<select class="m-wrap span12" id="status" name="status">
												${sSts}
											</select>
										</div>
									</div>
								</div>
								<div class="span6 ">
									<div class="control-group">
										<label class="control-label">
											联系电话
											<span class="required">*</span>
										</label>
										<div class="controls">
											<input type="text" placeholder="必填，请填写正确的联系电话" class="m-wrap span12" id="phone" name="phone" />
										</div>
									</div>
								</div>
							</div>
							<div class="row-fluid">
								<div class="span6">
									<div class="control-group">
										<label class="control-label">
											所在省份
										</label>
										<div class="controls">
											<select id="province" name="province" class="m-wrap span12">
												${sProvince }
											</select>
										</div>
									</div>
								</div>
								<div class="span6">
									<div class="control-group">
										<label class="control-label">
											传真
										</label>
										<div class="controls">
											<input type="text" placeholder="请填写正确的传真" class="m-wrap span12" id="fax" name="fax" />
										</div>
									</div>
								</div>
							</div>
							<div class="row-fluid">
								<div class="span6">
									<div class="control-group">
										<label class="control-label">
											详细地址
										</label>
										<div class="controls">
											<input type="text" id="address" name="address" class="m-wrap span12" />
										</div>
									</div>
								</div>
								<div class="span6">
									<div class="control-group">
										<label class="control-label">
											邮箱
										</label>
										<div class="controls">
											<input type="text" placeholder="请填写正确的邮箱格式" id="email" name="email" class="m-wrap span12" />
										</div>
									</div>
								</div>
							</div>
							<div class="form-actions">
								<a class="btn blue saveSupplier"> <i class="icon-ok"></i> 保存 </a>
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

<script src="<c:url value="/media/fm/bootstrap-table.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.validate.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.validate.messages_cn.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/additional-methods.js"/>" type="text/javascript"></script>

<script type="text/javascript">
	var $table = App.initTable($("#tbList"));
	var id = $("#id").val();

	$().ready(function()
	{
		doGetSupplierInfo();

		$(".saveSupplier").bind("click", function()
		{
			saveSupplier();
		});


		$(".cancelSave").bind("click", function()
		{
			backToList();
		});

		$("#fmSupplierInfo").validate(
		{
			rules :
			{
				supplier_code :
				{
					required : true
				},
				supplier_name :
				{
					required : true
				},
				has_system :
				{
					required : true
				},
				contact :
				{
					required : true
				},
				phone :
				{
					phone : true,
					required : true
				},
				fax :
				{
					fax : true
				},
				email :
				{
					email : true
				}
			}
		});
	});

	function doGetSupplierInfo()
	{
		var id = $("#id").val();
		if (id == undefined || id == "")
		{
			$("#id").val(0);
			$("#status").val(1);
			return;
		}
		var url = "supplier/supplierEdit.do?id=" + id;
		$.get(url, function(data, status)
		{
			$("#id").val(data.id);
			$("#supplier_code").val(data.supplier_code);
			$("#supplier_name").val(data.supplier_name);
			$("#has_system").val(data.has_system);
			$("#canPrint").val(data.canPrint);
			$("#contact").val(data.contact);
			$("#phone").val(data.phone);
			$("#fax").val(data.fax);
			$("#province").val(data.province);
			$("#address").val(data.address);
			$("#email").val(data.email);
			$("#status").val(data.status);
			$("#innerCode").val(data.innerCode);
		});
	}

	function saveSupplier()
	{
		if ($("#fmSupplierInfo").valid() == false)
		{
			return;
		}

		App.block();
		
		var formParam =
		{
			"id" : $("#id").val(),
			"supplier_code" : $("#supplier_code").val(),
			"supplier_name" : $("#supplier_name").val(),
			"has_system" : $("#has_system").val(),
			"canPrint" : $("#canPrint").val(),
			"contact" : $("#contact").val(),
			"phone" : $("#phone").val(),
			"fax" : $("#fax").val(),
			"email" : $("#email").val(),
			"province" : $("#province").val(),
			"address" : $("#address").val(),
			"status" : $("#status").val(),
			"innerCode" : $("#innerCode").val()
		};
		$.post("supplier/supplierSave.do", formParam, function(data)
		{
			App.alert(data);

			App.unblock();
		});
	}
	
	function backToList()
	{
		App.loadPage("supplier/supplierPage.do");
	}
</script>
