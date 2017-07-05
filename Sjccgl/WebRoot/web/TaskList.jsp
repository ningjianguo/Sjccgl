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
<link rel="stylesheet" type="text/css" href="<%=basePath%>easyui1.3.2/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>easyui1.3.2/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>easyui1.3.2/demo/demo.css">
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
	
function geshi(value){
	var date = new Date();
	 date.setTime(value.time);
	 var d = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
	 return d;
   }
</script>

<script type="text/javascript">
	
	$(function() {
		var is_index = '${param.is_index}';
		if(is_index == "2"){
			show_div_task();
		}else if(is_index == "1"){
			show_div_dcgt_list();
		}
		loadGrid();
		loadCheckType();
		loadCheckUser();
	});
	function loadGrid(){
		var task_name = $("#task_name").val();
		$('#list_data').datagrid({
			title : '抽查任务单管理',
			iconCls : 'icon-save',
			pageSize : 50, 
			pageList : [10,20,30,50],
			striped : true,
			singleSelect : true,
			fitColumns : true,
			height : 435,
			queryParams : {
				task_name : task_name,
				task_status : ""
			},
			url : 'queryTask',
			loadMsg : '数据正在加载中......',
			pagination : true,
			rownumbers : true,
			toolbar : [
				{
					text : '非定向抽查',
					iconCls : 'icon-add',
					handler : function (){
						show_div_task();
					}
				},{
					text : '定向抽查',
					iconCls : 'icon-add',
					handler : function (){
						show_div_dcgt_list();
					}
				}
			],
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
				//{field:'TASK_CHECK_USER',title:'审核用户',align:'center',width:100},
				{field:'taskDate',title:'创建日期',align:'center',width:120,
					formatter:function(val,rec){
						var date = new Date();
					    date.setTime(rec.taskDate.time);
					    var d = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
					    return d; 
						}
				},
				//{field:'TASK_CHECK_DATE',title:'审核日期',align:'center',width:100},
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
						var href  = "<a href=javascript:show_task_detail('"+rec.taskId+"','"+rec.taskName+"','"+rec.taskStatus+"','"+rec.taskUser+"','"+geshi(rec.taskDate)+"');>详细</a>&nbsp;&nbsp;";
							href += "<a href=javascript:print_task('"+rec.taskId+"');>导出</a>&nbsp;&nbsp;";
							href += "<a href=javascript:show_result_input('"+rec.taskId+"','"+rec.taskName+"','"+rec.taskStatus+"','"+rec.taskUser+"','"+geshi(rec.taskDate)+"');>结果录入</a>&nbsp;&nbsp;";
							href += "<a href=javascript:delete_task('"+rec.taskId+"');>删除</a>&nbsp;&nbsp;";
							return href;
					}
				}
			]]
		});
	}
	
	function show_div_task(){
		$win = $('#div_task').window({
            top:($(window).height() - 400) * 0.5
		});
		$('#div_task').window('open');
		$('#addForm #task_name').val(shengChengTaskName());
		
	}
	
	function loadCheckType(){
		$.ajax({
			type:"POST",
	        url:'<%=basePath%>optionType',
	        success: function(data) {
	        	var array = eval(data);
	        	var value = "";
				for(var i=0;i<array.length;i++){
					value += "<input type='checkbox' name='task_check_type' value='"+array[i].checkId+"'/>"+array[i].checkMatters;
					value += "<br/>";
				}
				$("#td_task_check_type").html(value);
				$("#dx_td_task_check_type").html(value);
	        }
    	});
	}
	//复选框勾选限制
	function checkboxLimit(){
		$('.td_check_user').click(function(){
			var limitLength = $(".td_check_user:checked").length;
            if (limitLength >= 3) {
           	    limitLength = 0;
           		return false;
            }
		});
	}
	function loadCheckUser(){
		$.ajax({
			type:"POST",
	        url:'<%=basePath%>getAllUser',
	        success: function(data) {
	        	var array = eval(data);
	        	var value = "";
				for(var i=0;i<array.length;i++){
					value += "<input type='checkbox' class='td_check_user' name='td_check_user' value='"+array[i].userid+"'/>"+array[i].username;
					value += "<br/>";
				}
				$("#user_checkbox").html(value);
				$("#dx_user_checkbox").html(value);
		    	checkboxLimit();
	        }
    	});
	}
	
	function saveTask(){
		var task_check_type_name = "&task_check_type_name=";
		var td_check_user = "&check_user=";
		var obj = document.getElementsByName("task_check_type");  
		var objUser = document.getElementsByName("td_check_user"); 
	    for(var i=0; i<obj.length; i++){  
			if(obj[i].checked){  
				task_check_type_name += obj[i].nextSibling.nodeValue+";";
			}  
	    }
	    for(var i=0; i<objUser.length; i++){  
			if(objUser[i].checked){  
				td_check_user += objUser[i].nextSibling.nodeValue+";";
			}  
	    }
	    task_check_type_name = task_check_type_name.substring(0, task_check_type_name.length-1);
	    td_check_user = td_check_user.substring(0, td_check_user.length-1);
	    $("[name=td_check_user]:checkbox").attr("checked", false);
		$.ajax({
		    url : "<%=basePath%>saveTask",
		    dataType : "json",
		    async : false,
		    data : $('#addForm').serialize()+td_check_user+task_check_type_name,
		    type : "post",
		    success : function(data){
		    	if(data.success == "success"){
			    	$('#div_task').window('close');
			    	loadGrid();
		    	}
		    }
		});
	}
	
	function show_task_detail(task_id,task_name,task_status,task_user,task_date){
		$('#detail_task_name').val(task_name);
		if(task_status=="1"){
			$('#detail_task_status').val("待检查");
		}else if(task_status=="2"){
			$('#detail_task_status').val("已审核");
		}else if(task_status=="3"){
			$('#detail_task_status').val("已打印");
		}else if(task_status=="4"){
			$('#detail_task_status').val("检查完成");
		}
		$('#detail_task_user').val(task_user);
		$('#detail_task_date').val(task_date);
		
		loadTaskDetailGrid(task_id);
		
		$win = $('#div_task_detail').window({
            top:($(window).height() - 420) * 0.5
		});
		
		$('#div_task_detail').window('open');
		
	}
	
	function loadTaskDetailGrid(td_task_id){
		$('#task_detail_list').datagrid({
			title : '抽查任务单详情',
			iconCls : 'icon-save',
			pageSize : 100, 
			pageList : [10,20,50,100],
			striped : true,
			singleSelect : true,
			fitColumns : false,
			height : 355,
			queryParams : {
				taskid : td_task_id
			},
			url : '<%=basePath%>queryTaskDetail',
			loadMsg : '数据正在加载中......',
			pagination : true,
			rownumbers : true,
			columns:[[
				{field:'mattersname',title:'抽查行业类型',align:'center',width:150},
				{field:'username',title:'执法人员',align:'center',width:150}, 
				{field:'bjcdw',title:'公司名称',align:'center',width:150},
				{field:'fddbr',title:'联系人',align:'center',width:150},
				{field:'dh',title:'联系电话',align:'center',width:150},
				{field:'dz',title:'地址',align:'center',width:150}
			]]
		});
	}
	
	function delete_task(task_id){
		$.messager.confirm("操作提示", "您确定要执行删除操作吗？", function (data) {  
            if(data){  
            	$.ajax({
				    url : '<%=basePath%>deleteTask',
				    dataType : "json",
				    async : false,
				    data : {taskId:task_id},
				    type : "post",
				    success : function(data){
				     if( data.success == "success" ){ 
				    	loadGrid();
				      }
				    }
				});
            } else {
            
            }  
        });  
	};
	
	function show_div_dcgt_list(){
		$win = $('#div_dcgt_list').window({
            top:($(window).height() - 490) * 0.5
		});
		loadDcgtInfoGrid();
		$('#div_dcgt_list').window('open');
		$('#dxAddForm #task_name').val(shengChengTaskName());
	}
	
	function loadDcgtInfoGrid(){
		$('#search_dcgt_list').datagrid({
			title : '调查个体列表',
			iconCls : 'icon-save',
			pageSize : 50, 
			pageList : [10,20,50,100],
			striped : true,
			singleSelect : true,
			fitColumns : true,
			height : 355,
			queryParams : {
				dz : $("#dz").val()
			},
			url : '<%=basePath%>queryCompanyInfo',
			loadMsg : '数据正在加载中......',
			pagination : true,
			rownumbers : true,
			columns:[[
				{field:'gsmc',title:'公司名称',align:'center',width:200},
				{field:'qyxz',title:'企业性质',align:'center',width:200},
				{field:'jylx',title:'经营类型',align:'center',width:200},
				{field:'hy',title:'行业',align:'center',width:200},
				{field:'dz',title:'地址',align:'center',width:200}
			]]
		});
	}
	
	function saveDxTask(){
		var row = $('#search_dcgt_list').datagrid('getSelected');
		if(row==null){
			$.messager.show({
							title : '错误',
							msg : '您未选择调查个体!'
					});
			return;
		}
		$('#dcgtid').val(row.dcgtid);
		var task_check_type_name = "&task_check_type_name=";
		var td_check_user = "&check_user=";
		var objDx = document.getElementsByName("task_check_type");  
		var objUserDx = document.getElementsByName("td_check_user"); 
		var iszd = $("input[name='iszd']:checked").val();
	    for(var i=0; i<objDx.length; i++){  
			if(objDx[i].checked){  
				task_check_type_name += objDx[i].nextSibling.nodeValue+";";
			}  
	    }
	    for(var i=0; i<objUserDx.length; i++){  
			if(objUserDx[i].checked){  
				td_check_user += objUserDx[i].nextSibling.nodeValue+";";
			}  
	    }
	    task_check_type_name = task_check_type_name.substring(0, task_check_type_name.length-1);
	    td_check_user = td_check_user.substring(0, td_check_user.length-1);
	    $("[name=td_check_user]:checkbox").attr("checked", false);
		$.ajax({
		    url : "<%=basePath%>saveTask",
		    dataType : "json",
		    async : false,
		    data : $('#dxAddForm').serialize()+td_check_user+task_check_type_name+"&iszd="+iszd,
		    type : "post",
		    success : function(data){
		    	if(data.success == "success"){
			    	$('#div_dcgt_list').window('close');
			    	loadGrid();
		    	}
		    }
		});
	}
	
	function print_task(taskid){
		$("#exportTaskid").val(taskid);
		document.forms[0].action = "<%=basePath%>exportTask";
		document.forms[0].submit();
	}
	
	function show_result_input(task_id,task_name,task_status,task_user,task_date){
		$('#task_id').val(task_id);
		$('#result_task_name').val(task_name);
		if(task_status=="1"){
			$('#result_task_status').val("待检查");
		}else if(task_status=="2"){
			$('#result_task_status').val("已审核");
		}else if(task_status=="3"){
			$('#result_task_status').val("已打印");
		}else if(task_status=="4"){
			$('#result_task_status').val("检查完成");
		}
		$('#result_task_user').val(task_user);
		$('#result_task_date').val(task_date);
		
		loadTaskResultInputGrid(task_id);
		
		$win = $('#div_result_input').window({
            top:($(window).height() - 490) * 0.5
		});
		
		$('#div_result_input').window('open');
	}
	
	function loadTaskResultInputGrid(td_task_id){
		$('#task_result_input_list').datagrid({
			title : '抽查任务单结果录入',
			iconCls : 'icon-save',
			pageSize : 100, 
			pageList : [10,20,50,100],
			striped : true,
			singleSelect : true,
			fitColumns : true,
			height : 355,
			queryParams : {
				taskid : td_task_id
			},
			url : '<%=basePath%>queryTaskDetail',
			loadMsg : '数据正在加载中......',
			pagination : true,
			rownumbers : true,
			columns:[[
				{field:'mattersname',title:'抽查行业类型',align:'center',width:200},
				 {field:'username',title:'执法人员',align:'center',width:200
					/* formatter:function(val,rec){
						return rec.username + ";"+rec.username;
					} */
				}, 
				{field:'bjcdw',title:'公司名称',align:'center',width:200},
				{field:'fddbr',title:'联系人',align:'center',width:200},
				{field:'dh',title:'联系电话',align:'center',width:200},
				{field:'dz',title:'地址',align:'center',width:200},
				{field:'isinputresult',title:'结果是否录入',align:'center',
					formatter:function(val,rec){
						if(rec.isinputresult=="0"){
							return "已录入";
						}else{
							return "未录入";
						}
					}
				},
				{field:'TD_REMARK',title:'操作',align:'center',
					formatter:function(val,rec){
						var html  = "<input type='button' class='cx_btn' value='结果录入' ";
						    html += "onclick='show_div_task_dcgt_result(\""+rec.tdId+"\",\""+rec.bjcdw+"\",\""+rec.dz+"\",\""+rec.dh+"\",\"\",\""+rec.fddbr+"\");'/>";
						return html;
					}
				}
			]]
		});
	}
	
	function saveOrUpdateTaskDcgtResult(){
		if($("#jcjgclyj").val().trim()==""){
			alert("请填写检查结果及审核意见");
			$("#jcjgclyj").focus();
			return;
		}
		
		$.ajax({
		    url : "<%=basePath%>saveOrUpdateTaskDcgtResult",
		    dataType : "json",
		    async : false,
		    data : $('#resultForm').serialize() + "&td_id=" + $('#td_id').val(),
		    type : "post",
		    success : function(req){
		    
		     if(req.success =="success"){
		   		$("#div_task_dcgt_result").window('close');
		   		$("#task_detail_list").window('close');
		   		$("#iszd_0").attr("checked",false);
		   		$("#iszd_null").attr("checked",false);
		   		$("#task_result_input_list").datagrid("reload",{td_task_id:$("#task_id").val()});
		   		}
		    }
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
		$("#yyzzbh").val(yyzzbh);
		$("#fddbr").val(fddbr);
		
		$.ajax({
			type : "POST",
	        url : '<%=basePath%>queryDetailDcgtResult',
	        data : {td_id:td_id},
	        success : function(data) {
	        	var array = eval("("+data+")");
	        	
					$("#jcr1").val(array[0].jcr1);
					$("#zjh1").val(array[0].zjh1);
					$("#jcr2").val(array[0].jcr2);
					$("#zjh2").val(array[0].zjh2);
					$("#id").val(array[0].id);
					$("#jzr").val(array[0].jzr);
					$("#dwhzs").val(array[0].dwhzs);
					$("#jcsj_start").val(array[0].jcsj_start);
					$("#jcsj_end").val(array[0].jcsj_end);
					$("#jcxm").val(array[0].jcxm);
					$("#jcgcms").val(array[0].jcgcms);
					$("#jcjgclyj").val(array[0].jcjgclyj);
					$("#bjcdw_yj").val(array[0].bjcdw_yj);
					$("#jzr_yj").val(array[0].jzr_yj);
					$("#yyzzbh").val(array[0].yyzzbh);
					 if(array[0].iszd == "0"){
					 $("#iszd_0").attr("checked",true);
					}
					
					if(array[0].iszd == "1"){
					 $("#iszd_null").attr("checked",true);
					} 
					 
	        	
	        	$('#div_task_dcgt_result').window('open');
				$.messager.progress('close');
	        }
    	});
	}
	
	function shengChengTaskName(){
		var date = new Date();
		var year = date.getFullYear();
		var month = formatString(date.getMonth() + 1);
		var day = formatString(date.getDate());
		var hours = formatString(date.getHours()); 
		var minutes = formatString(date.getMinutes());
		var seconds = date.getSeconds();
		return "任务单"+year+"-"+month+"-"+day+"~"+hours+":"+minutes;
	}
	
	function formatString(v){
		if(v < 10){
			return "0" + v;
		}
		return v;
	}
   	</script>
</head>
<body>
<div class="main_box">
	<div id="filterDiv" class="easyui-panel" style="padding:5px;" title="检索条件">
		<table cellpadding="0" cellspacing="0" border="0" class="table_list">
			<tr>
				<th align="right" width="12%">抽查任务单名称：</th>
				<td width="15%">
					<input type="text" id="task_name" name="task_name" style="width: 100%"/>
				</td>
				<td width="67">
					<input type="button" name="input5" class="cx_btn" value="查 询" onclick="loadGrid();"/>
				</td>
			</tr>
		</table>
	</div>
	<table id="list_data"></table>
</div>

<div id="div_task" class="easyui-window" closed="true" title="非定向抽查添加" style="width:400px;" modal="true" collapsible="false" minimizable="false" maximizable="false">
	<form id="addForm">
	<input type="hidden" name="exportTaskid" id="exportTaskid"/>
	<input type="hidden" name="exportTaskname" id="exportTaskname"/>
	<table cellpadding="0" cellspacing="0" border="0" class="table_list">
		<tr>
			<th style="width:120px">抽查任务单名称：<font color="red">*</font></th>
			<td>
				<input id="task_name" name="task_name" required="true" class="easyui-validatebox" style="width: 99%;"/>
			</td>
		</tr>
		<tr>
			<th style="width:120px">不重复抽查时间段：</th>
			<td >
				<input class="easyui-datebox" name="startTime" style="width: 110px;"
					data-options="formatter:myformatter,parser:myparser"></input>
				-
				<input class="easyui-datebox" name="endTime" style="width: 110px;"
					data-options="formatter:myformatter,parser:myparser"></input>
			</td>
		</tr>
		<tr>
			<th>抽查行业类型：<font color="red">*</font></th>
			<td id="td_task_check_type">
				
			</td>
		</tr>
		<tr>
			<th>执法人员：<font color="red">*</font></th>
			<td id="user_checkbox">
				
			</td>
		</tr>
		<tr>
			<th colspan="2">
				<input type="button" class="cx_btn" value="提 交" onclick="saveTask();"/>
			</th>
		</tr>
	</table>
	</form>
</div>

<div id="div_task_detail" class="easyui-window" closed="true" title="抽查任务单详细" style="width:900px" modal="true" collapsible="false" minimizable="false" maximizable="false">
	<table cellpadding="0" cellspacing="0" border="0" class="table_list">
		<tr>
			<th style="width:120px">抽查任务单名称：</th>
			<td>
				<input id="detail_task_name" readonly="readonly" class="easyui-validatebox" style="width: 200px;"/>
			</td>
			<th style="width:100px">任务单状态：</th>
			<td>
				<input id="detail_task_status" readonly="readonly" class="easyui-validatebox" style="width: 200px;"/>
			</td>
		</tr>
		<tr>
			<th style="width:100px">创建用户：</th>
			<td>
				<input id="detail_task_user" readonly="readonly" class="easyui-validatebox" style="width: 200px;"/>
			</td>
			<th style="width:100px">创建日期：</th>
			<td>
				<input id="detail_task_date" readonly="readonly" class="easyui-validatebox" style="width: 200px;"/>
			</td>
		</tr>
	</table>
	<table id="task_detail_list"></table>
</div>

<div id="div_dcgt_list" class="easyui-window" closed="true" title="定向抽查添加" style="width:800px;" modal="true" collapsible="false" minimizable="false" maximizable="false">
	<form id="dxAddForm">
	<input type="hidden" name="isDx" value="yes"/>
	<table cellpadding="0" cellspacing="0" border="0" class="table_list">
		<tr>
			<th style="width:150px">企业地址：</th>
			<td style="width:220px">
				<input id="dz" name="dz" class="easyui-validatebox" style="width: 200px;"/>
			</td>
			<td align="right">
				<input type="button" class="cx_btn" value="查 询" onclick="loadDcgtInfoGrid();"/>
			</td>
		</tr>
	</table>
	<table id="search_dcgt_list">
		<input type="hidden" id="dcgtid" name="dcgtid" value=""/>
	</table>
	<table cellpadding="0" cellspacing="0" border="0" class="table_list">
		<tr>
			<th>抽查任务单名称：<font color="red">*</font></th>
			<td style="width:30%">
				<input id="task_name" name="task_name" required="true" class="easyui-validatebox" style="width:100%;"/>
			</td>
			<th></th>
			<td></td>
		</tr>
		<tr>
			<th>不重复抽查时间段：</th>
			<td>
				<input class="easyui-datebox" name="startTime" style="width: 110px;"
					data-options="formatter:myformatter,parser:myparser"></input>
				-
				<input class="easyui-datebox" name="endTime" style="width: 110px;"
					data-options="formatter:myformatter,parser:myparser"></input>
			</td>
			<th>是否重点检查：</th>
			<td>
				<input type="radio" name="iszd" value="1"/>是&nbsp;&nbsp;<input type="radio" name="iszd" value="0"/>否
			</td>
		</tr>
		<tr>
			<th>抽查行业类型：<font color="red">*</font></th>
			<td id="dx_td_task_check_type">
				
			</td>
			<th>执法人员：<font color="red">*</font></th>
			<td id="dx_user_checkbox">
				
			</td>
		</tr>
		<tr>
			<th colspan="4">
				<input type="button" class="cx_btn" value="提 交" onclick="saveDxTask();"/>
			</th>
		</tr>
	</table>
	</form>
</div>

<div id="div_result_input" class="easyui-window" closed="true" title="抽查任务单结果录入" style="width: 900px;" modal="true" collapsible="false" minimizable="false" maximizable="false">
	<table cellpadding="0" cellspacing="0" border="0" class="table_list">
		<tr>
			<th style="width:120px">抽查任务单名称：</th>
			<td>
				<input id="result_task_name" readonly="readonly" class="easyui-validatebox" style="width: 200px;"/>
			</td>
			<th style="width:100px">任务单状态：</th>
			<td>
				<input id="result_task_status" readonly="readonly" class="easyui-validatebox" style="width: 200px;"/>
			</td>
		</tr>
		<tr>
			<th style="width:100px">创建用户：</th>
			<td>
				<input id="result_task_user" readonly="readonly" class="easyui-validatebox" style="width: 200px;"/>
			</td>
			<th style="width:100px">创建日期：</th>
			<td>
				<input id="result_task_date" readonly="readonly" class="easyui-validatebox" style="width: 200px;"/>
			</td>
		</tr>
	</table>
	<input type="hidden" id="task_id" name="task_id"/>
	<table id="task_result_input_list"></table>
</div>

<div id="div_task_dcgt_result" class="easyui-window" closed="true" title="抽查任务单结果录入 - 结果录入" style="width: 900px;" modal="true" collapsible="false" minimizable="false" maximizable="false">
	<form id="resultForm">
	<table cellpadding="0" cellspacing="0" border="0" class="table_list">
		<tr>
			<th style="width:200px">被检查单位（人）：</th>
			<td colspan="3">
				<input id="id" name="id" type="hidden"/>
				<input id="td_id" name="td_id" type="hidden"/>
				<input id="dcgtid" name="dcgtid" type="hidden"/>
				<input id="bjcdw" name="bjcdw" class="easyui-validatebox" style="width: 100%;" readonly="readonly"/>
			</td>
		</tr>
		<tr>
			<th>地址：</th>
			<td>
				<input id="result_dz" name="result_dz" class="easyui-validatebox" style="width: 200px;" readonly="readonly"/>
			</td>
			<th>电话：</th>
			<td>
				<input id="dh" name="dh" class="easyui-validatebox" style="width: 200px;" readonly="readonly"/>
			</td>
		</tr>
		<tr>
			<th>营业执照编号：</th>
			<td>
				<input id="yyzzbh" name="yyzzbh" class="easyui-validatebox" style="width: 200px;" readonly="readonly"/>
			</td>
			<th>法定代表人：</th>
			<td>
				<input id="fddbr" name="fddbr" class="easyui-validatebox" style="width: 200px;" readonly="readonly"/>
			</td>
		</tr>
		<tr>
			<th>检查人：</th>
			<td>
				<input id="jcr1" name="jcr1" class="easyui-validatebox" style="width: 200px;" readonly="readonly"/>
			</td>
			<th>证件号：</th>
			<td>
				<input id="zjh1" name="zjh1" class="easyui-validatebox" style="width: 200px;" readonly="readonly"/>
			</td>
		<tr>
		<tr>
			<th>检查人：</th>
			<td>
				<input id="jcr2" name="jcr2" class="easyui-validatebox" style="width: 200px;" readonly="readonly"/>
			</td>
			<th>证件号：</th>
			<td>
				<input id="zjh2" name="zjh2" class="easyui-validatebox" style="width: 200px;" readonly="readonly"/>
			</td>
		<tr>
		<tr>
			<th>见证人：</th>
			<td>
				<input id="jzr" name="jzr" class="easyui-validatebox" style="width: 200px;"/>
			</td>
			<th>单位或住所：</th>
			<td>
				<input id="dwhzs" name="dwhzs" class="easyui-validatebox" style="width: 200px;"/>
			</td>
		</tr>
		<tr>
			<th>检查时间：</th>
			<td colspan="3">
				<input id="jcsj_start" name="jcsj_start" class="easyui-validatebox" style="width: 200px;"/>
				-
				<input id="jcsj_end" name="jcsj_end" class="easyui-validatebox" style="width: 200px;"/>
			</td>
		</tr>
		<tr>
			<th>检查项目：</th>
			<td colspan="3">
				<input id="jcxm" name="jcxm" class="easyui-validatebox" style="width: 100%;"/>
			</td>
		</tr>
		<tr>
			<th>检查过程描述：</th>
			<td colspan="3">
				<textarea id="jcgcms" name="jcgcms" style="width: 100%;resize:none;" rows="2"></textarea>
			</td>
		</tr>
		<tr>
			<th>检查结果及审核意见：</th>
			<td colspan="3">
				<textarea id="jcjgclyj" name="jcjgclyj" style="width: 100%;resize:none;" rows="2"></textarea>
			</td>
		</tr>
		<tr>
			<th>被检查单位（人）意见：</th>
			<td colspan="3">
				<input id="bjcdw_yj" name="bjcdw_yj" class="easyui-validatebox" style="width: 100%;"/>
			</td>
		</tr>
		<tr>
			<th>见证人意见：</th>
			<td colspan="3">
				<input id="jzr_yj" name="jzr_yj" class="easyui-validatebox" style="width: 100%;"/>
			</td>
		</tr>
		<tr>
			<th>是否合格：</th>
			<td colspan="3">
				<input type="radio" name="iszd" id="iszd_0" value="0"/>是&nbsp;&nbsp;<input type="radio" name="iszd" id="iszd_null" value="1"/>否
			</td>
		</tr>
		<tr>
			<th style="text-align: center;" colspan="4">
				<input type="button" class="cx_btn" value="结果保存" onclick="saveOrUpdateTaskDcgtResult();"/>
			</th>
		</tr>
	</table>
	</form>
</div>
</body>
</html>

