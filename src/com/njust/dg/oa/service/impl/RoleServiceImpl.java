package com.njust.dg.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.njust.dg.oa.dao.RoleDao;
import com.njust.dg.oa.dao.UserDao;
import com.njust.dg.oa.model.Role;
import com.njust.dg.oa.model.User;
import com.njust.dg.oa.service.RoleService;
import com.njust.dg.oa.vo.RoleManagerFormBean;

@Service("roleService")
public class RoleServiceImpl implements RoleService {
	@Resource
	private RoleDao roleDao;
	@Resource
	private UserDao userDao;

	@Override
	public List<Role> getAllRoles() {
		return roleDao.getAllRoles();
	}

	@Override
	public void updateRole(RoleManagerFormBean roleManagerFormBean) {
		Role role = roleDao.load(roleManagerFormBean.getRoleId());
		if (!role.getRoleName().equals(roleManagerFormBean.getRoleName())) {
			Role r = roleDao.getRoleByName(roleManagerFormBean.getRoleName());
			if (r != null) {
				roleManagerFormBean.setSuccess(false);
				roleManagerFormBean.setStatus("对不起，该角色名已存在，请重新输入！");
				return;
			}
		}
		role.setRoleName(roleManagerFormBean.getRoleName());
		roleDao.update(role);
		roleManagerFormBean.setSuccess(true);
		roleManagerFormBean.setStatus("修改成功！");
	}

	@Override
	public void addRole(RoleManagerFormBean roleManagerFormBean) {
		Role r = roleDao.getRoleByName(roleManagerFormBean.getRoleName());
		if (r != null) {
			roleManagerFormBean.setSuccess(false);
			roleManagerFormBean.setStatus("对不起，该角色名已存在，请重新输入！");
			return;
		}
		r = new Role();
		r.setRoleName(roleManagerFormBean.getRoleName());
		roleDao.add(r);
		roleManagerFormBean.setSuccess(true);
		roleManagerFormBean.setStatus("添加成功！");
	}

	@Override
	public void deleteRole(RoleManagerFormBean roleManagerFormBean) {
		roleDao.delete(roleManagerFormBean.getRoleId());
		roleManagerFormBean.setStatus("删除成功！");
		roleManagerFormBean.setSuccess(true);
	}

	@Override
	public List<Role> getRolesByUser(int userId) {
		User u = userDao.load(userId);
		return roleDao.findRolesByUser(u);
	}

}
