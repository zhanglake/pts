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
		<link href="<c:url value="/media/css/theme/default.css"/>" rel="stylesheet" type="text/css" id="style_color" />
		<link href="<c:url value="/media/css/uniform.default.css"/>" rel="stylesheet" type="text/css" />
		<!-- END GLOBAL MANDATORY STYLES -->
		<link rel="shortcut icon" href="<c:url value="/media/image/favicon.ico"/>" />
	</head>
	<!-- END HEAD -->
	<!-- BEGIN BODY -->
	<body class="page-header-fixed">
		<!-- BEGIN HEADER -->
		<div class="header navbar navbar-inverse navbar-fixed-top">
			<!-- BEGIN TOP NAVIGATION BAR -->
			<div class="navbar-inner">
				<div class="container-fluid">
					<!-- BEGIN LOGO -->
					<a class="brand" href="#"> <img style="height: 40px;" id="logo-top" src="" alt="logo" />${C_USR.company_name} </a>
					<!-- END LOGO -->
					<!-- BEGIN RESPONSIVE MENU TOGGLER -->
					<a href="javascript:;" class="btn-navbar collapsed" data-toggle="collapse" data-target=".nav-collapse"> <img src="<c:url value="/media/image/menu-toggler.png"/>" alt="" /> </a>
					<!-- END RESPONSIVE MENU TOGGLER -->

					<!-- BEGIN TOP NAVIGATION MENU -->
					<ul class="nav pull-right">
						<li class="dropdown" id="header_notification_bar">
							<a title="未处理二维码申请记录数" class="dropdown-toggle applyRecord"> 
								<i class="icon-qrcode"></i>
								<span class="badge" style="top: 2px; right: 16px;"></span> 
							</a>
						</li>
						<!-- BEGIN USER LOGIN DROPDOWN -->
						<li class="dropdown user">
							<a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown"> <span class="username">${C_USR.usrNm}(${C_USR.role.rlNm })</span> <i class="icon-angle-down"></i> </a>
							<ul class="dropdown-menu">
								<li>
									<a href="javascript:App.loadPage('account/profilelEdit.do');"><i class="icon-user"></i> 账号信息</a>
								</li>
								<li>
									<a href="index/doLogout.do"><i class="icon-key"></i> 退出</a>
								</li>
								<li>
									<a href="javascript:void(0);" id="update-log"><i class="icon-list-alt"></i> 更新日志</a>
								</li>
								<li>
									<a href="mailto:zhenghua.zhang@weifu.com.cn?subject=溯源系统错误上报" id="report-bug"><i class="icon-wrench"></i> 错误上报</a>
								</li>
							</ul>
						</li>
						<!-- END USER LOGIN DROPDOWN -->
					</ul>
					<!-- END TOP NAVIGATION MENU -->
				</div>
			</div>
			<!-- END TOP NAVIGATION BAR -->
		</div>

		<!-- END HEADER -->

		<!-- BEGIN CONTAINER -->
		<div class="page-container">
			<!-- BEGIN SIDEBAR -->
			<div class="page-sidebar nav-collapse collapse">
				<!-- BEGIN SIDEBAR MENU -->
				<ul class="page-sidebar-menu">
					<li>
						<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
						<div class="sidebar-toggler hidden-phone"></div>
						<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
					</li>
					<li>
						<!-- BEGIN RESPONSIVE QUICK SEARCH FORM -->
						<form class="sidebar-search">
							<!--<div class="input-box">
								<a href="javascript:;" class="remove"></a>
								<input type="text" placeholder="Search..." />
								<input type="button" class="submit" value=" " />
							</div>
						--></form>
						<!-- END RESPONSIVE QUICK SEARCH FORM -->
					</li>

					<li class="m start active ">
						<a href="javascript:;" url="portal/myPortal.do"> <i class="icon-home"></i> <span class="title">我的工作室</span> <span class="selected"></span> </a>
					</li>
					
					<!-- +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
					
					<!-- +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
					
				</ul>
				<!-- END SIDEBAR MENU -->
			</div>
			<!-- END SIDEBAR -->

			<!-- BEGIN PAGE -->
			<div class="page-content">

				<!-- BEGIN PAGE CONTAINER-->
				<!-- +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
				<div id="div_container" class="container-fluid">

				</div>
				<!-- +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
				<!-- END PAGE CONTAINER-->

			</div>
			<!-- END PAGE -->
		</div>

		<!-- END CONTAINER -->

		<!-- BEGIN FOOTER -->
		<div class="footer">
			<div class="footer-inner">
				${cpy}
			</div>
			<div class="footer-tools">
				<span class="go-top"> <i class="icon-angle-up"></i> </span>
			</div>
		</div>
		<!-- END FOOTER -->

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

		<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
		<!-- BEGIN CORE PLUGINS -->
		<script src="<c:url value="/media/fm/jquery-1.10.1.min.js"/>" type="text/javascript"></script>
		<script src="<c:url value="/media/fm/jquery-migrate-1.2.1.min.js"/>" type="text/javascript"></script>
		<!-- IMPORTANT! Load jquery-ui-1.10.1.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
		<script src="<c:url value="/media/fm/jquery-ui-1.10.1.custom.min.js"/>" type="text/javascript"></script>
		<script src="<c:url value="/media/fm/bootstrap.min.js"/>" type="text/javascript"></script>
		<!--[if lt IE 9]>
		<script src="<c:url value="/media/fm/excanvas.min.js"/>"></script>
		<script src="<c:url value="/media/fm/respond.min.js"/>"></script>  
		<![endif]-->
		<script src="<c:url value="/media/fm/jquery.slimscroll.min.js"/>" type="text/javascript"></script>
		<script src="<c:url value="/media/fm/jquery.blockui.min.js"/>" type="text/javascript"></script>
		<script src="<c:url value="/media/fm/jquery.cookie.min.js"/>" type="text/javascript"></script>
		<script src="<c:url value="/media/fm/jquery.uniform.min.js"/>" type="text/javascript"></script>
		<script src="<c:url value="/media/fm/json2.js"/>" type="text/javascript"></script>
		<script src="<c:url value="/media/fm/math.js"/>" type="text/javascript"></script>
		<script src="<c:url value="/media/fm/highcharts.js"/>" type="text/javascript"></script>
		<script src="<c:url value="/media/fm/exporting.js"/>" type="text/javascript"></script> 
		<!--  
	    <script src="<c:url value="/media/fm/date.js"/>" type="text/javascript" ></script>
	    -->
		<script src="<c:url value="/media/fm/app.js"/>" type="text/javascript"></script>
		<!-- END CORE PLUGINS -->

		<!-- BEGIN PAGE LEVEL SCRIPTS -->
		<script src="<c:url value="/media/js/index.js"/>" type="text/javascript"></script>
		
		<script src="<c:url value="/media/fm/bootstrap-table.js"/>" type="text/javascript"></script>
		<script src="<c:url value="/media/fm/bootstrap-table-zh-CN.js"/>" type="text/javascript"></script>
		<!-- END PAGE LEVEL SCRIPTS -->

		<script>
			jQuery(document).ready(function()
			{
				App.init(); // initlayout and core plugins
				Index.init();
				showLogo();
				// 更新日志
				$("#update-log").click(function () {
					window.open("index/updateLog.do");
				});
			});
			
			function showLogo()
			{
				var logo = "${C_COM.logo }";
				if(null != logo&& logo != "")
				{
					$("#logo-top").attr("src", "<c:url value='/upload/logo/" + logo + "'/>");
				}
				else
				{
					$("#logo-top").attr("src", "<c:url value='/media/image/logo.png'/>");
				}
			}
		</script>
		<!-- END JAVASCRIPTS -->
	</body>
	<!-- END BODY -->

</html>
