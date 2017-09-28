<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
	<!--<![endif]-->
	<!-- BEGIN HEAD -->
	<head>
		<title>${tit}</title>
		<meta content="width=device-width, initial-scale=1.0" name="viewport" />
		<meta content="" name="description" />
		<meta content="" name="author" />
		<!-- BEGIN GLOBAL MANDATORY STYLES -->
		<link href="<c:url value="/media/css/bootstrap.min.css"/>" rel="stylesheet" type="text/css" />
		<link href="<c:url value="/media/css/bootstrap-responsive.min.css"/>" rel="stylesheet" type="text/css" />
		<link href="<c:url value="/media/css/font-awesome.min.css"/>" rel="stylesheet" type="text/css" />
		<link href="<c:url value="/media/css/style-metro.css"/>" rel="stylesheet" type="text/css" />
		<link href="<c:url value="/media/css/style.css"/>" rel="stylesheet" type="text/css" />
		<link href="<c:url value="/media/css/style-responsive.css"/>" rel="stylesheet" type="text/css" />
		<link href="<c:url value="/media/css/uniform.default.css"/>" rel="stylesheet" type="text/css" />
		<!-- END GLOBAL MANDATORY STYLES -->
		<link rel="shortcut icon" href="<c:url value="/media/image/favicon.ico"/>" />
		
		<style type="text/css">
			table .param-part{
				margin-top: 20px;
				width:80%;
			}
			.min-package, .big-package, .mid-package{
				margin-left: 11%;
			}
			table td {
				font-size: 12px;
				line-height: 18px;
			}
			table .print-info{
				font-size: 16px;
				line-height: 18px;
				font-weight: bold;
				padding-left: 30%;
				padding-top: 5%;
			}
			.big-package td{
				font-size: 20px;
				line-height: 20px;
			}
			.min-package{
				margin-top: 20px;
			}
			.min-package img{
				width: 110px;
				height: 110px;
			}
			.big-package img{
				width: 140px;
				height: 140px;
			}
			.mid-package img {
				width: 70px;
				height: 70px;
			}
		</style>
		
		<style type="text/css" media="print">
			.un-print {
				display: none;
			}
			.PageNext{
				page-break-after: always;
			}
			table td {
				font-size: 10px;
				line-height: 10px;
			}
			.min-package, .big-package{
				margin-left: 0;
			}
			.min-package{
				width: 10cm;
				height: 1.2cm;
				margin-top: 3px;
			}
			.big-package{
				font-size: 10pt;
				font-family: 'Microsoft YaHei';
				line-height: 12pt;
				width: 10cm;
				height: 7cm;
			}
			.min-package .img-part{
				width: 2.0cm;
				margin-left: 0.6cm;
				margin-top: 0;
			}
			.min-package .info-part {
				white-space: nowrap;
				font-family: 'Microsoft YaHei';
				margin-left: -23%;
				margin-top: 0;
				-ms-transform: scale(0.8);
				-webkit-transform: scale(0.8);
				-moz-transform: scale(0.8);
				-o-transform: scale(0.8);
				transform: scale(0.8);
			}
			.big-package .info-part{
				white-space: nowrap;
				font-size: 18pt;
				line-height: 18px;
				margin-left: -10%;
				margin-top: 20%;
				-ms-transform: scale(0.8);
				-webkit-transform: scale(0.8);
				-moz-transform: scale(0.8);
				-o-transform: scale(0.8);
				transform: scale(0.8);
			}
			.big-package .info-part tr td{
				padding-bottom: 10px;
			}
			.big-package .info-part .serial-no{
				padding-top: 5%;
			}
			.min-package .img-part img{
				width: 1.6cm;
				height: 1.6cm;
				margin-top: -8px;
				padding: 0px;
			}
			.big-package img{
				padding-top: 0;
				margin-top: 0;
				width: 4.0cm;
				height: 4.0cm;
			}
			
			
			.min-package .single-left{
				margin-left: -44%;
			}
			
			.one-qrcode{
				margin-top: -2.5%;
				margin-left: -1.6%;
				font-size: 8pt;
				font-family: 'Microsoft YaHei';
				line-height: 10pt;
			}
			.one-qrcode .info-part{
				white-space: nowrap;
				font-family: 'Microsoft YaHei';
				margin-left: -57%;
				margin-top: 1.121%;
				-ms-transform: scale(0.6);
				-webkit-transform: scale(0.6);
				-moz-transform: scale(0.6);
				-o-transform: scale(0.6);
				transform: scale(0.6);
			}
			.one-qrcode .info-part .serial-no{
				padding-top: 0;
				margin-top: 0;
			}
			.one-qrcode .img-part img{
				width: 1.2cm;
				height: 1.2cm;
				margin-top: 0;
				padding: 0px;
			}
			
		</style>
	</head>
	<!-- END HEAD -->
	<!-- BEGIN BODY -->
	<body>
		<div class="un-print">
			<h1></h1>
			<input type="hidden" id="productId" value="${product.id }" />
			<input type="hidden" id="productCode" value="${product.code }" />
			<input type="hidden" id="productName" value="${product.name }" />
			<input type="hidden" id="supplierName" value="${supplierName }" />
			<input type="hidden" id="capacity" value="${capacity }" />
			<input type="hidden" id="pkgLevel" value="${pkgLevel }" />
			<input type="hidden" id="isSingle" value="${isSingle }" />
			<input type="hidden" id="number" value="${number }" />
			<input type="hidden" id="pkgId" value="${pkgId }" />
			
			<table class="param-part">
				<tr>
					<td class="print-info no-wrap">
						打印 ${product.name }(${product.code }) 第 ${pkgLevel } 层二维码 <span id="number-print"></span> 张 &emsp;
						<button class="btn red doPrint">
							打印
						</button>
					</td>
				</tr>
			</table>
			
			<div id="div_Alert" class="modal hide fade" tabindex="-1" data-backdrop="static" data-keyboard="false">
				<div class="modal-header">
					<button data-dismiss="modal" class="close" type="button"></button>
					<h3 class="page-title">
						提示
					</h3>
				</div>
				<div class="modal-body">
					<p id="div_AlertMsg">
				</div>
				<div class="modal-footer">
					<button type="button" data-dismiss="modal" class="btn green">
						OK
					</button>
				</div>
			</div>
		</div>
		<div id="qrArea">
			
		</div>

		<script src="<c:url value="/media/fm/jquery-1.10.1.min.js"/>" type="text/javascript"></script>
		<script src="<c:url value="/media/fm/jquery-migrate-1.2.1.min.js"/>" type="text/javascript"></script>
		<!-- IMPORTANT! Load jquery-ui-1.10.1.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
		<script src="<c:url value="/media/fm/jquery-ui-1.10.1.custom.min.js"/>" type="text/javascript"></script>
		<script src="<c:url value="/media/fm/bootstrap.min.js"/>" type="text/javascript"></script>
		<!--[if lt IE 9]>
			<script src="<c:url value="/media/fm/excanvas.min.js"/>"></script>
			<script src="<c:url value="/media/fm/respond.min.js"/>"></script>  
		<![endif]-->
		<script src="<c:url value="/media/fm/app.js"/>" type="text/javascript"></script>
		<script src="<c:url value="/media/fm/date.js" />" type="text/javascript"></script>
		<script src="<c:url value="/media/fm/qrcode.js" />" type="text/javascript"></script>
		<script src="<c:url value="/media/fm/qrcode-img.js" />" type="text/javascript"></script>
		
		<script>
		    var printQr = new Array(); //已经打印的二维码
		    var storeQr = new Array(); //存储即将打印的二维码
		    var len; //二维码打印数量
		    
		    jQuery(document).ready(function () 
		    {
		    	showQrCode();
		    	
		    	$(".doPrint").click(function()
		    	{
					doPrint();
				});
				
		    });
		    
		    function showQrCode()
		    {
		    	var number = $("#number").val();
    			var productId = $("#productId").val();
    			var pkgLevel = $("#pkgLevel").val();
    			var pkgId = $("#pkgId").val();
    			var number = $("#number").val();
		    	$.get("showOneLevelQRCode.do?pkgLevel=" + pkgLevel + "&productId=" + productId + "&pkgId=" + pkgId + "&number=" + number, function(data, status)
				{
					showContent(data);
				});
		    }
		    
		    function showContent(data)
		    {
		    	var str = "";
		    	var pkgLevel = $("#pkgLevel").val();
		    	var isSingle = $("#isSingle").val();
		    	var capacity = $("#capacity").val();
		    	var productName = $("#productName").val();
		    	var productCode = $("#productCode").val();
		    	var supplierName = $("#supplierName").val();
		    	var i = 0;
				var qrarea_size = data[0]["TPNM"];
		    	len = data.length;
		    	
		    	str += "<div class='row-fluid'>";
		    	str += "<div class='portlet'>";
		    	str += "<div class='portlet-body'>";
		    	
		    	if(len == 0)
		    	{
		    		str += "<div style='padding-left: 10%; padding-top: 5%; color: red;'><h3>无二维码可打印, 请回到查询页面！</h3></div>";
		    	}
		    	else
		    	{
		    		$("#number-print").text(len);
		    		while( i < len)
			    	{
						storeQr[i] = data[i]["ID"];
			    		str += "<table class='qrcode-table'>";
			    		str += 	  "<tr>";
						if (qrarea_size == "5*3.5") {
							str = getOilTableHtml(str, i, productName, data[i]["SERIALNO"], len, data[i]["SAPNO"], data[i++]["SPECNO"]);
						} else {
							str = getTableHtml(str, i, productName, productCode, capacity, data[i]["PRODUCETIME"], data[i++]["SERIALNO"], len);
						}
			    		if(pkgLevel == 1 && isSingle != 1 && i < len)
			    		{
							storeQr[i] = data[i]["ID"];
							if (qrarea_size == "5*3.5") {
								str = getOilTableHtml(str, i, productName, data[i]["SERIALNO"], len, data[i]["SAPNO"], data[i++]["SPECNO"]);
							} else {
								str = getTableHtml(str, i, productName, productCode, capacity, data[i]["PRODUCETIME"], data[i++]["SERIALNO"], len);
							}
			    		}
			    		str +=    "</tr>";
			    		str += "</table>";
		    			str += "<div class='PageNext'></div>";
			    	}
		    	}
		    	str += "</div></div></div>";
		    	$("#qrArea").empty();
		    	$("#qrArea").html(str);
		    	
		    	if(pkgLevel == 1  && isSingle != 1)
		    	{
		    		$(".qrcode-table").addClass("min-package");
		    		$(".qrcode-table").removeClass("big-package");
		    		if(len == 1)
		    		{
		    			$(".qrcode-table").addClass("one-qrcode");
		    		}
		    		else
		    		{
		    			$(".qrcode-table").removeClass("one-qrcode");
		    		}
		    	}
		    	else
		    	{
		    		$(".qrcode-table").removeClass("min-package");
		    		$(".qrcode-table").addClass("big-package");
		    	}
				// 中型二维码
				if (qrarea_size == "5*3.5") {
					$(".qrcode-table").removeClass("min-package");
					$(".qrcode-table").removeClass("big-package");
					$(".qrcode-table").addClass("mid-package");
				}

		    	for(var i = 0; i < len; i++)
		    	{
					$("#qrCode_" + i).html(create_qrcode(data[i]["QRCODE"]));
		    	}
		    	
		    }
		    
		    function create_qrcode(text)
		    {
		    	var qr = qrcode(5, 'L');
		    	qr.addData(text);
				qr.make();
				return qr.createImgTag();
		    }

			function getOilTableHtml(str, i, productName, serialNo, len, sapno, specno) {
				var tr = "<table class='info-part'>";
				str += 		"<td rowspan='5'>";
				str += 		  "<table class='img-part'>";
				str += 			"<tr>";
				str += 			  "<td id='qrCode_" + (i++) + "'></td>";
				str += 			"</tr>";
				str += 		  "</table>";
				str += 		"</td>";
				str += 		"<td>";
				if( i == len && len % 2 != 0) {
					str += 	"<table class='info-part single-left'>";
				} else {
					str += 	"<table class='info-part'>";
				}
				str +=  		"<tr>";
				str += 			  "<td>SAP号：" + sapno + "</td>";
				str +=  		"</tr>";
				str +=  		"<tr>";
				str += 			  "<td>名称：" + productName + "</td>";
				str +=  		"</tr>";
				str +=  		"<tr>";
				str += 			  "<td>规格：" + specno + "</td>";
				str +=  		"</tr>";
				str +=  		"<tr>";
				str += 			  "<td class='serial-no'>序列号：" + serialNo + "</td>";
				str +=  		"</tr>";
				str +=  		"<tr>";
				str += 			  "<td>无锡威孚高科技集团股份有限公司</td>";
				str +=  		"</tr>";
				str += 		  "</table>";
				str += 		"</td>";
				return str;
			}

		    function getTableHtml(str, i, productName, productCode, capacity, productTime, serialNo, len)
		    {
		    	if(productTime == undefined)
		    	{
		    		productTime = new Date().toString("yyyy-MM");
		    	}
		    	var tr = "<table class='info-part'>";
		    	str += 		"<td rowspan='5'>";
		    	str += 		  "<table class='img-part'>";
		    	str += 			"<tr>";	
		    	str += 			  "<td id='qrCode_" + (i++) + "'></td>";				
				str += 			"</tr>";	
				str += 		  "</table>";
				str += 		"</td>";
				str += 		"<td>";
				if( i == len && len % 2 != 0)
				{
					str += 	"<table class='info-part single-left'>";
				}
				else
				{
					str += 	"<table class='info-part'>";
				}
				str +=  		"<tr>";
				str += 			  "<td>名称：" + productName + "</td>";
				str +=  		"</tr>";
				str +=  		"<tr>";
				str += 			  "<td>编码：" + productCode + "</td>";
				str +=  		"</tr>";
				str +=  		"<tr>";
				str += 			  "<td>数量：" + capacity + "</td>";
				str +=  		"</tr>";
				str +=  		"<tr>";
				str += 			  "<td>生产日期：" + productTime + "</td>";
				str +=  		"</tr>";
				str +=  		"<tr>";
				str += 			  "<td class='serial-no'>序列号：" + serialNo + "</td>";
				str +=  		"</tr>";
				str += 		  "</table>";
				str += 		"</td>";
				
				return str;
		    }
		    
		    function doPrint()
		    {
		    	if(storeQr.length < 1)
		    	{
		    		App.alert("该层级目前无二维码可打印, 请补录生产信息或申请二维码再打印！");
		    		return;
		    	}
		    	if(printQr == storeQr)
		    	{
		    		App.alert("该批二维码已打印，不能重复打印！");
		    		return;
		    	}
		    	updateQr();
				insertLog();
				
		    	window.print();
		    }
		    
		    function updateQr()
		    {
		    	printQr = storeQr;
		    	$.ajax({
			        url: "updateQr.do",
			        data: {printQr: printQr},
			        method: "POST",
			        traditional: true
			    });
		    }
		    
		    function insertLog()
		    {
		    	var pkgLevel = $("#pkgLevel").val();
		    	var productName = $("#productName").val();
		    	var productCode = $("#productCode").val();
		    	$.ajax({
			        url: "insertLog.do?number=" + len + "&pkgLevel=" + pkgLevel + "&productName=" + productName + "&productCode=" + productCode,
			        method: "POST",
			        traditional: true
			    });
		    }
		    		    
		</script>

	</body>
	<!-- END BODY -->

</html>

