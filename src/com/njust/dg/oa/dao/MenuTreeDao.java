package com.njust.dg.oa.dao;

import java.util.List;

import com.njust.dg.oa.model.MenuTree;
import com.njust.dg.oa.model.Role;

public interface MenuTreeDao extends BaseDao<MenuTree> {
	/**
	 * 取出MenuTree中所有的已经标签
	 * 
	 * @return
	 */
	public List<MenuTree> getOneLevelMenu();

	/**
	 * 取出若干角色对应的标签
	 * 
	 * @return
	 */
	public List<MenuTree> getMenusInRoles(List<Role> roles);

	/**
	 * 通过mid查找Menu
	 * 
	 * @param mid
	 * @return
	 */
	public MenuTree loadMenuByMId(String mid);
}
