package com.wonders.main.services.impl;

import java.io.Serializable;
import java.util.List;

import com.wonders.main.dao.BaseDao;
import com.wonders.main.services.BaseService;
import com.wonders.util.Page;

public class BaseServiceImpl implements BaseService{

	private BaseDao baseDao;

	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List findAll(Class c) {
		return this.getBaseDao().findAll(c);
	}

	@Override
	public void saveObject(Object object) {
		this.getBaseDao().saveObject(object);
	}

	@Override
	public void updateObject(Object object) {
		this.getBaseDao().updateObject(object);
	}

	@Override
	public void deleteObject(Object object) {
		this.getBaseDao().deleteObject(object);
	}
	
	public Object getObject(Class c,Serializable id){
		return this.getBaseDao().getObject(c, id);
	}

	@Override
	public int queryTotal(Class c) {
		return this.getBaseDao().queryTotal(c);
	}

	@Override
	public List getPaging(Class c, Page page) {
		return this.getBaseDao().getPaging(c, page);
	}
	
}
