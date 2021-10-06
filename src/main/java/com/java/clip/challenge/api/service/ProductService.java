package com.java.clip.challenge.api.service;

import java.util.List;

import com.java.clip.challenge.api.dto.product.NewProductRequest;
import com.java.clip.challenge.api.dto.product.NewProductResponse;
import com.java.clip.challenge.api.dto.product.ProductDTO;
import com.java.clip.challenge.api.dto.product.UpdateProductRequest;
import com.java.clip.challenge.api.dto.product.UpdateProductResponse;

public interface ProductService {

	List<ProductDTO> getAllProducts();

	ProductDTO getProductById(String id);
	
	List<ProductDTO> getProductByCode(String code);

	NewProductResponse createProduct(NewProductRequest request);

	UpdateProductResponse updateProductById(UpdateProductRequest request, String id);

	void deleteProductById(String id);

}
