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
		$('#dg').datagrid({
			url : "<%=basePath%>queryMattersType",
			pageSize : 10,//默认选择的分页是每页15行数据  
			pageList : [ 5, 10, 15, 20 ],//可以选择的分页集合  
			nowrap : true//设置为true，当数据长度超出列宽时将会自动截取  
		});
		init();
	});
	
	//初始化表格
	function init(){
		$('#dlg').dialog('close');
		$('#dlg-excel').dialog('close');
	}
	
	//判断表单是否填充完整
	function isFullForm(){
		var checkMatters = $('#checkMatters').val();
		var checkYj = $('#checkYj').val();
		var checkBody = $('#checkBody').val();
		var checkObject = $('#checkObject').val();
		var checkContent = $('#checkContent').val();
		var checkBl = $('#checkBl').val();
		var checkPc = $('#checkPc').val();
		if(checkMatters==""||checkYj==""||checkBody==""||checkObject==""||checkContent==""||checkBl==""||checkPc==""){
			return false;
		}else{
			return true;
		}
	}
	
	var count = 0;
	//操作事项
	function operateMattersType(flag,url){
		if(!isFullForm()){
			$.messager.show({
						title : '错误',
						msg : '字段未填充完整!'
				});
				return ;
		}
		if(flag == 'add'){
			$.ajax({
				type:"post",
				url: url,
				data: $('#myform').serialize(),
				async:false,
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
		}else if(flag == 'edite'){
			$.ajax({
				type:"post",
				url: url,
				data: $('#myform').serialize(),
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
	
	//添加事项
	function addMattersType(){
		$('#dlg').dialog('open').dialog('center').dialog('setTitle',
						'添加事项');
		$('#myform input').val('');
		$('#save').attr("onclick","operateMattersType('add','<%=basePath%>addMattersType')");
	}
	
	//删除事项
	function delMattersType(){
		var row = $('#dg').datagrid('getSelected');
		var checkedItems = $('#dg').datagrid('getChecked');
		var items = new Array(); //创建一个数组
			$.each(checkedItems, function(index, item) {
				items.push(item.checkId);
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
								$.post('<%=basePath%>delMattersType', {
									checkId : items[index]
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
	
	//修改事项
	function editMattersType() {
			var row = $('#dg').datagrid('getSelected');
			if (row) {
				$('#dlg').dialog('open').dialog('center').dialog('setTitle',
						'编辑事项');
				$('#myform').form('load', row);
				$('#save').attr("onclick","operateMattersType('edite','<%=basePath%>editeMattersType')");
			}else{
				$.messager.show({
						title : '错误',
						msg : '请先选中行!'
				});
			}
		}
	
	//excel导入
	function importExcelFile(){
		var formData = new FormData($("#fileForm")[0]);
		$.ajax({
			  url: "<%=basePath%>importMattersType",
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
							msg : '导入失败!'
					});
			  		}
				  },
			  error:function(request){
			  	$.messager.show({
							title : '错误',
							msg : '导入失败!'
					});
			 	 }
			  });
	}
	
	//excel导出
	function exportExcelFile(){
		window.location.href="<%=basePath%>exportMattersType";
	}
</script>

<body>
	<table id="dg" title="随机抽查事项清单" class="easyui-datagrid" style="height:403;"
		toolbar="#toolbar" pagination="true" rownumbers="true"
		fitColumns="true" singleSelect="false">
		<thead>
			<tr>
				<th field="checkId" checkbox="true"></th>
				<th field="checkXuhao" width="30" align="center">序号</th>
				<th field="checkMatters" width="30" align="center">事项名称</th>
				<th field="checkYj" width="30" align="center">抽查依据</th>
				<th field="checkBody" width="30" align="center">抽查主体</th>
				<th field="checkObject" width="30" align="center">抽查对象</th>
				<th field="checkContent" width="30" align="center">抽查内容</th>
				<th field="checkBl" width="30" align="center">抽查比例</th>
				<th field="checkPc" width="30" align="center">抽查频次</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar">
		<a href="javascript:addMattersType()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加事项</a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="javascript:editMattersType()">编辑事项</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delMattersType()">移除事项</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-excel" plain="true" onclick="javascript:$('#dlg-excel').dialog('open')">Excel导入</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-excel" plain="true" onclick="javascript:exportExcelFile()">Excel导出</a>
	</div>
	
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" style="width:90px" id="save">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">取消</a>
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
	
	<div id="dlg" class="easyui-dialog" title="添加事项" style="width:700px;height:184px;overflow: hidden;" buttons="#dlg-buttons" data-options="modal:true">
	<form id="myform" action="" method="post">
	<table class="table_list" id="regTable">
		<input type="hidden" name="taskId" id="checkId" value=""/>
		<input type="hidden" name="checkXuhao" id="checkXuhao" value=""/>
		<tr>
			<th>事项名称:</th>
			<td><input type="text" name="checkMatters" id="checkMatters" 
				required="true" class="easyui-validatebox" missingMessage="事项名称不能为空" value=""/></td>
			<th>抽查依据:</th>
			<td>
			<input type="text" class="easyui-validatebox" name="checkYj" id="checkYj" required="true" missingMessage="抽查依据不能为空" value=""/>
			</td>
		</tr>
		<tr>
			<th>抽查主体:</th>
			<td><input type="text" name="checkBody" id="checkBody"
				required="true" class="easyui-validatebox" missingMessage="抽查主体不能为空" value=""/></td>
			<th>抽查对象:</th>
			<td><input type="text" name="checkObject" id="checkObject"
				required="true" class="easyui-validatebox" missingMessage="抽查对象不能为空" value=""/></td>
		</tr>
		<tr>
			<th>抽查比例:</th>
			<td><input type="text" name="checkBl" id="checkBl" 
				class="easyui-validatebox" maxlength="3" required="true" missingMessage="抽查比例不能为空" value=""/></td>
			<th>抽查频次:</th>
			<td><input type="text" name="checkPc" id="checkPc"
				class="easyui-validatebox" maxlength="3" required="true" missingMessage="抽查频次不能为空" value=""/></td>
		</tr>
		<tr>
			<th>抽查内容:</th>
			<td colspan="3"><input size="90" type="text" name="checkContent" id="checkContent" class="easyui-validatebox" required="true" missingMessage="抽查内容不能为空" value="${checkContent }"/></td>
		</tr>
	</table>
	</form>	
</div>
</body>
</html>
