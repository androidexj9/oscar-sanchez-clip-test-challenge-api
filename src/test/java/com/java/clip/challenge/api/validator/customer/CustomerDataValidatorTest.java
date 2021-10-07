package com.java.clip.challenge.api.validator.customer;

import com.java.clip.challenge.api.exception.BadRequestException;
import com.java.clip.challenge.api.model.Customer;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;
import static com.java.clip.challenge.api.exception.errorhandling.ErrorMessage.VALIDATION_ERROR;

public class CustomerDataValidatorTest {

	private Customer customer = new Customer();

	@Test
	public void itShouldThrowErrorMessagesInTypeField() {
		
		this.customer.setName("Owen");
		this.customer.setLastName("Ramirez");
		this.customer.setEmail("owen@example.com");
		this.customer.setPassword("HelloWorld123");
		this.customer.setStatus(0);
		
		assertAll(() -> {
			// Check the message when the type field is null
			this.customer.setType(null);
			Exception errorMessageException = assertThrows(BadRequestException.class, () -> {
				CustomerDataValidator customerDataValidator = new CustomerDataValidator();
				customerDataValidator.validate(customer);
			});
			assertEquals(VALIDATION_ERROR, errorMessageException.getMessage());
		}, () -> {
			// Check the message when the type field is 0
			this.customer.setType(0);
			Exception errorMessageException = assertThrows(BadRequestException.class, () -> {
				CustomerDataValidator customerDataValidator = new CustomerDataValidator();
				customerDataValidator.validate(customer);
			});
			assertEquals(VALIDATION_ERROR, errorMessageException.getMessage());
		}, () -> {
			// Check the message when the type field is -1
			this.customer.setType(-1);
			Exception errorMessageException = assertThrows(BadRequestException.class, () -> {
				CustomerDataValidator customerDataValidator = new CustomerDataValidator();
				customerDataValidator.validate(customer);
			});
			assertEquals(VALIDATION_ERROR, errorMessageException.getMessage());
		}, () -> {
			// Check the message when the type field is 3
			this.customer.setType(3);
			Exception errorMessageException = assertThrows(BadRequestException.class, () -> {
				CustomerDataValidator customerDataValidator = new CustomerDataValidator();
				customerDataValidator.validate(customer);
			});
			assertEquals(VALIDATION_ERROR, errorMessageException.getMessage());
		});
	}

	@Test
	public void itShouldThrowErrorMessagesInFirstNameField() {
		this.customer.setType(1);
		this.customer.setLastName("Ramirez");
		this.customer.setEmail("owen@example.com");
		this.customer.setPassword("HelloWorld123");
		this.customer.setStatus(0);
		
		assertAll(() -> {
			this.customer.setName(null);
			Exception exception = assertThrows(BadRequestException.class, () -> {
				CustomerDataValidator customerDataValidator = new CustomerDataValidator();
				customerDataValidator.validate(customer);
			});
			assertEquals(VALIDATION_ERROR, exception.getMessage());
		}, () -> {
			this.customer.setName("");
			Exception exception = assertThrows(BadRequestException.class, () -> {
				CustomerDataValidator customerDataValidator = new CustomerDataValidator();
				customerDataValidator.validate(customer);
			});
			assertEquals(VALIDATION_ERROR, exception.getMessage());
		}, () -> {
			this.customer.setName("   ");
			Exception exception = assertThrows(BadRequestException.class, () -> {
				CustomerDataValidator customerDataValidator = new CustomerDataValidator();
				customerDataValidator.validate(customer);
			});
			assertEquals(VALIDATION_ERROR, exception.getMessage());
		});
	}

	@Test
	public void itShouldThrowErrorMessagesInLastNameField() {
		this.customer.setType(2);
		this.customer.setName("Owen");
		this.customer.setEmail("owen@example.com");
		this.customer.setPassword("HelloWorld123");
		this.customer.setStatus(0);
		assertAll(() -> {
			this.customer.setLastName(null);
			Exception exception = assertThrows(BadRequestException.class, () -> {
				CustomerDataValidator customerDataValidator = new CustomerDataValidator();
				customerDataValidator.validate(customer);
			});
			assertEquals(VALIDATION_ERROR, exception.getMessage());
		}, () -> {
			this.customer.setLastName("");
			Exception exception = assertThrows(BadRequestException.class, () -> {
				CustomerDataValidator customerDataValidator = new CustomerDataValidator();
				customerDataValidator.validate(customer);
			});
			assertEquals(VALIDATION_ERROR, exception.getMessage());
		}, () -> {
			this.customer.setLastName("   ");
			Exception exception = assertThrows(BadRequestException.class, () -> {
				CustomerDataValidator customerDataValidator = new CustomerDataValidator();
				customerDataValidator.validate(customer);
			});
			assertEquals(VALIDATION_ERROR, exception.getMessage());
		});
	}

