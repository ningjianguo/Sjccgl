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

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.wonders.main.dao.CheckMattersTypeDao;
import com.wonders.main.dao.DcgtInfoDao;
import com.wonders.main.model.TbCheckMattersType;
import com.wonders.main.model.TbDcgtInfo;
import com.wonders.main.services.DcgtInfoService;
import com.wonders.util.ExcelImport;
import com.wonders.util.ExportExcel;
import com.wonders.util.Page;

public class DcgtInfoServiceImpl extends BaseServiceImpl implements DcgtInfoService {

	private DcgtInfoDao dcgtInfoDao;
	
	private CheckMattersTypeDao checkMattersTypeDao;
	
	public void setDcgtInfoDao(DcgtInfoDao dcgtInfoDao) {
		this.dcgtInfoDao = dcgtInfoDao;
	}
    
    public void setCheckMattersTypeDao(CheckMattersTypeDao checkMattersTypeDao) {
		this.checkMattersTypeDao = checkMattersTypeDao;
	}


/**
 * 把数据转成json
 */
	@Override
	public String queryDcgtListToJson(Page page) {
		
		 List<TbDcgtInfo> dcgtInfos = dcgtInfoDao.getPaging(TbDcgtInfo.class, page);
			
		JSONObject jsonObject= new JSONObject();
		
		for (TbDcgtInfo tbDcgtInfo : dcgtInfos) {
			if(tbDcgtInfo.getCjsj() != null){
			tbDcgtInfo.setCjsj(new java.util.Date(tbDcgtInfo.getCjsj().getTime()));
			}
		}
		
		jsonObject.accumulate("total", page.getTotalCount());
		jsonObject.accumulate("rows", dcgtInfos);
		
		return jsonObject.toString();
	}


	@Override
	public String saveDcgtInfo(TbDcgtInfo dcgtInfo) {
		
		 if (dcgtInfoDao.saveDcgtInfo(dcgtInfo)) {
			 return "success";
		} 
		
		return null;
	}


	@Override
	public String updateDcgt(TbDcgtInfo dcgtInfo) {
		
		 if (dcgtInfoDao.updateDcgt(dcgtInfo)) {
			 return "success";
		} 
		
		return null;
	}


	@Override
	public String deleteDcgt(TbDcgtInfo dcgtInfo) {
		
		 if (dcgtInfoDao.deleteDcgt(dcgtInfo)) {
			 JSONObject jobj = new JSONObject();
			jobj.accumulate("info", "success");
			return jobj.toString();
		} 
		 return null;
	}


