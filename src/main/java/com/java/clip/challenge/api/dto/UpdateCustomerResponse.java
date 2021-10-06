package com.java.clip.challenge.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCustomerResponse {
	
    private String id;
    private Integer type;
    private String firstName;
    private String lastName;
    private String email;
    private Integer status;
    
}