	@Test
	public void itShouldThrowErrorMessagesInEmailField() {
		this.customer.setType(1);
		this.customer.setName("Owen");
		this.customer.setLastName("Ramirez");
		this.customer.setPassword("HelloWorld123");
		this.customer.setStatus(0);
		assertAll(() -> {
			this.customer.setEmail(null);
			Exception exception = assertThrows(BadRequestException.class, () -> {
				CustomerDataValidator customerDataValidator = new CustomerDataValidator();
				customerDataValidator.validate(customer);
			});
			assertEquals(VALIDATION_ERROR, exception.getMessage());
		}, () -> {
			this.customer.setEmail("");
			Exception exception = assertThrows(BadRequestException.class, () -> {
				CustomerDataValidator customerDataValidator = new CustomerDataValidator();
				customerDataValidator.validate(customer);
			});
			assertEquals(VALIDATION_ERROR, exception.getMessage());
		}, () -> {
			this.customer.setEmail("     ");
			Exception exception = assertThrows(BadRequestException.class, () -> {
				CustomerDataValidator customerDataValidator = new CustomerDataValidator();
				customerDataValidator.validate(customer);
			});
			assertEquals(VALIDATION_ERROR, exception.getMessage());
		}, () -> {
			this.customer.setEmail("owenexample.com");
			Exception exception = assertThrows(BadRequestException.class, () -> {
				CustomerDataValidator customerDataValidator = new CustomerDataValidator();
				customerDataValidator.validate(customer);
			});
			assertEquals(VALIDATION_ERROR, exception.getMessage());
		}, () -> {
			this.customer.setEmail("owen@example");
			Exception exception = assertThrows(BadRequestException.class, () -> {
				CustomerDataValidator customerDataValidator = new CustomerDataValidator();
				customerDataValidator.validate(customer);
			});
			assertEquals(VALIDATION_ERROR, exception.getMessage());
		});
	}

	@Test
	public void itShouldThrowErrorMessagesPasswordField() {
		this.customer.setType(2);
		this.customer.setName("Owen");
		this.customer.setLastName("Ramirez");
		this.customer.setEmail("owen@example.com");
		this.customer.setStatus(0);
		assertAll(() -> {
			this.customer.setPassword(null);
			Exception errorMessagePassword = assertThrows(BadRequestException.class, () -> {
				CustomerDataValidator customerDataValidator = new CustomerDataValidator();
				customerDataValidator.validate(customer);
			});
			assertEquals(VALIDATION_ERROR, errorMessagePassword.getMessage());
		}, () -> {
			this.customer.setPassword("");
			Exception errorMessagePassword = assertThrows(BadRequestException.class, () -> {
				CustomerDataValidator customerDataValidator = new CustomerDataValidator();
				customerDataValidator.validate(customer);
			});
			assertEquals(VALIDATION_ERROR, errorMessagePassword.getMessage());
		}, () -> {
			this.customer.setPassword("  ");
			Exception errorMessagePassword = assertThrows(BadRequestException.class, () -> {
				CustomerDataValidator customerDataValidator = new CustomerDataValidator();
				customerDataValidator.validate(customer);
			});
			assertEquals(VALIDATION_ERROR, errorMessagePassword.getMessage());
		}, () -> {
			this.customer.setPassword("hiWorld");
			Exception errorMessagePassword = assertThrows(BadRequestException.class, () -> {
				CustomerDataValidator customerDataValidator = new CustomerDataValidator();
				customerDataValidator.validate(customer);
			});
			assertEquals(VALIDATION_ERROR, errorMessagePassword.getMessage());
		}, () -> {
			this.customer.setPassword("helloworld");
			Exception errorMessagePassword = assertThrows(BadRequestException.class, () -> {
				CustomerDataValidator customerDataValidator = new CustomerDataValidator();
				customerDataValidator.validate(customer);
			});
			assertEquals(VALIDATION_ERROR, errorMessagePassword.getMessage());
		}, () -> {
			this.customer.setPassword("helloWorld");
			Exception errorMessagePassword = assertThrows(BadRequestException.class, () -> {
				CustomerDataValidator customerDataValidator = new CustomerDataValidator();
				customerDataValidator.validate(customer);
			});
			assertEquals(VALIDATION_ERROR, errorMessagePassword.getMessage());
		});
	}

	@Test
	public void itShouldThrowErrorMessagesInStatusField() {
		this.customer.setType(1);
		this.customer.setName("Owen");
		this.customer.setLastName("Ramirez");
		this.customer.setEmail("owen@example.com");
		this.customer.setPassword("HelloWorld123");
		assertAll(() -> {
			this.customer.setStatus(null);
			Exception errorMessageStatus = assertThrows(BadRequestException.class, () -> {
				CustomerDataValidator customerDataValidator = new CustomerDataValidator();
				customerDataValidator.validate(customer);
			});
			assertEquals(VALIDATION_ERROR, errorMessageStatus.getMessage());
		}, () -> {
			this.customer.setStatus(-1);
			Exception errorMessageStatus = assertThrows(BadRequestException.class, () -> {
				CustomerDataValidator customerDataValidator = new CustomerDataValidator();
				customerDataValidator.validate(customer);
			});
			assertEquals(VALIDATION_ERROR, errorMessageStatus.getMessage());
		}, () -> {
			this.customer.setStatus(2);
			Exception errorMessageStatus = assertThrows(BadRequestException.class, () -> {
				CustomerDataValidator customerDataValidator = new CustomerDataValidator();
				customerDataValidator.validate(customer);
			});
			assertEquals(VALIDATION_ERROR, errorMessageStatus.getMessage());
		}, () -> {
			this.customer.setStatus(10);
			Exception errorMessageStatus = assertThrows(BadRequestException.class, () -> {
				CustomerDataValidator customerDataValidator = new CustomerDataValidator();
				customerDataValidator.validate(customer);
			});
			assertEquals(VALIDATION_ERROR, errorMessageStatus.getMessage());
		}, () -> {
			this.customer.setStatus(-10);
			Exception errorMessageStatus = assertThrows(BadRequestException.class, () -> {
				CustomerDataValidator customerDataValidator = new CustomerDataValidator();
				customerDataValidator.validate(customer);
			});
			assertEquals(VALIDATION_ERROR, errorMessageStatus.getMessage());
		}, () -> {
			this.customer.setStatus(0);
			CustomerDataValidator customerDataValidator = new CustomerDataValidator();
			customerDataValidator.validate(customer);
		});
	}

}
