package com.wonders.auth.dao;

import java.util.List;

import com.wonders.auth.model.TbAuthUser;
import com.wonders.main.dao.BaseDao;
import com.wonders.main.model.User;
import com.wonders.util.Page;

public interface UserDao extends BaseDao {

	public TbAuthUser checkLogin(String loginName, String passWord);
	
	public TbAuthUser getUserByLoginName(String loginName);
	
	public boolean changePassword(String loginName, String passWord);
	
	/**
	 * 查询所有数据
	 * @return list<User>
	 */
	public List<User> queryAllUser();
	
	/**
	 * 获取用户信息
	 * @param id 用户id
	 * */
	public User getUserById(String id);
	
	/**
	 * 保存用户
	 * @param user
	 * @return  true：保存成功、false:保存失败
	 */
	public Boolean saveUser(User user);
	
	/**
	 * 删除用户
	 * @param user
	 * @return true：删除成功、false:删除失败
	 */
	public Boolean deleteUser(User user);
	
	/**
	 * 更新用户
	 * @param user
	 * @return true：更新成功、false:更新失败
	 */
	public Boolean updataUser(User user);
	
	 /**
	  * 按条件分页
	  * @param everyPage 显示个数
	  * @param currentPage  当前页数
	  * @param user  条件
	  * @return  数据
	  */
	 public List<User> getUserPaging(Page page,User user );
	 /**
	  * 按条件查询总数
	  * @param user 条件
	  * @return 
	  */
	 public int getTotalByname(User user);
}
