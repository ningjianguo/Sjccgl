package com.wonders.main.services;

public interface TaskDcgtService  extends BaseService{

  /**
   * 查询统计数据
   * @param checkId 事项类别iD
   * @param startTime 开始时间
   * @param endTime   结束时间
   * @return  json数据
   */
	public String queryTaskDcgtData(String checkId ,String startTime, String endTime );
}
