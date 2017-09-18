<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<link rel="stylesheet" type="text/css" href="<c:url value="/media/css/jquery.fileupload.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/media/css/multi-select-metro.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/media/css/chosen.css"/>" />

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
			<a href="#">企业管理</a>
		</li>
	</ul>
</div>

<!-- END PAGE HEADER-->

<div class="row-fluid">
	<div class="portlet box blue tabbable">
		<div class="portlet-title">
			<div class="caption">
				<i class="icon-reorder"></i>
				<span class="hidden-480">企业信息</span>
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
						<form action="#" id="fmCompanyInfo" class="form-horizontal" style="padding-top:20px;">
							<!--<h3 class="form-section">
								基本信息
							</h3>
							-->
							<div class="row-fluid">
								<div class="span6 ">
									<div class="control-group">
										<label class="control-label">
											企业编码
											<span class="required">*</span>
										</label>
										<div class="controls">
											<input type="hidden" name="id" id="id" value="${id }" />
											<input type="text" placeholder="必填项" class="m-wrap span12" id="company_code" name="company_code" />
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
											<input type="text" placeholder="必填项" class="m-wrap span12" id="contact" name="contact" />
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
											<input type="text" placeholder="必填项" class="m-wrap span12" id="name" name="name" />
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
											<input type="text" placeholder="必填项, 请输入正确的联系电话" class="m-wrap span12" id="phone" name="phone" />
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
											传真
										</label>
										<div class="controls">
											<input type="text" placeholder="请输入正确的传真" class="m-wrap span12" id="fax" name="fax" />
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
											详细地址
										</label>
										<div class="controls">
											<input type="text" id="address" name="address" class="m-wrap span12" />
										</div>
									</div>
								</div>
							</div>
							<div class="row-fluid">
								<div class="span6">
									<div class="contorl-group">
										<label class="control-label">LOGO</label>
										<div class="controls">
											<input type="hidden" class="m-wrap span12" name="logo" id="logo" />
											<img id="logo_img" style="width: 200px; height: 40px;" />
											<span style="margin-top: 10px; margin-left: 24px;" class="btn fileinput-button">
												<span>上传LOGO</span>
												<input id="fileupload" type="file" name="files[]" multiple>
											</span>
											<div style="font-family: 'Microsoft YaHei'; padding-top: 3px;" class="text-error">
											</div>
										</div>
									</div>
								</div>
								<div class="span6">
									<div class="control-group">
										<label class="control-label">备注</label>
										<div class="controls">
											<input type="text" id="remark" name="remark" class="m-wrap span12" />
										</div>
									</div>
								</div>
							</div>
							<div class="form-actions">
								<a class="btn blue saveCompany"> <i class="icon-ok"></i> 保存 </a>
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
<script src="<c:url value="/media/fm/jquery.multi-select.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/chosen.jquery.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.validate.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.validate.messages_cn.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/additional-methods.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.ui.widget.fileupload.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.iframe-transport.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.fileupload.js"/>" type="text/javascript"></script>	
<script src="<c:url value="/media/fm/jquery.fileupload-process.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.fileupload-validate.js"/>" type="text/javascript"></script>

<script type="text/javascript">
	var $table = App.initTable($("#tbList"));
	var id = $("#id").val();

	$().ready(function()
	{
		//setCompanyCd();
		
		doGetCompanyInfo();
		$("#dealer").multiSelect();

		$(".saveCompany").bind("click", function()
		{
			saveCompany();
		});

		$(".cancelSave").bind("click", function()
		{
			backToList();
		});
		
		$(".distributeDealer").bind("click", function()
		{
			distributeDealer();
		});

		$("#fmCompanyInfo").validate(
		{
			rules :
			{
				company_code :
				{
					required : true
				},
				name :
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
				}
			}
		});
		
		$("#fileupload").fileupload({  
			url: "common/uploadImg.do",
		    acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
		    done: function (e, data) {//设置文件上传完毕事件的回调函数 
		     	data = data.result;
		      	if (typeof(data.message) != undefined) {
					$("#logo").val(data);
		            $("#logo_img").attr("src", "<c:url value='/upload/logo/" + data + "'/>");
		        }
		    }
		}).on("fileuploadprocessalways", function (e, data){
			var index = data.index, file = data.files[index];
			if (file.error) {
				$(".text-error").html("仅支持JPG, GIF, PNG格式");
			}
		});
	});

	function setCompanyCd()
	{
		if ($("#stCd").val() != "")
		{
			$("#stCd").attr("disabled", true);
		}
	}
	
	function doGetCompanyInfo()
	{
		var id = $("#id").val();
		if (id == undefined || id == "")
		{
			$("#id").val(0);
			$("#logo_img").attr("src", "");
			return;
		}
		var url = "company/companyEdit.do?id=" + id;
		$.get(url, function(data, status)
		{
			$("#id").val(data.id);
			$("#company_code").val(data.company_code);
			$("#name").val(data.name);
			$("#contact").val(data.contact);
			$("#phone").val(data.phone);
			$("#fax").val(data.fax);
			$("#province").val(data.province);
			$("#address").val(data.address);
			$("#status").val(data.status);
			$("#remark").val(data.remark);
			$("#logo").val(data.logo);
			$("#logo_img").attr("src", "<c:url value='/upload/logo/" + data.logo + "'/>");
		});
	}

	function saveCompany()
	{
		if ($("#fmCompanyInfo").valid() == false)
		{
			return;
		}

		App.block();
		//serialize只序列化可用的
		//$("#stCd").attr("disabled", false);
		var formParam =
		{
			"id" : $("#id").val(),
			"company_code" : $("#company_code").val(),
			"name" : $("#name").val(),
			"contact" : $("#contact").val(),
			"phone" : $("#phone").val(),
			"fax" : $("#fax").val(),
			"province" : $("#province").val(),
			"address" : $("#address").val(),
			"status" : $("#status").val(),
			"remark" : $("#remark").val(),
			"logo" : $("#logo").val()
		};
		$.post("company/companySave.do", formParam, function(data)
		{
			App.alert(data);

			App.unblock();
			//重新设置
			//setCompanyCd();
		});
	}
	
	function distributeDealer()
	{
		var id = $("#id").val();
		if (id == undefined || id == "" || id == 0)
		{
			App.alert("请先保存企业信息！");
			return;
		}
		var dealer = $("#dealer").val();
		if (dealer == null)
		{
			dealer = "";
		}
		else
		{
			dealer = dealer.join(",");
		}
		
		var data = 
		{
			companyId : id,
			dealers : dealer
		};
		
		App.block();
		$.post("company/distributeDealer.do", data, function(data) 
		{
			App.alert(data);

			App.unblock();
		});
	}
	
	function backToList()
	{
		App.loadPage("company/comPage.do");
	}
</script>
