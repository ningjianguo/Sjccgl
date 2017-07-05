package com.wonders.auth.model;

import java.util.HashSet;
import java.util.Set;

/**
 * 菜单资源(TB_AUTH_RESOURCE)
 * */
public class TbAuthResource implements java.io.Serializable {
	
	private String resid;
	private String presid;
	private String resname;
	private String url;
	private String comments;
	private Long resLevel;
	private String treelayer;
	
	public String getResid() {
		return resid;
	}
	public void setResid(String resid) {
		this.resid = resid;
	}
	public String getPresid() {
		return presid;
	}
	public void setPresid(String presid) {
		this.presid = presid;
	}
	public String getResname() {
		return resname;
	}
	public void setResname(String resname) {
		this.resname = resname;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Long getResLevel() {
		return resLevel;
	}
	public void setResLevel(Long resLevel) {
		this.resLevel = resLevel;
	}
	public String getTreelayer() {
		return treelayer;
	}
	public void setTreelayer(String treelayer) {
		this.treelayer = treelayer;
	}
}