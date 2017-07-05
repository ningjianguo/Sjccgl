package com.wonders.main.services.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.wonders.main.dao.CheckMattersTypeDao;
import com.wonders.main.model.TbCheckMattersType;
import com.wonders.main.services.CheckMattersTypeService;
import com.wonders.util.ExcelImport;
import com.wonders.util.ExportExcel;

@Service
public class CheckMattersTypeServiceImpl extends BaseServiceImpl implements CheckMattersTypeService {

	private CheckMattersTypeDao checkMattersTypeDao;
	
	@Override
	public String getMattersTypeByPageNum(int pageNum, int rows) {
		List<TbCheckMattersType> datas = checkMattersTypeDao.getMattersTypeByPageNum(pageNum, rows);
		int totalCount = queryTotal(TbCheckMattersType.class);
		JSONObject jsonObj = new JSONObject();
		jsonObj.accumulate("total", totalCount);
		jsonObj.accumulate("rows", datas);
		return jsonObj.toString();
	}
	
	@Override
	public String saveMattersType(TbCheckMattersType tbCheckMattersType) {
		if(checkMattersTypeDao.saveMattersType(tbCheckMattersType)){
			return "success";
		}
		return null;
	}
	
	public void setCheckMattersTypeDao(CheckMattersTypeDao checkMattersTypeDao) {
		this.checkMattersTypeDao = checkMattersTypeDao;
	}
	
	@Override
	public String optionType() {
		List<TbCheckMattersType> checkMattersTypes =  checkMattersTypeDao.findAll(TbCheckMattersType.class);
		JSONArray jsondata = JSONArray.fromObject(checkMattersTypes);
		return jsondata.toString();
	}

	@Override
	public String editeMattersType(TbCheckMattersType tbCheckMattersType) {
		if(checkMattersTypeDao.editeMattersType(tbCheckMattersType)){
			return "success";
		}
		return null;
	}

	@Override
	public String deleteMattersType(TbCheckMattersType tbCheckMattersType) {
		if(checkMattersTypeDao.deleteMattersType(tbCheckMattersType)){
			JSONObject jobj = new JSONObject();
			jobj.accumulate("info", "success");
			return jobj.toString();
		}
		return null;
	}

	@Override
	public String importMattersType(File file) {
		   String errInfo = "fail";
		   String succInfo = "success";
		   Map<String, String> header = new HashMap<String, String>();
		   header.put("序号", "checkXuhao");
		   header.put("事项名称", "checkMatters");
		   header.put("抽查依据", "checkYj");
		   header.put("抽查主体", "checkBody");
		   header.put("抽查对象", "checkObject");
		   header.put("抽查内容", "checkContent");
		   header.put("抽查比例", "checkBl");
		   header.put("抽查频次", "checkPc");
		   ExcelImport export = new ExcelImport(header);
		   export.init(file);
		   try {
			List<TbCheckMattersType> checkMattersTypes = export.bindToModels(TbCheckMattersType.class, true);
			for (TbCheckMattersType tbCheckMattersType : checkMattersTypes) {
				if(!checkMattersTypeDao.saveMattersType(tbCheckMattersType)){
					return errInfo;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return succInfo;
	}

	@Override
	public String exportMattersType() {
			String succInfo = "success";
		    ExportExcel<TbCheckMattersType> ex = new ExportExcel<TbCheckMattersType>();
	        String[] headers =  {"序号","事项名称", "抽查依据", "抽查主体", "抽查对象", "抽查内容", "抽查比例", "抽查频次" };
	        List<TbCheckMattersType> checkMattersTypes =  checkMattersTypeDao.findAll(TbCheckMattersType.class);
	        ActionContext ac = ActionContext.getContext();   
	        ServletContext sc = (ServletContext) ac.get(ServletActionContext.SERVLET_CONTEXT); 
	        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");//设置日期格式
	        String fileName = "事项清单-"+df.format(new Date())+".xls";
	        String path = sc.getRealPath("/")+"temp//"+fileName;
	      try {
			OutputStream out = new FileOutputStream(path);
			ex.exportExcel("随机抽查事项清单",headers, checkMattersTypes, out);
			out.close();//关闭流
			return fileName;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
