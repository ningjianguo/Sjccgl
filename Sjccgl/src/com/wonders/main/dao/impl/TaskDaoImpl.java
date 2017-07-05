package com.wonders.main.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.wonders.main.dao.TaskDao;
import com.wonders.main.model.TbTask;
import com.wonders.util.Page;
import com.wonders.util.PageUtil;

public class TaskDaoImpl extends BaseDaoImpl implements
		TaskDao {

	@Override
	public List<TbTask> getTaskByPageNum(int pageNum, int rows) {
		int totalCount = queryTotal(TbTask.class);
		Page page = PageUtil.createPage(rows, totalCount, pageNum);
		List datas = getPaging(TbTask.class, page);
		return datas;
	}
	
	@Override
	public List<TbTask> getTaskByPageNum(int pageNum, int rows,
			String taskName, String taskStatus) {
		String hql = null;
		String limitHql = null;
		if("".equals(taskName)){
			limitHql = (taskStatus == "" ? "" : " where taskStatus='"+taskStatus+"'");
		}else if("".equals(taskStatus)){
			limitHql = (taskName == "" ? "" : " where taskName like '%"+taskName+"%'");
		}else{
			limitHql = " where taskStatus='"+taskStatus+"' and taskName like '%"+taskName+"%'";
		}
		hql = "select count(*) from TbTask "+ limitHql;
		Long count = (Long)getSession().createQuery(hql).uniqueResult();
		Page page = PageUtil.createPage(rows, count.intValue(), pageNum);
		List datas = getPaging(TbTask.class, page,limitHql);
		return datas;
	}
	
	@Override
	public Boolean saveTask(TbTask tbTask) {
		try {
			saveObject(tbTask);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
			return true;
	}

	@Override
	public Boolean editeTask(TbTask tbTask) {
		 try {
				updateObject(tbTask);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
	}

	@Override
	public Boolean deleteTask(TbTask tbTask) {
		 try {
				deleteObject(tbTask);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
	}
	
	@Override
	public List<TbTask> queryByTaskCheckType(String[] ids) {
		
		String sql="select * from tb_task ta  where ";
		
		 if(ids!=null){
			 for (int i = 0; i < ids.length; i++) {
				 sql+="ta.task_check_type  like '%"+ids[i]+"%'  and ";
			}
			 sql=sql.substring(0,sql.length()-4);
		 }
		
		Query query = getSession().createSQLQuery(
				sql).addEntity(TbTask.class);
		List<TbTask> tbTasks= query.list();
		return tbTasks;
	}

	@Override
	public String getNewTaskId() {
		String sql = "select task_id from (select rownum rn ,t.task_id from tb_task t) " +
				"where rn=(select count(*) from tb_task)";
		String newId = (String)getSession().createSQLQuery(sql).uniqueResult();
		return newId;
	}
}
