package com.wonders.main.action;

import com.wonders.auth.action.BaseAction;
import com.wonders.main.model.TbCheckMattersType;
import com.wonders.main.model.TbTaskDcgtResult;
import com.wonders.main.services.TaskDcgtResultService;

public class TaskDcgtResultAction extends BaseAction<TbTaskDcgtResult>{

	private static final long serialVersionUID = -327189659764655338L;
	private int pagerow;
	private int page;
	private TaskDcgtResultService taskDcgtResultService;
	
	//查询结果
	public String queryDcgtResult(){
		String id = request.getParameter("td_task_id");
		String gsmc = request.getParameter("gsmc");
		String check_matters = request.getParameter("check_matters");
		String username = request.getParameter("username");
		String[] args = {id,gsmc,check_matters,username};
		printJsonStringToBrowser(taskDcgtResultService.getTaskDcgtResultByPageNum(page,pagerow,args));
		return null;
	}
	
	//查询结果详细信息
	public String queryDetailDcgtResult(){
		String id = request.getParameter("td_id");
		printJsonStringToBrowser(taskDcgtResultService.getTaskDcgtResultById(id));
		return null;
	}
	
	/**
	 * 结果录入
	 * @return
	 */
	public String saveOrUpdateTaskDcgtResult(){
		
		String id = request.getParameter("td_id");
		
		String ishg = request.getParameter("iszd");
		
		String str = taskDcgtResultService.updataTaskResAndDcgtInfo(id, ishg, model);
		
		printJsonStringToBrowser(str);
		return null;
	}
	
	public void setPagerow(int pagerow) {
		this.pagerow = pagerow;
	}
	
	public void setPage(int page) {
		this.page = page;
	}

	public void setTaskDcgtResultService(TaskDcgtResultService taskDcgtResultService) {
		this.taskDcgtResultService = taskDcgtResultService;
	}
}
