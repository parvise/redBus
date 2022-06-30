package com.demo.busBookingApp.entity;

import java.util.Date;

public class Ticket {

	private volatile int ticketId;
	private String ticketName;
	private volatile Date seletedTime;
	private boolean isDriver;
	private String seatNumber;
	private volatile boolean isSelected;
	private volatile boolean isAvailable;
	private volatile boolean isBooked;
	private volatile boolean isFemaleSeat;
	private volatile boolean isBlocked;
	private volatile String btnStyleClsName;
	private String ticketTitleWindow;
	private volatile int totalSeats;
	private int maxActive;
	private volatile int serviceNo;

	public Ticket(int ticketId, String ticketName, Date seletedTime, int maxActive) {
		super();
		this.ticketId = ticketId;
		this.ticketName = ticketName;
		this.seletedTime = seletedTime;
		this.maxActive = maxActive;
	}

	public Ticket(int ticketId, String ticketName, int maxActive) {
		super();
		this.ticketId = ticketId;
		this.ticketName = ticketName;
		this.maxActive = maxActive;
	}

	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public String getTicketName() {
		return ticketName;
	}

	public void setTicketName(String ticketName) {
		this.ticketName = ticketName;
	}

	public Date getSeletedTime() {
		return seletedTime;
	}

	public void setSeletedTime(Date seletedTime) {
		this.seletedTime = seletedTime;
	}

	public boolean isDriver() {
		return isDriver;
	}

	public void setDriver(boolean isDriver) {
		this.isDriver = isDriver;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public boolean isBooked() {
		return isBooked;
	}

	public void setBooked(boolean isBooked) {
		this.isBooked = isBooked;
	}

	public boolean isFemaleSeat() {
		return isFemaleSeat;
	}

	public void setFemaleSeat(boolean isFemaleSeat) {
		this.isFemaleSeat = isFemaleSeat;
	}

	public String getBtnStyleClsName() {
		return btnStyleClsName;
	}

	public void setBtnStyleClsName(String btnStyleClsName) {
		this.btnStyleClsName = btnStyleClsName;
	}

	public int getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public String getTicketTitleWindow() {
		return ticketTitleWindow;
	}

	public void setTicketTitleWindow(String ticketTitleWindow) {
		this.ticketTitleWindow = ticketTitleWindow;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public int getServiceNo() {
		return serviceNo;
	}

	public void setServiceNo(int serviceNo) {
		this.serviceNo = serviceNo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + serviceNo;
		result = prime * result + ticketId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ticket other = (Ticket) obj;
		if (serviceNo != other.serviceNo)
			return false;
		if (ticketId != other.ticketId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Ticket [ticketId=" + ticketId + ", isSelected=" + isSelected + ", isAvailable=" + isAvailable
				+ ", isBooked=" + isBooked + ", isFemaleSeat=" + isFemaleSeat + ", isBlocked=" + isBlocked
				+ ", serviceNo=" + serviceNo + "]";
	}

}
