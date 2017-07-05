package com.wonders.main.services;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.wonders.main.model.TbDcgtInfo;
import com.wonders.util.Page;



public interface DcgtInfoService extends BaseService{
     /**
      * 把数据转成json
      * @return
      */
	 public String queryDcgtListToJson(Page page);
	 
	 /**
	  *  保存数据
	  * @param dcgtInfo
	  * @return
	  */
	 public String saveDcgtInfo(TbDcgtInfo dcgtInfo );
	 /**
	  * 更新数据
	  * @param dcgtInfo
	  * @return
	  */
	 public String updateDcgt(TbDcgtInfo dcgtInfo);
	 /**
	  * 删除数据
	  * @param dcgtInfo
	  * @return
	  */
	 public String deleteDcgt(TbDcgtInfo dcgtInfo);
	 
	 /**
	  * 导入数据  返回list
	  * @param header 属性map
	  * @param file   文件
	  * @return
	  */
	 public String importDcgtInfo(File file);
	 
	 /**
	  * 导出数据
	  * @return
	  */
	 public String exportDcgtInfo();
	 
	 /**
	  * 查询调查个体信息
	  * @param address 公司地址
	  * @param currentPage 当前页
	  * @param everyPage 每页显示行数
	  * @return
	  */
	 public String queryDcgtInfo(String address,String currentPage,String everyPage);
}
