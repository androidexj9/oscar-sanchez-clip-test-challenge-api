package com.java.clip.challenge.api.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.java.clip.challenge.api.model.Customer;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String>  {
	
	List<Customer> findAll();
}
