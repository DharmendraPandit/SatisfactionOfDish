package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.service.RestaurantService;

/**
 * 
 * @author DharmendraPandit
 *
 */
@SpringBootApplication
public class SpringBootAssignmentApplication implements CommandLineRunner {

	@Autowired
	RestaurantService service;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootAssignmentApplication.class, args);
	}

	/**
	 * To get maximum satisfaction of disks
	 */
	@Override
	public void run(String... arg0) throws Exception {
		service.getSatisfactionResult();
	}
}
