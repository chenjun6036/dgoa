package com.njust.dg.oa.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.njust.dg.oa.model.MenuTree;
import com.njust.dg.oa.model.User;
import com.njust.dg.oa.service.UserService;
import com.njust.dg.oa.vo.LoginFormBean;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
@Controller("loginAction")
@Scope("prototype")
public class LoginAction extends ActionSupport implements ModelDriven<LoginFormBean>, SessionAware {
	private Map<String, Object> session;
	private LoginFormBean loginFormBean = new LoginFormBean();
	private UserService userService;
	private List<MenuTree> menuTrees;

	public String login() {
		User user = userService.login(loginFormBean);
		if (user != null) {
			this.session.put("user", user);
		}
		return SUCCESS;
	}

	public String register() {
		userService.register(loginFormBean);
		return SUCCESS;
	}

	public String showTree() {
		menuTrees = new ArrayList<MenuTree>();
		User u = (User) session.get("user");
		if (u != null) {
			try {
				menuTrees = userService.getMenuTreeByUser(u);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return "showtree";
	}

	public String logout() {
		if (session.get("user") != null) {
			session.put("user", null);
		}
		return Action.LOGIN;
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		this.session = arg0;
	}

	@Override
	public LoginFormBean getModel() {
		// TODO Auto-generated method stub
		return loginFormBean;
	}

	public LoginFormBean getLoginFormBean() {
		return loginFormBean;
	}

	public void setLoginFormBean(LoginFormBean loginFormBean) {
		this.loginFormBean = loginFormBean;
	}

	public UserService getUserService() {
		return userService;
	}

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public List<MenuTree> getMenuTrees() {
		return menuTrees;
	}

	public void setMenuTrees(List<MenuTree> menuTrees) {
		this.menuTrees = menuTrees;
	}

}
