package com.demo.busBookingApp.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.demo.busBookingApp.entity.BusServiceSeats;
import com.demo.busBookingApp.entity.ShowBusDetailsUI;
import com.demo.busBookingApp.entity.Ticket;
import com.demo.busBookingApp.service.RedBusSeatDetailsService;

public class GetBusTicketsFromDB {

	private static volatile GetBusTicketsFromDB _getBusTicketsFromDB;
	private RedBusSeatDetailsService service;
	private static ShowBusDetailsUI busDetailsUi;
	private static Map<Integer, ShowBusDetailsUI> busDetailsUiMap = new HashMap();

	private GetBusTicketsFromDB() {

	}

	public static synchronized GetBusTicketsFromDB getInstance(RedBusSeatDetailsService service) {
		if (_getBusTicketsFromDB == null) {
			_getBusTicketsFromDB = new GetBusTicketsFromDB();
			Iterable<BusServiceSeats> allBusServiceDetails = service.findAllServiceNos();
			Iterator<BusServiceSeats> busServiceIterator = allBusServiceDetails.iterator();
			while (busServiceIterator.hasNext()) {
				BusServiceSeats busServiceSeats = busServiceIterator.next();
				displaySeats(busServiceSeats);
				// service.findBusServiceDetails(5656);
			}
			// displaySeats(service.findBusServiceDetails(5757));

		}
		return _getBusTicketsFromDB;
	}

	public static ShowBusDetailsUI displaySeats(BusServiceSeats buSeatsInfo) {
		if (buSeatsInfo == null)
			return null;
		int totalSeats = buSeatsInfo.getTotalSeats();

		busDetailsUi = new ShowBusDetailsUI();

		busDetailsUi.setServiceNo(buSeatsInfo.getServiceNo());
		busDetailsUi.setSeatsAvailable(buSeatsInfo.getAvailableSeats());
		busDetailsUi.setBoardingPoint(buSeatsInfo.getSource());
		busDetailsUi.setDroppingPoint(buSeatsInfo.getDestination());
		busDetailsUi.setDurationHrs(buSeatsInfo.getDurationHrs());
		busDetailsUi.setPrice(buSeatsInfo.getPrice());
		busDetailsUi.setBusDescription(buSeatsInfo.getBusDescription());
		busDetailsUi.setTotalSeats(totalSeats);
		busDetailsUi.setBusServiceType(buSeatsInfo.getBusServiceType());
		String bookeSeats = buSeatsInfo.getBookedSeats();
		String femaleBookedSeats = buSeatsInfo.getFemaleSeats();
		List<Integer> bookedSeatNosList = new ArrayList<>();
		List<Integer> femaleBookedSeatNosList = new ArrayList<>();
		if (bookeSeats != null && !bookeSeats.trim().equals("")) {
			String[] splitSeats = bookeSeats.split("#");
			if (bookeSeats.length() > 1) {
				for (String seat : splitSeats) {
					bookedSeatNosList.add(Integer.parseInt(seat));
				}
			} else {
				bookedSeatNosList.add(Integer.parseInt(bookeSeats));
			}
			busDetailsUi.setBookedSeats(bookedSeatNosList);
		}

		if (femaleBookedSeats != null && !femaleBookedSeats.trim().equals("")) {
			String[] splitSeats = femaleBookedSeats.split("#");
			if (femaleBookedSeats.length() > 1) {
				for (String seat : splitSeats) {
					femaleBookedSeatNosList.add(Integer.parseInt(seat));
				}
			} else {
				femaleBookedSeatNosList.add(Integer.parseInt(femaleBookedSeats));
			}
			busDetailsUi.setFemaleBookedSeats(femaleBookedSeatNosList);
		}

		Calendar pickupTime = Calendar.getInstance();
		pickupTime.setTime(buSeatsInfo.getDepartureTime());

		System.out.println("Pik Time " + buSeatsInfo.getDepartureTime());
		System.out.println("Time " + pickupTime.getTime());

		Calendar dropTime = Calendar.getInstance();
		dropTime.setTime(new Date());
		dropTime.add(Calendar.HOUR, buSeatsInfo.getDurationHrs()); //
		// adds
		System.out.println("Drop Time " + dropTime.getTime());
		System.out.println(dropTime.HOUR_OF_DAY);

		SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
		String drop = hourFormat.format(dropTime.getTime());
		String pickup = hourFormat.format(pickupTime.getTime());

		busDetailsUi.setBetweenTime(pickup + " " + drop);
		System.out.println("Betwen Time " + busDetailsUi.getBetweenTime());
		if (buSeatsInfo.getBusServiceType().equalsIgnoreCase("Passanger"))
			busLayoutDesignViewPassenger(buSeatsInfo, totalSeats, bookedSeatNosList, femaleBookedSeatNosList);
		else if (buSeatsInfo.getBusServiceType().equalsIgnoreCase("Sleeper"))
			busLayoutDesignViewSleeper(buSeatsInfo, totalSeats, bookedSeatNosList, femaleBookedSeatNosList);
		else if (buSeatsInfo.getBusServiceType().equalsIgnoreCase("Deluxe"))
			busLayoutDesignViewDeluxe(buSeatsInfo, totalSeats, bookedSeatNosList, femaleBookedSeatNosList);

		if (busDetailsUiMap.containsKey(buSeatsInfo.getServiceNo())) {
			busDetailsUi = busDetailsUiMap.get(buSeatsInfo.getServiceNo());
		}

		busDetailsUiMap.put(buSeatsInfo.getServiceNo(), busDetailsUi);
		return busDetailsUi;
	}

