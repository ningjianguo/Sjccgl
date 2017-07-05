package com.wonders.main.dao;

import java.util.List;
import com.wonders.main.model.Resource;

public interface TbAuthResourceDao {

	public List<Resource> findAll();
	
	public List<Resource> findAllByUserid(String userid);
	
	public void saveResource(Resource r);
	
	public void updateResource(Resource r);

	public void deleteResource(Resource r);
	
	public void deleteUserResourceByUser(String userid);

	public void saveUserResourceByUser(String sql);
	
	public boolean isUsername(String username);
	
}
