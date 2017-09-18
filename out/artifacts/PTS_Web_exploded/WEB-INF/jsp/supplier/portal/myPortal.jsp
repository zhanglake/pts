<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<link href="<c:url value="/media/css/jquery.easy-pie-chart.css"/>" rel="stylesheet" type="text/css" media="screen" />
<link rel="stylesheet" type="text/css" href="<c:url value="/media/css/select2_metro.css"/>" />
    
<!-- BEGIN PAGE HEADER-->
<div class="row-fluid">
    <div class="span12">
    </div>
</div>

<!-- END PAGE HEADER-->
<div id="dashboard">
     <!-- BEGIN DASHBOARD STATS  -->
    
    <div class="row-fluid" id="searchPrint">
    	<div class="span12 form-horizontal">
    		<input type="hidden" id="hasSystem" value=${supplier.has_system } />
    		<div class="row-fluid">
    			<div class="span4">
    				<div class="control-group">
    					<label class="control-label">
    						选择物品：
    					</label>
    					<div class="controls">
    						<select id="product" class="m-wrap" style="width: 220px;">
    						</select>
    					</div>
    				</div>
    			</div>
    			
    		</div>
    		<div class="row-fluid" style="margin-bottom: 10px;">
    			<div class="span4">
    				<label class="control-label">包装规则：</label>
    				<div class="controls">
    					<span class="control-label" style="color: red; width: 80px;" id="packageRule"></span>
    				</div>
    			</div>
    			<div class="span6">
    				<label class="control-label">二维码数目：</label>
    				<div class="controls no-wrap">
    					<span class="control-label" id="show-left" style="font-size: 14px;"></span>
    				</div>
    			</div>
    		</div>
    		<div class="row-fluid">
    			<div class="span4">
    				<div class="control-group">
    					<label class="control-label">
    						选择层级：
    					</label>
    					<div class="controls">
    						<select id="pkgLevel" class="m-wrap">
    						</select>
    					</div>
    				</div>
    			</div>
    			<div class="span4">
    				<div class="control-group">
    					<label class="control-label">
    						打印数量：
    					</label>
    					<div class="controls no-wrap">
    						<input type="text" class="m-wrap" id="number" />
    						<button class="btn green doSearch">查询</button>
    						<button class="btn red applyQrcode">补码申请</button>
    					</div>
    				</div>
    			</div>
    		</div>
    	</div>
    </div>
	<div id="qrArea">
		
	</div>
</div>
<!-- END DASHBOARD STATS -->

<script src="<c:url value="/media/fm/qrcode.js" />" type="text/javascript"></script>
<script src="<c:url value="/media/fm/qrcode-img.js" />" type="text/javascript"></script>
<script src="<c:url value="/media/fm/select2.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/media/fm/hashMap.js"/>" type="text/javascript"></script>

