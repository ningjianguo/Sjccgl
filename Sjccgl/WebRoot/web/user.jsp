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
<link rel="stylesheet" type="text/css" href="<%=basePath%>easyui1.3.2/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>easyui1.3.2/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>easyui1.3.2/demo/demo.css"/>
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
	
		loadGrid();
	
	});
	

	
	//初始化表格
	function loadGrid(){
        $('#dg').datagrid({
			url : '<%=basePath%>queryUserList',
			  queryParams : {
				loginname : $("#loginName1").val(),
				username : $("#userName1").val(),
				mobilephone : $("#mobilePhone1").val()
			},
			pageSize : 10,
			pageList : [ 5, 10, 15, 20 ],
			nowrap : true 
		});
		
		$('#dlg').dialog('close');
	    $('#dlg-excel').dialog('close');
	}

	
	//操作信息
	function operateUser(flag,url){
		if(flag == 'add'){
		if($("#myform").form('validate')){
			$.ajax({
				type:"post",
				url: url,
				data: $("#myform").serialize(),
				success: function(data){
					if(data == "success"){
						$('#dlg').dialog('close');
						$('#dg').datagrid('reload');
					}
				},
				error: function(request){
					$.messager.show({
								title : '错误',
								msg : '添加失败,请重新添加!'
							});
				}
			})
			
			}else{
			   $.messager.show({
						title : '错误',
						msg : '字段未填充完整!'
				});
			}
		}else if(flag == 'edite'){
			$.ajax({
				type:"post",
				url: url,
				data: $("#myform").serialize(),
				success: function(data){
					if(data == "success"){
						$('#dlg').dialog('close');
						$('#dg').datagrid('reload');
					}
				},
				error: function(request){
					$.messager.show({
								title : '错误',
								msg : '编辑失败,请重新编辑!'
							});
				}
			})
		}
	}
	
	//修改信息
	function editUser() {
			var row = $('#dg').datagrid('getSelected');
			if (row) {
				$('#dlg').dialog('open').dialog('center').dialog('setTitle',
						'编辑信息');
				$('#myform').form('load', row);
				$('#save').attr("onclick","operateUser('edite','<%=basePath%>updateUser')");
				
			}else{
			  	$.messager.show({
						title : '错误',
						msg : '请先选中行!'
				});
			}
		}
		
	//删除信息
	function deleteUser() {
	
			var row = $('#dg').datagrid('getSelected');
			var checkedItems = $('#dg').datagrid('getChecked');
			var items = new Array(); //创建一个数组
				$.each(checkedItems, function(index, item) {
					items.push(item.userid);
				});
				$.extend($.messager.defaults,{
	                ok:"确定",
	                cancel:"取消",
	                modal:true
	            });
	            
	            
			if (row) {
			$.messager.confirm('警告',
							"您确定要移除这"+items.length+"条数据吗?",
						function(r) {
							if (r) {
								for (index in items) {
									$.post('<%=basePath%>deleteUser', {
										userid : items[index]
									}, function(data) {
										if (data.info == "success") {
										
											$('#dg').datagrid('reload');
										} else {
											$.messager.show({
												title : '错误',
												msg : '删除失败，请重新删除!'
											});
										}
									}, 'json');
								}
							}
							}); 
			}else{
			  	$.messager.show({
						title : '错误',
						msg : '请先选中行!'
				});
			}
		}	
		
	//添加信息
	function addUser(){
		$('#dlg').dialog('open').dialog('center').dialog('setTitle',
						'添加信息');
	 $("#myform").form('clear');
		$('#save').click(function(){
			$('#save').attr("onclick","operateUser('add','<%=basePath%>saveUser')");
		});
	}
	
        
     //excel导入
	function importExcelFile(){
		var formData = new FormData($("#fileForm")[0]);
		$.ajax({
			  url: "<%=basePath%>importUser",
			  type: "post",
			  data: formData,
			  contentType: false, //必须false才会避开jQuery对 formdata 的默认处理 XMLHttpRequest会对 formdata 进行正确的处理 
			  processData: false, //必须false才会自动加上正确的Content-Type
			  success: function (data) {
			  		if(data == "success"){
			  			$('#dlg-excel').dialog('close');
						$('#dg').datagrid('reload');
						$.messager.show({
							title : '成功',
							msg : '导入成功!'
						});
			  		}else{
			  			$.messager.show({
							title : '错误',
							msg : '上传失败!'
					});
			  		}
				  },
			  error:function(request){
			  	$.messager.show({
							title : '错误',
							msg : '上传失败!'
					});
			 	 }
			  });
	}
	 //导出
	   function exportExcelFile(){
		window.location.href="<%=basePath%>exportUser";
	}
</script>

