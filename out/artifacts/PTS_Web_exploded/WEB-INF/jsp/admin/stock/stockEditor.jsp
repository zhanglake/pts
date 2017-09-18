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
			<a href="#">库位管理</a>
		</li>
	</ul>
</div>

<!-- END PAGE HEADER-->

<div class="row-fluid">
	<div class="portlet box blue tabbable">
		<div class="portlet-title">
			<div class="caption">
				<i class="icon-reorder"></i>
				<span class="hidden-480">库位信息</span>
			</div>
		</div>

		<div class="portlet-body form">
			<div class="portlet-tabs">
				<div class="tab-content">
					<div class="active">
						<!-- BEGIN FORM-->
						<form action="#" id="fmStockInfo" class="form-horizontal" style="padding-top:20px;">
							<div class="row-fluid">
								<div class="span6 ">
									<div class="control-group">
										<label class="control-label">
											库位编码
											<span class="required">*</span>
										</label>
										<div class="controls">
											<input type="hidden" name="id" id="id" value="${id }" />
											<input type="text" class="m-wrap span12" id="code" name="code" />
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
											<input type="text" class="m-wrap span12" id="name" name="name" />
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
											所在地
										</label>
										<div class="controls">
											<input type="hidden" id="areaID" name="areaID" value="0" treePath="" />
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
											备注
										</label>
										<div class="controls">
											<input type="text" id="remark" name="remark" class="m-wrap span12" />
										</div>
									</div>
								</div>
							</div>
							<div class="form-actions">
								<a class="btn blue saveStock"> <i class="icon-ok"></i> 保存 </a>
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
<script src="<c:url value="/media/fm/jquery.lSelect.js"/>" type="text/javascript"></script>

<script type="text/javascript">
	var $table = App.initTable($("#tbList"));
	var $areaId = $("#areaID");
	var id = $("#id").val();

	$().ready(function()
	{
		//setDealerCd();
		doGetStockInfo();

		$(".saveStock").bind("click", function()
		{
			saveStock();
		});


		$(".cancelSave").bind("click", function()
		{
			backToList();
		});

		$("#fmStockInfo").validate(
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
				}
			}
		});
	});
	
	function doGetStockInfo()
	{
		var id = $("#id").val();
		if (id == undefined || id == "")
		{
			$("#id").val(0);
			$areaId.lSelect({
				url: "common/area.do"
			});
			return;
		}
		var url = "stock/stockEdit.do?id=" + id;
		$.get(url, function(data, status)
		{
			$("#id").val(data.id);
			$("#code").val(data.code);
			$("#name").val(data.name);
			$("#areaID").show();
			$("#areaID").val(data.area.id);
			$("#areaID").attr("treePath", data.area.treePath);
			$("#address").val(data.address);
			$("#remark").val(data.remark);
			$("#status").val(data.status);
			$areaId.lSelect({
				url: "common/area.do"
			});
		});
	}

	function saveStock()
	{
		if ($("#fmStockInfo").valid() == false)
		{
			return;
		}
		App.block();
		var formParam =
		{
			"id" : $("#id").val(),
			"code" : $("#code").val(),
			"name" : $("#name").val(),
			"area.id" : $("#areaID").val(),
			"address" : $("#address").val(),
			"status" : $("#status").val(),
			"remark" : $("#remark").val()
		};
		$.post("stock/stockSave.do", formParam, function(data)
		{
			App.alert(data);

			App.unblock();
		});
	}
	
	function backToList()
	{
		App.loadPage("stock/stockPage.do");
	}
</script>
