package com.java.clip.challenge.api.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.java.clip.challenge.api.dto.product.ProductDTO;
import com.java.clip.challenge.api.model.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String>  {
	
	List<Product> findAll();

	ProductDTO findById();

	@Query("{'code' : ?0}")
	List<Product> findByCode(String code);
	
	boolean existsByCode(String code);
	
}
