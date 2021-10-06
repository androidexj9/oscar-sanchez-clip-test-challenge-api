package com.java.clip.challenge.api.dto.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "Product Response")
public class NewProductResponse {

	@ApiModelProperty(example = "615d0c37e353d5b007305d6a", value = "Unique Identifier")
	private String id;
	
}
