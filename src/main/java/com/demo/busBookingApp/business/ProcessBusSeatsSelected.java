package com.demo.busBookingApp.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.demo.busBookingApp.entity.PassengerBean;
import com.demo.busBookingApp.entity.PassengerBookingTickets;
import com.demo.busBookingApp.entity.ShowBusDetailsUI;
import com.demo.busBookingApp.entity.Ticket;
import com.demo.busBookingApp.util.GetBusTicketsFromDB;

public class ProcessBusSeatsSelected {

	@SuppressWarnings("unchecked")
	public List<PassengerBookingTickets> updateSeletectedSeats(HttpServletRequest request, int serviceNo) {
		ShowBusDetailsUI showBusDetailsUI = GetBusTicketsFromDB.getBusDetailsUi();

		Map<Integer, ShowBusDetailsUI> busDetailsUiMap = GetBusTicketsFromDB.getBusDetailsMapUi();
		if (busDetailsUiMap.containsKey(serviceNo))
			showBusDetailsUI = busDetailsUiMap.get(serviceNo);

		Map<Integer, List<Ticket>> busMap = showBusDetailsUI.getViewBusSetasMap();
		Iterator<Entry<Integer, List<Ticket>>> iterator = busMap.entrySet().iterator();
		List<Ticket> selectedSeatList = new ArrayList<>();

		HttpSession session = request.getSession(true);

		Map<Integer, Map<String, List<Ticket>>> serviceNoSeatsMap = (Map<Integer, Map<String, List<Ticket>>>) session
				.getAttribute("serviceNoSeatsMap");
		if (serviceNoSeatsMap == null)
			serviceNoSeatsMap = new HashMap<Integer, Map<String, List<Ticket>>>();

		String[] uiTicketIdList = request.getParameterValues("ticketId[]");
		StringBuilder builder = new StringBuilder();
		builder.append("'");
		String message = "Success";
		if (uiTicketIdList != null) {
			while (iterator.hasNext()) {
				Entry<Integer, List<Ticket>> entry = iterator.next();
				for (String uiTicket : uiTicketIdList) {
					int ticketNo = Integer.parseInt(uiTicket);
					// System.out.println("iterator" + iterator);
					if (iterator != null) {
						List<Ticket> ticketList = entry.getValue();
						for (Ticket ticket : ticketList) {
							synchronized (ticket) {
								if (ticket.isSelected() && !ticket.isBlocked() && ticket.getTicketId() == ticketNo) {
									ticket.setBlocked(true);
									ticket.setBtnStyleClsName("btnDBlocked");
									ticket.setSeletedTime(new Date());
									builder.append(ticket.getTicketId() + "#");
									selectedSeatList.add(ticket);
									message = "Success";
								}
							}
						}
					}
				}
			}
		}

		Map<String, List<Ticket>> userSelectdSeatsMap = null;
		if (serviceNoSeatsMap.containsKey(showBusDetailsUI.getServiceNo())) {
			userSelectdSeatsMap = serviceNoSeatsMap.get(showBusDetailsUI.getServiceNo());
			if (userSelectdSeatsMap == null) {
				userSelectdSeatsMap = new HashMap();
			}
		} else if (userSelectdSeatsMap == null) {
			userSelectdSeatsMap = new HashMap();
		}

		userSelectdSeatsMap.put(request.getParameter("passengerName"), selectedSeatList);
		if (!message.equalsIgnoreCase("Blocked") && selectedSeatList.size() > 0) {
			builder.append("','" + showBusDetailsUI.getTotalSeats() + "'");
			showBusDetailsUI.setSelectedSeats(builder.toString());
			// System.out.println("Continue clicked " +
			// showBusDetailsUI.getSelectedSeats());

			// System.out.println("updateSeletectedSeats session " + session);
			serviceNoSeatsMap.put(showBusDetailsUI.getServiceNo(), userSelectdSeatsMap);
			session.setAttribute("serviceNoSeatsMap", serviceNoSeatsMap);

			return getRequestSeatsFromUI(request, serviceNo);
		}
		return null;

	}

