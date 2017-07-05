<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ page import="java.net.URLEncoder"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String  path = request.getContextPath();
	String  basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>easyui1.3.2/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>easyui1.3.2/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>easyui1.3.2/demo/demo.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>common/css/style.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>common/css/public.css"/>
<script type="text/javascript" src="<%=basePath%>easyui1.3.2/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>easyui1.3.2/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>easyui1.3.2/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/common.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/uuid.js"></script>
<script type="text/javascript" src="<%=basePath%>common/js/operate.js"></script>
<script type="text/javascript" src="<%=basePath%>My97DatePicker/WdatePicker.js"></script>

<style type="text/css">
	a {text-decoration: none;font-family: Simsun;}
</style>

<script type="text/javascript">
	$(function() {	 
		loadGrid();
	});
	
	function loadGrid(){
		$('#list_data').datagrid({
			title : '抽查结果归档',
			iconCls : 'icon-save',
			pageSize : 50, 
			pageList : [10,20,30,50],
			striped : true,
			singleSelect : true,
			fitColumns : true,
			height : 435,
			queryParams : {
				task_name : $("#task_name").val(),
				task_status : $("#task_status").combobox('getValue')
			},
			url : '<%=basePath%>queryTask',
			loadMsg : '数据正在加载中......',
			pagination : true,
			rownumbers : true,
			columns:[[
			    {field:'taskId',hidden:true},
				{field:'taskName',title:'抽查任务单名称',align:'center',width:200},
				{field:'taskCheckTypeName',title:'抽查行业类型',align:'center',width:200
					/* formatter:function(val,rec){
						var value = "";
						var array = rec.taskCheckTypeName.split(";");
						for(var i=0;i<array.length-1;i++){
							value += array[i] + "<br/>";
						}
						return value;
					} */
				},
				{field:'taskUser',title:'创建用户',align:'center',width:100},
				{field:'taskDate',title:'创建日期',align:'center',width:120,
					formatter:function(val,rec){
					var date = new Date();
				    date.setTime(rec.taskDate.time);
				    var d = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
				    return d; 
					}
				}, 
				{field:'taskStatus',title:'任务单状态',align:'center',width:100,
					formatter:function(val,rec){
						var value = "";
						if(rec.taskStatus=="1"){
							value = "待检查";
						}else if(rec.taskStatus=="2"){
							value = "已审核";
						}else if(rec.taskStatus=="3"){
							value = "已打印";
						}else if(rec.taskStatus=="4"){
							value = "检查完成";
						}
						return value;
					}
				},
				{field:'CZ',title:'操作',align:'center',width:180,
					formatter:function(val,rec){
						var href  = "<a href=javascript:show_task_detail('"+rec.taskId+"');>详细</a>&nbsp;&nbsp;";
						return href;
					}
				}
			]]
		});
	}
	
	function show_task_detail(task_id){
		$("#task_id").val(task_id);
	
		loadTaskDetailGrid();
	
		$win = $('#div_task_detail').window({
            top:($(window).height() - 420) * 0.5
		});
		
		$('#div_task_detail').window('open');
	}
	
	function loadTaskDetailGrid(){
		$('#task_detail_list').datagrid({
			title : '抽查结果归档 - 详细',
			iconCls : 'icon-save',
			pageSize : 100, 
			pageList : [10,20,50,100],
			striped : true,
			singleSelect : true,
			fitColumns : true,
			height : 355,
			queryParams : {
				td_task_id : $("#task_id").val(),
				gsmc : $("#gsmc").val(),
				check_matters : $("#check_matters").val(),
				username : $("#username").val()
			},
			url : '<%=basePath%>queryDcgtResult',
			loadMsg : '数据正在加载中......',
			pagination : true,
			rownumbers : true,
			columns:[[
				{field:'CHECK_MATTERS',title:'抽查行业类型',align:'center',width:200},
				{field:'USERNAME',title:'执法人员',align:'center',width:100
					/* formatter:function(val,rec){
						return rec.USERNAME + ";"+rec.USERNAME2;
					} */
				},
				{field:'GSMC',title:'公司名称',align:'center',width:100},
				{field:'LXR',title:'联系人',align:'center',width:100},
				{field:'LXDH',title:'联系电话',align:'center',width:100},
				{field:'DZ',title:'地址',align:'center'},
				{field:'TD_ISINPUTRESULT',title:'结果是否录入',align:'center',
					formatter:function(val,rec){
						if(rec.TD_ISINPUTRESULT=="0"){
							return "已录入";
						}else{
							return "未录入";
						}
					}
				},
				{field:'TD_REMARK',title:'操作',align:'center',
					formatter:function(val,rec){
						var html  = "<input type='button' class='cx_btn' value='查看结果' ";
						    html += "onclick='show_div_task_dcgt_result(\""+rec.TD_ID+"\",\""+rec.GSMC+"\",\""+rec.DZ+"\",\""+rec.LXDH+"\",\"\",\""+rec.LXR+"\");'/>";
						return html;
					}
				}
			]]
		});
	}
	
	function show_div_task_dcgt_result(td_id,bjcdw,dz,dh,yyzzbh,fddbr){
		$.messager.progress({title:'请稍后',msg:'页面加载中...' });
		$win = $('#div_task_dcgt_result').window({
            top:($(window).height() - 490) * 0.5
		});
		
		$("#td_id").val(td_id);
		$("#bjcdw").val(bjcdw);
		$("#result_dz").val(dz);
		$("#dh").val(dh);
		$("#fddbr").val(fddbr);
		
		$.ajax({
			type : "POST",
	        url : '<%=basePath%>queryDetailDcgtResult',
	        data : {td_id:td_id},
	        success : function(data) {
	        	var array = eval("("+data+")");
	        	    
					$("#id").val(array[0].id);
					$("#yyzzbh").val(array[0].yyzzbh);
					$("#jzr").val(array[0].jzr);
					$("#dwhzs").val(array[0].dwhzs);
					$("#jcr1").val(array[0].jcr1);
					$("#zjh1").val(array[0].zjh1);
					$("#jcr2").val(array[0].jcr2);
					$("#zjh2").val(array[0].zjh2);
					$("#jcsj_start").val(array[0].jcsj_start);
					$("#jcsj_end").val(array[0].jcsj_end);
					$("#jcxm").val(array[0].jcxm);
					$("#jcgcms").val(array[0].jcgcms);
					$("#jcjgclyj").val(array[0].jcjgclyj);
					$("#bjcdw_yj").val(array[0].bjcdw_yj);
					$("#jzr_yj").val(array[0].jzr_yj);
					$("#bjcdw,#result_dz,#dh,#fddbr,#id,#yyzzbh,#jzr,#dwhzs,#jcr1,#zjh1,#jcr2,#zjh2,#jcsj_start,#jcsj_end,#jcxm,#jcgcms,#jcjgclyj,#bjcdw_yj,#jzr_yj")
						.attr("disabled",true);
	        	$('#div_task_dcgt_result').window('open');
				$.messager.progress('close');
	        }
    	});
	}
	
