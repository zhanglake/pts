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
	<div class="span12">
		<div class="alert alert-block alert-info fade in">
			<h5>当前包装规则 编号：<b>${rule.code }</b>, 名称：<b>${rule.name }</b></h5>
		</div>
	</div>
</div>

<!-- 列表开始 -->
<div class="row-fluid">
	<div class="span12 portlet box blue">
		<div class="portlet-title">
			<div class="caption">
				<i class="icon-globe"></i><span class="titleExcel">包装设置</span>
			</div>
			<div class="actions">
				<button class="btn red doAddNew">
					新增
					<i class="m-icon-swapright m-icon-white"></i>
				</button>
				<button class="btn red backToList">
					返回
					<i class="m-icon-swapright m-icon-white"></i>
				</button>
			</div>
		</div>

		<div class="portlet-body">
			<table id="tbList" class="table table-striped table-bordered table-hover table-full-width" data-click-to-select="true" data-single-select="true">
				<thead>
					<tr>
						<th data-field="ID" data-visible="false">
						</th>
						<th data-field="PKGLEVEL">
							包装层级
						</th>
						<th data-field="CODE">
							包装编号(新)
						</th>
						<th data-field="CODE_OLD">
							包装编号(旧)
						</th>
						<th data-field="NAME">
							包装名称
						</th>
						<th data-field="PKGTYPENM">
							包装类型
						</th>
						<th data-field="SPECIFICATIONS">
							包装规格
						</th>
						<th data-field="CAPACITY">
							包装数量(个)
						</th>
						<th data-field="QRARER_SIZE">
							二维码尺寸
						</th>
						<th data-formatter="operateFormatter" data-events="operateEvents">
							操作
						</th>
					</tr>
				</thead>
			</table>
		</div>
		
		<div id="formEdit" class="modal hide fade" data-backdrop="static" data-keyboard="false">
			<div class="modal-header">
				<button data-dismiss="modal" class="close" type="button"></button>
				<h3 class="page-title" id="tileBiao">
					新增包装层级
				</h3>
			</div>
			<div class="modal-body">
				<form action="#" id="fmMapEdit" class="form-horizontal">
					<input type="hidden" value="0" id="id" name="id">
					<input type="hidden" value="${rule.id }" id="rule_id" name="rule_id">
					<div class="control-group">
						<label class="control-label">
							包装层级
							<span class="required">*</span>
						</label>
						<div class="controls">
							<input type="number" placehoder="必填项" class="form-control span8" id="pkgLevel" name="pkgLevel" />
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">
							包装
							<span class="required">*</span>
						</label>
						<div class="controls">
							<select id="package_id" name="package_id" class="span8">
								${sPackage }
							</select>
						</div>
					</div>
				</form>
			</div>

			<div class="modal-footer">
				<button type="button" data-dismiss="modal" class="btn red">
					取消
				</button>
				<button type="button" onclick="doSave()" class="btn green">
					保存
				</button>
			</div>
		</div>
	</div>
</div>

<script src="<c:url value="/media/fm/select2.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.validate.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.validate.messages_cn.js"/>" type="text/javascript"></script>

<script type="text/javascript">
	var $table = App.initTable($("#tbList"));
	var maxNum = 0;

	$().ready(function()
	{
		$(".doSearch").bind("click", function()
		{
			doSearch();
		});
		
		$(".doAddNew").bind("click", function()
		{
			doAddNew();
		});
	
		$(".backToList").bind("click", function()
		{
			backToList();
		});
		
		$("#package_id").select2();
		
		$("#fmMapEdit").validate(
		{
			rules :
			{
				pkgLevel :
				{
					required : true,
					number : true
				},
				package_id :
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
		
		$.get("package/packageGetByRuleID.do?ruleId=" + $("#rule_id").val(), function(data, status)
		{
			maxNum = data.length;
			$("#pkgLevel").val(maxNum+1);
			$table.bootstrapTable("load", data);
			App.unblock();
		});
	}
	
	
	function doAddNew()
	{
		$("#pkgLevel").attr("disabled", true);
		$("#formEdit").modal(
		{
			keyboard : true
		});
	}
	
	function doSave()
	{
		if ($("#fmMapEdit").valid() == false)
		{
			return;
		}
		
		$("#pkgLevel").attr("disabled", false);
		var formParam = $("#fmMapEdit").serialize();

		$.post("package/savePkgMap.do", formParam, function(data)
		{
			App.alert(data);

			doSearch();

			$("#formEdit").modal("hide");
		});
	}
	
	function backToList()
	{
		App.loadPage("package/multiPkg.do");
	}
	
	function operateFormatter(value, row, index)
	{
		if(index == (maxNum - 1))
		{
			return '<a class="delete" href="javascript:void(0)">删除</a>';
		}
		else
		{
			return '';
		}
	}
	
	//查看经销商
	window.operateEvents = 
	{
	    'click .delete': function (e, value, row, index) 
	    {
	    	var pkgId = row.ID;
			if (pkgId == undefined)
			{
				App.alert("操作错误，请稍后重试！");
				return;
			}
			if(confirm("确认删除该包装设置吗?") == false)
			{
				return;
			}
			App.block();
			$.post("package/deletePkgMap.do?ruleId=" + $("#rule_id").val() + "&pkgId=" + pkgId, function(data)
			{
				App.alert(data);
				App.unblock();
				doSearch();
			});
	    }
	};
	
</script>
