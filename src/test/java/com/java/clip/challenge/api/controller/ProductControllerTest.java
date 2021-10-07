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

import com.java.clip.challenge.api.dto.product.ProductDTO;
import com.java.clip.challenge.api.dto.product.NewProductResponse;
import com.java.clip.challenge.api.dto.product.NewProductRequest;
import com.java.clip.challenge.api.dto.product.UpdateProductRequest;
import com.java.clip.challenge.api.dto.product.UpdateProductResponse;
import com.java.clip.challenge.api.service.ProductService;

@ActiveProfiles(value = "test")
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductService productService;

	private static final String REQUEST_MAPPING = "/api/v1";

	@Test
	public void itShouldGetAllProducts() throws Exception {
		String getMapping = "/products";
		when(productService.getAllProducts())
			.thenReturn(new ArrayList<>());
		mockMvc.perform(get(REQUEST_MAPPING + getMapping).contentType(APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	
	@Test
	public void itShouldGetProductById() throws Exception {
		String getMapping = "/products/1234";
		when(productService.getProductById(anyString()))
			.thenReturn(new ProductDTO());
		mockMvc.perform(get(REQUEST_MAPPING + getMapping).contentType(APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	
	@Test
	public void itShouldCreateNewProduct() throws Exception {
		String postMapping = "/products";
		when(productService.createProduct(any(NewProductRequest.class)))
			.thenReturn(new NewProductResponse());
		mockMvc.perform(post(REQUEST_MAPPING + postMapping).content("{}").contentType(APPLICATION_JSON))
			.andExpect(status().isCreated());
	}

	@Test
	public void itShouldUpdateProductById() throws Exception {
		String putMapping = "/products/1234";
		when(productService.updateProductById(any(UpdateProductRequest.class), anyString()))
			.thenReturn(new UpdateProductResponse());
		mockMvc.perform(put(REQUEST_MAPPING + putMapping).content("{}").contentType(APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	
	@Test
	public void itShouldDeleteProductById() throws Exception {
		String deleteMapping = "/products/1234";
		doNothing()
			.when(productService)
			.deleteProductById(anyString());
		mockMvc.perform(delete(REQUEST_MAPPING + deleteMapping).contentType(APPLICATION_JSON))
			.andExpect(status().isNoContent());
	}
}
