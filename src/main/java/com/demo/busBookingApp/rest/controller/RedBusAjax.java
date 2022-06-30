package com.demo.busBookingApp.rest.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.demo.busBookingApp.entity.ShowBusDetailsUI;
import com.demo.busBookingApp.entity.ShowBusDetailsUIBean;
import com.demo.busBookingApp.entity.Ticket;
import com.demo.busBookingApp.service.RedBusSeatDetailsService;
import com.demo.busBookingApp.util.GetBusTicketsFromDB;

@RestController
public class RedBusAjax {

	@Autowired
	private RedBusSeatDetailsService service;

	@GetMapping(value = "/selectSeats/{id}/{serviceNo}")
	public String findById(@PathVariable("id") int id, @PathVariable("serviceNo") int serviceNo) {
		// System.out.println("welcome To ajax");
		// System.out.println("Welcom---> " + id);
		GetBusTicketsFromDB _getBusTicketsFromDB = GetBusTicketsFromDB.getInstance(service);
		// ShowBusDetailsUI showBusDetailsUI =
		// _getBusTicketsFromDB.getBusDetailsUi();

		Map<Integer, ShowBusDetailsUI> busDetailsUiMap = _getBusTicketsFromDB.getBusDetailsMapUi();
		ShowBusDetailsUI showBusDetailsUI = busDetailsUiMap.get(serviceNo);
		// ShowBusDetailsUIBean uiBean = converStaticToBean(showBusDetailsUI);
		Map<Integer, List<Ticket>> busMap = showBusDetailsUI.getViewBusSetasMap();

		Iterator<Entry<Integer, List<Ticket>>> iterator = busMap.entrySet().iterator();
		StringBuilder builder = new StringBuilder();
		builder.append("'");
		String status = "Success";
		boolean isBreak = false;
		while (iterator.hasNext()) {
			Entry<Integer, List<Ticket>> entry = iterator.next();
			List<Ticket> ticketList = entry.getValue();
			for (Ticket ticket : ticketList) {
				if (id > 0 && ticket.getTicketId() == id) {
					ticket.setSelected(true);
					// ticket.setBtnStyleClsName("btnDGreen");
					// ticket.setSeletedTime(new Date());
					// System.out.println("HasCode " + ticket.hashCode());
					if (ticket.isSelected() && !ticket.isBlocked()) {
						builder.append(ticket.getTicketId() + "#");
						isBreak = true;
					} else if (ticket.isBlocked()) {
						ticket.setSelected(false);
						ticket.setBtnStyleClsName("btnDBlocked");
						status = "Blocked";
						isBreak = true;
					} else if (ticket.isBooked()) {
						ticket.setSelected(false);
						ticket.setBtnStyleClsName("btnDGray");
						status = "Booked";
						isBreak = true;
					} else if (ticket.isBooked() && ticket.isFemaleSeat()) {
						ticket.setSelected(false);
						ticket.setBtnStyleClsName("btnDYellow");
						status = "Booked";
						isBreak = true;
					}
				} else if (id < 0 && -ticket.getTicketId() == id) {
					ticket.setSelected(false);
					ticket.setBtnStyleClsName("btnDAvl");
					// ticket.setSeletedTime(new Date());
					// System.out.println("HasCode " + ticket.hashCode());
					isBreak = true;
				}
				if (isBreak)
					break;
			}
			if (isBreak)
				break;
		}
		builder.append("','" + showBusDetailsUI.getTotalSeats() + "'");
		showBusDetailsUI.setSelectedSeats(builder.toString());
		// System.out.println(busMap);
		// System.out.println("showBusDetailsUI" +
		// showBusDetailsUI.getSelectedSeats());
		return status;
	}

	private ShowBusDetailsUIBean converStaticToBean(ShowBusDetailsUI showBusDetailsUI) {
		ShowBusDetailsUIBean uiBean = new ShowBusDetailsUIBean();
		uiBean.setBetweenTime(showBusDetailsUI.getBetweenTime());
		uiBean.setBoardingPoint(showBusDetailsUI.getBoardingPoint());
		uiBean.setBookedSeats(showBusDetailsUI.getBookedSeats());
		uiBean.setBusDescription(showBusDetailsUI.getBusDescription());
		uiBean.setDroppingPoint(showBusDetailsUI.getDroppingPoint());
		uiBean.setDurationHrs(showBusDetailsUI.getDurationHrs());
		uiBean.setFemaleBookedSeats(showBusDetailsUI.getFemaleBookedSeats());
		uiBean.setPrice(showBusDetailsUI.getPrice());
		uiBean.setSeatsAvailable(showBusDetailsUI.getSeatsAvailable());
		uiBean.setSelectedSeats(showBusDetailsUI.getSelectedSeats());
		uiBean.setServiceNo(showBusDetailsUI.getServiceNo());
		uiBean.setTotalSeats(showBusDetailsUI.getTotalSeats());
		Map<Integer, List<Ticket>> busMap = showBusDetailsUI.getViewBusSetasMap();

		Map<Integer, List<Ticket>> newBusMap = new HashMap<>();
		Set<Integer> set = busMap.keySet();
		for (Integer integer : set) {
			Integer newInt = new Integer(integer.intValue());
			List<Ticket> list = busMap.get(integer);
			List<Ticket> newList = new ArrayList<>();
			for (Ticket ticket : list) {
				Ticket newTicket = new Ticket(ticket.getTicketId(), ticket.getTicketName(), ticket.getSeletedTime(),
						60);
				newList.add(newTicket);
			}
			newBusMap.put(newInt, newList);
		}
		uiBean.setViewBusSetasMap(newBusMap);
		return uiBean;
	}

