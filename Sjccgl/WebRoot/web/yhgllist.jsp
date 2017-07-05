<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ page import="java.net.URLEncoder"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String  path = request.getContextPath();
String  basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>easyui1.3.2/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>easyui1.3.2/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>easyui1.3.2/demo/demo.css">
<script type="text/javascript" src="<%=basePath%>easyui1.3.2/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>easyui1.3.2/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/common.js"></script>
<link href="<%=basePath%>common/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>common/css/public.css" rel="stylesheet" type="text/css" />


<style type="text/css">
	a {text-decoration: none;font-family: Simsun;}
</style>

<script type="text/javascript">
	$(function() {
		/* $.extend($.fn.validatebox.defaults.rules,{
			mobile : {// 验证手机号码 
        		validator : function(value) { 
            		return /^(13|15|18|17)\d{9}$/i.test(value); 
            	}, 
        		message : '手机号码格式不正确'
        	},
        	email:{ 
		        validator : function(value){ 
			        return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value); 
			    }, 
				message : '邮箱格式不正确'
			}
        }); */
		
		//loadGrid($('#loginname').val(),$('#username').val(),$('#mobilephone').val());
		loadGrid();
		$('#dlg').dialog('close');
		$('#treedlg').dialog('close');
	});
	
	function loadGrid(){
		$('#list_data').datagrid({
			title : '随机抽查事项清单',
			iconCls : 'icon-save',
			pageSize : 30, 
			height:403,
			pageList : [10,20,30,50],
			fitColumns : true,
			striped : true,
			singleSelect : true,
			url : '<%=basePath%>sqlhelper.do?queryid=AUTH_TB_AUTH_USER&resptype=pagejson',
			queryParams : {
				loginname : loginname,
				username : username,
				mobilephone : mobilephone
			},
			loadMsg : '数据正在加载中......',
			pagination : true,
			rownumbers : true,
			frozenColumns : [[{field : 'ck',checkbox:true}]],
			toolbar : [
				{
					text : '添加',
					iconCls : 'icon-add',
					handler : function (){
						showAddWindow();
					}
				}
				,
				{
					text : '授权',
					iconCls : 'icon-edit',
					handler : function (){
						var row = $('#list_data').datagrid('getSelected');
						if (row){
							$('#treedlg').dialog({modal:true}).dialog('open');
							$('#treedlg_userid').val(row.USERID);
			             	showTree(row.USERID);
						}else{
							alert("请选择需要授权的用户！");
						}
					}
				}
			],
			columns:[[
				{field:'checkXuhao',title:'序号',align:'center',width:30},
				{field:'checkMatters',title:'事项名称',align:'center',width:30},
				{field:'checkYj',title:'抽查依据',align:'center',width:30},
				{field:'checkBody',title:'抽查主体',align:'center',width:30},
				{field:'checkObject',title:'抽查对象',align:'center',width:30},
				{field:'checkContent',title:'抽查内容',align:'center',width:30},
				{field:'checkBl',title:'抽查比例',align:'center',width:30},
				{field:'checkPc',title:'抽查频次',align:'center',width:30},
				{field:'CZ',title:'操作',align:'center',width:30,
					formatter:function(val,rec){
						var href = "<a href=javascript:showDetail('"+rec.USERID+"');>修改</a>&nbsp;&nbsp;"+
								   "<a href=javascript:deleteUser('"+rec.USERID+"','"+rec.LOGINNAME+"');>删除</a>&nbsp;&nbsp;";
						return href;
					}
				}
			]]
		});
	}
	
	function reloadGrid(){
		loadGrid($('#loginname').val(),$('#username').val(),$('#mobilephone').val());
	}
	
	function saveUser(url){
		var username = $('#loginname_show').val();
		var flag = false;
		$.ajax({
			type: "POST",
	        url: 'findIsUsername.action?username='+username,
	        async:false,
	        success: function(data) {
				if(data == "true"){
					alert("该登录名已被注册！");
				}else{
					flag = true;
				}
	        }
	    });
		if(flag){
			if($("#myform").form('validate')){
				var params = {
					"user.userid" : $('#userid').val(),
					"user.username" : $('#username_show').val(),
					"user.loginname" : $('#loginname_show').val(),
					"user.comments" : $('#comments_show').val(),
					"user.mobilephone" : $('#mobilephone_show').val(),
					"user.email" : $('#email_show').val(),
					"user.zfrybh" : $('#zfrybh_show').val(),
					"user.password" : $('#password').val()
				};
				$.ajax({
					type: "POST",
			        url: url,
			        data: params,
			        success: function(data) {
						$('#dlg').dialog('close');
						reloadGrid();
			        }
			    });
			}else{
				alert("请填写表单！");
			}
		}
	}
	
	
	function updateUser(url){
		if($("#xgmyform").form('validate')){
			var params = {
				"user.xguserid" : $('#xguserid').val(),
				"user.xgusername" : $('#xgusername_show').val(),
				"user.xgloginname" : $('#xgloginname_show').val(),
				"user.xgcomments" : $('#xgcomments_show').val(),
				"user.xgmobilephone" : $('#xgmobilephone_show').val(),
				"user.xgzfrybh" : $('#xgzfrybh_show').val(),
				"user.xgemail" : $('#xgemail_show').val()
			};
			$.ajax({
				type: "POST",
		        url: url,
		        data: params,
		        success: function(data) {
					$('#xgdlg').dialog('close');
					reloadGrid();
		        }
		    });
		}else{
			alert("请填写表单！");
		}
	}
	
	function showDetail(userid){
		$.messager.progress({title:'请稍后',msg:'页面加载中...' });
		loadToJson("<%=basePath%>sqlhelper.do?queryid=AUTH_TB_AUTH_USER_BYID",'userid='+userid,function (data) {
			$.each(data.root,function(idx,item){
				$('#xguserid').val(item.USERID); 
				$('#xgloginname_show').val(item.LOGINNAME);
				$('#xgusername_show').val(item.USERNAME); 
				$('#xgmobilephone_show').val(item.MOBILEPHONE);
				$('#xgcomments_show').val(item.COMMENTS);
				$('#xgemail_show').val(item.EMAIL);
				$('#xgzfrybh_show').val(item.ZFRYBH);
				$('#xgpassword').val(item.PSD);
			});
			$.messager.progress('close');
			$('#xgdlg').dialog({modal:true}).dialog('open');
		});
	}
	
	function deleteUser(userid,username){
		if(username == "admin"){
			alert("该用户为系统管理员，不能删除！");
			return;
		}
		var params = {
			"user.userid" : userid
		};
		if(confirm("确认删除？")){
			$.ajax({
				type: "POST",
		        url: "<%=basePath%>deleteUser.action",
		        data: params,
		        success: function(data) {
					reloadGrid();
		        }
		    });
		}
	}
	
	
	function showTree(userid){
		$('#tt').tree({                    
			checkbox: true,  
			cascadeCheck : false,
			animate:true,                 
			url: '<%=basePath%>showUserResourceMenu.action?userid='+userid,                    
			onClick:function(node){                    
			                
			}             
		});
	}
	
	function getChecked(){
		var nodes = $('#tt').tree('getChecked');
		var s = '';
		for(var i=0; i<nodes.length; i++){
			if (s != '') {
				s += ','
			};
			s += nodes[i].id;
		}
		return s;
	}
	
	function saveUserResource(){
		var menu = getChecked();
		var userid = $('#treedlg_userid').val();
	    $.ajax({
			type: "POST",
	        url: "<%=basePath%>saveUserResourceMenu.action",
	        data: {menu : menu,userid : userid},
	        success: function(data) {
				alert("用户授权成功！");
				$('#treedlg').dialog('close');
	        }
	    });
	}
	
	$.extend($.fn.validatebox.defaults.rules, { 
		minLength: { 
			validator: function(value, param){ 
				return value.length >= param[0]; 
			}, 
			message: 'Please enter at least {0} characters.' 
		} 
	}); 
	
	function showAddWindow(){
		$('#userid').val(''); 
		$('#loginname_show').val('');
		$('#username_show').val(''); 
		$('#mobilephone_show').val('');
		$('#comments_show').val('');
		$('#email_show').val('');
		$('#dlg').dialog({modal:true}).dialog('open');
	}
