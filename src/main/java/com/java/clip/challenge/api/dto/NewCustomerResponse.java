package com.java.clip.challenge.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "Customer Response")
public class NewCustomerResponse {

	@ApiModelProperty(example = "615d0c37e353d5b007305d6a", value = "Unique Identifier")
	private String id;
	
}
