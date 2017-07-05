package com.wonders.auth.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.wonders.auth.dao.ResourceDao;
import com.wonders.auth.model.TbAuthResource;
import com.wonders.util.UUIDUtil;



public class ResourceDaoImpl extends HibernateDaoSupport implements ResourceDao {

	public List<TbAuthResource> getResourceByUserid(String userid){
		final String sql = "SELECT A.* FROM TB_AUTH_RESOURCE A,TB_AUTH_USER_RESOURCE B "+
						 "WHERE A.RESID = B.RESID "+
						 "  AND B.USERID='"+userid+"' "+
						 "ORDER BY A.TREE_LAYER";
		List<TbAuthResource> list = this.getHibernateTemplate().executeFind(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).addEntity(TbAuthResource.class).list();
			}
			
		});
		return list;
	}
	
	public List<TbAuthResource> getAll(){
		String hql = " from TbAuthResource"; 
		return this.getHibernateTemplate().find(hql);
	}

	public TbAuthResource getResourceByKeyword(String keyword){
		String hql = " from TbAuthResource where keyword='"+keyword+"'";
		List<TbAuthResource> list = this.getHibernateTemplate().find(hql);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public void saveTbPtlog(String operationname,String operator,String actionclassname,String actionmethodname,String ip,String ssproject,String projectname,String mac){
		StringBuffer sqlBuff = new StringBuffer("INSERT INTO TB_PT_LOG(LOGID,OPERATIONNAME,OPERATOR,ACTIONCLASSNAME,ACTIONMETHODNAME,IP,SSPROJECT,PROJECTNAME,OPERATIONSJ,MAC) "); 
		sqlBuff.append("VALUES ('"+UUIDUtil.getUUID()+"','"+operationname+"','"+operator+"','"+actionclassname+"','"+actionmethodname+"','"+ip+"','"+ssproject+"','"+projectname+"',SYSDATE,'"+mac+"')") ;
		final String sql = sqlBuff.toString();
		this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				int count = session.createSQLQuery(sql).executeUpdate();
				if (count != 0){
					return true;
				}
				return false;
			}
		});
	}

	public TbAuthResource getResourceById(String resid) {
		String hql = " from TbAuthResource where resid='"+resid+"'";
		List<TbAuthResource> list = this.getHibernateTemplate().find(hql);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
}
