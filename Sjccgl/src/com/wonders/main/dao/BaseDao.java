package com.wonders.main.dao;

import java.io.Serializable;
import java.util.List;

import com.wonders.util.Page;


public interface BaseDao {

public Object getObject(Class c,Serializable id);
	
	public List findAll(Class c);
	
	public void saveObject(Object object);
	
	public void updateObject(Object object);
	
	public void deleteObject(Object object);
	
	public int queryTotal(Class c);
	
	public List getPaging(Class c,Page page);
	
	/**
	 * 根据条件分页查询
	 * @param c
	 * @param page
	 * @param limitTerm 查询限制条件
	 * @return
	 */
	public List getPaging(Class c,Page page,String limitTerm);
}
