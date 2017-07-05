package com.wonders.main.dao;

import java.util.List;

import com.wonders.main.model.TbDcgtInfo;


public interface DcgtInfoDao  extends BaseDao{


	 /**
	  *  保存数据
	  * @param dcgtInfo
	  * @return true：保存成功、false:保存失败
	  */
	 public Boolean saveDcgtInfo(TbDcgtInfo dcgtInfo );
	 /**
	  * 更新数据
	  * @param dcgtInfo
	  * @return true：更新成功、false:更新失败
	  */
	 public Boolean updateDcgt(TbDcgtInfo dcgtInfo);
	 /**
	  * 删除数据
	  * @param dcgtInfo
	  * @return true：删除成功、false:删除失败
	  */
	 public Boolean deleteDcgt(TbDcgtInfo dcgtInfo);
	 
	 /**
	  * 查询调查个体信息
	  * @param pageNum
	  * @param rows
	  * @param address
	  * @return
	  */
	 public List<TbDcgtInfo> getDcgtByPageNum(int pageNum, int rows, String address);
				
}
