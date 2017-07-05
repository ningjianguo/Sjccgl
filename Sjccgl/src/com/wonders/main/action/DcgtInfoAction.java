package com.wonders.main.action;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.wonders.auth.action.BaseAction;
import com.wonders.main.model.TbDcgtInfo;
import com.wonders.main.services.DcgtInfoService;
import com.wonders.util.Page;
import com.wonders.util.PageUtil;


public class DcgtInfoAction  extends BaseAction<TbDcgtInfo>{
	
	private DcgtInfoService dcgtInfoService;
	
	public void setDcgtInfoService(DcgtInfoService dcgtInfoService) {
		this.dcgtInfoService = dcgtInfoService;
	}
	
  /**
    * 显示数据
    * @param pagepojo
    * @return
    */
	@SuppressWarnings("unchecked")
	public String queryDcgtList(){
	    //   当前页
		String currentPage = request.getParameter("page");
	    //  显示个数
		String everyPage = request.getParameter("pagerow");
		//总数量
	    int totalCount = dcgtInfoService.queryTotal(TbDcgtInfo.class);
		
	    Page page = PageUtil.createPage(Integer.valueOf(everyPage), totalCount, Integer.valueOf(currentPage));
	    
	    String datajson = dcgtInfoService.queryDcgtListToJson(page);
	    
	    printJsonStringToBrowser(datajson);
	    
		return null;
		
		
		
	}
	/**
	 * 添加信息
	 * @return
	 * @throws ParseException 
	 */
   public String saveDcgtInfo() throws ParseException{
	   
	   //创建时间
	   model.setCjsj(new Date());
	   
	   //穿件人
	   Map session = ActionContext.getContext().getSession();
	   model.setCjr(session.get("userName").toString());
	  
	   printJsonStringToBrowser(dcgtInfoService.saveDcgtInfo(model));
		
	return null;
   }

   /**
    * 更新信息
    * @return
    */
   public String updateDcgt(){
	   
	   printJsonStringToBrowser(dcgtInfoService.updateDcgt(this.model));
	   
	   return null;
   }
   
   /**
    * 更新信息
    * @return
    */
   public String deleteDcgt(){
	   
	   printJsonStringToBrowser(dcgtInfoService.deleteDcgt(this.model));
	   
	   return null;
   }
   
   /**
    * Excel导入
    * @return
    */
   public String importDcgtInfo(){
	   
      String errInfo = "fail";
		//判断是否是excel格式的文件
		if(excelFileFileName.endsWith(".xls")){
			printJsonStringToBrowser(dcgtInfoService.importDcgtInfo(excelFile));
		}else{
			printJsonStringToBrowser(errInfo);
		}
	   return null;
   }
   
   /**
    * 导出数据成Excel
    * @return
    */
   public String exportDcgtInfo(){
	  
	   return SUCCESS;
   }
	
	public InputStream getInputStream(){  
		String filePath = request.getRealPath("/temp");
		excelFileFileName = dcgtInfoService.exportDcgtInfo();
		File file = new File(filePath, excelFileFileName);
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}  
	     return null;
	 } 
   
	/**
	 * 显示个体调查列表
	 * @return
	 */
	public String queryCompanyInfo(){
		String address = request.getParameter("dz");
		String currentPage = request.getParameter("page");// 当前页
		String everyPage = request.getParameter("pagerow");// 显示个数
		printJsonStringToBrowser(dcgtInfoService.queryDcgtInfo(address,currentPage,everyPage));
		return null;
	}
}
