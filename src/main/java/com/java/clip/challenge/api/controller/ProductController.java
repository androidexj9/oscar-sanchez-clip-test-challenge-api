package com.java.clip.challenge.api.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.java.clip.challenge.api.dto.product.NewProductRequest;
import com.java.clip.challenge.api.dto.product.NewProductResponse;
import com.java.clip.challenge.api.dto.product.UpdateProductRequest;
import com.java.clip.challenge.api.dto.product.UpdateProductResponse;
import com.java.clip.challenge.api.dto.product.ProductDTO;
import com.java.clip.challenge.api.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/api/v1")
@Api(value = "Product - Clip Code Challenge API Project", tags = "Product Operations")
public class ProductController {

	public static final Logger log = LogManager.getLogger(ProductController.class);

	@Autowired
	ProductService productService;
	
	/**
	 * Calling get all products operation
	 * 
	 * @param 
	 * @return List<ProductDTO>
	 */
	@GetMapping(value = "/products", produces = "application/json")
	@ResponseStatus(value = HttpStatus.OK)
	@ApiOperation(value = "Get all Products of the application")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Search in the database without parameters"),
							@ApiResponse(code = 201, message = "Resource created successfully"),
							@ApiResponse(code = 401, message = "Unauthorized"), 
							@ApiResponse(code = 403, message = "Access prohibited"),
							@ApiResponse(code = 404, message = "Not Found"), })
	public List<ProductDTO> getAllProducts() {
		log.info("ProductController.getAllProducts - Calling get all products operation");
		return productService.getAllProducts();
	}
	
	/**
	 * Calling get product by id operation
	 * 
	 * @param id String
	 * @return ProductDTO
	 */
	@GetMapping("/products/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get Product by ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Search in the database without parameters"),
							@ApiResponse(code = 401, message = "Unauthorized"), 
							@ApiResponse(code = 403, message = "Access prohibited"),
							@ApiResponse(code = 404, message = "Not Found"), })
	public ProductDTO getProductById(@PathVariable String id) {
		log.info("ProductController.getProductById - Calling get product by id operation");
		return productService.getProductById(id);
	}
	
	/**
	 * Calling create product operation
	 * 
	 * @param requets NewProductRequest
	 * @return NewProductResponse
	 */
	@PostMapping(value = "/products", produces = "application/json")
	@ResponseStatus(value = HttpStatus.CREATED)
	@ApiOperation(value = "Create New Product in the application")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Resource created successfully"),
							@ApiResponse(code = 401, message = "Unauthorized"), 
							@ApiResponse(code = 403, message = "Access prohibited"),
							@ApiResponse(code = 404, message = "Not Found"), })
	public NewProductResponse createProduct(@RequestBody NewProductRequest request) {
		log.info("ProductController.createProduct - Calling create product operation");
		return productService.createProduct(request);
	}
	
	/**
	 * Calling update product operation
	 * 
	 * @param requets UpdateProductRequest
	 * @param id String
	 * @return UpdateProductResponse
	 */
	@ResponseStatus(HttpStatus.OK)
	@PutMapping(value = "/products/{id}", consumes = "application/json", produces = "application/json")
	@ApiOperation(value = "Update a Product in the application")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Resource update successfully"),
							@ApiResponse(code = 401, message = "Unauthorized"), 
							@ApiResponse(code = 403, message = "Access prohibited"),
							@ApiResponse(code = 404, message = "Not Found"), })
	public UpdateProductResponse putProduct(@RequestBody UpdateProductRequest request, @PathVariable String id) {
		log.info("ProductController.putProduct - Calling update product operation");
		return productService.updateProductById(request, id);
	}
	
	/**
	 * Calling delete product operation
	 * 
	 * @param id String
	 * @return
	 */
	@DeleteMapping(value = "/products/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Delete a Product in the application")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "No Content"),
							@ApiResponse(code = 404, message = "Not Found"), })
	public void deleteProductById(@PathVariable String id) {
		log.info("ProductController.deleteProductById - Calling delete product operation");
		productService.deleteProductById(id);
	}
}
