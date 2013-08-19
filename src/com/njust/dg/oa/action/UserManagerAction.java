package com.njust.dg.oa.action;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.njust.dg.oa.model.User;
import com.njust.dg.oa.service.UserService;
import com.njust.dg.oa.vo.UserManagerFormBean;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
@Controller("userManagerAction")
@Scope("prototype")
public class UserManagerAction extends ActionSupport implements ModelDriven<UserManagerFormBean>, SessionAware {
	@Resource
	private UserService userService;
	private UserManagerFormBean userManagerFormBean = new UserManagerFormBean();
	private Map<String, Object> session;

	public UserManagerFormBean getUserManagerFormBean() {
		return userManagerFormBean;
	}

	public void setUserManagerFormBean(UserManagerFormBean userManagerFormBean) {
		this.userManagerFormBean = userManagerFormBean;
	}

	public String getAllUsers() {
		userManagerFormBean.setUsers(userService.getAllUsers());
		// 不把password传到前台，确保安全性
		for (User u : userManagerFormBean.getUsers()) {
			u.setPassword("");
		}
		return "getAllUsers";
	}
	public String getAllNeedAuditUsers() {
		userManagerFormBean.setUsers(userService.getAllNeedAuditUsers());
		// 不把password传到前台，确保安全性
		for (User u : userManagerFormBean.getUsers()) {
			u.setPassword("");
		}
		return "getAllUsers";
	}
	
	public String getAllAudits(){
		userManagerFormBean.setUsers(userService.getAllAudits());
		// 不把password传到前台，确保安全性
		for (User u : userManagerFormBean.getUsers()) {
			u.setPassword("");
		}
		return "getAllUsers";
	}
	
	public String getAllExecutor(){
		userManagerFormBean.setUsers(userService.getAllExecutor());
		// 不把password传到前台，确保安全性
		for (User u : userManagerFormBean.getUsers()) {
			u.setPassword("");
		}
		return "getAllUsers";
	}
	public String getAllAdmin(){
		userManagerFormBean.setUsers(userService.getAllAdmin());
		// 不把password传到前台，确保安全性
		for (User u : userManagerFormBean.getUsers()) {
			u.setPassword("");
		}
		return "getAllUsers";
	}
	public String getAllSuperAdmin(){
		userManagerFormBean.setUsers(userService.getAllSuperAdmin());
		// 不把password传到前台，确保安全性
		for (User u : userManagerFormBean.getUsers()) {
			u.setPassword("");
		}
		return "getAllUsers";
	}

	// public String exportUsers(){
	// List<User> users = userService.getAllUsers();
	//
	// return null;
	// }

	public String updateUser() {
		userService.updateUser(userManagerFormBean);
		return "operaUser";
	}
	public String auditUser() {
		userService.auditUser(userManagerFormBean);
		return "operaUser";
	}

	public String addUser() {
		userService.addUser(userManagerFormBean);
		return "operaUser";
	}

	public String deleteUser() {
		userService.deleteUser(userManagerFormBean, session);
		return "operaUser";
	}

	public String addUserRole() {
		userService.addUserRole(userManagerFormBean);
		return "operaUser";
	}

	public String deleteUserRole() {
		userService.deleteUserRole(userManagerFormBean);
		return "operaUser";
	}

	@Override
	public UserManagerFormBean getModel() {
		return userManagerFormBean;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