</script>
</head>
<body>
<div class="main_box">
	<div id="filterDiv" class="easyui-panel" style="padding:5px;" title="检索条件">
		<table cellpadding="0" cellspacing="0" border="0" class="table_list">
			<tr>
				<th align="right">抽查任务单名称：</th>
				<td>
					<input type="text" id="task_name" name="task_name"/>
				</td>
				<th align="right">任务单状态：</th>
				<td>
					<select class="easyui-combobox" id="task_status" name="task_status" style="width: 100px;">
						<option value="">请选择</option>
						<option value="1">待检查</option>
						<option value="4">检查完成</option>
					</select>
				</td>
				<td align="left">
					<input type="button" name="input5" class="cx_btn" value="查 询" onclick="loadGrid();"/>
				</td>
			</tr>
		</table>
	</div>
	<table id="list_data"></table>
</div>

<div id="div_task_detail" class="easyui-window" closed="true" title="抽查结果归档 - 详细" style="width:900px" modal="true" collapsible="false" minimizable="false" maximizable="false">
	<table cellpadding="0" cellspacing="0" border="0" class="table_list">
		<tr>
			<th style="width:100px">抽查行业类型：</th>
			<td>
				<input id="check_matters" class="easyui-validatebox" style="width: 200px;"/>
			</td>
			<th style="width:100px">执法人员：</th>
			<td>
				<input id="username" class="easyui-validatebox" style="width: 200px;"/>
			</td>
		</tr>
		<tr>
			<th style="width:120px">公司名称：</th>
			<td>
				<input id="task_id" type="hidden"/>
				<input id="gsmc" class="easyui-validatebox" style="width: 200px;"/>
			</td>
			<td colspan="2" align="center">
				<input type="button" name="input5" class="cx_btn" value="查 询" onclick="loadTaskDetailGrid();"/>
			</td>
		</tr>
	</table>
	<table id="task_detail_list"></table>
