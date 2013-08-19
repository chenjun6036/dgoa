package com.njust.dg.oa.vo;

import java.util.List;

import com.njust.dg.oa.model.User;

public class UserManagerFormBean {
	private User user;
	private List<User> users;
	private Boolean success;
	private String status;
	private int roleId;
	private Boolean isApproval;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public Boolean getIsApproval() {
		return isApproval;
	}

	public void setIsApproval(Boolean isApproval) {
		this.isApproval = isApproval;
	}
}
