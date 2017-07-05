package com.wonders.main.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.wonders.auth.action.BaseAction;
import com.wonders.main.model.Resource;
import com.wonders.main.model.User;
import com.wonders.main.services.BaseService;
import com.wonders.main.services.TbAuthResourceService;
import com.wonders.util.MD5Util;

public class TbAuthResourceAction extends BaseAction{
	
	private TbAuthResourceService tbAuthResourceService;
	private BaseService baseService;
	private Resource resource = new Resource();
	private User user = new User();

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public BaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}
	
	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public TbAuthResourceService getTbAuthResourceService() {
		return tbAuthResourceService;
	}

	public void setTbAuthResourceService(TbAuthResourceService tbAuthResourceService) {
		this.tbAuthResourceService = tbAuthResourceService;
	}
	
	/**
	 * 功能：根据用户，展示该用户对应的菜单
	 */
	public String showUserResourceMenu() throws Exception {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			String userid = request.getParameter("userid");
			List<Resource> list = this.getTbAuthResourceService().findAllByUserid(userid);
			String menu = this.getTbAuthResourceService().parseReourceToJson(list);
			response.getWriter().print(menu);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	/**
	 * 功能：保存用户
	 */
	public String saveUser()throws Exception{
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		
		this.getBaseService().saveObject(getParameterToUser(request,"save"));
		response.getWriter().print("success");
		return null;
	}
	
	/**
	 * 功能：修改用户
	 */
	public String updateUser()throws Exception{
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		user.setUserid(request.getParameter("user.xguserid"));
		user.setUsername(request.getParameter("user.xgusername"));
		user.setLoginname(request.getParameter("user.xgloginname"));
		user.setComments(request.getParameter("user.xgcomments"));
		user.setMobilephone(request.getParameter("user.xgmobilephone"));
		user.setEmail(request.getParameter("user.xgemail"));
		user.setZfrybh(request.getParameter("user.xgzfrybh"));
		this.tbAuthResourceService.updateUser(user);
		response.getWriter().print("success");
		return null;
	}
	
	/**
	 * 功能：删除用户
	 */
	public String deleteUser()throws Exception{
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		user.setUserid(request.getParameter("user.userid"));
		this.baseService.deleteObject(user);
		response.getWriter().print("success");
		return null;
	}
	
	/**
	 * 功能：从页面封装数据到User对象
	 */
	public User getParameterToUser(HttpServletRequest request,String saveOrUpdate){
		user.setUserid(request.getParameter("user.userid"));
		user.setUsername(request.getParameter("user.username"));
		user.setLoginname(request.getParameter("user.loginname"));
		user.setComments(request.getParameter("user.comments"));
		user.setMobilephone(request.getParameter("user.mobilephone"));
		user.setEmail(request.getParameter("user.email"));
		user.setZfrybh(request.getParameter("user.zfrybh"));
		MD5Util md5 = new MD5Util();
		String psw = md5.calcMD5(request.getParameter("user.password"));
		if(saveOrUpdate.equals("save")){
			user.setPsw(psw);
		}
		return user;
	}
	
	/**
	 * 功能：保存用户所对应资源
	 */
	public String saveUserResourceMenu()throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		
		String menu = request.getParameter("menu");
		String userid = request.getParameter("userid");
		this.getTbAuthResourceService().saveUserResourceByUser(menu, userid);
		response.getWriter().print("success");
		return null;
	}
	
	
	
	
	
	/**
	 * 查询是否已有相同的用户名已注册
	 * @throws IOException 
	 */
	public String findIsUsername() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String username = request.getParameter("username");
		boolean isUsername = this.getTbAuthResourceService().isUsername(username);
		if(isUsername){
			response.getWriter().print("true");
		}else{
			response.getWriter().print("false");
		}
		return null;
	}  
	
}
