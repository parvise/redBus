package com.demo.busBookingApp.rest.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.busBookingApp.entity.LoginBean;
import com.demo.busBookingApp.service.CacheService;

@RestController
@RequestMapping(value = "/cache")
public class CacheConceptController {

	@Autowired
	private CacheService service;

	@GetMapping("/getList")
	public Map<Integer, LoginBean> getData() {
		return service.getList();
	}

	@PutMapping("/update/{key}/{replaceUname}")
	public LoginBean update(@PathVariable Integer key, @PathVariable String replaceUname) {
		return service.update(key, replaceUname);
	}

	@GetMapping("/get/{key}")
	public LoginBean get(@PathVariable Integer key) {
		return service.get(key);
	}

	@PostMapping("/add/{userName}/{password}")
	public Map<Integer, LoginBean> add(@PathVariable String userName, @PathVariable String password) {
		return service.add(userName, password);
	}

	@DeleteMapping("/remove/{key}")
	public LoginBean remove(@PathVariable Integer key) {
		return service.remove(key);
	}

}
