package com.wonders.auth.service;

import java.util.List;

import com.wonders.auth.model.TbAuthResource;


public interface ResourceService {

	/**
	 * 获得指定父节点下的子节点列表
	 * */
	public List<TbAuthResource> getResourceByUserid(String userid);
	
	public TbAuthResource getResourceByKeyword(String keyword);
	
	public List<TbAuthResource> getAll();
	
	public void saveTbPtlog(String operationname,String operator,String actionclassname,
			String actionmethodname,String ip,String ssproject,String projectname,String mac);
	
	public TbAuthResource getResourceById(String resid) ;
}
