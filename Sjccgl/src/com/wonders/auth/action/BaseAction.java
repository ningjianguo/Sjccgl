package com.wonders.auth.action;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 功能：描述该函数所实现的功能（不要描述如何实现这些功能）
 */
public class BaseAction<T> extends ActionSupport implements ServletRequestAware,ServletResponseAware,ModelDriven<T> {
	
	/**
	 * 无
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 日志
	 */
	protected static final Log log = LogFactory.getLog(BaseAction.class);

	/**
	 * request对象
	 */
	protected HttpServletRequest request;
	/**
	 * 
	 */
	protected HttpServletResponse response;

	/**
	 * 
	 * 功能：描述该函数所实现的功能（不要描述如何实现这些功能） 
	 * 
	 * @return 如果该函数引用或修改了某些全局变量或对象，也应在函数级注释中说明
	 *         ---------------------------------------
	 */
	
	protected T model;
	
	protected File excelFile;
	protected String excelFileFileName;
	
	public BaseAction() {
		try {
			ParameterizedType type = (ParameterizedType) getClass()
					.getGenericSuperclass();
			Class classz = (Class) type.getActualTypeArguments()[0];

			this.model = (T) classz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * 
	 * 功能：描述该函数所实现的功能（不要描述如何实现这些功能）
	 * 
	 * @param request
	 *            如果该函数引用或修改了某些全局变量或对象，也应在函数级注释中说明
	 *            ---------------------------------------
	 */
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * 
	 */
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * 
	 * 功能：描述该函数所实现的功能（不要描述如何实现这些功能） 
	 * 
	 * @param response
	 *            如果该函数引用或修改了某些全局变量或对象，也应在函数级注释中说明
	 *            ---------------------------------------
	 */
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	/**
	 * 打印json格式输出流到前端
	 */
	public void printJsonStringToBrowser(String jsonString){
		try {
			response.setContentType("html/text;charset=UTF-8");
			response.getWriter().write(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public T getModel() {
		return this.model;
	}
	
	public void setExcelFile(File excelFile) {
		this.excelFile = excelFile;
	}
	
	public File getExcelFile() {
		return excelFile;
	}

	public void setExcelFileFileName(String excelFileFileName) {
		this.excelFileFileName = excelFileFileName;
	}

	public String getExcelFileFileName() {
		try {
			return  new String(excelFileFileName.getBytes(), "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