</script>

</head>
<body>
<div class="main_box">
	<div id="filterDiv" class="easyui-panel" style="padding:10px;" title="检索条件" collapsible="true">
		<table cellpadding="0" cellspacing="0" border="0" class="table_list">
			<tr>
				<th align="right" width="8%">登录名：</th>
				<td width="15%">
					<input id="loginname" type="text" name="loginname"/>
				</td>
				<th align="right" width="8%">姓名：</th>
				<td width="15%">
					<input id="username" type="text" name="username"/>
				</td>
				<th align="right" width="8%">电话：</th>
				<td width="15%">
					<input id="mobilephone" type="text" name="mobilephone"/>
				</td>
				<td width="67" align="right" >
					<input type="button" name="input5" class="cx_btn" value="查 询" onclick="reloadGrid()"/>
				</td>
				<td width="15%">
				</td>
			</tr>
		</table>
	</div>
	<br/>
	<table id="list_data"></table>
</div>
 
<div id="dlg" class="easyui-dialog" title="操作" style="width:700px;height:184px;overflow: hidden;"data-options="
			iconCls: 'icon-save',
			buttons: [{
				text:'保存',iconCls:'icon-ok',handler:function(){
						saveUser('<%=basePath%>saveUser.action');
				}
			},{
				text:'关闭',iconCls:'icon-no',handler:function(){
					$('#dlg').dialog('close');
				}
			}]
		">
	<form id="myform" action="" method="post">
	<table class="table_list" id="regTable">
		<input type="hidden" name="userid" id="userid"/>
		<tr>
			<th>登录名:</th>
			<td><input type="text" name="loginname" id="loginname_show" 
				required="true" class="easyui-validatebox" missingMessage="登录名不能为空"/></td>
			<th>密码:</th>
			<td>
			<input type="password" name="password" id="password"/>
			</td>
		</tr>
		<tr>
			<th>姓名:</th>
			<td><input type="text" name="username" id="username_show"
				required="true" class="easyui-validatebox" missingMessage="姓名不能为空"/></td>
			<th>执法人员编号：</th>
			<td><input type="text" name="zfrybh" id="zfrybh_show"
				required="true" class="easyui-validatebox" missingMessage="执法人员编号不能为空"/></td>
		</tr>
		<tr>
			<th>手机:</th>
			<td><input type="text" name="mobilephone" id="mobilephone_show" 
				class="easyui-validatebox" data-options="required:true,validType:'mobile'" missingMessage="手机号码不能为空"/></td>
			<th>邮箱:</th>
			<td><input type="text" name="email" id="email_show"
				class="easyui-validatebox" data-options="validType:'email'"/></td>
		</tr>
		<tr>
			<th>备注:</th>
			<td colspan="3"><input size="90" type="text" name="comments" id="comments_show"/></td>
		</tr>
	</table>
	</form>	
