package com.java.clip.challenge.api.service;

import static com.java.clip.challenge.api.exception.errorhandling.ErrorMessage.NOT_FOUND_RESOURCE;
import static com.java.clip.challenge.api.exception.errorhandling.ErrorMessage.PRODUCT;
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

import com.java.clip.challenge.api.dto.product.NewProductRequest;
import com.java.clip.challenge.api.dto.product.NewProductResponse;
import com.java.clip.challenge.api.dto.product.ProductDTO;
import com.java.clip.challenge.api.dto.product.UpdateProductRequest;
import com.java.clip.challenge.api.dto.product.UpdateProductResponse;
import com.java.clip.challenge.api.exception.NotFoundException;
import com.java.clip.challenge.api.model.Product;
import com.java.clip.challenge.api.repository.ProductRepository;
import com.java.clip.challenge.api.validator.product.NewProductValidator;
import com.java.clip.challenge.api.validator.product.UpdateProductValidator;

import ma.glasnost.orika.MapperFacade;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProductServiceImplTest {

	@Mock
	private ProductRepository productRepository;

	@Mock
	private MapperFacade orikaMapperFacade;

	@Mock
	private NewProductValidator newProductValidator;

	@Mock
	private UpdateProductValidator updateProductValidator;

	@InjectMocks
	private ProductServiceImpl productServiceImpl;
	
	@SuppressWarnings("deprecation")
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGetAllProduct_Successfully() {
		// Then
		when(productRepository.findAll()).thenReturn(new ArrayList<>());
		when(orikaMapperFacade.mapAsList(anyList(), any())).thenReturn(new ArrayList<>());
		List<ProductDTO> testResult = productServiceImpl.getAllProducts();
		
		// Assertions
		assertNotNull(testResult);
	}
	
	
	@Test
	public void testGetProductById_Successfully() {
		// Then
		when(productRepository.findById(anyString())).thenReturn(Optional.of(new Product()));
		when(orikaMapperFacade.map(any(), any())).thenReturn(new ProductDTO());
		ProductDTO testResult = productServiceImpl.getProductById(anyString());
		
		// Assertions
		assertNotNull(testResult);
	}

	@Test
	public void testGetProductById_WithIdNotFound() {
		// Then
		when(productRepository.findById(anyString())).thenReturn(Optional.empty());
		NotFoundException thrownException = assertThrows(NotFoundException.class, () -> productServiceImpl.getProductById(anyString()));
		
		// Assertions
		assertEquals(String.format(NOT_FOUND_RESOURCE, PRODUCT, ""), thrownException.getMessage());
	}
	
	@Test
	public void testCreateProduct_Successfully() {
		// Given
		Product mockCreateProduct = new Product();
		mockCreateProduct.setName("");
		mockCreateProduct.setCode("");
		mockCreateProduct.setImgMain("");
		mockCreateProduct.setStatus(1);
		mockCreateProduct.setType(1);
		
		NewProductRequest newProductRequest = new NewProductRequest();
		newProductRequest.setName("John");
		newProductRequest.setCode("");
		newProductRequest.setImgMain("");
		newProductRequest.setStatus(1);
		newProductRequest.setType(1);
		
		// Then
		when(orikaMapperFacade.map(any(NewProductRequest.class), any())).thenReturn(mockCreateProduct);
		doNothing().when(newProductValidator).validate(any());
		when(productRepository.save(any())).thenReturn(new Product());
		when(orikaMapperFacade.map(any(Product.class), any())).thenReturn(new NewProductResponse());
		
		// Assertions
		assertNotNull(productServiceImpl.createProduct(newProductRequest));
	}
	
	@Test
	public void testUpdateProductById_Successfully() {
		// Given
		Product mockUpdateProduct = new Product();
		mockUpdateProduct.setName("");
		mockUpdateProduct.setType(1);
		mockUpdateProduct.setImgMain("");
		mockUpdateProduct.setStatus(1);
		mockUpdateProduct.setId("");

		// Then
		when(orikaMapperFacade.map(any(UpdateProductRequest.class), any())).thenReturn(mockUpdateProduct);
		doNothing().when(updateProductValidator).validate(any());
		when(productRepository.save(any())).thenReturn(new Product());
		when(orikaMapperFacade.map(any(Product.class), any())).thenReturn(new UpdateProductResponse());
		
		// Assertions
		assertNotNull(productServiceImpl.updateProductById(new UpdateProductRequest(), ""));
	}
	
	
	@Test
	public void testDeleteProductById_Successfully() {
		// Then
		when(productRepository.findById(anyString())).thenReturn(Optional.of(new Product()));
		doNothing().when(productRepository).deleteById(anyString());
		productServiceImpl.deleteProductById(anyString());
		
		// Assertions
		verify(productRepository).deleteById(anyString());
	}

	@Test
	public void testDeleteProductById_WithIdNotFound() {
		// Then
		when(productRepository.findById(anyString())).thenReturn(Optional.empty());
		doNothing().when(productRepository).deleteById(anyString());
		NotFoundException thrownException = assertThrows(NotFoundException.class, () -> productServiceImpl.deleteProductById(anyString()));
		
		// Assertions
		assertEquals(String.format(NOT_FOUND_RESOURCE, PRODUCT, ""), thrownException.getMessage());
	}
}
