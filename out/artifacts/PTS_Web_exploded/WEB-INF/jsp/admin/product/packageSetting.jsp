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
			<a href="#">产品管理</a>
			<span class="icon-angle-right"></span>
		</li>
		<li>
			<a href="#">包装设置</a>
		</li>
	</ul>
</div>

<!-- END PAGE HEADER-->

<div class="row-fluid">
	<div class="portlet box blue tabbable">
		<div class="portlet-title">
			<div class="caption">
				<i class="icon-reorder"></i>
				<span class="hidden-480">批量设置包装</span>
			</div>
			<div class="actions">
				<button type="button" onclick="doSave()" class="btn green">
					保存
				</button>
			</div>
		</div>

		<div class="portlet-body">
			<div class="portlet-tabs">
				<div class="tab-content">
					<div class="active form">
						<div class="form-horizontal">
							<div class="row-fluid">
								<div class="span6">
									<div class="control-group">
										<label class="control-label">
											包装规则
										</label>
										<div class="controls">
											<select class="wrap" id="packageID">
												${sPackageRules }
											</select>
										</div>
									</div>
								</div>
								<div class="span6">
									<div class="control-group">
										<label class="control-label">
											选择物品
										</label>
										<div class="controls">
											<select class="wrap span6" id="products">
											</select>
										</div>
									</div>
								</div>
							</div>
						</div>
						
						<table id="productList" class="table table-bordered table-hover">
							<thead>
								<tr>
									<th data-field="sapNo">
										SAP号
									</th>
									<th data-field="code">
										编码
									</th>
									<th data-field="name">
										名称
									</th>
									<th data-field="categoryName">
										类型
									</th>
									<th data-field="specNo">
										规格型号
									</th>
									<th data-field="price">
										售价
									</th>
									<th data-field="points">
										积分
									</th>
									<th>操作</th>
								</tr>
							</thead>
						</table>
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
<script src="<c:url value="/media/fm/hashMap.js"/>" type="text/javascript"></script>

<script type="text/javascript">

	var productMap;

	$().ready(function()
	{
		loadProduct();
		$("#products").change( function ()
		{
			productsItem($(this).val());
		});
	});

	function doSave()
	{
		var packageID = $("#packageID").val();
		var ids = "";
		var productId = "";
		
		if (confirm("确定保存包装规则吗？") == false)
		{
			App.unblock();
			return;
		}
		
		$("#productList").find("tr").each(function ()
		{
			productId = $(this).find(".productId").val();
			if(productId != undefined)
			{
				ids += productId + ",";
			}
		});
		console.log(ids);
		if(ids == "")
		{
			App.alert("没有选择产品！");
			return;
		}
		
		$.post("product/packageSetting.do", {packageID : packageID, products : ids}, function(data)
		{
			if(data == "1")
			{
				App.alert("批量设置包装成功！");
			}
			else
			{
				App.alert("批量设置包装失败！");
			}
		});
	}
	
	function loadProduct()
    {
    	$.get("product/loadProduct.do", function(data, status)
    	{
    		productMap = new HashMap();
			var trHtml = '';
			$.each(data, function(i, product) 
			{
				trHtml += '<option value="' + product.id + '">' + product.code + '（' + product.name + '）</option>';
				productMap.put(product.id, product);
			});
			$("#products").html(trHtml);
			$("#products").select2();
    	});
    }
    
    function productsItem(productItem)
	{
		if ($("#productList").find("tr").length == 2)
		{
			$("#productList").find(".no-records-found").remove();
		}
		var product = productMap.get(productItem);
		var exists = false;
		$(".productId").each(function()
		{
			if ($(this).text() == product.mtrlcd)
			{
				exists = true;
				return false;
			}
		});
		if (exists == true)
		{
			App.alert("已存在该项！");
			return;
		}
		
		var tr = ['<tr>', 
			'<td>' + product.sapNo + '</td>',
			'<td>' + product.code + '</span></td>',
			'<td>' + product.name + '</td>',
			'<td>' + product.categoryName + '</td>',
			'<td>' + product.specNo + '<input type="hidden" class="productId" value="' + product.id + '"></td>',
			'<td>' + product.price + '</td>',
			'<td>' + product.points + '</td>',
			'<td><a class="deleteItem">删除</a></td>',
			'</tr>'].join("");
		//alert(tr);
		$("#productList").append(tr);
		
		deleteItem();
	}
	
	function deleteItem()
	{
		$(".deleteItem").bind("click", function(e) 
		{
			e.preventDefault();
			$(this).closest("tr").remove();
		});
	}
</script>
