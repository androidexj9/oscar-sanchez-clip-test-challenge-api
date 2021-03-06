package com.java.clip.challenge.api.exception.errorhandling;

public class ErrorMessage {

	public static final String MISSING_REQUIRED_INPUT = "Required field %s is missing.";
	public static final String ID = "id";

	public static final String INVALID_INPUT = "Invalid input on field %s. Correct format is: %s";
	public static final String CORRECT_FORMAT_NUMERIC = "A number with or " + "without a decimal point.";

	public static final String EMAIL = "email";
	public static final String ALREADY_EXISTING_EMAIL = "email %s is already in use, try with another one or login.";

	public static final String PASSWORD = "password";

	public static final String NOT_FOUND_RESOURCE = "%s was not found with the given id: %s";

	public static final String CUSTOMER = "Customer";
	
	public static final String PRODUCT = "Product";
	public static final String ALREADY_EXISTING_PRODUCT_CODE = "product code %s is already in use, try with another one.";
	public static final String CODE = "code";
	
	public static final String INVALID_CREDENTIALS = "Invalid login credentials.";
	public static final String UNAVAILABLE_ENTITY = "This %s is currently unavailable.";
	public static final String VALIDATION_ERROR = "One or more fields are invalid";

	public static final String BAD_REQUEST_JSON = "Malformed JSON request.";

	public static final String BAD_REQUEST_EMPTY_NULL_NAME = "name is required.";
	public static final String BAD_REQUEST_EMPTY_NULL_FIRST_NAME = "firstName is required.";
	public static final String BAD_REQUEST_EMPTY_NULL_LAST_NAME = "lastName is required.";
	public static final String BAD_REQUEST_EMPTY_NULL_PASSWORD = "password is required.";
	public static final String BAD_REQUEST_INVALID_STATUS = "Invalid status. Field should be 0 or 1.";
	public static final String BAD_REQUEST_EMPTY_NULL_STATUS = "status is required.";
	public static final String BAD_REQUEST_EMPTY_NULL_MAIL = "email is required.";
	public static final String BAD_REQUEST_FORMAT_MAIL = "Format required: {name}@{domain].{com,es}.";
	public static final String BAD_REQUEST_MAIL_ALREADY_EXISTS = "email already exists.";
	public static final String FILTER_CHARACTERS = "Characters 'Space' and 'Line Break' are invalids";

	private ErrorMessage() {
	}

}
