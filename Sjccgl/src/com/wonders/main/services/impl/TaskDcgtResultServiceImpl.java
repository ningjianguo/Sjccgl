package com.wonders.main.services.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.wonders.main.dao.TaskDcgtDao;
import com.wonders.main.dao.TaskDcgtResultDao;
import com.wonders.main.model.TbTaskDcgt;
import com.wonders.main.model.TbTaskDcgtResult;
import com.wonders.main.services.TaskDcgtResultService;
import com.wonders.util.ExportExcel;

public class TaskDcgtResultServiceImpl extends BaseServiceImpl implements TaskDcgtResultService{
	
	private TaskDcgtResultDao taskDcgtResultDao;
	private TaskDcgtDao taskDcgtDao;
	
	@Override
	public String getTaskDcgtResultByPageNum(int page, int pagerow, String[] args) {
		List<Object[]> datas = taskDcgtResultDao.getTaskDcgtResultByPageNum(pagerow, page, args);
		int totalCount = datas.size();
		Map<String,String> dataMap = null;
		List lists = new ArrayList();
		for (Object[] data : datas) {
			dataMap = new HashMap<String, String>();
			dataMap.put("TD_ID", data[0].toString());
			dataMap.put("CHECK_MATTERS", data[1].toString());
			dataMap.put("USERNAME", data[2].toString()+";"+data[3].toString());
			dataMap.put("GSMC", data[4].toString());
			dataMap.put("LXR", data[5].toString());
			dataMap.put("LXDH", data[6].toString());
			dataMap.put("DZ", data[7].toString());
			dataMap.put("TD_ISINPUTRESULT", data[8].toString());
			lists.add(dataMap);
		}
		JSONObject jsonObj = new JSONObject();
		jsonObj.accumulate("total", totalCount);
		jsonObj.accumulate("rows", lists);
		return jsonObj.toString();
	}
	
	public void setTaskDcgtResultDao(TaskDcgtResultDao taskDcgtResultDao) {
		this.taskDcgtResultDao = taskDcgtResultDao;
	}

	@Override
	public String getTaskDetail(String taskid) {
		 List<Map<String, Object>> list =  taskDcgtResultDao.getTaskDetail(taskid);
		 JSONObject jsonObject = new JSONObject();
		 int total = list.size();
		 jsonObject.accumulate("total",total);
		 jsonObject.accumulate("rows", list);
		 
		return jsonObject.toString();
	}

	@Override
	public String getTaskDcgtResultById(String id) {
		List<Map<String, Object>> lists = taskDcgtResultDao.getTaskDcgtResultById(id);
	
		JSONArray jsondatda = JSONArray.fromObject(lists);
		
		return jsondatda.toString();
	}

