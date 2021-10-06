package com.java.clip.challenge.api.dto.customer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Template that saves relevant customer info")
public class CustomerDTO {

	@ApiModelProperty(example = "615cfc3f18e6c4ce6d169279", value = "Unique Identifier")
	private String id;

	@ApiModelProperty(example = "2", value = "Value that allows to identify if the customer is admin or normal customer")
	private Integer type;

	@ApiModelProperty(example = "Oscar", value = "First name of the customer")
	private String firstName;

	@ApiModelProperty(example = "Sánchez", value = "Last name of the customer")
	private String lastName;

	@ApiModelProperty(example = "oscar.sanchez@clip.com", value = "Customer's email that allows us to log in the application, has to be unique")
	private String email;

	@ApiModelProperty(example = "1", value = "Value that allows to identify if the customer is active or inactive")
	private Integer status;
}
