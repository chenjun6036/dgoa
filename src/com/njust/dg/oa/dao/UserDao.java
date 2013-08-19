package com.njust.dg.oa.dao;

import java.util.List;

import com.njust.dg.oa.model.User;

/**
 * 用户DAO
 * 
 * @author chenjun
 * 
 */
public interface UserDao extends BaseDao<User> {

	/**
	 * 根据用户名查找用户
	 * 
	 * @param userName
	 * @return
	 */
	public User findUserByUserName(String userame);

	public List<User> getAllUsers();
	
	/**
	 * 根据角色名查询用户
	 * @return
	 */
	public List<User> getUsersByRoleName(String roleName);

	public List<User> getAllNeedAuditUser();
}
