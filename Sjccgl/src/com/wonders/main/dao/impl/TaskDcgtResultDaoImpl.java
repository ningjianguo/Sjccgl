package com.wonders.main.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import com.wonders.main.dao.TaskDcgtResultDao;
import com.wonders.main.model.TbTaskDcgtResult;


public class TaskDcgtResultDaoImpl extends BaseDaoImpl implements TaskDcgtResultDao{

	@Override
	public List<Object[]> getTaskDcgtResultByPageNum(int pageNum, int rows,
			String[] args) {
		String hql = "from tb_task  ta inner join tb_task_dcgt tadc  on ta.task_id=tadc.td_task_id " +
				"inner join  tb_task_dcgt_result tadcre on tadcre.td_id=tadc.td_id ";
		String limitTaskId = "where ta.task_id='"+args[0]+"'";
		String hqlCount = "select count(*) "+hql+limitTaskId;
		String hqlId = "select tadcre.id "+hql+limitTaskId;
		String hqlResult = "select tadc.td_id, ta.task_check_type_name,tadc.td_check_user,tadc.td_check_user2," +
				"tadcre.bjcdw,tadcre.fddbr,tadcre.dh,tadcre.dz,tadc.td_isinputresult "+hql;
		List<String> ids = sqlQuery(hqlId, args,null).list();
		List<Object[]> lists = new ArrayList<Object[]>();
		for (String id : ids) {
			Object[] datas = (Object[]) sqlQuery(hqlResult+"where tadcre.id='"+id+"'", null,id).uniqueResult();
			lists.add(datas);
		}
		return lists;
	}
	
	private Query sqlQuery(String hql,String[] args,String id){
		Query query = null;
		if(args!=null&&!"".equals(args[1])){
			hql+=" and tadcre.bjcdw like '%"+args[1]+"%'";
		}
		if(args!=null&&!"".equals(args[2])){
			hql+=" and ta.task_check_type_name like '%"+args[2]+"%'";
		}
		if(args!=null&&!"".equals(args[3])){
			hql+=" and tadc.td_check_user like '%"+args[3]+"%'";
		}
		if(args == null){
			query = getSession().createSQLQuery(hql);
		}else{
		    query = getSession().createSQLQuery(hql);
		}
		return query;
	}
	@Override
	public List<Map<String, Object>> getTaskDetail(String taskid) {
		Query query =getSession().createSQLQuery("select ta.task_check_type_name , tbres.jcr1 ,tbres.jcr2 ,tbres.bjcdw ,tbres.fddbr ,tbres.dh ,tbres.dz, tadc.td_isinputresult,tbres.td_id from tb_task ta  inner join tb_task_dcgt tadc on ta.task_id=tadc.td_task_id  inner  join tb_task_dcgt_result tbres on tbres.td_id=tadc.td_id  where ta.task_id=:str");
		query.setString("str", taskid);
		
        List<Object> result = query.list();
	 	
	 	Iterator it = result.iterator(); 
	 	List<Map<String, Object>> lists = new ArrayList<Map<String,Object>>();
	 	Map<String, Object> map = null;
	 	
	 	String username="";
	 	while (it.hasNext()) {      
	 	   map = new HashMap<String, Object>();
	 	   Object[] tuple = (Object[]) it.next();
	 	   map.put("mattersname", tuple[ 0 ]);
	 	   username=tuple[ 1 ]+";"+tuple[ 2 ];
	 	   map.put("username", username);
	 	   map.put("bjcdw", tuple[ 3 ]);
	 	   map.put("fddbr", tuple[ 4 ]);
	 	   map.put("dh", tuple[ 5] );
	 	   map.put("dz", tuple[ 6] );
	 	   map.put("isinputresult", tuple[ 7]);
	 	   map.put("tdId", tuple[ 8]);
	 	   lists.add(map);
	 	}  
		return lists;
				
	}

