<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%String path = request.getContextPath(); %>
<title>SL会员商城</title>
<!-- The styles -->
	<link id="bs-css" href="<%=path %>/statics/css/bootstrap-cerulean.css" rel="stylesheet">
	<style type="text/css">
	  body {
		padding-bottom: 40px;
	  }
	  .sidebar-nav {
		padding: 9px 0;
	  }
	</style>
	<link href="<%=path %>/statics/css/bootstrap-responsive.css" rel="stylesheet">
	<link href="<%=path %>/statics/css/charisma-app.css" rel="stylesheet">
	<link href="<%=path %>/statics/css/jquery-ui-1.8.21.custom.css" rel="stylesheet">
	<link href='<%=path %>/statics/css/fullcalendar.css' rel='stylesheet'>
	<link href='<%=path %>/statics/css/fullcalendar.print.css' rel='stylesheet'  media='print'>
	<link href='<%=path %>/statics/css/chosen.css' rel='stylesheet'>
	<link href='<%=path %>/statics/css/uniform.default.css' rel='stylesheet'>
	<link href='<%=path %>/statics/css/colorbox.css' rel='stylesheet'>
	<link href='<%=path %>/statics/css/jquery.cleditor.css' rel='stylesheet'>
	<link href='<%=path %>/statics/css/jquery.noty.css' rel='stylesheet'>
	<link href='<%=path %>/statics/css/noty_theme_default.css' rel='stylesheet'>
	<link href='<%=path %>/statics/css/elfinder.min.css' rel='stylesheet'>
	<link href='<%=path %>/statics/css/elfinder.theme.css' rel='stylesheet'>
	<link href='<%=path %>/statics/css/jquery.iphone.toggle.css' rel='stylesheet'>
	<link href='<%=path %>/statics/css/opa-icons.css' rel='stylesheet'>
	<link href='<%=path %>/statics/css/uploadify.css' rel='stylesheet'>

	<!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
	<!--[if lt IE 9]>
	  <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->

	<!-- The fav icon -->
	<link rel="shortcut icon" href="<%=path %>/statics/img/favicon.ico">
		
</head>

<body>
		<div class="container-fluid">
		<div class="row-fluid">
		
			<div class="row-fluid">
				<div class="span12 center login-header">
					<h2>SL会员商城</h2>
				</div><!--/span-->
			</div><!--/row-->
			
			<div class="row-fluid">
				<div class="well span5 center login-box">
					<div class="alert alert-info">
						请输入登陆帐号和密码...
					</div>
					<div class="form-horizontal">
						<fieldset>
							<div class="input-prepend" title="登陆账号" data-rel="tooltip">
								<span class="add-on"><i class="icon-user"></i></span><input autofocus class="input-large span10" name="loginCode" id="loginCode" type="text" value="" />
							</div>
							<div class="clearfix"></div>

							<div class="input-prepend" title="登录密码" data-rel="tooltip">
								<span class="add-on"><i class="icon-lock"></i></span><input class="input-large span10" name="password" id="password" type="password" value="" />
							</div>
							<div class="clearfix"></div>

							<ul id="formtip"></ul><!-- 提示信息 -->
							<p class="center span5">
								<button type="submit" class="btn btn-primary" id="loginBtn">登录</button>
							</p>
						</fieldset>
				</div><!--/span-->
			</div><!--/row-->
			</div><!--/fluid-row-->
		</div>
	</div><!--/.fluid-container-->

	<!-- external javascript
	================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->

	<!-- jQuery -->
	<script src="<%=path %>/statics/js/jquery-1.7.2.min.js"></script>
	<!-- jQuery UI -->
	<script src="<%=path %>/statics/js/jquery-ui-1.8.21.custom.min.js"></script>
	<!-- transition / effect library -->
	<script src="<%=path %>/statics/js/bootstrap-transition.js"></script>
	<!-- alert enhancer library -->
	<script src="<%=path %>/statics/js/bootstrap-alert.js"></script>
	<!-- modal / dialog library -->
	<script src="<%=path %>/statics/js/bootstrap-modal.js"></script>
	<!-- custom dropdown library -->
	<script src="<%=path %>/statics/js/bootstrap-dropdown.js"></script>
	<!-- scrolspy library -->
	<script src="<%=path %>/statics/js/bootstrap-scrollspy.js"></script>
	<!-- library for creating tabs -->
	<script src="<%=path %>/statics/js/bootstrap-tab.js"></script>
	<!-- library for advanced tooltip -->
	<script src="<%=path %>/statics/js/bootstrap-tooltip.js"></script>
	<!-- popover effect library -->
	<script src="<%=path %>/statics/js/bootstrap-popover.js"></script>
	<!-- button enhancer library -->
	<script src="<%=path %>/statics/js/bootstrap-button.js"></script>
	<!-- accordion library (optional, not used in demo) -->
	<script src="<%=path %>/statics/js/bootstrap-collapse.js"></script>
	<!-- carousel slideshow library (optional, not used in demo) -->
	<script src="<%=path %>/statics/js/bootstrap-carousel.js"></script>
	<!-- autocomplete library -->
	<script src="<%=path %>/statics/js/bootstrap-typeahead.js"></script>
	<!-- tour library -->
	<script src="<%=path %>/statics/js/bootstrap-tour.js"></script>
	<!-- library for cookie management -->
	<script src="<%=path %>/statics/js/jquery.cookie.js"></script>
	<!-- calander plugin -->
	<script src='<%=path %>/statics/js/fullcalendar.min.js'></script>
	<!-- data table plugin -->
	<script src='<%=path %>/statics/js/jquery.dataTables.min.js'></script>

	<!-- chart libraries start -->
	<script src="<%=path %>/statics/js/excanvas.js"></script>
	<script src="<%=path %>/statics/js/jquery.flot.min.js"></script>
	<script src="<%=path %>/statics/js/jquery.flot.pie.min.js"></script>
	<script src="<%=path %>/statics/js/jquery.flot.stack.js"></script>
	<script src="<%=path %>/statics/js/jquery.flot.resize.min.js"></script>
	<!-- chart libraries end -->

	<!-- select or dropdown enhancer -->
	<script src="<%=path %>/statics/js/jquery.chosen.min.js"></script>
	<!-- checkbox, radio, and file input styler -->
	<script src="<%=path %>/statics/js/jquery.uniform.min.js"></script>
	<!-- plugin for gallery image view -->
	<script src="<%=path %>/statics/js/jquery.colorbox.min.js"></script>
	<!-- rich text editor library -->
	<script src="<%=path %>/statics/js/jquery.cleditor.min.js"></script>
	<!-- notification plugin -->
	<script src="<%=path %>/statics/js/jquery.noty.js"></script>
	<!-- file manager library -->
	<script src="<%=path %>/statics/js/jquery.elfinder.min.js"></script>
	<!-- star rating plugin -->
	<script src="<%=path %>/statics/js/jquery.raty.min.js"></script>
	<!-- for iOS style toggle switch -->
	<script src="<%=path %>/statics/js/jquery.iphone.toggle.js"></script>
	<!-- autogrowing textarea plugin -->
	<script src="<%=path %>/statics/js/jquery.autogrow-textarea.js"></script>
	<!-- multiple file upload plugin -->
	<script src="<%=path %>/statics/js/jquery.uploadify-3.1.min.js"></script>
	<!-- history.js for cross-browser state change on ajax -->
	<script src="<%=path %>/statics/js/jquery.history.js"></script>
	<!-- 自己的js文件 -->
	<script src="<%=path %>/statics/localjs/index.js"></script>
</body>
</html>