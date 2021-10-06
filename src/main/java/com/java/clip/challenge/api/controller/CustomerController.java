package com.java.clip.challenge.api.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.java.clip.challenge.api.dto.CustomerDTO;
import com.java.clip.challenge.api.dto.NewCustomerRequest;
import com.java.clip.challenge.api.dto.NewCustomerResponse;
import com.java.clip.challenge.api.dto.UpdateCustomerRequest;
import com.java.clip.challenge.api.dto.UpdateCustomerResponse;
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
		log.info("CustomerController.getAllCustomers - Calling get operation");
		return customerService.getAllCustomers();
	}

	@GetMapping("/customers/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Validation the session of the customer")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Search in the database without parameters"),
							@ApiResponse(code = 401, message = "Unauthorized"), 
							@ApiResponse(code = 403, message = "Access prohibited"),
							@ApiResponse(code = 404, message = "Not Found"), })
	public CustomerDTO getCustomerById(@PathVariable String id) {
		return customerService.getCustomerById(id);
	}

	@PostMapping(value = "/customers", produces = "application/json")
	@ResponseStatus(value = HttpStatus.CREATED)
	@ApiOperation(value = "Create New Customer in the application")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Resource created successfully"),
							@ApiResponse(code = 401, message = "Unauthorized"), 
							@ApiResponse(code = 403, message = "Access prohibited"),
							@ApiResponse(code = 404, message = "Not Found"), })
	public NewCustomerResponse createCustomer(@RequestBody NewCustomerRequest request) {
		return customerService.createCustomer(request);
	}

	@ResponseStatus(HttpStatus.OK)
	@PutMapping(value = "/customers/{id}", consumes = "application/json", produces = "application/json")
	@ApiOperation(value = "Update a Customer in the application")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Resource update successfully"),
							@ApiResponse(code = 401, message = "Unauthorized"), 
							@ApiResponse(code = 403, message = "Access prohibited"),
							@ApiResponse(code = 404, message = "Not Found"), })
	public UpdateCustomerResponse putCustomer(@RequestBody UpdateCustomerRequest request, @PathVariable String id) {
		return customerService.updateCustomerById(request, id);
	}

	@DeleteMapping(value = "/customers/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Delete a Customer")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "No Content"),
							@ApiResponse(code = 404, message = "Not Found"), })
	public void deleteCustomerById(@PathVariable String id) {
		customerService.deleteCustomerById(id);
	}

	@GetMapping("/tokens/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Validates provided access token")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found valid token"),
			 			    @ApiResponse(code = 401, message = "Unauthorized"),
			 			    @ApiResponse(code = 403, message = "Access prohibited") })
	public ResponseEntity<String> validateToken(@PathVariable String id) {
		return customerService.validateToken(id);
	}
}
