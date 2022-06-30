package com.demo.busBookingApp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.demo.busBookingApp.business.ProcessBusSeatsSelected;
import com.demo.busBookingApp.entity.BusServiceSeats;
import com.demo.busBookingApp.entity.MessageStyle;
import com.demo.busBookingApp.entity.PassengerBookingTickets;
import com.demo.busBookingApp.entity.PassengerTicketEntity;
import com.demo.busBookingApp.entity.ShowBusDetailsUI;
import com.demo.busBookingApp.entity.Ticket;
import com.demo.busBookingApp.service.PassengerTicketBookService;
import com.demo.busBookingApp.service.RedBusSeatDetailsService;
import com.demo.busBookingApp.util.GetBusTicketsFromDB;

@Controller
public class ConfirmRedBusSelectedSeatsController {

	@Autowired
	private RedBusSeatDetailsService service;

	@Autowired
	private PassengerTicketBookService bookService;

	@PostMapping(value = "/displaySelectedSeats/{serviceNo}")
	public String displaySelectedSeats(Model model, HttpServletRequest request,
			@PathVariable("serviceNo") int serviceNoUi, HttpServletResponse response) {
		System.out.println("Method displaySelectedSeats starts" + serviceNoUi);

		ProcessBusSeatsSelected busSeatsSelection = new ProcessBusSeatsSelected();
		List<PassengerBookingTickets> ticketBeanList = busSeatsSelection.updateSeletectedSeats(request, serviceNoUi);
		MessageStyle style = new MessageStyle();
		HttpSession session = request.getSession(false);
		// System.out.println("getRequestSeatsFromUI session " + session);
		String primpPassengerName = request.getParameter("passengerName");
		Map<Integer, Map<String, List<Ticket>>> serviceNoSeatsMap = null;
		if (session != null) {
			serviceNoSeatsMap = (Map<Integer, Map<String, List<Ticket>>>) session.getAttribute("serviceNoSeatsMap");
		}

		List<Ticket> selectedSeatList = null;
		if (serviceNoSeatsMap != null) {
			if (serviceNoSeatsMap.containsKey(serviceNoUi)) {
				Map<String, List<Ticket>> userSelectdSeatsMap = serviceNoSeatsMap.get(serviceNoUi);
				if (serviceNoSeatsMap != null) {
					if (userSelectdSeatsMap.containsKey(primpPassengerName)) {
						selectedSeatList = userSelectdSeatsMap.get(primpPassengerName);
					}
				}
			}
		}

		if (ticketBeanList != null && ticketBeanList.size() > 0) {
			request.setAttribute("passengerBean", ticketBeanList);
			model.addAttribute("passengerBean", ticketBeanList);
			style.setMessageName("Please make payment before 60 seconds");
			style.setClassName("success");
			model.addAttribute("message", style);
			model.addAttribute("serviceNo", serviceNoUi);
			System.out.println("***** serviceNoSeatsMap *****" + serviceNoSeatsMap);
			return "displaySelectedSeats";
		}
		return "redBus";

	}

