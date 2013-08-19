package com.njust.dg.oa.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.njust.dg.oa.dao.BaseDao;
import com.njust.dg.oa.model.Pager;
import com.njust.dg.oa.model.SystemContext;


public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {

	/**
	 * 此处不能使用setSessionFactory注入，因为setSessionFactory在HibernateDaoSupport
	 * 中已经定义了而且还是final的，所以不能被覆盖
	 * 
	 * @param sessionFactory
	 */
	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	/**
	 * 创建一个Class的对象来获取泛型的class
	 */
	private Class<T> clz;

	@SuppressWarnings("unchecked")
	public Class<T> getClz() {
		if (clz == null) {
			// 获取泛型的Class对象
			clz = ((Class<T>) (((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]));
		}
		return clz;
	}

	@Override
	public void add(T t) {
		this.getHibernateTemplate().save(t);
	}

	@Override
	public void delete(int id) {
		this.getHibernateTemplate().delete(this.load(id));
	}

	@Override
	public void update(T t) {
		this.getHibernateTemplate().update(t);
	}

	@Override
	public T load(int id) {
		return this.getHibernateTemplate().load(getClz(), id);
	}

	@Override
	public void addOrUpdata(T t) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().saveOrUpdate(t);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> list(String hql, Object[] args) {
		Query u = this.getSession().createQuery(hql);
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				u.setParameter(i, args[i]);
			}
		}
		List<T> list = u.list();
		return list;
	}

	@Override
	public List<T> list(String hql) {
		return this.list(hql, null);
	}

	@Override
	public List<T> list(String hql, Object arg) {
		return this.list(hql, new Object[] { arg });
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pager<T> find(String hql, Object[] args) {
		Pager<T> pages = new Pager<T>();
		int pageOffset = SystemContext.getPageOffset();
		int pageSize = SystemContext.getPageSize();
		Query q = this.getSession().createQuery(hql);
		Query cq = this.getSession().createQuery(getCountHql(hql));
		if (args != null) {
			int index = 0;
			for (Object arg : args) {
				q.setParameter(index, arg);
				cq.setParameter(index, arg);
				index++;
			}
		}
		long totalRecord = (Long) cq.uniqueResult();
		q.setFirstResult(pageOffset);
		q.setMaxResults(pageSize);
		List<T> datas = q.list();
		pages.setDatas(datas);
		pages.setPageOffset(pageOffset);
		pages.setPageSize(pageSize);
		pages.setTotalRecord(totalRecord);
		return pages;
	}

	private String getCountHql(String hql) {
		// 1、获取from前面的字符串
		String f = hql.substring(0, hql.indexOf("from"));
		// 2、将from前面的字符串替换为select count(*)
		if (f.equals("")) {
			hql = "select count(*) " + hql;
		} else {
			hql = hql.replace(f, "select count(*) ");
		}
		// 3、将fetch替换为""，因为抓取查询不能使用count(*)
		hql = hql.replace("fetch", " ");
		return hql;
	}

	@Override
	public Pager<T> find(String hql, Object arg) {
		return this.find(hql, new Object[] { arg });
	}

	@Override
	public Pager<T> find(String hql) {
		return this.find(hql, null);
	}

	@Override
	public T load(Long id) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().load(getClz(), id);
	}

}
