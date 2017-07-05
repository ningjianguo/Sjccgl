<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8"/>
	<title>长沙市商务监管双随机抽查管理系统</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1"/>
	<link type="text/css" rel="stylesheet" href="<%=basePath %>common/css/login3.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>easyui1.3.2/themes/default/easyui.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>easyui1.3.2/themes/icon.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>easyui1.3.2/demo/demo.css"/>
	<script type="text/javascript" src="<%=basePath%>easyui1.3.2/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>easyui1.3.2/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>easyui1.3.2/locale/easyui-lang-zh_CN.js"></script>
	 <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/> 
		<meta name="viewport" content="width=device-width, initial-scale=1.0"/> 
        <title>Custom Login Form Styling</title>
        <meta name="description" content="Custom Login Form Styling with CSS3" />
        <meta name="keywords" content="css3, login, form, custom, input, submit, button, html5, placeholder" />
        <meta name="author" content="Codrops" />
        <link rel="shortcut icon" href="../favicon.ico"/> 
        <link rel="stylesheet" type="text/css" href="css/style.css" />
        <script src="js/modernizr.custom.63321.js"></script>
	<script type="text/javascript">
		document.onkeydown = keyDownSearch;  
	    
	    function keyDownSearch(e) {    
	        var theEvent = e || window.event;    
	        var code = theEvent.keyCode || theEvent.which || theEvent.charCode;    
	        if (code == 13) {    
	            login();
	            return false;    
	        }    
	        return true;    
	    }
	
		function login(){
			if(document.getElementById("loginName").value==""){
				alert("请输入用户名");
				document.getElementById("loginName").focus();
				return;
			}
			
			if(document.getElementById("passWord").value==""){
				alert("请输入密码");
				document.getElementById("passWord").focus();
				return;
			}
			
			document.getElementById("loginForm").submit();
		}
		
		function showLoginDialog(){
			document.getElementById("dlg").style.display="";
		}
		
		function closeLogin(){
			document.getElementById("dlg").style.display="none";
		}
	</script> 
</head>
<body style="overflow: hidden;height: 100%;background-color: #ffffff">
<br/><br/>
<span style="font-family: 迷你简粗宋;font-size: 20px;padding-left: 700px"></span>
<a href="javascript:showLoginDialog();" style="font-family: 迷你简粗宋;font-size: 20px;float: right;padding-right: 50px;">点击登录</a>
<div style="background-color: #15B835;height: 80%;width: 100%;vertical-align: middle;">
	<div style="position: absolute;padding: 20px;">
		<img src="<%=basePath %>common/images/4.png"/>
	</div>
	<div style="text-align: center;padding-top: 120px;">
		<img src="<%=basePath %>common/images/7.png"/>
	</div>
</div>
	
<div class="container" style="display: none;" id="dlg">
	<section class="main">
		<form class="form-2" id="loginForm" action="login.action" method="post">
			<h1><span class="log-in">登 录&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a onclick="closeLogin();" style="cursor: pointer;font-size: 20px">×</a></span> </h1>
			<p class="float">
				<label for="login"><i class="icon-user"></i>账号</label>
				<input type="text" id="loginName" name="loginName" placeholder="请输入账号">
				<label for="password"><i class="icon-lock"></i>密码</label>
				<input type="password" id="passWord" name="passWord" placeholder="请输入密码" class="showpassword">
			</p>
			<br/>
			<p class="clearfix"> 
				<input type="submit" name="submit" onclick="login();" value="登 录">
			</p>
		</form>​​
	</section>
</div>
</body>
</html>