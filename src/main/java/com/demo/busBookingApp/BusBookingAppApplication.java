package com.demo.busBookingApp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class BusBookingAppApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BusBookingAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Welcome To Green Bus App");
	}
}
