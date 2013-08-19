<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>登录</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css">
    <script type="text/javascript" src="extjs/ext-all.js"></script>
    <script type="text/javascript" src="extjs/locale/ext-lang-zh_CN.js"></script>
    <script type="text/javascript" src="login.js"></script>
    <style type="text/css">
    	div#center{
    		width:350px;
    		height:250px;
    		position:absolute;
    		top:50%;
    		left:50%;
    		margin:-125 0 0 -170;
    	}
	.x-form-item-label{
		font-size:14px !important;
		font-weight:400;
	}
	.x-btn-inner{
		font-size:14px !important;
		font-weight:500 !important;
	}
    </style>
  </head>
  	
  <body>
    <div id="center"></div>
  </body>
</html>
