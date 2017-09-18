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
			<a href="#">经销商管理</a>
		</li>
	</ul>
</div>

<!-- END PAGE HEADER-->

<div class="row-fluid">
	<div class="portlet box blue tabbable">
		<div class="portlet-title">
			<div class="caption">
				<i class="icon-reorder"></i>
				<span class="hidden-480">经销商信息</span>
			</div>
		</div>

		<div class="portlet-body form">
			<div class="portlet-tabs">
				<div class="tab-content">
					<div class="active">
						<!-- BEGIN FORM-->
						<form action="#" id="fmDealerInfo" class="form-horizontal" style="padding-top:20px;">
							<div class="row-fluid">
								<div class="span6 ">
									<div class="control-group">
										<label class="control-label">
											经销商编码
											<span class="required">*</span>
										</label>
										<div class="controls">
											<input type="hidden" name="id" id="id" value="${id }" />
											<input type="text" placeholder="必填" class="m-wrap span12" id="dealer_code" name="dealer_code" />
										</div>
									</div>
								</div>
								<div class="span6">
									<div class="control-group">
										<label class="control-label">
											税号
											<span class="required">*</span>
										</label>
										<div class="controls">
											<input type="text" id="revenueNO" name="revenueNO" class="m-wrap span12" />
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
											<input type="text" placeholder="必填" class="m-wrap span12" id="dealer_name" name="dealer_name" />
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
								<div class="span6">
									<div class="control-group">
										<label class="control-label">
											区号
										</label>
										<div class="controls">
											<input type="text" placeholder="请填写正确的区号" class="m-wrap span12" id="areaCode" name="areaCode" />
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
											电话
										</label>
										<div class="controls">
											<input type="text" placeholder="请填写正确的电话" id="tel" name="tel" class="m-wrap span12" />
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
							</div>
							<div class="form-actions">
								<a class="btn blue saveDealer"> <i class="icon-ok"></i> 保存 </a>
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
		doGetDealerInfo();

		$(".saveDealer").bind("click", function()
		{
			saveDealer();
		});


		$(".cancelSave").bind("click", function()
		{
			backToList();
		});

		$("#fmDealerInfo").validate(
		{
			rules :
			{
				dealer_code :
				{
					required : true
				},
				dealer_name :
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
				tel :
				{
					phone : true
				},
				revenueNO :
				{
					required : true
				}
			}
		});
	});

	function doGetDealerInfo()
	{
		var id = $("#id").val();
		if (id == undefined || id == "")
		{
			$("#id").val(0);
			return;
		}
		var url = "dealer/dealerEdit.do?id=" + id;
		$.get(url, function(data, status)
		{
			$("#id").val(data.id);
			$("#dealer_code").val(data.dealer_code);
			$("#dealer_name").val(data.dealer_name);
			$("#contact").val(data.contact);
			$("#phone").val(data.phone);
			$("#areaCode").val(data.areaCode);
			$("#tel").val(data.tel);
			$("#province").val(data.province);
			$("#address").val(data.address);
			$("#revenueNO").val(data.revenueNO);
			$("#status").val(data.status);
			$("#innerCode").val(data.innerCode);
		});
	}

	function saveDealer()
	{
		if ($("#fmDealerInfo").valid() == false)
		{
			return;
		}

		App.block();
		var formParam =
		{
			"id" : $("#id").val(),
			"dealer_code" : $("#dealer_code").val(),
			"dealer_name" : $("#dealer_name").val(),
			"contact" : $("#contact").val(),
			"phone" : $("#phone").val(),
			"areaCode" : $("#areaCode").val(),
			"tel" : $("#tel").val(),
			"province" : $("#province").val(),
			"address" : $("#address").val(),
			"revenueNO" : $("#revenueNO").val(),
			"status" : $("#status").val(),
			"innerCode" : $("#innerCode").val()
		};
		$.post("dealer/dealerSave.do", formParam, function(data)
		{
			if(data == "1")
			{
				App.alert("经销商信息保存成功！");
			}
			else if(data == "2")
			{
				App.alert("经销商编码或税号已存在，请重新填写！");
			}
			else if(data == "3")
			{
				App.alert("经销商信息更新成功！");
			}
			else
			{
				App.alert("经销商信息保存失败！");
			}
			
			App.unblock();
		});
	}
	
	function backToList()
	{
		App.loadPage("dealer/dealerPage.do");
	}
</script>
