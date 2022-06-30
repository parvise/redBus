package com.demo.busBookingApp.entity;

import java.util.List;
import java.util.Map;

public class ShowBusDetailsUIBean {
	private int serviceNo;
	private String betweenTime;
	private int durationHrs;
	private String busDescription;
	private int seatsAvailable;
	private double price;
	private String boardingPoint;
	private String droppingPoint;
	private Map<Integer, List<Ticket>> viewBusSetasMap;
	private List<Integer> bookedSeats;
	private int totalSeats;
	private String selectedSeats;
	private List<Integer> femaleBookedSeats;

	public int getServiceNo() {
		return serviceNo;
	}

	public void setServiceNo(int serviceNo) {
		this.serviceNo = serviceNo;
	}

	public String getBetweenTime() {
		return betweenTime;
	}

	public void setBetweenTime(String betweenTime) {
		this.betweenTime = betweenTime;
	}

	public int getDurationHrs() {
		return durationHrs;
	}

	public void setDurationHrs(int durationHrs) {
		this.durationHrs = durationHrs;
	}

	public String getBusDescription() {
		return busDescription;
	}

	public void setBusDescription(String busDescription) {
		this.busDescription = busDescription;
	}

	public int getSeatsAvailable() {
		return seatsAvailable;
	}

	public void setSeatsAvailable(int seatsAvailable) {
		this.seatsAvailable = seatsAvailable;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getBoardingPoint() {
		return boardingPoint;
	}

	public void setBoardingPoint(String boardingPoint) {
		this.boardingPoint = boardingPoint;
	}

	public String getDroppingPoint() {
		return droppingPoint;
	}

	public void setDroppingPoint(String droppingPoint) {
		this.droppingPoint = droppingPoint;
	}

	public Map<Integer, List<Ticket>> getViewBusSetasMap() {
		return viewBusSetasMap;
	}

	public void setViewBusSetasMap(Map<Integer, List<Ticket>> viewBusSetasMap) {
		this.viewBusSetasMap = viewBusSetasMap;
	}

	public List<Integer> getBookedSeats() {
		return bookedSeats;
	}

	public void setBookedSeats(List<Integer> bookedSeats) {
		this.bookedSeats = bookedSeats;
	}

	public int getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}

	public String getSelectedSeats() {
		if (selectedSeats == null)
			return "'#'";
		return selectedSeats;
	}

	public void setSelectedSeats(String selectedSeats) {
		this.selectedSeats = selectedSeats;
	}

	public List<Integer> getFemaleBookedSeats() {
		return femaleBookedSeats;
	}

	public void setFemaleBookedSeats(List<Integer> femaleBookedSeats) {
		this.femaleBookedSeats = femaleBookedSeats;
	}

}