	@Override
	public String importDcgtInfo(File file) {
		 String succInfo = "success";
		 String errInfo = "fail";
		 Map<String, String> header = new HashMap<String, String>();
		   header.put("公司名称", "gsmc");
		   header.put("备案时间", "basj");
		   header.put("地址", "dz");
		   header.put("注册资金", "zczj");
		   header.put("证书编号", "zsbh");
		   header.put("企业性质", "qyxz");
		   header.put("经营类型", "jylx");
		   header.put("联系人", "lxr");
		   header.put("事项类别", "hy");
		   header.put("卡类别", "klb");
		   header.put("备案机构名称", "bajgmc");
		   header.put("备注", "bz");
		   header.put("联系电话", "lxdh");
		   header.put("联系传真", "lxcz");
		   header.put("法人", "fr");
		   header.put("总面积", "zmj");
		   header.put("室内面积", "snmj");
		   header.put("展示场地面积", "zscd");
		   header.put("是否具备照相、指纹录入设备", "sfjbzxss");
		   header.put("是否具备规范的内部管理制度", "sfjbgfgl");
		   header.put("交易量", "jyl");
		   header.put("许可证编码", "xkzbm");
		   header.put("批准时间", "pzsj"); 
		   header.put("邮编", "yb");
		   header.put("创建时间", "cjsj");
		   header.put("创建人", "cjr");
		   header.put("是否删除", "sfsc");
		   header.put("工商注册登记号", "gszcdjh");
		   header.put("违法记录", "wfjl");
		   header.put("是否重点检查", "iszd"); 
		   ExcelImport export = new ExcelImport(header);
		   export.init(file);
		   try {
		   //封装成list 保存到数据库
		   List<TbDcgtInfo> tbDcgtInfos = export.bindToModels(TbDcgtInfo.class, true);
		   
		   // 事项类别数据
		   List<TbCheckMattersType> checkMattersTypes = checkMattersTypeDao.findAll(TbCheckMattersType.class);
		   
		    //数据转换
		   for (int i = 0; i < tbDcgtInfos.size(); i++) {
			 
			    for (int j = 0; j < checkMattersTypes.size(); j++) {
					
			    	if(tbDcgtInfos.get(i).getHy().equals(checkMattersTypes.get(j).getCheckMatters())){
			    		
			    		tbDcgtInfos.get(i).setHy(checkMattersTypes.get(j).getCheckId());
			    	}
				}
			     
			    
			     if(tbDcgtInfos.get(i).getSfjbgfgl()!="" | tbDcgtInfos.get(i).getSfjbzxss()!="" |tbDcgtInfos.get(i).getIszd()!=" " | tbDcgtInfos.get(i).getSfsc()!=""){
			    	 
			    	 if( tbDcgtInfos.get(i).getSfjbgfgl().equals("是")){
			    		 tbDcgtInfos.get(i).setSfjbgfgl("1");
			    	 }else if( tbDcgtInfos.get(i).getSfjbgfgl().equals("否")){
			    		 tbDcgtInfos.get(i).setSfjbgfgl("0");
			    	 }
			    	 
			    	 if( tbDcgtInfos.get(i).getSfjbzxss().equals("是")){
			    		 tbDcgtInfos.get(i).setSfjbzxss("1");
			    	 }else if( tbDcgtInfos.get(i).getSfjbzxss().equals("否")){
			    		 tbDcgtInfos.get(i).setSfjbzxss("0");
			    	 }
			    	 
			    	 if( tbDcgtInfos.get(i).getIszd().equals("是")){
			    		 tbDcgtInfos.get(i).setIszd("1");
			    	 }else if( tbDcgtInfos.get(i).getIszd().equals("否")){
			    		 tbDcgtInfos.get(i).setIszd("0");
			    	 }
			    	 
			    	 if( tbDcgtInfos.get(i).getSfsc().equals("是")){
			    		 tbDcgtInfos.get(i).setSfsc("1");
			    	 }else if( tbDcgtInfos.get(i).getSfsc().equals("否")){
			    		 tbDcgtInfos.get(i).setSfsc("0");
			    	 }
			    	 
			     }else{
			    	 tbDcgtInfos.get(i).setSfjbgfgl("1");
			    	 tbDcgtInfos.get(i).setSfjbzxss("1");
			    	 tbDcgtInfos.get(i).setIszd("1");
			    	 tbDcgtInfos.get(i).setSfsc("1");
			     }
			     
			     //创建时时间系统时间
			     tbDcgtInfos.get(i).setCjsj(new Date());
			     
			    //保存
			   if(!dcgtInfoDao.saveDcgtInfo(tbDcgtInfos.get(i))){
				   return errInfo;
			   }
			    
		   }
		   
		} catch (Exception e) {
			e.printStackTrace();
			return errInfo;
		}
		return succInfo;
	}

   /**
    * 导出数据
    */
	@Override
	public String exportDcgtInfo() {
	
		 ExportExcel<TbDcgtInfo> ex = new ExportExcel<TbDcgtInfo>();
	        String[] headers =
	        { "公司名称", "备案时间", "地址", "注册资金", "证书编号", "企业性质", "经营类型", "联系人", "事项类别", "卡类别", "备案机构名称", "备注", "联系电话" , "联系传真", "法人", "总面积", "室内面积", "展示场地面积" , "是否具备照相、指纹录入设备", "是否具备规范的内部管理制度", "交易量", "许可证编码", "批准时间" , "邮编", "创建时间", "创建人", "是否删除", "工商注册登记号" , "违法记录", "是否重点检查"};
	        
	      List<TbDcgtInfo> dcgtInfos =  dcgtInfoDao.findAll(TbDcgtInfo.class);
	       
	      List<TbCheckMattersType> checkMattersTypes = checkMattersTypeDao.findAll(TbCheckMattersType.class);
	       ActionContext ac = ActionContext.getContext();   
	        ServletContext sc = (ServletContext) ac.get(ServletActionContext.SERVLET_CONTEXT); 
	        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");//设置日期格式
	        String fileName = "市场主体信息库-"+df.format(new Date())+".xls";
	        String path = sc.getRealPath("/")+"temp//"+fileName;
	      try {
			OutputStream out = new FileOutputStream(path);
			ex.exportDcgtInfo("市场主体信息库清单",headers, dcgtInfos, out,checkMattersTypes);
			 //关闭流
			out.close();
	            
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	  return fileName; 
	}

		@Override
		public String queryDcgtInfo(String address, String currentPage, String everyPage) {
			List<TbDcgtInfo> datas = dcgtInfoDao.getDcgtByPageNum(Integer.parseInt(currentPage), Integer.parseInt(everyPage),address);
			int totalCount = datas.size();
			for (TbDcgtInfo tbDcgtInfo : datas) {
				String hyId = tbDcgtInfo.getHy();
				String hy = checkMattersTypeDao.getCheckMattersByCheckId(hyId);
				tbDcgtInfo.setHy(hy);
			}
			JSONObject jsonObj = new JSONObject();
			jsonObj.accumulate("total", totalCount);
			jsonObj.accumulate("rows", datas);
			return jsonObj.toString();
		}


}
