package com.java.clip.challenge.api.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.java.clip.challenge.api.dto.product.NewProductRequest;
import com.java.clip.challenge.api.dto.product.NewProductResponse;
import com.java.clip.challenge.api.dto.product.ProductDTO;
import com.java.clip.challenge.api.dto.product.UpdateProductRequest;
import com.java.clip.challenge.api.dto.product.UpdateProductResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
	
	public static final Logger log = LogManager.getLogger(ProductServiceImpl.class);
	
	@Override
	public List<ProductDTO> getAllProducts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductDTO getProductById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NewProductResponse createProduct(NewProductRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UpdateProductResponse updateProductById(UpdateProductRequest request, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteProductById(String id) {
		// TODO Auto-generated method stub
		
	}
	
}
