package com.wonders.auth.service;

import java.io.File;
import java.util.List;

import com.wonders.auth.model.TbAuthUser;
import com.wonders.main.model.User;


public interface UserService {
	
	/**
	 * 获取用户信息
	 * @param loginName 登录名
	 * @param password 密码
	 * */
	public TbAuthUser checkLogin(String loginName, String passWord);
	
	/**
	 * 获取用户信息
	 * @param loginName 登录名
	 * */
	public TbAuthUser getUserByLoginName(String loginName);
	
	/**
	 * 修改密码
	 * @param loginName 登录名
	 * @param oldPass 原密码
	 * @param newPass 新密码
	 * */
	public String changePassword(String loginName, String oldPass, String newPass);
	
	/**
	 * 获取用户信息
	 * @param id 用户id
	 * */
	public User getUserById(String id);
		
	
	/**
	 * 查询所有数据
	 * @return list<User>
	 */
	public List<User> queryAllUser();
	
	/**
	 * 查询所有数据
	 * @return json格式字符串
	 */
	public String queryAllUsers();
	
	/**
	 * 保存用户
	 * @param user
	 * @return 提示
	 */
	public String saveUser(User user);
	
	/**
	 * 删除用户
	 * @param user
	 * @return 提示
	 */
	public String deleteUser(User user);
	
	/**
	 * 更新用户
	 * @param user
	 * @return 提示
	 */
	public String updataUser(User user);
	
	/**
	 * 分页
	 * @param everyPage  显示个数
	 * @param currentPage  当前页数
	 * @return  json数据
	 */
	public String getUserPaging(Integer everyPage ,Integer currentPage );
	
	/**
	  * 导入数据  返回list
	  * @param header 属性map
	  * @param file   文件
	  * @return
	  */
	 public String importUser(File file);
	 
	 /**
	  * 导出数据
	  * @return
	  */
	 public String exportUser();
	 
	 /**
	  * 按条件分页
	  * @param everyPage 显示个数
	  * @param currentPage  当前页数
	  * @param user  条件
	  * @return  json数据
	  */
	 public String getUserPaging(Integer everyPage ,Integer currentPage,User user );
	 
	
}