	private static void busLayoutDesignViewSleeper(BusServiceSeats buSeatsInfo, int totalSeats,
			List<Integer> bookedSeatNosList, List<Integer> femaleBookedSeatNosList) {
		StringBuilder builder = new StringBuilder();
		int temp = 1;
		builder.append("'");
		Map<Integer, List<Ticket>> busTicketsRowMap = new LinkedHashMap<>();
		for (int i = 1; i <= 4; i++) {
			List<Ticket> list = new ArrayList<>();
			for (int j = 1; j <= totalSeats / 4; j++) {
				String number = null;
				Ticket ticket = new Ticket(temp, "S" + temp, 60);
				ticket.setSeatNumber(String.valueOf(temp));
				ticket.setTotalSeats(totalSeats);
				ticket.setServiceNo(busDetailsUi.getServiceNo());
				if (bookedSeatNosList.contains(temp)) {
					ticket.setBooked(true);
					// Female seats color changed
					if (femaleBookedSeatNosList.contains(temp)) {
						ticket.setBtnStyleClsName("btnDYellow");
						ticket.setFemaleSeat(true);
					} else {
						ticket.setBtnStyleClsName("btnDGray");
					}
				} else {
					ticket.setAvailable(true);
					ticket.setBtnStyleClsName("btnDAvl");
				}

				if (i == 1) {
					ticket.setTicketTitleWindow("W|U|" + ticket.getTicketId());
				}

				if (i == 4) {
					ticket.setTicketTitleWindow("W|D|" + ticket.getTicketId());
				}
				list.add(ticket);

				if (ticket.isSelected())
					builder.append(ticket.getTicketId() + "-" + buSeatsInfo.getServiceNo() + "#");
				// builder.append(ticket.getTicketId() + "#");
				if (temp <= 9) {
					DecimalFormat format = new DecimalFormat("00");
					number = format.format(temp);
					ticket.setSeatNumber(number);
				}
				// System.out.print(temp + ",");
				temp = temp + 4;

			}
			busTicketsRowMap.put(i, list);
			temp = i;
			temp++;
		}
		// System.out.println(map);

		busDetailsUi.setViewBusSetasMap(busTicketsRowMap);

		builder.append("','" + busDetailsUi.getTotalSeats() + "'");
		busDetailsUi.setSelectedSeats(builder.toString());
	}

