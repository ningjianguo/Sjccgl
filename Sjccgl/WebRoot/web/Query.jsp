<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ page import="java.net.URLEncoder"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
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
	function myformatter(date){
		var y = date.getFullYear();
		var m = date.getMonth()+1;
		var d = date.getDate();
		return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
	}
	
	function myparser(s){
		if (!s) return new Date();
		var ss = (s.split('-'));
		var y = parseInt(ss[0],10);
		var m = parseInt(ss[1],10);
		var d = parseInt(ss[2],10);
		if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
			return new Date(y,m-1,d);
		}else{
			return new Date();
		}
	}
</script>

<script type="text/javascript">
	$(function() {
		loadDcgtlxList();
	});
	
	function loadDcgtlxList(){
		$.ajax({
			type:"POST",
	        url:'<%=basePath%>optionType',
	        async:true,
	        success: function(data) {
				var values = eval(data);
				$('#check_id').combobox('loadData',values);
	        }
    	});
	}
	
	function loadGrid(check_id,startTime,endTime){
		$('#list_data').datagrid({
			title : '数据统计',
			iconCls : 'icon-save',
			pageSize : 20, 
			height:403,
			pageList : [10,20,30,50],
			fitColumns : true,
			striped : true,
			singleSelect : true,
			url : '<%=basePath%>queryTaskDcgt',
			queryParams : {
				check_id : check_id,
				startTime : startTime,
				endTime : endTime
			},
			loadMsg : '数据正在加载中......',
			pagination : true,
			rownumbers : true,
			columns:[[
				{field:'matters',title:'抽查事项类型',align:'center',width:30},
				{field:'dcgtcount',title:'调查个体总数',align:'center',width:30},
				{field:'rescount',title:'已检查总数',align:'center',width:30}
			]]
		});
	}
	
	function query(){
		var check_id_str = $('#check_id').combobox('getValues');
		if($("#queryForm").form('validate')){
			loadGrid(check_id_str.join(","),$('#startTime').datebox('getValue'),$('#endTime').datebox('getValue'));
		}else{
			alert("请输入查询条件");
		}
	}
</script>

</head>
<body>
<div class="main_box">
	<div id="filterDiv" class="easyui-panel" style="padding:10px;" title="检索条件" collapsible="true">
		<form id="queryForm">
		<table cellpadding="0" cellspacing="0" border="0" class="table_list">
			<tr>
				<th align="right">抽查事项类型：</th>
				<td width="50%">
					<input id="check_id" class="easyui-combobox" required="true" missingMessage="抽查事项类型不能为空"
						valueField='checkId' textField='checkMatters' method='get' panelHeight='auto' style='width:500px;' data-options="multiple:true"/>
				</td>
				<th align="right">时间：</th>
				<td width="20%">
					<input class="easyui-datebox" id="startTime" required="true" missingMessage="时间不能为空" style="width: 100px"
						data-options="formatter:myformatter,parser:myparser"></input>
					-<input class="easyui-datebox" id="endTime" required="true" missingMessage="时间不能为空" style="width: 100px"
						data-options="formatter:myformatter,parser:myparser"></input>
				</td>
				<td align="center">
					<input type="button" name="input5" class="cx_btn" value="查 询" onclick="query()"/>
				</td>
			</tr>
		</table>
		</form>
	</div>
	<br/>
	<table id="list_data"></table>
</div>
</body>
</html>
