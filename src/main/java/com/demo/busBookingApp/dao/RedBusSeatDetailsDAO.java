package com.demo.busBookingApp.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demo.busBookingApp.entity.BusServiceSeats;

@Repository
public interface RedBusSeatDetailsDAO extends CrudRepository<BusServiceSeats, Integer> {

	@Query(value = "SELECT p FROM BusServiceSeats p WHERE p.serviceNo=?1", nativeQuery = false)
	BusServiceSeats findBusServiceDetails(int serviceNo);
}
