package com.demo.busBookingApp.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "bus_seats_booked")
@DynamicUpdate
public class PassengerTicketEntity {

	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false)
	private int id;
	@Column(name = "service_no", nullable = false)
	private int serviceNo;
	@Column(name = "prim_pass_name", length = 128, nullable = false)
	private String primaryPassName;
	@Column(name = "mobile_no", length = 128, nullable = false)
	private String mobileNo;
	@Column(name = "email_id", length = 128, nullable = false)
	private String emailId;
	@Column(name = "gender", length = 128, nullable = false)
	private String gender;
	@Column(name = "pass_name", length = 128, nullable = false)
	private String name;
	@Column(name = "age", nullable = false)
	private int age;
	@Column(name = "seat_no", nullable = false)
	private int ticketId;

	@Column(name = "booked_date_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date bookeDateTime;

	@Transient
	private String status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getServiceNo() {
		return serviceNo;
	}

	public void setServiceNo(int serviceNo) {
		this.serviceNo = serviceNo;
	}

	public String getPrimaryPassName() {
		return primaryPassName;
	}

	public void setPrimaryPassName(String primaryPassName) {
		this.primaryPassName = primaryPassName;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public Date getBookeDateTime() {
		return bookeDateTime;
	}

	public void setBookeDateTime(Date bookeDateTime) {
		this.bookeDateTime = bookeDateTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
