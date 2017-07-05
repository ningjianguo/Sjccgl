package com.wonders.main.services;

import java.io.File;

import com.wonders.main.model.TbCheckMattersType;


public interface CheckMattersTypeService extends BaseService{
	/**
	 * 获取事项信息
	 * @param pageNum 当前页号
	 * @param rows	每页总数据条数
	 * @return	json格式的数据
	 */
	public String getMattersTypeByPageNum(int pageNum, int rows);
	
	/**
	 * 添加事项信息
	 * @param tbCheckMattersType 事项对象
	 * @return 提示信息
	 */
	public String saveMattersType(TbCheckMattersType tbCheckMattersType);
	
	/**
	 * 编辑事项信息
	 * @param tbCheckMattersType 事项对象
	 * @return 提示信息
	 */
	public String editeMattersType(TbCheckMattersType tbCheckMattersType);
	
	/**
	 * 删除事项信息
	 * @param tbCheckMattersType 事项对象
	 * @return 提示信息
	 */
	public String deleteMattersType(TbCheckMattersType tbCheckMattersType);
	
	/**
	 * 导入事项清单
	 * @param file 被上传的文件
	 * @return 成功与否提示符
	 */
	public String importMattersType(File file);
	
	/**
	 * 导出事项清单
	 * @return 需下载的文件名
	 */
	public String exportMattersType();
	
	/**
	 * 下拉选项类型
	 * @return
	 */
    public String optionType(); 
    
}
