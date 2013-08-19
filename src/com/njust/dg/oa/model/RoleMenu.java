package com.njust.dg.oa.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "role_menu")
public class RoleMenu {
	private int id;
	private Role role;
	private MenuTree menuTree;

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "role_id")
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@ManyToOne
	@JoinColumn(name = "menu_id")
	public MenuTree getMenuTree() {
		return menuTree;
	}

	public void setMenuTree(MenuTree menuTree) {
		this.menuTree = menuTree;
	}
}