	private static void busLayoutDesignViewPassenger(BusServiceSeats buSeatsInfo, int totalSeats,
			List<Integer> bookedSeatNosList, List<Integer> femaleBookedSeatNosList) {
		StringBuilder builder = new StringBuilder();
		int temp = 1;
		builder.append("'");
		totalSeats = 49;
		Map<Integer, List<Ticket>> busTicketsRowMap = new LinkedHashMap<>();
		int listAdd = 0;
		for (int i = 1; i <= 4; i++) {
			List<Ticket> list = new ArrayList<>();
			for (int j = 1; j <= (totalSeats + 1) / 5; j++) {
				if (i == 4 && j - 1 == 9) {
					listAdd = (temp + 1) * (j - 1);
				} else if (i == 4) {
					listAdd = -1;
				}
				if (i < 4) {
					listAdd = temp;
					if (temp < 7)
						temp = temp + 3;
					else
						temp = temp + 5;
				}
				Ticket ticket = new Ticket(listAdd, "S" + listAdd, 60);
				ticket.setSeatNumber(String.valueOf(listAdd));
				ticket.setTotalSeats(totalSeats);
				ticket.setServiceNo(busDetailsUi.getServiceNo());
				if (bookedSeatNosList.contains(listAdd)) {
					ticket.setBooked(true);
					// Female seats color changed
					if (femaleBookedSeatNosList.contains(temp)) {
						ticket.setBtnStyleClsName("btnDYellow");
						ticket.setFemaleSeat(true);
					} else {
						ticket.setBtnStyleClsName("btnDGray");
					}
				} else {
					ticket.setAvailable(true);
					ticket.setBtnStyleClsName("btnDAvl");
				}

				if (i == 1) {
					ticket.setTicketTitleWindow("W|U|" + ticket.getTicketId());
				}

				list.add(ticket);
				if (ticket.isSelected())
					builder.append(ticket.getTicketId() + "-" + buSeatsInfo.getServiceNo() + "#");
			}
			temp = i + 1;
			busTicketsRowMap.put(i, list);
		}

		temp = 10;
		List<Ticket> list = null;
		int reservedSeats = totalSeats;
		for (int i = 5; i <= 6; i++) {
			if (i == 6) {
				list = new ArrayList<>();
			}
			if (i == 5)
				list = new ArrayList<>();
			for (int j = 1; j <= (totalSeats + 1) / 5; j++) {
				if (i == 6 && j <= 2) {
					listAdd = reservedSeats;
					// System.out.print("#,");
					reservedSeats--;
				} else if (i == 5 && j <= 2) {
					listAdd = -1;
					// System.out.print("#,");
				} else {
					if (j == 10) {
						temp = temp + 1;
					}
					listAdd = temp;
					temp = temp + 5;
				}

				Ticket ticket = new Ticket(listAdd, "S" + listAdd, 60);
				ticket.setSeatNumber(String.valueOf(listAdd));
				ticket.setTotalSeats(totalSeats);
				ticket.setServiceNo(busDetailsUi.getServiceNo());
				if (bookedSeatNosList.contains(listAdd)) {
					ticket.setBooked(true);
					// Female seats color changed
					if (femaleBookedSeatNosList.contains(listAdd)) {
						ticket.setBtnStyleClsName("btnDYellow");
						ticket.setFemaleSeat(true);
					} else {
						ticket.setBtnStyleClsName("btnDGray");
					}
				} else {
					ticket.setAvailable(true);
					ticket.setBtnStyleClsName("btnDAvl");
				}

				if (i == 6) {
					ticket.setTicketTitleWindow("W|D|" + ticket.getTicketId());
				}

				if (listAdd == 48 || listAdd == 49) {
					ticket.setBooked(true);
					ticket.setBtnStyleClsName("btnDGray");
					ticket.setTicketTitleWindow("W|R|" + ticket.getTicketId());
				}

				list.add(ticket);
				if (ticket.isSelected())
					builder.append(ticket.getTicketId() + "-" + buSeatsInfo.getServiceNo() + "#");

			}
			temp = 11;
			busTicketsRowMap.put(i, list);
		}

		for (List<Ticket> integer : busTicketsRowMap.values()) {
			System.out.println(integer);
		}

		busDetailsUi.setViewBusSetasMap(busTicketsRowMap);

		builder.append("','" + busDetailsUi.getTotalSeats() + "'");
		busDetailsUi.setSelectedSeats(builder.toString());
	}

