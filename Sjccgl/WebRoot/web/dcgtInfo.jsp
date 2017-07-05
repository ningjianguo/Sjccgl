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
			url : '<%=basePath%>queryDcgtList',
			pageSize : 10,
			pageList : [ 5, 10, 15, 20 ],
			nowrap : true
		})
		init();
	});
	
	//初始化表格
	function init(){
		$('#dlg').dialog('close');
		$('#dlg-excel').dialog('close');
	}
	
	//转换时间格式
	function formatDatebox(value){
		 if (value == null || value == '') {    
       			 return '';    
		    }    
		  	var date = new Date();
		    date.setTime(value.time);
		    var d = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
		    return d; 
	}

	//判断是否
	function formatYorN(value){
		 if (value == null || value == '') {    
       			 return '';    
		  }    
		  if(value == '1'){
		      return '是'; 
		  }	
		   if(value == '0'){
		      return '否'; 
		  }		
	}
	
	//事项类别格式
	function formatHy(value){
	
	     var text="";
	     
		 if (value == null || value == '') {    
       			 return '';    
		  }else{
		  
		   
		    $.ajax({
				type:"post",
				url: '<%=basePath%>optionType',
				data: null,
				async:false,
				success: function(data){
				
				 var cc = jQuery.parseJSON(data); 
				 
				 for(var o in cc){  
				
					if(value == cc[o].checkId){
					
						text = cc[o].checkMatters;
						
						}
					}  
			    }  
		  }); 
		     
		  return text;
		}
		  	
	}
	
	
	//操作信息
	function operateDcgtInfo(flag,url){
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
	
	//修改信息
	function editDcgtInfo() {
			var row = $('#dg').datagrid('getSelected');
			if (row) {
				$('#dlg').dialog('open').dialog('center').dialog('setTitle',
						'编辑信息');

				var  cjsjdate =	formatDatebox(row.cjsj);
				$('#myform').form('load', row);

				$("#cjsj").val(cjsjdate);
				$('#save').attr("onclick","operateDcgtInfo('edite','<%=basePath%>updateDcgtInfo')");
				
			}else{
			  	$.messager.show({
						title : '错误',
						msg : '请先选中行!'
				});
			}
		}
		
	//删除信息
	function deleteDcgtInfo() {
	
			var row = $('#dg').datagrid('getSelected');
			var checkedItems = $('#dg').datagrid('getChecked');
			var items = new Array(); //创建一个数组
				$.each(checkedItems, function(index, item) {
					items.push(item.dcgtid);
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
									$.post('<%=basePath%>deleteDcgtInfo', {
										dcgtid : items[index]
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
	function addDcgtInfo(){
		$('#dlg').dialog('open').dialog('center').dialog('setTitle',
						'添加信息');
	 $("#myform").form('clear');
		$('#save').click(function(){
			$('#save').attr("onclick","operateDcgtInfo('add','<%=basePath%>saveDcgtInfo')");
		});
	}
	
	
 // 时间框格式
	function ww3(date){  
            var y = date.getFullYear();  
            var m = date.getMonth()+1;  
            var d = date.getDate();  
            var h = date.getHours();  
            var min = date.getMinutes();  
            var sec = date.getSeconds();  
            var str = y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);  
            return str;  
        }  
        function w3(s){  
            if (s){
             return new Date();  
             }
            var y = s.substring(0,4);  
            var m =s.substring(5,7);  
            var d = s.substring(8,10);  
            if (!isNaN(y) && !isNaN(m) && !isNaN(d)){  
                return new Date(y,m-1,d);  
            } else {  
                return new Date();  
            }  
        }  
        
     //excel导入
	function importExcelFile(){
		var formData = new FormData($("#fileForm")[0]);
		$.ajax({
			  url: "<%=basePath%>importDcgtInfo",
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
		window.location.href="<%=basePath%>exportDcgtInfo";
	}
</script>

<body>
	<table id="dg" title="市场主体信息库" class="easyui-datagrid" style="height:403;"
		toolbar="#toolbar" pagination="true" rownumbers="true"
		fitColumns="true" singleSelect="false">
		<thead>
			<tr>
				<th field="dcgtid" checkbox="true"></th>
				<th field="gsmc" align="center">公司名称</th>
				<th field="basj" align="center">备案时间</th>
				<th field="dz" align="center">地址</th>
				<th field="zczj" align="center">注册资金</th>
				<th field="zsbh" align="center">证书编号</th>
				<th field="qyxz" align="center">企业性质</th>
				<th field="jylx" align="center">经营类型</th>
				<th field="lxr" align="center">联系人</th>
				<th field="hy" align="center" formatter="formatHy">事项类别</th>
				<th field="klb" align="center">卡类别</th>
				<th field="bajgmc" align="center">备案机构名称</th>
				<th field="bz" align="center">备注</th>
				<th field="lxdh" align="center">联系电话</th>
				<th field="lxcz" align="center">联系传真</th>
				<th field="fr" align="center">法人</th>
				<th field="zmj" align="center">总面积</th>
				<th field="snmj" align="center">室内面积</th>
				<th field="zscd" align="center">展示场地面积</th>
				<th field="sfjbzxss" align="center" formatter="formatYorN">是否具备照相、指纹录入设备</th>
				<th field="sfjbgfgl" align="center" formatter="formatYorN">是否具备规范的内部管理制度</th>
				<th field="jyl" align="center">交易量</th>
				<th field="xkzbm" align="center">许可证编码</th>
				<th field="pzsj" align="center">批准时间</th>
				<th field="yb" align="center">邮编</th>
				<th field="cjsj"  align="center" formatter="formatDatebox">创建时间</th>
				<th field="cjr" align="center">创建人</th>
				<th field="sfsc" align="center" formatter = "formatYorN">是否删除</th>
				<th field="gszcdjh" align="center">工商注册登记号</th>
				<th field="wfjl" align="center">违法记录</th>
				<th field="iszd" align="center" formatter="formatYorN">是否重点检查</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar">
		<a href="javascript:addDcgtInfo()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加信息</a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="javascript:editDcgtInfo()">编辑信息</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteDcgtInfo()">移除信息</a> 
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
	
	<div id="dlg" class="easyui-dialog" title="添加事项" style="width:700px;height:514px;overflow: hidden;" buttons="#dlg-buttons" data-options="modal:true">
	<form id="myform" action="" method="post">
	<table class="table_list" id="regTable">
		<input type="hidden" name="dcgtid" id="dcgtid" value=""/>
		<input type="hidden" name="cjr" id="cjr" value=""/>
		<input type="hidden" name=cjsj id="cjsj" value=""  />
		<tr>
			<th>公司名称:</th>
			<td><input  name="gsmc" id="gsmc"  type="text" 
				required="true" class="easyui-validatebox" missingMessage="公司名称不能为空"/></td>
			<th>备案时间:</th>
			<td>
			<input id="basj" type="text" name="basj" class="easyui-datebox" data-options="formatter:ww3,parser:w3"></td>
			
		</tr>
		
		<tr>
			<th>地址:</th>
			<td><input type="text" name="dz" id="dz"
				required="true" class="easyui-validatebox" missingMessage="地址不能为空"/></td>
			<th>注册资金(万元)：</th>
			<td><input type="text" name="zczj" id="zczj"
				required="true" class="easyui-validatebox" missingMessage="注册资金不能为空"/></td>
		</tr>
		
		<tr>
			<th>证书编号:</th>
			<td><input type="text" name="zsbh" id="zsbh" 
				required="true" class="easyui-validatebox"  missingMessage="证书编号不能为空"/></td>
			<th>企业性质:</th>
			<td><input type="text" name="qyxz" id="qyxz" /></td>
		</tr>
		<tr>
			<th>经营类型:</th>
			<td><input type="text" name="jylx" id="jylx" 
				required="true" class="easyui-validatebox" missingMessage="经营类型不能为空"/></td>
			<th>联系人:</th>
			<td>
			<input type="text" name="lxr" id="lxr"
			required="true" class="easyui-validatebox" missingMessage="联系人不能为空"/></td>
		</tr>
		<tr>
			<th>事项类别:</th>
			<td>
				<input  id="hy" name="hy" class="easyui-combobox" required="true"
					editable="false" 
					data-options="valueField:'checkId',textField:'checkMatters',url:'<%=basePath%>optionType',panelHeight:'auto'">
				
				</td>
			<th>卡类别:</th>
			<td>
			<input type="text" name="klb" id="klb"/>
			</td>
		</tr>
		<tr>
			<th>备案机构名称:</th>
			<td><input type="text" name="bajgmc" id="bajgmc" /></td>
			<th>备注:</th>
			<td>
			<input type="text" name="bz" id="bz"/>
			</td>
		</tr>
		<tr>
			<th>联系电话:</th>
			<td><input type="text" name="lxdh" id="lxdh" 
			required="true" class="easyui-validatebox" data-options="required:true,validType:'mobile'" missingMessage="联系电话不能为空"/></td>
			<th>联系传真:</th>
			<td>
			<input type="text" name="lxcz" id="lxcz"/>
			</td>
		</tr>
		<tr>
			<th>法人:</th>
			<td><input type="text" name="fr" id="fr" /></td>
			<th>总面积:</th>
			<td>
			<input type="text" name="zmj" id="zmj"/>
			</td>
		</tr>
		<tr>
			<th>室内面积:</th>
			<td><input type="text" name="snmj" id="snmj" /></td>
		    <th>展示场地面积:</th>
			<td><input type="text" name="zscd" id="zscd" /></td>
		</tr>
		<tr>
			
			<th>是否具备照相、指纹录入设备:</th>
			<td>
			<input class="easyui-combobox" name="sfjbzxss" id="sfjbzxss" panelHeight="auto" style="width:80px;"   
                   data-options="valueField: 'value',textField: 'label',data: [{  
                   label: '是',  
                   value: '1',  
                   selected:true},   //是做为默认值  
                   {label: '否',  
                   value: '0'}]"                    
                 />
			</td>
			<th>是否具备规范的内部管理制度:</th>
			<td>
			<input class="easyui-combobox" name="sfjbgfgl" id="sfjbgfgl" panelHeight="auto" style="width:80px;"   
                   data-options="valueField: 'value',textField: 'label',data: [{  
                   label: '是',  
                   value: '1',  
                   selected:true},   //是做为默认值  
                   {label: '否',  
                   value: '0'}]"                    
                 />
			</td>
		</tr>
		<tr>
			<th>交易量:</th>
			<td>
			<input type="text" name="jyl" id="jyl"/>
			</td>
			<th>许可证编码:</th>
			<td><input type="text" name="xkzbm" id="xkzbm" 
				required="true" class="easyui-validatebox" missingMessage="许可证编码不能为空"/></td>
		</tr>
		<tr>
			<th>批准时间:</th>
			<td>
			<input id="pzsj" type="text" name="pzsj" class="easyui-datebox" data-options="formatter:ww3,parser:w3">
			</td>
			<th>邮编:</th>
			<td><input type="text" name="yb" id="yb" /></td>
		</tr>
		<tr>
			<th>是否删除</th>
			<td>
			<input class="easyui-combobox" name="sfsc" id="sfsc" panelHeight="auto" style="width:80px;"   
                   data-options="valueField: 'value',textField: 'label',data: [{  
                   label: '是',  
                   value: '1',  
                   selected:true},   //是做为默认值  
                   {label: '否',  
                   value: '0'}]"                    
                 />
			</td>
			
			<th>工商注册登记号:</th>
			<td><input type="text" name="gszcdjh" id="gszcdjh" 
				required="true" class="easyui-validatebox" missingMessage="工商注册登记号不能为空"/></td>
		</tr>
		
		<tr>
			
			<th>违法记录:</th>
			<td>
			<input type="text" name="wfjl" id="wfjl"/>
			</td>
			
			<th>是否重点检查:</th>
			<td>
			    <input class="easyui-combobox" name="iszd" id="iszd" panelHeight="auto" style="width:80px;"   
                   data-options="valueField: 'value',textField: 'label',data: [{  
                   label: '是',  
                   value: '1',  
                   selected:true},   //是做为默认值  
                   {label: '否',  
                   value: '0'}]"                    
                 />
			</td>	
		</tr>

	
	</table>
	</form>	
</div>
</body>
</html>
