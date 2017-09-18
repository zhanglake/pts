<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<link rel="stylesheet" type="text/css" href="<c:url value="/media/css/jquery.fileupload.css"/>" />
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
	<div class="span12 portlet box yellow">
		<div class="portlet-title">
			<div class="caption">
				<i class="icon-reorder"></i>查询条件
			</div>
		
			<div class="actions">
				
			</div>
		</div>
		<div class="portlet-body form form-horizontal">
			<input type="hidden" id="orderId" value="${orderId }" />
			<div class="control-group">
				<label class="control-label">
					模糊检索：
				</label>
				<div class="controls">
					<div class="input-append">
						<input type="text" placeholder="SAP号、编号、名称或类型关键字" class="m-wrap" id="searchParam" />
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
				<i class="icon-globe"></i><span class="titleExcel">物品列表</span>
			</div>
		</div>

		<div class="portlet-body" id="table-part">
			<table id='tbList' class='table table-striped table-bordered table-hover table-full-width'></table>
		</div>
	</div>
</div>

<div id="updateModel" class="modal hide fade" data-backdrop="static">
	<div class="modal-header">
		<button data-dismiss="modal" class="close" type="button"></button>
		<h3 class="page-title" id="modal-header"></h3>
	</div>
	<div class="modal-body">
		<table id="updateRecord" class="table table-bordered table-hover">
			<thead>
				<tr>
					<th data-field="usrNm">
						操作人
					</th>
					<th data-field="logInfo">
						修改信息
					</th>
					<th data-field="createTime">
						修改时间
					</th>
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

<script src="<c:url value="/media/fm/select2.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.validate.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.validate.messages_cn.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/js/config.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.ui.widget.fileupload.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.iframe-transport.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.fileupload.js"/>" type="text/javascript"></script>	
<script src="<c:url value="/media/fm/jquery.fileupload-process.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.fileupload-validate.js"/>" type="text/javascript"></script>

<script type="text/javascript">
	var $table;

	$().ready(function()
	{
		App.loadActions("ProductInfo", loadActionsBack);

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
			
			$(".doExport").bind("click", function()
			{
				doExport();
			});
			
			$(".doImport").bind("click", function()
			{
				$("#importFile").text("");
				$("#toImport").val("");
				$(".text-error").text("");
				$("#import-modal").modal({keyboard: true});
			});
		}
		doSearch();
		
		$("#packageID").select2();
		$("#products").select2();
		
		$(".input-append input").keypress(function (e) {
			if (e.which == 13) {
				doSearch();
			}
		});
		
		$("#fileupload").fileupload({  
			url: "product/importXls.do",
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
	});

	function doSearch()
	{
		App.block();
		
		/*var table = "<table id='tbList' class='table table-striped table-bordered table-hover table-full-width'></table>";
		$("#table-part").empty();
		$("#table-part").html(table);*/
		var orderId = $("#orderId").val();
		var searchParam = $("#searchParam").val();
		
		$table = $("#tbList").bootstrapTable("destroy").bootstrapTable({
			 url: "product/productList.do?searchParam=" + searchParam + "&orderId=" + orderId,
			 pagination: true,
			 sidePagination: "server",
			 clickToSelect: true,
			 singleSelect: true ,
			 columns: [
				  {
					  checkbox: true
				  },
				  {
				  	title: "id",
				  	field: "id",
				  	align: "center",
				  	valign: "middle",
				  	visible: false,
				  	"class": "width-40"
				  },
				  {
                      title: "SAP号",
                      field: "sapNo"
                  },
                  {
                      title: "物品编号",
                      field: "code"
                  }
                  ,
                  {
                      title: "物品名称",
                      field: "name"
                  }
                  ,
                  {
                      title: "物品类型编码",
                      field: "category"
                  }
                  ,
                  {
                      title: "物品类型名称",
                      field: "categoryName"
                  }
                  ,
                  {
                      title: "包装规则",
                      field: "packageName"
                  },
                  {
                      title: "规格型号",
                      field: "specNo"
                  },
                  {
                  	title: "售价",
                  	field: "price"
                  },
                  {
                  	title: "积分",
                  	field: "points"
                  },
                  {
                  	title: "状态",
                  	field: "stsName"
                  },
                  {
					title : "修改记录",
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
		App.loadPage("product/editPage.do?proId=");
	}
	
	function doEdit()
	{
		var id = App.getTableSelection($table).id;
		if (id == undefined)
		{
			App.alert("请选择物品！");
			return;
		}
		App.loadPage("product/editPage.do?proId=" + id);
	}
	
	window.operateEvents = 
	{
	    'click .view': function (e, value, row, index) 
	    {
	        var productId = row.id;
			var $updateRecord = App.initTable($("#updateRecord"));
			
			$("#updateModel>.modal-header>.page-title").text(row.name + "修改记录");
			
			$.get("product/updateRecord.do?productId=" + productId, function (data, status)
			{
				$updateRecord.bootstrapTable("load", data);
			});
			
			$("#updateModel").modal({keyboard: true});
	    }
	};
	
	function doExport()
	{
		var orderId = $("#orderId").val();
		var searchParam = $("#searchParam").val();
		App.executeExport("product/exportList.do", {searchParam: searchParam, orderId: orderId, title: config.title_xls_product});
	}
	
	function downloadExcel(){
    	 $.ajax({
		     url:"product/downloadTpl.do",
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
		     url:"product/doImport.do?file=" + fileName,
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
