package com.java.clip.challenge.api.validator.customer;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.java.clip.challenge.api.exception.BadRequestException;
import com.java.clip.challenge.api.model.Customer;
import com.java.clip.challenge.api.repository.CustomerRepository;

public class NewCustomerValidatorTest {

	@Mock
	private CustomerRepository customerRepository;

	@Mock
	private CustomerDataValidator customerDataValidator;

	@InjectMocks
	private NewCustomerValidator newCustomerValidator;

	@SuppressWarnings("deprecation")
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCreateCustomerWithEmailAlreadyTaken() {
		// Given
		Customer mockCreateCustomer = new Customer();
		mockCreateCustomer.setEmail("email_already_taken@example.com");
		
		// Then
		doNothing().when(customerDataValidator).validate(any());
		when(customerRepository.existsByEmail(anyString())).thenReturn(true);
		Exception thrownException = assertThrows(BadRequestException.class, () -> { 
			newCustomerValidator.validate(mockCreateCustomer);
		});
		
		// Assertions
		assertEquals(String.format("email %s is already in use, try with another one or login.", mockCreateCustomer.getEmail()), thrownException.getMessage());
	}

	@Test
	public void testCreateCustomerWithNewEmail() {
		// Given
		Customer mockCreateCustomer = new Customer();
		mockCreateCustomer.setEmail("new_unique_email@example.com");
		
		// Then
		doNothing().when(customerDataValidator).validate(any());
		when(customerRepository.existsByEmail(anyString())).thenReturn(false);
		
		// Assertions
		assertDoesNotThrow(() -> {
			newCustomerValidator.validate(mockCreateCustomer);
		});
	}
}
