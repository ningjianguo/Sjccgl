package com.wonders.main.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import com.wonders.main.dao.CheckMattersTypeDao;
import com.wonders.main.model.TbCheckMattersType;
import com.wonders.util.Page;
import com.wonders.util.PageUtil;

public class CheckMattersTypeDaoImpl extends BaseDaoImpl implements
		CheckMattersTypeDao {

	@Override
	public List<TbCheckMattersType> getMattersTypeByPageNum(int pageNum,
			int rows) {
		int totalCount = queryTotal(TbCheckMattersType.class);
		Page page = PageUtil.createPage(rows, totalCount, pageNum);
		List datas = getPaging(TbCheckMattersType.class, page);
		return datas;
	}

	@Override
	public Boolean saveMattersType(TbCheckMattersType tbCheckMattersType) {
		try {
			int xuhao = getMaxCheckXuhao();
			tbCheckMattersType.setCheckXuhao(String.valueOf(xuhao + 1));
			saveObject(tbCheckMattersType);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public int getMaxCheckXuhao() {
		String sql = "SELECT NVL(MAX(TO_NUMBER(CHECK_XUHAO)),0) FROM TB_CHECK_MATTERS_TYPE";
		BigDecimal maxValue = null;
		try {
			maxValue = (BigDecimal) getSession().createSQLQuery(sql)
					.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maxValue.intValue();
	}

	@Override
	public Boolean editeMattersType(TbCheckMattersType tbCheckMattersType) {
		try {
			updateObject(tbCheckMattersType);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Boolean deleteMattersType(TbCheckMattersType tbCheckMattersType) {
		try {
			deleteObject(tbCheckMattersType);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public String getCheckMattersByCheckId(String checkId) {
		String hql = "select checkMatters from TbCheckMattersType where checkId=:id";
		String checkMatters = (String) getSession().createQuery(hql).setString("id", checkId).uniqueResult();
		return checkMatters;
	}

	@Override
	public String getMattersIdByMattersType(String mattersType) {
		String sql = "select check_id from tb_check_matters_type where check_matters=:matter";
		String checkId = (String) getSession().createSQLQuery(sql).setString("matter", mattersType).uniqueResult();
		return checkId;
	}

}
