package com.java.clip.challenge.api.service;

import static com.java.clip.challenge.api.exception.errorhandling.ErrorMessage.CUSTOMER;
import static com.java.clip.challenge.api.exception.errorhandling.ErrorMessage.NOT_FOUND_RESOURCE;
import static com.java.clip.challenge.api.service.PopulateFields.setLetterCases;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.stereotype.Service;

import com.java.clip.challenge.api.dto.CustomerDTO;
import com.java.clip.challenge.api.dto.NewCustomerRequest;
import com.java.clip.challenge.api.dto.NewCustomerResponse;
import com.java.clip.challenge.api.dto.UpdateCustomerRequest;
import com.java.clip.challenge.api.dto.UpdateCustomerResponse;
import com.java.clip.challenge.api.exception.BadRequestException;
import com.java.clip.challenge.api.exception.NotFoundException;
import com.java.clip.challenge.api.exception.UnauthorizedException;
import com.java.clip.challenge.api.mapper.CustomerMapping;
import com.java.clip.challenge.api.model.Customer;
import com.java.clip.challenge.api.repository.CustomerRepository;
import com.java.clip.challenge.api.security.RsaPasswordEncoder;
import com.java.clip.challenge.api.security.TokenStoreService;
import com.java.clip.challenge.api.validator.customer.NewCustomerValidator;
import com.java.clip.challenge.api.validator.customer.UpdateCustomerValidator;

import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
	
	public static final Logger log = LogManager.getLogger(CustomerServiceImpl.class);
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private MapperFacade orikaMapperFacade;
	
	@Autowired
	private CustomerMapping customerMapping;
	
	@Autowired
	private RsaPasswordEncoder passwordEncoder;
	
	@Autowired
	private NewCustomerValidator newCustomerValidator;

	@Autowired
	private UpdateCustomerValidator updateCustomerValidator;
	
	@Autowired
	private TokenStoreService tokenStoreService;
	
	@Override
	public List<CustomerDTO> getAllCustomers() {
		log.info("CustomerServiceImpl.getAllCustomers - Before getting all the customers");
		
		List<Customer> response = customerRepository.findAll();
				
		log.info("CustomerServiceImpl.getAllCustomers - Consulted successfully on mongoDB: {}", response);
		
		return orikaMapperFacade.mapAsList(response, CustomerDTO.class);
	}

	@Override
	public CustomerDTO getCustomerById(String id) {
		Optional<Customer> userFound = customerRepository.findById(id);
		if (userFound.isPresent())
			return orikaMapperFacade.map(userFound.get(), CustomerDTO.class);
		throw new NotFoundException(String.format(NOT_FOUND_RESOURCE, CUSTOMER, id));
	}
	
	@Override
	public List<CustomerDTO> getCustomerByEmail(String email) {
		log.info("ServiceApplicationimpl.getCustomerByEmail - Searching customers by email");
		List<Customer> users = customerRepository.findCustomerWithCredentials(email, "");
		log.info("ServiceApplicationimpl.getCustomerByEmail l operation was successful: {}", users);
		return new ArrayList<>();
	}

	@Override
	public NewCustomerResponse createCustomer(NewCustomerRequest request) {
		Customer customer = orikaMapperFacade.map(request, Customer.class);
		try{
			customer.setPassword(passwordEncoder.decode(request.getPassword()));
		} catch (Exception e){
			throw new BadRequestException("Error decrypting data");
		}
		
		newCustomerValidator.validate(customer);
		setLetterCases(customer);
		customer.setPassword(request.getPassword());
		Customer savedCustomers = customerRepository.save(customer);
		
		log.info("CustomerServiceImpl.createCustomer - Customer saved successfully with id: {}", customer.getId());
		
		return orikaMapperFacade.map(savedCustomers, NewCustomerResponse.class);
	}

	@Override
	public UpdateCustomerResponse updateCustomerById(UpdateCustomerRequest request, String id) {
		request.setId(id);
		Customer userUpdatedFields = orikaMapperFacade.map(request, Customer.class);
		try {
			userUpdatedFields.setPassword(passwordEncoder.decode(request.getPassword()));
		} catch (Exception e) {
			throw new BadRequestException("Error decrypting data");
		}
		updateCustomerValidator.validate(userUpdatedFields);
		setLetterCases(userUpdatedFields);
		userUpdatedFields.setPassword(request.getPassword());
		Customer updatedCustomer = customerRepository.save(userUpdatedFields);
		return orikaMapperFacade.map(updatedCustomer, UpdateCustomerResponse.class);
	}

	@Override
	public void deleteCustomerById(String id) {
		Optional<Customer> userFoundById = customerRepository.findById(id);
		if (!userFoundById.isPresent())
			throw new NotFoundException(String.format(NOT_FOUND_RESOURCE, CUSTOMER, id));
		customerRepository.deleteById(id);
	}

	@Override
	public ResponseEntity<String> validateToken(String token) {
		ResponseEntity<String> response = null;
		response = new ResponseEntity<String>("Valid token", HttpStatus.OK);
		OAuth2AccessToken dbToken = null;

		if (StringUtils.isBlank(token))
			throw new UnauthorizedException("Provided token can't be null nor empty");

		try {
			dbToken = tokenStoreService.readAccessToken(token);
		} catch (InvalidTokenException e) {
			throw new UnauthorizedException("Provided token not found");
		}

		if (dbToken == null)
			throw new UnauthorizedException("Provided token not found");

		if (dbToken.getTokenType() == "wildcard")
			return response;

		if (dbToken.isExpired())
			throw new UnauthorizedException("Provided token has expired");

		return response;
	}
}
