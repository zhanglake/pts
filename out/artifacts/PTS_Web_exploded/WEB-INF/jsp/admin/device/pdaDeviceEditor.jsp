<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!-- BEGIN PAGE HEADER-->
<div class="row-fluid">
	<h3 class="page-title">
	</h3>

	<ul class="breadcrumb">
		<li>
			<a href="#">基础数据</a>
			<span class="icon-angle-right"></span>
		</li>
		<li>
			<a href="#">PDA终端信息</a>
		</li>
	</ul>
</div>

<!-- END PAGE HEADER-->

<div class="row-fluid">
	<div class="portlet box blue tabbable">
		<div class="portlet-title">
			<div class="caption">
				<i class="icon-reorder"></i>
				<span class="hidden-480">PDA终端信息</span>
			</div>
		</div>

		<div class="portlet-body form">
			<div class="portlet-tabs">
				<div class="tab-content">
					<div class="active">
						<!-- BEGIN FORM-->
						<form action="#" id="fmPDADeviceInfo" class="form-horizontal" style="padding-top:20px;">
							<div class="row-fluid">
								<div class="span6 ">
									<div class="control-group">
										<label class="control-label">
											终端编号
											<span class="required">*</span>
										</label>
										<div class="controls">
											<input type="hidden" name="id" id="id" value="${id }" />
											<input type="hidden" name="company_id" id="company_id" value="${companyId }" />
											<input type="text" class="m-wrap span12" id="deviceNo" name="deviceNo" />
										</div>
									</div>
								</div>
								
							</div>
							<div class="row-fluid">
								<div class="span6 ">
									<div class="control-group">
										<label class="control-label">
											领用人
											<span class="required">*</span>
										</label>
										<div class="controls">
											<input type="text" class="m-wrap span12" id="register" name="register" />
										</div>
									</div>
								</div>
							</div>
							<div class="row-fluid">
								<div class="span6 ">
									<div class="control-group">
										<label class="control-label">
											领用日期
											<span class="required">*</span>
										</label>
										<div class="controls">
											<input type="date" class="m-wrap span12 date" id="regDate" name="regDate" />
										</div>
									</div>
								</div>
							</div>
							<div class="row-fluid">
								<div class="span6 ">
									<div class="control-group">
										<label class="control-label">
											库位
											<span class="required">*</span>
										</label>
										<div class="controls">
											<select class="m-wrap span12" id="stockId" name="stockId">
												${stockSelect}
											</select>
										</div>
									</div>
								</div>
							</div>
							<div class="row-fluid">
								<div class="span6 ">
									<div class="control-group">
										<label class="control-label">
											IP地址
											<span class="required">*</span>
										</label>
										<div class="controls">
											<input type="text" class="m-wrap span12" id="ip" name="ip" />
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
							</div>
							
							<div class="row-fluid">
								<div class="span6 ">
									<div class="control-group">
										<label class="control-label">备注</label>
										<div class="controls">
											<input type="text" id="remark" name="remark" class="m-wrap span12" />
										</div>
									</div>
								</div>
							</div>
							<div class="form-actions">
								<a class="btn blue savePDA"> <i class="icon-ok"></i> 保存 </a>
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
<script src="<c:url value="/media/fm/bootstrap-datepicker.js"/>" type="text/javascript"></script>

<script type="text/javascript">
	var $table = App.initTable($("#tbList"));
	var id = $("#id").val();

	$().ready(function()
	{
		doGetPdaDeviceInfo();

		$(".savePDA").bind("click", function()
		{
			savePDA();
		});


		$(".cancelSave").bind("click", function()
		{
			backToList();
		});

		$("#fmPDADeviceInfo").validate(
		{
			rules :
			{
				deviceNo :
				{
					required : true
				},
				stockId :
				{
					required : true
				},
				register :
				{
					required : true
				},
				regDate :
				{
					required : true
				},
				ip :
				{
					required : true
				}
			}
		});
	});

	
	function doGetPdaDeviceInfo()
	{
		var id = $("#id").val();
		
		if(id == undefined || id == ""){
			$("#id").val(0);
			return;
		}
		
		var url = "device/pdaEdit.do?id=" + id;
		$.get(url, function(data, status)
		{
			$("#id").val(data.id);
			$("#company_id").val(data.companyId);
			$("#deviceNo").val(data.deviceNo);
			$("#register").val(data.register);
			$("#regDate").val(data.regDate);
			$("#ip").val(data.ip);
			$("#status").val(data.status);
			$("#remark").val(data.remark);
			$("#stockId").val(data.stock.id);
		});
	}

	function savePDA()
	{
		if ($("#fmPDADeviceInfo").valid() == false)
		{
			return;
		}

		App.block();
		//serialize只序列化可用的
		//$("#stCd").attr("disabled", false);
		var formParam =
		{
			"id" : $("#id").val(),
			"companyId" : $("#company_id").val(),
			"deviceNo" : $("#deviceNo").val(),
			"stock.id": $("#stockId").val(),
			"register" : $("#register").val(),
			"regDate" : $("#regDate").val(),
			"ip" : $("#ip").val(),
			"status" : $("#status").val(),
			"remark" : $("#remark").val()
		};
		$.post("device/pdaSave.do", formParam, function(data)
		{
			App.alert(data);

			App.unblock();
		});
	}
	
	function backToList()
	{
		App.loadPage("device/pdaDevicePage.do");
	}
</script>
