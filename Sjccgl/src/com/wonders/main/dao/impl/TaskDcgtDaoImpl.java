package com.wonders.main.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import com.wonders.main.dao.TaskDcgtDao;
import com.wonders.main.model.TbTaskDcgt;

public class TaskDcgtDaoImpl extends BaseDaoImpl implements TaskDcgtDao {
	
	@Override
	public List<Map<String, Object>> queryTackDcgtNum(String checkId,
			String startTime, String endTime) {
		
		String sql="SELECT C.CHECK_ID, C.CHECK_MATTERS, COUNT(B.DCGTID) AS COUNT, (SELECT COUNT(*) FROM TB_TASK_DCGT RES  WHERE RES.TD_CHECK_TYPE = C.CHECK_ID AND RES.TD_ISINPUTRESULT = '0') AS RES_COUNT  FROM TB_TASK_DCGT A, TB_DCGT_INFO B, TB_CHECK_MATTERS_TYPE C, TB_TASK D  WHERE A.TD_DCGTID = B.DCGTID AND B.HY = C.CHECK_ID AND A.TD_TASK_ID = D.TASK_ID AND INSTR(:str,C.CHECK_ID ) > 0 AND (D.TASK_DATE >= to_date(:str1,'yyyy-MM-dd') OR to_date(:str1,'yyyy-MM-dd') IS NULL ) AND (D.TASK_DATE <= to_date(:str2,'yyyy-MM-dd') OR to_date(:str2,'yyyy-MM-dd') IS NULL ) GROUP BY C.CHECK_ID,C.CHECK_MATTERS ";
		
		Query query =getSession().createSQLQuery(sql);
		query.setString("str", checkId);
		query.setString("str1", startTime);
		query.setString("str2", endTime);
		
	 	List<Object> result = query.list();
	 	
	 	Iterator it = result.iterator(); 
	 	
	 	List<Map<String, Object>> lists = new ArrayList<Map<String,Object>>();
	 	Map<String, Object> map = null;
	 	
	 	while (it.hasNext()) {      
	 	   Object[] tuple = (Object[]) it.next();
	 	   map= new HashMap<String, Object>();
	 	   map.put("iszd", tuple[ 0 ]);
	 	   map.put("matters", tuple[ 1 ]);
	 	   map.put("dcgtcount", tuple[ 2 ]);
	 	   map.put("rescount", tuple[ 3 ]);
	 	   lists.add(map);
	 	}  
	
		return lists;
	}

	@Override
	public Boolean saveTaskDcgt(TbTaskDcgt tbTaskDcgt) {
		try {
			saveObject(tbTaskDcgt);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public String getNewTaskDcgtId() {
		String sql = "select td_id from (select rownum rn ,t.td_id from tb_task_dcgt t) " +
				"where rn=(select count(*) from tb_task_dcgt)";
		String newId = (String)getSession().createSQLQuery(sql).uniqueResult();
		return newId;
	}
}
