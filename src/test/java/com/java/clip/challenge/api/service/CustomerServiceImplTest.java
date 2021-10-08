package com.java.clip.challenge.api.service;

import static com.java.clip.challenge.api.exception.errorhandling.ErrorMessage.CUSTOMER;
import static com.java.clip.challenge.api.exception.errorhandling.ErrorMessage.NOT_FOUND_RESOURCE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.java.clip.challenge.api.dto.customer.CustomerDTO;
import com.java.clip.challenge.api.dto.customer.NewCustomerRequest;
import com.java.clip.challenge.api.dto.customer.NewCustomerResponse;
import com.java.clip.challenge.api.dto.customer.UpdateCustomerRequest;
import com.java.clip.challenge.api.dto.customer.UpdateCustomerResponse;
import com.java.clip.challenge.api.exception.NotFoundException;
import com.java.clip.challenge.api.model.Customer;
import com.java.clip.challenge.api.repository.CustomerRepository;
import com.java.clip.challenge.api.security.RsaPasswordEncoder;
import com.java.clip.challenge.api.validator.customer.NewCustomerValidator;
import com.java.clip.challenge.api.validator.customer.UpdateCustomerValidator;

import ma.glasnost.orika.MapperFacade;

@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerServiceImplTest {

	@Mock
	private CustomerRepository customerRepository;

	@Mock
	private MapperFacade orikaMapperFacade;

	@Mock
	private NewCustomerValidator newCustomerValidator;

	@Mock
	private UpdateCustomerValidator updateCustomerValidator;

	@Mock
	private RsaPasswordEncoder passwordEncoder;

	@InjectMocks
	private CustomerServiceImpl customerServiceImpl;

	@SuppressWarnings("deprecation")
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetAllCustomers_Successfully() {
		// Then
		when(customerRepository.findAll()).thenReturn(new ArrayList<>());
		when(orikaMapperFacade.mapAsList(anyList(), any())).thenReturn(new ArrayList<>());
		List<CustomerDTO> testResult = customerServiceImpl.getAllCustomers();
		
		assertNotNull(testResult);
	}
	
	
	@Test
	public void testGetCustomerById_Successfully() {
		// Then
		when(customerRepository.findById(anyString())).thenReturn(Optional.of(new Customer()));
		when(orikaMapperFacade.map(any(), any())).thenReturn(new CustomerDTO());
		CustomerDTO testResult = customerServiceImpl.getCustomerById(anyString());
		
		assertNotNull(testResult);
	}

	@Test
	public void testGetCustomerById_WithIdNotFound() {
		// Then
		when(customerRepository.findById(anyString())).thenReturn(Optional.empty());
		NotFoundException thrownException = assertThrows(NotFoundException.class, () -> customerServiceImpl.getCustomerById(anyString()));
		
		assertEquals(String.format(NOT_FOUND_RESOURCE, CUSTOMER, ""), thrownException.getMessage());
	}
	

	@Test
	public void testCreateCustomer_Successfully() {
		// Given
		Customer mockCreateCustomer = new Customer();
		mockCreateCustomer.setName("");
		mockCreateCustomer.setLastName("");
		mockCreateCustomer.setEmail("");
		
		String encryptedPassword = "cKtW2JkL0STGZ6uhs/8R/isp9hGPtE49GVLQQVPGMz/UVPlvCEuLP7ZofRmCBFlXJdeeOraUIC4qWPt+yCC/ndlYRu2vW9MituTxSYgq+CU50TiB3/4u4Qn1Iv2I34X11ybyMPT30uZXpEshsf7ZxNdk9cv19wat3Abn5mNwqWZomyCiMvyz7yc0o50AM6KpCTIDAZ5fyxFjwEo0QfxzMoxQjOc3qlmF35b34hbygC+vxgo0Aq4cEv+upcN/8vfGFsCdjOS87byRiy1HmBuYSm1ieY7IJlM5/N0WMhLHku07XrGJAUnTN4LhfJtPJSSRjxe4aqPpvbElYorqFTdbdQ==";
		
		NewCustomerRequest newCustomerRequest = new NewCustomerRequest();
		newCustomerRequest.setName("John");
		newCustomerRequest.setLastName("Petrucci");
		newCustomerRequest.setPassword(encryptedPassword);
		newCustomerRequest.setEmail("test-at@gmail.com");
		newCustomerRequest.setStatus(1);
		
		// Then
		when(orikaMapperFacade.map(any(NewCustomerRequest.class), any())).thenReturn(mockCreateCustomer);
		doNothing().when(newCustomerValidator).validate(any());
		when(passwordEncoder.encode(anyString())).thenReturn("");
		when(customerRepository.existsByEmail(anyString())).thenReturn(false);
		when(customerRepository.save(any())).thenReturn(new Customer());
		when(orikaMapperFacade.map(any(Customer.class), any())).thenReturn(new NewCustomerResponse());
		
		assertNotNull(customerServiceImpl.createCustomer(newCustomerRequest));
	}
	

	@Test
	public void testUpdateCustomerById_Successfully() {
		// Given
		Customer mockUpdateCustomer = new Customer();
		mockUpdateCustomer.setName("");
		mockUpdateCustomer.setLastName("");
		mockUpdateCustomer.setEmail("");

		// Then
		when(orikaMapperFacade.map(any(UpdateCustomerRequest.class), any())).thenReturn(mockUpdateCustomer);
		doNothing().when(updateCustomerValidator).validate(any());
		when(passwordEncoder.encode(anyString())).thenReturn("");
		when(customerRepository.save(any())).thenReturn(new Customer());
		when(orikaMapperFacade.map(any(Customer.class), any())).thenReturn(new UpdateCustomerResponse());
		
		assertNotNull(customerServiceImpl.updateCustomerById(new UpdateCustomerRequest(), ""));
	}
	
	
	@Test
	public void testDeleteCustomerById_Successfully() {
		// Then
		when(customerRepository.findById(anyString())).thenReturn(Optional.of(new Customer()));
		doNothing().when(customerRepository).deleteById(anyString());
		customerServiceImpl.deleteCustomerById(anyString());
		
		verify(customerRepository).deleteById(anyString());
	}

	@Test
	public void testDeleteCustomerById_WithIdNotFound() {
		// Then
		when(customerRepository.findById(anyString())).thenReturn(Optional.empty());
		doNothing().when(customerRepository).deleteById(anyString());
		NotFoundException thrownException = assertThrows(NotFoundException.class, () -> customerServiceImpl.deleteCustomerById(anyString()));
		
		assertEquals(String.format(NOT_FOUND_RESOURCE, CUSTOMER, ""), thrownException.getMessage());
	}
}
