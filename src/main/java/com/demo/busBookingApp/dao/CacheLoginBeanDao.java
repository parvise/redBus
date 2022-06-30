package com.demo.busBookingApp.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demo.busBookingApp.entity.LoginBean;

@Repository
public interface CacheLoginBeanDao extends CrudRepository<LoginBean, Integer> {

	@Query(value = "SELECT p FROM LoginBean p WHERE p.id=?1", nativeQuery = false)
	LoginBean findById(int id);
}
