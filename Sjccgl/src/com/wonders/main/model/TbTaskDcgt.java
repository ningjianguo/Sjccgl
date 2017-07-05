package com.wonders.main.model;

public class TbTaskDcgt implements java.io.Serializable {
	
	private String tdId;
	private String tdTaskId;
	private String tdCheckType;
	private String tdDcgtid;
	private String tdIshg;
	private String tdRemark;
	private String tdIsInputResult;//是否已经录入结果；0：是
	private String tdCheckUser;
	private String tdCheckUser2;
	
	public String getTdCheckUser2() {
		return tdCheckUser2;
	}
	public void setTdCheckUser2(String tdCheckUser2) {
		this.tdCheckUser2 = tdCheckUser2;
	}
	public String getTdId() {
		return tdId;
	}
	public void setTdId(String tdId) {
		this.tdId = tdId;
	}
	public String getTdTaskId() {
		return tdTaskId;
	}
	public void setTdTaskId(String tdTaskId) {
		this.tdTaskId = tdTaskId;
	}
	public String getTdCheckType() {
		return tdCheckType;
	}
	public void setTdCheckType(String tdCheckType) {
		this.tdCheckType = tdCheckType;
	}
	public String getTdDcgtid() {
		return tdDcgtid;
	}
	public void setTdDcgtid(String tdDcgtid) {
		this.tdDcgtid = tdDcgtid;
	}
	public String getTdIshg() {
		return tdIshg;
	}
	public void setTdIshg(String tdIshg) {
		this.tdIshg = tdIshg;
	}
	public String getTdRemark() {
		return tdRemark;
	}
	public void setTdRemark(String tdRemark) {
		this.tdRemark = tdRemark;
	}
	public String getTdCheckUser() {
		return tdCheckUser;
	}
	public void setTdCheckUser(String tdCheckUser) {
		this.tdCheckUser = tdCheckUser;
	}
	public String getTdIsInputResult() {
		return tdIsInputResult;
	}
	public void setTdIsInputResult(String tdIsInputResult) {
		this.tdIsInputResult = tdIsInputResult;
	}
	
}