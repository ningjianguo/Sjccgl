package com.wonders.main.dao;

import java.util.List;
import java.util.Map;

import com.wonders.main.model.TbTaskDcgt;

public interface TaskDcgtDao extends BaseDao{

	/**
	 *  查询调查个数 已查询总数
	 * @param checkId 事项类别ID
	 * @param startTime 开始时间
	 * @param endTime  结束时间
	 * @return  map集合
	 */
	public List<Map<String, Object> > queryTackDcgtNum(String checkId, String startTime,
			String endTime);
	/**
	 * 保存任务单详细表信息
	 * @param tbTaskDcgt
	 * @return true 保存成功 false 保存失败
	 */
	public Boolean saveTaskDcgt(TbTaskDcgt tbTaskDcgt);
	
	/**
	 * 获取任务单详细表新增加的主键
	 * @return
	 */
	public String getNewTaskDcgtId();
}
