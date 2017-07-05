package com.wonders.main.dao.impl;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.wonders.main.dao.TbAuthResourceDao;
import com.wonders.main.model.Resource;

public class TbAuthResourceDaoImpl extends HibernateDaoSupport implements TbAuthResourceDao {

	public List<Resource> findAll() {
		String hql = " from Resource order by treelayer";
		List<Resource> list = this.getHibernateTemplate().find(hql);
		return list;
	}
	
	public List<Resource> findAllByUserid(String userid) {
		final String sql = " SELECT A.*,B.RESID AS TEMP " +
							"  FROM TB_AUTH_RESOURCE A,(SELECT T.RESID FROM TB_AUTH_USER_RESOURCE T WHERE T.USERID='"+userid+"') B " + 
							" WHERE A.RESID = B.RESID(+) " +
							" ORDER BY A.TREE_LAYER";
		List<Resource> list = this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)throws HibernateException, SQLException {
				List<Resource> resourcelist = new LinkedList<Resource>();
				List<Object[]> list = (List<Object[]>)session.createSQLQuery(sql).list();
				for (Object[] object : list) {
					Resource r = new Resource(object);
					resourcelist.add(r);
				}
				return resourcelist;
			}
		});
		return list;
	}
	
	public void saveResource(Resource r){
		this.getHibernateTemplate().save(r);
	}
	
	public void updateResource(Resource r){
		this.getHibernateTemplate().update(r);
	}

	public void deleteResource(Resource r) {
		final String sql = "DELETE FROM TB_AUTH_RESOURCE WHERE RESID='" + r.getResid() + "'";
		this.getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session)throws HibernateException, SQLException {
				return session.createSQLQuery(sql).executeUpdate();
			}
			
		});
	}
	
	
	public void deleteUserResourceByUser(String userid) {
		final String sql = "DELETE FROM TB_AUTH_USER_RESOURCE WHERE USERID='"+userid+"'";
		this.getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session)throws HibernateException, SQLException {
				return session.createSQLQuery(sql).executeUpdate();
			}
			
		});
	}

	public void saveUserResourceByUser(String sql) {
		final String temp = sql;
		this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)throws HibernateException, SQLException {
				return session.createSQLQuery(temp).executeUpdate();
			}
		});
	}

	@Override
	public boolean isUsername(String username) {
		// TODO Auto-generated method stub
		String hql = " from User where loginname = '"+username+"'";
		List<Resource> list = this.getHibernateTemplate().find(hql);
		if(list != null && list.size() > 0){
			return true;
		}else{
			return false;
		}
	}

}
