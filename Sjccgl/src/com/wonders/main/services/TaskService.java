package com.wonders.main.services;

import java.io.File;

import com.wonders.main.model.TbTask;


public interface TaskService extends BaseService{
	/**
	 * 获取任务信息
	 * @param pageNum 当前页号
	 * @param rows	每页总数据条数
	 * @return	json格式的数据
	 */
	public String getTaskByPageNum(int pageNum, int rows);
	/**
	 * 根据查询条件获取任务信息
	 * @param pageNum 当前页号
	 * @param rows 每页总数据条数
	 * @param taskName 任务名称
	 * @param taskStatus 任务状态
	 * @return json格式的数据
	 */
	public String getTaskByPageNum(int pageNum, int rows,String taskName,String taskStatus);
	
	/**
	 * 添加任务信息
	 * @param tbTask 任务对象
	 * @return 提示信息
	 */
	public boolean saveTask(TbTask tbTask);
	
	/**
	 * 添加非定向、定向抽查信息
	 * @param args 参数数组
	 * @return 提示信息
	 */
	public String saveSearchTask(String[] args,String dcgtid,String iszd);
	
	/**
	 * 编辑任务信息
	 * @param tbTask 任务对象
	 * @return 提示信息
	 */
	public String editeTask(TbTask tbTask);
	
	/**
	 * 删除任务信息
	 * @param tbTask 任务对象
	 * @return 提示信息
	 */
	public String deleteTask(TbTask tbTask);
	
	/**
	 * 导入任务清单
	 * @param file 被上传的文件
	 * @return 成功与否提示符
	 */
	public String importTask(File file);
	
	/**
	 * 导出任务清单
	 * @return 需下载的文件名
	 */
	public String exportTask();
	
	/**
	 * 下拉选项类型
	 * @return
	 */
    public String optionType(); 
    
}
