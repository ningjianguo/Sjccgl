package com.wonders.main.services;

import java.util.List;
import com.wonders.main.model.Resource;
import com.wonders.main.model.User;

public interface TbAuthResourceService {

	public List<Resource> findAll();
	
	public List<Resource> findAllByUserid(String userid);
	
	public void saveResource(Resource r);
	
	public void updateResource(Resource r); 
	
	public void deleteResource(Resource r);
	
	public void deleteUserResourceByUser(String userid);
	
	public void saveUserResourceByUser(String menu,String userid);
	
	public String parseReourceToJson(List<Resource> list);
	
	public void updateUser(User user);
	
	public boolean isUsername(String username);
	
}
