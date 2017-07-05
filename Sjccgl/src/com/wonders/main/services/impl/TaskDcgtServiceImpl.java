package com.wonders.main.services.impl;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.wonders.main.dao.TaskDao;
import com.wonders.main.dao.TaskDcgtDao;
import com.wonders.main.services.TaskDcgtService;

public class TaskDcgtServiceImpl extends BaseServiceImpl implements TaskDcgtService{
	private TaskDcgtDao taskDcgtDao;
	private TaskDao taskDao;
	
	
	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}
	public void setTaskDcgtDao(TaskDcgtDao taskDcgtDao) {
		this.taskDcgtDao = taskDcgtDao;
	}

	@Override
	public String queryTaskDcgtData(String checkId, String startTime,
			String endTime) {

			
			List<Map<String, Object>> list = taskDcgtDao.queryTackDcgtNum(checkId, startTime, endTime);
			
			if (!list.get(0).isEmpty()) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.accumulate("total", list.size());
				jsonObject.accumulate("rows", list);
				return jsonObject.toString();
				
			}else{
				JSONObject jsonObject = new JSONObject();
				return jsonObject.toString();
			}
			
	}

}
