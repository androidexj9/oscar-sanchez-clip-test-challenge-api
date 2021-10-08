package com.java.clip.challenge.api.validator.customer;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.java.clip.challenge.api.exception.BadRequestException;
import com.java.clip.challenge.api.exception.NotFoundException;
import com.java.clip.challenge.api.model.Customer;
import com.java.clip.challenge.api.repository.CustomerRepository;

public class UpdateCustomerValidatorTest {

	@Mock
	private CustomerRepository customerRepository;

	@Mock
	private CustomerDataValidator customerDataValidator;

	@InjectMocks
	private UpdateCustomerValidator updateCustomerValidator;

	@SuppressWarnings("deprecation")
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testUpdateCustomerByIdWithMissingId() {
		// Given
		Customer mockUpdateCustomer = new Customer();

		Exception thrownException = assertThrows(BadRequestException.class, () -> {
			updateCustomerValidator.validate(mockUpdateCustomer);
		});
		
		assertEquals("Required field id is missing.", thrownException.getMessage());
	}

	@Test
	public void testUpdateCustomerByIdWithIdNotFound() {
		// Given
		Customer mockUpdateCustomer = new Customer();
		mockUpdateCustomer.setId("mockedId");

		when(customerRepository.findById(anyString())).thenReturn(Optional.empty());
		Exception thrownException = assertThrows(NotFoundException.class, () -> {
			updateCustomerValidator.validate(mockUpdateCustomer);
		});
		
		assertEquals("Customer was not found with the given id: mockedId", thrownException.getMessage());
	}

	@Test
	public void testUpdateCustomerByIdWithEmailAlreadyTaken() {
		// A customer with email "current_email@example.com" tries
		// to update its email to "my_email@example.com", but
		// it's already taken by another customer.
		
		// Given
		Customer mockUpdateRequest = new Customer();
		mockUpdateRequest.setId("mockedCustomerToUpdateId");
		mockUpdateRequest.setEmail("my_email@example.com");

		Customer mockOwnerOfEmail = new Customer();
		mockOwnerOfEmail.setId("mockedEmailOwnerId");
		mockOwnerOfEmail.setEmail("my_email@example.com");

		Customer mockCustomerToUpdate = new Customer();
		mockCustomerToUpdate.setId("mockedCustomerToUpdateId");
		mockCustomerToUpdate.setEmail("current_email@example.com");
		
		// Then
		when(customerRepository.findById("mockedCustomerToUpdateId")).thenReturn(Optional.of(mockCustomerToUpdate));
		doNothing().when(customerDataValidator).validate(any());
		when(customerRepository.existsByEmail(anyString())).thenReturn(true);
		Exception thrownException = assertThrows(BadRequestException.class, () -> {
			updateCustomerValidator.validate(mockUpdateRequest);
		});
		
		// Assertions
		assertEquals("email my_email@example.com is already in use, try with another one or login.", thrownException.getMessage());
	}

	@Test
	public void testUpdateCustomerByIdSuccessfullyWithNewEmail() {
		// Given
		Customer mockUpdateCustomer = new Customer();
		mockUpdateCustomer.setId("1234");
		mockUpdateCustomer.setEmail("new_unique_email@example.com");
		
		// Then
		when(customerRepository.findById(anyString())).thenReturn(Optional.of(mockUpdateCustomer));
		doNothing().when(customerDataValidator).validate(any());
		when(customerRepository.existsByEmail(anyString())).thenReturn(false);
		
		// Assertions
		assertDoesNotThrow(() -> {
			updateCustomerValidator.validate(mockUpdateCustomer);
		});
	}

	@Test
	public void testUpdateCustomerByIdWithSameEmail() {
		// Given
		Customer mockUpdateCustomer = new Customer();
		mockUpdateCustomer.setId("1234");
		mockUpdateCustomer.setEmail("my_same_email@example.com");
		
		// Then
		when(customerRepository.findById(anyString())).thenReturn(Optional.of(mockUpdateCustomer));
		doNothing().when(customerDataValidator).validate(any());
		when(customerRepository.existsByEmail(anyString())).thenReturn(true);
		
		// Assertions
		assertDoesNotThrow(() -> {
			updateCustomerValidator.validate(mockUpdateCustomer);
		});
	}
}
