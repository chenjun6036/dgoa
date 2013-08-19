package com.njust.dg.oa.dao;

import java.util.List;

import com.njust.dg.oa.model.Pager;

public interface BaseDao<T> {
	public void add(T t);

	public void delete(int id);

	public void update(T t);

	public void addOrUpdata(T t);

	public T load(int id);
	
	public T load(Long id);

	public List<T> list(String hql, Object[] args);

	public List<T> list(String hql);

	public List<T> list(String hql, Object arg);

	public Pager<T> find(String hql, Object[] args);

	public Pager<T> find(String hql, Object arg);

	public Pager<T> find(String hql);
}
