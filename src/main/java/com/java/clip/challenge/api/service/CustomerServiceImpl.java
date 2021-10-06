package com.java.clip.challenge.api.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.clip.challenge.api.dto.CustomerDTO;
import com.java.clip.challenge.api.model.Customer;
import com.java.clip.challenge.api.repository.CustomerRepository;

import ma.glasnost.orika.MapperFacade;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	public static final Logger log = LogManager.getLogger(CustomerServiceImpl.class);
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private MapperFacade orikaMapperFacade;
	
	@Override
	public List<CustomerDTO> getAllCustomers() {
		log.info("CustomerServiceImpl.getAllCustomers - Before getting all the customers");
		
		List<Customer> response = customerRepository.findAll();
				
		log.info("CustomerServiceImpl.getAllCustomers - Consulted successfully on mongoDB: {}", response);
		
		return orikaMapperFacade.mapAsList(response, CustomerDTO.class);
	}

	@Override
	public void getCustomerById() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createCustomer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCustomerById() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCustomerById() {
		// TODO Auto-generated method stub
		
	}
}
