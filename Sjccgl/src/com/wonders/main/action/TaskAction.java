package com.wonders.main.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.wonders.auth.action.BaseAction;
import com.wonders.main.model.TbTask;
import com.wonders.main.services.TaskDcgtResultService;
import com.wonders.main.services.TaskService;

public class TaskAction extends BaseAction<TbTask>{

	private static final long serialVersionUID = -327189659764655338L;
	private int pagerow;
	private int page;
	private TaskService taskService;
	private TaskDcgtResultService taskDcgtResultService;
	private String taskId;
	
	//查询任务
	public String queryTask(){
		String taskName = request.getParameter("task_name");
		String taskStatus = request.getParameter("task_status");
		if("".equals(taskName) && "".equals(taskStatus)){
			printJsonStringToBrowser(taskService.getTaskByPageNum(page, pagerow));
		}else{
			printJsonStringToBrowser(taskService.getTaskByPageNum(page, pagerow,taskName,taskStatus));
		}
		return null;
	}
	
	/**
	 * 查询任务详细
	 * @return
	 */
	public String queryTaskDetail(){
		String taskid =  request.getParameter("taskid");
		String jsondata =  taskDcgtResultService.getTaskDetail(taskid);
		printJsonStringToBrowser(jsondata);
		return null;
	}
	
	/**
	 * 查询任务删除
	 * @return
	 */
	public String deleteTask(){
		
		String jsonString = taskService.deleteTask(model);
		
		printJsonStringToBrowser(jsonString);
		return null;
	}
	
	/**
	 * 定向、非定向抽查
	 * @return
	 */
	public String saveTask(){
		String task_name = request.getParameter("task_name");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String task_check_type_name = request.getParameter("task_check_type_name");
		String td_check_user = request.getParameter("check_user");
		String dcgtid = request.getParameter("dcgtid");
		String iszd = request.getParameter("iszd");
		String[] args = {task_name,startTime,endTime,task_check_type_name,td_check_user};
		printJsonStringToBrowser(taskService.saveSearchTask(args,dcgtid,iszd));
		return null;
	}
	
	/**
	 * 导出任务
	 * @return
	 */
	public String exportTask(){
		String taskId =  request.getParameter("exportTaskid");
		this.taskId = taskId;
		return SUCCESS;
	}
	
	public InputStream getInputStream(){  
		String filePath = request.getRealPath("/temp");
		excelFileFileName = taskDcgtResultService.exportTaskResult(this.taskId);
		File file = new File(filePath, excelFileFileName);
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}  
	     return null;
	 }  
	
	//修改任务
	public String editeMattersType(){
		return null;
	}
	
	
	//导入任务
	public String importMattersType(){
		String errInfo = "fail";
		//判断是否是excel格式的文件
		/*if(excelFileFileName.endsWith(".xls")){
				printJsonStringToBrowser(checkMattersTypeService.importMattersType(excelFile));
			}else{
				printJsonStringToBrowser(errInfo);
			}*/
		return null;
	}
	
	//导出任务
	public String exportMattersType(){
		return SUCCESS;
	}
	
	public void setPagerow(int pagerow) {
		this.pagerow = pagerow;
	}
	
	public void setPage(int page) {
		this.page = page;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public void setTaskDcgtResultService(TaskDcgtResultService taskDcgtResultService) {
		this.taskDcgtResultService = taskDcgtResultService;
	}

	
}