	@PostMapping(value = "/bookTickets/{serviceNo}")
	public String bookTickets(Model model, HttpServletRequest request, int serviceNo) throws InterruptedException {
		System.out.println("Method bookTickets starts");
		boolean isUpdate = true;
		String[] selectedSeatList = request.getParameterValues("selectedSeatList");
		// System.out.println("confirmedList" +
		// Arrays.toString(selectedSeatList));

		ProcessBusSeatsSelected busSeatsSelection = new ProcessBusSeatsSelected();

		HttpSession session = request.getSession(false);
		// System.out.println("bookTickets session " + session);

		List<PassengerBookingTickets> passengerBookingTicketList = busSeatsSelection.getRequestSeatsFromUI(request,
				serviceNo);
		// System.out.println("Test value comes or not" +
		// passengerBookingTicketList);

		String primpPassengerName = request.getParameter("passengerName");
		Map<Integer, Map<String, List<Ticket>>> serviceNoSeatsMap = null;
		List<Ticket> ticketBeanList = new ArrayList<>();
		if (session != null) {
			serviceNoSeatsMap = (Map<Integer, Map<String, List<Ticket>>>) session.getAttribute("serviceNoSeatsMap");
		}
		Map<String, List<Ticket>> userSelectdSeatsMap = null;
		if (serviceNoSeatsMap != null) {
			if (serviceNoSeatsMap.containsKey(serviceNo)) {
				userSelectdSeatsMap = serviceNoSeatsMap.get(serviceNo);
				if (serviceNoSeatsMap != null) {
					if (userSelectdSeatsMap.containsKey(primpPassengerName)) {
						ticketBeanList = userSelectdSeatsMap.get(primpPassengerName);
					}
				}
			}
		}

		// System.out.println("selectedSeatList l " + selectedSeatList.length +
		// " , " + selectedSeatList);
//		ShowBusDetailsUI busUI = GetBusTicketsFromDB.getBusDetailsUi();
		ShowBusDetailsUI busUI = null;
		Map<Integer, ShowBusDetailsUI> busDetailsUiMap = GetBusTicketsFromDB.getBusDetailsMapUi();

		if (busDetailsUiMap != null && busDetailsUiMap.containsKey(serviceNo)) {
			busUI = busDetailsUiMap.get(serviceNo);
		}

		StringBuilder builder = new StringBuilder();
		StringBuilder femaleBuilder = new StringBuilder();

		List<PassengerTicketEntity> ticketEntityList = convertBeanToEntity(passengerBookingTicketList);

		MessageStyle messsage = new MessageStyle();

		for (PassengerTicketEntity passTicket : ticketEntityList) {

			for (Ticket ticket : ticketBeanList) {
				synchronized (ticket) {
					if (ticket.getTicketId() == passTicket.getTicketId() && !ticket.isBooked()) {

						long now = new Date().getTime();
						Date lastAccessedDate = ticket.getSeletedTime();
						long timeoutPeriod = ticket.getMaxActive();

						// ReentrantLock
						// ReadWriteLock
						long remainingTime = (timeoutPeriod) - (now - lastAccessedDate.getTime()) / 1000;
						System.out.println("remainingTime" + remainingTime);
						// Allowing to user Not book the tickets because 60
						// seconds expired.

						if (remainingTime <= 0) {
							messsage.setMessageName("Sorry for the inconvenience. Your transaction time-out...");
							messsage.setClassName("fail");
							ticket.setBlocked(false);
							ticket.setSelected(false);
							ticket.setAvailable(true);
							ticket.setBtnStyleClsName("btnDAvl");
							ticket.setFemaleSeat(false);
							busUI.setSelectedSeats("");
							if (userSelectdSeatsMap != null)
								userSelectdSeatsMap.remove(primpPassengerName);
							isUpdate = false; //
							break;
						} else {
							ticket.setBlocked(true);
							ticket.setBtnStyleClsName("btnDBlocked");
							ticket.setSelected(false);
							ticket.setAvailable(false);
							Thread.sleep(1000);
							ticket.setBooked(true);
							ticket.setBlocked(false);
							if (passTicket.getGender().equalsIgnoreCase("Female")) {
								ticket.setBtnStyleClsName("btnDYellow");
								ticket.setFemaleSeat(true);
								femaleBuilder.append(passTicket.getTicketId() + "#");
							} else {
								ticket.setBtnStyleClsName("btnDGray");
							}
							passTicket.setBookeDateTime(new Date());
							messsage.setMessageName(
									"Thanks for using Green Bus App. Your tickets  successfully booked");
							messsage.setClassName("success");
							passTicket.setStatus("Booked Successfully");
							builder.append(ticket.getTicketId() + "#");
							isUpdate = true;
							continue;
						}
					} else {
						isUpdate = false;
						messsage.setMessageName(
								"Thanks for using Green Bus App. Your tickets booked fail. Sorry for the inconvenience");
						messsage.setClassName("fail");
						passTicket.setStatus("Booked Failure");
					}
				}
			}
		}

		if (isUpdate) {
			BusServiceSeats seats = service.findBusServiceDetails(serviceNo);
			// Append Existing Booked Tickets from DB
			builder.append(seats.getBookedSeats());
			// Append Existing Female Booked Tickets from DB
			femaleBuilder.append(seats.getFemaleSeats());
			String bookedSeats = builder.toString();
			if (bookedSeats.length() > 0 && bookedSeats.lastIndexOf("#") == bookedSeats.length() - 1)
				bookedSeats = bookedSeats.substring(0, bookedSeats.length() - 1);

			String femaleBookedSeats = femaleBuilder.toString();
			if (femaleBookedSeats.length() > 0 && femaleBookedSeats.lastIndexOf("#") == femaleBookedSeats.length() - 1)
				femaleBookedSeats = femaleBookedSeats.substring(0, femaleBookedSeats.length() - 1);
			synchronized (seats) {
				if (seats != null) {
					seats.setBookedSeats(bookedSeats);
					seats.setFemaleSeats(femaleBookedSeats);
					seats.setAvailableSeats(seats.getAvailableSeats() - ticketBeanList.size());
					System.out.println("Updated Successfully");
					service.updateBusServiceDetails(seats);
					busUI.setSeatsAvailable(seats.getAvailableSeats());
					bookService.savePassengerTicketDetails(ticketEntityList);
					if (userSelectdSeatsMap != null)
						userSelectdSeatsMap.remove(primpPassengerName);
					// GetBusTicketsFromDB.reloadInstance();
				}
			}

		}

		model.addAttribute("passengerBean", convertEntityToBean(ticketEntityList));
		model.addAttribute("message", messsage);
		System.out.println("Booked Successfully ***** serviceNoSeatsMap *****" + serviceNoSeatsMap);
		return "ticketsBookedSucess";
	}

