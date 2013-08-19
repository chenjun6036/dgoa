package com.njust.dg.oa.service;

import java.util.List;

import com.njust.dg.oa.model.Role;
import com.njust.dg.oa.vo.RoleManagerFormBean;

public interface RoleService {
	/**
	 * 列出所有的用户
	 * 
	 * @return
	 */
	List<Role> getAllRoles();

	/**
	 * 更新角色信息
	 * 
	 * @param roleManagerFormBean
	 */
	void updateRole(RoleManagerFormBean roleManagerFormBean);

	/**
	 * 添加角色
	 * 
	 * @param roleManagerFormBean
	 */
	void addRole(RoleManagerFormBean roleManagerFormBean);

	/**
	 * 删除角色
	 * 
	 * @param roleManagerFormBean
	 */
	void deleteRole(RoleManagerFormBean roleManagerFormBean);

	/**
	 * 获取特定用户的角色列表
	 * 
	 * @param userId
	 * @return
	 */
	List<Role> getRolesByUser(int userId);

}
