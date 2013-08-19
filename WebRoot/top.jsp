<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'top.jsp' starting page</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
	.x-toolbar {
    border: 0 solid !important;
	}
	.x-btn-inner {
		font-size: 16px !important;
		color:#3cacfe !important;
		font-weight:900 !important;
	}
	.x-btn-icon{		
		height:24px !important;
		width:24px !important;
	}	
	</style>
	<link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css">
    <script type="text/javascript" src="extjs/ext-all-debug.js"></script>
    <script type="text/javascript" src="extjs/locale/ext-lang-zh_CN.js"></script>
    <script type="text/javascript">
    	Ext.onReady(function(){
    		Ext.create('Ext.toolbar.Toolbar',{
    			renderTo:'td1',
    			height:32,
    			style:"background-color:transparent !important;background-image:none !important",
    			items:[
    			{    				
    				text:"安全退出",
    				icon:'images/logout5.png',
    				width:105,
    				height:30,
    				handler:function(){
    					var form=document.getElementById("logout");
    					form.submit();
    				}
    			}]
    		});
    	});    	
    </script>
  </head>  
  <body style="background:url(images/title111.png) no-repeat">
  <form id="logout" action="loginAction!logout" target='_parent'>
  	<table height="100%" width="100%" >
    	<tr >
    		<td rowspan="2">&nbsp;</td>
    		<TD height="60px" colspan="2" align="right">
    			<iframe width="420" scrolling="no" height="60" frameborder="0" allowtransparency="true" src="http://www.tianqi.com/index.php?c=code&id=12&color=%23ffffff&icon=1&num=5"></iframe>
    		</td>
    	</tr>
    	<tr>
    	<td width="320" align="right" valign="middle" style="padding-right:15px" class="x-btn-inner">欢迎您：${user.realName}</td>
    	<td id="td1" width="120px" height="32px" valign="middle"></td>	
    	</tr>
    </table>
  </form> 
  </body>
</html>
