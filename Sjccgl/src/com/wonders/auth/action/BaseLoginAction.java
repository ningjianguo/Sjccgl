package com.wonders.auth.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.wonders.auth.model.TbAuthResource;
import com.wonders.auth.model.TbAuthUser;
import com.wonders.auth.service.ResourceService;
import com.wonders.auth.service.UserService;
import com.wonders.main.model.User;

public class BaseLoginAction extends BaseAction<User> implements IBaseLogin {

	private static final long serialVersionUID = -3026714614660066511L;
	private UserService userService;
	private ResourceService resourceService;
	private String loginName;
	private String passWord;
	private String message;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	/**
	 * 登录
	 * */
	public String login() throws Exception {
		log.info("loginAction begin************************");
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		boolean isValid = false;
		TbAuthUser user = null;
		// 判断是否已经在sso验证通过
		if ((request.getRemoteUser() != null) && (!request.getRemoteUser().equals(""))) {
			loginName = request.getRemoteUser();
			// 直接获取用户信息
			user = userService.getUserByLoginName(loginName);
			if (user != null) {
				isValid = true;
			}
		} else {
			// 获得s权限，这个是不使用单点登录过来的
			if (StringUtils.isNotBlank(loginName) && StringUtils.isNotBlank(passWord)) {
				try {
					user = userService.checkLogin(loginName, passWord);
					if (user != null) {
						isValid = true;
						log.debug("username is " + user.getLoginname() + user.getUserid());
						log.debug("authentication is " + isValid);
						log.debug("username is " + user.getUsername());
					}
				} catch (Exception e) {
					log.info(e.getMessage());
					message = "请确认用户名或密码是否正确！";
					this.addActionMessage(message);
					//return ERROR;
					response.getWriter().write("用户名或密码错误，请重新输入！");
					return null;
				}
			} else {
				if (StringUtils.isNotBlank(passWord) && StringUtils.isBlank(loginName)) {
					this.message = "请输入用户名！";
				} else if (StringUtils.isNotBlank(loginName) && StringUtils.isBlank(passWord)) {
					this.message = "请输入密码！";
				}
			}
		}
		// 登录成功
		log.info("loginAction end************************");
		if (user != null) {
			Map session = ActionContext.getContext().getSession();
			
			List<TbAuthResource> authlist = this.resourceService.getResourceByUserid(user.getUserid());
			session.put("authlist", authlist);
			
			//在LoginFilter中需要判定user对象是否存在
			session.put("user", user);
			session.put("loginUser", user);
			session.put("userName", user.getUsername());
			return SUCCESS;
		} else {
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return null;
		}
	}

	/**
	 * 登出
	 * */
	public String logout() throws Exception {
		ActionContext.getContext().getSession().clear();
		return SUCCESS;
	}

}
