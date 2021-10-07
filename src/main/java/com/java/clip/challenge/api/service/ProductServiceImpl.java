package com.java.clip.challenge.api.service;

import static com.java.clip.challenge.api.exception.errorhandling.ErrorMessage.CUSTOMER;
import static com.java.clip.challenge.api.exception.errorhandling.ErrorMessage.NOT_FOUND_RESOURCE;
import static com.java.clip.challenge.api.exception.errorhandling.ErrorMessage.PRODUCT;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.clip.challenge.api.constants.Constants;
import com.java.clip.challenge.api.dto.product.NewProductRequest;
import com.java.clip.challenge.api.dto.product.NewProductResponse;
import com.java.clip.challenge.api.dto.product.ProductDTO;
import com.java.clip.challenge.api.dto.product.UpdateProductRequest;
import com.java.clip.challenge.api.dto.product.UpdateProductResponse;
import com.java.clip.challenge.api.exception.NotFoundException;
import com.java.clip.challenge.api.mapper.ProductMapping;
import com.java.clip.challenge.api.model.Product;
import com.java.clip.challenge.api.repository.ProductRepository;
import com.java.clip.challenge.api.validator.product.NewProductValidator;
import com.java.clip.challenge.api.validator.product.UpdateProductValidator;

import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

	private static final Logger log = LogManager.getLogger(ProductServiceImpl.class);

	private static final String CLASS_NAME = ProductServiceImpl.class.getName();

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private MapperFacade orikaMapperFacade;

	@Autowired
	private ProductMapping productMapping;
	
	@Autowired
	private NewProductValidator newProductValidator;

	@Autowired
	private UpdateProductValidator updateProductValidator;

	@Override
	public List<ProductDTO> getAllProducts() {
		// 0. Initialize constants and variables
		final String METHOD_NAME = CLASS_NAME + ".getAllProducts:-";

		// 1. Log entry into the method
		log.debug(METHOD_NAME + Constants.ENTER_METHOD);

		// 2. Get All Products from productRepository
		List<Product> response = productRepository.findAll();
		log.info(METHOD_NAME + "Consulted successfully on mongoDB: {}", response);

		// 3. Log exit from the method
		log.debug(METHOD_NAME + Constants.EXIT_METHOD);
		return orikaMapperFacade.mapAsList(response, ProductDTO.class);
	}

	@Override
	public ProductDTO getProductById(String id) {
		// 0. Initialize constants and variables
		final String METHOD_NAME = CLASS_NAME + ".getProductById:-";

		// 1. Log entry into the method
		log.debug(METHOD_NAME + Constants.ENTER_METHOD);

		// 2. Get Product By ID from productRepository
		Optional<Product> productFound = productRepository.findById(id);
		if (productFound.isPresent()) {
			log.info(METHOD_NAME + "Product found successfully with id: {}", id);
			return orikaMapperFacade.map(productFound.get(), ProductDTO.class);
		}

		// 3. Log exit from the method
		log.debug(METHOD_NAME + Constants.EXIT_METHOD);
		throw new NotFoundException(String.format(NOT_FOUND_RESOURCE, PRODUCT, id));
	}

	@Override
	public List<ProductDTO> getProductByCode(String code) {
		// 0. Initialize constants and variables
		final String METHOD_NAME = CLASS_NAME + ".getProductByCode:-";

		// 1. Log entry into the method
		log.debug(METHOD_NAME + Constants.ENTER_METHOD);

		// 2. Get Product By Code from productRepository
		List<Product> products = productRepository.findByCode(code);
		log.info(METHOD_NAME + "Product found successfully with credentials: {}", products);

		// 3. Log exit from the method
		log.debug(METHOD_NAME + Constants.EXIT_METHOD);
		throw new NotFoundException(String.format(NOT_FOUND_RESOURCE, PRODUCT, code));
	}

	@Override
	public NewProductResponse createProduct(NewProductRequest request) {
		// 0. Initialize constants and variables
		final String METHOD_NAME = CLASS_NAME + ".createProduct:-";

		// 1. Log entry into the method
		log.debug(METHOD_NAME + Constants.ENTER_METHOD);

		// 2. Map Product object
		Product product = orikaMapperFacade.map(request, Product.class);

		// 3. Validations for Product object
		newProductValidator.validate(product);

		// 4. Save Product from productRepository
		Product savedProducts = productRepository.save(product);
		log.info(METHOD_NAME + "Product saved successfully with id: {}", product.getId());

		// 5. Log exit from the method
		log.debug(METHOD_NAME + Constants.EXIT_METHOD);
		return orikaMapperFacade.map(savedProducts, NewProductResponse.class);
	}

	@Override
	public UpdateProductResponse updateProductById(UpdateProductRequest request, String id) {
		// 0. Initialize constants and variables
		final String METHOD_NAME = CLASS_NAME + ".updateProductById:-";

		// 1. Log entry into the method
		log.debug(METHOD_NAME + Constants.ENTER_METHOD);

		// 2. Set id to request object
		request.setId(id);

		// 3. Map Product object
		Product productUpdatedFields = orikaMapperFacade.map(request, Product.class);

		// 4. Validations for Product object
		updateProductValidator.validate(productUpdatedFields);

		// 5. Update Product from productRepository
		Product updatedProduct = productRepository.save(productUpdatedFields);
		log.info(METHOD_NAME + "Product updated successfully with id: {}", id);

		// 7. Log exit from the method
		log.debug(METHOD_NAME + Constants.EXIT_METHOD);
		return orikaMapperFacade.map(updatedProduct, UpdateProductResponse.class);
	}

	@Override
	public void deleteProductById(String id) {
		// 0. Initialize constants and variables
		final String METHOD_NAME = CLASS_NAME + ".deleteProductById:-";

		// 1. Log entry into the method
		log.debug(METHOD_NAME + Constants.ENTER_METHOD);

		// 2. Get Product By Id from productRepository
		Optional<Product> userFoundById = productRepository.findById(id);
		if (!userFoundById.isPresent()) {
			throw new NotFoundException(String.format(NOT_FOUND_RESOURCE, CUSTOMER, id));
		}

		// 3. Delete Product By ID from productRepository
		productRepository.deleteById(id);
		log.info(METHOD_NAME + "Product deleted successfully with id: {}", id);

		// 4. Log exit from the method
		log.debug(METHOD_NAME + Constants.EXIT_METHOD);
	}

}
