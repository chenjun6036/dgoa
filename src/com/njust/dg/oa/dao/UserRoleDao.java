package com.njust.dg.oa.dao;

import com.njust.dg.oa.model.UserRole;

/**
 * 用户角色DAO
 * 
 * @author chenjun
 * 
 */
public interface UserRoleDao extends BaseDao<UserRole> {
	/**
	 * 根据用户id和角色id去的用户的角色
	 * 
	 * @param userId
	 * @param roleId
	 * @return
	 */
	public UserRole getUserRoleByUserIdAndRoleId(int userId, int roleId);
}
