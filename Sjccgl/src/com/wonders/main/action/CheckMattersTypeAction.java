package com.wonders.main.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.wonders.auth.action.BaseAction;
import com.wonders.main.model.TbCheckMattersType;
import com.wonders.main.services.CheckMattersTypeService;

public class CheckMattersTypeAction extends BaseAction<TbCheckMattersType>{

	private static final long serialVersionUID = -327189659764655338L;
	private int pagerow;
	private int page;
	private CheckMattersTypeService checkMattersTypeService;
	
	//查询事项
	public String queryMattersType(){
		printJsonStringToBrowser(checkMattersTypeService.getMattersTypeByPageNum(page, pagerow));
		return null;
	}
	
	//添加事项
	public String addMattersType(){
		printJsonStringToBrowser(checkMattersTypeService.saveMattersType(getModel()));
		return null;
	}
	
	//修改事项
	public String editeMattersType(){
		printJsonStringToBrowser(checkMattersTypeService.editeMattersType(getModel()));
		return null;
	}
	
	//删除事项
	public String delMattersType(){
		printJsonStringToBrowser(checkMattersTypeService.deleteMattersType(getModel()));
		return null;
	}
	
	//导入事项
	public String importMattersType(){
		String errInfo = "fail";
		//判断是否是excel格式的文件
		if(excelFileFileName.endsWith(".xls")){
				printJsonStringToBrowser(checkMattersTypeService.importMattersType(excelFile));
			}else{
				printJsonStringToBrowser(errInfo);
			}
		return null;
	}
	
	//导出事项
	public String exportMattersType(){
		return SUCCESS;
	}
	
	public InputStream getInputStream(){  
		String filePath = request.getRealPath("/temp");
		excelFileFileName = checkMattersTypeService.exportMattersType();
		File file = new File(filePath, excelFileFileName);
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}  
	     return null;
	 }  
	
	// 下拉选项
	public String optionType(){
		printJsonStringToBrowser(checkMattersTypeService.optionType());
		return null;
	}
	
	public void setPagerow(int pagerow) {
		this.pagerow = pagerow;
	}
	
	public void setPage(int page) {
		this.page = page;
	}
	
	public void setCheckMattersTypeService(
			CheckMattersTypeService checkMattersTypeService) {
		this.checkMattersTypeService = checkMattersTypeService;
	}
}