	private List<PassengerTicketEntity> convertBeanToEntity(List<PassengerBookingTickets> ticketBeanList) {
		List<PassengerTicketEntity> list = new ArrayList<>();
		for (PassengerBookingTickets passengerBookingTickets : ticketBeanList) {
			PassengerTicketEntity ticketBean = new PassengerTicketEntity();
			ticketBean.setServiceNo(passengerBookingTickets.getServiceNo());
			ticketBean.setPrimaryPassName(passengerBookingTickets.getPrimaryPassName());
			ticketBean.setEmailId(passengerBookingTickets.getEmailId());
			ticketBean.setGender(passengerBookingTickets.getGender());
			ticketBean.setAge(passengerBookingTickets.getAge());
			ticketBean.setName(passengerBookingTickets.getName());
			ticketBean.setTicketId(passengerBookingTickets.getTicketId());
			ticketBean.setMobileNo(passengerBookingTickets.getMobileNo());
			list.add(ticketBean);

		}
		return list;
	}

	private List<PassengerBookingTickets> convertEntityToBean(List<PassengerTicketEntity> ticketBeanList) {
		List<PassengerBookingTickets> list = new ArrayList<>();
		int count = 1;
		for (PassengerTicketEntity passengerBookingTickets : ticketBeanList) {
			PassengerBookingTickets ticketBean = new PassengerBookingTickets();
			ticketBean.setServiceNo(passengerBookingTickets.getServiceNo());
			ticketBean.setPrimaryPassName(passengerBookingTickets.getPrimaryPassName());
			ticketBean.setEmailId(passengerBookingTickets.getEmailId());
			ticketBean.setGender(passengerBookingTickets.getGender());
			ticketBean.setAge(passengerBookingTickets.getAge());
			ticketBean.setName(passengerBookingTickets.getName());
			ticketBean.setTicketId(passengerBookingTickets.getTicketId());
			ticketBean.setMobileNo(passengerBookingTickets.getMobileNo());
			ticketBean.setsNo(count);
			list.add(ticketBean);
			count++;

		}
		return list;
	}
}
