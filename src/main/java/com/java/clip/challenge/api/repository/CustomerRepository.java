package com.java.clip.challenge.api.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.java.clip.challenge.api.dto.customer.CustomerDTO;
import com.java.clip.challenge.api.model.Customer;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String>  {
	
	List<Customer> findAll();

	CustomerDTO findById();

	@Query("{ 'email' : ?0, 'password' : ?1 }")
	List<Customer> findCustomerWithCredentials(String email, String password);

	@Query("{'email' : ?0}")
	Customer findByEmail(String email);

	boolean existsByEmail(String email);
}
