package com.java.clip.challenge.api.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.java.clip.challenge.api.dto.CustomerDTO;
import com.java.clip.challenge.api.service.CustomerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/api/v1")
@Api(value = "Clip Code Challenge API Project", tags = "ClipCodeChallenge")
public class CustomerController {
	
	public static final Logger log = LogManager.getLogger(CustomerController.class);
	
	@Autowired
	CustomerService customerService;
	
	@GetMapping(value = "/customers", produces = "application/json")
	@ResponseStatus(value = HttpStatus.OK)
	@ApiOperation(value = "Get all customers from the database of the application")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Search in the database without parameters"),
			@ApiResponse(code = 201, message = "Resource created successfully"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Access prohibited"),
			@ApiResponse(code = 404, message = "Not Found"), })
	public List<CustomerDTO> getAllCustomers() {
		log.info("CustomerController.getAllCustomers - Calling get operation:" + customerService.getAllCustomers().size());
		return customerService.getAllCustomers();
	}
}
