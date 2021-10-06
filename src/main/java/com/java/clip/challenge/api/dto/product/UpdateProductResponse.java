package com.java.clip.challenge.api.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProductResponse {

	private String id;
	private Integer type;
	private String code;
	private String name;
	private String imgMain;
	private Integer status;

}