<body>

	<div id="filterDiv" class="easyui-panel" style="padding:10px;" title="检索条件" collapsible="true">
	   <form  id="queryForm">
		<table cellpadding="0" cellspacing="0" border="0" class="table_list">
			<tr>
				<th align="right" width="8%">登录名：</th>
				<td width="15%">
					<input id="loginName1" type="text" name="loginname" />
				</td>
				<th align="right" width="8%">姓名：</th>
				<td width="15%">
					<input id="userName1" type="text" name="username" />
				</td>
				<th align="right" width="8%">手机电话：</th>
				<td width="15%">
					<input id="mobilePhone1" type="text" name="mobilephone" />
				</td>
				<td width="67" align="right" >
					<input type="button" name="input5" class="cx_btn" value="查 询" onclick="loadGrid()"/>
				</td>
				<td width="15%">
				</td>
			</tr>
		</table>
		</form>
	</div>

	<table id="dg" title="执法人员信息库" class="easyui-datagrid" style="height:403;"
		toolbar="#toolbar" pagination="true" rownumbers="true"
		fitColumns="true" singleSelect="false">
		<thead>
			<tr>
				<th field="userid" checkbox="true"></th>
				<th field="username" align="center" width="30">用户名</th>
				<th field="loginname" align="center" width="30">登录名</th>
				<th field="comments" align="center" width="30">备注</th>
				<th field="gender" align="center" width="30">性别</th>
				<th field="mobilephone" align="center" width="30">手机号码</th>
				<th field="telephone" align="center" width="30">电话号码</th>
				<th field="email" align="center" width="30">邮箱</th>
				<th field="zfrybh" align="center" width="30">执法人员编号</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar">
		<a href="javascript:addUser()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加信息</a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="javascript:editUser()">编辑信息</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:deleteUser()">移除信息</a> 
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-excel" plain="true" onclick="javascript:$('#dlg-excel').dialog('open')">Excel导入</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-excel" plain="true" onclick="javascript:exportExcelFile()">Excel导出</a>
	</div>
	
	
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton c6"
			iconCls="icon-ok" style="width:90px" id="save">保存</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')"
			style="width:90px">取消</a>
	</div>
	
	<div id="dlg-excel-buttons">
		<a href="javascript:importExcelFile()" class="easyui-linkbutton c6" iconCls="icon-excel" style="width:100px;">Excel导入</a>
	</div>
	
	<div id="dlg-excel" class="easyui-dialog" title="Excel导入" style="width:300px;height:100px;overflow: hidden;" buttons="#dlg-excel-buttons" data-options="modal:true">
		<form id="fileForm" action="" method="post" enctype="multipart/form-data">
			<tr>
				<td>Excel文件:</td>		
				<td><input type="file" name="excelFile" accept="application/vnd.ms-excel" style="margin-top: 4px; width: 50%;"/></td>
			</tr>
		</form>
	</div>
	
	<div id="dlg" class="easyui-dialog" title="添加信息" style="width:700px;height:230px;overflow: hidden;" buttons="#dlg-buttons" data-options="modal:true">
	<form id="myform" action="" method="post">
	<table class="table_list" id="regTable">
	
		<input type="hidden" name="userid" id="userid" value=""/>
		
		<tr>
			<th>用户名:</th>
			<td><input type="text" name="username" id="username"
				required="true" class="easyui-validatebox" missingMessage="用户名不能为空"/></td>
			<th>登录名：</th>
			<td><input type="text" name="loginname" id="loginname"
				required="true" class="easyui-validatebox" missingMessage="登录名为空"/></td>
		</tr>
		
		<tr>
			<th>密码:</th>
			<td><input type="password" name="psw" id="psw" 
				required="true" class="easyui-validatebox"  missingMessage="密码不能为空"/></td>
			<th>执法人员编号:</th>
			<td><input type="text" name="zfrybh" id="zfrybh" 
				required="true" class="easyui-validatebox"  missingMessage="执法人员编号不能为空"/></td>	
			
		</tr>
		
		<tr>
			<th>性别:</th>
			<td><input type="text" name="gender" id="gender" 
				required="true" class="easyui-validatebox"  missingMessage="性别不能为空"/></td>
				
			<th>手机号码:</th>
			<td><input type="text" name="mobilephone" id="mobilephone" 
				required="true" class="easyui-validatebox"  missingMessage="手机号码不能为空"/></td>
		</tr>
		
	    <tr>
			<th>电话号码:</th>
			<td><input type="text" name="telephone" id="telephone" 
			class="easyui-validatebox" data-options="required:true,validType:'mobile'" missingMessage="手机号码不能为空"/></td>
				
			<th>邮箱:</th>
			<td><input type="text" name="email" id="email" 
				class="easyui-validatebox" data-options="validType:'email'"/></td>
		</tr>
	    
	    <tr>
			<th>备注:</th>
			<td><input type="text" name="comments" id="comments" 
			size="90" type="text" name="xgcomments" /></td>
		</tr>
		
	</table>
	</form>	
</div>
</body>
</html>
