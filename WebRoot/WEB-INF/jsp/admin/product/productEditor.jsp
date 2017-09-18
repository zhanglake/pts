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
			<a href="#">物品管理</a>
		</li>
	</ul>
</div>

<!-- END PAGE HEADER-->

<div class="row-fluid">
	<div class="portlet box blue tabbable">
		<div class="portlet-title">
			<div class="caption">
				<i class="icon-reorder"></i>
				<span class="hidden-480">物品信息</span>
			</div>
		</div>

		<div class="portlet-body form">
			<div class="portlet-tabs">
				<div class="tab-content">
					<div class="active">
						<!-- BEGIN FORM-->
						<form action="#" id="fmProductEdit" class="form-horizontal">
							<input type="hidden" value="${id }" id="id" name="id">
							<div class="row-fluid">
								<div class="span6">
									<div class="control-group">
										<label class="control-label">
											SAP号
											<span class="required">*</span>
										</label>
										<div class="controls">
											<input type="text" placehoder="必填项" class="wrap span12" id="sapNo" name="sapNo" />
										</div>
									</div>
								</div>
								<div class="span6">
									<div class="control-group">
										<label class="control-label">
											物品名称
											<span class="required">*</span>
										</label>
										<div class="controls">
											<input type="text" placehoder="必填项" class="wrap span12" id="name" name="name" />
										</div>
									</div>
								</div>
							</div>
							<div class="row-fluid">
								<div class="span6">
									<div class="control-group">
										<label class="control-label">
											物品编号
											<span class="required">*</span>
										</label>
										<div class="controls">
											<input type="text" placehoder="必填项" class="wrap span12" id="code" name="code" />
										</div>
									</div>
								</div>
								<div class="span6">
									<div class="control-group">
										<label class="control-label">
											规格型号
										</label>
										<div class="controls">
											<input type="text" class="wrap span12" id="specNo" name="specNo" />
										</div>
									</div>
								</div>
							</div>
							<div class="row-fluid">
								<div class="span6">
									<div class="control-group">
										<label class="control-label">
											物品类型
										</label>
										<div class="controls">
											<select id="category" name="category" class="wrap span12">
												${sProductType}
											</select>
										</div>
									</div>
								</div>
								<div class="span6">
									<div class="control-group">
										<label class="control-label">
											售价
										</label>
										<div class="controls">
											<input type="number" class="wrap span12" id="price" name="price" />
										</div>
									</div>
								</div>
							</div>
							<div class="row-fluid">
								<div class="span6">
									<div class="control-group">
										<label class="control-label">
											包装规则
										</label>
										<div class="controls">
											<select id="packageID" name="packageID" class="wrap span12">
												${sPackageRule}
											</select>
										</div>
									</div>
								</div>
								<div class="span6">
									<div class="control-group">
										<label class="control-label">
											积分
										</label>
										<div class="controls">
											<input type="number" class="wrap span12" id="points" name="points" />
										</div>
									</div>
								</div>
							</div>
							<div class="row-fluid">
								<div class="span6">
									<div class="control-group">
										<label class="control-label">
											打印二维码
											<span class="required">*</span>
										</label>
										<div class="controls">
											<select id="printQR" name="printQR" class="wrap span12">
												<option value="1">是</option>
												<option value="0">否</option>
											</select>
										</div>
									</div>
								</div>
								<div class="span6">
									<div class="control-group">
										<label class="control-label">
											状态
											<span class="required">*</span>
										</label>
										<div class="controls">
											<select id="sts" name="sts" class="wrap span12">
												${sSts }
											</select>
										</div>
									</div>
								</div>
							</div>
							<div class="row-fluid">
								<div class="span6">
									<div class="control-group">
										<label class="control-label">
											二维码下发MS
											<span class="required">*</span>
										</label>
										<div class="controls">
											<select id="isIssued" name="isIssued" class="wrap span12">
												<option value="1">是</option>
												<option value="0">否</option>
											</select>
										</div>
									</div>
								</div>
							</div>
							<div id="edit-show">
								<div class="row-fluid">
									<div class="span6">
										<div class="control-group">
											<label class="control-label">
												创建时间
											</label>
											<div class="controls">
												<input type="text" id="createTime" class="wrap span12" disabled />
											</div>
										</div>
									</div>
								</div>
								<div class="row-fluid">
									<div class="span6">
										<div class="control-group">
											<label class="control-label">
												修改时间
											</label>
											<div class="controls">
												<input type="text" id="updateTime" class="wrap span12" disabled />
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="form-actions">
								<a class="btn blue saveProduct"> <i class="icon-ok"></i> 保存 </a>
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

<script src="<c:url value="/media/fm/jquery.validate.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.validate.messages_cn.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/additional-methods.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/select2.min.js"/>" type="text/javascript"></script>

<script type="text/javascript">

	$().ready(function()
	{
		doGetProduct();
		
		//$("#supplierID").select2();
		$("#packageID").select2();
		$("#edit-show").hide();

		$(".saveProduct").bind("click", function()
		{
			saveProduct();
		});

		$(".cancelSave").bind("click", function()
		{
			backToList();
		});

		$("#fmProductEdit").validate(
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
				sapNo :
				{
					required : true
				},
				price : 
				{
					number : true
				},
				printQR :
				{
					required : true
				},
				isIssued : 
				{
					required : true
				},
				sts : 
				{
					required : true
				}
			}
		});
	});

	function doGetProduct()
	{
		var id = $("#id").val();
		if (id == undefined || id == "")
		{
			$("#id").val(0);
			$("#category").val(1);
			$("#sts").val(1);
			$("#points").val(0);
			$("#price").val(0);
			//$("#supplierID").val("");
			$("#packageID").val("");
			$("#edit-show").hide();
			return;
		}
		var url = "product/editProduct.do?proId=" + id;
		$.get(url, function(data, status)
		{
			$("#id").val(data.id);
			$("#code").val(data.code);
			$("#name").val(data.name);
			$("#sapNo").val(data.sapNo);
			$("#category").val(data.category);
			$("#sts").val(data.sts);
			$("#points").val(data.points);
			$("#specNo").val(data.specNo);
			$("#price").val(data.price);
			$("#isIssued").val(data.isIssued);
			$("#createTime").val(data.createTime);
			$("#updateTime").val(data.updateTime);
			/*$("#supplierID").select2().select2("val", null);
			$("#supplierID").select2().select2("val", data.supplierID);*/
			$("#packageID").select2().select2("val", null);
			$("#packageID").select2().select2("val", data.packageID);
			
			$("#edit-show").show();
		});
	}

	function saveProduct()
	{
		if ($("#fmProductEdit").valid() == false)
		{
			return;
		}

		var formParam = $("#fmProductEdit").serialize();
		App.block();
		$.post("product/saveProduct.do", formParam, function(data)
		{
			if(data == "-1")
			{
				App.alert("产品编码已存在！");
			}
			else
			{
				if(data == "0")
				{
					App.alert("产品保存失败！");
				}
				else
				{
					App.alert("产品保存成功!");
					$("#id").val(data);
					doGetProduct();
				}
			}
			App.unblock();
		});
	}
	
	function backToList()
	{
		App.loadPage("product/productPage.do");
	}
</script>
