<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<link rel="stylesheet" type="text/css" href="<c:url value="/media/css/daterangepicker.css"/>" />
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
			<a href="#">申请二维码</a>
		</li>
	</ul>
</div>

<div class="row-fluid tabbable">
	<div class="span12 tabbable tabbable-custom">
		<ul class="nav nav-tabs">
			<li class="active"><a href="#portlet_tab1" data-toggle="tab">申请</a></li>
			<li><a href="#portlet_tab2" data-toggle="tab">申请记录</a></li>
		</ul>
		<div class="tab-content">
			<div class="tab-pane active" id="portlet_tab1">
				<div class="row-fluid">
					<div class="span12 portlet box blue">
						<div class="portlet-title">
							<div class="caption">
								<i class="icon-globe"></i><span class="titleExcel">申请二维码</span>
							</div>
						</div>
				
						<div class="portlet-body flip-scroll">
							<form action="#" id="applyForm" class="form-horizontal" style="padding-top:20px;">
								<div class="row-fluid">
									<div class="span6 ">
										<div class="control-group">
											<label class="control-label">
												物品
												<span class="required">*</span>
											</label>
											<div class="controls">
												<select id="product" name="product" class="m-wrap" style="width: 220px;">
												</select>
											</div>
										</div>
									</div>
								</div>
								<div class="row-fluid">
									<div class="span6 ">
										<div class="control-group">
											<label class="control-label">
												物品数量
												<span class="required">*</span>
											</label>
											<div class="controls">
												<input type="text" class="form-control" id="number" name="number" />
											</div>
										</div>
									</div>
								</div>
								<div class="form-actions">
									<a class="btn blue doApply"> 提交 </a>
									<a class="btn backToPortal"> 返回 </a>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
			<div class="tab-pane" id="portlet_tab2">
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
								<button class="btn red doSearch">
									刷新
									<i class="m-icon-swapright m-icon-white"></i>
								</button>
							</div>
						</div>
						<div class="portlet-body form form-horizontal">
							<div class="control-group">
								<label class="control-label">
									申请时间
								</label>
								<div class="controls">
									<div id="date-range" class="btn">
										<i class="icon-calendar"></i>
										&nbsp;<span></span> 
										<b class="caret"></b>
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
								<i class="icon-globe"></i><span class="titleExcel">申请记录</span>
							</div>
						</div>
				
						<div class="portlet-body" id="table-part">
						
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script src="<c:url value="/media/fm/jquery.validate.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/jquery.validate.messages_cn.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/date.js"/>" type="text/javascript" ></script>
<script src="<c:url value="/media/fm/daterangepicker.js"/>"	type="text/javascript"></script>
<script src="<c:url value="/media/fm/select2.min.js"/>" type="text/javascript"></script>

<script type="text/javascript">
	
	var $table;

	$().ready(function()
	{
		App.initDateRange($("#date-range"), 1);	
		loadProduct();	
		initNumber();
		doSearch();
		
		$(".doApply").bind("click", function()
		{
			doApply();
		});
		
		$(".doSearch").bind("click", function()
		{
			doSearch();
		});
		
		$(".backToPortal").bind("click", function()
		{
			backToPortal();
		});
				
		$("#applyForm").validate(
		{
			rules :
			{
				number :
				{
					required : true
				}
			}
		});
	});
	
	function loadProduct()
    {
    	$.get("portal/loadProduct.do", function(data, status)
    	{
    		productMap = new HashMap();
			var trHtml = '';
			$.each(data, function(i, product) 
			{
				trHtml += '<option value="' + product.id + '">' + product.code + '（' + product.name + '）</option>';
				productMap.put(product.id, product);
			});
			$("#product").html(trHtml);
			$("#product").select2();
			$("#product").select2().select2("val", data[0].id);
    	});
    }
	
	function initNumber()
    {
    	$("#number").bind("blur", function()
    	{
    		var number = $(this).val();
    		var product = $("#product").val();
    		
			if(number != "" && !exp.test(number))
			{ 
				App.alert("请输入正整数！");
				return;
			}
			
			console.log(product);
			
			$.get("portal/judgeEnoughToMinPkg.do?number=" + number + "&productId=" + product, function(data, status)
			{
				if(data.code == "-1")
				{
					App.alert("当前产品最小包装容量为" + data.capacity + "，请输入比该容量大的数！");
					return;
				}
			});
    	});
    }
    
    function doApply()
    {
    	if ($("#applyForm").valid() == false)
		{
			return;
		}
		
		var productId = $("#product").val();
		var count = $("#number").val();
		
		$.post("portal/applyQRCode.do?productId=" + productId + "&count=" + count, function(data)
		{
			if(data == "1")
			{
				App.alert("二维码申请成功！");
			}
			else
			{
				App.alert("二维码申请失败！");
			}
			doSearch();
		});
    }
    
    function doSearch()
    {
    	App.block();
		$("#table-part").empty();
		var table = "<table id='tbList' class='table table-striped table-bordered table-hover table-full-width'></table>";
		$("#table-part").html(table);
		
		var dates = $("#date-range span").html().split('~');
		var fmtm = $.trim(dates[0]);
		var totm = $.trim(dates[1]);
		
		$table = $("#tbList").bootstrapTable({
			 url: "portal/applyList.do?fmtm=" + fmtm + "&totm=" + totm,
			 pagination: true,
			 sidePagination: "server",
			 clickToSelect: true,
			 singleSelect: true ,
			 columns: [
                  {
                      title: "物品编号",
                      field: "code"
                  },
                  {
                      title: "物品名称",
                      field: "name"
                  },
                  {
                      title: "申请人",
                      field: "lgnNm"
                  },
                  {
                      title: "申请数量",
                      field: "count"
                  },
                  {
                      title: "申请时间",
                      field: "createTime"
                  },
                  {
                      title: "状态",
                      field: "stsName"
                  }
             ]
		});
		App.unblock();
    }
    
    function backToPortal()
    {
    	App.loadPage("portal/myPortal.do");
    }
	
</script>
