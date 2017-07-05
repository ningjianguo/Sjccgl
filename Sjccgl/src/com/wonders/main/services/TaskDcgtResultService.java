package com.wonders.main.services;

import com.wonders.main.model.TbTaskDcgtResult;

public interface TaskDcgtResultService  extends BaseService{

	/**
	 * 查询结果归档
	 * @param 
	 * @return
	 */
	public String getTaskDcgtResultByPageNum(int page, int pagerow, String[] args);
	
	/**
	 * 查询任务详细 
	 * @param taskid 
	 * @return json数据
	 */
	public String getTaskDetail(String taskid);
	
	/**
	 * 根据ID查询详细信息
	 * @param id
	 * @return
	 */
	public String getTaskDcgtResultById(String id);
	
	/**
	 * 更新任务录入表 和是否重点
	 * @param id    id
	 * @param isZd  是否重点
	 * @param taskDcgtResult  
	 * @return    提示
	 */
	public String updataTaskResAndDcgtInfo(String id ,String isZd ,TbTaskDcgtResult taskDcgtResult);
	
	/**
	 * 导出任务表
	 * @param taskId 任务单ID
	 * @return
	 */
	public String exportTaskResult(String taskId);
}
