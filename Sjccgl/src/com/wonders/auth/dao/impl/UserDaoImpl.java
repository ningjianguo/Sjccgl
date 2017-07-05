package com.wonders.auth.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.wonders.auth.dao.UserDao;
import com.wonders.auth.model.TbAuthUser;
import com.wonders.main.dao.impl.BaseDaoImpl;
import com.wonders.main.model.User;
import com.wonders.util.Page;
import com.wonders.util.PageUtil;


public class UserDaoImpl extends BaseDaoImpl implements UserDao {

	@SuppressWarnings("unchecked")
	public TbAuthUser checkLogin(String loginName, String passWord) {
		String hql = "from TbAuthUser us where us.loginname=? and us.psw=?";
		List<TbAuthUser> list = this.getHibernateTemplate().find(hql,
				new Object[] { loginName, passWord });
		if (list != null && list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public TbAuthUser getUserByLoginName(String loginName) {
		try {
			String hql = "from TbAuthUser us where us.loginname=?";
			List<TbAuthUser> list = this.getHibernateTemplate().find(hql,new Object[]{loginName});
			if (list != null && list.size() != 0) {
				return list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean changePassword(final String loginName, final String passWord) {
		return (Boolean)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session)throws HibernateException, SQLException {
				String hql = "update TbAuthUser us set us.psw=? where us.loginname=?";
				Query query = session.createQuery(hql);
				query.setString(0, passWord);
				query.setString(1, loginName);
				int count = query.executeUpdate();
				if (count != 0){
					return true;
				}
				return false;
			}
		});
	}

	@Override
	public List<User> queryAllUser() {
          try {
        	  
          List<User> users =findAll(User.class);
		  return users;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Boolean saveUser(User user) {
		
		 try {
			saveObject(user);
			return true ;
		} catch (Exception e) {
			e.printStackTrace();
			return false ;
		}
	}

	@Override
	public Boolean deleteUser(User user) {
		
		try {
			deleteObject(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Boolean updataUser(User user) {
		 
		try {
			updateObject(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public User getUserById(String id) {
		
		User user = null;
		try {
			user = (User) getSession().createQuery("from User us where us.userid=:str").setString("str", id).uniqueResult();
		} catch (DataAccessResourceFailureException e) {
			e.printStackTrace();
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public List<User> getUserPaging(Page page,
			User user) {
		
		String  sql = "select * from tb_auth_user us where 1=1 ";
		if(!"".equals(user.getUsername())){
			sql+=" and us.username like '%"+user.getUsername()+"%'";
		}
		if(!"".equals(user.getLoginname())){
			sql+=" and us.loginname like '%"+user.getLoginname()+"%'";
		}
		if(!"".equals(user.getMobilephone())){
			sql+=" and us.mobilephone like '%"+user.getMobilephone()+"%'";
		}
		
		Query query = getSession().createSQLQuery(sql).addEntity(User.class);
		 
		 query.setMaxResults(page.getEveryPage());  
	        //设置起点  
	     query.setFirstResult(page.getBeginIndex()); 
	     
	     List<User> users =  query.list();
		return users;
	}

	@Override
	public int getTotalByname(User user) {
		
		String  sql = "select count(*) from tb_auth_user us where 1=1 ";
		if(!"".equals(user.getUsername())){
			sql+=" and us.username like '%"+user.getUsername()+"%'";
		}
		if(!"".equals(user.getLoginname())){
			sql+=" and us.loginname like '%"+user.getLoginname()+"%'";
		}
		if(!"".equals(user.getMobilephone())){
			sql+=" and us.mobilephone like '%"+user.getMobilephone()+"%'";
		}
		Query query = getSession().createSQLQuery(sql);
		 BigDecimal  bigDecimal= (BigDecimal)query.uniqueResult();
		return bigDecimal.intValue();
	}

}
