package com.demo.busBookingApp.entity;

import java.util.Date;

public class PassengerBookingTickets {

	private int sNo;
	private int serviceNo;
	private String primaryPassName;
	private String mobileNo;
	private String emailId;
	private String gender;
	private String name;
	private int age;
	private int ticketId;
	private Date bookeDateTime;

	public int getsNo() {
		return sNo;
	}

	public void setsNo(int sNo) {
		this.sNo = sNo;
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

	@Override
	public String toString() {
		return "PassengerBookingTickets [sNo=" + sNo + ", serviceNo=" + serviceNo + ", primaryPassName="
				+ primaryPassName + ", mobileNo=" + mobileNo + ", emailId=" + emailId + ", gender=" + gender + ", name="
				+ name + ", age=" + age + ", ticketId=" + ticketId + ", bookeDateTime=" + bookeDateTime + "]";
	}

}