	@Override
	public String updataTaskResAndDcgtInfo(String id, String ishg,
			TbTaskDcgtResult taskDcgtResult) {
		
		//查出原先数据
		TbTaskDcgtResult dcgtResult = taskDcgtResultDao.queryTaskDcgtResultById(taskDcgtResult.getId());
		 dcgtResult.setJcr1(taskDcgtResult.getJcr1());
		 dcgtResult.setZjh1(taskDcgtResult.getZjh1());
		 dcgtResult.setJcr2(taskDcgtResult.getJcr2());
		 dcgtResult.setZjh2(taskDcgtResult.getZjh2());
		 dcgtResult.setJzr(taskDcgtResult.getJzr());
		 dcgtResult.setDwhzs(taskDcgtResult.getDwhzs());
		 dcgtResult.setJcsj_start(taskDcgtResult.getJcsj_start());
		 dcgtResult.setJcsj_end(taskDcgtResult.getJcsj_end());
		 dcgtResult.setJcxm(taskDcgtResult.getJcxm());
		 dcgtResult.setJcgcms(taskDcgtResult.getJcgcms());
		 dcgtResult.setJcjgclyj(taskDcgtResult.getJcjgclyj());
		 dcgtResult.setBjcdw_yj(taskDcgtResult.getBjcdw_yj());
		 dcgtResult.setJzr_yj(taskDcgtResult.getJzr_yj());
		 dcgtResult.setYyzzbh(taskDcgtResult.getYyzzbh());
		 
		 //更新数据
		 if( taskDcgtResultDao.updateTaskRes(dcgtResult)){
			 TbTaskDcgt tdcgt = (TbTaskDcgt) taskDcgtDao.getObject(TbTaskDcgt.class, dcgtResult.getTd_id());
			 tdcgt.setTdIshg(ishg);
			 taskDcgtDao.updateObject(tdcgt);
			 JSONObject jsonObject = new JSONObject();
			 jsonObject.accumulate("success", "success");
			 return jsonObject.toString();
		 }

		return null;
	}
	@Override
	public String exportTaskResult(String taskId) {
		String succInfo = "success";
	    ExportExcel<DetailTaskInfo> ex = new ExportExcel<DetailTaskInfo>();
        String[] headers =  {"抽查行业类型","执法人员", "公司名称", "联系人", "联系电话", "地址" };
        List<Map<String, Object>> list =  taskDcgtResultDao.getTaskDetail(taskId);
        List<DetailTaskInfo> lists = new ArrayList<DetailTaskInfo>();
        DetailTaskInfo taskInfo = null;
       for (Map<String, Object> map : list) {
        	taskInfo = new DetailTaskInfo();
        	taskInfo.setBjcdw(map.get("bjcdw").toString());
        	taskInfo.setDz(map.get("dz").toString());
        	taskInfo.setFddbr(map.get("fddbr").toString());
        	taskInfo.setLxdh(map.get("dh").toString());
        	taskInfo.setMattersName(map.get("mattersname").toString());
        	taskInfo.setUserName(map.get("username").toString());
        	lists.add(taskInfo);
		}
        ActionContext ac = ActionContext.getContext();   
        ServletContext sc = (ServletContext) ac.get(ServletActionContext.SERVLET_CONTEXT); 
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");//设置日期格式
        String fileName = "抽查任务单详情-"+df.format(new Date())+".xls";
        String path = sc.getRealPath("/")+"temp//"+fileName;
      try {
		OutputStream out = new FileOutputStream(path);
		ex.exportExcel("抽查任务单详情",headers, lists, out);
		out.close();//关闭流
		return fileName;
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	return null;
	}
	
	public class DetailTaskInfo{
		private String test;//用于填充格式，无实际作用
		private String mattersName;//事项类型
		private String userName;//执法人员
		private String bjcdw;//公司名称
		private String fddbr;//联系人
		private String lxdh;//联系电话
		private String dz;//地址
		public DetailTaskInfo(String test,String mattersName,String dz, String userName,
				String bjcdw, String fddbr, String lxdh) {
			super();
			this.test = test;
			this.mattersName = mattersName;
			this.userName = userName;
			this.bjcdw = bjcdw;
			this.fddbr = fddbr;
			this.lxdh = lxdh;
			this.dz = dz;
		}
		public DetailTaskInfo() {
			super();
		}
		public String getMattersName() {
			return mattersName;
		}
		public void setMattersName(String mattersName) {
			this.mattersName = mattersName;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getBjcdw() {
			return bjcdw;
		}
		public void setBjcdw(String bjcdw) {
			this.bjcdw = bjcdw;
		}
		public String getFddbr() {
			return fddbr;
		}
		public void setFddbr(String fddbr) {
			this.fddbr = fddbr;
		}
		public String getLxdh() {
			return lxdh;
		}
		public void setLxdh(String lxdh) {
			this.lxdh = lxdh;
		}
		public String getDz() {
			return dz;
		}
		public void setDz(String dz) {
			this.dz = dz;
		}
		public String getTest() {
			return test;
		}
		public void setTest(String test) {
			this.test = test;
		}
	}
	public void setTaskDcgtDao(TaskDcgtDao taskDcgtDao) {
		this.taskDcgtDao = taskDcgtDao;
	}
	
}