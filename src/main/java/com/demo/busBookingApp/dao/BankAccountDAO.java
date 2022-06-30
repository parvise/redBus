package com.demo.busBookingApp.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demo.busBookingApp.entity.BankAccount;

@Repository
public interface BankAccountDAO extends CrudRepository<BankAccount, Long> {

	// 3). Example for Spring Data by Query Annoatations
		@Query(value = "SELECT p FROM BankAccount p WHERE p.fullName=?1", nativeQuery = false)
		BankAccount findByName(String name);
}
