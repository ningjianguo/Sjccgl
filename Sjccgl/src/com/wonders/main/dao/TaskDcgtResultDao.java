package com.wonders.main.dao;

import java.util.List;
import java.util.Map;

import com.wonders.main.model.TbTaskDcgtResult;

public interface TaskDcgtResultDao extends BaseDao{
	
	/**
	 * 获取结果归档信息
	 * @param pageNum 当前页号
	 * @param rows	每页总数据条数
	 * @param args 限制条件参数
	 * @return	归档信息
	 */
	public List<Object[]> getTaskDcgtResultByPageNum(int pageNum, int rows,String[] args);
	
	/**
	 * 查询任务详细数据
	 * @param taskid  id
	 * @return  需要的数据
	 */
	public List<Map<String, Object>> getTaskDetail(String taskid);
	
	/**
	 * 查询任务详情条数
	 * @param taskid id
	 * @return 条数
	 */
	public int getTaskDetailNum(String taskid);
	
	/**
	 * 根据ID查询任务详情  
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> getTaskDcgtResultById(String id);
	
	/**
	 * 根据ID查询任务详情  
	 * @param id
	 * @return
	 */
	public TbTaskDcgtResult queryTaskDcgtResultById(String id);
	
	/**
	 * 更新个体中的是否重点
	 * @param id  录入结果表id
	 * @param taskDcgtResult
	 * @return true 成功  false 失败
	 */
	public int updataDcgtInfoIszd(String id, 
			String iszd);
	/**
	 * 更新录入结果表
	 * @param taskDcgtResult
	 * @return
	 */
	public Boolean updateTaskRes(TbTaskDcgtResult taskDcgtResult);
	
}