	private static void busLayoutDesignViewDeluxe(BusServiceSeats buSeatsInfo, int totalSeats,
			List<Integer> bookedSeatNosList, List<Integer> femaleBookedSeatNosList) {
		StringBuilder builder = new StringBuilder();
		int temp = 1;
		builder.append("'");
		totalSeats = 48;
		Map<Integer, List<Ticket>> busTicketsRowMap = new LinkedHashMap<>();
		Integer listAdd = 0;
		for (int i = 1; i <= 3; i++) {
			List<Ticket> list = new ArrayList<>();
			for (int j = 1; j <= totalSeats / 4; j++) {
				if (i == 3) {
					if (j == 12)
						listAdd = temp;
					else
						listAdd = -1;
				} else
					listAdd = temp;
				if (j >= 2)
					temp = temp + 4;
				else
					temp = temp + 2;

				if (i >= 4 && j >= 2) {
					listAdd = temp;
				}
				Ticket ticket = new Ticket(listAdd, "S" + listAdd, 60);
				ticket.setSeatNumber(String.valueOf(listAdd));
				ticket.setTotalSeats(totalSeats);
				ticket.setServiceNo(busDetailsUi.getServiceNo());
				if (bookedSeatNosList.contains(listAdd)) {
					ticket.setBooked(true);
					// Female seats color changed
					if (femaleBookedSeatNosList.contains(listAdd)) {
						ticket.setBtnStyleClsName("btnDYellow");
						ticket.setFemaleSeat(true);
					} else {
						ticket.setBtnStyleClsName("btnDGray");
					}
				} else {
					ticket.setAvailable(true);
					ticket.setBtnStyleClsName("btnDAvl");
				}

				if (i == 1) {
					ticket.setTicketTitleWindow("W|U|" + ticket.getTicketId());
				}

				list.add(ticket);
				if (ticket.isSelected())
					builder.append(ticket.getTicketId() + "-" + buSeatsInfo.getServiceNo() + "#");
			}
			temp = i;
			temp++;
			busTicketsRowMap.put(i, list);
		}

		temp = 5;
		List<Ticket> list = null;
		for (int i = 4; i <= 5; i++) {
			list = new ArrayList<>();
			for (int j = 1; j <= totalSeats / 4; j++) {

				if (i == 4 && j == 1)
					listAdd = -1;
				else if (i == 5 && j == 1)
					listAdd = totalSeats;
				else {
					listAdd = temp;
					if ((i == 4 || i == 5) && j == 12) {
						listAdd = temp + 1;
						temp = 6;
					} else
						temp = temp + 4;
				}

				Ticket ticket = new Ticket(listAdd, "S" + listAdd, 60);
				ticket.setSeatNumber(String.valueOf(listAdd));
				ticket.setTotalSeats(totalSeats);
				ticket.setServiceNo(busDetailsUi.getServiceNo());
				if (bookedSeatNosList.contains(listAdd)) {
					ticket.setBooked(true);
					// Female seats color changed
					if (femaleBookedSeatNosList.contains(listAdd)) {
						ticket.setBtnStyleClsName("btnDYellow");
						ticket.setFemaleSeat(true);
					} else {
						ticket.setBtnStyleClsName("btnDGray");
					}
				} else {
					ticket.setAvailable(true);
					ticket.setBtnStyleClsName("btnDAvl");
				}

				if (i == 5) {
					ticket.setTicketTitleWindow("W|D|" + ticket.getTicketId());
				}

				if (listAdd == 48) {
					ticket.setBooked(true);
					ticket.setBtnStyleClsName("btnDGray");
					ticket.setTicketTitleWindow("W|R|" + ticket.getTicketId());
				}

				list.add(ticket);
				if (ticket.isSelected())
					builder.append(ticket.getTicketId() + "-" + buSeatsInfo.getServiceNo() + "#");

			}
			busTicketsRowMap.put(i, list);
		}

		for (List<Ticket> integer : busTicketsRowMap.values()) {
			System.out.println(integer);
		}

		busDetailsUi.setViewBusSetasMap(busTicketsRowMap);

		builder.append("','" + busDetailsUi.getTotalSeats() + "'");
		busDetailsUi.setSelectedSeats(builder.toString());
	}

	/*
	 * public static void main(String[] args) { busLayoutDesignView2(null, 0,
	 * null, null); }
	 */

	public static ShowBusDetailsUI getBusDetailsUi() {
		return busDetailsUi;
	}

	public static Map<Integer, ShowBusDetailsUI> getBusDetailsMapUi() {
		return busDetailsUiMap;
	}

	public static void reloadInstance() {
		busDetailsUi = null;
	}

}
