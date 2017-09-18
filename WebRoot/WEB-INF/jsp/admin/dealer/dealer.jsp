<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/media/css/jquery.fileupload.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/media/css/daterangepicker.css"/>" />

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
	<div class="span12 portlet box yellow">
		<div class="portlet-title">
			<div class="caption">
				<i class="icon-reorder"></i>查询条件
			</div>
			<div class="tools">
				<a href="javascript:;" class="collapse"></a>
			</div>
			<div class="actions">
			</div>
		</div>
		<div class="portlet-body form form-horizontal">
			<div class="control-group">
				<label class="control-label">
					模糊检索：
				</label>
				<div class="controls">
					<div class="input-append">
						<input type="text" placeholder="编码或名称关键字" class="m-wrap" id="searchParam" />
						<span class="add-on doSearch">
							<i class="icon-search"></i>
						</span>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- 列表开始 -->
<div class="row-fluid">
	<div class="span12 portlet box blue">
		<div class="portlet-title">
			<div class="caption">
				<i class="icon-globe"></i><span class="titleExcel">经销商列表</span>
			</div>
		</div>

		<div class="portlet-body" id="table-part">
			<table id="tbList" class="table table-striped table-bordered table-hover table-full-width">
			</table>
		</div>
	</div>
</div>

<div id="son-dealers" class="modal hide fade" data-backdrop="static" style="width: 700px; left: 45%;">
	<div class="modal-header">
		<button data-dismiss="modal" class="close" type="button"></button>
		<h3 class="page-title" id="modal-header"></h3>
	</div>
	<div class="modal-body">
		<table id="item-table" class="table table-striped table-bordered table-hover table-full-width">
			<thead>
				<tr>
					<th data-field="CODE">编码</th>
					<th data-field="NAME">名称</th>
					<th data-field="CONTACT">联系人</th>
					<th data-field="TEL">联系电话</th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="modal-footer">
		<button type="button" data-dismiss="modal" class="btn green">
			确定
		</button>
	</div>
</div>

<div id="import-modal" class="modal hide fade" data-backdrop="static">
	<div class="modal-header">
		<button data-dismiss="modal" class="close" type="button"></button>
		<h3 class="page-title" id="modal-header">批量导入</h3>
	</div>
	<div class="modal-body">
		<div class="row-fluid form">
			<div class="span12 form-horizontal">
				<div class="control-group">
					<a href="javascript:downloadExcel();">点击下载要导入的Excel模板</a>
				</div>
			</div>
		</div>
		<div class="row-fluid form">
			<div class="span12 form-horizontal">
				<div class="contorl-group">
					<label class="control-label">请选择要导入的文件：</label>
					<div class="controls">
						<span id="importFile"></span>
						<input type="hidden" id="toImport" />
						<span class="btn fileinput-button">
							<span>选择文件</span>
							<input id="fileupload" type="file" name="files[]" multiple>
						</span>
						<button class="btn green" onclick="doImport()">导入</button>
						<div style="font-family: 'Microsoft YaHei'; padding-top: 3px;" class="text-error"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal-footer">
		<button type="button" data-dismiss="modal" class="btn">
			取消
		</button>
	</div>
</div>

<script src="<c:url value="/media/fm/jquery.ui.widget.fileupload.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.iframe-transport.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.fileupload.js"/>" type="text/javascript"></script>	
<script src="<c:url value="/media/fm/jquery.fileupload-process.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.fileupload-validate.js"/>" type="text/javascript"></script>

