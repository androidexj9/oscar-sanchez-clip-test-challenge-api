package com.java.clip.challenge.api.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.java.clip.challenge.api.dto.customer.CustomerDTO;
import com.java.clip.challenge.api.dto.customer.NewCustomerRequest;
import com.java.clip.challenge.api.dto.customer.NewCustomerResponse;
import com.java.clip.challenge.api.dto.customer.UpdateCustomerRequest;
import com.java.clip.challenge.api.dto.customer.UpdateCustomerResponse;

public interface CustomerService {

	List<CustomerDTO> getAllCustomers();
	
	CustomerDTO getCustomerById(String id);
	
	List<CustomerDTO> getCustomerByEmail(String email);
	
	NewCustomerResponse createCustomer(NewCustomerRequest request);
	
	UpdateCustomerResponse updateCustomerById(UpdateCustomerRequest request, String id);
	
	void deleteCustomerById(String id);

	ResponseEntity<String> validateToken(String id);

}
