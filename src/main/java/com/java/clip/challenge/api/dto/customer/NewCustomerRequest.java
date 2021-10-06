package com.java.clip.challenge.api.dto.customer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "New Customer Request")
public class NewCustomerRequest {

	@ApiModelProperty(example = "2", value = "Value that allows to identify if the customer is admin or normal customer")
	private Integer type;

	@ApiModelProperty(example = "Oscar", value = "First name of the customer")
	private String firstName;

	@ApiModelProperty(example = "Sánchez", value = "Last name of the customer")
	private String lastName;

	@ApiModelProperty(example = "oscar.sanchez@clip.com", value = "Customer's email that allows us to log in the application, has to be unique")
	private String email;

	@ApiModelProperty(example = "$2a$10$yT5LCrve73db3UQ2DzuNjuSEP4Sgw8rcBERkp7/tIrC1LAaeUsUsG", value = "Secret Code to enter to the email")
	private String password;

	@ApiModelProperty(example = "1", value = "Value that allows to identify if the customer is active or inactive")
	private Integer status;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
