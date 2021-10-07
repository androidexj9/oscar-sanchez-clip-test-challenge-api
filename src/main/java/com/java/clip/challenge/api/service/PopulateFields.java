package com.java.clip.challenge.api.service;

import com.java.clip.challenge.api.model.Customer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PopulateFields {
	
	public static void setLetterCases(Customer request){
		request.setName(request.getName().toLowerCase());
		request.setLastName(request.getLastName().toLowerCase());
		request.setEmail(request.getEmail().toLowerCase());
	}

	private PopulateFields() {
	}
	
}
