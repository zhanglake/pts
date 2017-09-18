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
			.param-part{
				margin-top: 20px;
				width:100%;
			}
			.min-package, .big-package{
				margin-left: 19%;
			}
			table td {
				font-size: 12px;
				line-height: 18px;
			}
			table td.level-td{
				font-size: 18px;
				padding-left: 20%; 
				padding-top: 10px; 
			}
			.big-package td{
				font-size: 20px;
				line-height: 20px;
			}
			.min-package .info-part{
				font-size: 20pt;
				font-family: 'Microsoft YaHei';
				line-height: 16pt;
				-ms-transform: scale(0.8);
				-webkit-transform: scale(0.8);
				-moz-transform: scale(0.8);
				-o-transform: scale(0.8);
				transform: scale(0.8);
			}
			.min-package img{
				width: 90px;
				height: 90px;
			}
			.big-package img{
				width: 140px;
				height: 140px;
			}
		</style>
		
		<style type="text/css" media="print">
			table{
				font-size: 14pt;
				font-family: 'Microsoft YaHei';
				line-height: 16px;
			}
			.un-print {
				display: none;
			}
			.PageNext{
				page-break-after: always;
			}
			.min-package, .big-package{
				margin-left: 0;
			}
			.min-package{
				width: 10cm;
				height: 1.2cm;
			}
			.big-package{
				width: 10cm;
				height: 7cm;
			}
			.min-package .img-part{
				width: 2.0cm;
				margin-left: 0.4cm;
				margin-top: 1.7%;
			}
			.min-package .info-part{
				line-height: 10px;
				white-space: nowrap;
				margin-left: -62%;
				margin-top: -12.3%;
				-ms-transform: scale(0.3);
				-webkit-transform: scale(0.3);
				-moz-transform: scale(0.3);
				-o-transform: scale(0.3);
				transform: scale(0.3);
			}
			.big-package .info-part{
				font-size: 18pt;
				line-height: 18px;
				white-space: nowrap;
				margin-left: -15%;
				margin-top: 22%;
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
				padding-top: 6%;
			}
			.min-package img{
				width: 1.0cm;
				height: 1.0cm;
				padding: 0px;
			}
			.big-package img{
				padding-top: 0;
				margin-top: 0;
				width: 4.0cm;
				height: 4.0cm;
			}
		</style>
	</head>
	<!-- END HEAD -->
	<!-- BEGIN BODY -->
	<body>
		<div class="un-print">
			<h1></h1>
			<table class="param-part">
				<tr>
					<td class="level-td">
						序列号：
						<input type="text" id="serialNo" />
						<button class="btn blue doShow" style="margin-top: -10px;">
							显示
						</button>
						<button class="btn green doPrint" style="margin-top: -10px;">
							补打
						</button>
					</td>
				</tr>
			</table>
		</div>
		
		<div id="qrArea">
		</div>
		
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
		<script src="<c:url value="/media/fm/qrcode-img.js" />" type="text/javascript"></script>
		
		<script>
		    jQuery(document).ready(function () 
		    {
		    	$(".doShow").click(function()
		    	{
		    		showQrCode();
		    	});
		    	
		    	$(".doPrint").click(function()
		    	{
					window.print();
					//return false;
				});
		    });
		    
		    function showQrCode()
		    {
		    	var serialNo = $("#serialNo").val();
		    	$.get("showBySerialNo.do?serialNo=" + serialNo, function(data, status)
				{
					if(data == null)
					{
						App.alert("无此序列号二维码！");
						return;
					}
					showContent(data);
				});
		    }
		    
		    function showContent(data)
		    {
		    	var str = "";
		    	var pkgLevel = data["PKGLEVEL"];
		    	str += "<div class='row-fluid'>";
		    	str += "<div class='portlet'>";
		    	str += "<div class='portlet-body'>";
		    	str += "<table class='qrcode-table'>";
		    	str += 	  "<tr>";
		    	str = getTableHtml(str, data);
		    	str +=    "</tr>";
		    	str += "</table>";
		    	str += "</div></div></div>";
		    	
		    	$("#qrArea").empty();
		    	$("#qrArea").html(str);
		    	
		    	if(pkgLevel == 1)
		    	{
		    		$(".qrcode-table").addClass("min-package");
		    		$(".qrcode-table").removeClass("big-package");
		    		qrSize = 100;
		    	}
		    	else
		    	{
		    		$(".qrcode-table").removeClass("min-package");
		    		$(".qrcode-table").addClass("big-package");
		    		qrSize = 140;
		    	}
		    	
				$("#qrCode").html(create_qrcode(data["QRCODE"]));
		    }
		    
		    function create_qrcode(text)
		    {
		    	var qr = qrcode(5, 'L');
		    	qr.addData(text);
				qr.make();
				return qr.createImgTag();
		    }
		    
		    function getTableHtml(str, data)
		    {
		    	str += 		"<td rowspan='5'>";
		    	str += 		  "<table class='img-part'>";
		    	str += 			"<tr>";	
		    	str += 			  "<td id='qrCode'></td>";				
				str += 			"</tr>";	
				str += 		  "</table>";
				str += 		"</td>";
				str += 		"<td>";
				str += 		  "<table class='info-part'>";
				str +=  		"<tr>";
				str += 			  "<td>名称：" + data["PRODUCT_NAME"] + "</td>";
				str +=  		"</tr>";
				str +=  		"<tr>";
				str += 			  "<td>编码：" + data["PRODUCT_CODE"]+ "</td>";
				str +=  		"</tr>";
				str +=  		"<tr>";
				str += 			  "<td>数量：" + data["CAPACITY"] + "</td>";
				str +=  		"</tr>";
				str +=  		"<tr>";
				str += 			  "<td>生产日期：" + data["PRODUCE_TIME"] + "</td>";
				str +=  		"</tr>";
				str +=  		"<tr>";
				str += 			  "<td class='serial-no'>序列号：" + data["SERIALNO"] + "</td>";
				str +=  		"</tr>";
				str += 		  "</table>";
				str += 		"</td>";
				
				return str;
		    }
		    		    
		</script>

	</body>
	<!-- END BODY -->

</html>

