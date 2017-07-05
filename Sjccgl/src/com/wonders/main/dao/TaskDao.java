package com.wonders.main.dao;

import java.util.List;

import com.wonders.main.model.TbTask;

public interface TaskDao extends BaseDao {
	
	/**
	 * 获取任务信息
	 * @param pageNum 当前页号
	 * @param rows	每页总数据条数
	 * @return	任务信息
	 */
	public List<TbTask> getTaskByPageNum(int pageNum, int rows);
	
	/**
	 * 根据条件获取任务信息
	 * @param pageNum 当前页号
	 * @param rows	每页总数据条数
	 * @param taskName 任务名称
	 * @param taskStatus 任务状态
	 * @return	任务信息
	 */
	public List<TbTask> getTaskByPageNum(int pageNum, int rows,String taskName,String taskStatus);
	
	/**
	 * 保存任务信息
	 * @param tbTask 任务对象
	 * @return true：保存成功、false:保存失败
	 */
	public Boolean saveTask(TbTask tbTask);
	
	/**
	 * 编辑任务信息
	 * @param tbTask 任务对象
	 * @return true：编辑成功、false:编辑失败
	 */
	public Boolean editeTask(TbTask tbTask);
	
	/**
	 * 删除任务信息
	 * @param tbTask 任务对象
	 * @return true：编辑成功、false:编辑失败
	 */
	public Boolean deleteTask(TbTask tbTask);
	
	/**
	 * 根据多选事项类型id 查询任务信息 
	 * @param ids 多选id 拼接字符串  " 6,2"
	 * @return  返回任务单
	 */
	public List<TbTask> queryByTaskCheckType(String[] ids);
	
	 /**
     * 根据行号求ID
     */
	public String getNewTaskId();
}
