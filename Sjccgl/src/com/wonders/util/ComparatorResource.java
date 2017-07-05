package com.wonders.util;

import java.util.Comparator;

import com.wonders.auth.model.TbAuthResource;


/**
 * 对菜单资源Set集合排序
 * */
public class ComparatorResource implements Comparator<TbAuthResource> {

	public int compare(TbAuthResource tar1, TbAuthResource tar2) {
		return tar1.getTreelayer().compareTo(tar2.getTreelayer());
	}
}
