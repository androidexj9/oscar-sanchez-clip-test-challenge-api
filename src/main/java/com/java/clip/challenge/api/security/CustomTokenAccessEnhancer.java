package com.java.clip.challenge.api.security;

import static java.util.Objects.nonNull;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.java.clip.challenge.api.model.Customer;
import com.java.clip.challenge.api.repository.CustomerRepository;

@Component
public class CustomTokenAccessEnhancer implements TokenEnhancer {

	@Autowired
	public CustomerRepository customerRepository;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication authentication) {
		Map<String, Object> additionalInfo = new HashMap<>();
		Customer customer = customerRepository.findByEmail(authentication.getName());
		if (nonNull(customer)) {
			additionalInfo.put("_id", customer.getId());
		}
		((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInfo);
		return oAuth2AccessToken;
	}
}
