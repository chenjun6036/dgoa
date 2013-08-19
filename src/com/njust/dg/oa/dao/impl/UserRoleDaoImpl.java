package com.njust.dg.oa.dao.impl;

import org.springframework.stereotype.Repository;

import com.njust.dg.oa.dao.UserRoleDao;
import com.njust.dg.oa.model.UserRole;

@Repository("userRoleDao")
public class UserRoleDaoImpl extends BaseDaoImpl<UserRole> implements UserRoleDao {

	@Override
	public UserRole getUserRoleByUserIdAndRoleId(int userId, int roleId) {
		return (UserRole) this.getSession().createQuery("select ur from UserRole ur where role.roleId=:roleId and user.userId=:userId")
				.setParameter("roleId", roleId).setParameter("userId", userId).uniqueResult();
	}

}
