<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>SL会员商城</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="Charisma, a fully featured, responsive, HTML5, Bootstrap admin template.">
	<meta name="author" content="Muhammad Usman">
	<%String path = request.getContextPath(); %>
	<!-- The styles -->
	<link  href="<%=path %>/statics/css/bootstrap-cerulean.css" rel="stylesheet">
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
					<h2>对不起，您没有权限访问!请返回<a href="main.html">首页</a></h2>
				</div><!--/span-->
			</div><!--/row-->
			
			<div class="row-fluid">
				<div class="well span5 center login-box">
					<img src="<%=path %>/statics/img/jg.png"/>
				</div><!--/span-->
			</div><!--/row-->
				</div><!--/fluid-row-->
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
	<script src='js/fullcalendar.min.js'></script>
	<!-- data table plugin -->
	<script src='js/jquery.dataTables.min.js'></script>

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
	
		
</body>
</html>