	@Override
	public int getTaskDetailNum(String taskid) {
		
			Query query =getSession().createSQLQuery("select count(*)  from tb_task ta  inner join tb_task_dcgt tadc on ta.task_id=tadc.td_task_id  inner  join tb_task_dcgt_result tbres on tbres.td_id=tadc.td_id  where ta.task_id =:str");
			query.setString("str", taskid);
			BigDecimal	integer = (BigDecimal) query.uniqueResult();
			
		return integer.intValue();
	}

	@Override
	public List<Map<String, Object>> getTaskDcgtResultById(String id) {
		
		String sql = "select  tbdc.td_ishg, tbdcres.jcr1 ,tbdcres.zjh1 ,tbdcres.jcr2 ,tbdcres.zjh2 ,tbdcres.id ,tbdcres.jzr ,tbdcres.dwhzs ,tbdcres.jcsj_start ,tbdcres.jcsj_end ,tbdcres.jcxm ,tbdcres.jcgcms ,tbdcres.jcjgclyj ,tbdcres.bjcdw_yj ,tbdcres.jzr_yj ,tbdcres.yyzzbh   from tb_task_dcgt_result tbdcres inner join tb_task_dcgt tbdc on tbdc.td_id=tbdcres.td_id  where tbdcres.td_id=:id ";
		Query query =getSession().createSQLQuery(sql);
		query.setString("id", id);
		
        List<Object> result = query.list();
	 	Iterator it = result.iterator(); 
	 	
	 	List<Map<String, Object>> lists = new ArrayList<Map<String,Object>>();
	 	Map<String, Object> map = null;
	 	
	 	while (it.hasNext()) {      
	 	   Object[] tuple = (Object[]) it.next();
	 	   map= new HashMap<String, Object>();
	 	   map.put("iszd", tuple[ 0 ]);
	 	   map.put("jcr1", tuple[ 1 ]);
	 	   map.put("zjh1", tuple[ 2 ]);
	 	   map.put("jcr2", tuple[ 3 ]);
	 	   map.put("zjh2", tuple[ 4 ]);
	 	   map.put("id", tuple[ 5 ]);
	 	   map.put("jzr", tuple[ 6 ]);
	 	   map.put("dwhzs", tuple[ 7 ]);
	 	   map.put("jcsj_start", tuple[ 8 ]);
	 	   map.put("jcsj_end", tuple[ 9 ]);
	 	   map.put("jcxm", tuple[ 10 ]);
	 	   map.put("jcgcms", tuple[ 11 ]);
	 	   map.put("jcjgclyj", tuple[ 12 ]);
	 	   map.put("bjcdw_yj", tuple[ 13 ]);
	 	   map.put("jzr_yj", tuple[ 14 ]);
	 	   map.put("yyzzbh", tuple[ 15 ]);
	 	   lists.add(map);
	 	}  
		return lists;
	}

	@Override
	public int updataDcgtInfoIszd(String id, String iszd) {
		
		String sql = "update  tb_dcgt_info  tbinfo set  tbinfo.iszd="+"'"+iszd+"'"+"  where tbinfo.dcgtid = ( select tbdc.td_dcgtid from tb_task_dcgt tbdc  inner join tb_task_dcgt_result tbtares on tbdc.td_id= tbtares.td_id where tbtares.id="+"'"+id+"'"+" ) ";
		
		 int num=0;
		try {
			num = getSession().createSQLQuery(sql).executeUpdate();
		
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		 return num;
	}

	@Override
	public Boolean updateTaskRes(TbTaskDcgtResult taskDcgtResult) {
		    
		try {
			updateObject(taskDcgtResult);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public TbTaskDcgtResult queryTaskDcgtResultById(String id) {
		String sql = "select * from tb_task_dcgt_result where id=:id ";
		Query query =getSession().createSQLQuery(sql).addEntity(TbTaskDcgtResult.class);
		query.setString("id", id);
		return (TbTaskDcgtResult)query.uniqueResult();
	}

}
