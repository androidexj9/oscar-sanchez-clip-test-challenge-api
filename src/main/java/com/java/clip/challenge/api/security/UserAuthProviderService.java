package com.java.clip.challenge.api.security;

import static com.java.clip.challenge.api.exception.errorhandling.ErrorMessage.INVALID_CREDENTIALS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.java.clip.challenge.api.exception.AuthenticationException;
import com.java.clip.challenge.api.model.Customer;
import com.java.clip.challenge.api.repository.CustomerRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class UserAuthProviderService implements AuthenticationManager {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private RsaPasswordEncoder rsaPasswordEncoder;

	@Autowired
	private CustomUserDetailsService customerDetailsService;

	private Authentication signInUser(Customer customer) {
		UserDetails customerDetails = customerDetailsService.loadUserByUsername(customer.getEmail());
		Authentication authentication = new UsernamePasswordAuthenticationToken(customerDetails, customer.getId(), customerDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return authentication;
	}

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		String email = auth.getName();
		String password = auth.getCredentials().toString();
		password = rsaPasswordEncoder.decode(password);
		Customer customer = customerRepository.findByEmail(email);
		if (customer != null && rsaPasswordEncoder.matches(password, customer.getPassword()))
			return signInUser(customer);
		else
			throw new AuthenticationException(INVALID_CREDENTIALS);
	}
}
