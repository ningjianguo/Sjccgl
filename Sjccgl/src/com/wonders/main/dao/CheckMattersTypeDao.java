package com.wonders.main.dao;

import java.util.List;

import com.wonders.main.model.TbCheckMattersType;

public interface CheckMattersTypeDao extends BaseDao {
	
	/**
	 * 获取事项信息
	 * @param pageNum 当前页号
	 * @param rows	每页总数据条数
	 * @return	事项信息
	 */
	public List<TbCheckMattersType> getMattersTypeByPageNum(int pageNum, int rows);
	
	/**
	 * 保存事项信息
	 * @param tbCheckMattersType 事项对象
	 * @return true：保存成功、false:保存失败
	 */
	public Boolean saveMattersType(TbCheckMattersType tbCheckMattersType);
	
	/**
	 * 编辑事项信息
	 * @param tbCheckMattersType 事项对象
	 * @return true：编辑成功、false:编辑失败
	 */
	public Boolean editeMattersType(TbCheckMattersType tbCheckMattersType);
	
	/**
	 * 删除事项信息
	 * @param tbCheckMattersType 事项对象
	 * @return true：编辑成功、false:编辑失败
	 */
	public Boolean deleteMattersType(TbCheckMattersType tbCheckMattersType);
	
	/**
	 * 获得最大序号值
	 * @return 序号
	 */
	public int getMaxCheckXuhao();
	
	/**
	 * 根据事项ID得到事项名称
	 * @param checkId 事项ID
	 * @return 事项名称
	 */
	public String getCheckMattersByCheckId(String checkId);
	
	/**
     * 根据抽查类型获取对应类型的主键
     * @param mattersType 抽查类型
     * @return 类型主键
     */
	public String getMattersIdByMattersType(String mattersType); 
}
