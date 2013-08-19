package com.njust.dg.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.njust.dg.oa.dao.BaseDao;
import com.njust.dg.oa.model.Pager;
import com.njust.dg.oa.service.BaseService;

public class BaseServiceImpl<T> implements BaseService<T> {
	@Resource
	private BaseDao<T> baseDao;
	@Override
	public void add(T t) {
		// TODO Auto-generated method stub
		baseDao.add(t);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		baseDao.delete(id);
	}

	@Override
	public void update(T t) {
		// TODO Auto-generated method stub
		baseDao.update(t);
	}

	@Override
	public void addOrUpdata(T t) {
		// TODO Auto-generated method stub
		baseDao.addOrUpdata(t);
	}

	@Override
	public T load(int id) {
		// TODO Auto-generated method stub
		return baseDao.load(id);
	}

	@Override
	public List<T> list(String hql, Object[] args) {
		// TODO Auto-generated method stub
		return baseDao.list(hql, args);
	}

	@Override
	public List<T> list(String hql) {
		// TODO Auto-generated method stub
		return baseDao.list(hql);
	}

	@Override
	public List<T> list(String hql, Object arg) {
		// TODO Auto-generated method stub
		return baseDao.list(hql, arg);
	}

	@Override
	public Pager<T> find(String hql, Object[] args) {
		// TODO Auto-generated method stub
		return baseDao.find(hql, args);
	}

	@Override
	public Pager<T> find(String hql, Object arg) {
		// TODO Auto-generated method stub
		return baseDao.find(hql, arg);
	}

	@Override
	public Pager<T> find(String hql) {
		// TODO Auto-generated method stub
		return baseDao.find(hql);
	}

	@Override
	public T load1(Long id) {
		// TODO Auto-generated method stub
		return baseDao.load(id);
	}

}
