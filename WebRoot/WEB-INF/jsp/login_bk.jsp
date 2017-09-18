<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!--> <html lang="en"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
	<title>${tit}</title>
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />
	<!-- BEGIN GLOBAL MANDATORY STYLES -->
	
	<link rel="stylesheet" href="<c:url value="assets/css/reset.css"/>">
	<link rel="stylesheet" href="<c:url value="assets/css/supersized.css"/>">
    <link rel="stylesheet" href="<c:url value="assets/css/style.css"/>">
    
	<!-- END PAGE LEVEL STYLES -->
	<link rel="shortcut icon" href="<c:url value="/media/image/favicon.ico"/>" />
</head>
<!-- END HEAD -->

<!-- BEGIN BODY -->
<body class="login">
	<div class="page-container">
		<h3><img src="<c:url value="/media/image/logo-login.png"/>" width="200px"/></h3>
		<form class="form-vertical login-form" action="doLogin.do">
			<div class="alert-error"><span></span></div>
			<input type="text" name="username" class="username" placeholder="用户名">
			<input type="password" name="password" id="password" class="password" placeholder="密码">
			<button type="submit">
				登　 录
			</button>    
		</form>
	</div>
	
	<!-- BEGIN PAGE LEVEL PLUGINS -->
	<script src="<c:url value="/media/fm/jquery-1.10.1.min.js"/>" type="text/javascript"></script>
	<script src="<c:url value="/media/fm/jquery.form.js"/>" type="text/javascript"></script>
	<script src="<c:url value="/media/fm/jquery.validate.min.js"/>" type="text/javascript"></script>
	<script src="<c:url value="/media/fm/md5.js"/>"                 type="text/javascript"></script>
	<!-- END PAGE LEVEL PLUGINS -->
	
	<script src="<c:url value="/assets/js/supersized.3.2.7.min.js"/>" type="text/javascript"></script>
	<script src="<c:url value="/assets/js/supersized-init.js"/>" type="text/javascript"></script>
	<script src="<c:url value="/assets/js/scripts.js"/>" type="text/javascript"></script>
	
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="<c:url value="/media/js/login.js"/>" type="text/javascript"></script>   
	<script>
		jQuery(document).ready(function() { 
		  	Login.init();
		});
	</script>
</body>
<!-- END BODY -->
</html>