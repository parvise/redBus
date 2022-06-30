package com.demo.busBookingApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.busBookingApp.dao.PassengerTicketBookDAO;
import com.demo.busBookingApp.entity.PassengerTicketEntity;

@Service
public class PassengerTicketBookService {

	@Autowired
	private PassengerTicketBookDAO ticketBookDAO;
	
	public Iterable<PassengerTicketEntity> savePassengerTicketDetails(Iterable<PassengerTicketEntity> passDetails) {
		return ticketBookDAO.save(passDetails);
	}
}
