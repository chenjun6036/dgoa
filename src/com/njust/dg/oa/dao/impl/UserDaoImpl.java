package com.njust.dg.oa.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.njust.dg.oa.dao.UserDao;
import com.njust.dg.oa.model.User;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	@Override
	public User findUserByUserName(String username) {
		User user = (User) getSession().createQuery("from User u where u.userName=:username").setParameter("username", username)
				.uniqueResult();
		return user;
	}

	@Override
	public List<User> getAllUsers() {
		List<User> users = this.list("select u from User u");
		return users;
	}


	@Override
	public List<User> getUsersByRoleName(String roleName) {
		@SuppressWarnings("unchecked")
		List<User> users = getSession().createQuery("select ur.user from UserRole ur where ur.role.roleName=:roleName").setParameter("roleName", roleName).list();
		return users;
	}

	@Override
	public List<User> getAllNeedAuditUser() {
		List<User> users = this.list("from User u where u.status = 1");
		return users;
	}

}
