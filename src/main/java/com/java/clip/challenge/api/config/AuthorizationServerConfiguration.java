package com.java.clip.challenge.api.config;

import com.java.clip.challenge.api.security.TokenStoreService;
import com.java.clip.challenge.api.security.CustomTokenAccessEnhancer;
import com.java.clip.challenge.api.security.CustomUserDetailsService;
import com.java.clip.challenge.api.security.UserAuthProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private UserAuthProviderService userAuthProviderService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private TokenStoreService tokenStoreService;

	@Value("${clip.client.id}")
	private String clientId;

	@Value("${clip.client.secret}")
	private String clientSecret;

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.allowFormAuthenticationForClients().checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient(clientId).secret(passwordEncoder.encode(clientSecret))
				.authorizedGrantTypes("password", "client_credentials").scopes("all").accessTokenValiditySeconds(3600);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		endpoints.pathMapping("/oauth/token", "/api/v1/login")
				.tokenStore(tokenStoreService).authenticationManager(userAuthProviderService)
				.userDetailsService(userDetailsService).tokenEnhancer(getCustomTokenAccessTokenEnhancer());
	}

	@Bean
	public TokenEnhancer getCustomTokenAccessTokenEnhancer() {
		return new CustomTokenAccessEnhancer();
	}
}
