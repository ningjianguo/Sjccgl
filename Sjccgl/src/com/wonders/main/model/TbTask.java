package com.wonders.main.model;

import java.util.Date;

public class TbTask implements java.io.Serializable {
	
	private String taskId;
	private String taskName;
	private Date taskDate;
	private String taskUser;
	private Date taskCheckDate;
	private String taskCheckUser;
	private String taskStatus;
	private String taskCheckType;
	private String taskCheckTypeName;
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Date getTaskDate() {
		return taskDate;
	}
	public void setTaskDate(Date taskDate) {
		this.taskDate = taskDate;
	}
	public String getTaskUser() {
		return taskUser;
	}
	public void setTaskUser(String taskUser) {
		this.taskUser = taskUser;
	}
	public Date getTaskCheckDate() {
		return taskCheckDate;
	}
	public void setTaskCheckDate(Date taskCheckDate) {
		this.taskCheckDate = taskCheckDate;
	}
	public String getTaskCheckUser() {
		return taskCheckUser;
	}
	public void setTaskCheckUser(String taskCheckUser) {
		this.taskCheckUser = taskCheckUser;
	}
	public String getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	public String getTaskCheckType() {
		return taskCheckType;
	}
	public void setTaskCheckType(String taskCheckType) {
		this.taskCheckType = taskCheckType;
	}
	public String getTaskCheckTypeName() {
		return taskCheckTypeName;
	}
	public void setTaskCheckTypeName(String taskCheckTypeName) {
		this.taskCheckTypeName = taskCheckTypeName;
	}
}