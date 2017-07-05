var loadToJson = function(url, params, fun) {
	$.ajax({
				type : "POST",
				url : url,
				data : params,
				success : function(data) {
					var dataStr = "{root:" + data + "}";
					var dataObj = eval("(" + dataStr + ")");// 转换为json对象
					fun(dataObj);
				}
			});
}




// 数据源信息表单清空
function clearForm() {
	$('#config').form('clear');
	$('#dlg').dialog('close');
	$('#dlg_userinfo').dialog('close');
	$('#result').val('');
}


//自动生成uuid
function getUUID() {
	var uuid = new UUID().createUUID();
	return uuid;
}


//获取当前日期
function getTodayDate(){
	var todayDate = new Date();
	var year = todayDate.getFullYear();
	var month = todayDate.getMonth()+1;
	if(month<10){
		month = '0'+month;
	}
	var day = todayDate.getDate();
	if(day<10){
		day = '0'+day;
	}
	return year+'-'+month+'-'+day;
}

//处理combobox显示问题
function getCombobox(name){
	var data = $('#'+name).combobox('getData');
	/*var data = $('#'+name).combobox('getData');
  	if(data.length>1){//如果长度大于1，对combobox进行排序
  		for(var i=0;i<data.length;i++){
  			var str = data[i];
  			data[i] = data[data.length-1];
  			data[data.length-1] = str;
  		} 	
  	}
  	$('#'+name).combobox('loadData',data);*/
	var h = (data.length*19);
	if(h >222){//当combobox高度超过222时出现纵向滚动条	
		$('#'+name).combo({panelHeight:222});  
	}else{
		//$('#'+name).combo({panelHeight:h}); 
	}
}

//处理combobox显示问题
function getXzCombobox(name){
	var data = $('#'+name).combobox('getData');
  	$('#'+name).combobox('loadData',data);
	var h = (data.length*19);
	if(h >150){//当combobox高度超过150时出现纵向滚动条	
		$('#'+name).combo({panelHeight:150});  
	}else{
		//$('#'+name).combo({panelHeight:h}); 
	}
}

//前台合并单元格
$.extend($.fn.datagrid.methods, {  
	autoMergeCells : function (jq, fields) { 
		//alert(jq);
		//alert(fields);
		return jq.each(function () {  
			var target = $(this);
			//alert(target);
			if (!fields) {  
				fields = target.datagrid("getColumnFields");  
			}
			//alert(fields);
			var rows = target.datagrid("getRows");  
			var i = 0,  
			j = 0,  
			temp = {};  
			for (i; i < rows.length; i++) {  
				var row = rows[i];  
				j = 0;  
				for (j; j < fields.length-3; j++) {  
					var field = fields[j];  
					var tf = temp[field];  
		            if (!tf) {  
		            	tf = temp[field] = {};  
		                tf[row[field]] = [i];  
		            } else {  
		                var tfv = tf[row[field]];  
		                if (tfv) {  
		                    tfv.push(i);  
		                } else {  
		                    tfv = tf[row[field]] = [i];  
		                }  
		            }  
				}  
			}  
    $.each(temp, function (field, colunm) {  
        $.each(colunm, function () {  
            var group = this;
            //alert(group);
              
            if (group.length > 1) {  
                var before,  
                after,  
                megerIndex = group[0];  
                for (var i = 0; i < group.length; i++) {  
                    before = group[i];  
                    after = group[i + 1];  
                    if (after && (after - before) == 1) {  
                        continue;  
                    }  
                    var rowspan = before - megerIndex + 1;  
                    if (rowspan > 1) {
                    	//alert(rowspan);
                        target.datagrid('mergeCells', {  
                            index : megerIndex,  
                            field : field,  
                            rowspan : rowspan  
                        });  
                    }  
                    if (after && (after - before) != 1) {  
                        megerIndex = after;  
                    }  
                    }  
                }
            //alert();
            });  
        });  
    });  
}  
});  


//定义转换函数
function dateConvert(dateParms){ 
    // 对传入的时间参数进行判断
    if(dateParms instanceof Date){
        var datetime=dateParms;
    }
    //判断是否为字符串
    if((typeof dateParms=="string")&&dateParms.constructor==String){
        //将字符串日期转换为日期格式
        var datetime= new Date(Date.parse(dateParms.replace(/-/g,   "/")));
    
    }
    //获取年月日时分秒
     var year = datetime.getFullYear();
     var month = datetime.getMonth()+1; 
     //var date = datetime.getDate(); 
     //var hour = datetime.getHours(); 
     //var minutes = datetime.getMinutes(); 
     //var second = datetime.getSeconds();
     //月，日，时，分，秒 小于10时，补0
     if(month<10){
      month = "0" + month;
     }
     //if(date<10){
     // date = "0" + date;
     //}
     //if(hour <10){
     // hour = "0" + hour;
     //}
     //if(minutes <10){
     // minutes = "0" + minutes;
     //}
     //if(second <10){
     // second = "0" + second ;
     //}
     //拼接日期格式【例如：yyyymmdd】
     var time = year+month;
     //或者：其他格式等
     //var time = year+"年"+month+"月"+date+"日"+hour+":"+minutes+":"+second; 
     //返回处理结果
     return time;
}

