package com.njust.dg.oa.service;

import java.util.List;
import java.util.Map;

import com.njust.dg.oa.model.MenuTree;
import com.njust.dg.oa.model.User;
import com.njust.dg.oa.vo.LoginFormBean;
import com.njust.dg.oa.vo.UserManagerFormBean;

public interface UserService {
	/**
	 * 登录
	 * 
	 * @param loginFormBean
	 * @return
	 */
	public User login(LoginFormBean loginFormBean);

	/**
	 * 注册
	 * 
	 * @param loginFormBean
	 */
	public void register(LoginFormBean loginFormBean);

	/**
	 * 根据用户查找对应的功能树
	 * 
	 * @param u
	 * @return
	 */
	public List<MenuTree> getMenuTreeByUser(User u);

	/**
	 * 获取所有的用户
	 * 
	 * @return
	 */
	public List<User> getAllUsers();

	/**
	 * 修改用户(除密码和审核和状态)
	 * 
	 * @param userManagerFormBean
	 */
	public void updateUser(UserManagerFormBean userManagerFormBean);

	/**
	 * 添加用户
	 * 
	 * @param userManagerFormBean
	 */
	public void addUser(UserManagerFormBean userManagerFormBean);

	/**
	 * 删除用户
	 * 
	 * @param userManagerFormBean
	 * @param session
	 */
	public void deleteUser(UserManagerFormBean userManagerFormBean, Map<String, Object> session);

	/**
	 * 添加用户的角色
	 * 
	 * @param userManagerFormBean
	 */
	public void addUserRole(UserManagerFormBean userManagerFormBean);

	/**
	 * 删除用户的角色
	 * 
	 * @param userManagerFormBean
	 */
	public void deleteUserRole(UserManagerFormBean userManagerFormBean);
	
	/**
	 * 获得所有审核用户
	 * @return
	 */
	public List<User> getAllAudits();
	
	/**
	 * 获取所有执行用户
	 * @return
	 */
	public List<User> getAllExecutor();
	/**
	 * 获取所有管理员
	 * @return
	 */
	public List<User> getAllAdmin();
	/**
	 * 获取所有超级管理员
	 * @return
	 */
	public List<User> getAllSuperAdmin();
	
	/**
	 * 根据用户id获取用户
	 * @param id
	 * @return
	 */
	public User load(int id);

	public List<User> getAllNeedAuditUsers();

	public void auditUser(UserManagerFormBean userManagerFormBean);
}
