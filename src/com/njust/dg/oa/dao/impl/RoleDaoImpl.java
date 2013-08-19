package com.njust.dg.oa.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.njust.dg.oa.dao.RoleDao;
import com.njust.dg.oa.model.Role;
import com.njust.dg.oa.model.User;

@Repository("roleDao")
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao {

	@Override
	public List<Role> findRolesByUser(User u) {
		// TODO Auto-generated method stub
		return this.list("select u.role from UserRole u where u.user=?", u);
	}

	@Override
	public List<Role> getAllRoles() {
		return this.list("select r from Role r");
	}

	@Override
	public Role getRoleByName(String roleName) {
		return (Role) this.getSession().createQuery("select r from Role r where roleName=:roleName").setParameter("roleName", roleName)
				.uniqueResult();
	}

}
