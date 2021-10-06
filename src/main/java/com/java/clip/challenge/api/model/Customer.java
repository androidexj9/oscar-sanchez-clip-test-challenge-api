package com.java.clip.challenge.api.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "customers") 
@Data
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String _id;

	private Integer type;

	private String firstName;

	private String lastName;

	private String email;

	private String password;

	private Integer status;

}