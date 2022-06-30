package com.demo.busBookingApp.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.busBookingApp.entity.LoginBean;

@RestController
public class HelloWorldRestController {

	@RequestMapping(value = "/welcome", method = RequestMethod.POST)
	public ResponseEntity<LoginBean> welcome(@RequestBody LoginBean bean) {
		System.out.println(bean.getUsername());
		bean.setUsername(bean.getUsername().toUpperCase());
		bean.setPassword(bean.getPassword().toUpperCase());
		return new ResponseEntity<LoginBean>(bean, HttpStatus.OK);
	}

	@RequestMapping(value = "/hello", method = RequestMethod.POST)
	public LoginBean hello(Model model) {
		LoginBean bean = new LoginBean();
		bean.setUsername("test");
		bean.setPassword("test2");
		return bean;
	}
	
	@RequestMapping(value = "/test1", method = RequestMethod.POST)
	public LoginBean test() {
		LoginBean bean = new LoginBean();
		bean.setUsername("test");
		bean.setPassword("test2");
		return bean;
	}
}
