package com.njust.dg.oa.dao;

import java.util.List;

import com.njust.dg.oa.model.Role;
import com.njust.dg.oa.model.User;

/**
 * 角色DAO
 * 
 * @author chenjun
 * 
 */
public interface RoleDao extends BaseDao<Role> {
	/**
	 * 查找某用户的角色
	 * 
	 * @param u
	 * @return
	 */
	public List<Role> findRolesByUser(User u);

	/**
	 * 查找所有的角色
	 * 
	 * @return
	 */
	public List<Role> getAllRoles();

	/**
	 * 根据角色名查找角色
	 * 
	 * @param roleName
	 * @return
	 */
	public Role getRoleByName(String roleName);

}