</div>

<div id="div_task_dcgt_result" class="easyui-window" closed="true" title="抽查结果归档  - 查看结果" style="width: 900px;" modal="true" collapsible="false" minimizable="false" maximizable="false">
	<form id="resultForm">
	<table cellpadding="0" cellspacing="0" border="0" class="table_list">
		<tr>
			<th style="width:200px">被检查单位（人）：</th>
			<td colspan="3">
				<input id="id" name="result.id" type="hidden"/>
				<input id="td_id" name="result.td_id" type="hidden"/>
				<input id="bjcdw" name="result.bjcdw" class="easyui-validatebox" style="width: 100%;"/>
			</td>
		</tr>
		<tr>
			<th>地址：</th>
			<td>
				<input id="result_dz" name="result.result_dz" class="easyui-validatebox" style="width: 200px;"/>
			</td>
			<th>电话：</th>
			<td>
				<input id="dh" name="result.dh" class="easyui-validatebox" style="width: 200px;"/>
			</td>
		</tr>
		<tr>
			<th>营业执照编号：</th>
			<td>
				<input id="yyzzbh" name="result.yyzzbh" class="easyui-validatebox" style="width: 200px;"/>
			</td>
			<th>法定代表人：</th>
			<td>
				<input id="fddbr" name="result.fddbr" class="easyui-validatebox" style="width: 200px;"/>
			</td>
		</tr>
		<tr>
			<th>见证人：</th>
			<td>
				<input id="jzr" name="result.jzr" class="easyui-validatebox" style="width: 200px;"/>
			</td>
			<th>单位或住所：</th>
			<td>
				<input id="dwhzs" name="result.dwhzs" class="easyui-validatebox" style="width: 200px;"/>
			</td>
		</tr>
		<tr>
			<th>检查人：</th>
			<td>
				<input id="jcr1" name="result.jcr1" class="easyui-validatebox" style="width: 100px;"/>
			</td>
			<th>证件号：</th>
			<td>
				<input id="zjh1" name="result.zjh1" class="easyui-validatebox" style="width: 150px;"/>
			</td>
		</tr>
		<tr>
			<th>检查人：</th>
			<td>
				<input id="jcr2" name="result.jcr2" class="easyui-validatebox" style="width: 100px;"/>
			</td>
			<th>证件号：</th>
			<td>
				<input id="zjh2" name="result.zjh2" class="easyui-validatebox" style="width: 150px;"/>
			</td>
		</tr>
		<tr>
			<th>检查时间：</th>
			<td colspan="3">
				<input id="jcsj_start" name="result.jcsj_start" class="easyui-validatebox" style="width: 200px;"/>
				-
				<input id="jcsj_end" name="result.jcsj_end" class="easyui-validatebox" style="width: 200px;"/>
			</td>
		</tr>
		<tr>
			<th>检查项目：</th>
			<td colspan="3">
				<input id="jcxm" name="result.jcxm" class="easyui-validatebox" style="width: 100%;"/>
			</td>
		</tr>
		<tr>
			<th>检查过程描述：</th>
			<td colspan="3">
				<textarea id="jcgcms" name="result.jcgcms" style="width: 100%;resize:none;" rows="3"></textarea>
			</td>
		</tr>
		<tr>
			<th>检查结果及处理意见：</th>
			<td colspan="3">
				<textarea id="jcjgclyj" name="result.jcjgclyj" style="width: 100%;resize:none;" rows="3"></textarea>
			</td>
		</tr>
		<tr>
			<th>被检查单位（人）意见：</th>
			<td colspan="3">
				<input id="bjcdw_yj" name="result.bjcdw_yj" class="easyui-validatebox" style="width: 100%;"/>
			</td>
		</tr>
		<tr>
			<th>见证人意见：</th>
			<td colspan="3">
				<input id="jzr_yj" name="result.jzr_yj" class="easyui-validatebox" style="width: 100%;"/>
			</td>
		</tr>
	</table>
	</form>
</div>

</body>
</html>

