package com.java.clip.challenge.api.security;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import com.java.clip.challenge.api.exception.NotFoundException;
import com.java.clip.challenge.api.model.Customer;
import com.java.clip.challenge.api.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class CustomUserDetailsService implements UserDetailsService  {

	@Autowired
	CustomerRepository customerRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws NotFoundException {
		Customer userFromDb = customerRepository.findByEmail(email);

		if (isNotEmpty(userFromDb)) {
			Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
			GrantedAuthority authority = new SimpleGrantedAuthority(userFromDb.getType() == 1 ? "ADMIN" : "USER");
			grantedAuthorities.add(authority);
			return new org.springframework.security.core.userdetails.User(userFromDb.getEmail(), userFromDb.getPassword(), grantedAuthorities);
		} else {
			throw new NotFoundException(String.format("Customer with email '%s' not found", email));
		}
	}
}
