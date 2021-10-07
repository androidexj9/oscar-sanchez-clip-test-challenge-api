package com.java.clip.challenge.api.validator.customer;

import com.java.clip.challenge.api.exception.BadRequestException;
import com.java.clip.challenge.api.model.Customer;
import com.java.clip.challenge.api.validator.Validator;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.java.clip.challenge.api.exception.GlobalExceptionBody.ErrorDetails;
import static com.java.clip.challenge.api.exception.errorhandling.ErrorMessage.*;
import static com.java.clip.challenge.api.validator.ValidationUtils.*;

@Service
public class CustomerDataValidator implements Validator<Customer> {

	private static final String TYPE = "Type";
	
	private static final String CORRECT_FORMAT_TYPE = "Number 1 for admin or number 2 for normal customer";

	private static final String FIRST_NAME = "First name";

	private static final String LAST_NAME = "Last name";

	private static final String CORRECT_FORMAT_EMAIL = "an_accepted-email.example@domain.com.mx";

	private static final String CORRECT_FORMAT_PASSWORD = "10 characters minimum with at least one lowercase letter, one uppercase letter, and one number.";

	private static final String STATUS = "Status of customer";
	
	private static final String CORRECT_FORMAT_STATUS = "Number 0 for unavailable or number 1 for available";

	@Override
	public void validate(Customer customer) {
		List<ErrorDetails> errorDetails = new ArrayList<>();
		validateType(customer.getType(), errorDetails);
		validateName(customer.getName(), errorDetails);
		validateLastName(customer.getLastName(), errorDetails);
		validateEmailFormat(customer.getEmail(), errorDetails);
		validatePassword(customer.getPassword(), errorDetails);
		validateStatus(customer.getStatus(), errorDetails);
		if (CollectionUtils.isNotEmpty(errorDetails))
			throw new BadRequestException(VALIDATION_ERROR, errorDetails);
	}

	private void validateType(Integer type, List<ErrorDetails> errorDetails) {
		if (type == null) {
			ErrorDetails error = new ErrorDetails();
			error.setErrorMessage(String.format(MISSING_REQUIRED_INPUT, TYPE));
			error.setFieldName(TYPE);
			errorDetails.add(error);
			return;
		}
		if (type < 1 || type > 2) {
			ErrorDetails error = new ErrorDetails();
			error.setErrorMessage(String.format(INVALID_INPUT, TYPE, CORRECT_FORMAT_TYPE));
			error.setFieldName(TYPE);
			errorDetails.add(error);
			return;
		}
	}

	private void validateName(String name, List<ErrorDetails> errorDetails) {
		if (!isValidString(name)) {
			ErrorDetails error = new ErrorDetails();
			error.setErrorMessage(String.format(MISSING_REQUIRED_INPUT, FIRST_NAME));
			error.setFieldName(FIRST_NAME);
			errorDetails.add(error);
			return;
		}
	}

	private void validateLastName(String lastName, List<ErrorDetails> errorDetails) {
		if (!isValidString(lastName)) {
			ErrorDetails error = new ErrorDetails();
			error.setErrorMessage(String.format(MISSING_REQUIRED_INPUT, LAST_NAME));
			error.setFieldName(LAST_NAME);
			errorDetails.add(error);
			return;
		}
	}

	private void validateEmailFormat(String email, List<ErrorDetails> errorDetails) {
		if (!isValidString(email)) {
			ErrorDetails error = new ErrorDetails();
			error.setErrorMessage(String.format(MISSING_REQUIRED_INPUT, EMAIL));
			error.setFieldName(EMAIL);
			errorDetails.add(error);
			return;
		}
		if (!isValidEmail(email)) {
			ErrorDetails error = new ErrorDetails();
			error.setErrorMessage(String.format(INVALID_INPUT, EMAIL, CORRECT_FORMAT_EMAIL));
			error.setFieldName(EMAIL);
			errorDetails.add(error);
			return;
		}
	}

	private void validatePassword(String password, List<ErrorDetails> errorDetails) {
		if (!isValidString(password)) {
			ErrorDetails error = new ErrorDetails();
			error.setErrorMessage(String.format(MISSING_REQUIRED_INPUT, PASSWORD));
			error.setFieldName(PASSWORD);
			errorDetails.add(error);
			return;
		}
		if (!isValidPassword(password)) {
			ErrorDetails error = new ErrorDetails();
			error.setErrorMessage(String.format(INVALID_INPUT, PASSWORD, CORRECT_FORMAT_PASSWORD));
			error.setFieldName(PASSWORD);
			errorDetails.add(error);
			return;
		}
	}

	private void validateStatus(Integer status, List<ErrorDetails> errorDetails) {
		if (status == null) {
			ErrorDetails error = new ErrorDetails();
			error.setErrorMessage(String.format(MISSING_REQUIRED_INPUT, STATUS));
			error.setFieldName(STATUS);
			errorDetails.add(error);
			return;
		}
		if (status < 0 || status > 1) {
			ErrorDetails error = new ErrorDetails();
			error.setErrorMessage(String.format(INVALID_INPUT, STATUS, CORRECT_FORMAT_STATUS));
			error.setFieldName(STATUS);
			errorDetails.add(error);
			return;
		}
	}
	
}
