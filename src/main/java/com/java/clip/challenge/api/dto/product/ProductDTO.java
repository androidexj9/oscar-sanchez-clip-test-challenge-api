package com.java.clip.challenge.api.dto.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Template that saves relevant product info")
public class ProductDTO {

	@ApiModelProperty(example = "615cfc3f18e6c4ce6d169279", value = "Unique Identifier")
	private String id;
	
	@ApiModelProperty(example = "2", value = "Value that allows to identify the type of product")
	private Integer type;
	
	@ApiModelProperty(example = "NIKE-01", value = "Value that allows to identify the code of product")
	private String  code;
	
	@ApiModelProperty(example = "NIKE Retro", value = "Value that allows to identify the name of product")
	private String name;
	
	@ApiModelProperty(example = "img/shoes/NIKE-01.jpg", value = "Value that allows to identify the main img of product")
	private String imgMain;
	
	@ApiModelProperty(example = "1", value = "Value that allows to identify if the product is active or inactive")
	private Integer status;
	
}
