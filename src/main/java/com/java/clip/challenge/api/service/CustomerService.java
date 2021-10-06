package com.java.clip.challenge.api.service;

import java.util.List;

import com.java.clip.challenge.api.dto.CustomerDTO;

public interface CustomerService {

	public List<CustomerDTO> getAllCustomers();
	
	public void getCustomerById();
	
	public void createCustomer();
	
	public void updateCustomerById();
	
	public void deleteCustomerById();

}
