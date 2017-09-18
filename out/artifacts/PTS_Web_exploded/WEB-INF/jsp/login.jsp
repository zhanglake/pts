<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
	<!--<![endif]-->
	<!-- BEGIN HEAD -->
	<head>
		<title>${tit}</title>
		<meta content="width=device-width, initial-scale=1.0" name="viewport" />
		<meta content="" name="description" />
		<meta content="" name="author" />
		<!-- BEGIN GLOBAL MANDATORY STYLES -->

		<link rel="stylesheet" href="assets/css/login_style.css" />
		<!-- END PAGE LEVEL STYLES -->
		<link rel="shortcut icon"
			href="<c:url value="/media/image/favicon.ico"/>" />
	</head>
	<!-- END HEAD -->

	<!-- BEGIN BODY -->
	<body>
		<div class="login">
			<div class="login_content">
				<div class="login_img">
					<img src="<c:url value="/assets/img/login/login_left.png"/>" />
				</div>
				<div class="login_part">
					<form class="login-form" action="doLogin.do">
						<div class="alert-error"><span></span></div>
						<div class="field">
							<label>
								用户名：
							</label>
							<input id="username" type="text" class="text" name="username" style="width: 150px;" />
						</div>
						<div class="field">
							<label>
								密 &nbsp;&nbsp;码：
							</label>
							<input id="password" type="password" class="text" name="password" style="width: 150px;" />
						</div>
						<div class="field">
							<label></label>
							<button type="submit" class="loginButton"></button> 
						</div>
					</form>
				</div>
			</div>
			<div class="login_footer">
				<div class="login_font"></div>
			</div>
		</div>

		<!-- BEGIN PAGE LEVEL PLUGINS -->
		<script src="<c:url value="/media/fm/jquery-1.10.1.min.js"/>" type="text/javascript"></script>
		<script src="<c:url value="/media/fm/jquery.form.js"/>" type="text/javascript"></script>
		<script src="<c:url value="/media/fm/jquery.validate.min.js"/>" type="text/javascript"></script>
		<script src="<c:url value="/media/fm/md5.js"/>" type="text/javascript"></script>
		<!-- END PAGE LEVEL PLUGINS -->

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