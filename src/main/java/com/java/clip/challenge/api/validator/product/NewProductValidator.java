package com.java.clip.challenge.api.validator.product;

import static com.java.clip.challenge.api.exception.errorhandling.ErrorMessage.ALREADY_EXISTING_PRODUCT_CODE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.clip.challenge.api.exception.BadRequestException;
import com.java.clip.challenge.api.model.Product;
import com.java.clip.challenge.api.repository.ProductRepository;

@Service
public class NewProductValidator {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductDataValidator productDataValidator;
	
	public void validate(Product product) {
		productDataValidator.validate(product);
		validateUniqueCode(product.getCode().toUpperCase());
	}
	
	private void validateUniqueCode(String code) {
		if (productRepository.existsByCode(code)) {
			throw new BadRequestException(String.format(ALREADY_EXISTING_PRODUCT_CODE, code));
		}
	}

}
