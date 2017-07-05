package com.wonders.auth.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IBaseLogin {
	
	public String login() throws Exception;

	public String logout() throws Exception;

	public HttpServletRequest getRequest();

	public void setRequest(HttpServletRequest request);

	public void setServletRequest(HttpServletRequest request);

	public HttpServletResponse getResponse();

	public void setResponse(HttpServletResponse response);

	public void setServletResponse(HttpServletResponse response);
}
