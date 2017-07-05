package com.wonders.main.services.impl;

import java.util.List;
import com.wonders.main.dao.BaseDao;
import com.wonders.main.dao.TbAuthResourceDao;
import com.wonders.main.model.Resource;
import com.wonders.main.model.User;
import com.wonders.main.services.TbAuthResourceService;

public class TbAuthResourceServiceImpl implements TbAuthResourceService{

	private TbAuthResourceDao tbAuthResourceDao;
	private BaseDao baseDao;
	private StringBuffer sb = new StringBuffer("");
	
	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public TbAuthResourceDao getTbAuthResourceDao() {
		return tbAuthResourceDao;
	}

	public void setTbAuthResourceDao(TbAuthResourceDao tbAuthResourceDao) {
		this.tbAuthResourceDao = tbAuthResourceDao;
	}

	public List<Resource> findAll() {
		List<Resource> list = this.getTbAuthResourceDao().findAll();
		return list;
	}
	
	public List<Resource> findAllByUserid(String userid){
		return this.getTbAuthResourceDao().findAllByUserid(userid);
	}
	
	public void saveResource(Resource r){
		this.getTbAuthResourceDao().saveResource(r);
	}
	
	public void updateResource(Resource r){
		this.getTbAuthResourceDao().updateResource(r);
	}
	
	public void deleteResource(Resource r){
		this.getTbAuthResourceDao().deleteResource(r);
	}
	
	public void deleteUserResourceByUser(String userid) {
		this.getTbAuthResourceDao().deleteUserResourceByUser(userid);
	}
	
	public void saveUserResourceByUser(String menu,String userid) {
		String[] array = menu.split(",");
		String sql = "INSERT INTO TB_AUTH_USER_RESOURCE(USERID,RESID) ";
		for (int i = 0; i < array.length; i++) {
			if(i > 0){
				sql += " UNION ";
			}
			sql += "SELECT '"+userid+"','"+array[i]+"' FROM DUAL";
		}
		this.getTbAuthResourceDao().deleteUserResourceByUser(userid);
		if(!"".equals(menu)){
			this.getTbAuthResourceDao().saveUserResourceByUser(sql);
		}
	}
	
	public String parseReourceToJson(List<Resource> list){
		String str = "";
		str += "[{";
		str += "	\"checked\":true,";
		str += "	\"id\":\"0\",";
		str += "	\"text\":\"系统菜单结构\",";
		str += "	\"children\":[";
		
		for (int i = 0; i < list.size(); i++) {
			Resource r = list.get(i);
			if(r.getPresid()!=null && r.getPresid().equals("0")){
				str += "		{";
				if(r.getTemp()!=null){
				str += "			\"checked\":true,";
				}
				str += "			\"id\":\""+r.getResid()+"\",";
				str += "			\"text\":\""+r.getResname()+"\",";
				str += "			\"children\":[";
				for (int j = 0; j < list.size(); j++) {
					Resource rchild = list.get(j);
					if(rchild.getPresid()!=null && rchild.getPresid().equals(r.getResid())){
						str += " 				{";
						if(rchild.getTemp()!=null){
						str += "					\"checked\":true,";
						}
						str += "					\"id\":\""+rchild.getResid()+"\",";
						str += "					\"text\":\""+rchild.getResname()+"\",";
						
						
						str = str.substring(0,str.lastIndexOf(","));
						
						str += "				}";
						str += "				,";
					}
				}
				
				str = str.substring(0,str.lastIndexOf(","));
				
				str += "			]";
				str += "		}";
				str += "		,";
			}
		}
		
		str = str.substring(0,str.lastIndexOf(","));
				
		str += "	]";
		str += "}]";
		return str;
	}
	
	public void updateUser(User user){
		User object = (User)this.getBaseDao().getObject(User.class, user.getUserid());
		object.setUsername(user.getUsername());
		object.setLoginname(user.getLoginname());
		object.setComments(user.getComments());
		object.setMobilephone(user.getMobilephone());
		object.setEmail(user.getEmail());
		object.setZfrybh(user.getZfrybh());
		this.getBaseDao().updateObject(object);
	}

	@Override
	public boolean isUsername(String username) {
		// TODO Auto-generated method stub
		return this.tbAuthResourceDao.isUsername(username);
	}
}
