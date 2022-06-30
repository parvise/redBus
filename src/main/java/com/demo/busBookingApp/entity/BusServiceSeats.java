package com.demo.busBookingApp.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "bus_service_seats")
@DynamicUpdate
public class BusServiceSeats implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6969383111434478680L;

	@Id
	@Column(name = "service_no", nullable = false)
	private int serviceNo;

	@Column(name = "source", nullable = false)
	private String source;

	@Column(name = "destination", nullable = false)
	private String destination;

	@Column(name = "bus_description", nullable = false)
	private String busDescription;

	@Column(name = "price", nullable = false)
	private double price;

	@Column(name = "departure_time", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date departureTime;

	@Column(name = "duration_hrs", nullable = false)
	private int durationHrs;

	@Column(name = "total_seats", nullable = false)
	private int totalSeats;

	@Column(name = "avl_Seats", nullable = false)
	private int availableSeats;

	@Column(name = "booked_seats", nullable = false)
	private String bookedSeats;

	@Column(name = "female_seats", nullable = false)
	private String femaleSeats;

	@Column(name = "bus_type", nullable = false)
	private String busServiceType;

	@Version
	@Column(name = "VERSION")
	private Long version;

	public int getServiceNo() {
		return serviceNo;
	}

	public void setServiceNo(int serviceNo) {
		this.serviceNo = serviceNo;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getBusDescription() {
		return busDescription;
	}

	public void setBusDescription(String busDescription) {
		this.busDescription = busDescription;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	public int getDurationHrs() {
		return durationHrs;
	}

	public void setDurationHrs(int durationHrs) {
		this.durationHrs = durationHrs;
	}

	public int getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}

	public int getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}

	public String getBookedSeats() {
		return bookedSeats;
	}

	public void setBookedSeats(String bookedSeats) {
		this.bookedSeats = bookedSeats;
	}

	public String getFemaleSeats() {
		return femaleSeats;
	}

	public void setFemaleSeats(String femaleSeats) {
		this.femaleSeats = femaleSeats;
	}

	public String getBusServiceType() {
		return busServiceType;
	}

	public void setBusServiceType(String busServiceType) {
		this.busServiceType = busServiceType;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "BusServiceSeats [serviceNo=" + serviceNo + ", price=" + price + ", totalSeats=" + totalSeats
				+ ", availableSeats=" + availableSeats + ", bookedSeats=" + (bookedSeats) + ", version=" + (version)
				+ "]";
	}

}
