package com.java.clip.challenge.api.validator.customer;

import com.java.clip.challenge.api.exception.BadRequestException;
import com.java.clip.challenge.api.exception.NotFoundException;
import com.java.clip.challenge.api.model.Customer;
import com.java.clip.challenge.api.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.java.clip.challenge.api.exception.errorhandling.ErrorMessage.*;
import static com.java.clip.challenge.api.validator.ValidationUtils.isValidString;

@Service
public class UpdateCustomerValidator {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerDataValidator customerDataValidator;

	public void validate(Customer customer) {
		// If the given id matches a customer, we need the email
		// of that customer for the unique email validation.
		String emailOfCustomerToUpdate = validateGivenId(customer.getId());
		customerDataValidator.validate(customer);
		validateUniqueEmail(customer.getEmail(), emailOfCustomerToUpdate);
	}

	private String validateGivenId(String id) {
		if (!isValidString(id))
			throw new BadRequestException(String.format(MISSING_REQUIRED_INPUT, ID));
		Optional<Customer> customerFoundById = customerRepository.findById(id);
		if (!customerFoundById.isPresent())
			throw new NotFoundException(String.format(NOT_FOUND_RESOURCE, CUSTOMER, id));
		return customerFoundById.get().getEmail();
	}

	private void validateUniqueEmail(String emailInRequest, String emailOfCustomerToUpdate) {
		// If the email in the request exists already, we verify it
		// belongs to the customer being updated. Otherwise, it
		// belongs to somebody else and the request is rejected.
		if (customerRepository.existsByEmail(emailInRequest) && !emailInRequest.equals(emailOfCustomerToUpdate)) {
			throw new BadRequestException(String.format(ALREADY_EXISTING_EMAIL, emailInRequest));
		}
	}
	
}