<script>
    var exp = /^[1-9]\d*$/;
    var productMap;
    
    jQuery(document).ready(function () 
    {
    	//$("#product").select2();
    	loadProduct();
    	initNumber();
    	hasPermission();
    	
    	$(".doSearch").bind("click", function()
    	{
    		doSearch();
    	});
    	
    	$(".doPrint").bind("click", function()
    	{
    		doPrint();
    	});
    	
    	$(".doRecruit").bind("click", function()
    	{
    		window.open("portal/recruitPrint.do");
    	});
    	
    	$(".applyQrcode").bind("click", function()
    	{
    		applyQrcode();
    	});
    	
    	$("#product").change(function()
    	{
    		loadRuleMap();
    		showLeft(1);
    	});
    	
    });
    
    function loadProduct()
    {
    	var vNumber = ${number };
    	var vProductId = ${productId };
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
			
			if(vNumber != null && vNumber != 0)
	    	{
	    		$("#number").val(vNumber);
	    		$("#product").select2().select2("val", vProductId);
	    		loadRuleMap();
	    		showLeft(2);
	    	}
	    	else
	    	{
	    		if(data.length > 0)
	    		{
	    			$("#product").select2().select2("val", data[0].id);
	    			loadRuleMap();
					showLeft(1);
	    		}
	    	}
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
			
    	});
    }
    
    function hasPermission()
    {
    	var canPrint = ${C_SUP.canPrint };
    	
    	if(canPrint == 0)
    	{
    		$(".doSearch,.doPrint,.doRecruit").hide();
    	}
    	else
    	{
    		$(".doSearch,.doPrint,.doRecruit").show();
    	}
    }
    
    function loadRuleMap()
    {
    	var productId = $("#product").val();
    	$.get("portal/loadRuleMap.do?productId=" + productId, function(data, status)
    	{
    		if(null != data && "" != data)
    		{
    			$("#packageRule").text(data.packageRule);
    			$("#pkgLevel").html(data.ruleMap);
    		}
    	});
    }
    
    function showLeft(type)
    {
    	var product = $("#product").val();
    	var html = "";
    	$.get("portal/showLeft.do?productId=" + product, function(data, status)
    	{
    		html += "";
    		for(var i = 0; i < data.length; i++)
    		{
    			html += "第 " + data[i]["pkgLevel"] + " 层 ";
    			if(data[i]["pkgLevel"] == 1)
    			{
    				html += "可直接打印数<span id='bindedNum' style='color: red;'> " + data[i]["bindedNum"] + " </span>个,";
    				html += "可补录数<span id='left_" + (i + 1) + "' style='color: red;'> " + data[i]["recruitNum"] + " </span>个. ";
    			}
    			else
    			{
    				html += "<span id='left_" + (i + 1) + "' style='color: red;'> " + data[i]["recruitNum"] + " </span>个"; 
    			}
    		}
    		$("#show-left").html(html);
    		
    		if(type == 2)
    		{
    			doSearch();
    		}
    	});
    }
    
    function doSearch()
    {
    	var number = $("#number").val();
    	var productId = $("#product").val();
    	var pkgLevel = $("#pkgLevel").find("option:selected").text();
    	var pkgId = $("#pkgLevel").val();
    	var leftNumber = $("#left_" + pkgLevel).text();
    	
		if(!exp.test(number))
		{ 
			App.alert("请输入正整数！");
			return;
		}
		if(Number(number) > 1000)
		{
			App.alert("每批打印二维码数请控制在1000张以内！");
			return;
		}
		if(pkgLevel == 1)
		{
			var bindedNum = $("#bindedNum").text();
			leftNumber = Number(leftNumber) + Number(bindedNum);
		}
		
		if(Number(number) > Number(leftNumber))
		{
			App.alert("超出当前层级可用二维码数！");
			return;
		}
		
		if(pkgLevel == 1)
		{
			$.get("portal/hasProductInfo.do?number=" + number + "&productId=" + productId, function(data, status)
			{
				if(data == "1")
				{
					loadQrCodeData(number, productId, pkgLevel, pkgId);
				}
				else
				{
					App.alert("生产信息不足，进入补录页面。");
					App.loadPage("portal/recruitPage.do?number=" + number + "&productId=" + productId);
				}
			});	
		}
		else
		{
			loadQrCodeData(number, productId, pkgLevel, pkgId);
		}	
    }  
    
    function loadQrCodeData(number, productId, pkgLevel, pkgId)
    {
    	$.ajax({
    		type: "POST",
    		url: "portal/showQRCode.do",
    		data: {number: number, productId: productId, pkgLevel: pkgLevel, pkgId: pkgId},
    		success: function(qrcodes)
    		{
    			showQRCode(qrcodes, pkgLevel, productId, pkgId, number);
    		}
    	});
    } 
     
    function showQRCode(data, pkgLevel, productId, pkgId, number)
    {
    	var str = "";
    	var ids = "";
    	str += "<div class='row-fluid'><div class=''><div class='portlet'>";
    	str += "<div class='portlet-title' style='margin-top: 10px;'>";
    	str += "<div class='caption'>二维码如下&emsp;&emsp;&emsp;&emsp;<button class='btn blue doPrint'>打印</button></div></div>";
    	str += "<div class='portlet-body'>";
    	
    	if(data.length == 0)
    	{
    		str += "<div style='padding-left: 12%; padding-top: 5%; color: red;'><h3>当前层级无二维码可打印!</h3></div>";
    	}
    	for(var i = 0; i < data.length; i++)
    	{
	    	str += "<span id='qrCode_" + i + "' style='padding-top: 20px; margin-right: 60px; line-height: 50px;'>";
	    	str += "</span>";
    	}
    	
    	str += "</div></div></div></div>";
    	$("#qrArea").empty();
    	$("#qrArea").append(str);
    	
    	for(var i = 0; i < data.length; i++)
    	{
    		ids += data[i]["ID"] + ",";
    		$("#qrCode_" + i).html(create_qrcode(data[i]["QRCODE"]));
    	}
    	
    	$(".doPrint").bind("click", function()
    	{
    		pkgLevel = encodeURIComponent(pkgLevel);
    		//ids =  encodeURIComponent(ids);
    		productId = encodeURIComponent(productId);
    		pkgId = encodeURIComponent(pkgId);
    		number = encodeURIComponent(number);
    		
    		window.open("portal/printQRCode.do?pkgLevel=" + pkgLevel + "&productId=" + productId + "&pkgId=" + pkgId + "&number=" + number, "newwindow");
    	});
    }
    
	function create_qrcode(text)
	{
		var qr = qrcode(5, 'L');
		qr.addData(text);
		qr.make();
		return qr.createImgTag();
	}
    
    function applyQrcode()
    {
    	App.loadPage("portal/applyPage.do");
    }
    
</script>

