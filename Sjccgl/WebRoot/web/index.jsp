<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String  path = request.getContextPath();
String  basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>长沙市商务监管双随机抽查管理系统</title>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>easyui1.3.2/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>easyui1.3.2/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>easyui1.3.2/jquery.treeview.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>easyui1.3.2/demo/demo.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>common/css/blue.css">
	<script type="text/javascript" src="<%=basePath%>easyui1.3.2/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>easyui1.3.2/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>easyui1.3.2/jquery.treeview.js"></script>
	
	<style type="text/css">
		.mya{display: block;background-color: #E0ECFF;padding: 3px;font-size: 13px;text-decoration: none;border:1px solid #95B8E7; }
	</style>
	<script type="text/javascript">
		function add(title,href){
			if ($('#tt').tabs('exists', title)){
				$('#tt').tabs('select', title);  //如果tab存在就自动选择。
		        var tab = $('#tt').tabs('getSelected');
		        $("#tt").tabs('update',{
			        tab: tab,
			        options:{
			        	content:'<iframe frameborder="0" src="'+href+'" style="width:100%;height:99%;"></iframe>'
			        }
		        });
		        tab.panel('refresh');
		    } else {//如果不存在就创建
				$('#tt').tabs('add',{
					title:title,
		         	content: '<iframe frameborder="0" src="'+href+'" style="width:100%;height:99%;"></iframe>',
		         	//iconCls:'icon-save',
		         	closable:true
		        });
		    }
		}
	
	    $(window).resize(function(){  
	        var height1 = $(window).height()-30;  
	        $("#main_layout").attr("style","width:100%;height:"+height1+"px");  
	        $("#main_layout").layout("resize",{  
	            width:"100%",  
	            height:height1+"px"  
	        });  
	    });
	    
		$(function() {
			//树状菜单生成 JQuery Treeview
			$(".filetree").treeview({
				animated : "medium",
				collapsed : false
				//collapsed菜单载入时关闭还是展开
			});
		});
		
		//非定向抽查添加
		function fdxcctj(){
			add('抽查任务','web/TaskList.jsp?is_index=2');
		}
		
		//定向抽查添加
		function dxcctj(){
			add('抽查任务','web/TaskList.jsp?is_index=1');
		}
		
		//抽查结果归档
		function ccjggd(){
			add('抽查结果归档','web/TaskSearch.jsp');
		}
		
		//市场主体信息添加
		function addScztxx(){
			add('市场主体信息库','web/dcgtInfo.jsp');
		}
		//行业监管清单
		function showHyjgqd(){
			add('随机抽查事项','web/sjccsxqd.jsp');
		}
		//执法人员新增
		function addZfry(){
			add('执法人员信息库','web/user.jsp');
		}
	</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true" background-color:#95B8E7;" id="main_layout" >
		<div data-options="region:'north'" style="height:79px;background-color:#95B8E7;">
			<%-- 
			<br/>
			<span style="font-size: 30px;padding:10px;margin-top: 20px">随机抽查管理系统</span>
			<div style="float:right;font-family:'Microsoft yahei';padding:10px;padding-top:15px;display: inline;">${userName}，欢迎您！ <a style="text-decoration: none;" href="<%=basePath%>">退出</a></div>
			--%>
			<div id="header">
				<div class="header_left" style="width:500px; ">
					<h1><img src="<%=basePath%>common/images/logo.png" style="height:65px;width:500px; padding-top: 5px;"/></h1>
				</div>
				<div style="padding-top: 35px;">
					<span>[ ${user.username }，欢迎您！]</span>&nbsp;
					<a style="text-decoration:none;" href="<%=basePath%>">[退出系统]</a>
				</div>
				<div class="header_right">
					<div class="top-r"></div>
				</div>
			</div>
		</div>
		<div data-options="region:'west',split:true" title="导航菜单" style="width:200px;">
			<div class="filetree">
					<s:iterator id="rs" value="#session.authlist">
						<s:if test="#rs.presid == 0">
								<s:iterator id="s" value="#session.authlist">
									<s:if test="#rs.resid == #s.presid">
											<!--<span class="file"> 
												<a  style="text-decoration:none;color:#000000;font-size:20px;" href="javascript:add('<s:property value="#s.resname"/>','<%=basePath %><s:property value="#s.url"/>')" >
													<s:property value="#s.resname"/>
												</a>
											</span>-->
											<button onclick="javascript:add('<s:property value="#s.resname"/>',
											'<%=basePath %><s:property value="#s.url"/>')" style="width: 170px;height: 40px" class="button">
											<s:property value="#s.resname"/></button>
											<br/><br/>
									</s:if>
								</s:iterator>
						</s:if>
					</s:iterator>
			</div>
		</div>
		<div data-options="region:'center'">
			<div id="tt" class="easyui-tabs" data-options="fit:true,border:false,plain:true" >
				<div title="主页" style="" id="home">
				
				<ul class="case">
					<s:iterator id="rs" value="#session.authlist">
					<s:if test="#rs.resid == '020100000000'">
					<li>
						<div class="link_box">
							<img class="case_w" onclick="addScztxx();" src="<%=basePath %>common/images/welcomeBg/1.png" width="110" height="75" />
						</div>
					</li>
					</s:if>
					</s:iterator>
					<s:iterator id="rs" value="#session.authlist">
					<s:if test="#rs.resid == '020200000000'">
					<li>
						<div class="link_box">
							<img class="case_w" onclick="showHyjgqd();" src="<%=basePath %>common/images/welcomeBg/2.png" width="110" height="75" />
						</div>
					</li>
					</s:if>
					</s:iterator>
					<s:iterator id="rs" value="#session.authlist">
					<s:if test="#rs.resid == '020300000000'">
					<li>
						<div class="link_box" appCode='PMLOG'>
							<img class="case_w" onclick="addZfry();" src="common/images/welcomeBg/3.png" width="110" height="75" />
						</div>
					</li>
					</s:if>
					</s:iterator>
					<s:iterator id="rs" value="#session.authlist">
					<s:if test="#rs.resid == '030300000000'">
					<li>
						<div class="link_box" appCode='PXZFPT'>
							<img class="case_w" onclick="fdxcctj();" src="<%=basePath %>common/images/welcomeBg/4.png" width="110" height="75" />
						</div>
					</li>
					</s:if>
					</s:iterator>
					<s:iterator id="rs" value="#session.authlist">
					<s:if test="#rs.resid == '030300000000'">
				    <li>
						<div class="link_box" appCode='STAMP'>
							<img class="case_w" onclick="dxcctj();" src="<%=basePath %>common/images/welcomeBg/5.png" width="110" height="75" />
						</div>
					</li>
					</s:if>
					</s:iterator>
					<s:iterator id="rs" value="#session.authlist">
					<s:if test="#rs.resid == '030400000000'">
					<li>
						<div class="link_box">
							<img class="case_w" onclick="ccjggd();" src="<%=basePath %>common/images/welcomeBg/6.png" width="110" height="75" />
						</div>
					</li>
					</s:if>
					</s:iterator>
				</ul>
				<img src="<%=basePath %>common/images/welcomeBg/0.png" style="padding-left: 35px;padding-top: 10px"/>
				</div>
			</div>
		</div>
		
		<div data-options="region:'south',split:true" style="height:35px;background-color:#95B8E7;">
			<div style="position: absolute;margin-left: 540px;margin-top: 7px;">Copyright © 2015 - 2016  长沙市商务行政执法支队 版权所有</div>
		</div>
	</div>
</body>
</html>
