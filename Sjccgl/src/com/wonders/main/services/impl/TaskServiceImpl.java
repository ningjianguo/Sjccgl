package com.wonders.main.services.impl;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.wonders.main.dao.CheckMattersTypeDao;
import com.wonders.main.dao.DcgtInfoDao;
import com.wonders.main.dao.TaskDao;
import com.wonders.main.dao.TaskDcgtDao;
import com.wonders.main.dao.TaskDcgtResultDao;
import com.wonders.main.model.TbDcgtInfo;
import com.wonders.main.model.TbTask;
import com.wonders.main.model.TbTaskDcgt;
import com.wonders.main.model.TbTaskDcgtResult;
import com.wonders.main.services.TaskService;

@Service
public class TaskServiceImpl extends BaseServiceImpl implements TaskService {

	private TaskDao taskDao;
	private TaskDcgtDao taskDcgtDao;
	private CheckMattersTypeDao checkMattersTypeDao;
	private TaskDcgtResultDao taskDcgtResultDao;
	private DcgtInfoDao dcgtInfoDao;
	
	@Override
	public String getTaskByPageNum(int pageNum, int rows) {
		List<TbTask> datas = taskDao.getTaskByPageNum(pageNum, rows);
		int totalCount = queryTotal(TbTask.class);
		JSONObject jsonObj = new JSONObject();
		jsonObj.accumulate("total", totalCount);
		jsonObj.accumulate("rows", datas);
		return jsonObj.toString();
	}
	
	@Override
	public String getTaskByPageNum(int pageNum, int rows, String taskName,
			String taskStatus) {
		List<TbTask> datas = taskDao.getTaskByPageNum(pageNum, rows,taskName,taskStatus);
		int totalCount = datas.size();
		JSONObject jsonObj = new JSONObject();
		jsonObj.accumulate("total", totalCount);
		jsonObj.accumulate("rows", datas);
		return jsonObj.toString();
	}
	
	@Override
	public boolean saveTask(TbTask tbTask) {
		try {
			taskDao.saveObject(tbTask);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String editeTask(TbTask tbTask) {
		 if (taskDao.editeTask(tbTask)) {
			 return "success";
		}
		return null;
	}

	@Override
	public String deleteTask(TbTask tbTask) {
		JSONObject jsonObj = new JSONObject();
		 if ( taskDao.deleteTask(tbTask)) {
			 jsonObj.accumulate("success", "success");
			 return jsonObj.toString();
		}
		return null;
	}

	@Override
	public String saveSearchTask(String[] args,String dcgtid,String iszd) {
		TbTask tbTask = new TbTask();
		TbTaskDcgt taskDcgt = new TbTaskDcgt();
		TbTaskDcgtResult taskDcgtResult = new TbTaskDcgtResult();
		tbTask.setTaskName(args[0]);
		tbTask.setTaskDate(new Date());
		tbTask.setTaskStatus("1");//默认状态为新创建
		tbTask.setTaskCheckTypeName(args[3]);
		Map session = ActionContext.getContext().getSession();
		tbTask.setTaskUser(session.get("userName").toString());
		
		String[] task_check_type_name = {args[3]};
		String checkType = "";
		if(args[3].contains(";")){
			task_check_type_name = args[3].split(";");
		}
		String[] td_check_user = {args[4]};
		if(args[4].contains(";")){
			//定向抽查保存   断点 arg[4]为空 split 报错
			td_check_user = args[4].split(";");
		}
		
		for (String typeName : task_check_type_name) {
			checkType+=checkMattersTypeDao.getMattersIdByMattersType(typeName)+";";
		}
		checkType = checkType.substring(0, checkType.length()-1);
		tbTask.setTaskCheckType(checkType);
		if(saveTask(tbTask)){//保存任务名称到任务表
			taskDcgt.setTdTaskId(tbTask.getTaskId());
			taskDcgt.setTdCheckType(checkType);
			taskDcgt.setTdCheckUser(td_check_user[0]);
			taskDcgt.setTdIsInputResult("1");//默认未录入结果
			if(dcgtid!=null&&iszd!=null){
				//定向抽查判断
				taskDcgt.setTdDcgtid(dcgtid);
			}
			if(td_check_user.length == 2){
				taskDcgt.setTdCheckUser2(td_check_user[1]);
			}
			if(taskDcgtDao.saveTaskDcgt(taskDcgt)){//保存任务主键、检查人和抽查类型主键到任务单详细表
				taskDcgtResult.setTd_id(taskDcgt.getTdId());
				taskDcgtResult.setJcsj_start(args[1]);
				taskDcgtResult.setJcsj_end(args[2]);
				taskDcgtResult.setJcr1(td_check_user[0]);
				TbDcgtInfo tbDcgtInfo = null;
				if(dcgtid!=null&&iszd!=null&&!"".equals(dcgtid)&&!"".equals(iszd)){
					//定向抽查判断
					tbDcgtInfo = (TbDcgtInfo) dcgtInfoDao.getObject(TbDcgtInfo.class, dcgtid);
					taskDcgtResult.setBjcdw(tbDcgtInfo.getGsmc());
					taskDcgtResult.setDz(tbDcgtInfo.getDz());
					taskDcgtResult.setDh(tbDcgtInfo.getLxdh());
					taskDcgtResult.setYyzzbh(tbDcgtInfo.getGszcdjh());
					taskDcgtResult.setFddbr(tbDcgtInfo.getFr());
					tbDcgtInfo.setIszd(iszd);
					dcgtInfoDao.updateDcgt(tbDcgtInfo);
				}
				if(td_check_user.length == 2){
					taskDcgtResult.setJcr2(td_check_user[1]);
				}
				taskDcgtResultDao.saveObject(taskDcgtResult);//保存检查时间、检查人到任务单详细结果表
			}
		}
		JSONObject jsonObj = new JSONObject();
		return jsonObj.accumulate("success", "success").toString();
	}
	@Override
	public String importTask(File file) {
		
		return null;
	}

	@Override
	public String exportTask() {
		
		return null;
	}

	@Override
	public String optionType() {
		
		return null;
	}

	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}
	
	public void setTaskDcgtDao(TaskDcgtDao taskDcgtDao) {
		this.taskDcgtDao = taskDcgtDao;
	}
	
	public void setCheckMattersTypeDao(CheckMattersTypeDao checkMattersTypeDao) {
		this.checkMattersTypeDao = checkMattersTypeDao;
	}

	public void setTaskDcgtResultDao(TaskDcgtResultDao taskDcgtResultDao) {
		this.taskDcgtResultDao = taskDcgtResultDao;
	}

	public void setDcgtInfoDao(DcgtInfoDao dcgtInfoDao) {
		this.dcgtInfoDao = dcgtInfoDao;
	}
}
