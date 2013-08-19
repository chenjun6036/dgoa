package com.njust.dg.oa.test;

import java.util.List;

import javax.annotation.Resource;

import org.jbpm.api.ProcessEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.njust.dg.oa.dao.MenuTreeDao;
import com.njust.dg.oa.dao.RoleDao;
import com.njust.dg.oa.dao.RoleMenuDao;
import com.njust.dg.oa.dao.UserDao;
import com.njust.dg.oa.dao.UserRoleDao;
import com.njust.dg.oa.model.MenuTree;
import com.njust.dg.oa.model.Role;
import com.njust.dg.oa.model.RoleMenu;
import com.njust.dg.oa.model.User;
import com.njust.dg.oa.model.UserRole;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class TestUserRole {
	private UserDao userDao;
	private RoleDao roleDao;
	private UserRoleDao userRoleDao;
	private ProcessEngine processEngine;
	@Resource
	private MenuTreeDao menuTreeDao;
	@Resource
	private RoleMenuDao roleMenuDao;
	
//	@Test
//	public void test(){
//		String a = null;
//		a += "123";
//		System.out.println(a);
//	}
	
	@Test
	public void testAddUser() {

		User user = new User();
		user.setRealName("陈俊");
		user.setUserName("chenjun");
		user.setPassword("111111");
		userDao.add(user);
		User user1 = new User();
		user1.setRealName("陈俊1");
		user1.setUserName("chenjun1");
		user1.setPassword("111111");
		userDao.add(user1);
		Role role = new Role();
		role.setRoleName("管理员");
		roleDao.add(role);
		UserRole userRole1 = new UserRole();
		userRole1.setUser(user);
		userRole1.setRole(role);
		userRoleDao.add(userRole1);
		UserRole userRole2 = new UserRole();
		userRole2.setUser(user1);
		userRole2.setRole(role);
		userRoleDao.add(userRole2);
	}

	@Test
	public void testFindRoleByUser() {
		User u = userDao.load(9);
		// System.out.println(u.getRealName());
		List<Role> roles = roleDao.findRolesByUser(u);
		for (Role role : roles) {
			System.out.println(role.getRoleName());
		}
	}

	@Test
	public void testAddRoles() {
		Role role = new Role();

		role.setRoleName("超级管理员");
		roleDao.add(role);

		role.setRoleName("管理员");
		roleDao.add(role);

		role.setRoleName("审核用户");
		roleDao.add(role);

		role.setRoleName("执行用户");
		roleDao.add(role);

		role.setRoleName("普通用户");
		roleDao.add(role);
	}

	@Test
	public void testMenuTreeAdd() {
		MenuTree arg0 = new MenuTree();

		arg0.setText("系统管理");
		arg0.setHrefTarget("#");
		arg0.setMid("001");
		arg0.setLeaf(false);
		arg0.setParent(null);
		menuTreeDao.add(arg0);

		arg0.setText("用户管理");
		arg0.setHrefTarget("systemMgr/UserMgr.jsp");
		arg0.setMid("0011");
		arg0.setLeaf(true);
		arg0.setParent(menuTreeDao.loadMenuByMId("001"));
		menuTreeDao.add(arg0);

		arg0.setText("角色管理");
		arg0.setHrefTarget("systemMgr/RoleMgr.jsp");
		arg0.setMid("0012");
		arg0.setLeaf(true);
		arg0.setParent(menuTreeDao.loadMenuByMId("001"));
		menuTreeDao.add(arg0);

		arg0.setText("审批流转");
		arg0.setHrefTarget("#");
		arg0.setMid("002");
		arg0.setLeaf(false);
		arg0.setParent(null);
		menuTreeDao.add(arg0);

		arg0.setText("我的申请查询");
		arg0.setHrefTarget("#");
		arg0.setMid("0021");
		arg0.setLeaf(true);
		arg0.setParent(menuTreeDao.loadMenuByMId("002"));
		menuTreeDao.add(arg0);

	}

	@Test
	public void testRoleMenuAdd() {
		RoleMenu roleMenu = new RoleMenu();
		roleMenu.setRole(roleDao.load(2));
		roleMenu.setMenuTree(menuTreeDao.load(1));
		roleMenuDao.add(roleMenu);
	}
	
//	public void testGet

	public UserDao getUserDao() {
		return userDao;
	}

	@Resource
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public RoleDao getRoleDao() {
		return roleDao;
	}

	@Resource
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public UserRoleDao getUserRoleDao() {
		return userRoleDao;
	}

	@Resource
	public void setUserRoleDao(UserRoleDao userRoleDao) {
		this.userRoleDao = userRoleDao;
	}

	public ProcessEngine getProcessEngine() {
		return processEngine;
	}

	@Resource
	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}
}
