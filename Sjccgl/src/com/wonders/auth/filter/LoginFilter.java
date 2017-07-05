/**
 * 版权： 指明该文件的版权信息
 * 功能： 指明该文件所实现的功能
 */

package com.wonders.auth.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;

/**
 * 
 * 
 */
public class LoginFilter implements Filter {

	private String myMatchUrl = ".*/login.*,.*/*.gif,.*/*.GIF,.*/*.jpg,.*/*.JPG,.*/*.png,.*/*.js,.*/*.css,.*/sqlhelper.do";

	private String returnPage = "/login.jsp";

	private String[] arrayMyMatchUtl;

	/**
	 * 
	 */
	public void destroy() {

	}

	/**
	 * 
	 * 功能：描述该函数所实现的功能（不要描述如何实现这些功能）
	 * 
	 * @return 如果该函数引用或修改了某些全局变量或对象，也应在函数级注释中说明
	 *         ---------------------------------------
	 */
	public String getMyMatchUrl() {
		return myMatchUrl;
	}

	/**
	 * 
	 * 功能：描述该函数所实现的功能（不要描述如何实现这些功能）
	 * 
	 * @param myMatchUrl
	 *            如果该函数引用或修改了某些全局变量或对象，也应在函数级注释中说明
	 *            ---------------------------------------
	 */
	public void setMyMatchUrl(String myMatchUrl) {
		this.myMatchUrl = myMatchUrl;
	}

	/**
	 * 功能：描述该函数所实现的功能（不要描述如何实现这些功能）
	 * @return 如果该函数引用或修改了某些全局变量或对象，也应在函数级注释中说明
	 */
	public String getReturnPage() {
		return returnPage;
	}

	/**
	 * 功能：描述该函数所实现的功能（不要描述如何实现这些功能） 
	 * @param returnPage
	 * 如果该函数引用或修改了某些全局变量或对象，也应在函数级注释中说明
	 */
	public void setReturnPage(String returnPage) {
		this.returnPage = returnPage;
	}


	/**
	 * 过滤器
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String requestUri = httpRequest.getRequestURI();
		String rootPath = httpRequest.getContextPath();
		boolean match = false;
		if (!ArrayUtils.isEmpty(this.arrayMyMatchUtl)) {
			for (int i = 0; i < this.arrayMyMatchUtl.length; i++) {
				if (requestUri.matches(this.arrayMyMatchUtl[i])) {
					match = true;
					break;
				}
			}
		}
		if (match) {
			chain.doFilter(request, response);
			return;
		}

		Object cuser = null;
		cuser = ((HttpServletRequest) request).getSession().getAttribute("user");

		// 已经登录或正在登录，则继续
		if ((requestUri.equals(rootPath + "/login.action"))
				|| (requestUri.equals(rootPath + "/login.jsp"))
				|| (requestUri.equals(rootPath + "/self_register.jsp"))
				|| (cuser != null)
				|| (requestUri.equals(rootPath + "/security.selfRegister.action"))) {
			chain.doFilter(request, response);
		} else if ((!(requestUri.equals(rootPath + "/login.action"))) && (cuser == null)) {
			if ((null != httpRequest.getHeader("X-Requested-With"))
					&& (httpRequest.getHeader("X-Requested-With").equals("XMLHttpRequest"))) {
				((HttpServletResponse) response).setStatus(500);
				String result = "{\"success\":false, errors:[{\"msg\":\"用户登陆已过期，请重新登陆\"}]" + ",target:\"login.jsp\"} ";
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().print(result);
			} else {
				HttpServletResponse httpResponse = (HttpServletResponse) response;
				httpResponse.reset();
				httpResponse.resetBuffer();
				response.setContentType("text/html");
				PrintWriter printWriter = httpResponse.getWriter();
				printWriter.println("<html>");
				printWriter.println("<head><title></title></head>");
				printWriter.println("<body>");
				printWriter.println("<SCRIPT LANGUAGE=\"JavaScript\">");
				printWriter.println("var currentWindow = window;");
				printWriter.println("if(currentWindow != null){");
				printWriter.println("currentWindow.top.location = \"" + rootPath + "/login.jsp\";");
				printWriter.println("}");
				printWriter.println("</SCRIPT>");
				printWriter.println("</body>");
				printWriter.println("</html>");
				printWriter.flush();
			}
			return;
		}
	}

	public void init(FilterConfig config) throws ServletException {
		if (myMatchUrl == null) {
			myMatchUrl = config.getInitParameter("myMatchUrl");
		}
		if (myMatchUrl == null) {
			returnPage = config.getInitParameter("returnPage");
		}
		if (arrayMyMatchUtl == null) {
			arrayMyMatchUtl = myMatchUrl.trim().split(",");
		}
	}
}
