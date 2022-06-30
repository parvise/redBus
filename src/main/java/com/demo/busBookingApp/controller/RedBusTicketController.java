package com.demo.busBookingApp.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.demo.busBookingApp.entity.PassengerBean;
import com.demo.busBookingApp.entity.ShowBusDetailsUI;
import com.demo.busBookingApp.service.RedBusSeatDetailsService;
import com.demo.busBookingApp.util.GetBusTicketsFromDB;

@Controller
public class RedBusTicketController {

	@Autowired
	private RedBusSeatDetailsService service;

	@GetMapping("/home")
	public String goToRedBusHome(Model model, Principal principal) {
		GetBusTicketsFromDB _getBusTicketsFromDB = GetBusTicketsFromDB.getInstance(service);
		ShowBusDetailsUI showBusDetailsUi = _getBusTicketsFromDB.getBusDetailsUi();
		Map<Integer, ShowBusDetailsUI> busDetailsUiMap = _getBusTicketsFromDB.getBusDetailsMapUi();
		System.out.println(showBusDetailsUi);
		if (showBusDetailsUi != null) {
			System.out.println("map------> " + showBusDetailsUi.getSelectedSeats());
			model.addAttribute("redBusSeatRowMap", showBusDetailsUi.getViewBusSetasMap());
		}
		if (busDetailsUiMap != null) {
			System.out.println("map------> " + busDetailsUiMap);
			model.addAttribute("busDetailsUiMap", busDetailsUiMap);
		}
		model.addAttribute("busHeaderDetails", showBusDetailsUi);
		model.addAttribute("passengerBean", new PassengerBean());
		return "redBus";
	}

}
