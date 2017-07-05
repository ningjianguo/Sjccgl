package com.wonders.auth.service.impl;

import java.util.List;
import com.wonders.auth.dao.ResourceDao;
import com.wonders.auth.model.TbAuthResource;
import com.wonders.auth.service.ResourceService;


public class ResourceServiceImpl implements ResourceService {
	
	private ResourceDao resourceDao;
	
	public void setResourceDao(ResourceDao resourceDao) {
		this.resourceDao = resourceDao;
	}

	public List<TbAuthResource> getResourceByUserid(String userid){
		return this.resourceDao.getResourceByUserid(userid);
	}
	
	public TbAuthResource getResourceByKeyword(String keyword){
		return this.resourceDao.getResourceByKeyword(keyword);
	}
	
	public List<TbAuthResource> getAll(){
		return this.resourceDao.getAll();
	}

	public void saveTbPtlog(String operationname, String operator,
			String actionclassname, String actionmethodname, String ip,
			String ssproject, String projectname,String mac) {
		this.resourceDao.saveTbPtlog(operationname, operator, actionclassname, actionmethodname, ip, ssproject, projectname,mac);
	}
	
	public TbAuthResource getResourceById(String resid) {
		return this.resourceDao.getResourceById(resid);
	}
}
