package com.wonders.main.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.wonders.main.dao.BaseDao;
import com.wonders.util.Page;

public class BaseDaoImpl extends HibernateDaoSupport implements BaseDao {

	@Override
	public List findAll(Class c) {
		String hql = " from " + c.getSimpleName();
		return this.getHibernateTemplate().find(hql);
	}

	@Override
	public void saveObject(Object object) {
		this.getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_EAGER); 
		this.getHibernateTemplate().save(object);
	}

	@Override
	public void updateObject(Object object) {
		this.getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_EAGER); 
		this.getHibernateTemplate().update(object);
	}

	@Override
	public void deleteObject(Object object) {
		this.getHibernateTemplate().delete(object);
	}

	@Override
	public Object getObject(Class c,Serializable id) {
		return this.getHibernateTemplate().get(c, id);
	}

	@Override
	public int queryTotal(Class c) {
		Query query = getSession().createQuery(
				"select count(*) from  " + c.getSimpleName());
		Long count = (Long) query.uniqueResult();
		return count.intValue();
	}

	@Override
	public List getPaging(Class c, Page page) {
		Query query = getSession().createQuery(" FROM  "+  c.getSimpleName());  
        //设置每页显示多少个，设置多大结果。  
        query.setMaxResults(page.getEveryPage());  
        //设置起点  
        query.setFirstResult(page.getBeginIndex());  
        return query.list();
	}

	@Override
	public List getPaging(Class c, Page page, String limitTerm) {
		Query query = getSession().createQuery(" FROM  " +  c.getSimpleName() + limitTerm);  
        //设置每页显示多少个，设置多大结果。  
        query.setMaxResults(page.getEveryPage());  
        //设置起点  
        query.setFirstResult(page.getBeginIndex());  
        return query.list();
	}
}
