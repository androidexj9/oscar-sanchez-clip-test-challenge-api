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

import com.java.clip.challenge.api.constants.Constants;
import com.java.clip.challenge.api.dto.customer.CustomerDTO;
import com.java.clip.challenge.api.dto.customer.NewCustomerRequest;
import com.java.clip.challenge.api.dto.customer.NewCustomerResponse;
import com.java.clip.challenge.api.dto.customer.UpdateCustomerRequest;
import com.java.clip.challenge.api.dto.customer.UpdateCustomerResponse;
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
	
	private static final Logger log = LogManager.getLogger(CustomerServiceImpl.class);
	
	private static final String CLASS_NAME = CustomerServiceImpl.class.getName();
	
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
		// 0. Initialize constants and variables
		final String METHOD_NAME = CLASS_NAME + ".getAllCustomers:-";
		
		// 1. Log entry into the method
		log.debug(METHOD_NAME + Constants.ENTER_METHOD);
		
		// 2. Get All Customers from customerRepository
		List<Customer> response = customerRepository.findAll();
		log.info(METHOD_NAME + "Consulted successfully on mongoDB: {}", response);
		
		// 3. Log exit from the method
		log.debug(METHOD_NAME + Constants.EXIT_METHOD);
		return orikaMapperFacade.mapAsList(response, CustomerDTO.class);
	}

	@Override
	public CustomerDTO getCustomerById(String id) {
		// 0. Initialize constants and variables
		final String METHOD_NAME = CLASS_NAME + ".getCustomerById:-";
		
		// 1. Log entry into the method
		log.debug(METHOD_NAME + Constants.ENTER_METHOD);
		
		// 2. Get Customer By ID from customerRepository
		Optional<Customer> userFound = customerRepository.findById(id);
		if (userFound.isPresent()) {
			log.info(METHOD_NAME + "Customer found successfully with id: {}", id);
			return orikaMapperFacade.map(userFound.get(), CustomerDTO.class);
		}
		
		// 3. Log exit from the method
		log.debug(METHOD_NAME + Constants.EXIT_METHOD);
		throw new NotFoundException(String.format(NOT_FOUND_RESOURCE, CUSTOMER, id));
	}
	
	@Override
	public List<CustomerDTO> getCustomerByEmail(String email) {
		// 0. Initialize constants and variables
		final String METHOD_NAME = CLASS_NAME + ".getCustomerByEmail:-";
		
		// 1. Log entry into the method
		log.debug(METHOD_NAME + Constants.ENTER_METHOD);
		
		// 2. Get Customer By Email from customerRepository
		List<Customer> users = customerRepository.findCustomerWithCredentials(email, "");
		log.info(METHOD_NAME + "Customer found successfully with credentials: {}", users);
		
		// 3. Log exit from the method
		log.debug(METHOD_NAME + Constants.EXIT_METHOD);
		return new ArrayList<>();
	}

	@Override
	public NewCustomerResponse createCustomer(NewCustomerRequest request) {
		// 0. Initialize constants and variables
		final String METHOD_NAME = CLASS_NAME + ".createCustomer:-";
		
		// 1. Log entry into the method
		log.debug(METHOD_NAME + Constants.ENTER_METHOD);
		
		// 2. Map Customer object
		Customer customer = orikaMapperFacade.map(request, Customer.class);
		try{
			customer.setPassword(passwordEncoder.decode(request.getPassword()));
		} catch (Exception e){
			throw new BadRequestException("Error decrypting data");
		}
		
		// 3. Validations for Customer object
		newCustomerValidator.validate(customer);
		
		// 3.1. Extra validations for Customer object
		setLetterCases(customer);
		customer.setPassword(request.getPassword());
		
		// 4. Save Customer from customerRepository
		Customer savedCustomers = customerRepository.save(customer);
		log.info(METHOD_NAME + "Customer saved successfully with id: {}", customer.getId());
		
		// 5. Log exit from the method
		log.debug(METHOD_NAME + Constants.EXIT_METHOD);
		return orikaMapperFacade.map(savedCustomers, NewCustomerResponse.class);
	}

	@Override
	public UpdateCustomerResponse updateCustomerById(UpdateCustomerRequest request, String id) {
		// 0. Initialize constants and variables
		final String METHOD_NAME = CLASS_NAME + ".updateCustomerById:-";
		
		// 1. Log entry into the method
		log.debug(METHOD_NAME + Constants.ENTER_METHOD);
		
		// 2. Set id to request object
		request.setId(id);
		
		// 3. Map Customer object
		Customer userUpdatedFields = orikaMapperFacade.map(request, Customer.class);
		try {
			userUpdatedFields.setPassword(passwordEncoder.decode(request.getPassword()));
		} catch (Exception e) {
			throw new BadRequestException("Error decrypting data");
		}
		
		// 4. Validations for Customer object
		updateCustomerValidator.validate(userUpdatedFields);
		
		// 4.1. Extra validations for Customer object
		setLetterCases(userUpdatedFields);
		
		// 5. Set password to userUpdatedFields object
		userUpdatedFields.setPassword(request.getPassword());
		
		// 6. Update Customer from customerRepository
		Customer updatedCustomer = customerRepository.save(userUpdatedFields);
		log.info(METHOD_NAME + "Customer updated successfully with id: {}", id);
		
		// 7. Log exit from the method
		log.debug(METHOD_NAME + Constants.EXIT_METHOD);
		return orikaMapperFacade.map(updatedCustomer, UpdateCustomerResponse.class);
	}

	@Override
	public void deleteCustomerById(String id) {
		// 0. Initialize constants and variables
		final String METHOD_NAME = CLASS_NAME + ".deleteCustomerById:-";
		
		// 1. Log entry into the method
		log.debug(METHOD_NAME + Constants.ENTER_METHOD);
		
		// 2. Get Customer By Id from customerRepository
		Optional<Customer> userFoundById = customerRepository.findById(id);
		if (!userFoundById.isPresent()) {
			throw new NotFoundException(String.format(NOT_FOUND_RESOURCE, CUSTOMER, id));
		}
		
		// 3. Delete Customer By ID from customerRepository
		customerRepository.deleteById(id);
		log.info(METHOD_NAME + "Customer deleted successfully with id: {}", id);
		
		// 4. Log exit from the method
		log.debug(METHOD_NAME + Constants.EXIT_METHOD);
	}

	@Override
	public ResponseEntity<String> validateToken(String token) {
		// 0. Initialize constants and variables
		final String METHOD_NAME = CLASS_NAME + ".validateToken:-";
		
		// 1. Log entry into the method
		log.debug(METHOD_NAME + Constants.ENTER_METHOD);
		
		// 2. Create ResponseEntity<String> object
		ResponseEntity<String> response = null;
		response = new ResponseEntity<String>("Valid token", HttpStatus.OK);
		OAuth2AccessToken dbToken = null;
		
		// 3. Validations for token object
		if (StringUtils.isBlank(token)) {
			throw new UnauthorizedException("Provided token can't be null nor empty");
		}

		try {
			dbToken = tokenStoreService.readAccessToken(token);
		} catch (InvalidTokenException e) {
			throw new UnauthorizedException("Provided token not found");
		}

		if (dbToken == null) {
			throw new UnauthorizedException("Provided token not found");
		}

		if (dbToken.getTokenType() == "wildcard") {
			return response;
		}

		if (dbToken.isExpired()) {
			throw new UnauthorizedException("Provided token has expired");
		}
		
		// 4. Log exit from the method
		log.debug(METHOD_NAME + Constants.EXIT_METHOD);
		return response;
	}
}
