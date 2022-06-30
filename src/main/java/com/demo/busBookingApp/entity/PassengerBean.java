package com.demo.busBookingApp.entity;

import java.util.Arrays;
import java.util.Date;

public class PassengerBean {

	private String passengerName;
	private String mobileNo;
	private String emailId;
	private String gender[];
	private String name[];
	private int age[];
	private int ticketId[];
	private Date bookedDateTime[];

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
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

	public String[] getGender() {
		return gender;
	}

	public void setGender(String[] gender) {
		this.gender = gender;
	}

	public String[] getName() {
		return name;
	}

	public void setName(String[] name) {
		this.name = name;
	}

	public int[] getAge() {
		return age;
	}

	public void setAge(int[] age) {
		this.age = age;
	}

	public int[] getTicketId() {
		return ticketId;
	}

	public void setTicketId(int[] ticketId) {
		this.ticketId = ticketId;
	}
	
	

	public Date[] getBookedDateTime() {
		return bookedDateTime;
	}

	public void setBookedDateTime(Date[] bookedDateTime) {
		this.bookedDateTime = bookedDateTime;
	}

	@Override
	public String toString() {
		return "PassengerBean [passengerName=" + passengerName + ", mobileNo=" + mobileNo + ", emailId=" + emailId
				+ ", gender=" + Arrays.toString(gender) + ", name=" + Arrays.toString(name) + ", age="
				+ Arrays.toString(age) + ", ticketId=" + Arrays.toString(ticketId) + "]";
	}

}
