package com.demo.busBookingApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.busBookingApp.dao.RedBusSeatDetailsDAO;
import com.demo.busBookingApp.entity.BusServiceSeats;

@Service
public class RedBusSeatDetailsService {

	@Autowired
	private RedBusSeatDetailsDAO redBusDao;

	public BusServiceSeats findBusServiceDetails(int serviceNo) {
		return redBusDao.findOne(serviceNo);
	}

	public BusServiceSeats updateBusServiceDetails(BusServiceSeats busSeats) {
		return redBusDao.save(busSeats);
	}

	public Iterable<BusServiceSeats> findAllServiceNos() {
		return redBusDao.findAll();
	}
}
