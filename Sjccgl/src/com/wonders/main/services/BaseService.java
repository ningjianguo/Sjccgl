package com.wonders.main.services;

import java.io.Serializable;
import java.util.List;

import com.wonders.auth.model.TbAuthResource;
import com.wonders.main.model.Resource;
import com.wonders.util.Page;


public interface BaseService {

	public List findAll(Class c);
	
	public void saveObject(Object object);
	
	public void updateObject(Object object);
	
	public void deleteObject(Object object);
	
	public Object getObject(Class c,Serializable id);
	
    public int queryTotal(Class c);
	
	public List getPaging(Class c,Page page);
}