</div>



<div id="xgdlg" class="easyui-dialog" closed="true" title="操作" style="width:700px;height:184px;"data-options="
			iconCls: 'icon-save',
			buttons: [{
				text:'保存',iconCls:'icon-ok',handler:function(){
						updateUser('<%=basePath%>updateUser.action');
				}
			},{
				text:'关闭',iconCls:'icon-no',handler:function(){
					$('#xgdlg').dialog('close');
				}
			}]
		">
	<form id="xgmyform" action="" method="post">
	<table class="table_list" id="regTable">
		<input type="hidden" name="xguserid" id="xguserid"/>
		<tr>
			<th>登录名:</th>
			<td><input type="text" name="xgloginname" id="xgloginname_show"
				required="true" class="easyui-validatebox" missingMessage="登录名不能为空" disabled="disabled"/>
				<input name="xgloginname" id="xgloginname_show" type="hidden"/></td>
			<th></th>
			<td></td>
		</tr>
		<tr>
			<th>姓名:</th>
			<td><input type="text" name="xgusername" id="xgusername_show"
				required="true" class="easyui-validatebox" missingMessage="姓名不能为空"/></td>
			<th>执法人员编号：</th>
			<td><input type="text" name="xgzfrybh" id="xgzfrybh_show"
				required="true" class="easyui-validatebox" missingMessage="执法人员编号不能为空"/></td>
		</tr>
		<tr>
			<th>手机:</th>
			<td><input type="text" name="xgmobilephone" id="xgmobilephone_show" 
				class="easyui-validatebox" data-options="required:true,validType:'mobile'" missingMessage="手机号码不能为空"/></td>
			<th>邮箱:</th>
			<td><input type="text" name="xgemail" id="xgemail_show"
				class="easyui-validatebox" data-options="validType:'email'"/></td>
		</tr>
		<tr>
			<th>备注:</th>
			<td colspan="3"><input size="90" type="text" name="xgcomments" id="xgcomments_show"/></td>
		</tr>
	</table>
	</form>	
</div>



<div id="treedlg" class="easyui-dialog" title="授权" style="width:400px;height:315px;" data-options="
			iconCls: 'icon-save',
			buttons: [{
				text:'保存',iconCls:'icon-ok',handler:function(){
					saveUserResource();
				}
			},{
				text:'关闭',iconCls:'icon-no',handler:function(){
					$('#treedlg').dialog('close');
				}
			}]
		">
	<input type="hidden" id="treedlg_userid"/>
	<table class="table_list">
		<tr>
			<th width="20%" valign="top" style="padding:8px;">资源列表:</th>
			<td>
				<ul id="tt" class="easyui-tree"></ul>
			</td>
		</tr>
	</table>
</div>


</body>
</html>
