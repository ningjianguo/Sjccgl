package com.wonders.main.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;

import com.wonders.auth.action.BaseAction;
import com.wonders.auth.service.UserService;
import com.wonders.main.model.User;
import com.wonders.util.MD5Util;

public class UserAction extends BaseAction<User> {

	private UserService userService;
	


	  /**
	    * 显示数据
	    * @param pagepojo
	    * @return
	    */
		@SuppressWarnings("unchecked")
		public String queryUserList(){
		    //当前页数
			String currentPage = request.getParameter("page");
		    //显示多少个
			String everyPage = request.getParameter("pagerow");
			
			
			String loginname = request.getParameter("loginname");
			String username = request.getParameter("username");
			String mobilephone = request.getParameter("mobilephone");
			
			//显示数据
			String dataJson="";
			if(loginname!=null && !"".equals(loginname) || username!=null && !"".equals(username)  || mobilephone!=null && !"".equals(mobilephone) ){
				
				User user = new User();
				user.setLoginname(loginname);
				user.setUsername(username);
				user.setMobilephone(mobilephone);
				dataJson = userService.getUserPaging(Integer.valueOf(everyPage), Integer.valueOf(currentPage), user);
				
			}else{
				 dataJson = userService.getUserPaging(Integer.valueOf(everyPage), Integer.valueOf(currentPage));
			}
			
		    printJsonStringToBrowser(dataJson);
		   
			return null;
		}
		
		
		/**
		 * 添加用户
		 * @return
		 * @throws ParseException 
		 */
	   public String saveUser() throws ParseException{
		   
		   if (getModel().getPsw()!=null && !getModel().getPsw().equals("") ) {
			   
			//加密   
			   getModel().setPsw(MD5Util.getInstance().calcMD5(getModel().getPsw()));
		 }
		   
		 String str =  userService.saveUser(getModel());
		   
		 printJsonStringToBrowser(str);
		 
		return null;
	   }

	   /**
	    * 更新用户
	    * @return
	    */
	   public String updateUser(){
		 
		   if (model.getPsw()!=null && !model.getPsw().equals("") ) {
			   
			   //未修改密码
			   User user = userService.getUserById(model.getUserid());
			   
			   if(!user.getPsw().equals(model.getPsw())){
				 //加密   
				   model.setPsw(MD5Util.getInstance().calcMD5(model.getPsw()));
			   }
				
			 }
		   
		   String str =  userService.updataUser(getModel());
		   
		   printJsonStringToBrowser(str);
		   
		   return null;
	   }
	   
	   /**
	    * 删除用户
	    * @return
	    */
	   public String deleteUser(){
		   
           String str =  userService.deleteUser(getModel());
		   
		   printJsonStringToBrowser(str);
		   
		   return null;
	   }
	   
	   /**
	    * Excel导入
	    * @return
	    */
	   public String importUser(){
		   
		   String errInfo = "fail";
			//判断是否是excel格式的文件
			if(excelFileFileName.endsWith(".xls")){
				printJsonStringToBrowser(userService.importUser(excelFile));
			}else{
				printJsonStringToBrowser(errInfo);
			}
		   return null;
	   }
	   
	   /**
	    * 导出数据成Excel
	    * @return
	    */
	   public String exportUser(){
		  
		   return SUCCESS;
	   }
		
		public InputStream getInputStream(){  
			String filePath = request.getRealPath("/temp");
			excelFileFileName = userService.exportUser();
			File file = new File(filePath, excelFileFileName);
			try {
				return new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}  
		     return null;
		 } 
		
		/**
		 * 获取所有用户
		 * @return
		 */
		public String getAllUser(){
			printJsonStringToBrowser(userService.queryAllUsers());
			return null;
		}
		
		
	 public void setUserService(UserService userService) {
			this.userService = userService;
	 }

}
