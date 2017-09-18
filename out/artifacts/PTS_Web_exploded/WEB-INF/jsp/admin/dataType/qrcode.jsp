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
			<a href="#">${typeName}</a>
		</li>
	</ul>
</div>

<!-- END PAGE HEADER-->

<!-- 列表开始 -->
<div class="row-fluid">
	<div class="span12 portlet box blue">
		<div class="portlet-title">
			<div class="caption">
				<i class="icon-globe"></i><span class="titleExcel">${typeName}列表</span>
			</div>
			<div class="actions">
			</div>
		</div>

		<div class="portlet-body">
			<div id="table-part">
			
			</div>
			<div id="formEdit" class="modal hide fade" tabindex="-1" data-backdrop="static" data-keyboard="false">
				<div class="modal-header">
					<button data-dismiss="modal" class="close" type="button"></button>
					<h3 class="page-title" id="tileBiao">
						${typeName}
					</h3>
				</div>
				<form action="#" id="fmDataEdit" class="form-horizontal">
					<div class="modal-body">
						<input type="hidden" name="tpid" id="tpid" value="${typeID}" />
						<input type="hidden" name="dctcd" id="dctcd" value="" />

						<div class="control-group">
							<label class="control-label">
								编码
								<span class="required">*</span>
							</label>
							<div class="controls">
								<input type="text" class="form-control" id="tpcd" name="tpcd" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">
								名称
								<span class="required">*</span>
							</label>
							<div class="controls">
								<input type="text" class="form-control" name="tpnm" id="tpnm" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">
								长度
								<span class="required">*</span>
							</label>
							<div class="controls">
								<input type="number" class="form-control" name="def1" id="def1" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">
								宽度
								<span class="required">*</span>
							</label>
							<div class="controls">
								<input type="number" class="form-control" id="def2" name="def2" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">
								状态
								<span class="required">*</span>
							</label>
							<div class="controls">
								<select id="sts" name="sts">
									${statusSelect}
								</select>
							</div>
						</div>
					</div>

					<div class="modal-footer">
						<button type="button" data-dismiss="modal" class="btn red">
							取消
						</button>
						<button type="button" onclick="doSave()" class="btn green">
							保存
						</button>
					</div>
				</form>
			</div>


		</div>

	</div>
</div>

<script src="<c:url value="/media/fm/jquery.validate.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.validate.messages_cn.js"/>" type="text/javascript"></script>

<script type="text/javascript">
	var $table = App.initTable($("#tbList"));

	$().ready(function()
	{
		App.loadActions("QRCodeSize", loadActionsBack);

		function loadActionsBack(){
			$(".doSearch").bind("click", function()
			{
				doSearch();
			});
			
			$(".doAddNew").bind("click", function()
			{
				doAddNew();
			});
	
			$(".doEdit").bind("click", function()
			{
				doEdit();
			});
			
		}

		$("#fmDataEdit").validate(
		{
			rules :
			{
				tpcd :
				{
					required : true
				},
				tpnm :
				{
					required : true
				},
				def1 : 
				{
					required : true
				},
				def2 : 
				{
					required : true
				}
			}
		});

		doSearch();
	});

	function doSearch()
	{
		App.block();
		$("#table-part").empty();
		var table = "<table id='tbList' class='table table-striped table-bordered table-hover table-full-width'></table>";
		$("#table-part").html(table);
		$table = $("#tbList").bootstrapTable({
			 url: "sysDataType/dataList.do?type=" + $("#tpid").val(),
			 pagination: true,
			 sidePagination: "server",
			 clickToSelect: true,
			 singleSelect: true ,
			 columns: [	
				{
					checkbox: true
				},
				{
					title : "序号",
					field : "dctcd",
					visible : false
					
				},
				{
					title : "编码",
					field : "tpcd"
				},
				{
					title : "名称",
					field : "tpnm"
				}, 
				{
					title : "长度",
					field : "def1"
				}, 
				{
					title : "宽度",
					field : "def2"
				}, 
				{
					title : "状态",
					field : "stsName"
				}
			]
		});
		App.unblock();
	}

	function doAddNew()
	{
		$("#dctcd").val("");
		$("#tpcd").val("");
		$("#tpnm").val("");
		$("#def1").val("");
		$("#def2").val("");
		$("#sts").val(1);

		$("#formEdit").modal(
		{
			keyboard : true
		});
	}

	function doEdit()
	{
		var dctcd = App.getTableSelection($table).dctcd;
		if (dctcd == undefined)
		{
			App.alert("请选择一条记录！");
			return;
		}

		var url = "sysDataType/editData.do?dicCode=" + dctcd;
		$.get(url, function(data, status)
		{
			$("#dctcd").val(data.dctcd);
			$("#tpcd").val(data.tpcd);
			$("#tpnm").val(data.tpnm);
			$("#def1").val(data.def1);
			$("#def2").val(data.def2);
			$("#sts").val(data.sts);
		});

		$("#formEdit").modal(
		{
			keyboard : true
		});
	}

	function doSave()
	{
		if ($("#fmDataEdit").valid() == false)
		{
			return;
		}

		var formParam = $("#fmDataEdit").serialize();

		$.post("sysDataType/saveData.do", formParam, function(data)
		{
			App.alert(data);

			doSearch();

			$("#formEdit").modal("hide");
		});
	}
</script>
