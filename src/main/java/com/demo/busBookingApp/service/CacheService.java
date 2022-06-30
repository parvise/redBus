package com.demo.busBookingApp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.demo.busBookingApp.dao.CacheLoginBeanDao;
import com.demo.busBookingApp.entity.LoginBean;

@Service
public class CacheService {

	@Autowired
	private CacheLoginBeanDao dao;

	private List<LoginBean> list = new ArrayList<>();
	private Map<Integer, LoginBean> map = new HashMap<>();

	@Cacheable(value = "cache1")
	public Map<Integer, LoginBean> getList() {
		System.out.println("Hitting getList DB");
		LoginBean bean = null;

		Iterable<LoginBean> iterable = dao.findAll();
		Iterator<LoginBean> itr = iterable.iterator();
		int count = 1;
		while (itr.hasNext()) {
			bean = itr.next();
			list.add(bean);
			map.put(count, bean);
			count++;
		}
		return map;
	}

	@CachePut(value = "cache1", key = "#loginId", unless = "#result==null")
	public LoginBean update(Integer loginId, String newUsername) {
		System.out.println("Hitting update DB " + loginId);
		LoginBean bean = null;
		if (map.containsKey(loginId)) {
			bean = map.get(loginId);
			bean.setUsername(newUsername);
		}
		bean = dao.findById(loginId);
		bean.setUsername(newUsername);
		dao.save(bean);
		return bean;
	}

	@Cacheable(value = "cache1", key = "#loginId", unless = "#result==null")
	public LoginBean get(Integer loginId) {
		System.out.println("Hitting get DB " + loginId);
		LoginBean bean = null;
		if (map.containsKey(loginId)) {
			bean = map.get(loginId);
		}
		bean = dao.findById(loginId);
		return bean;
	}

	@Cacheable(value = "cache1")
	public Map<Integer, LoginBean> add(String userName, String password) {
		System.out.println("Hitting add DB");
		LoginBean bean = null;
		bean = new LoginBean();
		bean.setUsername(userName);
		bean.setPassword(password);
		dao.save(bean);
		return getList();
	}

	@CacheEvict(value = "cache1")
	public LoginBean remove(Integer key) {
		LoginBean bean = dao.findById(key);
		dao.delete(bean);
		return bean;
	}
}
