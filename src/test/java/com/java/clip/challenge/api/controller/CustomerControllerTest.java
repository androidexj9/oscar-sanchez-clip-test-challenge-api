package com.java.clip.challenge.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.java.clip.challenge.api.dto.customer.CustomerDTO;
import com.java.clip.challenge.api.dto.customer.NewCustomerResponse;
import com.java.clip.challenge.api.dto.customer.NewCustomerRequest;
import com.java.clip.challenge.api.dto.customer.UpdateCustomerRequest;
import com.java.clip.challenge.api.dto.customer.UpdateCustomerResponse;
import com.java.clip.challenge.api.service.CustomerService;

@ActiveProfiles(value = "test")
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerService customerService;

	private static final String REQUEST_MAPPING = "/api/v1";

	@Test
	public void itShouldGetAllCustomers() throws Exception {
		String getMapping = "/customers";
		when(customerService.getAllCustomers())
			.thenReturn(new ArrayList<>());
		mockMvc.perform(get(REQUEST_MAPPING + getMapping).contentType(APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	
	@Test
	public void itShouldGetCustomerById() throws Exception {
		String getMapping = "/customers/1234";
		when(customerService.getCustomerById(anyString()))
			.thenReturn(new CustomerDTO());
		mockMvc.perform(get(REQUEST_MAPPING + getMapping).contentType(APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	
	@Test
	public void itShouldCreateNewCustomer() throws Exception {
		String postMapping = "/customers";
		when(customerService.createCustomer(any(NewCustomerRequest.class)))
			.thenReturn(new NewCustomerResponse());
		mockMvc.perform(post(REQUEST_MAPPING + postMapping).content("{}").contentType(APPLICATION_JSON))
			.andExpect(status().isCreated());
	}

	@Test
	public void itShouldUpdateCustomerById() throws Exception {
		String putMapping = "/customers/1234";
		when(customerService.updateCustomerById(any(UpdateCustomerRequest.class), anyString()))
			.thenReturn(new UpdateCustomerResponse());
		mockMvc.perform(put(REQUEST_MAPPING + putMapping).content("{}").contentType(APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	
	@Test
	public void itShouldDeleteCustomerById() throws Exception {
		String deleteMapping = "/customers/1234";
		doNothing()
			.when(customerService)
			.deleteCustomerById(anyString());
		mockMvc.perform(delete(REQUEST_MAPPING + deleteMapping).contentType(APPLICATION_JSON))
			.andExpect(status().isNoContent());
	}
}
