package com.njust.dg.oa.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.njust.dg.oa.dao.MenuTreeDao;
import com.njust.dg.oa.model.MenuTree;
import com.njust.dg.oa.model.Role;

@Repository("menuTreeDao")
public class MenuTreeDaoImpl extends BaseDaoImpl<MenuTree> implements MenuTreeDao {

	@Override
	public List<MenuTree> getOneLevelMenu() {
		String hql = "from MenuTree where leaf = false";
		return this.list(hql);
	}

	@Override
	public List<MenuTree> getMenusInRoles(List<Role> roles) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<MenuTree> menuTrees = this.getSession().createQuery("select RM.menuTree from RoleMenu RM where RM.role in (:roles) order by RM.menuTree.mid ASC")
				.setParameterList("roles", roles).list();
//		for (MenuTree menuTree : menuTrees) {
//			System.out.println(menuTree.getMid());
//		}
		return menuTrees;
	}

	@Override
	public MenuTree loadMenuByMId(String mid) {
		// TODO Auto-generated method stub
		String hql = "from MenuTree where mid =" + mid;
		return (MenuTree) this.getSession().createQuery(hql).uniqueResult();
	}
}
