package com.java.clip.challenge.api.mapper;

import org.springframework.stereotype.Component;

import com.java.clip.challenge.api.dto.product.ProductDTO;
import com.java.clip.challenge.api.dto.product.NewProductResponse;
import com.java.clip.challenge.api.dto.product.UpdateProductResponse;
import com.java.clip.challenge.api.model.Product;

import ma.glasnost.orika.MapperFactory;
import net.rakugakibox.spring.boot.orika.OrikaMapperFactoryConfigurer;

@Component
public class ProductMapping implements OrikaMapperFactoryConfigurer  {
	
	@Override
	public void configure(MapperFactory orikaMapperFactory) {
		orikaMapperFactory.classMap(Product.class, NewProductResponse.class).mapNulls(false).byDefault().register();
		orikaMapperFactory.classMap(Product.class, Product.class).mapNulls(false).byDefault().register();
		orikaMapperFactory.classMap(Product.class, ProductDTO.class).mapNulls(false).byDefault().register();
		orikaMapperFactory.classMap(ProductDTO.class, Product.class).mapNulls(false).byDefault().register();
		orikaMapperFactory.classMap(Product.class, UpdateProductResponse.class).mapNulls(false).byDefault().register();
	}
	
}
