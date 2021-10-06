package com.java.clip.challenge.api.validator.product;

import static com.java.clip.challenge.api.exception.errorhandling.ErrorMessage.INVALID_INPUT;
import static com.java.clip.challenge.api.exception.errorhandling.ErrorMessage.MISSING_REQUIRED_INPUT;
import static com.java.clip.challenge.api.exception.errorhandling.ErrorMessage.VALIDATION_ERROR;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.java.clip.challenge.api.exception.BadRequestException;
import com.java.clip.challenge.api.exception.GlobalExceptionBody.ErrorDetails;
import com.java.clip.challenge.api.model.Product;
import com.java.clip.challenge.api.validator.Validator;

@Service
public class ProductDataValidator implements Validator<Product> {
	
	private static final String TYPE = "Type";
	
	private static final String CORRECT_FORMAT_TYPE = "Number 1 for admin or number 2 for normal product";
	
	private static final String CODE = "Code";
	
	private static final String NAME = "name";
	
	private static final String IMG_MAIN = "img main";
	
	private static final String STATUS = "Status of product";
	
	private static final String CORRECT_FORMAT_STATUS = "Number 0 for unavailable or number 1 for available";
	
	@Override
	public void validate(Product product) {
		List<ErrorDetails> errorDetails = new ArrayList<>();
		validateType(product.getType(), errorDetails);
		validateCode(product.getCode(), errorDetails);
		validateName(product.getName(), errorDetails);
		validateImgMain(product.getImgMain(), errorDetails);
		validateStatus(product.getStatus(), errorDetails);
		if (CollectionUtils.isNotEmpty(errorDetails))
			throw new BadRequestException(VALIDATION_ERROR, errorDetails);
	}
	
	private void validateType(Integer type, List<ErrorDetails> errorDetails) {
		if (type == null) {
			ErrorDetails error = new ErrorDetails();
			error.setErrorMessage(String.format(MISSING_REQUIRED_INPUT, TYPE));
			error.setFieldName(TYPE);
			errorDetails.add(error);
			return;
		}
		if (type < 1 || type > 2) {
			ErrorDetails error = new ErrorDetails();
			error.setErrorMessage(String.format(INVALID_INPUT, TYPE, CORRECT_FORMAT_TYPE));
			error.setFieldName(TYPE);
			errorDetails.add(error);
			return;
		}
	}
	
	private void validateCode(String code, List<ErrorDetails> errorDetails) {
		if (code == null) {
			ErrorDetails error = new ErrorDetails();
			error.setErrorMessage(String.format(MISSING_REQUIRED_INPUT, CODE));
			error.setFieldName(CODE);
			errorDetails.add(error);
			return;
		}
	}
	
	private void validateName(String name, List<ErrorDetails> errorDetails) {
		if (name == null) {
			ErrorDetails error = new ErrorDetails();
			error.setErrorMessage(String.format(MISSING_REQUIRED_INPUT, NAME));
			error.setFieldName(NAME);
			errorDetails.add(error);
			return;
		}
	}
	
	private void validateImgMain(String imgMain, List<ErrorDetails> errorDetails) {
		if (imgMain == null) {
			ErrorDetails error = new ErrorDetails();
			error.setErrorMessage(String.format(MISSING_REQUIRED_INPUT, IMG_MAIN));
			error.setFieldName(IMG_MAIN);
			errorDetails.add(error);
			return;
		}
	}
	
	private void validateStatus(Integer status, List<ErrorDetails> errorDetails) {
		if (status == null) {
			ErrorDetails error = new ErrorDetails();
			error.setErrorMessage(String.format(MISSING_REQUIRED_INPUT, STATUS));
			error.setFieldName(STATUS);
			errorDetails.add(error);
			return;
		}
		if (status < 0 || status > 1) {
			ErrorDetails error = new ErrorDetails();
			error.setErrorMessage(String.format(INVALID_INPUT, STATUS, CORRECT_FORMAT_STATUS));
			error.setFieldName(STATUS);
			errorDetails.add(error);
			return;
		}
	}

}
