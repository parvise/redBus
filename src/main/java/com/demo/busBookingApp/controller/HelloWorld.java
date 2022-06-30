package com.demo.busBookingApp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import com.demo.busBookingApp.entity.LoginBean;

@Controller
public class HelloWorld {

	@Autowired
	private RestOperations operations;

	@Bean
	public RestOperations restOperations() {
		return new RestTemplate();
	}

	@GetMapping("/")
	public String goToHome() {
		return "bootStrap";
	}

	@GetMapping("/test")
	public String goToTest(Model model) {
		LoginBean bean = new LoginBean();
		// bean.setUsername("RRRRR");
		// bean.setPassword("Welcome");
		model.addAttribute("bean", bean);

		return "Test1";
	}

	// @PostMapping("/processLogin")
	@RequestMapping(value = "/processLogin", method = RequestMethod.POST)
	public String process(Model model, @ModelAttribute("bean") LoginBean bean, @RequestParam Map<String, String> name)
			throws Exception {
		System.out.println("Welcoem" + bean);
		System.out.println("Test" + name);
		Map<String, Object> map = new HashMap<>();
		map.put("bean", bean);

		LoginBean message = operations.postForObject("http://10.188.31.125:2020/busBookingApp/welcome", bean,
				LoginBean.class);
		System.out.println("Test " + message);
		model.addAttribute("bean", message);
		return "Test1";
	}
}
