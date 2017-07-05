package com.wonders.main.dao.impl;

import java.util.List;

import com.wonders.main.dao.DcgtInfoDao;
import com.wonders.main.model.TbDcgtInfo;
import com.wonders.util.Page;
import com.wonders.util.PageUtil;


public class DcgtInfoDaoImpl extends BaseDaoImpl implements DcgtInfoDao{


	@Override
	public Boolean saveDcgtInfo(TbDcgtInfo dcgtInfo) {
      
		 try {
			saveObject(dcgtInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Boolean updateDcgt(TbDcgtInfo dcgtInfo) {
		
		 try {
			 updateObject(dcgtInfo);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
	}

	@Override
	public Boolean deleteDcgt(TbDcgtInfo dcgtInfo) {
		
		 try {
			 deleteObject(dcgtInfo);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
	}

	@Override
	public List<TbDcgtInfo> getDcgtByPageNum(int pageNum, int rows,
			String address) {
		String limitHql = (address == ""?" ":" where dz like '%"+address+"%'");
		String hql = "select count(*) from TbDcgtInfo"+limitHql;
		Long count = (Long)getSession().createQuery(hql).uniqueResult();
		Page page = PageUtil.createPage(rows, count.intValue(), pageNum);
		List datas = getPaging(TbDcgtInfo.class, page,limitHql);
		return datas;
	}
	
}
