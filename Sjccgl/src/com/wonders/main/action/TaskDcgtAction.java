package com.wonders.main.action;

import com.wonders.auth.action.BaseAction;
import com.wonders.main.model.TbTaskDcgt;
import com.wonders.main.services.TaskDcgtService;

public class TaskDcgtAction extends BaseAction<TbTaskDcgt> {

	
	private TaskDcgtService taskDcgtService;
	
	
	
	public void setTaskDcgtService(TaskDcgtService taskDcgtService) {
		this.taskDcgtService = taskDcgtService;
	}


	/**
	 * 查询统计
	 * @return
	 */
	  public String queryTaskDcgt(){
		  
		String check_id = request.getParameter("check_id");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");

		String jsondata = taskDcgtService.queryTaskDcgtData(check_id, startTime, endTime);
		  
	    printJsonStringToBrowser(jsondata);
		 
		  return null;
	  }
	  
}
