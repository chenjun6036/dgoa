package com.njust.dg.oa.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "menu_tree")
public class MenuTree {
	private int id;
	private String mid;
	private String text;
	private Boolean leaf;
	private String hrefTarget;
	private MenuTree parent;
	private List<MenuTree> children;

	@ManyToOne
	@JoinColumn(name = "parent")
	public MenuTree getParent() {
		return parent;
	}

	public void setParent(MenuTree parent) {
		this.parent = parent;
	}

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	@Column(name = "href_target")
	public String getHrefTarget() {
		return hrefTarget;
	}

	public void setHrefTarget(String hrefTarget) {
		this.hrefTarget = hrefTarget;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	@OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
	@OrderBy("mid ASC")
	public List<MenuTree> getChildren() {
		return children;
	}

	public void setChildren(List<MenuTree> children) {
		this.children = children;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
