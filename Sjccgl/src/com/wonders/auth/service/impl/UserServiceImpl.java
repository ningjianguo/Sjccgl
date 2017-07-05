package com.wonders.auth.service.impl;

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

import com.opensymphony.xwork2.ActionContext;
import com.wonders.auth.dao.UserDao;
import com.wonders.auth.model.TbAuthUser;
import com.wonders.auth.service.UserService;
import com.wonders.main.model.User;
import com.wonders.util.ExcelImport;
import com.wonders.util.ExportExcel;
import com.wonders.util.MD5Util;
import com.wonders.util.Page;
import com.wonders.util.PageUtil;


public class UserServiceImpl implements UserService {
	
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public TbAuthUser checkLogin(String loginName, String passWord) {
		if (passWord != null && !"".equals(passWord)){
			//加密
			passWord = MD5Util.getInstance().calcMD5(passWord);
			return userDao.checkLogin(loginName, passWord);
		}
		return null;
	}

	public TbAuthUser getUserByLoginName(String loginName) {
		return userDao.getUserByLoginName(loginName);
	}

	public String changePassword(String loginName, String oldPass,
			String newPass) {
		//判断该用户名或密码是否正确
		TbAuthUser user = userDao.checkLogin(loginName, MD5Util.getInstance().calcMD5(oldPass));
		if (user != null){
			//修改密码
			boolean con = userDao.changePassword(loginName, MD5Util.getInstance().calcMD5(newPass));
			if (con){
				return "changePassSuccess";
			} else {
				return "changePassFail";
			}
		} else {
			return "oldPassError";
		}
	}

	@Override
	public List<User> queryAllUser() {
		
			List<User> userlist = userDao.queryAllUser();
			
			return userlist;
	}

	@Override
	public String saveUser(User user) {
		
			if(userDao.saveUser(user)){
				return "success";
			}
			return null;
	}

	@Override
	public String deleteUser(User user) {
		if(userDao.deleteUser(user)){
			
			 JSONObject jobj = new JSONObject();
			 jobj.accumulate("info", "success");
			 return jobj.toString();
		}
		return null;
	}

	@Override
	public String updataUser(User user) {
		if(userDao.updataUser(user)){
			return "success";
		}
		return null;
	}

	@Override
	public String getUserPaging(Integer everyPage, Integer currentPage) {
		
		int totalCount =userDao.queryTotal(User.class);
				
	    Page page = PageUtil.createPage(everyPage, totalCount, currentPage);
	    
	    List<User> datas = userDao.getPaging(User.class, page);
	    
	    JSONObject jsonObject= new JSONObject();
	    
	    jsonObject.accumulate("total", totalCount);
	    jsonObject.accumulate("rows", datas);
	    
		return jsonObject.toString();
	}

	@Override
	public User getUserById(String id) {
		
		return userDao.getUserById(id);
	}

	@Override
	public String importUser(File file) {
		 String succInfo = "success";
		 String errInfo = "fail";
		 Map<String, String> header = new HashMap<String, String>();
		   header.put("用户名", "username");
		   header.put("登录名", "loginname");
		   header.put("密码", "psw");
		   header.put("备注", "comments");
		   header.put("性别", "gender");
		   header.put("手机号码", "mobilephone");
		   header.put("电话号码", "telephone");
		   header.put("邮箱", "email");
		   header.put("执法人员编号", "zfrybh");
		
		   ExcelImport export = new ExcelImport(header);
		   export.init(file);
		   try {
			   //封装成list
			List<User> users = export.bindToModels(User.class, true);
			
			for (User user : users) {
				if(user.getPsw()!=null){
					user.setPsw(MD5Util.getInstance().calcMD5(user.getPsw()));
				}
				userDao.saveUser(user);
			}
			return succInfo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public String exportUser() {
		ExportExcel<User> ex = new ExportExcel<User>();
        String[] headers =
        { "用户名", "登录名", "密码", "备注", "性别", "手机号码", "电话号码", "邮箱", "执法人员编号"};
        
         List<User> users =  userDao.findAll(User.class);
       
        ActionContext ac = ActionContext.getContext();   
        ServletContext sc = (ServletContext) ac.get(ServletActionContext.SERVLET_CONTEXT); 
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");//设置日期格式
        String fileName = "执法人员信息库-"+df.format(new Date())+".xls";
        String path = sc.getRealPath("/")+"temp//"+fileName;
      try {
		OutputStream out = new FileOutputStream(path);
		ex.exportExcel("执法人员信息清单", headers, users, out);
		
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
	public String getUserPaging(Integer everyPage, Integer currentPage,
			User user) {
		//多少条数据
		int totalCount =userDao.getTotalByname(user);
		 
		Page page =PageUtil.createPage(everyPage, totalCount, currentPage);
		
		List<User> users = userDao.getUserPaging(page, user);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.accumulate("total", totalCount);
		jsonObject.accumulate("rows", users);
		
		return jsonObject.toString();
	}

	@Override
	public String queryAllUsers() {
		List<User> users = queryAllUser();
		JSONArray jsondata = JSONArray.fromObject(users);
		return jsondata.toString();
	}

}