<script type="text/javascript">

	var $itemTable = App.initTable($("#item-table"));
	var $table;
	
	$().ready(function()
	{
		doSearch();
		App.loadActions("DealerManagerComp", loadActionsBack);

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
			
			$(".doImport").bind("click", function()
			{
				$("#importFile").text("");
				$("#toImport").val("");
				$(".text-error").text("");
				$("#import-modal").modal({keyboard: true});
			});
			
			$("#fileupload").fileupload({  
				url: "dealer/importXls.do",
			    acceptFileTypes: /(\.|\/)(xlsx|xls)$/i,
			    done: function (e, data) {//设置文件上传完毕事件的回调函数 
			     	data = data.result;
			      	if (typeof(data.message) != undefined) 
			      	{
			      		var dt;
			      		if(isExcel2003(data) == "2007")
			      		{
			      			dt = data.split(".xlsx");
			      			$("#importFile").text($.trim(dt[0]) + ".xlsx");
							$("#toImport").val($.trim(dt[1]) + ".xlsx");
			      		}
			      		else if(isExcel2003(data) == "2003")
			      		{
			      			dt = data.split(".xls");
			      			$("#importFile").text($.trim(dt[0]) + ".xls");
							$("#toImport").val($.trim(dt[1]) + ".xls");
			      		}
			        }
			    }
			}).on("fileuploadprocessalways", function (e, data){
				var index = data.index, file = data.files[index];
				if (file.error) {
					$(".text-error").html("请上传Excel文档.");
				}
			});
		}
		
		$(".input-append input").keypress(function (e) {
			if (e.which == 13) {
				doSearch();
			}
		});		
	});
	
	function doSearch()
	{
		App.block();
		/*$("#table-part").empty();
		var table = "<table id='tbList' class='table table-striped table-bordered table-hover table-full-width'></table>";
		$("#table-part").html(table);*/
		$table = $("#tbList").bootstrapTable("destroy").bootstrapTable({
			 url: "dealer/dealerList.do?searchParam=" + $("#searchParam").val(),
			 pagination: true,
			 sidePagination: "server",
			 clickToSelect: true,
			 singleSelect: true ,
			 columns: [	
				{
					checkbox: true
				},
				{
					title : "id",
					field : "id",
					visible : false
					
				},
				{
					title : "税号",
					field : "revenueNO"
				},
				{
					title : "编码",
					field : "dealer_code"
				}, 
				{
					title : "名称",
					field : "dealer_name"
				}, 
				{
					title : "联系人",
					field : "contact"
				}, 
				{
					title : "联系电话",
					field : "phone"
				}, 
				{
					title : "区号",
					field : "areaCode"
				}, 
				{
					title : "电话",
					field : "tel"
				}, 
				{
					title : "地址",
					field : "address"
				},
				{
					title : "子经销商",
					events : "operateEvents", 
					formatter : function(value, row, index) 
					{
						return '<a class="view" href="javascript:void(0)">查看</a>';
					}
				}
			]
		});
		App.unblock();
	}
	
	function doAddNew()
	{
		App.loadPage("dealer/dealerEditPage.do?id=");
	}

	function doEdit()
	{
		var id = App.getTableSelection($table).id;
		if (id == undefined)
		{
			App.alert("请选择一条记录！");
			return;
		}
		App.loadPage("dealer/dealerEditPage.do?id=" + id);
	}
	
	window.operateEvents = 
	{
	    'click .view': function (e, value, row, index) 
	    {
			var dealerCode = row.dealer_code;
			var dealerName = row.dealer_name;
			
			$("#son-dealers>.modal-header>.page-title").text(dealerName);
			$.get("dealer/sonDealers.do?code=" + dealerCode, function (data, status)
			{
				$itemTable.bootstrapTable("load", data);
			});
			
			$("#son-dealers").modal({keyboard: true});
	    }
	};
	
	function downloadExcel(){
    	 $.ajax({
		     url:"dealer/downloadTpl.do",
		     success : function(url) 
		     {	
		         if(url != "")
		         {	
		            open(url, "_self");
		         }
		     },
		     error : function(response) {
				App.alert("网络异常,请联系管理员");
		     }
	    }); 
    }
    
    function doImport()
    {
    	var fileName = $("#toImport").val();
    	if(null == fileName || "" == fileName)
    	{
    		App.alert("请选择要导入的文件！");
    		return;
    	}
    
    	App.block();
    	$.ajax({
		     url:"dealer/doImport.do?file=" + fileName,
		     success : function(data) 
		     {	
		         if(data == 1)
		         {
		         	App.alert("导入成功！");
		         	doSearch();
		         	$("#import-modal").modal("hide");
		         	App.unblock();
		         }
		     },
		     error : function(response) {
		     	App.alert("网络异常,请联系管理员");
		     	App.unblock();
		     }
	    });
    }
    
    function isExcel2003(data)
    {
    	var reg2003 = /(\.|\/)(xls)$/i; // /^.+\\.(?i)(xls)$/;
    	var reg2007 = /(\.|\/)(xlsx)$/i; ///^.+\\.(?i)(xlsx)$/;
    	if(reg2003.test(data))
    	{
    		return "2003";
    	}
    	else if(reg2007.test(data))
    	{
    		return "2007";
    	}
    	else
    	{
    		return "0";
    	}
    }
	
</script>
