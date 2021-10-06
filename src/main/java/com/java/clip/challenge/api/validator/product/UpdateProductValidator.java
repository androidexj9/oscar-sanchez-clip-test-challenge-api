package com.java.clip.challenge.api.validator.product;

import static com.java.clip.challenge.api.exception.errorhandling.ErrorMessage.ALREADY_EXISTING_PRODUCT_CODE;
import static com.java.clip.challenge.api.exception.errorhandling.ErrorMessage.CODE;
import static com.java.clip.challenge.api.exception.errorhandling.ErrorMessage.MISSING_REQUIRED_INPUT;
import static com.java.clip.challenge.api.exception.errorhandling.ErrorMessage.NOT_FOUND_RESOURCE;
import static com.java.clip.challenge.api.exception.errorhandling.ErrorMessage.PRODUCT;
import static com.java.clip.challenge.api.validator.ValidationUtils.isValidString;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.clip.challenge.api.exception.BadRequestException;
import com.java.clip.challenge.api.exception.NotFoundException;
import com.java.clip.challenge.api.model.Product;
import com.java.clip.challenge.api.repository.ProductRepository;

@Service
public class UpdateProductValidator {
	
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductDataValidator productDataValidator;
	
	public void validate(Product product) {
		productDataValidator.validate(product);
		
		//String codeOfProductToUpdate = validateGivenCode(product.getCode());
		//validateUniqueProductCode(product.getCode(), codeOfProductToUpdate);
	}
	
	/*private String validateGivenCode(String code) {
		if (!isValidString(code)) {
			throw new BadRequestException(String.format(MISSING_REQUIRED_INPUT, CODE));
		}
		Product productFoundByCode = productRepository.findByCode(code);
		if (productFoundByCode == null) {
			throw new NotFoundException(String.format(NOT_FOUND_RESOURCE, PRODUCT, code));
		}
		return productFoundByCode.getCode();
	}*/

	private void validateUniqueProductCode(String codeInRequest, String codeOfProductToUpdate) {
		if (productRepository.existsByCode(codeInRequest) && !codeInRequest.equals(codeOfProductToUpdate)) {
			throw new BadRequestException(String.format(ALREADY_EXISTING_PRODUCT_CODE, codeInRequest));
		}
	}

}
