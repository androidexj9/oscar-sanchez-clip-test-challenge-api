package com.java.clip.challenge.api.dto.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "New Product Request")
public class NewProductRequest {

	@ApiModelProperty(example = "2", value = "Value that allows to identify the type of product")
	private Integer type;

	@ApiModelProperty(example = "NIKE-01", value = "Value that allows to identify the code of product")
	private String code;

	@ApiModelProperty(example = "NIKE Retro", value = "Value that allows to identify the name of product")
	private String name;

	@ApiModelProperty(example = "img/shoes/NIKE-01.jpg", value = "Value that allows to identify the main img of product")
	private String imgMain;

	@ApiModelProperty(example = "1", value = "Value that allows to identify if the product is active or inactive")
	private Integer status;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImgMain() {
		return imgMain;
	}

	public void setImgMain(String imgMain) {
		this.imgMain = imgMain;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
