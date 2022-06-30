package com.demo.busBookingApp.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demo.busBookingApp.entity.PassengerTicketEntity;

@Repository
public interface PassengerTicketBookDAO extends CrudRepository<PassengerTicketEntity, Integer> {

}
