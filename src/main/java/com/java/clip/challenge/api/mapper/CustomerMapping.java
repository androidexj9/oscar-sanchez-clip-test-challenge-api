package com.java.clip.challenge.api.mapper;

import org.springframework.stereotype.Component;

import com.java.clip.challenge.api.dto.CustomerDTO;
import com.java.clip.challenge.api.dto.LoginResponse;
import com.java.clip.challenge.api.dto.NewCustomerResponse;
import com.java.clip.challenge.api.dto.UpdateCustomerResponse;
import com.java.clip.challenge.api.model.Customer;

import ma.glasnost.orika.MapperFactory;
import net.rakugakibox.spring.boot.orika.OrikaMapperFactoryConfigurer;

@Component
public class CustomerMapping implements OrikaMapperFactoryConfigurer {
	
	@Override
	public void configure(MapperFactory orikaMapperFactory) {
		orikaMapperFactory.classMap(Customer.class, NewCustomerResponse.class).mapNulls(false).byDefault().register();
		orikaMapperFactory.classMap(Customer.class, Customer.class).mapNulls(false).byDefault().register();
		orikaMapperFactory.classMap(Customer.class, CustomerDTO.class).mapNulls(false).byDefault().register();
		orikaMapperFactory.classMap(CustomerDTO.class, Customer.class).mapNulls(false).byDefault().register();
		orikaMapperFactory.classMap(Customer.class, UpdateCustomerResponse.class).mapNulls(false).byDefault().register();
		orikaMapperFactory.classMap(Customer.class, LoginResponse.class).mapNulls(false).byDefault().register();
	}
	
}
