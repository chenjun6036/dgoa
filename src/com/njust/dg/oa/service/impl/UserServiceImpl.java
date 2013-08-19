package com.njust.dg.oa.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.njust.dg.oa.dao.MenuTreeDao;
import com.njust.dg.oa.dao.RoleDao;
import com.njust.dg.oa.dao.UserDao;
import com.njust.dg.oa.dao.UserRoleDao;
import com.njust.dg.oa.model.MenuTree;
import com.njust.dg.oa.model.Role;
import com.njust.dg.oa.model.User;
import com.njust.dg.oa.model.UserRole;
import com.njust.dg.oa.service.UserService;
import com.njust.dg.oa.vo.LoginFormBean;
import com.njust.dg.oa.vo.UserManagerFormBean;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Resource
	private UserDao userDao;
	@Resource
	private RoleDao roleDao;
	@Resource
	private MenuTreeDao menuTreeDao;
	@Resource
	private UserRoleDao userRoleDao;

	@Override
	public User login(LoginFormBean loginFormBean) {
		User user = userDao.findUserByUserName(loginFormBean.getUsername());
		if (user == null) {
			loginFormBean.setSuccess(false);
			loginFormBean.setStatus("该用户名不存在！");
			return null;
		} else if (user.getStatus() == 1) {
			loginFormBean.setSuccess(false);
			loginFormBean.setStatus("您的注册还在审核中，请耐心等待管理员的审核！");
			return null;
		} else if (user.getStatus() == 2) {
			loginFormBean.setSuccess(false);
			loginFormBean.setStatus("对不起，您的注册未审核成功！详情请联系管理员！");
			return null;
		} else if (!user.getPassword().equals(loginFormBean.getPassword())) {
			loginFormBean.setSuccess(false);
			loginFormBean.setStatus("密码错误！");
			return null;
		}
		loginFormBean.setSuccess(true);
		return user;
	}

	@Override
	public void register(LoginFormBean loginFormBean) {
		User user = userDao.findUserByUserName(loginFormBean.getUsername());
		if (user != null) {
			loginFormBean.setSuccess(false);
			loginFormBean.setStatus("对不起，该用户名已存在，请重新输入！");
			return;
		} else {
			user = new User();
			user.setUserName(loginFormBean.getUsername());
			user.setPassword(loginFormBean.getPassword());
			user.setRealName(loginFormBean.getRealName());
			user.setStatus(1);
			userDao.add(user);
			loginFormBean.setSuccess(true);
			loginFormBean.setStatus("注册成功！请等待管理员的审核！");
		}
	}

	@Override
	public List<MenuTree> getMenuTreeByUser(User u) {
		List<Role> roles = roleDao.findRolesByUser(u);
		List<MenuTree> menuTrees = menuTreeDao.getMenusInRoles(roles);
		List<MenuTree> oneLevelMenus = new ArrayList<MenuTree>();
		// for (Role role : roles) {
		// System.out.println(role.getRoleName());
		// }
		for (MenuTree menuTree : menuTrees) {
			if (menuTree.getLeaf() == false && !oneLevelMenus.contains(menuTree)) {
				oneLevelMenus.add(menuTree);
			}
		}
		//
		// for (MenuTree menuTree : menuTrees) {
		// System.out.println(menuTree.getText());
		// }
		// System.out.println("*******************************************");
		// System.out.println("前：");
		// for (MenuTree menuTree : oneLevelMenus) {
		// System.out.println(menuTree.getText());
		// for (MenuTree childMenu : menuTree.getChildren()) {
		// System.out.println("----" + childMenu.getText());
		// }
		// }
		// System.out.println("*******************************************");
		for (MenuTree menuTree : oneLevelMenus) {
			// for (MenuTree childMenu : menuTree.getChildren()) {
			// System.out.println("****" + childMenu.getText());
			// }
			// for (MenuTree childMenu : menuTree.getChildren()) {
			// System.out.println("++++" + childMenu.getText());
			// if (!menuTrees.contains(childMenu)) {
			// // menuTree.getChildren().remove(childMenu);
			// childMenu.setText("aaa");
			// }
			// }
			for (int i = 0; i < menuTree.getChildren().size(); i++) {
				if (!menuTrees.contains(menuTree.getChildren().get(i))) {
					menuTree.getChildren().remove(menuTree.getChildren().get(i));
					i--;
				}
			}
		}
		// System.out.println("*******************************************");
		// System.out.println("后：");
		// for (MenuTree menuTree : oneLevelMenus) {
		// System.out.println(menuTree.getText());
		// for (MenuTree childMenu : menuTree.getChildren()) {
		// System.out.println("----" + childMenu.getText());
		// }
		// }
		return oneLevelMenus;
	}

	@Override
	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}

	@Override
	public void updateUser(UserManagerFormBean userManagerFormBean) {
		User user = userManagerFormBean.getUser();
		User u = userDao.load(user.getUserId());
		if (!u.getUserName().equals(user.getUserName())) {
			User u1 = userDao.findUserByUserName(user.getUserName());
			if (u1 != null) {
				userManagerFormBean.setSuccess(false);
				userManagerFormBean.setStatus("对不起，该用户名已存在，请重新输入！");
				return;
			}
		}
		u.setEmail(user.getEmail());
		u.setRealName(user.getRealName());
		u.setUserName(user.getUserName());
		userDao.update(u);
		userManagerFormBean.setSuccess(true);
		userManagerFormBean.setStatus("修改成功！");
	}

	@Override
	public void addUser(UserManagerFormBean userManagerFormBean) {
		User user = userDao.findUserByUserName(userManagerFormBean.getUser().getUserName());
		if (user != null) {
			userManagerFormBean.setSuccess(false);
			userManagerFormBean.setStatus("对不起，该用户名已存在，请重新输入！");
			return;
		} else {
			userManagerFormBean.getUser().setStatus(0);
			userManagerFormBean.getUser().setPassword("111111");
			userDao.add(userManagerFormBean.getUser());
			Role role = roleDao.getRoleByName("普通用户");
			UserRole userRole = new UserRole(userManagerFormBean.getUser(), role);
			userRoleDao.add(userRole);
			userManagerFormBean.setSuccess(true);
			userManagerFormBean.setStatus("添加成功！");
		}
	}

	@Override
	public void deleteUser(UserManagerFormBean userManagerFormBean, Map<String, Object> session) {
		if (userManagerFormBean.getUser().getUserId() == ((User) session.get("user")).getUserId()) {
			userManagerFormBean.setSuccess(false);
			userManagerFormBean.setStatus("不能删除本人！");
			return;
		}
		userDao.delete(userManagerFormBean.getUser().getUserId());
		userManagerFormBean.setSuccess(true);
		userManagerFormBean.setStatus("删除成功！");
	}

	@Override
	public void addUserRole(UserManagerFormBean userManagerFormBean) {
		User user = userDao.load(userManagerFormBean.getUser().getUserId());
		List<Role> roles = roleDao.findRolesByUser(user);
		Role role = roleDao.load(userManagerFormBean.getRoleId());
		if (roles.contains(role)) {
			userManagerFormBean.setSuccess(false);
			userManagerFormBean.setStatus("此用户已经有该角色，不能重复添加！");
			return;
		}
		UserRole userRole = new UserRole(user, role);
		userRoleDao.add(userRole);
		userManagerFormBean.setSuccess(true);
		userManagerFormBean.setStatus("添加成功！");
	}

	@Override
	public void deleteUserRole(UserManagerFormBean userManagerFormBean) {
		UserRole userRole = userRoleDao.getUserRoleByUserIdAndRoleId(userManagerFormBean.getUser().getUserId(),
				userManagerFormBean.getRoleId());
		userRoleDao.delete(userRole.getId());
		userManagerFormBean.setSuccess(true);
		userManagerFormBean.setStatus("删除成功！");
	}

	@Override
	public List<User> getAllAudits() {
		// TODO Auto-generated method stub
		return userDao.getUsersByRoleName("审核用户");
	}

	@Override
	public List<User> getAllExecutor() {
		// TODO Auto-generated method stub
		return userDao.getUsersByRoleName("执行用户");
	}

	@Override
	public List<User> getAllAdmin() {
		// TODO Auto-generated method stub
		return userDao.getUsersByRoleName("管理员");
	}

	@Override
	public List<User> getAllSuperAdmin() {
		// TODO Auto-generated method stub
		return userDao.getUsersByRoleName("超级管理员");
	}

	@Override
	public User load(int id) {
		// TODO Auto-generated method stub
		return userDao.load(id);
	}

	@Override
	public List<User> getAllNeedAuditUsers() {
		// TODO Auto-generated method stub
		return userDao.getAllNeedAuditUser();
	}

	@Override
	public void auditUser(UserManagerFormBean userManagerFormBean) {
		// TODO Auto-generated method stub
		User u = userDao.load(userManagerFormBean.getUser().getUserId());
		if(userManagerFormBean.getIsApproval()){
			u.setStatus(0);
		}else{
			u.setStatus(2);
		}
		userDao.update(u);
		userManagerFormBean.setSuccess(true);
		userManagerFormBean.setStatus("审核成功！");
	}
}