	public List<PassengerBookingTickets> getRequestSeatsFromUI(HttpServletRequest request, int serviceNo) {
		HttpSession session = request.getSession(false);
		System.out.println("Method getRequestSeatsFromUI begins ");
		@SuppressWarnings("unchecked")
		Map<Integer, Map<String, List<Ticket>>> serviceNoSeatsMap = (Map<Integer, Map<String, List<Ticket>>>) session
				.getAttribute("serviceNoSeatsMap");
		if (serviceNoSeatsMap == null)
			serviceNoSeatsMap = new HashMap<Integer, Map<String, List<Ticket>>>();
		String primpPassengerName = request.getParameter("passengerName");
		List<Ticket> selectedSeatList = null;
		if (serviceNoSeatsMap != null) {
			if (serviceNoSeatsMap.containsKey(serviceNo)) {
				Map<String, List<Ticket>> userSelectdSeatsMap = serviceNoSeatsMap.get(serviceNo);
				if (serviceNoSeatsMap != null) {
					if (userSelectdSeatsMap.containsKey(primpPassengerName)) {
						selectedSeatList = userSelectdSeatsMap.get(primpPassengerName);
					}
				}
			}
		}

		PassengerBean bean = new PassengerBean();
		bean.setPassengerName(primpPassengerName);
		bean.setMobileNo(request.getParameter("mobileNo"));
		bean.setEmailId(request.getParameter("emailId"));

		String[] gender = request.getParameterValues("gender[]");
		bean.setGender(gender);

		String[] name = request.getParameterValues("name[]");
		bean.setName(name);

		String[] ageList = request.getParameterValues("age[]");
		int count = 0;
		if (ageList != null) {
			int[] ageNumber = new int[ageList.length];
			for (String printAge : ageList) {
				if (printAge != null && printAge != "") {
					int age = Integer.parseInt(printAge);
					ageNumber[count] = age;
					count++;
				}
			}
			bean.setAge(ageNumber);
		}

		String[] ticketIdList = request.getParameterValues("ticketId[]");
		if (ticketIdList != null) {
			int[] tickeId = new int[ticketIdList.length];
			Date[] bookedDateTime = new Date[ticketIdList.length];
			count = 0;
			for (String ticket : ticketIdList) {
				int ticketNo = Integer.parseInt(ticket);
				for (Ticket ticketId : selectedSeatList) {
					if (ticketNo == ticketId.getTicketId()) {
						// ticketId.setSeletedTime(new Date());
						bookedDateTime[count] = ticketId.getSeletedTime();
					}
				}
				tickeId[count] = ticketNo;
				count++;
			}
			bean.setTicketId(tickeId);
			bean.setBookedDateTime(bookedDateTime);
		}

		// System.out.println("Pass---." + bean);

		List<PassengerBookingTickets> ticketBeanList = new ArrayList<>();
		int countInc = 0;
		for (String genderItr : bean.getGender()) {
			PassengerBookingTickets ticketBean = new PassengerBookingTickets();
			ticketBean.setServiceNo(serviceNo);
			ticketBean.setPrimaryPassName(bean.getPassengerName());
			ticketBean.setEmailId(bean.getEmailId());
			ticketBean.setGender(genderItr);
			ticketBean.setAge(bean.getAge()[countInc]);
			ticketBean.setName(bean.getName()[countInc]);
			ticketBean.setTicketId(bean.getTicketId()[countInc]);
			ticketBean.setMobileNo(bean.getMobileNo());
			ticketBean.setBookeDateTime(bean.getBookedDateTime()[countInc]);
			countInc++;
			ticketBean.setsNo(countInc);

			ticketBeanList.add(ticketBean);
		}

		return ticketBeanList;
	}
}