	@GetMapping(value = "/unblockSeats/{passengerName}/{serviceNo}")
	public String seatExipreCheck(HttpServletRequest request, @PathVariable("passengerName") String primpPassengerName,
			@PathVariable("serviceNo") int serviceNo) {

		HttpSession session = request.getSession(false);

		Map<Integer, Map<String, List<Ticket>>> serviceNoSeatsMap = null;
		List<Ticket> selectedSeatList = new ArrayList<>();
		if (session != null) {
			serviceNoSeatsMap = (Map<Integer, Map<String, List<Ticket>>>) session.getAttribute("serviceNoSeatsMap");
		}
		Map<String, List<Ticket>> userSelectdSeatsMap = null;
		if (serviceNoSeatsMap != null) {
			if (serviceNoSeatsMap.containsKey(serviceNo)) {
				userSelectdSeatsMap = serviceNoSeatsMap.get(serviceNo);
				if (userSelectdSeatsMap != null) {
					if (userSelectdSeatsMap.containsKey(primpPassengerName)) {
						selectedSeatList = userSelectdSeatsMap.get(primpPassengerName);
					}
				}
			}
		}

		ShowBusDetailsUI busUI = GetBusTicketsFromDB.getBusDetailsUi();
		boolean isUpdate = true;
		while (isUpdate) {
			for (Ticket ticket : selectedSeatList) {
				synchronized (ticket) {
					if (ticket.isBooked())
						return "done";
					long now = new Date().getTime();
					Date lastAccessedDate = ticket.getSeletedTime();
					long timeoutPeriod = ticket.getMaxActive();
					long remainingTime = (timeoutPeriod) - (now - lastAccessedDate.getTime()) / 1000;
					/*
					 * System.out.println( "Seat Expiry :" + remainingTime + ":"
					 * + serviceNo + ":passgnrName : " + primpPassengerName);
					 * System.out.println(ticket);
					 */

					if (remainingTime <= 0) {
						ticket.setBlocked(false);
						ticket.setSelected(false);
						ticket.setAvailable(true);
						ticket.setBtnStyleClsName("btnDAvl");
						ticket.setFemaleSeat(false);
						busUI.setSelectedSeats("");
						isUpdate = false; //
						if (userSelectdSeatsMap != null)
							userSelectdSeatsMap.remove(primpPassengerName);
						// break;
					}
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			// System.out.println("unblockSeats");
		}
		System.out.println("***** serviceNoSeatsMap *****" + serviceNoSeatsMap);
		if (!isUpdate) {
			return "Expired";
		}
		return "welcome";

	}

	@GetMapping(value = "/unCheckServiceNoSeats/{serviceNo}")
	public String unCheckServiceNoSeats(@PathVariable("serviceNo") int serviceNo) {
		System.out.println("unCheckServiceNoSeats" + serviceNo);

		Map<Integer, ShowBusDetailsUI> busDetailsUiMap = GetBusTicketsFromDB.getBusDetailsMapUi();

		if (busDetailsUiMap.containsKey(serviceNo)) {
			ShowBusDetailsUI busDetailsUi = busDetailsUiMap.get(serviceNo);
			Map<Integer, List<Ticket>> busMap = busDetailsUi.getViewBusSetasMap();

			Iterator<Entry<Integer, List<Ticket>>> iterator = busMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<Integer, List<Ticket>> entry = iterator.next();
				List<Ticket> ticketList = entry.getValue();
				for (Ticket ticket : ticketList) {
					if (ticket.isSelected() && ticket.getSeletedTime() == null) {
						// ticket.setSelected(false);
						ticket.setBtnStyleClsName("btnDAvl");
						;
					}
				}
			}

		}

		return "Closed";
	}

	@GetMapping(value = "/verifySeatsBlocked/{seatIds}/{serviceNo}")
	public String verifySeatsBlocked(HttpServletRequest request, @PathVariable("seatIds") int seatIds[],
			@PathVariable("serviceNo") int serviceNo) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return "Success";
		}

		Map<Integer, Map<String, List<Ticket>>> serviceNoSeatsMap = (Map<Integer, Map<String, List<Ticket>>>) session
				.getAttribute("serviceNoSeatsMap");
		if (serviceNoSeatsMap == null)
			return "Success";
		if (serviceNoSeatsMap.containsKey(serviceNo)) {
			Map<String, List<Ticket>> ticketList = serviceNoSeatsMap.get(serviceNo);
			if (ticketList.size() > 0) {
				Collection<List<Ticket>> selectedAlredayList = ticketList.values();
				for (int seatNo : seatIds) {
					for (List<Ticket> list : selectedAlredayList) {
						for (Ticket ticket : list) {
							synchronized (ticket) {
								if (seatNo == ticket.getTicketId() && ticket.isBlocked()) {
									return "Blocked";
								}
							}
						}
					}
				}

			}
		}

		Map<Integer, ShowBusDetailsUI> busDetailsUiMap = GetBusTicketsFromDB.getBusDetailsMapUi();

		if (busDetailsUiMap.containsKey(serviceNo)) {
			ShowBusDetailsUI busDetailsUi = busDetailsUiMap.get(serviceNo);
			Map<Integer, List<Ticket>> busMap = busDetailsUi.getViewBusSetasMap();

			Iterator<Entry<Integer, List<Ticket>>> iterator = busMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<Integer, List<Ticket>> entry = iterator.next();
				List<Ticket> ticketList = entry.getValue();
				for (int seatNo : seatIds) {
					for (Ticket ticket : ticketList) {
						synchronized (ticket) {
							if (seatNo == ticket.getTicketId() && ticket.isBooked()) {
								return "Booked";
							}
						}
					}
				}
			}
		}

		return "Success";
	}
}
