package com.njust.dg.oa.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.njust.dg.oa.service.RoleService;
import com.njust.dg.oa.vo.RoleManagerFormBean;
import com.opensymphony.xwork2.ModelDriven;

@Controller("roleManagerAction")
@Scope("prototype")
public class RoleManagerAction implements ModelDriven<RoleManagerFormBean> {
	private RoleManagerFormBean roleManagerFormBean = new RoleManagerFormBean();
	@Resource
	private RoleService roleService;

	public String getAllRoles() {
		roleManagerFormBean.setRoles(roleService.getAllRoles());
		return "allRoles";
	}

	public String getRolesByUser() {
		roleManagerFormBean.setRoles(roleService.getRolesByUser(roleManagerFormBean.getUserId()));
		return "allRoles";
	}

	public String updateRole() {
		roleService.updateRole(roleManagerFormBean);
		return "success";
	}

	public String addRole() {
		roleService.addRole(roleManagerFormBean);
		return "success";
	}

	public String deleteRole() {
		roleService.deleteRole(roleManagerFormBean);
		return "success";
	}

	@Override
	public RoleManagerFormBean getModel() {
		return roleManagerFormBean;
	}

}
