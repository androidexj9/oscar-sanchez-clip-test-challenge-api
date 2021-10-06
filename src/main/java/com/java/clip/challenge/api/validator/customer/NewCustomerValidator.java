package com.java.clip.challenge.api.validator.customer;

import com.java.clip.challenge.api.exception.BadRequestException;
import com.java.clip.challenge.api.model.Customer;
import com.java.clip.challenge.api.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;

import static com.java.clip.challenge.api.exception.errorhandling.ErrorMessage.ALREADY_EXISTING_EMAIL;

@Service
public class NewCustomerValidator {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerDataValidator customerDataValidator;

	public void validate(Customer customer) {
		customerDataValidator.validate(customer);
		validateUniqueEmail(customer.getEmail().toLowerCase());
	}

	private void validateUniqueEmail(String email) {
		if (customerRepository.existsByEmail(email)) {
			throw new BadRequestException(String.format(ALREADY_EXISTING_EMAIL, email));
		}
	}
	
}
