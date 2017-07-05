package com.wonders.auth.dao;

import java.util.List;

import com.wonders.auth.model.TbAuthResource;


public interface ResourceDao {

	public List<TbAuthResource> getResourceByUserid(String userid);
	
	public List<TbAuthResource> getAll(); 
	
	public TbAuthResource getResourceByKeyword(String keyword);
	
	public void saveTbPtlog(String operationname,String operator,String actionclassname,
			String actionmethodname,String ip,String ssproject,String projectname,String mac);
	
	public TbAuthResource getResourceById(String resid); 
}
